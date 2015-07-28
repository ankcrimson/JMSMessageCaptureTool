package com.connector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.jms.*;
import javax.naming.*;

public class Topic2 {

	Context c;
	Connection tc;
	Session ts;
	Destination t;
	MessageConsumer sub;
	
	public void init(String fact, String url)
	{
		try{
		Hashtable<String,String> env=new Hashtable<String, String>();
		env.put(Context.PROVIDER_URL, url);
		env.put(Context.INITIAL_CONTEXT_FACTORY, fact);
		c=new InitialContext(env);
		}catch(NamingException ne){System.out.println("Naming: "+ne);}
	}
	
	public void setup(String cf, String tname)
	{
		try{
		//tcf=(TopicConnectionFactory)c.lookup(cf);
		//tc=tcf.createTopicConnection();
		tc=ConnMgr.getConnection(cf, c);
		ts=tc.createSession(false, Session.AUTO_ACKNOWLEDGE);
		t=(Destination)c.lookup(tname);
		
		sub=ts.createConsumer(t);
		sub.setMessageListener(new Listen2(tname));
		tc.start();
		}catch(NamingException ne){System.out.println(ne);}
		catch(JMSException je){System.out.println(je);}
	}
	
	public void close()
	{
		try{
			ts.close();
			tc.close();
		}catch(JMSException e){System.out.println(e);}
	}
	
	public void add(List<Topic2> topics)
	{
		try{
			BufferedReader br=new BufferedReader(new FileReader("config.txt"));
			String str="";
			while((str=br.readLine())!=null)
			{
				if(str.indexOf("--")<0)
				{
					System.out.println(str);
					String[] vals=str.split(",");
					Topic2 tmp=new Topic2();
					tmp.init(vals[2], vals[0]);
					tmp.setup(vals[1], vals[3]);
					topics.add(tmp);
				}
			}
		}catch(Exception e){System.out.println(e);}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Topic2 mt=new Topic2();
		List<Topic2> topics=new ArrayList<Topic2>();
		mt.add(topics);
		System.out.println("Type \"quit\" to quit");
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		try{
		while(!(br.readLine().equalsIgnoreCase("quit")))
		{}
		for(Topic2 t:topics){
			t.close();
		}
		}catch(Exception e){System.out.println("IO: "+e);}
	}

}
