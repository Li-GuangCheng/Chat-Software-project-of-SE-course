package com.chat.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.packet.XMPPError;
import org.jivesoftware.smack.parsing.ExceptionLoggingCallback;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smack.util.TLSUtils;
import org.jivesoftware.smackx.iqregister.AccountManager;

import com.llj.network.SeverConnection;

public class RegisterFrame extends JFrame implements KeyListener {

	private JPanel contentPane;
	private static JTextField textUsername;
	private static JPasswordField textPassword;
	private static JPasswordField confirmPswd;
	private static String username, password, confirm;
	private static AccountManager accountManager;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterFrame frame = new RegisterFrame();
					ChatFrame.setLocationCenter(frame);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the register frame.
	 */
	public RegisterFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginFrame.class.getResource("/Icons64/chat.png")));
		setTitle("Chat Register");
		setResizable(false);
//		setDefaultCloseOperation(JFrame.);
		setBounds(100, 100, 380, 258);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("Panel.background"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(5, 1, 8, 8));
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		contentPane.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(false);
		contentPane.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblUsername = new JLabel("Username:  ");
		lblUsername.setIcon(new ImageIcon(LoginFrame.class.getResource("/Icons16/user.png")));
		panel_2.add(lblUsername);
		
		textUsername = new JTextField();
		textUsername.addKeyListener(this);
		panel_2.add(textUsername);
		textUsername.setColumns(15);
		
		JPanel panel_3 = new JPanel();
		panel_3.setOpaque(false);
		contentPane.add(panel_3);
		
		JLabel lblPassword = new JLabel("Password:  ");
		lblPassword.setIcon(new ImageIcon(LoginFrame.class.getResource("/Icons16/lock.png")));
		panel_3.add(lblPassword);
		
		textPassword = new JPasswordField();
		textPassword.setColumns(15);
		textPassword.addKeyListener(this);
		panel_3.add(textPassword);
		
		JPanel panel_4 = new JPanel();
		panel_4.setOpaque(false);
		contentPane.add(panel_4);
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblConfirm = new JLabel("Confirm:      ");
		lblConfirm.setIcon(new ImageIcon(RegisterFrame.class.getResource("/Icons16/lock.png")));
		panel_4.add(lblConfirm);
		
		confirmPswd = new JPasswordField();
		confirmPswd.setColumns(15);
		confirmPswd.addKeyListener(this);
		panel_4.add(confirmPswd);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JButton btnReg = new JButton("Register");
		btnReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isSuccessful(register())){
					RegisterFrame.this.dispose();
				}
			}
		});
		panel.add(btnReg, BorderLayout.EAST);
		btnReg.setFont(UIManager.getFont("Button.font"));
		
	}
	
	/**
	 * Initialize the AccountManager. 
	 */
	private static void initManager() {
		if(accountManager == null){
			try {
				accountManager = AccountManager.getInstance(getConnection());
			} catch (SmackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Register a new account.
	 * @return
	 */
	private static XMPPError.Condition register() {
		initManager();
		
		XMPPError.Condition condition = null;
		
		username = textUsername.getText().trim();
		password = new String(textPassword.getPassword());
		confirm = new String(confirmPswd.getPassword());
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(confirm)){
			JOptionPane.showMessageDialog(null, "Please input your username, password and the confirm fileds!", "Warnning", JOptionPane.WARNING_MESSAGE); 
		} else {
			if(!confirm.equals(password)){
				JOptionPane.showMessageDialog(null, "The password and the confirming password are different, please re-input!", "Warnning", JOptionPane.WARNING_MESSAGE); 
			} else {
				if(accountManager != null){
					try {
						accountManager.createAccount(username, password);
					} catch (NoResponseException | XMPPErrorException | NotConnectedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						if (e instanceof XMPPException.XMPPErrorException) {
							condition = ((XMPPException.XMPPErrorException) e).getXMPPError().getCondition();
						}

						if (condition == null) {
							condition = XMPPError.Condition.internal_server_error;
						}
					}
					
				} else {
					condition = XMPPError.Condition.internal_server_error;
					System.out.println("accountManager is null.");
				}
			}
		}
		return condition;
	}
	
	/**
	 * Get the connection to the XMPP server.
	 * @return
	 * @throws SmackException
	 * @throws IOException
	 * @throws XMPPException
	 */
	private static XMPPConnection getConnection() throws SmackException, IOException, XMPPException {
        final XMPPTCPConnectionConfiguration.Builder builder = XMPPTCPConnectionConfiguration.builder()
                .setUsernameAndPassword("username", "password")
                .setHost(SeverConnection.severDNS)
                .setPort(SeverConnection.severPort)
                .setServiceName(SeverConnection.xmppDomain);

        try {
            TLSUtils.acceptAllCertificates(builder);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
        	e.printStackTrace();
        }

        final XMPPTCPConnectionConfiguration configuration = builder.build();

        final AbstractXMPPConnection connection = new XMPPTCPConnection( configuration );
        connection.setParsingExceptionCallback( new ExceptionLoggingCallback() );
        connection.connect();

        try {
			Thread.sleep(2000);
			System.err.println("Connected="+connection.isConnected());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return connection;
    }
	
	/**
	 * Judge the condition to see whether the registering operation is successful.
	 * @param condition
	 * @return
	 */
	private static boolean isSuccessful(XMPPError.Condition condition){
		if(condition == null) {
			JOptionPane.showMessageDialog(null, "Regiser successfully!", "Success", JOptionPane.PLAIN_MESSAGE);
//			LoginFrame frame = new LoginFrame();
//			ChatFrame.setLocationCenter(frame);
//			frame.setVisible(true);
			return true;
		} else {
			String errMsg = "Regiser failed!";
			if(condition.name().contains("conflict")){
				errMsg += "\nThe username has already be registered by someone else.\nPlease use another one.";
			}
			JOptionPane.showMessageDialog(null, errMsg, "Failed", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(isSuccessful(register())){
				this.dispose();
			}
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

}
