package com.connector;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

public class TopicRecieve implements MessageListener,Runnable{
	
	
	public void run() {
		// TODO Auto-generated method stub
		//tcon.start();
		//init1();
		init1();
		try{
			System.out.println("sleeping"+TOPIC);
			//Thread.sleep(99999999l);
		}catch(Exception e){System.out.println(e);}
	}
	
	
	public final String JNDI_FACTORY;
	public final String JMS_FACTORY;
	public final String TOPIC;
	public final String domain;
	private TopicConnectionFactory tconFactory;
	private TopicConnection tcon;
	private TopicSession tsession;
	private TopicSubscriber tsubscriber;
	private Topic topic;
	private boolean quit = false;
	private InitialContext ic;
	
	public void onMessage(Message msg) {
		/*try{
			Thread.sleep(100);
		}catch(Exception e){}
		*/// TODO Auto-generated method stub
		//System.out.println(TOPIC);
		if(msg instanceof TextMessage)
			{
			try{
				//System.out.println("MessageId:"+((TextMessage)msg).getJMSMessageID());
				String content=((new java.util.Date())+"Domain: "+domain+"topic="+TOPIC+"Message:\n"+((TextMessage)msg).getText());
				System.out.println(content);
				LoggingClass.logger.info(content);
			}catch(JMSException je){System.out.println(je);}
			
			}//System.out.print("Text Message: ");
		quit=true;
		//System.out.println(msg);
	}
	
	public TopicRecieve()
	{
		JNDI_FACTORY="weblogic.jndi.WLInitialContextFactory";
		JMS_FACTORY="CF0";
		TOPIC="T0";
		domain="t3://localhost:9001/";
		init1();
	
	}
	public TopicRecieve(String dname,String fname, String jfname,String tname)
	{
		JNDI_FACTORY=fname;
		JMS_FACTORY=jfname;
		TOPIC=tname;
		domain=dname;
		
	}
	public void init1()
	{
		try{
			ic=getInitialContext(domain);
			init(ic,TOPIC);
			//System.out.println(">>>");
			//while(!quit)
			//{
			//Thread.sleep(9900);
			//}
			//System.out.println("<<<");
			System.out.println("Now Listening to Topic: "+TOPIC);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	private InitialContext getInitialContext(String url)
			throws NamingException
			{
			Hashtable<String,String> env = new Hashtable<String,String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
			env.put(Context.PROVIDER_URL, url);
			env.put("weblogic.jndi.createIntermediateContexts", "true");
			env.put(Context.SECURITY_PRINCIPAL, "asriv5");
			env.put(Context.SECURITY_CREDENTIALS, "LiveFree@123");
			return new InitialContext(env);
			}

	public void close() throws JMSException {
		tsubscriber.close();
		tsession.close();
		tcon.close();
		System.out.println("closed: "+domain+" - "+TOPIC);
		}

	public void init(Context ctx, String topicName)
			throws NamingException, JMSException
			{
			tconFactory = (TopicConnectionFactory)
			PortableRemoteObject.narrow(ctx.lookup(JMS_FACTORY),
			TopicConnectionFactory.class);
			tcon = tconFactory.createTopicConnection();
			tsession = tcon.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			topic = (Topic)
			PortableRemoteObject.narrow(ctx.lookup(topicName),
			Topic.class);
			tsubscriber = tsession.createSubscriber(topic);
			tsubscriber.setMessageListener(this);
			tcon.start();
			}

}
