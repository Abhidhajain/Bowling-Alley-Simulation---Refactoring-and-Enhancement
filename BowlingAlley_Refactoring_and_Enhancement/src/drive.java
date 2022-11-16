import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import static java.lang.System.exit;

public class drive{
	JButton okButton,cancelButton;
	JFrame wind;
	JTextField askLanes,askPatrons;
	int nLanes,mPatrons;
	drive()
	{
		wind = BuildGeneralComponents.createWindow("Configure");
		JPanel configPanel = new JPanel();
		configPanel.setLayout(new BorderLayout());
		JPanel tempPanel = BuildGeneralComponents.createGridPanel(2,1);
		tempPanel.setBorder(new TitledBorder("Set Lanes & Patrons"));
		askLanes=BuildGeneralComponents.addPanel("Lanes",tempPanel);
		askPatrons=BuildGeneralComponents.addPanel("MaxPatrons",tempPanel);

		//button Panel
		JPanel buttonPanel = BuildGeneralComponents.createGridPanel(2,1);
		okButton = BuildGeneralComponents.createButton("Okay",buttonPanel);
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nLanes = Integer.parseInt(askLanes.getText());
				mPatrons = Integer.parseInt(askPatrons.getText());
				wind.hide();
				int numLanes = nLanes;
				int maxPatronsPerParty=mPatrons;
				ControlDesk controlDesk = new ControlDesk(numLanes);

				ControlDeskView cdv = new ControlDeskView( controlDesk, maxPatronsPerParty);
				ControlDeskSubscribe.subscribe(controlDesk, cdv );
			}
		});

		cancelButton = BuildGeneralComponents.createButton("Cancel",buttonPanel);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				wind.hide();
				exit(0);
			}
		});

		configPanel.add(tempPanel, "Center");
		configPanel.add(buttonPanel, "East");

		wind.getContentPane().add("Center", configPanel);
		wind.pack();

		BuildGeneralComponents.setWindow(wind);
	}
	public static void main(String[] args) {
		new drive();
	}
}
