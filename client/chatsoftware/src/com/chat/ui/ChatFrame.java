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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Collection;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
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
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultTreeModel;

import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.util.StringUtils;

import com.llj.network.SeverConnection;
import com.llj.util.DateUtil;

public class ChatFrame extends JFrame implements KeyListener {

	private JPanel contentPane;
	//textPane is the record about the textfield. 
	//textMessage is the input textfidld.
	private static JTextPane textPane, textMessage;
	private static JDialog emojiDialog = new JDialog();;
	private static boolean isEmojiOpen = false;
	private static ChatManager chatManager = null;// ChatManager used to create Chat.
	private static Roster roster = null;// Friends list
	private static Chat chat = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatFrame frame = new ChatFrame(null);
					setLocationCenter(frame);
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
	public ChatFrame(JFrame loginFrame) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ChatFrame.class.getResource("/Icons64/chat.png")));
		setTitle("Chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(813, 592);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//
		initManager(ChatFrame.this);
		chat = chatWith("lh");
		
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
//		friendTree.setModel(new DefaultTreeModel(
//			new DefaultMutableTreeNode("MyFriends") {
//				{
//					DefaultMutableTreeNode node_1;
//					node_1 = new DefaultMutableTreeNode("Group1");
//						node_1.add(new DefaultMutableTreeNode("friend1"));
//						node_1.add(new DefaultMutableTreeNode("friend2"));
//						node_1.add(new DefaultMutableTreeNode("friend3"));
//						node_1.add(new DefaultMutableTreeNode("friend4"));
//					add(node_1);
//					node_1 = new DefaultMutableTreeNode("Group2");
//						node_1.add(new DefaultMutableTreeNode("friend1"));
//						node_1.add(new DefaultMutableTreeNode("friend2"));
//						node_1.add(new DefaultMutableTreeNode("friend3"));
//						node_1.add(new DefaultMutableTreeNode("friend4"));
//					add(node_1);
//					node_1 = new DefaultMutableTreeNode("Group3");
//						node_1.add(new DefaultMutableTreeNode("friend1"));
//						node_1.add(new DefaultMutableTreeNode("friend2"));
//						node_1.add(new DefaultMutableTreeNode("friend3"));
//						node_1.add(new DefaultMutableTreeNode("friend4"));
//					add(node_1);
//					node_1 = new DefaultMutableTreeNode("Group4");
//						node_1.add(new DefaultMutableTreeNode("friend1"));
//						node_1.add(new DefaultMutableTreeNode("friend2"));
//						node_1.add(new DefaultMutableTreeNode("friend3"));
//						node_1.add(new DefaultMutableTreeNode("friend4"));
//					add(node_1);
//					node_1 = new DefaultMutableTreeNode("Group5");
//						node_1.add(new DefaultMutableTreeNode("friend1"));
//						node_1.add(new DefaultMutableTreeNode("friend2"));
//						node_1.add(new DefaultMutableTreeNode("friend3"));
//						node_1.add(new DefaultMutableTreeNode("friend4"));
//					add(node_1);
//					node_1 = new DefaultMutableTreeNode("Group6");
//						node_1.add(new DefaultMutableTreeNode("friend1"));
//						node_1.add(new DefaultMutableTreeNode("friend2"));
//						node_1.add(new DefaultMutableTreeNode("friend3"));
//						node_1.add(new DefaultMutableTreeNode("friend4"));
//					add(node_1);
//				}
//			}
//		));
//		scrollPane_2.setViewportView(friendTree);
		
		initFriendTree(friendTree, scrollPane_2);
		
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
		
		JMenuItem mntmAddFriend = new JMenuItem("Add Friend");
		mntmAddFriend.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/friend_add.png")));
		menu.add(mntmAddFriend);
		
		JMenuItem menuItemSetting = new JMenuItem("Settings...");
		menuItemSetting.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/set.png")));
		menu.add(menuItemSetting);
		
		JMenuItem menuItemAbout = new JMenuItem("About");
		menuItemAbout.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/info.png")));
		menu.add(menuItemAbout);
		
		JSeparator separator = new JSeparator();
		menu.add(separator);
		
		JMenuItem menuItemLogout = new JMenuItem("Logout");
		menuItemLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(LoginFrame.heartBeats != null){
						LoginFrame.heartBeats.stop();
					}
					if(LoginFrame.connection != null){
						LoginFrame.connection.disconnect();
					}
					Thread.sleep(3000);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ChatFrame.this.setVisible(false);
				if(loginFrame != null){
					loginFrame.setVisible(true);
				}
			}
		});
		menuItemLogout.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/ext.png")));
		menu.add(menuItemLogout);
		
		JPanel panel_5 = new JPanel();
		panel_3.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_5.add(scrollPane_1, BorderLayout.CENTER);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		scrollPane_1.setViewportView(textPane);
		
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
		panel_2.setPreferredSize(new Dimension(200, 0));
		panel_8.add(panel_2, BorderLayout.WEST);
		panel_2.setLayout(new GridLayout(1, 5, 0, 0));
		
		JButton btnFace = new JButton("");
		emojiDialog();
		btnFace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showEmojiDialog(btnFace);
			}
		});
		btnFace.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/Smile.png")));
		panel_2.add(btnFace);
		
		JButton btnFile = new JButton("");
		btnFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc=new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
				jfc.showDialog(new JLabel(), "Choose");
				jfc.setDialogTitle("Choose a file");
				File file=jfc.getSelectedFile();
				if(file != null) {
					if(file.isDirectory()){
						System.out.println("Folder:"+file.getAbsolutePath());
					}else if(file.isFile()){
						System.out.println("File:"+file.getAbsolutePath());
					}
					System.out.println(jfc.getSelectedFile().getName());
				}
			}
		});
		btnFile.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/folder.png")));
		panel_2.add(btnFile);
		
		JButton btnShake = new JButton("");
		btnShake.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendShake();
				shakeFrame();
			}
		});
		btnShake.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons32/shake.png")));
		panel_2.add(btnShake);
		
		JButton btnCapture = new JButton("");
		btnCapture.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/cut.png")));
		panel_2.add(btnCapture);
		
		JButton button = new JButton("");
		button.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/font.png")));
		panel_2.add(button);
		
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
		
		textMessage = new JTextPane();
		textMessage.addKeyListener(this);
		textMessage.setBackground(UIManager.getColor("Panel.background"));
		JScrollPane scrollPane = new JScrollPane(textMessage);
		panel_9.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel_10 = new JPanel();
		panel_10.setPreferredSize(new Dimension(0, 30));
		panel_6.add(panel_10, BorderLayout.SOUTH);
		panel_10.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_12 = new JPanel();
		panel_10.add(panel_12, BorderLayout.EAST);
		
		JCheckBox chckbxTranslate = new JCheckBox("Translate");
		panel_12.add(chckbxTranslate);
		
		JButton btnSend = new JButton("Send");
		// the send function of the Send Button.
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendMsg();
			}
		});
		btnSend.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/send.png")));
		panel_12.add(btnSend);
		
		
	}
	
	/**
	 * initialize the ChatManager
	 * @param frame
	 */
	private static void initManager(JFrame frame){
		if(LoginFrame.connection != null && SeverConnection.isConnectionValid(LoginFrame.connection)){
			chatManager = SeverConnection.getChatManager(LoginFrame.connection);
			chatManager.addChatListener(new LLJChatMessageListener(textPane, textMessage, frame));
			roster = Roster.getInstanceFor(LoginFrame.connection);
		}
	}
	
	/**
	 * Create chat with user of userJID
	 * @param userJID
	 * @return
	 */
	public static Chat chatWith(String userJID){
		Chat chat = chatManager.createChat(userJID+"@"+SeverConnection.xmppDomain);
		return chat;
	}
	
	/**
	 * Initialize the friends tree.
	 * @param tree
	 * @param sp
	 */
	public static void initFriendTree(JTree tree, JScrollPane sp){
		if(roster != null){
			Collection<RosterEntry> entries = roster.getEntries();
//			System.out.println("size="+entries.size());
			FriendTreeNode root = new FriendTreeNode("Root1");
			root.setNickname("Root2");
			root.setImg(new ImageIcon(TestFriendTree.class.getResource("/Icons16/user.png")));
			
			for (int i = 0; i < 1; i++) {
				FriendTreeNode child = new FriendTreeNode("My Friends");
				child.setNickname("My Friends");
				//Add my friends to the tree.
				for (RosterEntry entry : entries) {
					FriendTreeNode childj = new FriendTreeNode(entry.getName());
					childj.setNickname(entry.getName());
					childj.setImg(new ImageIcon(TestFriendTree.class.getResource("/Icons32/man.png")));
					childj.setSignature("I am " + entry.getName());
					child.addChild(childj);
				}
				root.addChild(child);
			}
			
			DefaultTreeModel jMode = new DefaultTreeModel(root);
			tree.setModel(jMode);
			tree.setCellRenderer(new FriendTreeRender());
			tree.setRootVisible(false);
			tree.setToggleClickCount(1);//Expand the tree with 1 time click.
			
			sp.setViewportView(tree);
		}else{
			System.out.println("Get roster from connection errors.");
		}
	}
	
	/**
	 * Send a message.
	 */
	public void sendMsg(){
		String msg = textMessage.getText();
		if(!StringUtils.isEmpty(msg)){
			Message msgTo = new Message();
			msgTo.setBody(msg);
			try {
				chat.sendMessage(msgTo);
				String insertMsg = LoginFrame.connection.getUser().split("@")[0] + "  " + DateUtil.format3(new Date()) + "\n    " + msg;
				if(intsertMsg(insertMsg, 1)){
					try {
						System.out.println("Message sent out successfully :" + msg);
						//Message was successfully sent out. Cleat the textfield.
						clearInputField();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					//TODO This part show be changed to a message box to inform user of instruction.
					System.out.println("Message sent out failed.");
				}
			} catch (NotConnectedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}else{
			//TODO This part show be changed to a message box to inform user of instruction.
			System.out.println("The sending message cannot be empty.");
		}
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
	
	/**
	 * Insert the message to the ChatArea
	 * @param msg, msgType
	 * 		msgType=0 means from, msgType=1 means to, msgType=xxxx...TBD
	 * @return whether the operation is success.
	 */
	public static boolean intsertMsg(String msg, int msgType) {
		boolean result = false;
		try {
//			msg.replaceAll("\\r\\n", "\\r\\n\\t");
			StyledDocument doc = textPane.getStyledDocument();
			// Set font.
			SimpleAttributeSet attr = new SimpleAttributeSet();
			if(msgType == 0)
				StyleConstants.setForeground(attr,Color.red);
			if(msgType == 1)
				StyleConstants.setForeground(attr,Color.BLUE);
			// insert String to the ChatArea.
			doc.insertString(doc.getLength(), msg + "\n", attr);
			
			result = true;
			//Move to the top down.
			textPane.selectAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Clear the input message of the input textfield.
	 * @throws Exception
	 */
	public static void clearInputField() throws Exception {
		if(textMessage != null) {
			textMessage.setText("");
		}
	}
	
	/**
	 * Create the dialog.
	 */
	public void emojiDialog() {
		final JPanel contentPanel = new JPanel();
		
		emojiDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		emojiDialog.setUndecorated(true);
		
		emojiDialog.setSize(400, 240);
		emojiDialog.getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 250, 240));
		contentPanel.setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		emojiDialog.getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(null);
		contentPanel.add(scrollPane, BorderLayout.CENTER);
	}
	
	/**
	 * show the emoji dialog relative to the facial button (above the facial btn).
	 * @param btn
	 */
	public void showEmojiDialog(JButton btn){
		if(isEmojiOpen){//if the dialog is shown, hide it.
			emojiDialog.setVisible(false);
			isEmojiOpen = false;
		}else{// if the dialog is hidden, show it.
			emojiDialog.setLocationRelativeTo(btn);
			emojiDialog.setLocation((int)(emojiDialog.getLocation().getX() + emojiDialog.getWidth()/2 - btn.getWidth()/2 + 2), (int)(emojiDialog.getLocation().getY() - emojiDialog.getHeight()/2 - btn.getHeight()/2 - 2));
			emojiDialog.setVisible(true);
			isEmojiOpen = true;
		}
	}
	
	/**
	 * Move the component to the center of the screen.
	 * @param component
	 */
	public static void setLocationCenter(Component component) {
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  
	    Dimension compSize = component.getSize();  
	    if (compSize.height > screenSize.height) {  
	        compSize.height = screenSize.height;  
	    }  
	    if (compSize.width > screenSize.width) {  
	        compSize.width = screenSize.width;  
	    }  
	    component.setLocation((screenSize.width - compSize.width) / 2,  
	            (screenSize.height - compSize.height) / 2);  
	}
	
	public static void sendShake(){
		Message shakeMsg = new Message();
		shakeMsg.setType(Message.Type.headline);
		shakeMsg.setBody("Shake");
		try {
			chat.sendMessage(shakeMsg);
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Shake the chatting window.
	 */
	public void shakeFrame() {
		int x = ChatFrame.this.getX();
		int y = ChatFrame.this.getY();
		for (int i = 0; i < 20; i++) {
			if ((i & 1) == 0) {
				x += 3;
				y += 3;
			} else {
				x -= 3;
				y -= 3;
			}
			ChatFrame.this.setLocation(x, y);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	//KeyListener interface
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_ENTER && e.isControlDown()){
			System.out.println("Ctrl+Enter pressed");
			sendMsg();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	//KeyListener interface
}
