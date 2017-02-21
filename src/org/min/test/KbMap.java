package org.min.test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.min.entities.Entity;

public class KbMap {

	private Map<String,Entity> entityKbMap = new HashMap<String,Entity>();
	public Map<String, Entity> getEntityKbMap() {
		return entityKbMap;
	}
	
	public void kbMap(String file)   {
		
		try {
			SAXReader reader = new SAXReader();
			Document doc=reader.read(new File(file));
			Element root=doc.getRootElement();
			for(Object o:root.elements("entity")){
				Element e=(Element)o;
				Entity entity=new Entity();
				String id=e.element("enity_id").getText();
				//System.out.println(id);
				entity.setEntityId(id);
				String name=e.element("title").getText();
				entity.setName(name);
				String category=e.element("category").getText();
				entity.setCategory(category);
				Map<String,String> properties=new HashMap<String,String>();
				List its=e.elements();
				for(int i = 0;i<its.size();i++){
					Element property=(Element)its.get(i);
					String key=property.getName();
					String value=property.getText();
					properties.put(key, value);
				}
				entity.setProperty(properties);
				entityKbMap.put(id, entity);
			}
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
	}

}
