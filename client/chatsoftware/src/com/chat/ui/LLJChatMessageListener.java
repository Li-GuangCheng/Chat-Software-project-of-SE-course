package com.chat.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JTextPane;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.chatstates.ChatState;

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
		chat.addMessageListener(new ChatMessageListener() {

			@Override
			public void processMessage(Chat chat, Message msg) {
				// TODO Auto-generated method stub
//				System.out.println("Message is : " + msg);
				ChatFrame.createChatWhenMsgComing(msg.getFrom().split("@")[0]);
				//Process normal message.
				if(msg.getBody() != null && msg.getSubject() == null && !msg.getBody().equals("Shake")){
//					System.out.println("Message is : " + msg);
					String from = msg.getFrom();
					from = from.split("@")[0];
					String time = DateUtil.format3(new Date());
					String userTime = from + "  " + time +"\n    ";
					String message = msg.getBody();
					ChatFrame.intsertMsg(userTime, message, 0);
				}
				//Process shaking window message.
				if(msg.getSubject() != null && msg.getSubject().equals("Shake") && msg.getBody().equals("Shake")){
					System.out.println("shake the frame");
					shakeFrame(chatFrame);
					
					String from = msg.getFrom();
					from = from.split("@")[0];
					String time = DateUtil.format3(new Date());
					String message = from + "  SHAKED your dialog at "+ time +". ";
					ChatFrame.intsertMsg(null, message, 0);
				}
				//Process receiving an icon.
				if(msg.getSubject() != null && msg.getSubject().equals("Facial")){
					String iconPath = msg.getBody();
					if(iconPath.indexOf(".png") == -1){
						System.out.println("not an icon message.");
					} else {
						System.out.println();
						ChatFrame.insertIcon(iconPath, 1, msg);
					}
				}
				//Process receiving a video call.
				if(msg.getSubject() != null && msg.getSubject().equals("Video")){
					String url = msg.getBody();
					String from = msg.getFrom();
					from = from.split("@")[0];
					String time = DateUtil.format3(new Date());
					String message = from + " wanted to have a video talk with you at "+ time +". ";
					ChatFrame.intsertMsg(null, message, 0);
					if(url != null){
						try {
							String cmdStr = "cmd /c start chrome https://appear.in/lljchat";  
							Runtime.getRuntime().exec(cmdStr);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							try {  
					            URI uri=new URI("https://appear.in/lljchat");  
					            Desktop.getDesktop().browse(uri);   
					        } catch (IOException e4) {  
					            e4.printStackTrace();  
					        } catch (URISyntaxException e4) {  
					            e4.printStackTrace();  
					        }  
						}  
					}
				}
			}

//			@Override
//			public void stateChanged(Chat chat, ChatState state) {
//				// TODO Auto-generated method stub
//				System.out.println("stateChanged");
//			}
			
		});
	}
	
	/**
	 * Shake the chatting window.
	 */
	public void shakeFrame(JFrame frame) {
		frame.setState(JFrame.NORMAL);
		frame.show();
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
