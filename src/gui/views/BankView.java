package gui.views;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modules.Client;
import modules.bank.BankCard;
import modules.bank.Card;
import modules.bank.CreditCard;
import modules.bank.DebitCard;
import system.Database;
import system.exceptions.DataCardException;

/**
 * Bank view to creating new cards, accounts, clients
 * 
 * @author Sebastian Smoliñski
 * @since 0.1
 * @version 0.5
 * @see View
 * @category GUI
 */
public class BankView extends View {
	
	// Variables pool
	private HashMap<String, JComponent> customers_components, cards_components; 
	private ArrayList<Client> clients; 
	private ArrayList<Card> cards;
	private Database database = new Database(); 
	private JTabbedPane tabbedPane;
	
	/**
	 * Construct runs at the start of program when views list is binded with names
	 * @param display - root pane where GUI will be render.
	 */
	public BankView(Container display) {
		super(display);
		
		view.setLayout(new BoxLayout(view, BoxLayout.PAGE_AXIS));
		customers_components = new HashMap<String, JComponent>(); 
		cards_components = new HashMap<String, JComponent>(); 
		clients = new ArrayList<Client>();
		cards = new ArrayList<Card>();
		
		// Add components to HashMaps 
		tabbedPane = new JTabbedPane();
		customers_components = new HashMap<String, JComponent>(); 
		customers_components.put("leftPanel", new JPanel()); 
		customers_components.put("rightPanel", new JPanel()); 
		customers_components.put("splitPane", new JSplitPane(JSplitPane.HORIZONTAL_SPLIT)); 
		customers_components.put("nameField", new JTextField());
		customers_components.put("surnameField", new JTextField());
		customers_components.put("peselField", new JTextField());
		customers_components.put("editButton", new JButton("Edytuj"));
		customers_components.put("addButton", new JButton("Dodaj"));
		customers_components.put("deleteButton", new JButton("Usuñ"));
		customers_components.put("customersList", new JList<String>());
		customers_components.put("scrollPane",  new JScrollPane());
		customers_components.put("customersPanel", new JPanel());
		
		cards_components.put("leftPanel", new JPanel()); 
		cards_components.put("rightPanel", new JPanel()); 
		cards_components.put("splitPane", new JSplitPane(JSplitPane.HORIZONTAL_SPLIT)); 
		cards_components.put("cardNumField", new JTextField());
		cards_components.put("balanceField", new JTextField());
		cards_components.put("dateField", new JTextField());
		cards_components.put("ownerComboBox", new JComboBox<Client>());
		cards_components.put("typeComboBox", new JComboBox<String>());
		cards_components.put("editButton", new JButton("Edytuj"));
		cards_components.put("addButton", new JButton("Dodaj"));
		cards_components.put("deleteButton", new JButton("Usuñ"));
		cards_components.put("cardsList", new JList<String>());
		cards_components.put("scrollPane",  new JScrollPane());
		cards_components.put("cardsPanel", new JPanel());
	}

	/**
	 * @see View
	 */
	@Override
	public String getName() {
		return "Bank";
	}
	
	/**
	 * Sets default close operation with action such as save before exit
	 */
	public void setCloseOperation() {
		((JFrame)display.getParent().getParent().getParent()).setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		((JFrame)display.getParent().getParent().getParent()).addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ex) {
            	try {
					database.SUser(center.getUsers());
					database.SCompany(center.getCompanies());
					database.SBank(center.getBanks());
				} catch (IOException e) {
					e.printStackTrace();
				}
            	
	        	((JFrame)display.getParent().getParent().getParent()).setVisible(false);
	        	((JFrame)display.getParent().getParent().getParent()).dispose();
            }
        });
	}
	
	/**
	 * Updates client and cards lists before log in to specified bank
	 */
	public void updateLists() {
		clients = center.getLoggedBank().getClients();
		
		((JList<String>)customers_components.get("customersList")).setModel(new DefaultListModel<String>() {
			private static final long serialVersionUID = 1L;
			ArrayList<Client> values = clients; 
			
			public int getSize() {
				return values.size();
			}

			public String getElementAt(int index) {
				return values.get(index).getName() + " " + values.get(index).getSurname();
			}
		});
		
		if(clients.size() != 0) {
			((JTextField)customers_components.get("nameField")).setText(clients.get(0).getName());
			((JTextField)customers_components.get("surnameField")).setText(clients.get(0).getSurname());
			((JTextField)customers_components.get("peselField")).setText(clients.get(0).getPesel());
			((JList<String>)customers_components.get("customersList")).setSelectedIndex(0);
		} 
		
		
		for(Client client : clients) {
			if(client.getCards() != null)
				cards.addAll(client.getCards()); 
		}
		
		
		if(cards.size() != 0) {
			((JList<String>)cards_components.get("cardsList")).setModel(new DefaultListModel<String>() {
				private static final long serialVersionUID = 1L;
				ArrayList<Card> values = cards;
				
				public int getSize() {
					return values.size();
				}

				public String getElementAt(int index) {
					return values.get(index).getNumber()+"";
				}
			});
			
			((JList<String>)cards_components.get("cardsList")).setSelectedIndex(0);	
		}
	}
	
	/**
	 * Initialize components - sets look of objects and sets positions
	 */
	public void initComponents() {
		
		
		// Companies Panel design create
		initCustomersPanel();
		initCardsPanel(); 
		
		
		view.add(tabbedPane);
	}
	
	/**
	 * Initialize and set customers design 
	 */
	private void initCustomersPanel() {
		customers_components.get("editButton").setPreferredSize(new Dimension(100, 30));
		customers_components.get("addButton").setPreferredSize(new Dimension(100, 30));
		customers_components.get("deleteButton").setPreferredSize(new Dimension(100, 30));
		
		customers_components.get("customersPanel").setLayout(new BorderLayout(0, 0));
		((JSplitPane)customers_components.get("splitPane")).setLeftComponent(customers_components.get("leftPanel"));
		((JSplitPane)customers_components.get("splitPane")).setRightComponent(customers_components.get("rightPanel"));
		
		((JSplitPane)customers_components.get("splitPane")).setEnabled(false);
		((JPanel)customers_components.get("leftPanel")).setLayout(new BorderLayout(0, 0));

		((JPanel)customers_components.get("customersPanel")).add(((JSplitPane)customers_components.get("splitPane")));
		
		((JList<String>)customers_components.get("customersList")).setModel(new DefaultListModel<String>() {
			private static final long serialVersionUID = 1L;
			ArrayList<Client> values = clients;
			
			public int getSize() {
				return values.size();
			}

			public String getElementAt(int index) {
				return values.get(index).getName() + " " + values.get(index).getSurname();
			}
		});
		
		((JList<String>)customers_components.get("customersList")).setFixedCellHeight(20);
		((JList<String>)customers_components.get("customersList")).setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		((JList<String>)customers_components.get("customersList")).setPreferredSize(new Dimension(100, 0));
		((JList<String>)customers_components.get("customersList")).setSelectedIndex(0);
		
		((JScrollPane)customers_components.get("scrollPane")).setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		((JScrollPane)customers_components.get("scrollPane")).setViewportView(((JList<String>)customers_components.get("customersList")));
		
		((JPanel)customers_components.get("leftPanel")).add(((JScrollPane)customers_components.get("scrollPane")));

		((JPanel)customers_components.get("rightPanel")).setLayout(new GridBagLayout());
		((JPanel)customers_components.get("rightPanel")).setMaximumSize(((JPanel)customers_components.get("rightPanel")).getPreferredSize()); 
		
		GridBagConstraints cntr = new GridBagConstraints();
		
		((JTextField)customers_components.get("nameField")).setText(clients.get(0).getName());
		((JTextField)customers_components.get("nameField")).setColumns(10); 
		((JTextField)customers_components.get("nameField")).setToolTipText("Imiê klienta");
		((JTextField)customers_components.get("nameField")).setPreferredSize(new Dimension(120, 30));
		
		cntr.gridx = 0;
		cntr.gridy = 0;
		cntr.insets = new Insets(0, 0, 5, 0);
		cntr.anchor = GridBagConstraints.CENTER;
		
		((JPanel)customers_components.get("rightPanel")).add(((JTextField)customers_components.get("nameField")), cntr); 
		
		cntr.gridy = 1;
		((JTextField)customers_components.get("surnameField")).setText(clients.get(0).getSurname());
		((JTextField)customers_components.get("surnameField")).setColumns(10); 
		((JTextField)customers_components.get("surnameField")).setToolTipText("Nazwisko klienta");
		((JTextField)customers_components.get("surnameField")).setPreferredSize(new Dimension(120, 30));

		((JPanel)customers_components.get("rightPanel")).add(((JTextField)customers_components.get("surnameField")), cntr); 
		
		cntr.gridy = 2;
		((JTextField)customers_components.get("peselField")).setText(clients.get(0).getPesel());
		((JTextField)customers_components.get("peselField")).setColumns(10); 
		((JTextField)customers_components.get("peselField")).setToolTipText("PESEL klienta");
		((JTextField)customers_components.get("peselField")).setPreferredSize(new Dimension(120, 30));

		((JPanel)customers_components.get("rightPanel")).add(((JTextField)customers_components.get("peselField")), cntr); 
		
		cntr.gridy = 3;
		cntr.insets = new Insets(0, 0, 5, 0);
		((JPanel)customers_components.get("rightPanel")).add(((JButton)customers_components.get("editButton")), cntr);

		cntr.gridy = 4;
		((JPanel)customers_components.get("rightPanel")).add(((JButton)customers_components.get("deleteButton")), cntr);

		cntr.gridy = 5;
		((JPanel)customers_components.get("rightPanel")).add(((JButton)customers_components.get("addButton")), cntr);
		
		tabbedPane.addTab("Klienci", null, ((JPanel)customers_components.get("customersPanel")), "Zarz¹dzaj spisem klientów");
	}
	
	/**
	 * Initialize and set cards design 
	 */
	private void initCardsPanel() {
		cards_components.get("editButton").setPreferredSize(new Dimension(100, 30));
		cards_components.get("addButton").setPreferredSize(new Dimension(100, 30));
		cards_components.get("deleteButton").setPreferredSize(new Dimension(100, 30));
		
		cards_components.get("cardsPanel").setLayout(new BorderLayout(0, 0));
		((JSplitPane)cards_components.get("splitPane")).setLeftComponent(cards_components.get("leftPanel"));
		((JSplitPane)cards_components.get("splitPane")).setRightComponent(cards_components.get("rightPanel"));
		
		((JSplitPane)cards_components.get("splitPane")).setEnabled(false);
		((JPanel)cards_components.get("leftPanel")).setLayout(new BorderLayout(0, 0));

		((JPanel)cards_components.get("cardsPanel")).add(((JSplitPane)cards_components.get("splitPane")));
		
		if(cards.size() == 0)
			((JList<String>)cards_components.get("cardsList")).setModel(new DefaultListModel<String>() {
				private static final long serialVersionUID = 1L;
				
				public int getSize() {
					return 0;
				}
	
				public String getElementAt(int index) {
					return "Test";
				}
			});
			
		((JList<String>)cards_components.get("cardsList")).setFixedCellHeight(20);
		((JList<String>)cards_components.get("cardsList")).setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		((JList<String>)cards_components.get("cardsList")).setPreferredSize(new Dimension(100, 0));
		((JList<String>)cards_components.get("cardsList")).setSelectedIndex(0);
		
		((JScrollPane)cards_components.get("scrollPane")).setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		((JScrollPane)cards_components.get("scrollPane")).setViewportView(((JList<String>)cards_components.get("cardsList")));
		
		((JPanel)cards_components.get("leftPanel")).add(((JScrollPane)cards_components.get("scrollPane")));

		((JPanel)cards_components.get("rightPanel")).setLayout(new GridBagLayout());
		((JPanel)cards_components.get("rightPanel")).setMaximumSize(((JPanel)cards_components.get("rightPanel")).getPreferredSize()); 
		
		GridBagConstraints cntr = new GridBagConstraints();
		
		((JTextField)cards_components.get("cardNumField")).setText(((JList<String>)cards_components.get("cardsList")).getModel().getElementAt(0));
		((JTextField)cards_components.get("cardNumField")).setColumns(10); 
		((JTextField)cards_components.get("cardNumField")).setToolTipText("Numer karty");
		((JTextField)cards_components.get("cardNumField")).setPreferredSize(new Dimension(120, 30));
		
		cntr.gridx = 0;
		cntr.gridy = 0;
		cntr.insets = new Insets(0, 0, 5, 0);
		cntr.anchor = GridBagConstraints.CENTER;
		
		((JPanel)cards_components.get("rightPanel")).add(((JTextField)cards_components.get("cardNumField")), cntr); 
		
		if(cards.size() == 0) {
			((JTextField)cards_components.get("balanceField")).setText("Stan konta");
			((JTextField)cards_components.get("balanceField")).setColumns(10); 
			((JTextField)cards_components.get("balanceField")).setPreferredSize(new Dimension(120, 30));
		} else {
			((JTextField)cards_components.get("balanceField")).setText(cards.get(0).getBalance()+"");
			((JTextField)cards_components.get("balanceField")).setColumns(10); 
			((JTextField)cards_components.get("balanceField")).setPreferredSize(new Dimension(120, 30));
		}
		
		((JTextField)cards_components.get("balanceField")).setToolTipText("Bilans konta");
		
		cntr.gridy = 1;
		((JPanel)cards_components.get("rightPanel")).add(((JTextField)cards_components.get("balanceField")), cntr); 
		
		if(cards.size() == 0) {
			((JTextField)cards_components.get("dateField")).setText("Data wygaœniêcia");
			((JTextField)cards_components.get("dateField")).setColumns(10); 
			((JTextField)cards_components.get("dateField")).setPreferredSize(new Dimension(120, 30));
		} else {
			((JTextField)cards_components.get("dateField")).setText(cards.get(0).getExp_date());
			((JTextField)cards_components.get("dateField")).setColumns(10); 
			((JTextField)cards_components.get("dateField")).setPreferredSize(new Dimension(120, 30));
		}
		
		((JTextField)cards_components.get("dateField")).setToolTipText("Data wygaœniêcia karty");
		
		cntr.gridy = 2;
		((JPanel)cards_components.get("rightPanel")).add(((JTextField)cards_components.get("dateField")), cntr); 
		
		for(Client client : clients) {
			((JComboBox<Client>)cards_components.get("ownerComboBox")).addItem(client); 
		}
		
		((JComboBox<Client>)cards_components.get("ownerComboBox")).setToolTipText("W³aœciciel konta");
		
		cntr.gridy = 3;
		((JPanel)cards_components.get("rightPanel")).add(((JComboBox<Card>)cards_components.get("ownerComboBox")), cntr); 
		
		cntr.gridy = 4;
		cntr.insets = new Insets(0, 0, 25, 0);
		((JComboBox<String>)cards_components.get("typeComboBox")).addItem("Kredytowa");
		((JComboBox<String>)cards_components.get("typeComboBox")).addItem("Bankomatowa");
		((JComboBox<String>)cards_components.get("typeComboBox")).addItem("Debetowa");
		((JPanel)cards_components.get("rightPanel")).add(((JComboBox<String>)cards_components.get("typeComboBox")), cntr); 

		cntr.gridy = 5;
		cntr.insets = new Insets(0, 0, 5, 0);
		((JPanel)cards_components.get("rightPanel")).add(((JButton)cards_components.get("editButton")), cntr);

		cntr.gridy = 6;
		((JPanel)cards_components.get("rightPanel")).add(((JButton)cards_components.get("deleteButton")), cntr);

		cntr.gridy = 7;
		((JPanel)cards_components.get("rightPanel")).add(((JButton)cards_components.get("addButton")), cntr);
		
		tabbedPane.addTab("Karty", null, ((JPanel)cards_components.get("cardsPanel")), "Zarz¹dzaj spisem kart");
	}
	
	/**
	 * Initialize action listeners for elements
	 */
	public void initActions() {
		// Handle add new customer list element action 
		((JButton)customers_components.get("addButton")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				clients.add(new Client("Nowy"+clients.size(), "Nowy", ""+clients.size()));
				 
				((JList<String>)customers_components.get("customersList")).setModel(new DefaultListModel<String>() {
					private static final long serialVersionUID = 1L;
					ArrayList<Client> values = clients; 
					
					public int getSize() {
						return values.size();
					}

					public String getElementAt(int index) {
						return values.get(index).getName() + " " + values.get(index).getSurname();
					}
				});
				
				if (((JList<String>)customers_components.get("customersList")).getSize().height <= clients.size() * 20)
					((JList<String>)customers_components.get("customersList")).setPreferredSize(new Dimension(100, ((JList<String>)customers_components.get("customersList")).getSize().height + 20));
				
				((JComboBox<Client>)cards_components.get("ownerComboBox")).removeAllItems();
				for(Client client : clients) {
					((JComboBox<Client>)cards_components.get("ownerComboBox")).addItem(client); 
				}
			}
		}); 
		
		// Handle remove customer element from list action
		((JButton)customers_components.get("deleteButton")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				String name = ((JTextField)customers_components.get("nameField")).getText();
				String pesel = ((JTextField)customers_components.get("peselField")).getText();
				
				if (name == null)
					return;
				
				Iterator<Client> iter = clients.iterator();
				while (iter.hasNext()) {
				    Client client = iter.next();

				    if (client.getPesel().equals(pesel))
				        iter.remove();
				}
				
				((JList<String>)customers_components.get("customersList")).setModel(new DefaultListModel<String>() {
					private static final long serialVersionUID = 1L;
					ArrayList<Client> values = clients; 
					
					public int getSize() {
						return values.size();
					}

					public String getElementAt(int index) {
						return values.get(index).getName() + " " + values.get(index).getSurname();
					}
				});
				
				if (((JList<String>)customers_components.get("customersList")).getSize().height >= (clients.size() - 1) * 20)
					((JList<String>)customers_components.get("customersList")).setPreferredSize(new Dimension(100, ((JList<String>)customers_components.get("customersList")).getSize().height - 20));
				
				((JComboBox<Client>)cards_components.get("ownerComboBox")).removeAllItems();
				for(Client client : clients) {
					((JComboBox<Client>)cards_components.get("ownerComboBox")).addItem(client); 
				}
			}
		});
		
		// Handle edit customers list element action
		((JButton)customers_components.get("editButton")).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				int order = ((JList<String>)customers_components.get("customersList")).getSelectedIndex(); 
				
				if(order < 0) 
					return;
				
				String nName = ((JTextField)customers_components.get("nameField")).getText();
				String nSurname = ((JTextField)customers_components.get("surnameField")).getText();
				String nPesel = ((JTextField)customers_components.get("peselField")).getText();
				
				if(!nPesel.equals(clients.get(order).getPesel()))
					for(Client client : clients) 
						if(client.getPesel().equals(nPesel)) {
							JOptionPane.showMessageDialog(display,
								    "Taki PESEL znajduje siê ju¿ w bazie!",
								    "B³¹d",
								    JOptionPane.ERROR_MESSAGE);
							return;
						}
				
				if(nPesel.length() < 11) {
					JOptionPane.showMessageDialog(display,
						    "Wprowadzono b³êdny PESEL",
						    "B³¹d",
						    JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				for(int i = 0; i < nPesel.length(); i++) {
					if(!Character.isDigit(nPesel.charAt(i))) {
						JOptionPane.showMessageDialog(display,
							    "Wprowadzono b³êdny PESEL",
							    "B³¹d",
							    JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				
				clients.get(order).setName(nName);
				clients.get(order).setSurname(nSurname);
				clients.get(order).setPesel(nPesel);
				
				((JList<String>)customers_components.get("customersList")).setModel(new DefaultListModel<String>() {
					private static final long serialVersionUID = 1L;
					ArrayList<Client> values = clients; 
					
					public int getSize() {
						return values.size();
					}

					public String getElementAt(int index) {
						return values.get(index).getName() + " " + values.get(index).getSurname();
					}
				});
				
				((JComboBox<Client>)cards_components.get("ownerComboBox")).removeAllItems();
				for(Client client : clients) {
					((JComboBox<Client>)cards_components.get("ownerComboBox")).addItem(client); 
				}
			}
		});
		
		// Handle switch customers list element action 
		((JList<String>)customers_components.get("customersList")).addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent ev) {
				int index = ((JList<String>) ev.getSource()).getSelectedIndex(); 
				
				if(index < 0)
					return;
				
				((JTextField)customers_components.get("nameField")).setText(clients.get(index).getName());
				((JTextField)customers_components.get("surnameField")).setText(clients.get(index).getSurname());
				((JTextField)customers_components.get("peselField")).setText(clients.get(index).getPesel());
			}
		});
		
		// Handle add new card list element action 
		((JButton)cards_components.get("addButton")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					int maxID = 0; 
					for(Card card : cards) {
						if(card.getNumber() > maxID) 
							maxID = card.getNumber();
					}
					cards.add(new CreditCard(maxID+1, "2018-05", 1000));
				} catch (DataCardException e) {
					e.printStackTrace();
				}
				 
				((JList<String>)cards_components.get("cardsList")).setModel(new DefaultListModel<String>() {
					private static final long serialVersionUID = 1L;
					ArrayList<Card> values = cards; 
					
					public int getSize() {
						return values.size();
					}

					public String getElementAt(int index) {
						return values.get(index).getNumber()+"";
					}
				});
				
				if (((JList<String>)cards_components.get("cardsList")).getSize().height <= cards.size() * 20)
					((JList<String>)cards_components.get("cardsList")).setPreferredSize(new Dimension(100, ((JList<String>)cards_components.get("cardsList")).getSize().height + 20));
				
			}
		}); 
		
		// Handle remove cards element from list action
		((JButton)cards_components.get("deleteButton")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				int cardNumber = Integer.parseInt(((JList<String>)cards_components.get("cardsList")).getSelectedValue()); 
				
				if (cardNumber == 0)
					return;
				
				Iterator<Client> iter = clients.iterator();
				while (iter.hasNext()) {
				    Client client = iter.next();
	
				    if (client.hasCard(cardNumber)) {
				    	 try {
							client.deleteCard(cardNumber);
						} catch (ClassNotFoundException | IOException e) {
							e.printStackTrace();
						}
				    }
				}
				
				cards.clear();
				for(Client client : clients) {
					if(client.getCards() != null)
						cards.addAll(client.getCards()); 
				}
				((JList<String>)cards_components.get("cardsList")).setModel(new DefaultListModel<String>() {
					private static final long serialVersionUID = 1L;
					ArrayList<Card> values = cards; 
					
					public int getSize() {
						return values.size();
					}
	
					public String getElementAt(int index) {
						return values.get(index).getNumber()+"";
					}
				});
				
				if (((JList<String>)cards_components.get("cardsList")).getSize().height >= (cards.size() - 1) * 20)
					((JList<String>)cards_components.get("cardsList")).setPreferredSize(new Dimension(100, ((JList<String>)cards_components.get("cardsList")).getSize().height - 20));
			}
		});
		
		// Handle edit cards list element action
		((JButton)cards_components.get("editButton")).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				int order = ((JList<String>)cards_components.get("cardsList")).getSelectedIndex(); 
				
				if(order < 0) 
					return;
				
				Card myCard = cards.get(order); 
				
				String nNumber = ((JTextField)cards_components.get("cardNumField")).getText();
				String nBalance = ((JTextField)cards_components.get("balanceField")).getText();
				String nExpDate = ((JTextField)cards_components.get("dateField")).getText();
				
				for(int i = 0; i < nNumber.length(); i++) {
					if(!Character.isDigit(nNumber.charAt(i))) {
						JOptionPane.showMessageDialog(display,
							    "Wprowadzono b³êdny numer",
							    "B³¹d",
							    JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				
				if(!nNumber.equals(Integer.toString(cards.get(order).getNumber())))
					for(Card card : cards) 
						if(card.getNumber() == Integer.parseInt(nNumber)) {
							JOptionPane.showMessageDialog(display,
								    "Istnieje ju¿ karta o takim numerze.",
								    "B³¹d",
								    JOptionPane.ERROR_MESSAGE);
							return;
						}
				
				for(int i = 0; i < nExpDate.length(); i++) {
					if(!Character.isDigit(nExpDate.charAt(i)) && nExpDate.charAt(i) != '-') {
						JOptionPane.showMessageDialog(display,
							    "Wprowadzono z³y format daty.",
							    "B³¹d",
							    JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				
				String [] date = nExpDate.split("-"); 
				if(date[0].length() != 4 || (date[1].length() != 2 || Integer.parseInt(date[1]) > 12)) {
					JOptionPane.showMessageDialog(display,
						    "Wprowadzono z³y format daty.",
						    "B³¹d",
						    JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				String type = (String)((JComboBox<String>)cards_components.get("typeComboBox")).getSelectedItem();
				int owner = ((JComboBox<Client>)cards_components.get("ownerComboBox")).getSelectedIndex();
								
				cards.get(order).setNumber(Integer.parseInt(nNumber));
				cards.get(order).setBalance(Double.parseDouble(nBalance));
				cards.get(order).setExp_date(nExpDate); 
				myCard = cards.get(order);
				
				for(Client client : clients) {
					Iterator<Card> iter = client.getCards().iterator();
					while(iter.hasNext()) {
						Card card = iter.next();
						if(card.getNumber() == myCard.getNumber())
							iter.remove();
					}
				}
				
				if((type.equals("Bankomatowa") && myCard instanceof BankCard) ||
					(type.equals("Kredytowa") && myCard instanceof CreditCard) ||
					(type.equals("Debetowa") && myCard instanceof DebitCard)) {
					clients.get(owner).addCard(myCard);
				} else {
					if(type.equals("Bankomatowa")) {
						BankCard bankCard = null;
						try {
							bankCard = new BankCard(myCard.getNumber(), myCard.getExp_date());
						} catch (DataCardException e) {
							e.printStackTrace();
						}
						
						clients.get(owner).addCard(bankCard);
					} else if(type.equals("Debetowa")) {
						DebitCard debitCard = null;
						try {
							debitCard = new DebitCard(myCard.getNumber(), myCard.getExp_date());
						} catch (DataCardException e) {
							e.printStackTrace();
						}
						
						clients.get(owner).addCard(debitCard);
					} else if(type.equals("Kredytowa")) {
						CreditCard creditCard = null;
						try {
							creditCard = new CreditCard(myCard.getNumber(), myCard.getExp_date(), 1000);
						} catch (DataCardException e) {
							e.printStackTrace();
						}
						
						clients.get(owner).addCard(creditCard);
					}
				}
				
				((JList<String>)cards_components.get("cardsList")).setModel(new DefaultListModel<String>() {
					private static final long serialVersionUID = 1L;
					ArrayList<Card> values = cards; 
					
					public int getSize() {
						return values.size();
					}

					public String getElementAt(int index) {
						return values.get(index).getNumber() + "";
					}
				});
				
				try {
					database.SClient(clients);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		// Handle switch cards list element action 
		((JList<String>)cards_components.get("cardsList")).addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent ev) {
				((JComboBox<Client>)cards_components.get("ownerComboBox")).removeAllItems();
				for(Client client : clients) {
					((JComboBox<Client>)cards_components.get("ownerComboBox")).addItem(client); 
				}
				
				int index = ((JList<String>) ev.getSource()).getSelectedIndex(); 
				
				if(index < 0)
					return;
				
				((JTextField)cards_components.get("cardNumField")).setText(cards.get(index).getNumber()+"");
				((JTextField)cards_components.get("balanceField")).setText(cards.get(index).getBalance()+"");
				((JTextField)cards_components.get("dateField")).setText(cards.get(index).getExp_date()+"");
				
				for(Client client : clients) {
					if(cards == null) {
						((JComboBox<Client>)cards_components.get("ownerComboBox")).setSelectedIndex(0);
						continue;
					}
						 
					for(Card card : client.getCards())
						if(card.getNumber() == cards.get(index).getNumber())
							((JComboBox<Client>)cards_components.get("ownerComboBox")).setSelectedItem(client);
				}
				
				if(cards.get(index) instanceof CreditCard) {
					((JComboBox<String>)cards_components.get("typeComboBox")).setSelectedIndex(0);
				}
				else if (cards.get(index) instanceof BankCard) {
					((JComboBox<String>)cards_components.get("typeComboBox")).setSelectedIndex(1);
				} else {
					((JComboBox<String>)cards_components.get("typeComboBox")).setSelectedIndex(2);
				}
			}
		});
	}
}
