package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JCheckBox;
import javax.swing.JProgressBar;

public class WindowApp {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowApp window = new WindowApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public WindowApp() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("HL7 - Simulator");
		frame.setBounds(100, 100, 358, 355);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(127, 11, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblSizeOfHospital = new JLabel("Acceleration Factor");
		lblSizeOfHospital.setBounds(10, 52, 107, 14);
		frame.getContentPane().add(lblSizeOfHospital);
		
		JLabel label = new JLabel("Endpoint");
		label.setBounds(10, 87, 86, 14);
		frame.getContentPane().add(label);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(127, 49, 86, 20);
		frame.getContentPane().add(textField_1);
		
		JLabel label_1 = new JLabel("Size of Hospital");
		label_1.setBounds(10, 14, 86, 14);
		frame.getContentPane().add(label_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(127, 84, 86, 20);
		frame.getContentPane().add(textField_2);
		
		JButton button_1 = new JButton("save");
		button_1.setBounds(258, 83, 55, 23);
		frame.getContentPane().add(button_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 151, 322, 2);
		frame.getContentPane().add(separator);
		
		JButton btnStartSimulation = new JButton("Start Simulation");
		btnStartSimulation.setBounds(10, 164, 123, 23);
		frame.getContentPane().add(btnStartSimulation);
		
		JButton button = new JButton("save");
		button.setBounds(258, 10, 55, 23);
		frame.getContentPane().add(button);
		
		JButton button_2 = new JButton("save");
		button_2.setBounds(258, 48, 55, 23);
		frame.getContentPane().add(button_2);
		
		JCheckBox chckbxAutocreateDatebaseAnd = new JCheckBox("Auto-create Datebase and tables (first start)");
		chckbxAutocreateDatebaseAnd.setBounds(10, 121, 270, 23);
		frame.getContentPane().add(chckbxAutocreateDatebaseAnd);
		
		JButton button_3 = new JButton("Stop Simulation");
		button_3.setBounds(209, 164, 123, 23);
		frame.getContentPane().add(button_3);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 249, 322, 2);
		frame.getContentPane().add(separator_1);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(10, 198, 322, 14);
		frame.getContentPane().add(progressBar);
		
		JLabel label_3 = new JLabel("Status");
		label_3.setBounds(152, 223, 107, 14);
		frame.getContentPane().add(label_3);
		
		JButton btnInfo = new JButton("Info");
		btnInfo.setBounds(10, 262, 89, 23);
		frame.getContentPane().add(btnInfo);
		
		JLabel label_2 = new JLabel("About HL7 - Simulator and how to use it");
		label_2.setBounds(118, 266, 214, 14);
		frame.getContentPane().add(label_2);
	}
}
