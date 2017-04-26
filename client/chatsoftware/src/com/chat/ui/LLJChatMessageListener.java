package com.chat.ui;

import java.util.Date;

import javax.swing.JTextPane;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.chatstates.ChatState;
import org.jivesoftware.smackx.chatstates.ChatStateListener;

import com.llj.util.DateUtil;

public class LLJChatMessageListener implements ChatManagerListener {

	private JTextPane textPane, textMessage;
	
	public LLJChatMessageListener(JTextPane textPane, JTextPane textMessage) {
		this.textPane = textPane;
		this.textMessage = textMessage;
	}

	@Override
	public void chatCreated(Chat chat, boolean arg1) {
		// TODO Auto-generated method stub
		chat.addMessageListener(new ChatStateListener() {

			@Override
			public void processMessage(Chat chat, Message msg) {
				// TODO Auto-generated method stub
				if(msg.getThread() != null){
//					System.out.println("Receive thread message.");
				}
				if(msg.getBody() != null){
					System.out.println("Message is : " + msg);
					String from = msg.getFrom();
					from = from.split("@")[0];
					String time = DateUtil.format3(new Date());
					String message = from + "  " + time +"\n    " + msg.getBody();
					ChatFrame.intsertMsg(message, 0);
				}
			}

			@Override
			public void stateChanged(Chat chat, ChatState state) {
				// TODO Auto-generated method stub
				System.out.println("stateChanged");
			}
			
		});
	}

}
