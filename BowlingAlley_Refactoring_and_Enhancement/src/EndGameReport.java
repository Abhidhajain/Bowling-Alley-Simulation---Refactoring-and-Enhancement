/**
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.util.*;

public class EndGameReport implements ListSelectionListener {

	private JFrame win;
	private JButton printButton, finished;
	private JList memberList;
	private Vector retVal;
	private int result;
	private String selectedMember;

	public EndGameReport( String partyName, Party party ) {
	
		result =0;
		retVal = new Vector();
		win = new JFrame("End Game Report for " + partyName + "?" );
		win.getContentPane().setLayout(new BorderLayout());
		((JPanel) win.getContentPane()).setOpaque(false);

		JPanel colPanel = BuildGeneralComponents.createGridPanel(1,2);

		// Member Panel
		JPanel partyPanel = BuildGeneralComponents.createFlowPanel();
		partyPanel.setBorder(new TitledBorder("Party Members"));
		
		Vector myVector = new Vector();
		Iterator iter = (party.getMembers()).iterator();
		while (iter.hasNext()){
			myVector.add( ((Bowler)iter.next()).getNickName() );
		}	
		memberList = new JList(myVector);
		memberList.setFixedCellWidth(120);
		memberList.setVisibleRowCount(5);
		memberList.addListSelectionListener(this);
		JScrollPane partyPane = new JScrollPane(memberList);
		partyPanel.add(partyPane);

		partyPanel.add( memberList );

		// Button Panel
		JPanel buttonPanel = BuildGeneralComponents.createGridPanel(2,1);

		printButton = BuildGeneralComponents.createButton("Print Report",buttonPanel);
		printButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				retVal.add(selectedMember);
			}
		});

		finished = BuildGeneralComponents.createButton("Finished",buttonPanel);
		finished.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				win.hide();
				result = 1;
			}
		});


		// Clean up main panel
		colPanel.add(partyPanel);
		colPanel.add(buttonPanel);

		win.getContentPane().add("Center", colPanel);

		win.pack();

		// Center Window on Screen
		BuildGeneralComponents.setWindow(win);
	}


	public void valueChanged(ListSelectionEvent e) {
		selectedMember =
			((String) ((JList) e.getSource()).getSelectedValue());
	}

	public Vector getResult() {
		while ( result == 0 ) {
			try {
				Thread.sleep(10);
			} catch ( InterruptedException e ) {
				System.err.println( "Interrupted" );
			}
		}
		return retVal;	
	}
	
	public void destroy() {
		win.hide();
	}

	public static void main( String args[] ) {
		Vector bowlers = new Vector();
		for ( int i=0; i<4; i++ ) {
			bowlers.add( new Bowler( "aaaaa", "aaaaa", "aaaaa" ) );
		}
		Party party = new Party( bowlers );
		String partyName="wank";
		EndGameReport e = new EndGameReport( partyName, party );
	}
	
}

