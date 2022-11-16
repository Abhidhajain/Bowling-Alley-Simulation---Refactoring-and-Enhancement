/*
 * PinSetterView/.java
 *
 * Version:
 *   $Id$
 *
 * Revision:
 *   $Log$
 */

/**
 *  constructs a prototype PinSetter GUI
 *
 */

import java.awt.*;
import javax.swing.*;
import java.util.Vector;


public class PinSetterView implements PinsetterObserver {

    private Vector pinVect = new Vector ( );
    private JPanel firstRoll;
    private JPanel secondRoll;
	private JFrame frame;
    /**
     * Constructs a Pin Setter GUI displaying which roll it is with
     * yellow boxes along the top (1 box for first roll, 2 boxes for second)
     * and displays the pins as numbers in this format:
     *
     *                7   8   9   10
     *                  4   5   6
     *                    2   3
     *                      1
     *
     */

    public PinSetterView ( int laneNum ) {
	
	frame = new JFrame ( "Lane " + laneNum + ":" );
	Container cpanel = frame.getContentPane ( );
	
	JPanel pins = BuildGeneralComponents.createGridPanel(4,7);
	
	//********************Top of GUI indicates first or second roll
	
	JPanel top = new JPanel ( );
	
	firstRoll = new JPanel ( );
	firstRoll.setBackground( Color.yellow );
	
	secondRoll = new JPanel ( );
	secondRoll.setBackground ( Color.black );
	
	top.add ( firstRoll, BorderLayout.WEST );
	top.add ( secondRoll, BorderLayout.EAST );



		var icon = createImageIcon("pin_s.jfif");
		JPanel one = new JPanel();
		JLabel oneL = new JLabel(icon);
		one.add(oneL);
		pinVect.add(oneL);
		JPanel two = new JPanel();
		JLabel twoL = new JLabel(icon);
		two.add(twoL);
		pinVect.add(twoL);
		JPanel three = new JPanel();
		JLabel threeL = new JLabel(icon);
		three.add(threeL);
		pinVect.add(threeL);
		JPanel four = new JPanel();
		JLabel fourL = new JLabel(icon);
		four.add(fourL);
		pinVect.add(fourL);
		JPanel five = new JPanel();
		JLabel fiveL = new JLabel(icon);
		five.add(fiveL);
		pinVect.add(fiveL);
		JPanel six = new JPanel();
		JLabel sixL = new JLabel(icon);
		six.add(sixL);
		pinVect.add(sixL);
		JPanel seven = new JPanel();
		JLabel sevenL = new JLabel(icon);
		seven.add(sevenL);
		pinVect.add(sevenL);
		JPanel eight = new JPanel();
		JLabel eightL = new JLabel(icon);
		eight.add(eightL);
		pinVect.add(eightL);
		JPanel nine = new JPanel();
		JLabel nineL = new JLabel(icon);
		nine.add(nineL);
		pinVect.add(nineL);
		JPanel ten = new JPanel();
		JLabel tenL = new JLabel(icon);
		ten.add(tenL);
		pinVect.add(tenL);

		/* Fourth Row */
		for(int i=0;i<4;i++){
			pins.add(new JPanel());
		}
		pins.add(seven);
		pins.add(new JPanel());
		pins.add(eight);
		pins.add(new JPanel());
		pins.add(nine);
		pins.add(new JPanel());
		pins.add(ten);
		for(int i=0;i<4;i++){
			pins.add(new JPanel());
		}
		/* Third Row */
		for(int i=0;i<5;i++){
			pins.add(new JPanel());
		}
		pins.add(four);
		pins.add(new JPanel());
		pins.add(five);
		pins.add(new JPanel());
		pins.add(six);
		for(int i=0;i<4;i++){
			pins.add(new JPanel());
		}




	/* Second Row */
		for(int i=0;i<7;i++)
		{
			pins.add (new JPanel());
		}
		pins.add(two);
		pins.add(new JPanel());
		pins.add(three);
		for(int i=0;i<5;i++)
	{
		pins.add (new JPanel());
	}

	/* First Row */
		for(int i=0;i<8;i++)
		{
			pins.add (new JPanel());
		}
		pins.add(one);
		for(int i=0;i<7;i++)
		{
			pins.add (new JPanel());
		}



	top.setBackground ( Color.black );
	cpanel.add ( top, BorderLayout.NORTH );
	
	pins.setBackground ( Color.black );
	pins.setForeground ( Color.yellow );
	
	cpanel.add ( pins, BorderLayout.CENTER );
	frame.pack();
    }
    
    
    /**
     * This method receives a pinsetter event.  The event is the current
     * state of the PinSetter and the method changes how the GUI looks
     * accordingly.  When pins are "knocked down" the corresponding label
     * is grayed out.  When it is the second roll, it is indicated by the
     * appearance of a second yellow box at the top.
     *
     * @param pe    The state of the pinsetter is sent in this event.
     */

	protected ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL == null) {
			System.err.println("Couldn't find file: " + path);
			return null;

		}
		else {
			return new ImageIcon(imgURL, "Bowling Pin");

		}
	}
    public void receivePinsetterEvent(PinsetterEvent pe){
		var pic1 = createImageIcon("pin_s.jfif");
		var pic2 = createImageIcon("pin_d.jfif");
	if ( !(pe.isFoulCommited()) ) {
	    	JLabel tempPin = new JLabel ( );
	    	for ( int c = 0; c < 10; c++ ) {
				boolean pin = pe.pinKnockedDown ( c );
				tempPin = (JLabel)pinVect.get ( c );
				if ( pin ) {
					tempPin.setIcon(pic2);
				}
				else {
					tempPin.setIcon(pic1);}
	    	}
    	}
		if ( pe.getThrowNumber() == 1 ) {
	   		 secondRoll.setBackground ( Color.yellow );
		}
	if ( pe.pinsDownOnThisThrow() == -1) {
		for ( int i = 0; i != 10; i++){
			((JLabel) pinVect.get(i)).setIcon(pic1);
		}
		secondRoll.setBackground( Color.black);
	}
    }
    
    public void show() {
    	frame.show();
    }

    public void hide() {
    	frame.hide();
    }
    
    public static void main ( String args [ ] ) {
		PinSetterView pg = new PinSetterView ( 1 );
    }
    
}
