package com.chat.ui;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTree;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JScrollPane;

public class TestFriendTree {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestFriendTree window = new TestFriendTree();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestFriendTree() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 627, 465);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		JTree tree = new JTree();
//		FriTreeNode root = new FriTreeNode("Root1");
//		root.setuName("Root2");
//		root.setImg(new ImageIcon(TestFriendTree.class.getResource("/Icons16/user.png")));
//		
//		for (int i = 0; i < 5; i++) {
//			FriTreeNode child = new FriTreeNode("Child" + i);
//			child.setuName("Child" + i);
//			child.setImg(new ImageIcon(TestFriendTree.class.getResource("/Icons16/user.png")));
//			child.setText("I am child" + i);
//			for(int j = 0; j< 6; j++){
//				FriTreeNode childj = new FriTreeNode("Child" + i + "" + j);
//				childj.setuName("Child" + i + "" + j);
//				childj.setImg(new ImageIcon(TestFriendTree.class.getResource("/Icons64/man.png")));
//				childj.setText("I am child" + i + "" + j);
//				child.addchild(childj);
//			}
//			root.addchild(child);
//		}
//		
//		DefaultTreeModel jMode = new DefaultTreeModel(root);
//		tree.setModel(jMode);
//		tree.setCellRenderer(new FriTreeRender());
//		
//		tree.setToggleClickCount(1); //设置展开节点之前的鼠标单击数为1
		
		
		JTree tree = new JTree();
		FriendTreeNode root = new FriendTreeNode("Root1");
		root.setNickname("Root2");
		root.setImg(new ImageIcon(TestFriendTree.class.getResource("/Icons16/user.png")));
		
		for (int i = 0; i < 5; i++) {
			FriendTreeNode child = new FriendTreeNode("Child" + i);
			child.setNickname("Child" + i);
			child.setImg(new ImageIcon(TestFriendTree.class.getResource("/Icons16/user.png")));
			child.setSignature("I am child" + i);
			for(int j = 0; j< 6; j++){
				FriendTreeNode childj = new FriendTreeNode("Child" + i + "" + j);
				childj.setNickname("Child" + i + "" + j);
				childj.setImg(new ImageIcon(TestFriendTree.class.getResource("/Icons32/man.png")));
				childj.setSignature("I am child" + i + "" + j);
				child.addChild(childj);
			}
			root.addChild(child);
		}
		
		DefaultTreeModel jMode = new DefaultTreeModel(root);
		tree.setModel(jMode);
		tree.setCellRenderer(new FriendTreeRender());
		tree.setRootVisible(false);
//		tree.setToggleClickCount(1); //设置展开节点之前的鼠标单击数为1
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(200, 0));
		frame.getContentPane().add(scrollPane, BorderLayout.WEST);
		scrollPane.setViewportView(tree);
	}

}
