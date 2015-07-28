package com.connector;

import javax.jms.*;
import javax.naming.*;

public class ConnMgr {

static Connection con;
public static Connection getConnection(String factName,Context c)
{

	try{
		if(con==null)
		{
		 ConnectionFactory connFact=(ConnectionFactory)c.lookup(factName);
		 con=connFact.createConnection();
		}
	}catch(Exception e){e.printStackTrace();}
	return con;
}
}
