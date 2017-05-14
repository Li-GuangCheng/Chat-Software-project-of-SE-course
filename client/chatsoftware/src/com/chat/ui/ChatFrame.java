package com.chat.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultTreeModel;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.filetransfer.FileTransfer.Status;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.translate.demo.TransApi;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.SynthesizerListener;
import com.llj.network.SeverConnection;
import com.llj.util.DateUtil;
import com.llj.util.RandomUtil;

import llj.testcode.ScreenCapture;

public class ChatFrame extends JFrame implements KeyListener {

	//Baidu translate API keys
	private static final String APP_ID = "20170514000047354";
    private static final String SECURITY_KEY = "povvc119uWhgvDB4YpIq";
    private static TransApi api = new TransApi(APP_ID, SECURITY_KEY);
	
	private JPanel contentPane;
	//textPane is the record about the textfield. 
	//textMessage is the input textfidld.
	private static JTextPane textPane, textMessage;
	private static JLabel lblChattingWith;
	private static JDialog emojiDialog = new JDialog();;
	private static boolean isEmojiOpen = false;
	private static ChatManager chatManager = null;// ChatManager used to create Chat.
	private static Roster roster = null;// Friends list
	private static FileTransferManager fileTransferManager = null;
	private static OutgoingFileTransfer fileTransfer = null;
	private static Chat chat = null;
	private static String toJID = "";// The selected person's name from the friend tree.
	private static JTree friendTree;
	private static JScrollPane scrollPane_2;
	private static JComboBox comboLanguage;//Language combobox
	private static JCheckBox chckbxTranslate;//Translate checkbox
	private static String personVoice = "xiaoyan";// Default using xiaoyan's voice.

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
		
		SpeechUtility.createUtility( SpeechConstant.APPID +"=59186de5");
		
		initManager(ChatFrame.this);
//		chat = chatWith("lh");
		
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
		if(LoginFrame.username != null){
			nickName.setText(LoginFrame.username);
		}
		panel_13.add(nickName);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/search.png")));
		panel_13.add(btnSearch);
		
		JButton headIcon = new JButton("");
		headIcon.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons32/head.png")));
		panel_11.add(headIcon, BorderLayout.WEST);
		
		//Friend Tree
		scrollPane_2 = new JScrollPane();
		panel.add(scrollPane_2, BorderLayout.CENTER);
		friendTree = new JTree();
		initFriendTree(friendTree, scrollPane_2);
		friendTree.addTreeSelectionListener(new FriendTreeSelectListener(friendTree));
		
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
		
		lblChattingWith = new JLabel("   Chatting with Bob");
		lblChattingWith.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 19));
		panel_14.add(lblChattingWith, BorderLayout.CENTER);
		
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
		
		//Add a friend
		JMenuItem mntmAddFriend = new JMenuItem("Add Friend");
		mntmAddFriend.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/add-user.png")));
		mntmAddFriend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FriendAddDialog frame = new FriendAddDialog(LoginFrame.connection);
				ChatFrame.setLocationCenter(frame);
				frame.setVisible(true);
			}
		});
		menu.add(mntmAddFriend);
		
		//Delete Friends
		JMenuItem mntmDeleteFriend = new JMenuItem("Delete Friend");
		mntmDeleteFriend.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/remove_user.png")));
		mntmDeleteFriend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FriendDeleteDialog frame = new FriendDeleteDialog(LoginFrame.connection);
				ChatFrame.setLocationCenter(frame);
				frame.setVisible(true);
			}
		});
		menu.add(mntmDeleteFriend);
		
		JMenu mnSettings = new JMenu("Settings...");
		mnSettings.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/set.png")));
		menu.add(mnSettings);
		
		JMenuItem menuItem = new JMenuItem("普通话中/英-青年女声(小燕)");
		menuItem.addActionListener(new VoiceSelectionListener());
		mnSettings.add(menuItem);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("普通话中/英-青年男声(小宇)");
		mntmNewMenuItem.addActionListener(new VoiceSelectionListener());
		mnSettings.add(mntmNewMenuItem);
		
		JMenuItem menuItem_4 = new JMenuItem("普通话-童年男声(小新)");
		menuItem_4.addActionListener(new VoiceSelectionListener());
		mnSettings.add(menuItem_4);
		
		JMenuItem menuItem_5 = new JMenuItem("普通话-童年女声(楠楠)");
		menuItem_5.addActionListener(new VoiceSelectionListener());
		mnSettings.add(menuItem_5);
		
		JMenuItem menuItem_1 = new JMenuItem("四川话-青年女生(小蓉)");
		menuItem_1.addActionListener(new VoiceSelectionListener());
		mnSettings.add(menuItem_1);
		
		JMenuItem menuItem_6 = new JMenuItem("粤语-青年男声(大龙)");
		menuItem_6.addActionListener(new VoiceSelectionListener());
		mnSettings.add(menuItem_6);
		
		JMenuItem menuItem_7 = new JMenuItem("粤语-青年女声(小梅)");
		menuItem_7.addActionListener(new VoiceSelectionListener());
		mnSettings.add(menuItem_7);
		
		JMenuItem menuItem_2 = new JMenuItem("东北话-青年女生(小芸)");
		menuItem_2.addActionListener(new VoiceSelectionListener());
		mnSettings.add(menuItem_2);
		
		JMenuItem menuItem_3 = new JMenuItem("河南话-青年男生(小坤)");
		menuItem_3.addActionListener(new VoiceSelectionListener());
		mnSettings.add(menuItem_3);
		
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
		panel_2.setPreferredSize(new Dimension(160, 0));
		panel_8.add(panel_2, BorderLayout.WEST);
		panel_2.setLayout(new GridLayout(1, 4, 0, 0));
		
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
				chooseFile();
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
		btnCapture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File picPath = new File("C:/temp");
		        if(!picPath.exists()){
		        	picPath.mkdir();
		        }
		        try {
		        	String fileName = DateUtil.format4(new Date()) + RandomUtil.generateString(4);
					File tempFile = new File("C:/temp", fileName+".png");  
					ScreenCapture capture = ScreenCapture.getInstance();  
					capture.captureImage();  
					JFrame frame = new JFrame();  
					JPanel panel = new JPanel();  
					panel.setLayout(new BorderLayout());  
					JLabel imagebox = new JLabel();  
					panel.add(BorderLayout.CENTER, imagebox);  
					imagebox.setIcon(capture.getPickedIcon());  
					capture.saveToFile(tempFile);  
					capture.captureImage();  
					imagebox.setIcon(capture.getPickedIcon());  
					frame.setContentPane(panel);  
					frame.setSize(400, 300);  
					frame.show();
					
					receiveFile(null, 0, tempFile.getName());
					sendFile(tempFile);
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
			}
		});
		btnCapture.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/cut.png")));
		panel_2.add(btnCapture);
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				receiveFile(null, 0, null);
			}
		});
		button.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/font.png")));
//		panel_2.add(button);
		
		JPanel panel_7 = new JPanel();
		panel_7.setPreferredSize(new Dimension(80, 0));
		panel_8.add(panel_7, BorderLayout.EAST);
		panel_7.setLayout(new GridLayout(1, 2, 0, 0));
		
		JButton btnPhone = new JButton("");
		btnPhone.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/phone.png")));
		panel_7.add(btnPhone);
		
		JButton btnVideo = new JButton("");
		btnVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendVideo();
			}
		});
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
		
		chckbxTranslate = new JCheckBox("Translate");
		panel_12.add(chckbxTranslate);
		
		JButton btnSend = new JButton("Send");
		// the send function of the Send Button.
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendMsg();
			}
		});
		
		comboLanguage = new JComboBox();
		{
			comboLanguage.addItem(new LanguageItem("zh", "Chinese"));
			comboLanguage.addItem(new LanguageItem("cht", "Chinese(Triditional)"));
			comboLanguage.addItem(new LanguageItem("en", "English"));
			comboLanguage.addItem(new LanguageItem("yue", "Cantonese"));
			comboLanguage.addItem(new LanguageItem("jp", "Japanese"));
			comboLanguage.addItem(new LanguageItem("kor", "Korean"));
			comboLanguage.addItem(new LanguageItem("fra", "French"));
			comboLanguage.addItem(new LanguageItem("de", "German"));
		}
		panel_12.add(comboLanguage);
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
			fileTransferManager = FileTransferManager.getInstanceFor(LoginFrame.connection);
			//Register the file receive listener.
			fileTransferManager.addFileTransferListener(new FileTransferListener(){
				@Override
				public void fileTransferRequest(FileTransferRequest request) {
					// TODO Auto-generated method stub
					System.out.println("Incoming a file. " + request.getRequestor());
					receiveFile(request, 1, null);
				}
				
			});
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
//					System.out.println(entry.getUser());
					FriendTreeNode childj = new FriendTreeNode(entry.getName());
					childj.setNickname(entry.getName());
					childj.setImg(new ImageIcon(TestFriendTree.class.getResource("/Icons32/man.png")));
					childj.setSignature("I am " + entry.getName());
					if(!entry.getGroups().isEmpty()){
//						System.out.println(entry.getGroups().get(0).getName());
						childj.setGroup(entry.getGroups().get(0).getName());
					}else{
						//Default group is Blue.
						childj.setGroup("Blue");
					}
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
	 * Refresh the friend tree when adding a friend or delete a friend.
	 */
	public static void refreshFriendTree() {
		System.out.println("refreshFriendTree() called.");
		scrollPane_2.remove(friendTree);
		if(roster != null){
			Collection<RosterEntry> entries = roster.getEntries();
			friendTree = new JTree();
			friendTree.removeAll();
//			System.out.println("size="+entries.size());
			FriendTreeNode root = new FriendTreeNode("Root1");
			root.setNickname("Root2");
			root.setImg(new ImageIcon(TestFriendTree.class.getResource("/Icons16/user.png")));
			
			for (int i = 0; i < 1; i++) {
				FriendTreeNode child = new FriendTreeNode("My Friends");
				child.setNickname("My Friends");
				//Add my friends to the tree.
				for (RosterEntry entry : entries) {
//					System.err.println(entry.getUser());
					FriendTreeNode childj = new FriendTreeNode(entry.getName());
					childj.setNickname(entry.getName());
					childj.setImg(new ImageIcon(TestFriendTree.class.getResource("/Icons32/man.png")));
					childj.setSignature("I am " + entry.getName());
					if(!entry.getGroups().isEmpty()){
						childj.setGroup(entry.getGroups().get(0).getName());
					}else{
						//Default group is Blue.
						childj.setGroup("Blue");
					}
					child.addChild(childj);
				}
				root.addChild(child);
			}
			
			DefaultTreeModel jMode = new DefaultTreeModel(root);
			jMode.reload();
			friendTree.setModel(jMode);
			friendTree.updateUI();
			friendTree.setCellRenderer(new FriendTreeRender());
			friendTree.addTreeSelectionListener(new TreeSelectionListener() {
				
				@Override
				public void valueChanged(TreeSelectionEvent e) {
					// TODO Auto-generated method stub
					treeItemSelection(friendTree); 
				}
			});
			friendTree.setRootVisible(false);
			friendTree.setToggleClickCount(1);//Expand the tree with 1 time click.
			scrollPane_2.setViewportView(friendTree);
			System.out.println("Friend Tree reloaded.");
		}else{
			System.out.println("Get roster from connection errors.");
		}
	}
	
	final class FriendTreeSelectListener implements TreeSelectionListener{
		
		private JTree tree;
		
		public FriendTreeSelectListener(JTree tree){
			this.tree = tree;
		}
		
		@Override
		public void valueChanged(TreeSelectionEvent arg0) {
			// TODO Auto-generated method stub
			treeItemSelection(tree);
			return;
		}
	}
	
	public static void treeItemSelection(JTree tree){
		FriendTreeNode node = (FriendTreeNode) tree.getLastSelectedPathComponent();
        if(node.isLeaf()){
        	System.out.print("Select ");
        	System.err.println(node.getNickname());
        	//change the chat with new UserJID
        	chat = chatWith(node.getNickname());
        	//change the toJID
        	toJID = node.getNickname();
        	//initialize the fileTransfer
        	fileTransfer = fileTransferManager
    				.createOutgoingFileTransfer(toJID+"@"+SeverConnection.xmppDomain+"/Smack");
        	//Empty the ChatRecord.
        	textPane.setText("");
        	lblChattingWith.setText("   Chatting with " + node.getNickname());
            return;  
        } 
	}
	
	public static void createChatWhenMsgComing(String userBareJID) {
		String currentUser = lblChattingWith.getText().toString();
		currentUser = currentUser.substring("   Chatting with ".length(), currentUser.length());
		if(!currentUser.equals(userBareJID)) {
			//change the chat with new UserJID
        	chat = chatWith(userBareJID);
        	//change the toJID
        	toJID = userBareJID;
        	//initialize the fileTransfer
        	fileTransfer = fileTransferManager
    				.createOutgoingFileTransfer(toJID+"@"+SeverConnection.xmppDomain+"/Smack");
        	//Empty the ChatRecord.
        	textPane.setText("");
        	lblChattingWith.setText("   Chatting with " + userBareJID);
		}
	}
	
	/**
	 * Send a message.
	 */
	public void sendMsg(){
		String msg = textMessage.getText();
		JSONObject translateRst = null;
		if(chckbxTranslate != null && chckbxTranslate.isSelected()){
			translateRst = translateMsg(msg);
		}
		if(!StringUtils.isEmpty(msg)){
			Message msgTo = new Message();
			if(translateRst != null){
//				msg = translateRst.getString("dst") + "("+translateRst.getString("src")+")";
				msg = translateRst.getString("dst");
			}
			msgTo.setBody(msg);
			try {
				chat.sendMessage(msgTo);
				String userTime = "";
				String message = "";
				if(translateRst != null){
//					insertMsg = LoginFrame.connection.getUser().split("@")[0] + "  " + DateUtil.format3(new Date()) + "\n    " + translateRst.getString("src") + "("+translateRst.getString("dst")+")";
					userTime = LoginFrame.connection.getUser().split("@")[0] + "  " + DateUtil.format3(new Date()) + "\n    ";
					message = translateRst.getString("src");
				}else{
					userTime = LoginFrame.connection.getUser().split("@")[0] + "  " + DateUtil.format3(new Date()) + "\n    ";
					message = msg;
				}
				if(intsertMsg(userTime, message, 1)){
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
	
	/**
	 * Translate the msg to the selected Language.
	 * @param msg
	 * @return
	 */
	public static JSONObject translateMsg(String msg){
		JSONObject result = null;
        try {
        	LanguageItem selectedLan = (LanguageItem) comboLanguage.getSelectedItem();
        	String lan = "en";//Default language is language.
        	if(selectedLan != null){
        		lan = selectedLan.getValue();
        		System.out.println("Translate to " + selectedLan.getText());
        	}
			JSONObject jsonObj = JSONObject.parseObject(api.getTransResult(msg, "auto", lan));
			JSONArray jsonArray = jsonObj.getJSONArray("trans_result");
			result = jsonArray.getJSONObject(0);
			System.out.println(result.toJSONString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
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
	public static boolean intsertMsg(String userTime, String msg, int msgType) {
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
			// insert the userTime String first
			if(userTime != null){
				doc.insertString(doc.getLength(), userTime, attr);
			}
			doc.insertString(doc.getLength(), msg + "  ", attr);
			
			JButton btnVoice = new JButton();
			btnVoice.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/voice.png")));
			btnVoice.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
//					System.out.println("play the sound");
					SpeechSynthesizer mTts= SpeechSynthesizer.createSynthesizer();
					mTts.setParameter(SpeechConstant.VOICE_NAME, personVoice);//设置发音人
					mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
					mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
					
					mTts.startSpeaking(msg, mSynListener);
				}
			});
			textPane.setCaretPosition(doc.getLength());
			if(userTime != null){
				textPane.insertComponent(btnVoice);
			}
			
			doc.insertString(doc.getLength(), "\n", attr);// Insert a new line.
			
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
	 * @param iconPath
	 * @param msgType
	 * 		msgType=0 means from, msgType=1 means to, msgType=xxxx...TBD
	 * @return
	 */
	public static boolean insertIcon(String iconPath, int msgType, Message msg) {
		boolean result = false;
		try {
			StyledDocument doc = textPane.getStyledDocument();
			// Set font.
			SimpleAttributeSet attr = new SimpleAttributeSet();
			if(msgType == 0)
				StyleConstants.setForeground(attr,Color.red);
			if(msgType == 1)
				StyleConstants.setForeground(attr,Color.BLUE);
			// insert String to the ChatArea.
			if(msg == null){
				String msgInfo = "me "  + "  " + DateUtil.format3(new Date());
				doc.insertString(doc.getLength(), msgInfo + "\n    ", attr);
			}else{
				String msgInfo = "";
				if(msgType == 0){
					 msgInfo = LoginFrame.connection.getUser().split("@")[0] + "  " + DateUtil.format3(new Date());
				}
				if(msgType == 1){
					 msgInfo = msg.getFrom().split("@")[0] + "  " + DateUtil.format3(new Date());
				}
				
				doc.insertString(doc.getLength(), msgInfo + "\n    ", attr);
			}
			textPane.setCaretPosition(doc.getLength());
			textPane.insertIcon(new ImageIcon(ChatFrame.class.getResource(iconPath)));
			doc.insertString(doc.getLength(), "\n", attr);//insert a line
			result = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static boolean receiveFile(FileTransferRequest request, int msgType, String filename){
		boolean result = false;
		
		SimpleAttributeSet attr = new SimpleAttributeSet();
		StyledDocument doc = textPane.getStyledDocument();
		
		JLabel lblReceiveFile = new JLabel();
		if(request != null){
			lblReceiveFile.setText(request.getFileName() + "(" + request.getFileSize() + ")");
		}else{
			if(msgType == 0 && filename != null){
				lblReceiveFile.setText(filename);
			}else{
				lblReceiveFile.setText("File name");
			}
		}
		lblReceiveFile.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons64/file.png")));
		
		textPane.setCaretPosition(doc.getLength());
		textPane.insertComponent(lblReceiveFile);
		
		try {
			doc.insertString(doc.getLength(), "   ", attr);//insert a line
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JButton btnAccept = new JButton("Receive");
		JButton btnReject = new JButton("Reject");
		
		btnAccept.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/tick.png")));
		if(request != null){
			//If there comes a file receiving request, accept it.
			btnAccept.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					JFileChooser jfc=new JFileChooser();
					jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					jfc.showDialog(new JLabel(), "Save");
					jfc.setDialogTitle("Save to a Directory");
					File file=jfc.getSelectedFile();
					if(file != null) {
						if(file.isDirectory()){
							System.out.println("Folder:"+file.getAbsolutePath());
							IncomingFileTransfer transfer = request.accept();
							try {
								transfer.recieveFile(new File(file.getAbsolutePath() + "\\" + request.getFileName()));
								//Listen the progress of receiving a file.
								new Thread(){  
				                    @Override  
				                    public void run(){  
				                        long startTime = System.currentTimeMillis();  
				                        while(!transfer.isDone()){  
				                            if (transfer.getStatus().equals(Status.error)){  
				                                System.out.println(DateUtil.format2(new Date())+"error!!!"+transfer.getError());  
				                            }else{  
				                                double progress = transfer.getProgress();  
				                                progress*=100;  
				                                System.out.println(DateUtil.format2(new Date())+" status="+transfer.getStatus());  
				                                System.out.println(DateUtil.format2(new Date())+" progress="+progress+"%");  
				                            }  
				                            try {  
				                                Thread.sleep(1000);
				                            } catch (InterruptedException e) {  
				                                e.printStackTrace();  
				                            }  
				                        }
				                        if(transfer.isDone()){
				                        	JOptionPane.showMessageDialog(null, "Receiving " + request.getFileName() + " successfully.\n It is stored at " + file.getAbsolutePath(), "Message", JOptionPane.INFORMATION_MESSAGE); 
				                        	btnAccept.setEnabled(false);
				                        	btnReject.setEnabled(false);
				                        }
				                        System.out.println("used "+((System.currentTimeMillis()-startTime)/1000)+" seconds  ");  
				                    }  
				                }.start(); 
							} catch (SmackException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else if(file.isFile()){
							System.out.println("File:"+file.getAbsolutePath());
							sendFile(file);
						}
					}
					
				}
			});
		}
		textPane.setCaretPosition(doc.getLength());
		//Only the receiver can see the Accept Button.
		if(msgType == 1){
			textPane.insertComponent(btnAccept);
		}
		
		try {
			doc.insertString(doc.getLength(), "   ", attr);//insert a line
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		btnReject.setIcon(new ImageIcon(ChatFrame.class.getResource("/Icons16/cross.png")));
		if(request != null){
			//If there comes a file receiving request, reject it.
			btnReject.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					try {
						request.reject();
						btnAccept.setEnabled(false);
                    	btnReject.setEnabled(false);
					} catch (NotConnectedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
		textPane.setCaretPosition(doc.getLength());
		//Only the receiver can see the Reject Button.
		if(msgType == 1){
			textPane.insertComponent(btnReject);
		}
		
		try {
			doc.insertString(doc.getLength(), "\n", attr);//insert a line
		} catch (BadLocationException e) {
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
		contentPanel.setLayout(new GridLayout(5, 9));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		emojiDialog.getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		emojiDialog.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {}
			
			@Override
			public void windowIconified(WindowEvent e) {}
			
			@Override
			public void windowDeiconified(WindowEvent e) {}

			@Override
			public void windowClosing(WindowEvent e) {}
			
			@Override
			public void windowClosed(WindowEvent e) {}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				//When the window is deactived, set isEmojiOpen to false.
				isEmojiOpen = false;
			}

			@Override
			public void windowActivated(WindowEvent e) {
				//When the window is deactived, set isEmojiOpen to true.
				isEmojiOpen = true;
			}
		});
		
		//Initialize the expressions.
		JLabel[] emojis = new JLabel[36]; /*放表情*/
		for(int i = 0; i<36; i++){
			emojis[i] = new JLabel();
			emojis[i].setIcon(new ImageIcon(ChatFrame.class.getResource("/ayemoji/Expression_"+(i+1)+".png")));
			emojis[i].setHorizontalAlignment(SwingConstants.CENTER);
			emojis[i].setBorder(BorderFactory.createLineBorder(new Color(225,225,225), 1));
			emojis[i].setToolTipText("Expression_"+(i+1));
			
			emojis[i].addMouseListener(new MouseAdapter() {
				//When choosing an expression
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == 1) {
						 JLabel cubl = (JLabel)(e.getSource());
						 String iconName = cubl.getToolTipText();
						 String iconPath = "/ayemoji/" + iconName + ".png";
//						 System.out.println("iconName is : " +iconName);
						 if(chat != null){
							 //Construct a message sending an icon.
							 Message iconMsg = new Message();
							 iconMsg.setSubject("Facial");
							 iconMsg.setTo(toJID+"@"+SeverConnection.xmppDomain);
							 iconMsg.setBody(iconPath);
							 iconMsg.setFrom(LoginFrame.connection.getUser());
							 try {
								chat.sendMessage(iconMsg);
								insertIcon(iconPath, 0, iconMsg);
							} catch (NotConnectedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						 } else {
							 insertIcon(iconPath, 0, null);
						 }
						 cubl.setBorder(BorderFactory.createLineBorder(new Color(225,225,225), 1));
						 emojiDialog.setVisible(false);
					}
				}

				//When moving the mouse on an expression
				@Override
				public void mouseEntered(MouseEvent e) {
					((JLabel) e.getSource()).setBorder(BorderFactory.createLineBorder(Color.BLUE));
				}

				//When moving the mouse out off an expression
				@Override
				public void mouseExited(MouseEvent e) {
					((JLabel) e.getSource()).setBorder(BorderFactory.createLineBorder(new Color(225, 225, 225), 1));
				}
			});
			
			contentPanel.add(emojis[i]);
		}
		
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
	
	/**
	 * Send a Shake message to the user so that the user's frame can be shaked.
	 */
	public static void sendShake(){
		Message shakeMsg = new Message();
		shakeMsg.setSubject("Shake");
		shakeMsg.setBody("Shake");
		try {
			chat.sendMessage(shakeMsg);
			String time = DateUtil.format3(new Date());
			String insertMessage = "You shaked " + shakeMsg.getTo().toString().split("@")[0] +"'s dialog at " + time +".";
			intsertMsg(null, insertMessage, 1);
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void sendVideo() {
		Message videoMsg = new Message();
		videoMsg.setSubject("Video");
		videoMsg.setBody("https://appear.in/lljchat");
		try {
			chat.sendMessage(videoMsg);
			String time = DateUtil.format3(new Date());
			String insertMessage = "You want to have a video talk with " + videoMsg.getTo().toString().split("@")[0] +" at " + time +".";
			intsertMsg(null, insertMessage, 1);
			try {
				String cmdStr = "cmd /c start chrome https://appear.in/lljchat";  
				Runtime.getRuntime().exec(cmdStr);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {  
		            URI uri=new URI("https://appear.in/lljchat");  
		            Desktop.getDesktop().browse(uri);   
		        } catch (IOException e4) {  
		            e4.printStackTrace();  
		        } catch (URISyntaxException e4) {  
		            e4.printStackTrace();  
		        }  
			}  
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
	
	/**
	 * Select a file.
	 */
	public static void chooseFile() {
		JFileChooser jfc=new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		jfc.showDialog(new JLabel(), "Choose a file");
		jfc.setDialogTitle("Choose a file");
		File file=jfc.getSelectedFile();
		if(file != null) {
			if(file.isDirectory()){
				System.out.println("Folder:"+file.getAbsolutePath());
			}else if(file.isFile()){
				System.out.println("File:"+file.getAbsolutePath());
				receiveFile(null, 0, file.getName());
				sendFile(file);
			}
			System.out.println(jfc.getSelectedFile().getName());
		}
	}
	
	/**
	 * send a file to the user.
	 * @param file
	 */
	public static void sendFile(File file){
		try {
			//Every time when you want to send a file, you have to create a new FileTransfer.
			fileTransfer = fileTransferManager
    				.createOutgoingFileTransfer(toJID+"@"+SeverConnection.xmppDomain+"/Smack");
			fileTransfer.sendFile(file, "Send");
			Thread.sleep(2000);
			System.out.println("Status :: " + fileTransfer.getStatus() + " Error :: " + fileTransfer.getError() + " Exception :: " + fileTransfer.getException());
		} catch (SmackException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class VoiceSelectionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String selectedVoice = e.getActionCommand();
			if(selectedVoice.equals("通话中/英-青年女声(小燕)")){
				personVoice = "xiaoyan";
			}
			if(selectedVoice.equals("普通话中/英-青年男声(小宇)")){
				personVoice = "xiaoyu";
			}
			if(selectedVoice.equals("普通话-童年男声(小新)")){
				personVoice = "vixx";
			}
			if(selectedVoice.equals("普通话-童年女声(楠楠)")){
				personVoice = "vinn";
			}
			if(selectedVoice.equals("四川话-青年女生(小蓉)")){
				personVoice = "vixr";
			}
			if(selectedVoice.equals("东北话-青年女生(小芸)")){
				personVoice = "vixyun";
			}
			if(selectedVoice.equals("河南话-青年男生(小坤)")){
				personVoice = "vixk";
			}
			if(selectedVoice.equals("粤语-青年男声(大龙)")){
				personVoice = "dalong";
			}
			if(selectedVoice.equals("粤语-青年女声(小梅)")){
				personVoice = "xiaomei";
			}
		}
	}
	
	//合成监听器
	private static SynthesizerListener mSynListener = new SynthesizerListener(){
	    //会话结束回调接口，没有错误时，error为null
	    public void onCompleted(SpeechError error) {}
	    //缓冲进度回调
	    //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
	    public void onBufferProgress(int percent, int beginPos, int endPos, String info) {}
	    //开始播放
	    public void onSpeakBegin() {}
	    //暂停播放
	    public void onSpeakPaused() {}
	    //播放进度回调
	    //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
	    public void onSpeakProgress(int percent, int beginPos, int endPos) {}
	    //恢复播放回调接口
	    public void onSpeakResumed() {}
		@Override
		public void onEvent(int arg0, int arg1, int arg2, int arg3, Object arg4, Object arg5) {
			// TODO Auto-generated method stub
			
		}
	};
	
	
	//KeyListener interface
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_ENTER && e.isControlDown()){
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
