package org.min.segWordsTool;

import java.io.IOException;

import org.liao.seg.client.Seg;

public class SegWords {

	public String Seg(String str){
	    Seg seg = Seg.getInstance();
		String res ="";
		try {
			res = seg.maxSeg(str);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public String toSegWordsByLocality(String words){
		
//		System.out.println("在分词内，传递进来的需要分词的文本为：" + words.toString().trim());
		//MinSeg nseg = MinSeg.getInstance(); //细粒度分词
		String res = Seg(words);
//		System.out.println("在分词内，分词的结果为：" + res);
		return res;
	}

}
