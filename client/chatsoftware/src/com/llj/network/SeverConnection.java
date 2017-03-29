package com.llj.network;

import org.jivesoftware.smack.packet.Mechanisms;
import org.jivesoftware.smackx.iot.discovery.ThingState;

public class SeverConnection {
	public static String severDNS = "ec2-54-254-130-230.ap-southeast-1.compute.amazonaws.com";
	public static String severPort = "5222";
	
	public static void login(String username, String pass){
		try {
			System.err.println(username+""+pass);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		login("111","5555");
	}
}
