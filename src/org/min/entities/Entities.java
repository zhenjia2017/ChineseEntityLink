package org.min.entities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.min.nlpp.WebCrawl;
import org.min.special.ForeignName;
import org.min.test.KbAndCategory;
import org.min.util.ReaderUtil;

public class Entities {
	
	public Map<String, Entity> zhishi;					//将所有的关键字按一定格式连起来，再存储:[KBBD001788/戈登·布朗/戈登/布朗/戈登 布朗/  , entity]
	public List<String> list = new LinkedList<String>();//存放该实体对应的所有关键字
	public Map<String, Entity> idEntityMap;				//实体对应的所有关键字与其对应的实体,对应关系应该是一对一
	public static HashMap<String,ArrayList<Entity>> entityMultiNameMap=new HashMap<String,ArrayList<Entity>>();//新的存储结构，一个关键字对应多个实体
	public HashMap<String,String> fullNameMap = new HashMap<String,String>();
	
	public void writeInFile() throws Exception
	{		
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./1.txt"), "utf-8"));	
		idEntityMap = new HashMap<String, Entity>();		
		Iterator iter = zhishi.keySet().iterator();
		
		while(iter.hasNext()){
			String str = (String) iter.next();	
			Entity entity = zhishi.get(str);
			
			String[] keyWord = str.split("/");
			String entity_id = keyWord[0];
			
			for(int i=1; i<keyWord.length; i++){
				String s = keyWord[i] +"/"+ entity_id;
				idEntityMap.put(entity_id, entity);
				list.add( s );				
				writer.write(s);  writer.newLine();  writer.flush();
			}
		}	
		writer.close();
	}
	public void linkedHashSet() throws Exception{   //去重
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("./1.txt"), "utf-8"));
		String str;
		Set<String> set  = new LinkedHashSet<String>();
		
		while((str = reader.readLine()) != null){
			set.add(str);
		}
		
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./2.txt"), "utf-8"));
		Iterator iter = set.iterator();
		Object[] o = set.toArray();
		
		for(Object obj : o){
			writer.write((String)obj);
			writer.newLine();
			writer.flush();
		}
		writer.close();
	}
	
	
	//写入Map<String,List<Entity>> entityMultiNameMap;结构中	
	public void read() throws Exception
	{		
		idEntityMap = new HashMap<String, Entity>();
		writeInFile();
		linkedHashSet();
		
		BufferedReader bin = new BufferedReader(new InputStreamReader(new FileInputStream("./2.txt"), "utf-8"));
		String line=bin.readLine();
		while(line!=null)
		{
			String entry[]=line.split("/");
			if(entry.length==2 && !entry[0].equals(""))
			{
//				System.out.println(entry[0]);
				if(entityMultiNameMap.containsKey(entry[0]))
				{
					ArrayList<Entity> list=entityMultiNameMap.get(entry[0]);
					Entity enti = idEntityMap.get(entry[1]);
					list.add(enti);
					entityMultiNameMap.put(entry[0],list);
				}
				else
				{
					ArrayList<Entity> list=new ArrayList<Entity>();
					//System.out.println(entry[1]);
					Entity enti = idEntityMap.get(entry[1]);
					list.add(enti);
					entityMultiNameMap.put(entry[0],list);
				}
			}
			line=bin.readLine();
		}		
	}
	
	public Entities(String file) throws Exception{		
		zhishi=new LinkedHashMap<String,Entity>();		
		init(file);
		read();
	}
	
	public Map<String,ArrayList<Entity>> getEntityMultiNameMap(){		
		return entityMultiNameMap;
	}
	
	public void setEntityMultiNameMap(HashMap<String, ArrayList<Entity>> entityMultiNameMap) {
		this.entityMultiNameMap =  entityMultiNameMap;
	}
	
	public HashMap<String,String> getFullNameMap()   {
		return fullNameMap;
	}
	
	public void setFullNameMap(HashMap<String,String> fullNameMap)   {
		this.fullNameMap = fullNameMap;
	}
	
	public  void init(String file) throws Exception{
		
		SAXReader reader = new SAXReader();
		Document doc=reader.read(new File(file));
		Element root=doc.getRootElement();
		
		StringBuilder allKeyPlusKnowlID = new StringBuilder();
		
		for(Object o:root.elements("entity")){
			
			Element e=(Element)o;
			Entity entity=new Entity();
			String id=e.element("enity_id").getText();
			entity.setEntityId(id);
			
			allKeyPlusKnowlID.append(id);
			allKeyPlusKnowlID.append("/");
			
			String name=e.element("title").getText();
			//if(name.matches("^[a-zA-Z]*"))	name = name.toLowerCase(); //全英文，大写变小写
			entity.setName(name);
			
			allKeyPlusKnowlID.append(name);
			allKeyPlusKnowlID.append("/");
			
			if(name.contains("·"))  {
				for(String str:name.split("·"))  {
					allKeyPlusKnowlID.append(str);
					allKeyPlusKnowlID.append("/");
				}
				allKeyPlusKnowlID.append(name.replaceAll("·", " "));
				allKeyPlusKnowlID.append("/");
			}
			
			String category = e.element("category").getText();
			entity.setCategory(category);
			
			allKeyPlusKnowlID.append(category);
			allKeyPlusKnowlID.append("/");
			
			Map<String,String> properties=new HashMap<String,String>();
			List its=e.elements();
			
			
			for(int i = 0;i<its.size();i++){
				Element property=(Element)its.get(i);
				String key=property.getName();
				String value=property.getText();
				properties.put(key, value);
				//System.out.println(key);
				if(key.indexOf("简称")==0 || key.equals("abbreviation")|| key.equals("other_name") || key.equals("nickname")){
					if(value.split("[,、·]").length>1){						
						for(String otherName:value.split("[,、·]")){
							//System.out.println(otherName);
							allKeyPlusKnowlID.append(otherName);
							allKeyPlusKnowlID.append("/");
							//entityNameMap.put(otherName, entity);
						}
						allKeyPlusKnowlID.append(value);
						allKeyPlusKnowlID.append("/");
					}
					else{
						allKeyPlusKnowlID.append(value);
						allKeyPlusKnowlID.append("/");
						//entityNameMap.put(value, entity);
					}
				}
				
				else if(key.indexOf("别名")==0 || key.indexOf("又名")==0)  {
					if(name.split("[,、·]").length == 1)  {
						if(value.split("[·、,]").length>1){
							
							for(String otherName:value.split("[·、,]")){
								allKeyPlusKnowlID.append(otherName);
								allKeyPlusKnowlID.append("/");
								allKeyPlusKnowlID.append(otherName+name);
								allKeyPlusKnowlID.append("/");
								allKeyPlusKnowlID.append(name+otherName);
								allKeyPlusKnowlID.append("/");
								
							}
						}
						else  {
							allKeyPlusKnowlID.append(value);
							allKeyPlusKnowlID.append("/");
							allKeyPlusKnowlID.append(name+value);
							allKeyPlusKnowlID.append("/");
							allKeyPlusKnowlID.append(value+name);
							allKeyPlusKnowlID.append("/");
						}
					}
				}
			}
			
			entity.setProperty(properties);
			//entityNameMap.put(name, entity);
			String str = allKeyPlusKnowlID.toString();			
			zhishi.put(str, entity);
			
			allKeyPlusKnowlID.delete(0, allKeyPlusKnowlID.length());
			
		}
		
	}
	public static void main(String []args) throws Exception  {
		ReaderUtil reader=new ReaderUtil("./data/id&Name.txt");
		HashMap<String,String> map = new HashMap<String,String>();
		String line = null;
		while((line = reader.readLine()) != null)  
			map.put(line.split(":")[0], line.split(":")[1]);
		Entities entity = new Entities("./data/PKBase_zhwiki_1_small_edit2.xml");
		Map<String, ArrayList<Entity>> entityNameMap=entity.entityMultiNameMap;
		ArrayList<Entity> entList=entityNameMap.get("克林顿");
		KbAndCategory cat = new KbAndCategory();
		HashMap<String,String> kbAndCategoryMap = cat.kbAndCategory();
		String kb = "NIL";
		if(entList != null)  {
			String k = "";
			for(Entity entities : entList){
				if(entity!=null){					
					k += entities.getEntityId() + " ";						
				}
			}
			kb = k;
			System.out.println(kb);
		}
		
		if(kb.contains(" "))  {
			String []ws = kb.split(" ");
			boolean flag = false;
			String weibo_id = "kebi";
			String k = "";
//			String category = "体育\\球类运动\\篮球\\";
			HashMap<String,String> categoryMap = cat.category();
			String id = weibo_id.replaceAll("[0-9]", "");
			String categorys = categoryMap.get(id);
			for(String category:categorys.split(" "))  {
				System.out.println(category);
				for(String s:ws)  {
					String nameCat = kbAndCategoryMap.get(s);
					if(nameCat.contains("\\"))  {
						String cate = nameCat.split("\\\\")[0]+"\\"+nameCat.split("\\\\")[1]+"\\"+nameCat.split("\\\\")[2]+"\\";
						if(cate.equals(category))  {
							flag = true;
							k += s+" ";
//							System.out.println(s);
						}
					}
				}
				if(flag)  {
					System.out.println(k);
					kb = k;
					break;
				}
			}
		}
	}
}
