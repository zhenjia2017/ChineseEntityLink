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
		
//		System.out.println("�ڷִ��ڣ����ݽ�������Ҫ�ִʵ��ı�Ϊ��" + words.toString().trim());
		//MinSeg nseg = MinSeg.getInstance(); //ϸ���ȷִ�
		String res = Seg(words);
//		System.out.println("�ڷִ��ڣ��ִʵĽ��Ϊ��" + res);
		return res;
	}

}
