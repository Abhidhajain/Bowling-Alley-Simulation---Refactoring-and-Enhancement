import java.awt.*;
import javax.swing.*;

public class BuildGeneralComponents {
    public static JButton createButton(String str, JPanel buttonPanel) {
        JButton btn= new JButton(str);
        JPanel btnPanel=new JPanel();
        btnPanel.setLayout(new FlowLayout());
        btnPanel.add(btn);
        buttonPanel.add(btnPanel);
        return btn;
    }
    public static JFrame createWindow(String str){
        JFrame win = new JFrame(str);
        win.getContentPane().setLayout(new BorderLayout());
        ((JPanel) win.getContentPane()).setOpaque(false);
        return win;
    }
    public static JPanel createGridPanel(int rows, int columns){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(rows,columns));
        return panel;
    }
    public static JPanel createFlowPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        return panel;
    }
    public static JTextField addPanel(String str,JPanel panel){
        JPanel inPanel = createFlowPanel();
        JLabel label1 = new JLabel(str);
        JTextField field1 = new JTextField("", 15);
        inPanel.add(label1);
        inPanel.add(field1);
        panel.add(inPanel);
        return field1;
    }
    public static void setWindow(JFrame win){
        Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
        win.setLocation(
                ((screenSize.width) / 2) - ((win.getSize().width) / 2),
                ((screenSize.height) / 2) - ((win.getSize().height) / 2));
        win.show();
    }

}
