package com.chat.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginFrame.class.getResource("/Icons64/chat.png")));
		setTitle("Chat Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 380, 258);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("Panel.background"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(5, 1, 8, 8));
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		contentPane.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(false);
		contentPane.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblUsername = new JLabel("Username:  ");
		lblUsername.setIcon(new ImageIcon(LoginFrame.class.getResource("/Icons16/user.png")));
		panel_2.add(lblUsername);
		
		textField_2 = new JTextField();
		panel_2.add(textField_2);
		textField_2.setColumns(15);
		
		JPanel panel_3 = new JPanel();
		panel_3.setOpaque(false);
		contentPane.add(panel_3);
		
		JLabel lblPassword = new JLabel("Password:  ");
		lblPassword.setIcon(new ImageIcon(LoginFrame.class.getResource("/Icons16/lock.png")));
		panel_3.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(15);
		panel_3.add(passwordField);
		
		JPanel panel_4 = new JPanel();
		panel_4.setOpaque(false);
		contentPane.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JButton btnLogin = new JButton("Login");
		panel_4.add(btnLogin, BorderLayout.CENTER);
		
		JLabel label = new JLabel("              ");
		panel_4.add(label, BorderLayout.WEST);
		
		JLabel label_1 = new JLabel("              ");
		panel_4.add(label_1, BorderLayout.EAST);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JButton btnNewButton = new JButton("Register");
		panel.add(btnNewButton, BorderLayout.WEST);
		
		JButton btnNewButton_1 = new JButton("Forget password");
		panel.add(btnNewButton_1, BorderLayout.EAST);
		
		JLabel label_2 = new JLabel("     ");
		panel.add(label_2, BorderLayout.NORTH);
	}

}
