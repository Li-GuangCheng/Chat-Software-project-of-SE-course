package com.chat.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;

public class VoiceDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VoiceDialog dialog = new VoiceDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VoiceDialog() {
		setResizable(false);
		Color c = new Color(0,0,255);
		setIconImage(Toolkit.getDefaultToolkit().getImage(VoiceDialog.class.getResource("/Icons16/chat.png")));
		setTitle("Talking");
		setBounds(100, 100, 300, 450);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.BLACK);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBounds(0, 283, 294, 139);
		contentPanel.add(panel);
		
		JButton btnHang = new JButton("");
		btnHang.setIcon(new ImageIcon(VoiceDialog.class.getResource("/Icons64/phone-hang-up.png")));
		btnHang.setForeground(new Color(51, 102, 204));
		btnHang.setBackground(c);
		btnHang.setOpaque(false);
		btnHang.setBorder(null);
		panel.add(btnHang);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(VoiceDialog.class.getResource("/Icons48/head.png")));
		btnNewButton.setEnabled(false);
		btnNewButton.setBounds(94, 31, 106, 97);
		contentPanel.add(btnNewButton);
		
		JLabel lblUserNickName = new JLabel("User Nick Name");
		lblUserNickName.setFont(new Font("宋体", Font.BOLD, 16));
		lblUserNickName.setForeground(Color.WHITE);
		lblUserNickName.setBounds(79, 149, 146, 20);
		contentPanel.add(lblUserNickName);
		
		JLabel lblNewLabel = new JLabel("Waiting for answer...");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 14));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(79, 179, 167, 15);
		contentPanel.add(lblNewLabel);
	}
}
