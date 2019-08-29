package gui.views;

import java.awt.Container;
import java.io.IOException;

import javax.swing.JPanel;

import modules.center.Center;

/**
 * Single display view that can be render in JFrame 
 * @author Sebastian Smoliñski
 * @version 0.2 
 * @since 0.1 
 * @category GUI
 *
 */
public abstract class View {
	protected JPanel view; 
	protected Container display; 
	protected Center center; 
	
	public View(Container display) {
		this.display = display; 
		this.view = new JPanel(); 
		try {
			this.center = new Center();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} 
	}
	
	public JPanel getPanel() {
		return this.view; 
	}
	
	public abstract String getName(); 
}
