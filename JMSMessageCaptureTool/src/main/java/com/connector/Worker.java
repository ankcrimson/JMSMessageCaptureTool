package com.connector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;

public class Worker {
	
public static void main(String[] args) {
	ArrayList<DestinationRecieve> trs=new ArrayList<DestinationRecieve>();
	
	String userName="";
	String password="";

	try{
	File f=new File("src/main/resources/login.properties");
	FileReader fr=new FileReader(f);
	Properties props=new Properties();
	props.load(fr);
	userName=props.getProperty("username");
	password=props.getProperty("password");
	fr.close();
	}
	catch(IOException ioe){
		System.out.println("Unable to read password. Please make sure its there in src/main/resources/login.properties");
	}
	
	//loaded common credentials
	
	try{
	File f=new File("src/main/resources/config.txt");
	FileReader fr=new FileReader(f);
	BufferedReader br=new BufferedReader(fr);
	String i="";
	while((i=br.readLine())!=null)
	{

		if(i.length()<=2)
			continue;
			
		if(i.indexOf("--")==0)
			continue;
		String[] info=i.split(",");
		DestinationRecieve tr2=new DestinationRecieve(info[0],info[2],info[1],info[3]);
		tr2.setUsername(userName);
		tr2.setPassword(password);
		//Thread t2=new Thread(tr2);
		//t2.setName(info[3]+"_"+info[0]);
		
		//t2.start();
		tr2.init1();
		trs.add(tr2);
	}
	}catch(Exception e){e.printStackTrace();}
	System.out.println("Enter quit to exit");
BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	try{
		String st="";
		while((st=br.readLine())!=null)
				{
			if(st.length()<=2)
				continue;
					if(st.equalsIgnoreCase("quit"))
					{
					for(DestinationRecieve t:trs)
						t.close();
					System.exit(0);
					}
				}
		//Thread tm=Thread.currentThread();
		System.out.println("xxxxxxxxxxxxxx----------------xxxxxxxxxxxxxxxxxxx");
		//tm.join();
	}catch(Exception e){System.out.println(e);}
}
}
