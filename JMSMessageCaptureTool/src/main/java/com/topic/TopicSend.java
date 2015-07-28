package com.topic;

import java.io.*;

import java.util.*;

import javax.transaction.*;

import javax.naming.*;

import javax.jms.*;

import javax.rmi.PortableRemoteObject;

public class TopicSend implements Runnable
{
	
	public TopicSend(String dname,String fname, String jfname,String tname,String username, String pass)
	{
		super();
		JNDI_FACTORY=fname;
		JMS_FACTORY=jfname;
		TOPIC=tname;
		domain=dname;
		this.username=username;
		this.pass=pass;
	}
	

	public void run() {
		// TODO Auto-generated method stub
		//tcon.start();
		//init1();
		init1();
		try{
			System.out.println("sleeping sending "+TOPIC);
			//Thread.sleep(99999999l);
		}catch(Exception e){System.out.println(e);}
	}
	
	
	
public final String JNDI_FACTORY;
public final String JMS_FACTORY;
public final String domain;
public String TOPIC;
public String username;
public String pass;

protected TopicConnectionFactory tconFactory;
protected TopicConnection tcon;
protected TopicSession tsession;
protected TopicPublisher tpublisher;
protected Topic topic;
protected TextMessage msg;

public void init1()
{
	try{
	InitialContext ic = getInitialContext(domain);
	}catch(Exception e){e.printStackTrace();}
}

public void init(Context ctx, String topicName) throws NamingException, JMSException

{

tconFactory = (TopicConnectionFactory)

PortableRemoteObject.narrow(ctx.lookup(JMS_FACTORY),TopicConnectionFactory.class);

tcon = tconFactory.createTopicConnection();

tsession = tcon.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

topic = (Topic) PortableRemoteObject.narrow(ctx.lookup(topicName), Topic.class);

tpublisher = tsession.createPublisher(topic);

msg = tsession.createTextMessage();

tcon.start();

}

public void send(String message) throws JMSException {

msg.setText(message);

tpublisher.publish(msg);

}

public void close() throws JMSException {

tpublisher.close();
tsession.close();
tcon.close();

}

public static void main(String[] args) throws Exception {

if (args.length != 2) {

System.out.println("Usage: java examples.jms.topic.TopicSend WebLogicURL TopicName");

return;

}

//InitialContext ic = getInitialContext(args[0]);

//TopicSend ts = new TopicSend();

//ts.init(ic, TOPIC);
//ts.TOPIC=args[1];
//ts.init(ic, args[1]);

//readAndSend(ts);

//ts.close();

}

protected static void readAndSend(TopicSend ts)

throws IOException, JMSException

{

BufferedReader msgStream = new BufferedReader (new InputStreamReader(System.in));

String line=null;

do {

System.out.print("Enter message (\"quit\" to quit): \n");

line = msgStream.readLine();

if (line != null && line.trim().length() != 0) {

ts.send(line);

System.out.println("JMS Message Sent: "+line+"\n");

}

} while (line != null && ! line.equalsIgnoreCase("quit"));

}

protected InitialContext getInitialContext(String url)

throws NamingException

{

Hashtable<String,String> env = new Hashtable<String,String>();

env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
env.put(Context.PROVIDER_URL, url);
env.put("weblogic.jndi.createIntermediateContexts", "true");
env.put(Context.SECURITY_PRINCIPAL, username);
env.put(Context.SECURITY_CREDENTIALS, pass);


return new InitialContext(env);

}}
