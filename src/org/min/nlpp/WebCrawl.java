package org.min.nlpp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawl {

	public String getCrawl(String name)   {
		String string = "";
//		<p id="unifypromptone">“<a href="/wiki/%E6%9D%9C%E5%85%B0%E7%89%B9">杜兰特</a>”是“<a href="/wiki/%E5%87%AF%E6%96%87%C2%B7%E6%9D%9C%E5%85%B0%E7%89%B9">凯文·杜兰特</a>”的同义词。</p>
    	String regex = "<p id=\"unifypromptone\">“<a href=\"/wiki/.*>(.*)</a>”是“<a href=\"/wiki/.*>(.*)</a>”的同义词。</p>";
		Pattern p = Pattern.compile(regex);
    	try {
    		String key = URLEncoder.encode(name, "utf-8");
			URL url = new URL("http://www.baike.com/wiki/"+ key +"&prd=button_doc_jinru");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		    conn.connect();
	        conn.setConnectTimeout(20000);
	        conn.setReadTimeout(20000);
	        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8")); 
	        String line = null;
	        while ((line = reader.readLine()) != null) { 
//	        	System.out.println(line);
	        	Matcher m = p.matcher(line);
	        	if(m.find())
	        		string = m.group(2);
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        
		return string;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		WebCrawl web = new WebCrawl();
		System.out.println(web.getCrawl("詹姆斯"));
	}

}
