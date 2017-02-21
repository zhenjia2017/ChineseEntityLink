package org.min.nlpp;

import java.util.ArrayList;

import org.min.segWordsTool.SegWords;


public class GetSegmentData {

	SegWords seg = null;
	ArrayList<String> list = null;
	String resultBysegmented;
	/**
	 * �԰ٶ��еĽ�����зִʣ������طִʽ��
	 * @param question   Ҫ��ѯ������
	 * @return  ArrayList<String>����
	 */
    public ArrayList<String> getSegData(String question){
    	ArrayList<String> res = new ArrayList<String>();
    	Crawler c = new Crawler();  //ץȡ��ҳ
    	seg = new SegWords();  //��ʼ�ִ�
    	list = c.getChinesChar(question);
    	
    	//-----------------�������ʧ�ܣ�������������ҳ-----------------
    	if(list.size() == 0){
    		
    		do{
    			try {
    				System.out.println("������ҳʧ�ܣ����¿�ʼ���س��򡣡���");	 
    				Thread.sleep(100000);
    				list = c.getChinesChar(question);//�������Ϊ�գ���ȥ�ٶ�������ҳ��ֱ���������Ϊֹ
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
        		
    		}while(list.size() != 0);
    	}
    	
    	
    	
    	
    	for(String s:list){
    		//System.out.println("Ҫ�ִʵ���ҳ����:"+s);
    		resultBysegmented = seg.toSegWordsByLocality(s).replaceAll("[a-z]|/", "");
    		res.add(resultBysegmented);
    	}
    	
      System.out.println("�ִʺ�Ľ��");
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
