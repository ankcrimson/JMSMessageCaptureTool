package com.connector;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class Listen2 implements MessageListener {
	String head="";
	public void setHead(String head) {
		this.head = head;
	}public String getHead() {
		return head;
	}
	public Listen2() {
		// TODO Auto-generated constructor stub
	}
	public Listen2(String tname) {
		// TODO Auto-generated constructor stub
		setHead(tname);
	}
	
	
	public synchronized void onMessage(Message m) {
		// TODO Auto-generated method stub
		try{
		if(m instanceof TextMessage)
			{
			System.out.println(getHead()+":");
			System.out.println(((TextMessage) m).getText());
			}
		}catch(Exception e ){System.out.println("listen: "+e);}
	}

}
