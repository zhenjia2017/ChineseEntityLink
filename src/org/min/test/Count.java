package org.min.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Count {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try  {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("./data/result1.txt"), "utf-8"));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./data/result.txt"),"utf-8"));
			String line = null;
			Pattern p = Pattern.compile("<name id = \"(.*)\">(.*)</name>");
			int count = 0;
			while((line = reader.readLine()) != null)  {
				String str = line.split("\t")[1];
				String s = str.split(" ")[0];
				count++;
				writer.write(count+"\t"+"Institute of Noetics and Wisdom Team-A"+"\t"+line.split("\t")[0]+"\t"+s+"\t"+str.split(" ")[1]+"\n");
				writer.flush();
			}
			writer.close();
			System.out.println(count);
		}catch(IOException e)  {
			e.printStackTrace();
		}
	}

}
