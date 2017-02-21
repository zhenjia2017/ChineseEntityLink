package org.min.special;

import java.util.HashMap;
import java.util.Map;

import org.liao.util.ReaderUtil;

public class TongYiCi {

private Map<String,String> tycMap;
	
	public TongYiCi(String file){
		tycMap=new HashMap<String,String>();
		ReaderUtil reader=new ReaderUtil(file);
		String line=null;
		while((line=reader.readLine())!=null){
			String[] strs=line.split("\t");
			if(strs.length!=2)
				continue;
			tycMap.put(strs[0], strs[1]);
			tycMap.put(strs[1], strs[0]);
		}
		reader.close();
	}
	
	public String getTyc(String name){
		return tycMap.get(name);
	}
	
//	public static void main(String []args)  {
//		TongYiCi tyc = new TongYiCi("./data/tongyici.txt");
//		System.out.println(tyc.getTyc("cctv"));
//	}

}
