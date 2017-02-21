package org.min.test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.min.entities.Entity;
import org.min.special.ForeignName;

public class Test {
	
	public static void main(String []args)   {
		String string = "2008Ó¢³¬";
		Pattern p = Pattern.compile("[0-9]");
		Matcher m = p.matcher(string);
		System.out.println(m.find());
	}
}
