package org.min.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class WriterUtil {

private PrintWriter writer;
	
	public WriterUtil(String file){
		this(file,false,"utf-8");
	}
	public WriterUtil(String file,boolean append){
		this(file,append,"utf-8");
	}
	public WriterUtil(String file,boolean append,String code){
		try {
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,append),code)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void println(String str){
		writer.println(str);
	}
	public void println(){
		writer.println();
	}
	public void print(String str){
		writer.print(str);
	}
	
	public void close(){
		writer.flush();
		writer.close();
	}

}
