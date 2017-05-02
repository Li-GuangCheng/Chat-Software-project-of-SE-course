package com.chat.ui;

import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JTextPane;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.chatstates.ChatState;
import org.jivesoftware.smackx.chatstates.ChatStateListener;

import com.llj.util.DateUtil;

public class LLJChatMessageListener implements ChatManagerListener {

	private JTextPane textPane, textMessage;
	private JFrame chatFrame;
	
	public LLJChatMessageListener(JTextPane textPane, JTextPane textMessage, JFrame chatFrame) {
		this.textPane = textPane;
		this.textMessage = textMessage;
		this.chatFrame = chatFrame;
	}

	@Override
	public void chatCreated(Chat chat, boolean arg1) {
		// TODO Auto-generated method stub
		chat.addMessageListener(new ChatStateListener() {

			@Override
			public void processMessage(Chat chat, Message msg) {
				// TODO Auto-generated method stub
//				System.out.println("Message is : " + msg);
				//Process normal message.
				if(msg.getBody() != null && msg.getSubject() == null && !msg.getBody().equals("Shake")){
//					System.out.println("Message is : " + msg);
					String from = msg.getFrom();
					from = from.split("@")[0];
					String time = DateUtil.format3(new Date());
					String message = from + "  " + time +"\n    " + msg.getBody();
					ChatFrame.intsertMsg(message, 0);
				}
				//Process shaking window message.
				if(msg.getSubject() != null && msg.getSubject().equals("Shake") && msg.getBody().equals("Shake")){
					System.out.println("shake the frame");
					shakeFrame(chatFrame);
					
					String from = msg.getFrom();
					from = from.split("@")[0];
					String time = DateUtil.format3(new Date());
					String message = from + "  SHAKED your dialog at "+ time +". ";
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
	
	/**
	 * Shake the chatting window.
	 */
	public void shakeFrame(JFrame frame) {
		int x = frame.getX();
		int y = frame.getY();
		for (int i = 0; i < 20; i++) {
			if ((i & 1) == 0) {
				x += 3;
				y += 3;
			} else {
				x -= 3;
				y -= 3;
			}
			frame.setLocation(x, y);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

}
