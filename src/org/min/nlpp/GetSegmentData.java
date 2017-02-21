package org.min.nlpp;

import java.util.ArrayList;

import org.min.segWordsTool.SegWords;


public class GetSegmentData {

	SegWords seg = null;
	ArrayList<String> list = null;
	String resultBysegmented;
	/**
	 * 对百度中的结果进行分词，并返回分词结果
	 * @param question   要查询的问题
	 * @return  ArrayList<String>数组
	 */
    public ArrayList<String> getSegData(String question){
    	ArrayList<String> res = new ArrayList<String>();
    	Crawler c = new Crawler();  //抓取网页
    	seg = new SegWords();  //开始分词
    	list = c.getChinesChar(question);
    	
    	//-----------------如何下载失败，则重新下载网页-----------------
    	if(list.size() == 0){
    		
    		do{
    			try {
    				System.out.println("下载网页失败，重新开始下载程序。。。");	 
    				Thread.sleep(100000);
    				list = c.getChinesChar(question);//如果返回为空，再去百度下载网页，直到下载完成为止
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
        		
    		}while(list.size() != 0);
    	}
    	
    	
    	
    	
    	for(String s:list){
    		//System.out.println("要分词的网页内容:"+s);
    		resultBysegmented = seg.toSegWordsByLocality(s).replaceAll("[a-z]|/", "");
    		res.add(resultBysegmented);
    	}
    	
      System.out.println("分词后的结果");
      int k = 1;
      for(String s:res){
//     	 System.out.println("------------------------------------------------------------------");
//     	 System.out.println("------------------------------page = "+k+"------------------------");
//     	 System.out.println("------------------------------------------------------------------");
//     	 System.out.println(s);
     	 k++;
      }
    	return res;

    }

}
