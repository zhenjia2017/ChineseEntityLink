package org.min.nlpp;

import java.io.IOException;
import java.util.ArrayList;

import org.liao.seg.client.Seg;
import org.min.segWordsTool.SegWords;

import liuyang.nlp.lda.main.LdaGibbsSampling;

public class ExtendProperty {
	private static Seg seg1 = Seg.getInstance();
	LdaGibbsSampling lda = new LdaGibbsSampling();//����ģ��
	ArrayList<String> res = new ArrayList<String>();
	SegWords seg = new SegWords();
    public ArrayList<String> getProperty(ArrayList<String> list){
    	res = lda.getLDARecommendWords(list);//������ģ�ͷ���ArrayList����
    	return res;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GetSegmentData g = new GetSegmentData();
		ExtendProperty p = new ExtendProperty();
		System.out.println("LDA������ȡ���Ϊ" + p.getProperty(g.getSegData("��˹")));
//		String string = "";
//		for(String str:p.getProperty(g.getSegData("��ʿ")))  {
//			try {
//				if(seg1.maxSeg(str).contains("/nr") && string.length()<seg1.maxSeg(str).split("/")[0].length())  
//					string = seg1.maxSeg(str).split("/")[0];
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		System.out.println(string);
	}

}
