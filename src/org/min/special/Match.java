package org.min.special;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.min.util.ReaderUtil;

public class Match {
	public String lowToUpper(String name)  {
		String result = "";
		Pattern p=Pattern.compile("[a-zA-Z]"); 
		Matcher m = p.matcher(name);
		if(m.find())  {
			char []chs = name.toCharArray();
			for(char ch:chs)  {
				if(ch>='a' && ch<='z')
					result += (char) (ch - 'a' + 'A');
				else if(ch>='A' && ch<='Z')
					result += (char) (ch - 'A' +'a');
				else
					result += ch;
			}
		}
		return result;
	}
	
	public String match(String name)    {
		char []chs = name.toCharArray();
		Pattern p = Pattern.compile("[0-9]");
		ReaderUtil reader=new ReaderUtil("./data/id&Name.txt");
		float max = 0;
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
			Matcher m = p.matcher(string);
			if(max < (float)num/(float)chs.length && !m.find())  {
				max = (float)num/(float)chs.length;
//				max2 = (float)num/(float)string.length();
				result = line.split(":")[0];
			}
		}
		if(max==1)  
			return result;		
		else
			return "NIL";
	}
	
	public static void main(String []args)  {
		Match m = new Match();
		System.out.println(m.match("ËÕÃÅÁç"));
	}

}
