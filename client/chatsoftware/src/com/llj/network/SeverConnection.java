package com.llj.network;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

import javax.swing.JOptionPane;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.SmackException.NotLoggedInException;
import org.jivesoftware.smack.StanzaListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.parsing.ExceptionLoggingCallback;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.packet.RosterPacket;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smack.util.TLSUtils;
import org.jivesoftware.smackx.chatstates.ChatState;
import org.jivesoftware.smackx.chatstates.ChatStateListener;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferNegotiator;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.jxmpp.util.XmppStringUtils;

import com.chat.ui.ChatFrame;
import com.chat.ui.FriendRequestDialog;

public class SeverConnection {
	public static String severDNS = "ec2-54-254-130-230.ap-southeast-1.compute.amazonaws.com";
	public static int severPort = 5222;
	public static String xmppDomain = "ip-172-31-21-142.ap-southeast-1.compute.internal";

	public static AbstractXMPPConnection getConnection(String username, String pass) {
		AbstractXMPPConnection connection = null;
		// Create a connection to the server.
		XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder()
				.setUsernameAndPassword(username, pass)
				.setHost(severDNS)
				.setPort(severPort)
				.setServiceName(xmppDomain);
		try {
			TLSUtils.acceptAllCertificates(builder);// Trust all certificates
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			e.printStackTrace();
		}
		try {
			XMPPTCPConnectionConfiguration configuration = builder.build();
			connection = new XMPPTCPConnection(configuration);
			connection.setParsingExceptionCallback(new ExceptionLoggingCallback());
			connection.addConnectionListener(new ConnectionListener() {
				@Override
				public void reconnectionSuccessful() {
					// TODO Auto-generated method stub
					System.out.println("reconnectionSuccessful()");
				}
				
				@Override
				public void reconnectionFailed(Exception arg0) {
					// TODO Auto-generated method stub
					System.out.println("reconnectionFailed()");
				}
				
				@Override
				public void reconnectingIn(int arg0) {
					// TODO Auto-generated method stub
					System.out.println("reconnectingIn()");
				}
				
				@Override
				public void connectionClosedOnError(Exception arg0) {
					// TODO Auto-generated method stub
					System.out.println("connectionClosedOnError()");
				}
				
				@Override
				public void connectionClosed() {
					// TODO Auto-generated method stub
					System.out.println("Connection closed.");
				}
				
				@Override
				public void connected(XMPPConnection arg0) {
					// TODO Auto-generated method stub
					System.out.println("connected().");
				}
				
				@Override
				public void authenticated(XMPPConnection arg0, boolean arg1) {
					// TODO Auto-generated method stub
					System.out.println("authenticated().");
				}
			});
			connection.connect();
			return connection;
		} catch (SmackException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (XMPPException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			System.err.println(username + ":" + pass+" successfully connect the xmpp server.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	//心跳保活
	static class KeepAlive implements Runnable {

		private AbstractXMPPConnection connection;
		
		public KeepAlive(AbstractXMPPConnection conn){
			this.connection = conn;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){
				if(!connection.isConnected()){
					try {
						System.out.println("reconnenct");
						connection.connect();
					} catch (SmackException | IOException | XMPPException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(!connection.isAuthenticated()){
					try {
						System.out.println("relogin");
						connection.login();
					} catch (XMPPException | SmackException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	//keep the session alive and return the thread so that the client can stop it.
	public static Thread heartBeats(AbstractXMPPConnection connection){
		KeepAlive keepAlive = new KeepAlive(connection);
		Thread t = new Thread(keepAlive);
		t.start();
		
		return t;
	}
	
	//Login
	public static AbstractXMPPConnection login(String userName, String password){
		AbstractXMPPConnection connection = getConnection(userName, password);
		try {
			connection.login();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (XMPPException | SmackException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(isConnectionValid(connection)){
			connection.addAsyncStanzaListener(new StanzaListener() {
				
				@Override
				public void processPacket(Stanza stanza) throws NotConnectedException {
					// TODO Auto-generated method stub
//					System.out.println("processPacket: " + stanza.toXML());
					Roster roster = Roster.getInstanceFor(connection);
					if(!stanza.getFrom().split("@")[0].equals(stanza.getTo().split("@")[0]) && ((Presence)stanza).getType() == Presence.Type.subscribe){
//						System.out.println(stanza.toXML());
						RosterEntry entry = roster.getEntry(stanza.getFrom());
						if(entry != null && entry.getType().name().equals("to")){
							System.out.println(""+entry.getUser()+":entry.getType()=" + entry.getType().name());
							JOptionPane.showMessageDialog(null, entry.getUser().split("@")[0]+" has accepted your request. You two are friends now.", "Tip", JOptionPane.PLAIN_MESSAGE); 
						}else{
							FriendRequestDialog frDialog = new FriendRequestDialog(connection, stanza);
							ChatFrame.setLocationCenter(frDialog);
							frDialog.setVisible(true);
						}
					}
					//When receiving a request of deleting a friend, send a a request of deleting a friend to him/her too.
					if(!stanza.getFrom().split("@")[0].equals(stanza.getTo().split("@")[0]) && ((Presence)stanza).getType() == Presence.Type.unsubscribe){
						if(roster != null) {
							RosterEntry entry = roster.getEntry(stanza.getFrom());
							try {
								if(entry != null){
									Presence p = new Presence(Presence.Type.unsubscribe);
									p.setTo(stanza.getFrom());
									connection.sendStanza(p);
									System.err.println("Send deleting a friend:" + stanza.getFrom());
									roster.removeEntry(entry);
								}
								System.err.println("Remove " + stanza.getFrom() + " from the roster");
							} catch (NotLoggedInException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NoResponseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (XMPPErrorException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						ChatFrame.refreshFriendTree();
					}
				}
			}, new StanzaFilter() {
				
				@Override
				public boolean accept(Stanza stanza) {
					// TODO Auto-generated method stub
					if(stanza instanceof Presence){
//						System.out.println("StanzaFilter-accept(Presence): " + stanza.toXML());
						return true;
					}else{
						return false;
					}
					
				}
			});
		}else{
			System.err.println("Connection is invalidate.");
		}
		return connection;
	}
	
	public static ChatManager getChatManager(AbstractXMPPConnection connection){
		if(connection != null){
			return ChatManager.getInstanceFor(connection);
		}else{
			return null;
		}
		
	}
	
	//Judge whether the conncetion is validate.
	public static boolean isConnectionValid(AbstractXMPPConnection connection){
		return (connection.isConnected() && connection.isAuthenticated());
	}
	
	final class PrefereceListener implements StanzaListener {

		@Override
		public void processPacket(Stanza stanza) throws NotConnectedException {
			// TODO Auto-generated method stub
			System.out.println("stanza:" + stanza.toXML());
		}
		
	}
	
	public static void main(String[] args) {
		AbstractXMPPConnection connection = login("alice", "111111");
		heartBeats(connection);
		
		//list friends list
		Roster roster = null;
		if(connection.isAuthenticated() && connection.isConnected()){
			roster = Roster.getInstanceFor(connection);
			if(roster != null){
				System.out.println("roster is not null");
				Collection<RosterEntry> entries = roster.getEntries();
				System.out.println("size="+entries.size());
				for (RosterEntry entry : entries) {
					System.out.println(entry);
				}
			}else{
				System.out.println("roster is null");
			}
			
		}else{
			System.out.println("Not connected or authenticated yet");
		}
		
		try {
			//添加好友
			roster.createEntry("lh@"+xmppDomain, "lh",  new String[]{"Friends"});
		} catch (NotLoggedInException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMPPErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Receive message.
		ChatManager chatManager = ChatManager.getInstanceFor(connection);
		chatManager.addChatListener(new ChatManagerListener(){

			@Override
			public void chatCreated(Chat chat, boolean arg1) {
				// TODO Auto-generated method stub
				chat.addMessageListener(new ChatStateListener() {

					@Override
					public void processMessage(Chat chat, Message msg) {
						// TODO Auto-generated method stub
						if(msg.getThread() != null){
//							System.out.println("Receive thread message.");
						}
						if(msg.getBody() != null){
							System.out.println("Message is : " + msg);
						}
					}

					@Override
					public void stateChanged(Chat chat, ChatState state) {
						// TODO Auto-generated method stub
						System.out.println("stateChanged");
					}
					
				});
				
			}
			
		});
		
		//send a message to someone.
//		Chat chat = chatManager.createChat("lh@"+xmppDomain);
//		Message msg = new Message();
//		msg.setBody("Hi LH");
//		try {
//			chat.sendMessage(msg);
//			System.out.println("sent a message");
//		} catch (NotConnectedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//Send a file.
//		FileTransferManager manager = FileTransferManager.getInstanceFor(connection);
//		manager.addFileTransferListener(new FileTransferListener() {
//			
//			@Override
//			public void fileTransferRequest(FileTransferRequest request) {
//				// TODO Auto-generated method stub
//				System.out.println("Incoming a file." + request.getRequestor());
//				IncomingFileTransfer transfer = request.accept();
//				try {
//					transfer.recieveFile(new File("C:\\Users\\Admin\\Desktop\\"+request.getFileName()));
//				} catch (SmackException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//                System.out.println("File " + request.getFileName() + " Received Successfully");
//			}
//		});
//		System.out.println("FileTransferNegotiator.isServiceEnabled()="+FileTransferNegotiator.isServiceEnabled(connection));
//		FileTransferNegotiator negotiator = FileTransferNegotiator.getInstanceFor(connection);
//		System.out.println("negotiator.isServiceEnabled(connection)="+negotiator.isServiceEnabled(connection));
//		System.err.println("isFullJID1="+XmppStringUtils.isFullJID("lh"));
//		System.err.println("isFullJID2="+XmppStringUtils.isFullJID("lh@"+xmppDomain +"/Smack"));
//		/**
//		 * If you are sending file to the Spark client, the last part of the FullJID have to be "/Spark".
//		 * If you are sending file to the Smack client, the last part of the FullJID have to be "/Smack".
//		 * Otherwise, the file sending function will fail.
//		 */
//		OutgoingFileTransfer fileTransfer = manager.createOutgoingFileTransfer("lh@"+xmppDomain +"/Spark");
//		try {
//			fileTransfer.sendFile(new File("C:\\Users\\Admin\\Desktop\\2.txt"), "Send a file.");
//			
//			Thread.sleep(2000);
//			
//			System.out.println("Status :: " + fileTransfer.getStatus() + " Error :: " + fileTransfer.getError() + " Exception :: " + fileTransfer.getException());
//		} catch (SmackException | InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
