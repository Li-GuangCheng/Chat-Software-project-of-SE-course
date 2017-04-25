package com.chat.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.util.StringUtils;

import com.llj.network.SeverConnection;

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textUsername;
	private JPasswordField textPassword;
	private static String username;
	private static String password;
	public static AbstractXMPPConnection connection;
	public static Thread heartBeats;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					ChatFrame.setLocationCenter(frame);
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
		
		Color c = new Color(0,0,255);
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		contentPane.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		panel_1.add(panel_5, BorderLayout.EAST);
		
		JButton btnNetworkSettings = new JButton("");
		btnNetworkSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NetworkSettingDialog dialog = new NetworkSettingDialog();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		btnNetworkSettings.setBackground(c);
		btnNetworkSettings.setOpaque(false);
		btnNetworkSettings.setBorder(null);
		btnNetworkSettings.setIcon(new ImageIcon(LoginFrame.class.getResource("/Icons16/network.png")));
		panel_5.add(btnNetworkSettings);
		
		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(false);
		contentPane.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblUsername = new JLabel("Username:  ");
		lblUsername.setIcon(new ImageIcon(LoginFrame.class.getResource("/Icons16/user.png")));
		panel_2.add(lblUsername);
		
		textUsername = new JTextField();
		panel_2.add(textUsername);
		textUsername.setColumns(15);
		
		JPanel panel_3 = new JPanel();
		panel_3.setOpaque(false);
		contentPane.add(panel_3);
		
		JLabel lblPassword = new JLabel("Password:  ");
		lblPassword.setIcon(new ImageIcon(LoginFrame.class.getResource("/Icons16/lock.png")));
		panel_3.add(lblPassword);
		
		textPassword = new JPasswordField();
		textPassword.setColumns(15);
		panel_3.add(textPassword);
		
		JPanel panel_4 = new JPanel();
		panel_4.setOpaque(false);
		contentPane.add(panel_4);
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JCheckBox chckbxRememberPassword = new JCheckBox("Remember Password               ");
		panel_4.add(chckbxRememberPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				username = textUsername.getText().trim();
				password = new String(textPassword.getPassword());
				if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
					JOptionPane.showMessageDialog(null, "Please input your username and password!", "Warnning", JOptionPane.WARNING_MESSAGE); 
				}else{
					System.out.println("username is '"+username+"'");
					System.out.println("password is '"+password+"'");
					connection = SeverConnection.login("lgc", "111111");
					heartBeats = SeverConnection.heartBeats(connection);
					if(connection.isConnected()){
						if(connection.isAuthenticated()){
							LoginFrame.this.setVisible(false);
							System.out.println("Successfully login to the server.");
							ChatFrame frame = new ChatFrame(LoginFrame.this);
							ChatFrame.setLocationCenter(frame);
							frame.setVisible(true);
						}else{
							JOptionPane.showMessageDialog(null, "Authentication failed!", "Warning", JOptionPane.WARNING_MESSAGE); 
						}
					}else{
						JOptionPane.showMessageDialog(null, "Failed to connnect to server!", "Error", JOptionPane.ERROR_MESSAGE); 
					}
				}
			}
		});
		btnLogin.setFont(UIManager.getFont("Button.font"));
		panel_4.add(btnLogin);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JButton btnRegister = new JButton("  Register");
		btnRegister.setFont(UIManager.getFont("Button.font"));
		btnRegister.setForeground(new Color(51, 102, 204));
		btnRegister.setBackground(c);
		btnRegister.setOpaque(false);
		btnRegister.setBorder(null);
		panel.add(btnRegister, BorderLayout.WEST);
		
		JButton btnForgetPswd = new JButton("Forget password?  ");
		btnForgetPswd.setFont(UIManager.getFont("Button.font"));
		btnForgetPswd.setForeground(new Color(51, 102, 204));
		btnForgetPswd.setBackground(c);
		btnForgetPswd.setOpaque(false);
		btnForgetPswd.setBorder(null);
		panel.add(btnForgetPswd, BorderLayout.EAST);
		
		JLabel label_2 = new JLabel("     ");
		panel.add(label_2, BorderLayout.NORTH);
	}

}
