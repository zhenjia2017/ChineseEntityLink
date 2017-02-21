package org.min.main;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.liao.seg.client.Seg;
import org.min.entities.Entities;
import org.min.entities.Entity;
import org.min.nlpp.ExtendProperty;
import org.min.nlpp.GetSegmentData;
import org.min.nlpp.WebCrawl;
import org.min.special.Match;
import org.min.special.NsLink;
import org.min.special.TongYiCi;
import org.min.test.KbAndCategory;
import org.min.util.ReaderUtil;
import org.min.util.WriterUtil;
import org.min.weibos.LinkData;
import org.min.weibos.Weibo;
import org.min.weibos.Weibos;

public class EntityLink {
	private static Seg seg = Seg.getInstance();
	private void edit(){
		ReaderUtil reader=new ReaderUtil("./data/PKBase_zhwiki_1_small.xml");
		WriterUtil writer=new WriterUtil("./data/PKBase_zhwiki_1_small_edit.xml");
		Pattern numPattern=Pattern.compile("^<\\d+");   //把数字开头的<>去掉
		//把以< <" <. <: <【开头或结尾的等去掉
		Pattern spiltPattern=Pattern.compile("<.*[(:/∶（　　、，》·】”—﹕-].*>.+<");
		Pattern oPattern=Pattern.compile(".*>.*&euro.*<");  //中间含有符号的去掉
		String line=null;
		while((line=reader.readLine())!=null){
			Matcher m1=numPattern.matcher(line);
			Matcher m2=spiltPattern.matcher(line);
			Matcher m3=oPattern.matcher(line);
			if(!m1.find()&&!m2.find()&&!m3.find()){
				if(line.contains("entity enity_id"))  {
					String []ws = line.split("<")[1].split(">")[0].split(" ");
					writer.println("  </entity><"+ws[0]+">");
					writer.println("    <enity_id>"+ws[1].split("=")[1].split("\"")[1]+"</enity_id>");
					writer.println("    <title>"+ws[2].split("=")[1].split("\"")[1]+"</title>");
				}
				else if(!line.contains("</entity>"))
					writer.println(line);
			}
			else
				System.out.println(line);
		}
		reader.close();
		writer.close();
	}
	
	private void link()   {		
		int total=0;
		int right=0;
		try {
			Entities entities = new Entities("./data/PKBase_zhwiki_1_small_edit2.xml");
			Weibos weibos=new Weibos("./data/weiboAutoTag_6.xml");
			TongYiCi tyc=new TongYiCi("./data/tongyici.txt");
			Match match = new Match();
			KbAndCategory cat = new KbAndCategory();
			HashMap<String,String> kbAndCategoryMap = cat.kbAndCategory();
			BufferedWriter resultWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./data/result1.txt"),"utf-8"));
			BufferedWriter wrongWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./data/wrong1.txt"),"utf-8"));
			
			List<Weibo> weiboList=weibos.getWeiboList();
			Map<String, ArrayList<Entity>> entityNameMap=entities.entityMultiNameMap;
			
			for(Weibo weibo:weiboList){
				String weibo_id=weibo.getId();
				Map<String,LinkData> linkDatas=weibo.getLinkDatas();
				total+=linkDatas.size();
				for(String name:linkDatas.keySet()){
					System.out.println(name);
					String nameSeg = seg.maxSeg(name);
					String kb="NIL";
					LinkData linkData=linkDatas.get(name);
					String nameTyc=tyc.getTyc(name);
					ArrayList<Entity> entList=entityNameMap.get(name);
					ArrayList<Entity> entTycList=entityNameMap.get(nameTyc);
					if(nameSeg.contains("/nr"))  {
						WebCrawl web = new WebCrawl();
						String fullName = web.getCrawl(name);
						ArrayList<Entity> entListFull=entityNameMap.get(fullName);
						if(entListFull != null)  {
							if(entListFull.size() == 1)  {
								kb = entListFull.get(0).getEntityId();
//								System.out.println(fullName);
//								System.out.println(kb);
							}
						}						
					}
					if(kb.equals("NIL"))  {
						if(entList!=null)  {
							if(entList.size()==1)  {
								kb = entList.get(0).getEntityId();
							}								
							else  {
								String k = "";
								for(Entity entity : entList){
									if(entity!=null){	
										k += entity.getEntityId() + " ";
									}
								}
								kb = k;
							}
						}
						if(entTycList!=null)  {
							if(entTycList.size()==1)
								kb = entTycList.get(0).getEntityId();
							else  {
								String k = "";
								for(Entity entity : entTycList){			
									if(entTycList!=null){					
										k += entity.getEntityId() + " ";
									}
								}
								kb = k;
							}
						}
						else  {							
							if(kb.equals("NIL"))  {
								Pattern p=Pattern.compile("[a-zA-Z]"); 
								Matcher mLow = p.matcher(name);
								if(mLow.find())  {
									String nameUpper = match.lowToUpper(name);
									if(!nameUpper.equals(""))  {
										ArrayList<Entity> entUpperList=entityNameMap.get(nameUpper);
										if(entUpperList != null)  {
											if(entUpperList.size()==1)
												kb = entUpperList.get(0).getEntityId();
											else  {
												String k = "";
												for(Entity entity : entUpperList){
													if(entity!=null){					
														k += entity.getEntityId() + " ";						
													}
												}
												kb = k;
											}
										}
									}															
								}
								
							}
						}
						if(kb.equals("NIL"))  	{									
							if(nameSeg.contains("ns") && nameSeg.split(" ").length==1)  {
								NsLink ns = new NsLink();
								kb = ns.nsLink(name);
							}
							else if(!nameSeg.contains("nr"))  {								
								kb = match.match(name);	
								if(!kb.equals("NIL"))  {
									if(kbAndCategoryMap.get(kb).contains("\\"))   {
										String strSeg = seg.maxSeg(kbAndCategoryMap.get(kb).split("\\\\")[kbAndCategoryMap.get(kb).split("\\\\").length-1]);
										//System.out.println(strSeg);
										if((strSeg.contains("/v")&&nameSeg.contains("/n")) || (strSeg.contains("/n")&&nameSeg.contains("/v")))
											kb = "NIL";
									}
									else  {
										String strSeg = seg.maxSeg(kbAndCategoryMap.get(kb));
										//System.out.println(strSeg);
										if((strSeg.contains("/v")&&nameSeg.contains("/n")) || (strSeg.contains("/n")&&nameSeg.contains("/v")))
											kb = "NIL";
									}
								}	
							}
								
						}
					}
//					if(kb.contains(" "))  {
//						boolean flag = false;
//						String []ws = kb.split(" ");
//						String k = "";
//						HashMap<String,String> categoryMap = cat.category();
//						String categorys = categoryMap.get(weibo_id.replaceAll("[0-9]", ""));
//						for(String category:categorys.split(" "))  {
//							for(String s:ws)  {
//								String nameCat = kbAndCategoryMap.get(s);
//								if(nameCat.contains("\\"))  {
//									String cate = nameCat.split("\\\\")[0]+"\\"+nameCat.split("\\\\")[1]+"\\"+nameCat.split("\\\\")[2]+"\\";
//									if(cate.equals(category))  {
//										flag = true;
//										k += s+" ";
//									}
//								}
//							}
//							if(flag)  {
//								kb = k.substring(0,k.length()-1);
//								break;
//							}
//						}
//					}
					if(kb.split(" ").length>1)  {
						String []ws = kb.split(" ");
						String k = "";
						for(String str:ws)  {
							String strSeg = "";
							if(kbAndCategoryMap.get(str).contains("\\"))
								strSeg = seg.maxSeg(kbAndCategoryMap.get(str.split("\\\\")[str.split("\\\\").length-1]));
							else
								strSeg = seg.maxSeg(kbAndCategoryMap.get(str));
							if(strSeg.split(" ").length == 1 &&!strSeg.split("/")[1].contains(nameSeg.split("/")[1]) && !nameSeg.split("/")[1].contains(strSeg.split("/")[1]))
								kb = kb.replaceAll(str+" ", "");
							else
								k += str+" ";
						}
						if(k.equals(""))
							kb = "NIL";
						else
							kb = k.substring(0,k.length()-1);
					}
//					else  {
//						if(!kb.equals("NIL"))  {
//							if(kbAndCategoryMap.get(kb).contains("\\"))   {
//								String strSeg = seg.maxSeg(kbAndCategoryMap.get(kb).split("\\\\")[kbAndCategoryMap.get(kb).split("\\\\").length-1]);
//								System.out.println(strSeg);
//								if(strSeg.split(" ").length != 1)
//									kb = "NIL";
//							}	
//						}											
//					}
					resultWriter.write(weibo_id+"	"+linkData.getId()+" "+kb+"\n");
					resultWriter.flush();
					if(kb.equals(linkData.getKb()))
						right++;
					else  {
						wrongWriter.write(weibo_id+"	"+linkData.getId()+" "+kb+" "+linkData.getName()+"	"+linkData.getKb()+"\n");
						wrongWriter.flush();
					}
				}
			}
			System.out.println("一共"+total+"个实体");
			System.out.println("正确连接"+right+"个实体");
			System.out.println("正确率为："+(double)right/total);
			resultWriter.close();
			wrongWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {

		EntityLink ent = new EntityLink();
		//ent.edit();
		ent.link();
	}

}
