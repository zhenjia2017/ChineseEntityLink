package org.min.nlpp;

import java.io.IOException;
import java.util.ArrayList;

import org.liao.seg.client.Seg;
import org.min.segWordsTool.SegWords;

import liuyang.nlp.lda.main.LdaGibbsSampling;

public class ExtendProperty {
	private static Seg seg1 = Seg.getInstance();
	LdaGibbsSampling lda = new LdaGibbsSampling();//主题模型
	ArrayList<String> res = new ArrayList<String>();
	SegWords seg = new SegWords();
    public ArrayList<String> getProperty(ArrayList<String> list){
    	res = lda.getLDARecommendWords(list);//从主题模型返回ArrayList数组
    	return res;
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GetSegmentData g = new GetSegmentData();
		ExtendProperty p = new ExtendProperty();
		System.out.println("LDA特征提取结果为" + p.getProperty(g.getSegData("罗斯")));
//		String string = "";
//		for(String str:p.getProperty(g.getSegData("爵士")))  {
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
