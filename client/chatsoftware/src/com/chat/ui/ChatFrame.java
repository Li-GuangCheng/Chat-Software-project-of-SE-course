package com.chat.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class ChatFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatFrame frame = new ChatFrame();
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
	public ChatFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ChatFrame.class.getResource("/Icons64/chat.png")));
		setTitle("Chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 812, 583);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("Panel.background"));
		panel.setPreferredSize(new Dimension(150, 0));
		contentPane.add(panel, BorderLayout.WEST);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_11 = new JPanel();
		panel_11.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_11.setPreferredSize(new Dimension(0, 50));
		panel.add(panel_11, BorderLayout.NORTH);
		panel_11.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_13 = new JPanel();
		panel_11.add(panel_13, BorderLayout.CENTER);
		
		JLabel nickName = new JLabel("My Name");
		panel_13.add(nickName);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/search.png")));
		panel_13.add(btnSearch);
		
		JButton headIcon = new JButton("");
		headIcon.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons32/head.png")));
		panel_11.add(headIcon, BorderLayout.WEST);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		panel.add(scrollPane_2, BorderLayout.CENTER);
		
		JTree friendTree = new JTree();
		friendTree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("MyFriends") {
				{
					DefaultMutableTreeNode node_1,node_2,node_3,node_4,node_5,node_6;
					node_1 = new DefaultMutableTreeNode("Group1");
						node_1.add(new DefaultMutableTreeNode("friend1"));
						node_1.add(new DefaultMutableTreeNode("friend2"));
						node_1.add(new DefaultMutableTreeNode("friend3"));
						node_1.add(new DefaultMutableTreeNode("friend4"));
					add(node_1);
					node_2 = new DefaultMutableTreeNode("Group2");
						node_2.add(new DefaultMutableTreeNode("friend1"));
						node_2.add(new DefaultMutableTreeNode("friend2"));
						node_2.add(new DefaultMutableTreeNode("friend3"));
						node_2.add(new DefaultMutableTreeNode("friend4"));
					add(node_2);
					node_3 = new DefaultMutableTreeNode("Group3");
						node_3.add(new DefaultMutableTreeNode("friend1"));
						node_3.add(new DefaultMutableTreeNode("friend2"));
						node_3.add(new DefaultMutableTreeNode("friend3"));
						node_3.add(new DefaultMutableTreeNode("friend4"));
					add(node_3);
					node_4 = new DefaultMutableTreeNode("Group4");
						node_4.add(new DefaultMutableTreeNode("friend1"));
						node_4.add(new DefaultMutableTreeNode("friend2"));
						node_4.add(new DefaultMutableTreeNode("friend3"));
						node_4.add(new DefaultMutableTreeNode("friend4"));
					add(node_4);
					node_5 = new DefaultMutableTreeNode("Group5");
						node_5.add(new DefaultMutableTreeNode("friend1"));
						node_5.add(new DefaultMutableTreeNode("friend2"));
						node_5.add(new DefaultMutableTreeNode("friend3"));
						node_5.add(new DefaultMutableTreeNode("friend4"));
					add(node_5);
					node_6 = new DefaultMutableTreeNode("Group6");
						node_6.add(new DefaultMutableTreeNode("friend1"));
						node_6.add(new DefaultMutableTreeNode("friend2"));
						node_6.add(new DefaultMutableTreeNode("friend3"));
						node_6.add(new DefaultMutableTreeNode("friend4"));
					add(node_6);
				}
			}
		));
		scrollPane_2.setViewportView(friendTree);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_4.setPreferredSize(new Dimension(0, 50));
		panel_3.add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_14 = new JPanel();
		panel_14.setPreferredSize(new Dimension(300, 0));
		panel_4.add(panel_14, BorderLayout.WEST);
		panel_14.setLayout(new BorderLayout(0, 0));
		
		JLabel lblChattingWithXxxxx = new JLabel("   Chatting with Bob");
		lblChattingWithXxxxx.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 19));
		panel_14.add(lblChattingWithXxxxx, BorderLayout.CENTER);
		
		JPanel panel_15 = new JPanel();
		panel_15.setPreferredSize(new Dimension(200, 0));
		panel_4.add(panel_15, BorderLayout.EAST);
		panel_15.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_16 = new JPanel();
		panel_16.setPreferredSize(new Dimension(50, 0));
		panel_15.add(panel_16, BorderLayout.EAST);
		panel_16.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JMenuBar menuBar = new JMenuBar();
		panel_16.add(menuBar);
		
		JMenu menu = new JMenu("");
		menu.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/Category.png")));
		menuBar.add(menu);
		
		JMenuItem menuItemSetting = new JMenuItem("Settings...");
		menuItemSetting.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/set.png")));
		menu.add(menuItemSetting);
		
		JMenuItem menuItemAbout = new JMenuItem("About");
		menuItemAbout.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/info.png")));
		menu.add(menuItemAbout);
		
		JSeparator separator = new JSeparator();
		menu.add(separator);
		
		JMenuItem menuItemLogout = new JMenuItem("Logout");
		menuItemLogout.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/ext.png")));
		menu.add(menuItemLogout);
		
		JPanel panel_5 = new JPanel();
		panel_3.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_5.add(scrollPane_1, BorderLayout.CENTER);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new EmptyBorder(1, 0, 0, 0));
		panel_6.setPreferredSize(new Dimension(0, 150));
		panel_3.add(panel_6, BorderLayout.SOUTH);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_8 = new JPanel();
		panel_8.setPreferredSize(new Dimension(0, 30));
		panel_6.add(panel_8, BorderLayout.NORTH);
		panel_8.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(160, 0));
		panel_8.add(panel_2, BorderLayout.WEST);
		panel_2.setLayout(new GridLayout(1, 4, 0, 0));
		
		JButton btnFace = new JButton("");
		btnFace.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/Smile.png")));
		panel_2.add(btnFace);
		
		JButton btnFile = new JButton("");
		btnFile.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/folder.png")));
		panel_2.add(btnFile);
		
		JButton btnShake = new JButton("");
		btnShake.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons32/shake.png")));
		panel_2.add(btnShake);
		
		JButton btnCapture = new JButton("");
		btnCapture.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/cut.png")));
		panel_2.add(btnCapture);
		
		JPanel panel_7 = new JPanel();
		panel_7.setPreferredSize(new Dimension(80, 0));
		panel_8.add(panel_7, BorderLayout.EAST);
		panel_7.setLayout(new GridLayout(1, 2, 0, 0));
		
		JButton btnPhone = new JButton("");
		btnPhone.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/phone.png")));
		panel_7.add(btnPhone);
		
		JButton btnVideo = new JButton("");
		btnVideo.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/video.png")));
		panel_7.add(btnVideo);
		
		JPanel panel_9 = new JPanel();
		panel_6.add(panel_9, BorderLayout.CENTER);
		panel_9.setLayout(new BorderLayout(0, 0));
		
		JTextPane textMessage = new JTextPane();
		textMessage.setBackground(UIManager.getColor("Panel.background"));
		JScrollPane scrollPane = new JScrollPane(textMessage);
		panel_9.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel_10 = new JPanel();
		panel_10.setPreferredSize(new Dimension(0, 30));
		panel_6.add(panel_10, BorderLayout.SOUTH);
		panel_10.setLayout(new BorderLayout(0, 0));
		
		JButton btnSend = new JButton("Send");
		btnSend.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/send.png")));
		panel_10.add(btnSend, BorderLayout.EAST);
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

}
