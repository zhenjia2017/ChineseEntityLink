package org.min.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class KbAndCategory {

	public  void init(String file) throws Exception{
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("data/kb&category.txt"), "utf-8"));
		
		SAXReader reader = new SAXReader();
		Document doc=reader.read(new File(file));
		Element root=doc.getRootElement();
		
		for(Object o:root.elements("entity")){
			
			Element e=(Element)o;
			String id=e.element("enity_id").getText();
			String name = e.element("title").getText();
			String category = e.element("category").getText();

			writer.write(id+"\t"+category+name+"\n");
			writer.flush();			
		}
		writer.close();
	}
	
	public HashMap<String,String> kbAndCategory()   {
		HashMap<String,String> kbAndCategoryMap = new HashMap<String,String>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("data/kb&category.txt"), "utf-8"));
			String line = null;
			
			while((line = reader.readLine()) != null)  
				kbAndCategoryMap.put(line.split("\t")[0], line.split("\t")[1]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return kbAndCategoryMap;
	}
	
	public HashMap<String,String> category()  {
		HashMap<String,String> categoryMap = new HashMap<String,String>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("data/weibo.txt"), "utf-8"));
			String line = null;
			String key = "wanyuangongjijin";
			String str = "";
			while((line = reader.readLine()) != null)  {
				if(line.contains("\t"))  {
					String string = line.split("\t")[0].replaceAll("[0-9]", "");
					if(!key.equals(string))  {
						categoryMap.put(key, str.substring(0, str.length()-1));
						key = string;
						str = line.split("\t")[1]+" ";
					}
					else
						str += line.split("\t")[1]+" ";
				}
			}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return categoryMap;
	}
	
	public static void main(String []args) throws Exception   {
		KbAndCategory kb = new KbAndCategory();
		kb.init("./data/PKBase_zhwiki_1_small_edit2.xml");
	}

}
