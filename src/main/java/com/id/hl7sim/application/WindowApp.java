package com.id.hl7sim.application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WindowApp {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	
	
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

	public void initialize() {
		
		frame = new JFrame();
		frame.setTitle("HL7 - Simulator");
		frame.setBounds(100, 100, 353, 483);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblFillInStart = new JLabel("Fill in start requirements:");
		lblFillInStart.setBounds(10, 11, 153, 14);
		frame.getContentPane().add(lblFillInStart);

		JLabel lblNewLabel = new JLabel("Number of Beds for Hospital:");
		lblNewLabel.setBounds(10, 54, 180, 14);
		frame.getContentPane().add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(200, 51, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblAccelerationFactor = new JLabel("Acceleration Factor:");
		lblAccelerationFactor.setBounds(10, 82, 141, 14);
		frame.getContentPane().add(lblAccelerationFactor);

		textField_1 = new JTextField();
		textField_1.setBounds(200, 79, 86, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JLabel lblEndpointip = new JLabel("Endpoint (IP)");
		lblEndpointip.setBounds(10, 117, 121, 14);
		frame.getContentPane().add(lblEndpointip);

		textField_2 = new JTextField();
		textField_2.setBounds(200, 110, 86, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Endpoint (Port)");
		lblNewLabel_1.setBounds(10, 149, 86, 14);
		frame.getContentPane().add(lblNewLabel_1);

		textField_3 = new JTextField();
		textField_3.setBounds(200, 146, 86, 20);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);

		JCheckBox chckbxAutocreateDatabasefirst = new JCheckBox("Auto-create Database (first Start)");
		chckbxAutocreateDatabasefirst.setBounds(10, 194, 246, 23);
		frame.getContentPane().add(chckbxAutocreateDatabasefirst);

		JButton btnSaveRequirements = new JButton("save requirements");
		btnSaveRequirements.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				String noB = textField.getText();
				App.setNumberOfBeds(Integer.parseInt(noB));
				
				String aF = textField_1.getText();
				App.setAccelerationFactor(Integer.parseInt(aF));

				App.setIp(textField_2.getText());

				String p = textField_3.getText();
				App.setPort(Integer.parseInt(p));
				
				if (chckbxAutocreateDatabasefirst.isSelected()) {
					// AUTO CREATE DATABASE
				}

			}
		});
		btnSaveRequirements.setBounds(10, 236, 141, 23);
		frame.getContentPane().add(btnSaveRequirements);

		JButton btnStartSimulation = new JButton("Start Simulation");
		btnStartSimulation.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				App.main(null);
			}
		});
		btnStartSimulation.setBounds(10, 294, 141, 23);
		frame.getContentPane().add(btnStartSimulation);

		JButton btnStopSimulation = new JButton("Stop Simulation");
		btnStopSimulation.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
		btnStopSimulation.setBounds(173, 294, 141, 23);
		frame.getContentPane().add(btnStopSimulation);

		

		JButton btnInfo = new JButton("Info");
		btnInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// display info
			}
		});
		btnInfo.setBounds(10, 411, 63, 23);
		frame.getContentPane().add(btnInfo);

		JLabel lblAboutHlsimulatorAnd = new JLabel("About HL7-Simulator and how to use it");
		lblAboutHlsimulatorAnd.setBounds(83, 415, 220, 14);
		frame.getContentPane().add(lblAboutHlsimulatorAnd);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 281, 414, 2);
		frame.getContentPane().add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 380, 414, 2);
		frame.getContentPane().add(separator_1);
	}

}
