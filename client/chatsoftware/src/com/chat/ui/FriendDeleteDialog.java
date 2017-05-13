package com.chat.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.Collection;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.SmackException.NotLoggedInException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;

import com.llj.network.SeverConnection;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FriendDeleteDialog extends JFrame {

	private JPanel contentPane;
	private static Roster roster = null;
	private static JList listFriends = null;
	private static AbstractXMPPConnection connection = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FriendDeleteDialog frame = new FriendDeleteDialog(null);
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
	public FriendDeleteDialog(AbstractXMPPConnection conn) {
		connection = conn;
		setIconImage(Toolkit.getDefaultToolkit().getImage(FriendDeleteDialog.class.getResource("/Icons16/chat.png")));
		setTitle("Delete Friends");
		setBounds(100, 100, 228, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btnCancel = new JButton("Cancel");
		panel.add(btnCancel);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(listFriends.getSelectedValue() != null){
					System.out.println("Delete a friend : " + listFriends.getSelectedValue());
					deleteAFriend();
				}else{
					JOptionPane.showMessageDialog(null, "Please select a friend first!", "Warning", JOptionPane.WARNING_MESSAGE); 
				}
			}
		});
		panel.add(btnDelete);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		initFriends();
		scrollPane.setViewportView(listFriends);
	}
	
	public static void initFriends(){
		listFriends = new JList();
		DefaultListModel listModel = new DefaultListModel();
		String[] friends = {"Friend1","Friend2","Friend3","Friend4","Friend5","Friend6","Friend7","Friend8","Friend9","Friend10","Friend11","Friend12","Friend13","Friend14","Friend15","Friend16","Friend17","Friend18","Friend19","Friend20","Friend21","Friend22","Friend23","Friend24","Friend25","Friend26","Friend27","Friend28"};
		if(connection != null){
			roster = Roster.getInstanceFor(connection);
			if(roster != null){
				Collection<RosterEntry> entries = roster.getEntries();
				if(!entries.isEmpty()){
					friends = new String[entries.size()];
				}
				int i=0;
				for (RosterEntry entry : entries) {
					listModel.add(i++, entry.getName());
				}
				listFriends.setModel(listModel);;
			}
		}else{
			for(int i=0;i<friends.length;i++){
				listModel.add(i, friends[i]);
			}
			listFriends.setModel(listModel);;
		}
	}
	
	public static void deleteAFriend(){
		if(listFriends != null){
			if(roster == null && connection != null){
				roster = Roster.getInstanceFor(connection);
			}
			if(roster != null){
				String to = (String)listFriends.getSelectedValue() + "@" + SeverConnection.xmppDomain;
				RosterEntry entry = roster.getEntry(to);
				if(entry != null){
					Presence p = new Presence(Presence.Type.unsubscribe);
					p.setTo(to);
					try {
						//Send unsubscribe to the friend to break up the friendship.
						connection.sendStanza(p);
						//Remove the friend from my friend list.
						roster.removeEntry(entry);
						
						Thread.sleep(1000);
						//Remove the friend from the friend UI list.
						((DefaultListModel)listFriends.getModel()).removeElement(listFriends.getSelectedValue());
						//Refresh the friend tree on the ChatDialog.
						ChatFrame.refreshFriendTree();
					} catch (NotConnectedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NotLoggedInException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoResponseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (XMPPErrorException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			if(connection == null){
				((DefaultListModel)listFriends.getModel()).removeElement(listFriends.getSelectedValue());
			}
		}
	}

}
