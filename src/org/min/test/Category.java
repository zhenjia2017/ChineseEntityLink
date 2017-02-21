package org.min.test;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.min.util.ReaderUtil;
import org.min.util.WriterUtil;

public class Category {

	private void nrCategory()   {
		ReaderUtil reader=new ReaderUtil("./data/PKBase_zhwiki_1_small_edit.xml");
		ReaderUtil reader2=new ReaderUtil("./data/categoryAndName.txt");
		WriterUtil writer=new WriterUtil("./data/PKBase_zhwiki_1_small_edit2.xml");
		HashMap<String,String> map = new  HashMap<String,String>();
		String regex = "<title>(.*)</title>";
		Pattern p = Pattern.compile(regex);
		String line = null;
		
		while((line = reader2.readLine()) != null)   {
			String key = line.split("\t")[1];
			String value = line.split("\t")[0];
			if(!map.containsKey(key))  
				map.put(key, value);
//			else  {
//				if(!map.get(key).equals(value))
//					System.out.println(line);
//			}
		}
		
		while((line = reader.readLine()) != null)   {
			Matcher m = p.matcher(line);
			if(m.find())   {
				if(map.containsKey(m.group(1)))
					writer.println("<category>"+map.get(m.group(1))+"</category>");
				else if(map.containsKey(m.group(1)+"¶Ó"))
					writer.println("<category>"+map.get(m.group(1)+"¶Ó")+"</category>");
				else
					writer.println("<category>"+"</category>");
			}
			
			writer.println(line);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Category category = new Category();
		category.nrCategory();
	}

}
