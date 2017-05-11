package com.chat.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

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
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.search.ReportedData;
import org.jivesoftware.smackx.search.UserSearchManager;
import org.jivesoftware.smackx.xdata.Form;

import com.llj.network.SeverConnection;

public class FriendAddDialog extends JFrame {

	private JPanel contentPane = new JPanel();
	private static JTextField textUsername;
	private static JTextField textNickname;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FriendAddDialog frame = new FriendAddDialog(null);
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
	public FriendAddDialog(AbstractXMPPConnection connection) {
		setTitle("Add Friend");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FriendRequestDialog.class.getResource("/Icons16/chat.png")));
		setBounds(100, 100, 409, 235);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(4, 1, 8, 8));
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		
		JLabel lblAddANew = new JLabel("Add a new friend.");
		lblAddANew.setFont(new Font("宋体", Font.BOLD, 16));
		panel_2.add(lblAddANew);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		
		JLabel lblUsername = new JLabel("Username:   ");
		panel_3.add(lblUsername);
		
		textUsername = new JTextField();
		textUsername.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if(!StringUtils.isEmpty(textUsername.getText().toString())){
					textUsername.setText(textUsername.getText().toString().toLowerCase().trim());
					textNickname.setText(textUsername.getText().toString().trim());
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		panel_3.add(textUsername);
		textUsername.setColumns(15);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		
		JLabel lblNickname = new JLabel("Nickname:   ");
		panel_4.add(lblNickname);
		
		textNickname = new JTextField();
		panel_4.add(textNickname);
		textNickname.setColumns(15);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		
		JLabel lblGroup = new JLabel("Group:   ");
		panel_1.add(lblGroup);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Red", "Yellow", "Blue", "Green", "Black", "Purple", "Orange"}));
		panel_1.add(comboBox);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = textUsername.getText().toString();
				if(!StringUtils.isEmpty(username)){
					if (connection != null) {
						try {
							UserSearchManager userSearchManager = new UserSearchManager(connection);
							Roster roster = Roster.getInstanceFor(connection);
							Form searchForm = userSearchManager.getSearchForm("search." + connection.getServiceName());
							Form answerForm = searchForm.createAnswerForm();
							answerForm.setAnswer("Username", true);
							answerForm.setAnswer("search", username);
							ReportedData data = userSearchManager.getSearchResults(answerForm, "search." + connection.getServiceName());
							
							List<ReportedData.Row> it = null;  
					        if (null != data && data.getRows() != null) {  
					            it = data.getRows();
//					            System.out.println("Have results=" + it.isEmpty());
					            for(int i=0; i<it.size(); i++){
					            	System.out.println(it.get(i).getValues("Username").get(0));
					            }
//					            Row row = null;  
//					            while (it.hasNext())  
//					            {  
//					                row = it.next();  
//					                String name = row.getValues("Username").next().toString();  
//					                result += name;  
//					            }  
					        }
					        
					        RosterEntry entry = roster.getEntry(username + "@" + SeverConnection.xmppDomain);
					        System.out.println(username + "@" + SeverConnection.xmppDomain);
					        System.out.println("it.isEmpty()=" + it.isEmpty()+ "\tentry=" + entry);
					        
					        if(it.isEmpty()){
					        	JOptionPane.showMessageDialog(null, "There is no user named " + username + "!\nPlease check.", "Warnning", JOptionPane.WARNING_MESSAGE); 
					        }else{
					        	if(entry != null){
					        		JOptionPane.showMessageDialog(null, username + " is already in your friend list!", "Warnning", JOptionPane.WARNING_MESSAGE);
					        	} else {
					        		if(roster != null) {
					        			String group = (String) comboBox.getSelectedItem();
					        			if(group == null){
					        				group = "Blue";
					        			}
					        			try {
											roster.createEntry(username+"@"+connection.getServiceName(), textNickname.getText().toString().trim(), new String[]{group});
										} catch (NotLoggedInException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										System.out.println("Add " + username);
										ChatFrame.refreshFriendTree();
										FriendAddDialog.this.dispose();
					        		}
					        	}
					        }
					        
						} catch (NoResponseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (XMPPErrorException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (NotConnectedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		okButton.setIcon(new ImageIcon(FriendRequestDialog.class.getResource("/Icons16/tick.png")));
		buttonPane.add(okButton);
	}

}
