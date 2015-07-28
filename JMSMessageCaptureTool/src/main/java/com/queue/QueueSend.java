package com.queue;

import java.util.Hashtable;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;



public class QueueSend {

	/**
	 * @param args
	 * gayatridevi
	 */
	public static final String jndi_factory="weblogic.jndi.WLInitialContextFactory";
	public final static String jms_factory="CF0";
	private static final String queue="Q0";
	private QueueConnectionFactory factory;
	private QueueConnection qcon;
	private QueueSession session;
	private QueueSender qs;
	private Queue q;
	private TextMessage msg;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		QueueSend qs=new QueueSend();
		InitialContext ic=qs.getInitialContext("t3://localhost:9001");
		qs.init(ic);
		qs.send("Hiiiii");
		qs.close();
	}
	public void init(Context ctx)
	{
		try{
		factory=(QueueConnectionFactory)ctx.lookup(jms_factory);
		qcon=factory.createQueueConnection();
		session=qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		q=(Queue)ctx.lookup(queue);
		qs=session.createSender(q);
		msg=session.createTextMessage();
		qcon.start();
		}catch(Exception e){System.out.println(e);}
	}
	public void send(String message)
	{
		try{
			msg.setText(message);
			qs.send(msg);
		}catch(Exception e){System.out.println(e);}
	}
	public void close()
	{
		try{
			qs.close();
			session.close();
			qcon.close();
		}catch(Exception e){System.out.println(e);}
	}
public InitialContext getInitialContext(String url)
{
	try{
		Hashtable<String, String> env=new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY	, jndi_factory);
		env.put(Context.PROVIDER_URL, url);
		return new InitialContext(env);
	}catch(Exception e){
		System.out.println(e);
	}
	return null;
}
}
