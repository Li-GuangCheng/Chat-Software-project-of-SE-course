package com.chat.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Toolkit;

public class AoubtDialog extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AoubtDialog frame = new AoubtDialog();
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
	public AoubtDialog() {
		setTitle("LLJChat About");
		setIconImage(Toolkit.getDefaultToolkit().getImage(AoubtDialog.class.getResource("/Icons16/chat.png")));
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 298, 236);
		contentPane = new JPanel();
		contentPane.setBackground(Color.CYAN);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon(AoubtDialog.class.getResource("/Icons64/logo.png")));
		contentPane.add(lblNewLabel, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setForeground(new Color(51, 102, 204));
		panel.setBackground(new Color(0,0,255));
		panel.setOpaque(false);
		panel.setBorder(null);
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(3, 1, 8, 8));
		
		JLabel lblVersionV = new JLabel("Version: v1.0.0");
		lblVersionV.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblVersionV);
		
		JLabel lblCopyrightllj = new JLabel("Copyright © 2017 llj. All rights reserved. ");
		lblCopyrightllj.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblCopyrightllj);
	}

}
