package gui.views;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import gui.Window;
import modules.bank.Bank;
import modules.center.User;
import modules.company.Company;

/** 
 * Class for the starting program view which
 * contains login to the system form 
 * @author Sebastian Smoliñski
 * @since 0.1 
 * @version 0.2 
 * @see View 
 * @category GUI 
 *
 */
public class LoginView extends View {

	private ArrayList<JComponent> components_list;
	
	/**
	 * Basic constructor, set components
	 * @param display - Container from actual frame 
	 */
	public LoginView(Container display) {
		super(display);
		
		view.setLayout(new BoxLayout(view, BoxLayout.PAGE_AXIS));
		components_list = new ArrayList<JComponent>(); 
		
		initComponents(); 
		initActions(); 
	}

	/**
	 * @see View
	 */
	@Override
	public String getName() {
		return "Login";
	}
	
	/**
	 * Initialize components - set look of objects
	 * and set positions 
	 */
	private void initComponents() {
		this.components_list.add(new JLabel("Zaloguj siê")); 
		this.components_list.add(new JTextField("Login"));
		this.components_list.add(new JPasswordField("Password"));
		this.components_list.add(new JButton("Wyœlij")); 
		
		for(Object element : this.components_list) {
			if(element instanceof JComponent) {
				((JComponent) element).setAlignmentX(Component.CENTER_ALIGNMENT);
			}
			
			if(element instanceof JButton) {
				((JButton) element).setMaximumSize(new Dimension(100, 30));
				view.add(Box.createRigidArea(new Dimension(0, 15)));
			} else if(element instanceof JTextComponent) {
				((JTextComponent) element).setMaximumSize(new Dimension(200, 30));
				view.add(Box.createRigidArea(new Dimension(0, 5)));
			}
			
			view.add((JComponent)element); 
		}
	}

	/**
	 * Initialize action listeners for objects
	 */
	private void initActions() {
		((JButton)components_list.get(3)).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				String login = ((JTextField)components_list.get(1)).getText(); 
				char [] password = ((JPasswordField)components_list.get(2)).getPassword();
				
				boolean flag = false;
				for(User user : center.getUsers()) {
					if(user.getLogin().equals(login) && Arrays.equals(password, user.getPassword().toCharArray()) && user.getRights().equals("Centrum")) {
						((CardLayout)display.getLayout()).show(display, "Center");
						
						((CenterView)((Window)display.getParent().getParent().getParent()).getViews().get(1)).setCloseOperation();
						((CenterView)((Window)display.getParent().getParent().getParent()).getViews().get(1)).initComponents();
						((CenterView)((Window)display.getParent().getParent().getParent()).getViews().get(1)).initActions();
						flag = true;
						break;
					} else if(user.getLogin().equals(login) && Arrays.equals(password, user.getPassword().toCharArray()) && user.getRights().equals("Firma")) {
						for(Company company : center.getCompanies()) {
							if(company.getName().equals(user.getOrgName()))
								center.setLoggedCompany(company);
						}
						
						((CompanyView)((Window)display.getParent().getParent().getParent()).getViews().get(2)).center = center; 
						((CompanyView)((Window)display.getParent().getParent().getParent()).getViews().get(2)).setCloseOperation();
						((CompanyView)((Window)display.getParent().getParent().getParent()).getViews().get(2)).initComponents();
						((CompanyView)((Window)display.getParent().getParent().getParent()).getViews().get(2)).initActions();
						((CardLayout)display.getLayout()).show(display, "Company");
						flag = true;
						break;
					} else if(user.getLogin().equals(login) && Arrays.equals(password, user.getPassword().toCharArray()) && user.getRights().equals("Bank")) {
						for(Bank bank : center.getBanks()) {
							if(bank.getName().equals(user.getOrgName())) {
								center.setLoggedBank(bank);
							}
						}
						
						((BankView)((Window)display.getParent().getParent().getParent()).getViews().get(3)).center = center; 
						((BankView)((Window)display.getParent().getParent().getParent()).getViews().get(3)).updateLists(); 
						((BankView)((Window)display.getParent().getParent().getParent()).getViews().get(3)).initComponents();
						((BankView)((Window)display.getParent().getParent().getParent()).getViews().get(3)).initActions();
						((BankView)((Window)display.getParent().getParent().getParent()).getViews().get(3)).setCloseOperation();
						((CardLayout)display.getLayout()).show(display, "Bank");
						flag = true;
						break;
					} 
				}
				
				if(flag == false)
					JOptionPane.showMessageDialog(display,
						    "Podano b³edny login / has³o",
						    "B³¹d",
						    JOptionPane.ERROR_MESSAGE);
			}
		});
	}
}