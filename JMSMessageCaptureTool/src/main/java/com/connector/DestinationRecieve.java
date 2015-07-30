package com.connector;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Destination;
import javax.jms.Connection;
import javax.jms.MessageConsumer;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

public class DestinationRecieve implements MessageListener,Runnable{
	
	public final String JNDI_FACTORY;
	public final String JMS_FACTORY;
	public final String TOPIC;
	public final String domain;
	private Connection tcon;
	private Session tsession;
	private MessageConsumer tsubscriber;
	private Destination destination;
	private boolean quit = false;
	private InitialContext ic;
	
	private String username;
	private String password;
	
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void run() {
		init1();
		try{
			System.out.println("sleeping"+TOPIC);
			//Thread.sleep(Long.MAX_VALUE);
		}catch(Exception e){System.out.println(e);}
	}
	
	public void init1()
	{
		try{
			ic=getInitialContext(domain);
			init(ic,TOPIC);
			System.out.println("Now Listening to Destination: "+TOPIC);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void init(Context ctx, String destinationName)
			throws NamingException, JMSException
			{
			tcon=ConnMgr.getConnection(JMS_FACTORY, ctx);
			tsession = tcon.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = (Destination)	PortableRemoteObject.narrow(ctx.lookup(destinationName),Destination.class);
			tsubscriber = tsession.createConsumer(destination);
			tsubscriber.setMessageListener(this);
			tcon.start();
			}

	
	public void onMessage(Message msg) {
		if(msg instanceof TextMessage)
			{
			try{
				//String content=((new java.util.Date())+"Domain: "+domain+"destination="+TOPIC+"Message:\n"+((TextMessage)msg).getText());
				String content=((new java.util.Date())+"Domain: "+Thread.currentThread().getName()+" - "+"Message:\n"+((TextMessage)msg).getText());
				
				System.out.println(content);
				LoggingClass.logger.info(content);
				
				Enumeration<String> props= msg.getPropertyNames();
				while(props.hasMoreElements())
				{
					String pname=props.nextElement();
					String pval=msg.getStringProperty(pname);
					String curProp=pname+"\n"+pval+"\n\n";
					System.out.println(curProp);
					LoggingClass.logger.info(content);
				}
				
			}catch(JMSException je){System.out.println(je);}
			
			}
		quit=true;
	}
	
	public DestinationRecieve()
	{
		JNDI_FACTORY="weblogic.jndi.WLInitialContextFactory";
		JMS_FACTORY="CF0";
		TOPIC="T0";
		domain="t3://localhost:9001/";
		//init1();
	}
	
	public DestinationRecieve(String dname,String fname, String jfname,String tname)
	{
		JNDI_FACTORY=fname;
		JMS_FACTORY=jfname;
		TOPIC=tname;
		domain=dname;
	}
	
	private InitialContext getInitialContext(String url)
			throws NamingException
			{
			Hashtable<String,String> env = new Hashtable<String,String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
			env.put(Context.PROVIDER_URL, url);
			env.put("weblogic.jndi.createIntermediateContexts", "true");
			env.put(Context.SECURITY_PRINCIPAL, username);
			env.put(Context.SECURITY_CREDENTIALS, password);
			return new InitialContext(env);
			}

	public void close() throws JMSException {
		tsubscriber.close();
		tsession.close();
		tcon.close();
		System.out.println("closed: "+domain+" - "+TOPIC);
		}
}
