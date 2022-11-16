import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ButtonAddition<T> {
    private JPanel newButtonPanel;
    public ButtonAddition(T controlDesk, String ButtonName){
        JButton newButton = new JButton(ButtonName);
        newButtonPanel = new JPanel();
        newButtonPanel.setLayout(new FlowLayout());
        newButton.addActionListener((ActionListener) controlDesk);
        newButtonPanel.add(newButton);
    }
    public JPanel returnButton(){return newButtonPanel;}
}



