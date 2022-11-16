


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChanceToRunnerUpPrompt implements ActionListener {

    private final JFrame win;
    private final JButton yesButton;
    private final JButton noButton;

    private int result;

    public ChanceToRunnerUpPrompt(String partyName,String runnerup, String winner) {

        result = 0;

        win = BuildGeneralComponents.createWindow("Should Runner Up get a chance?");

        JPanel colPanel = BuildGeneralComponents.createGridPanel(2, 1);

        // Label Panel
        JPanel labelPanel = BuildGeneralComponents.createFlowPanel();

        JLabel message = new JLabel("Winner and Runner up are "+winner+", "+runnerup+". Should runner up be given another chance?");

        labelPanel.add(message);

        // Button Panel
        JPanel buttonPanel = BuildGeneralComponents.createGridPanel(1, 2);

        Insets buttonMargin = new Insets(4, 4, 4, 4);

        yesButton = new JButton("Yes");
        JPanel yesButtonPanel = new JPanel();
        yesButtonPanel.setLayout(new FlowLayout());
        yesButton.addActionListener(this);
        yesButtonPanel.add(yesButton);

        noButton = new JButton("No");
        JPanel noButtonPanel = new JPanel();
        noButtonPanel.setLayout(new FlowLayout());
        noButton.addActionListener(this);
        noButtonPanel.add(noButton);

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        // Clean up main panel
        colPanel.add(labelPanel);
        colPanel.add(buttonPanel);

        win.getContentPane().add("Center", colPanel);

        win.pack();

        // Center Window on Screen
        Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
        win.setLocation(
                ((screenSize.width) / 2) - ((win.getSize().width) / 2),
                ((screenSize.height) / 2) - ((win.getSize().height) / 2));
        win.show();

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(yesButton)) {
            result = 1;
        }
        if (e.getSource().equals(noButton)) {
            result = 2;
        }

    }

    public int getResult() {
        while (result == 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.err.println("Interrupted");
            }
        }
        return result;
    }

    public void destroy() {
        win.setVisible(false);
    }

}

