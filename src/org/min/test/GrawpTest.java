package org.min.test;
import java.net.*;
import java.util.*;
import java.io.*;

public class GrawpTest {	
	public static void getContentByLanguage(String country){
		  try {
		   URL urlGoogle = new URL("http://www.google.com");
		   HttpURLConnection googleConnection = 
		     (HttpURLConnection)urlGoogle.openConnection();
		   googleConnection.setRequestProperty("Accept-Language",country);
		   
		   //
		   Map request = googleConnection.getRequestProperties();
		   Set reqSet = request.keySet();
		   for (Iterator iterator = reqSet.iterator(); iterator.hasNext();) {
		    String object = (String) iterator.next();
		    System.out.println(object + "  :  " + request.get(object));
		    
		   }
		   googleConnection.connect();
		   //
		   Map responses = googleConnection.getHeaderFields();
		   Set resSet = responses.keySet();
		   for (Iterator iterator = resSet.iterator(); iterator.hasNext();) {
		    String object = (String) iterator.next();
		    System.out.println(object + ":" + responses.get(object));
		    
		   }
		   //
		   
		   InputStream is = googleConnection.getInputStream();
		   BufferedReader br = new BufferedReader(new InputStreamReader(is));
		   String strLine = null;
		   while (null != (strLine = br.readLine())) {
		    System.out.println(strLine);
		   }
		   br.close();
		   is.close();
		   googleConnection.disconnect();
		  } catch (Exception e) {
		   // TODO: handle exception
		   e.printStackTrace();
		  }
		  
		 }
		 /**
		  * @param args
		  */
		 public static void main(String[] args) {
		  // TODO Auto-generated method stub
//		  System.out.println("获取日文页面");
//		  GrawpTest.getContentByLanguage("js");
//		  System.out.println("\n\n");
		  System.out.println("获取中文页面");
		  GrawpTest.getContentByLanguage("zh-HK");
		 }

	 

}
