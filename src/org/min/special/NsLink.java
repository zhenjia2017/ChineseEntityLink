package org.min.special;

import org.min.util.ReaderUtil;

public class NsLink {

	public String nsLink(String name)   {
		char []chs = name.toCharArray();
		ReaderUtil reader=new ReaderUtil("./data/id&Name/NsId&Name2.txt");
		float max = 0, max2 = 0;
		String result = "NIL";
		String line = null;
		
		while((line = reader.readLine()) != null)   {
			int num = 0;
			String string = line.split(":")[1];
			String str = string;
			for(char ch:chs)  {
				if(str.indexOf(ch) != -1)   {
					num++;
					if(str.indexOf(ch)<str.length())
						str = str.substring(str.indexOf(ch), str.length());
				}
			}
			if(max <= (float)num/(float)chs.length && max2 <= (float)num/(float)string.length())  {
				max = (float)num/(float)chs.length;
				max2 = (float)num/(float)string.length();
				result = line.split(":")[0];
			}			
		}
		if(max==1 && max2>=0.65)
			return result;
		else
			return "NIL";
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		NsLink ns = new NsLink();
		System.out.println(ns.nsLink("рк╠Ж"));
	}

}
