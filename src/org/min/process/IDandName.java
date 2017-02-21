package org.min.process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.liao.seg.client.Seg;
import org.min.util.ReaderUtil;

public class IDandName {

	private static Seg seg = Seg.getInstance();
	
	private void iDandName()   {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./data/id&Name.txt"),"utf-8"));
			SAXReader reader = new SAXReader();
			Document doc=reader.read(new File("./data/PKBase_zhwiki_1_small_edit2.xml"));
			Element root=doc.getRootElement();
			for(Object o:root.elements("entity")){
				Element e=(Element)o;
				String id=e.element("enity_id").getText();
				writer.write(id+":");
				String name=e.element("title").getText();
				writer.write(name+"\n");
				writer.flush();
			}
			writer.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void nrIdAndName()   {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./data/id&Name/Nrid&Name.txt"),"utf-8"));
			ReaderUtil reader=new ReaderUtil("./data/id&Name.txt");
			String line = null;
			
			while((line = reader.readLine()) != null)  {
				if(!line.split(":")[1].contains("-"))  {
					String string = seg.maxSeg(line.split(":")[1]);
					if(string.contains("/nr"))   {
						writer.write(line+"\n");
						writer.flush();
					}
				}			
			}
			writer.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void nsIdAndName()   {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./data/id&Name/NsId&Name2.txt"),"utf-8"));
			ReaderUtil reader=new ReaderUtil("./data/id&Name.txt");
			String line = null;	
			
			while((line = reader.readLine()) != null)  {
				if(!line.split(":")[1].contains("-"))  {
					String string = seg.maxSeg(line.split(":")[1]);
					if(string.contains("/ns"))   {
						writer.write(line+"\n");
						writer.flush();
					}
				}	
			}
			writer.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void ntIdAndName()   {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./data/id&Name/NtId&Name.txt"),"utf-8"));
			ReaderUtil reader=new ReaderUtil("./data/id&Name.txt");
			String line = null;
			
			while((line = reader.readLine()) != null)  {
				if(!line.split(":")[1].contains("-"))  {
					String string = seg.maxSeg(line.split(":")[1]);
					if(string.contains("/nt"))   {
						writer.write(line+"\n");
						writer.flush();
					}
				}			
			}
			writer.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void nzIdAndName()   {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./data/id&Name/NzId&Name.txt"),"utf-8"));
			ReaderUtil reader=new ReaderUtil("./data/id&Name.txt");
			String line = null;
			
			while((line = reader.readLine()) != null)  {
				if(!line.split(":")[1].contains("-"))  {					
					if(line.contains("_"))  {
						line = line.split("_")[0];
					}
					String string = seg.maxSeg(line.split(":")[1]);
					if(string.contains("/ns"))  {
						writer.write(line+"\n");
						writer.flush();
					}
				}			
			}
			writer.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		IDandName in = new IDandName();
		//in.iDandName();
		//in.nrIdAndName();
		in.nsIdAndName();
		//in.ntIdAndName();
		//in.nzIdAndName();
	}

}
