package org.min.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReaderUtil {

private BufferedReader reader;
	
	
	public ReaderUtil(File filepath) {
		this(filepath,"utf-8");
	}
	
	public ReaderUtil(File filepath,String code){
		try {
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(filepath),code));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ReaderUtil(String file,String code){
		this(new File(file),code);
	}
	
	public ReaderUtil(String file){
		this(new File(file));
	}
	
	public String readLine(){
		try {
			return reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void close(){
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
