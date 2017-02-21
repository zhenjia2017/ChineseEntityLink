package org.min.weibos;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class Weibos {

	private List<Weibo> weiboList;
	
	public Weibos(String file) throws Exception{
		weiboList=new ArrayList<Weibo>();
		init(file);
	}
	
	
	public List<Weibo> getWeiboList() {
		return weiboList;
	}


	public void setWeiboList(List<Weibo> weiboList) {
		this.weiboList = weiboList;
	}


	public void init(String file) throws Exception{
		int count = 0;
		Pattern pattern = Pattern.compile("[《（【“<*\\s+*>”】）》]");
		Pattern lowerPattern = Pattern.compile("[a-z*]");
		Pattern uperPattern = Pattern.compile("[A-Z]+$");
		SAXReader reader = new SAXReader();
		Document doc=reader.read(new File(file));
		Element root=doc.getRootElement();
		for(Object o:root.elements("weibo")){
			Element e=(Element)o;
			Weibo weibo=new Weibo();
			weiboList.add(weibo);
			String id=e.attributeValue("id");  //此处的id是微博的id
			//System.out.println(id);
			weibo.setId(id);
			Iterator<?> it=e.elementIterator();
			LinkData currentLinkData=null;
			Map<String,LinkData> linkDatas=new LinkedHashMap<String,LinkData>();
			weibo.setLinkDatas(linkDatas);
			while(it.hasNext()){
				Element sub=(Element)it.next();
//				if(sub.getName().equals("content")){
//					weibo.setContent(sub.getText());
//				}
				if(sub.getName().equals("name")){
					count++;
					Matcher match = pattern.matcher(sub.getText());
					Matcher lowermatch = lowerPattern.matcher(sub.getText());
					Matcher upermatch = uperPattern.matcher(sub.getText());
					if(currentLinkData!=null){
						linkDatas.put(currentLinkData.getName(), currentLinkData);
					}
					LinkData linkData=new LinkData();
					linkData.setId(sub.attributeValue("id"));
					if(match.find())  {
						String []ws = sub.getText().split("[《（【“<\\s+>”】》）]"); //匹配符号
						linkData.setName(ws[1]);
					}
					else if(lowermatch.find()) {
						String uper = sub.getText().toUpperCase();	
						//System.out.println(uper);
						linkData.setName(uper);		
					}
					else if(upermatch.find())  {
						String lower = sub.getText().toLowerCase();
						//System.out.println(lower);
						linkData.setName(lower);
					}
					else
						linkData.setName(sub.getText());
					currentLinkData=linkData;
				}
				else if(currentLinkData!=null){
					if(sub.getName().equals("startoffset"))
						currentLinkData.setStartoffset(Integer.parseInt(sub.getText()));
					if(sub.getName().equals("endoffset"))
						currentLinkData.setEntoffset(Integer.parseInt(sub.getText()));
					if(sub.getName().equals("kb"))
						currentLinkData.setKb(sub.getText());
					
				}
			}
			if(currentLinkData!=null){
				linkDatas.put(currentLinkData.getName(), currentLinkData);
			}
			
		}
		System.out.println(count);
		
	}

	public static void main(String []args) throws Exception  {
		Weibos wb = new Weibos("./data/weiboAutoTag_6.xml");
	}
}
