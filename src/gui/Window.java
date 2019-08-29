package gui;

import javax.swing.JFrame;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.util.ArrayList;

import gui.views.BankView;
import gui.views.CenterView;
import gui.views.CompanyView;
import gui.views.LoginView;
import gui.views.View; 

/**
 * Window represents simple window for application gui.
 * 
 * @see JFrame
 * @author Sebastian Smoliñski
 * @since 0.1
 * @version 0.5
 * @category GUI
 */
public class Window extends JFrame {
	private static final long serialVersionUID = 7446192599263749847L;
	private CardLayout layout;
	private Container display;
	private ArrayList<View> views = new ArrayList<View>();

	/**
	 * Make window visible, sets width, height and basic layout options.
	 * 
	 * @param title - window's title
	 * @param width - window's width in px
	 * @param height - window's height in px
	 */
	public Window(String title, int width, int height) {
		super(title);

		setSize(width, height);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Create and set layout
		display = getContentPane();
		layout = new CardLayout(60, 60);
		display.setLayout(layout);

		// Create list of views
		views.add(new LoginView(display));
		views.add(new CenterView(display));
		views.add(new CompanyView(display));
		views.add(new BankView(display)); 

		// Bind list of views with card names
		display.add(views.get(0).getName(), views.get(0).getPanel());
		display.add(views.get(1).getName(), views.get(1).getPanel());
		display.add(views.get(2).getName(), views.get(2).getPanel());
		display.add(views.get(3).getName(), views.get(3).getPanel());
		
		layout.show(display, "Login");
	}

	/**
	 * Starting point for program, it creates new Thread and object for Window
	 * 
	 * @param args - not used
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Window window = new Window("Centrum 0.5", 800, 600);
			}
		});
	}
	
	/**
	 * Gets views objects list for avoid duplication of instances
	 * @return array list of views objects
	 */
	public ArrayList<View> getViews() {
		return views; 
	}
}
