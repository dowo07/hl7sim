package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WindowApp {

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.setSize(500, 500);

		JPanel panel = new JPanel();

		JButton button1 = new JButton("Button");
		button1.setSize(20, 10);

		JTextField text = new JTextField(20);

		panel.add(button1);
		panel.add(text);
		frame.add(panel);

		frame.setVisible(true);

		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int numberOfBeds = Integer.parseInt(text.getText());
				System.out.println(numberOfBeds);

			}
		});

	}

}