package org.min.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.min.entities.Entity;
import org.min.util.WriterUtil;
import org.min.weibos.LinkData;
import org.min.weibos.Weibo;
import org.min.weibos.Weibos;

public class WeiboCategory {

	private void category()   {
		HashMap<String,Integer> mapCat = new HashMap<String,Integer>();
		Weibos weibos;
		try {
			KbMap map = new KbMap();
			map.kbMap("data/PKBase_zhwiki_1_small_edit2.xml");
			
			weibos = new Weibos("data/sample_query_2014.xml");
			WriterUtil writer=new WriterUtil("./data/weibo.txt");
			List<Weibo> weiboList=weibos.getWeiboList();
			Map<String,Entity> entityKbMap=map.getEntityKbMap();
			String weibo_id = "";
			for(Weibo weibo:weiboList)   {
//				weibo_id=weibo.getId().replaceAll("[0-9]", "");
				Map<String,LinkData> linkDatas=weibo.getLinkDatas();
				for(String name:linkDatas.keySet()){
					LinkData linkData=linkDatas.get(name);								
					String kb = linkData.getKb();
					if(!kb.equals("NIL"))  {
						System.out.println(kb);
						Entity entity=entityKbMap.get(kb);
						if(!entity.getCategory().equals(""))  {
							if(!mapCat.containsKey(entity.getCategory()))
								mapCat.put(entity.getCategory(), 1);
							else
								mapCat.put(entity.getCategory(), mapCat.get(entity.getCategory())+1);
						}
					}					
				}
				if(!weibo_id.equals(weibo.getId().replaceAll("[0-9]", "")))  {
					writer.println();
					List<Map.Entry<String,Integer>> infoId = new ArrayList<Map.Entry<String,Integer>>(mapCat.entrySet());
					Collections.sort(infoId, new Comparator<Map.Entry<String, Integer>>() {   
					    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {      
					        return (o2.getValue() - o1.getValue()); 
					    }
					});
					for (int j = 0; j < infoId.size(); j++) {						
					    String id = infoId.get(j).toString();
					    writer.println(weibo_id+"\t"+id.split("=")[0]+"\t"+id.split("=")[1]);
					}
					weibo_id=weibo.getId().replaceAll("[0-9]", "");
					mapCat.clear();
				}
			}
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		WeiboCategory weibo = new WeiboCategory();
		weibo.category();
	}

}
