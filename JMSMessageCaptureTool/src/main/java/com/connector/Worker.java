package com.connector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Worker {
	
public static void main(String[] args) {
	ArrayList<DestinationRecieve> trs=new ArrayList<DestinationRecieve>();
	
	try{
	File f=new File("config.txt");
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
