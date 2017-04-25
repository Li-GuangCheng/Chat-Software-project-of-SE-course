package com.chat.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Toolkit;

public class NetworkSettingDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public static String serverAddr = "ec2-54-254-130-230.ap-southeast-1.compute.amazonaws.com";
	public static int serverPort = 5222;
	private JTextField txtIP;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			NetworkSettingDialog dialog = new NetworkSettingDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public NetworkSettingDialog() {
		setTitle("Network Settings");
		setIconImage(Toolkit.getDefaultToolkit().getImage(NetworkSettingDialog.class.getResource("/Icons16/chat.png")));
		setBounds(100, 100, 387, 258);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(4, 1, 8, 8));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			{
				JLabel lblIpAddress = new JLabel("Server Addr : ");
				panel.add(lblIpAddress);
			}
			{
				txtIP = new JTextField();
				panel.add(txtIP);
				txtIP.setColumns(15);
				if(serverAddr != null && !serverAddr.equals("")){
					txtIP.setText(serverAddr);
				}
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			{
				JLabel lblServerPort = new JLabel("Server Port : ");
				panel.add(lblServerPort);
			}
			{
				textField = new JTextField();
				panel.add(textField);
				textField.setColumns(15);
				if(serverPort != -1){
					textField.setText("" + serverPort);
				}
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnCancel = new JButton("Cancel");
				btnCancel.setActionCommand("Cancel");
				buttonPane.add(btnCancel);
			}
			{
				JButton btnOK = new JButton("OK");
				btnOK.setActionCommand("OK");
				buttonPane.add(btnOK);
				getRootPane().setDefaultButton(btnOK);
			}
		}
	}

}
