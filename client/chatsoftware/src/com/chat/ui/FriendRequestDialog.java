package com.chat.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.SmackException.NotLoggedInException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;

public class FriendRequestDialog extends JFrame {

	private final JPanel contentPanel = new JPanel();
	private static JTextField textNickname;
	private static JLabel lblSomeone;
	private static JComboBox comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FriendRequestDialog dialog = new FriendRequestDialog(null, null);
			ChatFrame.setLocationCenter(dialog);
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FriendRequestDialog(AbstractXMPPConnection connection, Stanza stanza) {
		setTitle("Friend Request");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FriendRequestDialog.class.getResource("/Icons16/chat.png")));
		setBounds(100, 100, 409, 235);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(4, 1, 8, 8));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			{
				lblSomeone = new JLabel("Someone");
				//Set the label to the person who is asking to add a friend.
				if(stanza != null){
					lblSomeone.setText(stanza.getFrom().split("@")[0]);
				}
				lblSomeone.setFont(new Font("Microsoft Tai Le", Font.BOLD, 18));
				panel.add(lblSomeone);
			}
			{
				JLabel lblWantsToAdd = new JLabel(" wants to add you as his/her friend.");
				panel.add(lblWantsToAdd);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			{
				JLabel lblNickname = new JLabel("nickname:  ");
				panel.add(lblNickname);
			}
			{
				textNickname = new JTextField();
				//Set the TextField with the person's name who is asking to add a friend.
				if(stanza != null){
					textNickname.setText(stanza.getFrom().split("@")[0]);
				}
				panel.add(textNickname);
				textNickname.setColumns(15);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			{
				JLabel lblGroup = new JLabel("group:    ");
				panel.add(lblGroup);
			}
			{
				comboBox = new JComboBox();
				comboBox.setModel(new DefaultComboBoxModel(new String[] {"Red", "Yellow", "Blue", "Green", "Black", "Purple", "Orange"}));
				panel.add(comboBox);
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
				JButton cancelButton = new JButton("Reject");
				cancelButton.setIcon(new ImageIcon(FriendRequestDialog.class.getResource("/Icons16/cross.png")));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(!(connection == null || stanza == null)){
							if(stanza instanceof Presence){
								if(((Presence) stanza).getType() == Presence.Type.subscribe){
									Presence p = new Presence(Presence.Type.unsubscribe);
									p.setTo(stanza.getFrom());
									try {
										connection.sendStanza(p);
										System.err.println("Reject the request");
									} catch (NotConnectedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									
									//Delete the user from the roster.
									Roster roster = Roster.getInstanceFor(connection);
									if(roster != null) {
										RosterEntry entry = roster.getEntry(stanza.getFrom());
										try {
											if(entry != null)
												roster.removeEntry(entry);
											System.err.println("Remove " + stanza.getFrom() + " from the roster");
										} catch (NoResponseException e2) {
											// TODO Auto-generated catch block
											e2.printStackTrace();
										} catch (XMPPErrorException e2) {
											// TODO Auto-generated catch block
											e2.printStackTrace();
										} catch (NotConnectedException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										} catch (NotLoggedInException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									}
								}
							}
						} else {
							System.out.println("connection is null or the stanza is null");
						}
						
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			{
				JButton okButton = new JButton("Accept");
				okButton.setIcon(new ImageIcon(FriendRequestDialog.class.getResource("/Icons16/tick.png")));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(!(connection == null || stanza == null)){
							if(stanza instanceof Presence){
								if(((Presence) stanza).getType() == Presence.Type.subscribe){
									Presence p = new Presence(Presence.Type.subscribed);
									p.setTo(stanza.getFrom());
									try {
										connection.sendStanza(p);
										System.err.println("Accept the request");
										Roster roster = Roster.getInstanceFor(connection);
										if(roster != null) {
											try {
												String group = (String) comboBox.getSelectedItem();
												if(group == null){
													group = "Blue";
												}
												roster.createEntry(stanza.getFrom(), textNickname.getText().toString().trim(), new String[]{group});
												System.out.println("Add " + stanza.getFrom());
												ChatFrame.refreshFriendTree();
												FriendRequestDialog.this.dispose();
											} catch (NotLoggedInException | NoResponseException | XMPPErrorException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
												JOptionPane.showMessageDialog(null, "Adding a friend failed!", "Warnning", JOptionPane.WARNING_MESSAGE); 
											}
										}
									} catch (NotConnectedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
							}
						} else {
							System.out.println("connection is null or the stanza is null");
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
