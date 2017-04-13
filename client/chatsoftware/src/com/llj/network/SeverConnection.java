package com.llj.network;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.parsing.ExceptionLoggingCallback;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smack.util.TLSUtils;
import org.pushingpixels.substance.api.shaper.RectangularButtonShaper;

public class SeverConnection {
	public static String severDNS = "ec2-54-254-130-230.ap-southeast-1.compute.amazonaws.com";
	public static int severPort = 5222;
	public static String xmppDomain = "ip-172-31-21-142.ap-southeast-1.compute.internal";
	public static AbstractXMPPConnection connection;// Create a XMPPConnection

	public static void login(String username, String pass) {
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
			connection.connect();
			//return connection;
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
			System.err.println(username + ":" + pass+" successfully login the xmpp server.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return null;
	}

	public static void main(String[] args) {
		login("lgc", "111111");
	}
}
