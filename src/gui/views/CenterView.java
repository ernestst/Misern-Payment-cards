package gui.views;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
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

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modules.bank.Bank;
import modules.center.Transaction;
import modules.center.User;
import modules.company.Company;
import modules.company.ServiceCompany;
import modules.company.ShopCompany;
import modules.company.TransportCompany;
import system.Database;
import system.exceptions.DataCardException;

/**
 * Auth center view which contains center's admin panel
 * 
 * @author Sebastian Smoliñski
 * @since 0.1
 * @version 0.5
 * @see View
 * @category GUI
 */
public class CenterView extends View {

	// Variables pool 
	private HashMap<String, JComponent> companies_components, users_components, banks_components, archive_components; 
	private JTabbedPane tabbedPane;
	private Database database = new Database(); 
	
	/**
	 * Basic constructor, set components
	 * 
	 * @param display - Container from actual frame
	 */
	public CenterView(Container display) {
		super(display);
		
		this.view.setLayout(new BoxLayout(view, BoxLayout.PAGE_AXIS));
	}

	/**
	 * @see View
	 */
	@Override
	public String getName() {
		return "Center";
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
	 * Initialize components - set look and feel of objects and set positions
	 */
	public void initComponents() { 
		// Add components to HashMaps 
		tabbedPane = new JTabbedPane();
		companies_components = new HashMap<String, JComponent>(); 
		companies_components.put("leftPanel", new JPanel()); 
		companies_components.put("rightPanel", new JPanel()); 
		companies_components.put("splitPane", new JSplitPane(JSplitPane.HORIZONTAL_SPLIT)); 
		companies_components.put("nameField", new JTextField());
		companies_components.put("typeComboBox", new JComboBox<String>());
		companies_components.put("editButton", new JButton("Edytuj"));
		companies_components.put("addButton", new JButton("Dodaj"));
		companies_components.put("deleteButton", new JButton("Usuñ"));
		companies_components.put("companiesList", new JList<String>());
		companies_components.put("scrollPane",  new JScrollPane());
		companies_components.put("companiesPanel", new JPanel());
		
		users_components = new HashMap<String, JComponent>(); 
		users_components.put("leftPanel", new JPanel());
		users_components.put("rightPanel", new JPanel());
		users_components.put("usersPanel", new JPanel());
		users_components.put("splitPane", new JSplitPane(JSplitPane.HORIZONTAL_SPLIT));
		users_components.put("scrollPane", new JScrollPane());
		users_components.put("loginField", new JTextField());
		users_components.put("passField", new JPasswordField());
		users_components.put("orgNameField", new JTextField()); 
		users_components.put("roleComboBox", new JComboBox<String>());
		users_components.put("addButton", new JButton("Dodaj"));
		users_components.put("editButton", new JButton("Edytuj")); 
		users_components.put("deleteButton", new JButton("Usuñ"));
		users_components.put("usersList", new JList<String>());
		
		banks_components = new HashMap<String, JComponent>(); 
		banks_components.put("leftPanel", new JPanel());
		banks_components.put("rightPanel", new JPanel());
		banks_components.put("banksPanel", new JPanel());
		banks_components.put("splitPane", new JSplitPane(JSplitPane.HORIZONTAL_SPLIT));
		banks_components.put("scrollPane", new JScrollPane());
		banks_components.put("nameField", new JTextField());
		banks_components.put("addButton", new JButton("Dodaj"));
		banks_components.put("editButton", new JButton("Edytuj")); 
		banks_components.put("deleteButton", new JButton("Usuñ"));
		banks_components.put("banksList", new JList<String>());
		
		archive_components = new HashMap<String, JComponent>(); 
		archive_components.put("bottomPanel", new JPanel()); 
		archive_components.put("topPanel", new JPanel());
		archive_components.put("archivePanel", new JPanel());
		archive_components.put("splitPane", new JSplitPane(JSplitPane.VERTICAL_SPLIT)); 
		archive_components.put("scrollPane", new JScrollPane());
		archive_components.put("transList", new JList<String>());
		archive_components.put("detailsArea", new JTextArea());
		archive_components.put("companyField", new JTextField("Firma"));
		archive_components.put("bankField", new JTextField("Bank"));
		archive_components.put("cardnumField", new JTextField("Numer karty"));
		archive_components.put("ownerField", new JTextField("Imiê i nazwisko w³aœciciela"));
		archive_components.put("cashField", new JTextField("Kwota transakcji"));
		archive_components.put("firstCondCB", new JComboBox<String>());
		archive_components.put("secondCondCB", new JComboBox<String>());
		archive_components.put("thirdCondCB", new JComboBox<String>());
		archive_components.put("forthCondCB", new JComboBox<String>());
		archive_components.put("sendButton", new JButton("Wyszukaj"));
	
		// Companies Panel design create
		initCompaniesPanel();
					
		// Users Panel design create
		initUsersPanel();
		
		// Banks Panel design create
		initBanksPanel(); 
		
		// Archive Panel design create
		initArchivePanel();
		
		view.add(tabbedPane);
	}
	
	/**
	 * Initialize and set archive design
	 */
	private void initArchivePanel() {
		
		archive_components.get("archivePanel").setLayout(new BorderLayout(0,0));
		archive_components.get("archivePanel").setBorder(new EmptyBorder(5, 5, 5, 5));
		
		((JSplitPane)archive_components.get("splitPane")).setEnabled(false);
		((JSplitPane)archive_components.get("splitPane")).setRightComponent(archive_components.get("bottomPanel"));
		((JSplitPane)archive_components.get("splitPane")).setLeftComponent(archive_components.get("topPanel"));
		
		archive_components.get("bottomPanel").setLayout(new BoxLayout(archive_components.get("bottomPanel"), BoxLayout.X_AXIS));
		archive_components.get("topPanel").setLayout(new BoxLayout(archive_components.get("topPanel"), BoxLayout.X_AXIS));
		
		((JPanel)archive_components.get("archivePanel")).add(((JSplitPane)archive_components.get("splitPane")), BorderLayout.CENTER);
		
		((JTextArea)archive_components.get("detailsArea")).setFont(new Font("Monospaced", Font.PLAIN, 12));
		((JTextArea)archive_components.get("detailsArea")).setEditable(false);
		((JTextArea)archive_components.get("detailsArea")).setText("Transakcja z dnia YYYY-MM-DD\r\nFirma: XYZ\r\nKwota: 0.00 PLN\r\nNumer karty: 00 000\r\nBank: BG\u017B");
		
		((JList<String>)archive_components.get("transList")).setModel(new DefaultListModel<String>() {
			private static final long serialVersionUID = 1L;
			//String[] values = new String[] { "10.02.2018", "10.02.2018", "11.02.2018" };
			ArrayList<Transaction> values = center.getArchiveTransactions(); 
			
			public int getSize() {
				return values.size();
			}

			public String getElementAt(int index) {
				return values.get(index).toString();
			}
		});
		
		((JPanel)archive_components.get("bottomPanel")).add(archive_components.get("scrollPane"));
		((JList<String>)archive_components.get("transList")).setFixedCellWidth(200);
		((JScrollPane)archive_components.get("scrollPane")).setRowHeaderView(archive_components.get("transList"));
		((JPanel)archive_components.get("bottomPanel")).add(archive_components.get("detailsArea")); 
		
		((JTextField)archive_components.get("companyField")).setColumns(10); 
		((JPanel)archive_components.get("topPanel")).add(((JTextField)archive_components.get("companyField")));

		((JComboBox<String>)archive_components.get("firstCondCB")).setModel(new DefaultComboBoxModel<String>(new String[] {"ORAZ", "LUB"}));
		archive_components.get("topPanel").add(archive_components.get("firstCondCB"));
		
		((JTextField)archive_components.get("bankField")).setColumns(10); 
		archive_components.get("topPanel").add(archive_components.get("bankField"));

		((JComboBox<String>)archive_components.get("secondCondCB")).setModel(new DefaultComboBoxModel<String>(new String[] {"ORAZ", "LUB"}));
		archive_components.get("topPanel").add(archive_components.get("secondCondCB"));
		
		((JTextField)archive_components.get("cardnumField")).setColumns(10); 
		archive_components.get("topPanel").add(archive_components.get("cardnumField"));

		((JComboBox<String>)archive_components.get("thirdCondCB")).setModel(new DefaultComboBoxModel<String>(new String[] {"ORAZ", "LUB"}));
		archive_components.get("topPanel").add(archive_components.get("thirdCondCB"));
		
		((JTextField)archive_components.get("ownerField")).setColumns(10); 
		archive_components.get("topPanel").add(archive_components.get("ownerField"));

		((JComboBox<String>)archive_components.get("forthCondCB")).setModel(new DefaultComboBoxModel<String>(new String[] {"ORAZ", "LUB"}));
		archive_components.get("topPanel").add(archive_components.get("forthCondCB"));
		
		((JTextField)archive_components.get("cashField")).setColumns(10); 
		archive_components.get("topPanel").add(archive_components.get("cashField"));
		
		archive_components.get("topPanel").add(archive_components.get("sendButton"));

		tabbedPane.addTab("Archiwum", null, ((JPanel)archive_components.get("archivePanel")), "Przegl¹daj archiwum transakcji");
	}

	/**
	 * Initialize and set banks design 
	 */
	private void initBanksPanel() {
		banks_components.get("editButton").setPreferredSize(new Dimension(100, 30));
		banks_components.get("addButton").setPreferredSize(new Dimension(100, 30));
		banks_components.get("deleteButton").setPreferredSize(new Dimension(100, 30));
		
		banks_components.get("banksPanel").setLayout(new BorderLayout(0, 0));
		((JSplitPane)banks_components.get("splitPane")).setLeftComponent(banks_components.get("leftPanel"));
		((JSplitPane)banks_components.get("splitPane")).setRightComponent(banks_components.get("rightPanel"));
		
		((JSplitPane)banks_components.get("splitPane")).setEnabled(false);
		((JPanel)banks_components.get("leftPanel")).setLayout(new BorderLayout(0, 0));

		((JPanel)banks_components.get("banksPanel")).add(((JSplitPane)banks_components.get("splitPane")));
		
		((JList<String>)banks_components.get("banksList")).setModel(new DefaultListModel<String>() {
			private static final long serialVersionUID = 1L;
			ArrayList<Bank> values = center.getBanks();

			public int getSize() {
				return values.size();
			}

			public String getElementAt(int index) {
				return values.get(index).getName();
			}
		});
		
		((JList<String>)banks_components.get("banksList")).setFixedCellHeight(20);
		((JList<String>)banks_components.get("banksList")).setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		((JList<String>)banks_components.get("banksList")).setPreferredSize(new Dimension(100, 0));
		((JList<String>)banks_components.get("banksList")).setSelectedIndex(0);

		((JScrollPane)banks_components.get("scrollPane")).setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		((JScrollPane)banks_components.get("scrollPane")).setViewportView(((JList<String>)banks_components.get("banksList")));

		((JPanel)banks_components.get("leftPanel")).add(((JScrollPane)banks_components.get("scrollPane")));
		
		((JPanel)banks_components.get("rightPanel")).setLayout(new GridBagLayout());
		((JPanel)banks_components.get("rightPanel")).setMaximumSize(((JPanel)banks_components.get("rightPanel")).getPreferredSize()); 
		
		GridBagConstraints cntr = new GridBagConstraints();
		
		((JTextField)banks_components.get("nameField")).setText(((JList<String>)banks_components.get("banksList")).getModel().getElementAt(0));
		((JTextField)banks_components.get("nameField")).setColumns(10); 
		((JTextField)banks_components.get("nameField")).setToolTipText("Nazwa banku");
		((JTextField)banks_components.get("nameField")).setPreferredSize(new Dimension(120, 30));
		
		cntr.gridx = 0;
		cntr.gridy = 0;
		cntr.insets = new Insets(0, 0, 25, 0);
		cntr.anchor = GridBagConstraints.CENTER;
		
		((JPanel)banks_components.get("rightPanel")).add(((JTextField)banks_components.get("nameField")), cntr);
		
		cntr.insets = new Insets(0, 0, 5, 0);
		cntr.gridy = 1;
		((JPanel)banks_components.get("rightPanel")).add(((JButton)banks_components.get("editButton")), cntr);
		
		cntr.gridy = 2;
		((JPanel)banks_components.get("rightPanel")).add(((JButton)banks_components.get("deleteButton")), cntr);

		cntr.gridy = 3;
		((JPanel)banks_components.get("rightPanel")).add(((JButton)banks_components.get("addButton")), cntr);
		
		tabbedPane.addTab("Banki", null, ((JPanel)banks_components.get("banksPanel")), "Zarz¹dzaj spisem banków");
	}

	/**
	 * Initialize and set users design 
	 */
	private void initUsersPanel() {
		users_components.get("addButton").setPreferredSize(new Dimension(100, 30));
		users_components.get("editButton").setPreferredSize(new Dimension(100, 30));
		users_components.get("deleteButton").setPreferredSize(new Dimension(100, 30));

		users_components.get("usersPanel").setLayout(new BorderLayout(0, 0));
		((JSplitPane)users_components.get("splitPane")).setLeftComponent(users_components.get("leftPanel"));
		((JSplitPane)users_components.get("splitPane")).setRightComponent(users_components.get("rightPanel"));

		((JSplitPane)users_components.get("splitPane")).setEnabled(false);
		((JPanel)users_components.get("leftPanel")).setLayout(new BorderLayout(0, 0));

		((JPanel)users_components.get("usersPanel")).add(((JSplitPane)users_components.get("splitPane")));

		((JList<String>)users_components.get("usersList")).setModel(new DefaultListModel<String>() {
			private static final long serialVersionUID = 1L;
			ArrayList<User> values = center.getUsers();
			
			public int getSize() {
				return values.size();
			}
			
			public String getElementAt(int index) {
				return values.get(index).getLogin(); 
			}
		});

		((JList<String>)users_components.get("usersList")).setFixedCellHeight(20);
		((JList<String>)users_components.get("usersList")).setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		((JList<String>)users_components.get("usersList")).setPreferredSize(new Dimension(100, 0));
		((JList<String>)users_components.get("usersList")).setSelectedIndex(0);

		((JScrollPane)users_components.get("scrollPane")).setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		((JScrollPane)users_components.get("scrollPane")).setViewportView(((JList<String>)users_components.get("usersList")));

		((JPanel)users_components.get("leftPanel")).add(((JScrollPane)users_components.get("scrollPane")));

		((JPanel)users_components.get("rightPanel")).setLayout(new GridBagLayout());
		((JPanel)users_components.get("rightPanel")).setMaximumSize(((JPanel)users_components.get("rightPanel")).getPreferredSize()); 

		GridBagConstraints cntr = new GridBagConstraints();

		((JTextField)users_components.get("loginField")).setText(((JList<String>)users_components.get("usersList")).getModel().getElementAt(0));
		((JTextField)users_components.get("loginField")).setColumns(10); 
		((JTextField)users_components.get("loginField")).setToolTipText("Login u¿ytkownika");
		((JTextField)users_components.get("loginField")).setPreferredSize(new Dimension(120, 30));
		
		((JTextField)users_components.get("orgNameField")).setText(center.getUsers().get(0).getOrgName());
		((JTextField)users_components.get("orgNameField")).setColumns(10); 
		((JTextField)users_components.get("orgNameField")).setToolTipText("Nazwa organizacji reprezentowanej przez u¿ytkownika");
		((JTextField)users_components.get("orgNameField")).setPreferredSize(new Dimension(120, 30));
		
		cntr.gridx = 0;
		cntr.gridy = 0;
		cntr.insets = new Insets(0, 0, 5, 0);
		cntr.anchor = GridBagConstraints.CENTER;

		((JPanel)users_components.get("rightPanel")).add(((JTextField)users_components.get("loginField")), cntr); 
		
		cntr.gridx = 0;
		cntr.gridy = 1;
		cntr.insets = new Insets(0, 0, 5, 0);
		
		((JPanel)users_components.get("rightPanel")).add(((JTextField)users_components.get("orgNameField")), cntr); 
		
		char [] ch_password = null; 
		String rights = null; 
		for(int i = 0; i < center.getUsers().size(); i++)
			if(center.getUsers().get(i).getLogin() == ((JList<String>)users_components.get("usersList")).getModel().getElementAt(0)) {
				ch_password = center.getUsers().get(i).getPassword().toCharArray(); 
				rights = center.getUsers().get(i).getRights(); 
			}
		
		String s_password = new String(ch_password);
		((JPasswordField)users_components.get("passField")).setText(s_password);
		((JPasswordField)users_components.get("passField")).setColumns(10); 
		((JPasswordField)users_components.get("passField")).setToolTipText("Has³o u¿ytkownika");
		((JPasswordField)users_components.get("passField")).setPreferredSize(new Dimension(120, 30));
		
		cntr.gridx = 0;
		cntr.gridy = 2;
		cntr.anchor = GridBagConstraints.CENTER;

		((JPanel)users_components.get("rightPanel")).add(((JTextField)users_components.get("passField")), cntr); 

		((JComboBox<String>)users_components.get("roleComboBox")).setPreferredSize(new Dimension(110, 30));
		((JComboBox<String>)users_components.get("roleComboBox")).addItem("Centrum");
		((JComboBox<String>)users_components.get("roleComboBox")).addItem("Bank");
		((JComboBox<String>)users_components.get("roleComboBox")).addItem("Firma");
		((JComboBox<String>)users_components.get("roleComboBox")).setToolTipText("Uprawnienia u¿ytkownika");
		
		if(rights.equals("Centrum"))
			((JComboBox<String>)users_components.get("roleComboBox")).setSelectedIndex(0);
		else if(rights.equals("Bank"))
			((JComboBox<String>)users_components.get("roleComboBox")).setSelectedIndex(1);
		else 
			((JComboBox<String>)users_components.get("roleComboBox")).setSelectedIndex(2);
		
		cntr.gridy = 3;
		cntr.insets = new Insets(0, 0, 25, 0);
		((JPanel)users_components.get("rightPanel")).add(((JComboBox<String>)users_components.get("roleComboBox")), cntr);

		cntr.gridy = 4;
		cntr.insets = new Insets(0, 0, 5, 0);
		((JPanel)users_components.get("rightPanel")).add(((JButton)users_components.get("editButton")), cntr);
		
		cntr.gridy = 5; 
		((JPanel)users_components.get("rightPanel")).add(((JButton)users_components.get("deleteButton")), cntr);
		
		cntr.gridy = 6;
		((JPanel)users_components.get("rightPanel")).add(((JButton)users_components.get("addButton")), cntr);

		tabbedPane.addTab("U¿ytkownicy", null, ((JPanel)users_components.get("usersPanel")), "Zarz¹dzaj u¿ytkownikami aplikacji");
	}

	/**
	 * Initialize and set companies design 
	 */
	private void initCompaniesPanel() {
		companies_components.get("editButton").setPreferredSize(new Dimension(100, 30));
		companies_components.get("addButton").setPreferredSize(new Dimension(100, 30));
		companies_components.get("deleteButton").setPreferredSize(new Dimension(100, 30));
		
		companies_components.get("companiesPanel").setLayout(new BorderLayout(0, 0));
		((JSplitPane)companies_components.get("splitPane")).setLeftComponent(companies_components.get("leftPanel"));
		((JSplitPane)companies_components.get("splitPane")).setRightComponent(companies_components.get("rightPanel"));
		
		((JSplitPane)companies_components.get("splitPane")).setEnabled(false);
		((JPanel)companies_components.get("leftPanel")).setLayout(new BorderLayout(0, 0));

		((JPanel)companies_components.get("companiesPanel")).add(((JSplitPane)companies_components.get("splitPane")));
		
		((JList<String>)companies_components.get("companiesList")).setModel(new DefaultListModel<String>() {
			private static final long serialVersionUID = 1L;
			ArrayList<Company> values = center.getCompanies(); 
			
			public int getSize() {
				return values.size();
			}

			public String getElementAt(int index) {
				return values.get(index).getName();
			}
		});
		
		((JList<String>)companies_components.get("companiesList")).setFixedCellHeight(20);
		((JList<String>)companies_components.get("companiesList")).setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		((JList<String>)companies_components.get("companiesList")).setPreferredSize(new Dimension(100, 0));
		((JList<String>)companies_components.get("companiesList")).setSelectedIndex(0);
		
		((JScrollPane)companies_components.get("scrollPane")).setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		((JScrollPane)companies_components.get("scrollPane")).setViewportView(((JList<String>)companies_components.get("companiesList")));
		
		((JPanel)companies_components.get("leftPanel")).add(((JScrollPane)companies_components.get("scrollPane")));

		((JPanel)companies_components.get("rightPanel")).setLayout(new GridBagLayout());
		((JPanel)companies_components.get("rightPanel")).setMaximumSize(((JPanel)companies_components.get("rightPanel")).getPreferredSize()); 
		
		GridBagConstraints cntr = new GridBagConstraints();
		
		((JTextField)companies_components.get("nameField")).setText(((JList<String>)companies_components.get("companiesList")).getModel().getElementAt(0));
		((JTextField)companies_components.get("nameField")).setColumns(10); 
		((JTextField)companies_components.get("nameField")).setToolTipText("Nazwa firmy");
		((JTextField)companies_components.get("nameField")).setPreferredSize(new Dimension(120, 30));
		
		cntr.gridx = 0;
		cntr.gridy = 0;
		cntr.insets = new Insets(0, 0, 5, 0);
		cntr.anchor = GridBagConstraints.CENTER;
		
		((JPanel)companies_components.get("rightPanel")).add(((JTextField)companies_components.get("nameField")), cntr); 
		
		cntr.gridy = 1;
		((JComboBox<String>)companies_components.get("typeComboBox")).setPreferredSize(new Dimension(110, 30));
		((JComboBox<String>)companies_components.get("typeComboBox")).addItem("Sklep");
		((JComboBox<String>)companies_components.get("typeComboBox")).addItem("Transport");
		((JComboBox<String>)companies_components.get("typeComboBox")).addItem("Us³ugi");
		((JComboBox<String>)companies_components.get("typeComboBox")).addItem("Bankomat");
		((JComboBox<String>)companies_components.get("typeComboBox")).setToolTipText("Rodzaj firmy");
		
		Company first = null; 
		for(Company company : center.getCompanies()) {
			if(company.getName().equals(((JList<String>)companies_components.get("companiesList")).getModel().getElementAt(0)))
				first = company;
		}
		
		if(first instanceof ServiceCompany)
			((JComboBox<String>)companies_components.get("typeComboBox")).setSelectedItem("Us³ugi");
		else if(first instanceof ShopCompany)
			((JComboBox<String>)companies_components.get("typeComboBox")).setSelectedItem("Sklep");
		else if(first instanceof TransportCompany)
			((JComboBox<String>)companies_components.get("typeComboBox")).setSelectedItem("Transport");
		else 
			((JComboBox<String>)companies_components.get("typeComboBox")).setSelectedItem("Bankomat");
		
		cntr.gridy = 2;
		cntr.insets = new Insets(0, 0, 25, 0);
		((JPanel)companies_components.get("rightPanel")).add(((JComboBox<String>)companies_components.get("typeComboBox")), cntr);

		cntr.gridy = 3;
		cntr.insets = new Insets(0, 0, 5, 0);
		((JPanel)companies_components.get("rightPanel")).add(((JButton)companies_components.get("editButton")), cntr);

		cntr.gridy = 4;
		((JPanel)companies_components.get("rightPanel")).add(((JButton)companies_components.get("deleteButton")), cntr);

		cntr.gridy = 5;
		((JPanel)companies_components.get("rightPanel")).add(((JButton)companies_components.get("addButton")), cntr);
		
		tabbedPane.addTab("Firmy", null, ((JPanel)companies_components.get("companiesPanel")), "Zarz¹dzaj spisem firm");
	}
	
	/**
	 * Initialize action listeners for objects
	 */
	public void initActions() {
		// Handle add new companies list element action 
		((JButton)companies_components.get("addButton")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				center.addCompany("Nowy"+center.getCompanies().size(), "Us³ugi");
				 
				((JList<String>)companies_components.get("companiesList")).setModel(new DefaultListModel<String>() {
					private static final long serialVersionUID = 1L;
					ArrayList<Company> values = center.getCompanies(); 
					
					public int getSize() {
						return values.size();
					}

					public String getElementAt(int index) {
						return values.get(index).getName();
					}
				});
				
				if (((JList<String>)companies_components.get("companiesList")).getSize().height <= center.getCompanies().size() * 20)
					((JList<String>)companies_components.get("companiesList")).setPreferredSize(new Dimension(100, ((JList<String>)companies_components.get("companiesList")).getSize().height + 20));

			}
		}); 
		
		// Handle remove company element from list action
		((JButton)companies_components.get("deleteButton")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				String name = ((JList<String>)companies_components.get("companiesList")).getSelectedValue();
				
				if (name == null)
					return;
				
				center.deleteCompany(name);
				((JList<String>)companies_components.get("companiesList")).setModel(new DefaultListModel<String>() {
					private static final long serialVersionUID = 1L;
					ArrayList<Company> values = center.getCompanies(); 
					
					public int getSize() {
						return values.size();
					}

					public String getElementAt(int index) {
						return values.get(index).getName();
					}
				});
				
				if (((JList<String>)companies_components.get("companiesList")).getSize().height >= (center.getCompanies().size() - 1) * 20)
					((JList<String>)companies_components.get("companiesList")).setPreferredSize(new Dimension(100, ((JList<String>)companies_components.get("companiesList")).getSize().height - 20));
			}
		});
	
		// Handle edit companies list element action
		((JButton)companies_components.get("editButton")).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				int order = ((JList<String>)companies_components.get("companiesList")).getSelectedIndex(); 
				String nName = ((JTextField)companies_components.get("nameField")).getText();
				String nType = (String)((JComboBox<String>)companies_components.get("typeComboBox")).getSelectedItem(); 
				
				if(!nName.equals(center.getCompanies().get(order).getName()))
					for(Company comp : center.getCompanies()) 
						if(comp.getName().equals(nName)) {
							JOptionPane.showMessageDialog(display,
								    "Login jest ju¿ zajêty",
								    "B³¹d",
								    JOptionPane.ERROR_MESSAGE);
							return;
						}
				
				center.editCompany(nName, nType, order);
				
				((JList<String>)companies_components.get("companiesList")).setModel(new DefaultListModel<String>() {
					private static final long serialVersionUID = 1L;
					ArrayList<Company> values = center.getCompanies(); 
					
					public int getSize() {
						return values.size();
					}

					public String getElementAt(int index) {
						return values.get(index).getName();
					}
				});
			}
		});
	
		// Handle switch companies list element action 
		((JList<String>)companies_components.get("companiesList")).addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent ev) {
				((JTextField)companies_components.get("nameField")).setText(((JList<String>) ev.getSource()).getSelectedValue());
				
				for(Company company : center.getCompanies()) {
					if(company.getName().equals(((JList<String>) ev.getSource()).getSelectedValue())) 
						if(company instanceof ServiceCompany)
							((JComboBox<String>)companies_components.get("typeComboBox")).setSelectedItem("Us³ugi");
						else if (company instanceof TransportCompany) 
							((JComboBox<String>)companies_components.get("typeComboBox")).setSelectedItem("Transport");
						else if (company instanceof ShopCompany)
							((JComboBox<String>)companies_components.get("typeComboBox")).setSelectedItem("Sklep");
						else 
							((JComboBox<String>)companies_components.get("typeComboBox")).setSelectedItem("Bankomat");
				}
			}
		});
	
		// Handle add new users list element action
		((JButton)users_components.get("addButton")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				center.addUser("Nowy"+center.getUsers().size(), "pass", "Centrum", "None");
				
				((JList<String>)users_components.get("usersList")).setModel(new DefaultListModel<String>() {
					private static final long serialVersionUID = 1L;
					ArrayList<User> values = center.getUsers(); 
					
					public int getSize() {
						return values.size();
					}

					public String getElementAt(int index) {
						return values.get(index).getLogin();
					}
				});
				
				if (((JList<String>)users_components.get("usersList")).getSize().height <= center.getUsers().size() * 20)
					((JList<String>)users_components.get("usersList")).setPreferredSize(new Dimension(100, ((JList<String>)users_components.get("usersList")).getSize().height + 20));

			}
		});
	
		// Handle remove user element from list action
		((JButton)users_components.get("deleteButton")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				String login = ((JList<String>)users_components.get("usersList")).getSelectedValue();
				
				if (login == null)
					return;
				
				center.deleteUser(login);
				((JList<String>)users_components.get("usersList")).setModel(new DefaultListModel<String>() {
					private static final long serialVersionUID = 1L;
					ArrayList<User> values = center.getUsers(); 
					
					public int getSize() {
						return values.size();
					}

					public String getElementAt(int index) {
						return values.get(index).getLogin();
					}
				});
				
				if (((JList<String>)users_components.get("usersList")).getSize().height >= (center.getCompanies().size() - 1) * 20)
					((JList<String>)users_components.get("usersList")).setPreferredSize(new Dimension(100, ((JList<String>)users_components.get("usersList")).getSize().height - 20));
			}
		});
	
		// Handle edit users list element action
		((JButton)users_components.get("editButton")).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				int order = ((JList<String>)users_components.get("usersList")).getSelectedIndex(); 
				String nLogin = ((JTextField)users_components.get("loginField")).getText();
				String nPassword = new String(((JPasswordField)users_components.get("passField")).getPassword());
				String nType = (String)((JComboBox<String>)users_components.get("roleComboBox")).getSelectedItem(); 
				String nOrgName = ((JTextField)users_components.get("orgNameField")).getText();
				
				if(!nLogin.equals(center.getUsers().get(order).getLogin()))
					for(User user : center.getUsers()) 
						if(user.getLogin().equals(nLogin)) {
							JOptionPane.showMessageDialog(display,
								    "Login jest ju¿ zajêty",
								    "B³¹d",
								    JOptionPane.ERROR_MESSAGE);
							return;
						}
				
				center.editUser(nLogin, nPassword, nType, nOrgName, order);
				
				((JList<String>)users_components.get("usersList")).setModel(new DefaultListModel<String>() {
					private static final long serialVersionUID = 1L;
					ArrayList<User> values = center.getUsers(); 
					
					public int getSize() {
						return values.size();
					}

					public String getElementAt(int index) {
						return values.get(index).getLogin();
					}
				});
			}
		});
	
		// Handle switch users list element action 
		((JList<String>)users_components.get("usersList")).addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent ev) {
				((JTextField)users_components.get("loginField")).setText(((JList<String>) ev.getSource()).getSelectedValue());
				
				for(User user : center.getUsers()) {
					if(user.getLogin().equals(((JList<String>) ev.getSource()).getSelectedValue()))  {
						((JTextField)users_components.get("orgNameField")).setText(user.getOrgName());
						((JComboBox<String>)users_components.get("roleComboBox")).setSelectedItem(user.getRights());
						((JPasswordField)users_components.get("passField")).setText(user.getPassword());
					}	
				}
			}
		});
		
		// Handle add new banks list element action 
		((JButton)banks_components.get("addButton")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				center.addBank("Nowy"+center.getBanks().size());
				
				((JList<String>)banks_components.get("banksList")).setModel(new DefaultListModel<String>() {
					private static final long serialVersionUID = 1L;
					ArrayList<Bank> values = center.getBanks(); 
					
					public int getSize() {
						return values.size();
					}

					public String getElementAt(int index) {
						return values.get(index).getName();
					}
				});
				
				if (((JList<String>)banks_components.get("banksList")).getSize().height <= center.getBanks().size() * 20)
					((JList<String>)banks_components.get("banksList")).setPreferredSize(new Dimension(100, ((JList<String>)banks_components.get("banksList")).getSize().height + 20));

			}
		}); 
		
		// Handle remove banks element from list action
		((JButton)banks_components.get("deleteButton")).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				String name = ((JList<String>)banks_components.get("banksList")).getSelectedValue();
				
				if (name == null)
					return;
				
				center.deleteBank(name);
				((JList<String>)banks_components.get("banksList")).setModel(new DefaultListModel<String>() {
					private static final long serialVersionUID = 1L;
					ArrayList<Bank> values = center.getBanks(); 
					
					public int getSize() {
						return values.size();
					}

					public String getElementAt(int index) {
						return values.get(index).getName();
					}
				});
				
				if (((JList<String>)banks_components.get("banksList")).getSize().height >= (center.getBanks().size() - 1) * 20)
					((JList<String>)banks_components.get("banksList")).setPreferredSize(new Dimension(100, ((JList<String>)banks_components.get("banksList")).getSize().height - 20));
			}
		});
	
		// Handle edit banks list element action
		((JButton)banks_components.get("editButton")).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				int order = ((JList<String>)banks_components.get("banksList")).getSelectedIndex(); 
				String nName = ((JTextField)banks_components.get("nameField")).getText();
				
				if(!nName.equals(center.getBanks().get(order).getName()))
					for(Bank bank : center.getBanks()) 
						if(nName.equals(bank.getName())) {
							JOptionPane.showMessageDialog(display,
								    "Istnieje ju¿ taki bank.",
								    "B³¹d",
								    JOptionPane.ERROR_MESSAGE);
							return;
						}
				
				try {
					center.editBank(nName, order);
				} catch (DataCardException e) {
					e.printStackTrace();
				}
				
				((JList<String>)banks_components.get("banksList")).setModel(new DefaultListModel<String>() {
					private static final long serialVersionUID = 1L;
					ArrayList<Bank> values = center.getBanks(); 
					
					public int getSize() {
						return values.size();
					}

					public String getElementAt(int index) {
						return values.get(index).getName();
					}
				});
			}
		});
	
		// Handle switch bank list element action 
		((JList<String>)banks_components.get("banksList")).addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent ev) {
				((JTextField)banks_components.get("nameField")).setText(((JList<String>) ev.getSource()).getSelectedValue());
			}
		});
		
		// Handle switch transaction list element action 
		((JList<String>)archive_components.get("transList")).addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent ev) {
				int index = ((JList<String>)archive_components.get("transList")).getSelectedIndex();
				
				if(index < 0)
					return;
				
				((JTextArea)archive_components.get("detailsArea")).setText("Transakcja z dnia "+ 
				center.getArchive_transactions().get(index).getDate() + 
				"\r\nFirma: " + center.getArchive_transactions().get(index).getComp().getName() + "\r\nKwota: " + 
				center.getArchive_transactions().get(index).getAmount() + " PLN\r\nNumer karty: " +
				center.getArchive_transactions().get(index).getCard().getNumber() + "\r\nBank: " + 
				center.getArchive_transactions().get(index).getBank().getName());
			}
		});
		
		// Handle search for transaction action
		((JButton)archive_components.get("sendButton")).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				String company = ((JTextField)archive_components.get("companyField")).getText();
				String bank = ((JTextField)archive_components.get("bankField")).getText();
				String amount = ((JTextField)archive_components.get("cashField")).getText();
				String cardnumber = ((JTextField)archive_components.get("cardnumField")).getText();
				String owner = ((JTextField)archive_components.get("ownerField")).getText();
				
				String first = (String)((JComboBox<String>)archive_components.get("firstCondCB")).getSelectedItem();
				String second = (String)((JComboBox<String>)archive_components.get("secondCondCB")).getSelectedItem();
				String third = (String)((JComboBox<String>)archive_components.get("thirdCondCB")).getSelectedItem();
				String forth = (String)((JComboBox<String>)archive_components.get("forthCondCB")).getSelectedItem();
				
				String query = company + ";" + first + ";" + bank + ";" + second + ";" + amount + ";" + third + ";" + cardnumber + ";" + forth + ";" + owner; 
				center.setArchive_transactions(center.getArchivedTransaction(query));
				
				((JList<String>)archive_components.get("transList")).setModel(new DefaultListModel<String>() {
					private static final long serialVersionUID = 1L;
					ArrayList<Transaction> values = center.getArchivedTransaction(query);
					
					public int getSize() {
						return values.size();
					}

					public String getElementAt(int index) {
						return values.get(index).toString();
					}
				});
			}
		});
	}
}