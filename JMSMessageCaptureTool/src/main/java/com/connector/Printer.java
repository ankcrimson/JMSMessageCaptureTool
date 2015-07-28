package com.connector;

import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;


public class Printer implements Runnable {

MessageConsumer consumer;
public void setConsumer(MessageConsumer consumer) {
	this.consumer = consumer;
}
public void print(MessageConsumer c)
{
	Message msg=null;
	try{
	while((msg=c.receiveNoWait())!=null)
	{
		System.out.println(((TextMessage)msg).getText());
	}
	}catch(Exception e){e.printStackTrace();}
	}
@Override
	public void run() {
		// TODO Auto-generated method stub
		print(consumer);
	}
}
