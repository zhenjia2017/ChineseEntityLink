package org.min.special;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class ForeignName {

	public String toPinYin(String str) {  
        String py = "";  
        String[] t = new String[str.length()];  
          
        char [] hanzi=new char[str.length()];  
        for(int i=0;i<str.length();i++){  
            hanzi[i]=str.charAt(i);  
        }  
          
        HanyuPinyinOutputFormat t1 = new HanyuPinyinOutputFormat();  
        t1.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        t1.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        t1.setVCharType(HanyuPinyinVCharType.WITH_V);  
          
        try {  
            for (int i = 0; i < str.length(); i++) {  
                if ((str.charAt(i) >= 'a' && str.charAt(i) < 'z')  
                        || (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z')  
                        || (str.charAt(i) >= '0' && str.charAt(i) <= '9')) {  
                    py += str.charAt(i);  
                } else {  
                        t = PinyinHelper.toHanyuPinyinStringArray(hanzi[i], t1); 
                        if(t==null)
                        	continue;
                        py=py+t[0];  
                    }  
                py=py+" ";
                } 
        } catch (BadHanyuPinyinOutputFormatCombination e) {  
            e.printStackTrace();  
        }  
  
        return py.trim().toString();
	}
	//ƴ������༭�㷨
	@SuppressWarnings("resource")
	public String Compares(String name) throws Exception  {
		
		String result = null;
		String []w1 = name.split("\\s+");
		int i = 0;
		BufferedReader reader = new BufferedReader(new FileReader("data/Output1.txt"));
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> list1 = new ArrayList<String>();
		String line = null;
		
		while((line=reader.readLine())!=null)  {  //��֪ʶ���е��������ֵ�ƴ������list
			String []ws = line.split(":");
			String []s = ws[1].split("\\s+");
			if(s.length == w1.length)  {
				list1.add(ws[0]);
				list.add(ws[1]);
			}
		}
		System.out.println(w1.length);
		double []sum = new double[list.size()];   //Ϊ֪ʶ����ÿ��ƴ����¼һ����ֵ
		for(int l = 0;l<list.size();l++)  {   
			String []w2 = list.get(l).split("\\s+");
			for(i =0;i<w1.length;i++)  {
				if(!w1[i].equals(w2[i]))  {    //�Ա�����ƴ����ͬʱ
						if((w1[i].matches("[z*]")&&w2[i].matches("[z*]"))||   //��ĸ������ͬ
								(w2[i].matches("[zh*]")&&w1[i].matches("[zh*]"))||
								(w1[i].matches("[c*]")&&w2[i].matches("[c*]"))||
								(w2[i].matches("[ch*]")&&w1[i].matches("[ch*]"))||
								(w1[i].matches("[l*]")&&w2[i].matches("[n*]"))||
								(w2[i].matches("[n*]")&&w1[i].matches("[n*]"))||
								(w1[i].matches("[si]")&&w2[i].matches("[si]"))||
								(w2[i].matches("[ci]")&&w1[i].matches("[ci]")))  {
							if((w1[i].matches("[*in]")&&w2[i].matches("[*ing]"))|| //��ĸ����Ҳ����
									(w2[i].matches("[*in]")&&w1[i].matches("[*ing]"))||
									(w1[i].matches("[*en]")&&w2[i].matches("[*eng]"))||
									(w2[i].matches("[*en]")&&w1[i].matches("[*eng]"))||
									(w1[i].matches("[*an]")&&w2[i].matches("[*ang]"))||
									(w2[i].matches("[*an]")&&w1[i].matches("[*ang]"))||
									(w1[i].matches("[*ang]")&&w2[i].matches("[*ong]"))||
									(w2[i].matches("[*ang]")&&w1[i].matches("[*ong]")))  {
								sum[l]+=0.5;    //�����ĸ������ͬ�����ƶ���Ҳ���ƣ���ֵ��0.5
							}
							else
								sum[l]+=1.0;    //����ĸ������ͬ����ֵ��1.0
						}
						else if((w1[i].matches("[z*]")&&w2[i].matches("[zh*]"))|| //��ĸ��������
								(w2[i].matches("[z*]")&&w1[i].matches("[zh*]"))||
								(w1[i].matches("[c*]")&&w2[i].matches("[ch*]"))||
								(w2[i].matches("[c*]")&&w1[i].matches("[ch*]"))||
								(w1[i].matches("[l*]")&&w2[i].matches("[n*]"))||
								(w2[i].matches("[l*]")&&w1[i].matches("[n*]")))  {
							sum[l]+=0.5;                                         //��ֵ��0.5
							if((w1[i].matches("[*in]")&&w2[i].matches("[*ing]"))||
									(w2[i].matches("[*in]")&&w1[i].matches("[*ing]"))|| 
									(w1[i].matches("[*en]")&&w2[i].matches("[*eng]"))||
									(w2[i].matches("[*en]")&&w1[i].matches("[*eng]"))||
									(w1[i].matches("[*an]")&&w2[i].matches("[*ang]"))||
									(w2[i].matches("[*an]")&&w1[i].matches("[*ang]"))||
									(w1[i].matches("[*ang]")&&w2[i].matches("[*ong]"))||
									(w2[i].matches("[*ang]")&&w1[i].matches("[*ong]")))  {
								sum[l]+=0.5;                   //�����ĸ����Ҳ���ƣ���ֵ��0.5
							}
							else if((w1[i].matches("[*in]")&&w2[i].matches("[*in]"))||
									(w2[i].matches("[*ing]")&&w1[i].matches("[*ing]"))||
									(w1[i].matches("[*en]")&&w2[i].matches("[*en]"))||
									(w2[i].matches("[*eng]")&&w1[i].matches("[*eng]"))||
									(w1[i].matches("[*an]")&&w2[i].matches("[*an]"))||
									(w2[i].matches("[*ang]")&&w1[i].matches("[*ang]"))||
									(w1[i].matches("[*ang]")&&w2[i].matches("[*ang]"))||
									(w2[i].matches("[*ong]")&&w1[i].matches("[*ong]")))  {
								sum[l]+=0.0;                         //�����ĸ������ͬ����ֵ��0
							}
							else
								sum[l]+=1.0;                 //�����ĸ���������ƣ���ֵ��1.0
						}
						else if((w1[i].matches("[si]+$")&&w2[i].matches("[ci]+$"))||
								(w2[i].matches("[si]+$")&&w1[i].matches("[ci]+$")))  {
							sum[l]+=0.5;                 //�����si��ci����ֵ��0.5
						}
						else  {
							sum[l]+=1.0;               //��ĸ���������ƣ���ֵ�ȼ�1.0
							if((w1[i].matches("[*in]")&&w2[i].matches("[*ing]"))||
									(w2[i].matches("[*in]")&&w1[i].matches("[*ing]"))||
									(w1[i].matches("[*en]")&&w2[i].matches("[*eng]"))||
									(w2[i].matches("[*en]")&&w1[i].matches("[*eng]"))||
									(w1[i].matches("[*an]")&&w2[i].matches("[*ang]"))||
									(w2[i].matches("[*an]")&&w1[i].matches("[*ang]"))||
									(w1[i].matches("[*ang]")&&w2[i].matches("[*ong]"))||
									(w2[i].matches("[*ang]")&&w1[i].matches("[*ong]")))  {
								sum[l]+=0.5;       //��ĸ�������ƣ���ֵ�ټ�0.5
							}
							else if((w1[i].matches("[*in]")&&w2[i].matches("[*in]"))||
							(w2[i].matches("[*ing]")&&w1[i].matches("[*ing]"))||
							(w1[i].matches("[*en]")&&w2[i].matches("[*en]"))||
							(w2[i].matches("[*eng]")&&w1[i].matches("[*eng]"))||
							(w1[i].matches("[*an]")&&w2[i].matches("[*an]"))||
							(w2[i].matches("[*ang]")&&w1[i].matches("[*ang]"))||
							(w1[i].matches("[*ang]")&&w2[i].matches("[*ang]"))||
							(w2[i].matches("[*ong]")&&w1[i].matches("[*ong]")))  {
								sum[l]+=0.0;     //�����ĸ������ͬ����ֵ��0
							}
							else
								sum[l]+=1.0;        //�����ĸ���������ƣ���ֵ�ټ�1.0
						}
				}
			}
		}
		for(int l = 0;l<list.size();l++)  {
			if((sum[l] != 0)&&sum[l]<1.0)  {
				//System.out.println(list1.get(l));
				result = list1.get(l);
				//System.out.println(sum[l]);
			}
		}
		return result;
	}

}
