/* AddPartyView.java
 *
 *  Version
 *  $Id$
 * 
 *  Revisions:
 * 		$Log: NewPatronView.java,v $
 * 		Revision 1.3  2003/02/02 16:29:52  ???
 * 		Added ControlDeskEvent and ControlDeskObserver. Updated Queue to allow access to Vector so that contents could be viewed without destroying. Implemented observer model for most of ControlDesk.
 * 		
 * 
 */

/**
 * Class for GUI components need to add a patron
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class NewPatronView {

	private JFrame win;
	private JButton abort, finished;
	private JTextField nickField, fullField, emailField;
	private String nick, full, email;
	private AddPartyView addParty;
	private NewPatronView npv;

	public NewPatronView(AddPartyView v) {
		addParty=v;
		this.npv=this;

		win = BuildGeneralComponents.createWindow("Add Patron");

		JPanel colPanel = new JPanel();
		colPanel.setLayout(new BorderLayout());

		// Patron Panel
		JPanel patronPanel = BuildGeneralComponents.createGridPanel(3,1);
		patronPanel.setBorder(new TitledBorder("Your Info"));

		nickField=BuildGeneralComponents.addPanel("Nick Name",patronPanel);
		fullField=BuildGeneralComponents.addPanel("Full Name",patronPanel);
		emailField=BuildGeneralComponents.addPanel("E-Mail",patronPanel);

		// Button Panel
		JPanel buttonPanel = BuildGeneralComponents.createGridPanel(4,1);

		finished = BuildGeneralComponents.createButton("Add Patron",buttonPanel);
		finished.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nick = nickField.getText();
				full = fullField.getText();
				email = emailField.getText();
				addParty.updateNewPatron(npv);
				win.hide();
			}
		});

		abort = BuildGeneralComponents.createButton("Abort",buttonPanel);
		abort.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				win.hide();
			}
		});

		// Clean up main panel
		colPanel.add(patronPanel, "Center");
		colPanel.add(buttonPanel, "East");

		win.getContentPane().add("Center", colPanel);
		win.pack();

		// Center Window on Screen
		BuildGeneralComponents.setWindow(win);
	}


	public String getNick() {
		return nick;
	}

	public String getFull() {
		return full;
	}

	public String getEmail() {
		return email;
	}

}
