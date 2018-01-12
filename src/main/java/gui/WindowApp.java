package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class WindowApp {

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        
        JPanel panel = new JPanel();

        JButton button1 = new JButton();
        
        JTextField text = new JTextField(20);
        
        panel.add(button1);
        panel.add(text);
        frame.add(panel);
        
        
        frame.setVisible(true);

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JOptionPane.showMessageDialog(text, text.getText());

            }
        });

    }

}