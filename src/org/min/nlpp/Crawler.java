package org.min.nlpp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Crawler {

	StringBuilder pageChineseChar = new StringBuilder();    //存放下载下来的摘要和标题
    ArrayList<String> content = new ArrayList<String>();
    String str = null;
	/**
	 * 获取第一页的内容和第2页到第九页的URL地址
	 * @param wd
	 * @return
	 */
	 public ArrayList<String> getChinesChar(String wd)throws NullPointerException { 
	
         String key = wd; //查询关键字  
         String ss = null;
         String line = null;
                
         try {
			 key = URLEncoder.encode(key, "utf-8");
			 URL u = new URL("http://www.baidu.com/s?wd=" + key + "&ie=utf-8");  
	         java.net.URLConnection conn = u.openConnection();  
	         conn.setConnectTimeout(20000);
	         conn.setReadTimeout(20000);
	         BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));  
//	         BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./data/test.txt"),"utf-8"));
	         line = reader.readLine();  
	         String one_page = "";
	         while (line != null) {  
	        	 System.out.println(line);   
//	        	 writer.write(line+"\n");
//	        	 writer.flush();
	            if(line.contains("wd") && line.contains("下一页")){
	            	ss = line;     	//用户获取总共有多少页码  
	                //System.out.println(line);    
	            }
	            str = line.replaceAll("[^\u4e00-\u9fa5]", "");
	            if(!str.equals("")){
	            	one_page = one_page + str;
	            }
	            
	           // pageChineseChar.append("\n\r");
	            line = reader.readLine();  
	         }  
         	 content.add(one_page);
	         reader.close(); 
	         
//	         获取第二页到第九页的标题记摘要
	         int len = this.getPageCount(ss);
	         System.out.println("len = " + len);
	         for(int i = 1; i <= len; i++){
	        	 getMoreInfo(key, i);
	         }
//	        writer.close();
	        
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
         
         String strInput =pageChineseChar.toString().replaceAll("[^\u4e00-\u9fa5]", "");
         System.out.println("打印出所有的文本信息，每个list中存放一个页面中的所有标题和摘要");
         int k = 1;
         for(String s:content){
//        	 System.out.println("------------------------------------------------------------------");
//        	 System.out.println("------------------------------page = "+k+"------------------------");
//        	 System.out.println("------------------------------------------------------------------");
//        	 System.out.println(s);
        	 k++;
         }      
        
         return content;
	 }
	 /**
	  * 获取页码，第10页的pn = 90 故，我们把第9页当做第10页，把第1页当做第2页，这个规律是根据url分析得出来的
	  * @param line url地址
	  * @return
	  */
	 public int getPageCount(String line){
		 int a = 0;
		 if(line.contains(">9<") && line.contains(">8<") && line.contains(">7<") && line.contains(">6<") && line.contains(">5<") && line.contains(">4<") && line.contains(">3<") && line.contains(">2<") && line.contains(">1<")){
			 a = 9;
		 }
		 if(!line.contains(">9<") && line.contains(">8<") && line.contains(">7<") && line.contains(">6<") && line.contains(">5<") && line.contains(">4<") && line.contains(">3<") && line.contains(">2<") && line.contains(">1<")){
			 a = 8;
		 }
		 if(!line.contains(">9<") && !line.contains(">8<") && line.contains(">7<") && line.contains(">6<") && line.contains(">5<") && line.contains(">4<") && line.contains(">3<") && line.contains(">2<") && line.contains(">1<")){
			 a = 7;
		 }
		 if(!line.contains(">9<") && !line.contains(">8<") && !line.contains(">7<") && line.contains(">6<") && line.contains(">5<") && line.contains(">4<") && line.contains(">3<") && line.contains(">2<") && line.contains(">1<")){
			 a = 6;
		 }
		 if(!line.contains(">9<") && !line.contains(">8<") && !line.contains(">7<") && !line.contains(">6<") && line.contains(">5<") && line.contains(">4<") && line.contains(">3<") && line.contains(">2<") && line.contains(">1<")){
			 a = 5;
		 }
		 if(!line.contains(">9<") && !line.contains(">8<") && !line.contains(">7<") && !line.contains(">6<") && !line.contains(">5<") && line.contains(">4<") && line.contains(">3<") && line.contains(">2<") && line.contains(">1<")){
			 a = 4;
		 }
		 if(!line.contains(">9<") && !line.contains(">8<") && !line.contains(">7<") && !line.contains(">6<") && !line.contains(">5<") && !line.contains(">4<") && line.contains(">3<") && line.contains(">2<") && line.contains(">1<")){
			 a = 3;
		 }
		 if(!line.contains(">9<") && !line.contains(">8<") && !line.contains(">7<") && !line.contains(">6<") && !line.contains(">5<") && !line.contains(">4<") && !line.contains(">3<") && line.contains(">2<") && line.contains(">1<")){
			 a = 2;
		 }
		 if(!line.contains(">9<") && !line.contains(">8<") && !line.contains(">7<") && !line.contains(">6<") && !line.contains(">5<") && !line.contains(">4<") && !line.contains(">3<") && !line.contains(">2<") && line.contains(">1<")){
			 a = 1;
		 }
		 return a;
	 }
	 /**
	  * 获取第2页到第9页的网页的标题和摘要
	  * key 只能传入汉子，由于URLConnection具有重定向功能，所以每次输入汉子时，都会解析为一组代码，如果输入一组代码，则无法重定向
	 * @throws IOException 
	  */
	 public void getMoreInfo(String key,int page) throws IOException{
		 System.out.println("开始下载第    "+ (page+1) + "   页的内容");
		 
		 //http://news.baidu.com/ns?word=%E4%BD%93%E8%82%B2&pn=0&cl=2&ct=1&tn=news&ie=utf-8&bt=0&et=0
		 URL u = new URL("http://news.baidu.com/ns?word="  +key + "&pn=" +page*10 + "&cl=2&ct=1&tn=news&ie=utf-8&bt=0&et=0"); 
		
		 System.out.println("http://news.baidu.com/ns?word="  +key + "&pn=" +page*10 + "&cl=2&ct=1&tn=news&ie=utf-8&bt=0&et=0");
         java.net.URLConnection conn = u.openConnection();
         conn.setConnectTimeout(20000);
         conn.setReadTimeout(20000);
         String code = conn.getContentEncoding();
         BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));  
         String line = reader.readLine();  
         String one_page = "";
         while (line != null) {  
           str = line.replaceAll("[^\u4e00-\u9fa5]", "");
           if(!str.equals("")){
        	   one_page = one_page + str;    
           }
           // pageChineseChar.append("\n\r");
            line = reader.readLine();  
         }  
//         System.out.println("第    "+ page+1 + "   页的内容 为:  " + one_page);
         content.add(one_page);
         reader.close();  
//         System.out.println(page+1 + "   页的内容已经下载完毕");
	 }

}
