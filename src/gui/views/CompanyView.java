package gui.views;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import modules.Client;
import modules.bank.Bank;
import modules.bank.Card;
import modules.center.Transaction;
import system.Database;

/**
 * Company view which contains new transaction panel
 * 
 * @author Sebastian Smoliñski
 * @since 0.1
 * @version 0.1
 * @see View
 * @category GUI
 */
public class CompanyView extends View {
	
	// Variables pool
	private HashMap<String, JComponent> components; 
	private Database database = new Database(); 
	private ArrayList<Transaction> transactions; 
	
	/**
	 * Basic constructor, set components
	 * 
	 * @param display - Container from actual frame
	 */
	public CompanyView(Container display) {
		super(display);

		view.setLayout(new BoxLayout(view, BoxLayout.PAGE_AXIS));
		components = new HashMap<String, JComponent>(); 
		transactions = new ArrayList<Transaction>(); 
	}

	/**
	 * @see View
	 */
	@Override
	public String getName() {
		return "Company";
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
					database.STransaction(transactions);
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
		components.put("transLabel", new JLabel("Panel Transakcji"));
		components.put("amountField", new JTextField("0.00"));
		components.put("cardNumberField", new JTextField("000"));
		components.put("expDateField", new JTextField("2018-08-08"));
		components.put("transDateField", new JTextField("2018-08-08"));
		components.put("okButton", new JButton("Wykonaj"));
		components.put("clearButton", new JButton("Wyczyœæ"));
		
		((JTextField)components.get("amountField")).setText("0");
		((JTextField)components.get("cardNumberField")).setText("0");
		((JTextField)components.get("expDateField")).setText("1970-01-01");
		((JTextField)components.get("transDateField")).setText("1970-01-01");
		
		((JTextField)components.get("amountField")).setToolTipText("Kwota transakcji");
		((JTextField)components.get("cardNumberField")).setToolTipText("Numer karty");
		((JTextField)components.get("expDateField")).setToolTipText("Data wygaœniêcia karty");
		((JTextField)components.get("transDateField")).setToolTipText("Data transakcji");
		
		((JLabel)components.get("transLabel")).setAlignmentX(Component.CENTER_ALIGNMENT);
		components.get("transLabel").setFont(components.get("transLabel").getFont().deriveFont(18.0f));
		
		view.add(Box.createRigidArea(new Dimension(10, 30)));
		view.add((JLabel)components.get("transLabel"));

		((JTextField)components.get("amountField")).setAlignmentX(Component.CENTER_ALIGNMENT);
		components.get("amountField").setMaximumSize(new Dimension(200, 30));
		
		view.add(Box.createRigidArea(new Dimension(10, 30)));
		view.add((JTextField)components.get("amountField"));
		
		((JTextField)components.get("cardNumberField")).setAlignmentX(Component.CENTER_ALIGNMENT);
		components.get("cardNumberField").setMaximumSize(new Dimension(200, 30));
		
		view.add(Box.createRigidArea(new Dimension(0, 10)));
		view.add((JTextField)components.get("cardNumberField"));
		
		((JTextField)components.get("expDateField")).setAlignmentX(Component.CENTER_ALIGNMENT);
		components.get("expDateField").setMaximumSize(new Dimension(200, 30));
		
		view.add(Box.createRigidArea(new Dimension(0, 10)));
		view.add((JTextField)components.get("expDateField"));
		
		((JTextField)components.get("transDateField")).setAlignmentX(Component.CENTER_ALIGNMENT);
		components.get("transDateField").setMaximumSize(new Dimension(200, 30));
		
		view.add(Box.createRigidArea(new Dimension(0, 10)));
		view.add((JTextField)components.get("transDateField"));
		
		components.get("okButton").setPreferredSize(new Dimension(100, 30));
		components.get("clearButton").setPreferredSize(new Dimension(100, 30));
		
		((JButton)components.get("okButton")).setAlignmentX(Component.CENTER_ALIGNMENT);
		((JButton)components.get("clearButton")).setAlignmentX(Component.CENTER_ALIGNMENT);
		view.add(Box.createRigidArea(new Dimension(0, 15)));
		view.add((JButton)components.get("okButton"));
		view.add(Box.createRigidArea(new Dimension(0, 5)));
		view.add((JButton)components.get("clearButton"));
	}
	
	/**
	 * Initialize action listeners for objects
	 */
	public void initActions() {
		((JButton)components.get("okButton")).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				double amount = Double.parseDouble(((JTextField)components.get("amountField")).getText());
				String date = ((JTextField)components.get("transDateField")).getText();
				int cardNumber = Integer.parseInt(((JTextField)components.get("cardNumberField")).getText());
				
				Card usedCard = null; 
				Bank cardOwner = null; 
				
				for(Bank bank : center.getBanks())
					for(Client client : bank.getClients())
						if(client.hasCard(cardNumber))
							for(Card card : client.getCards()) {
								if(card.getNumber() == cardNumber) {
									usedCard = card;
									cardOwner = bank; 
								}
							}
				
				Transaction request = new Transaction(center.getLoggedCompany(), cardOwner, usedCard, amount, date);
				if(center.handleTransaction(request))
					transactions.add(request); 
				else 
					JOptionPane.showMessageDialog(display,
						    "Transakcja odrzucona.",
						    "B³¹d",
						    JOptionPane.ERROR_MESSAGE);
			}
		});
		
		((JButton)components.get("clearButton")).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				((JTextField)components.get("amountField")).setText("0");
				((JTextField)components.get("cardNumberField")).setText("0");
				((JTextField)components.get("expDateField")).setText("2018-08");
				((JTextField)components.get("transDateField")).setText("2018-08-08");
			}
		});
	}
}
