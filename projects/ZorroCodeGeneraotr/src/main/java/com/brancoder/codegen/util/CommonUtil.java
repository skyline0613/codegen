package com.brancoder.codegen.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;

public class CommonUtil {
	
	/**
	 * 
	 * @param str ex:MIR_ACCT_G
	 * @return mirAcctG
	 */
	public static String toCamelCase(String str) {
		return toCamelCase(str,false);
	}

	/**
	 * 
	 * @param str ex:MIR_ACCT_G
	 * @param firstCharUpper MirAcctG
	 * @return
	 */
	public static String toCamelCase(String str, boolean firstCharUpper) {
		String[] parts = str.split("_");
		String camelCaseString = "";
		try{
			for (String part : parts) {
				camelCaseString = camelCaseString + toProperCase(part);
			}
			if(!firstCharUpper){
				camelCaseString = camelCaseString.substring(0, 1).toLowerCase()
						+ camelCaseString.substring(1);			
			}
		}catch(Exception e){
			System.out.println("toCamelCase:"+str);
		}

		return camelCaseString;
	}	
	
	/**
	 * 首字大寫其他小寫
	 * @param str
	 * @return
	 */
	public static String toFirstCharUpperCase(final String str) {
		String camelCaseString = "";
		camelCaseString = str.substring(0, 1).toUpperCase()
				+ str.substring(1).toLowerCase();	

		return camelCaseString;
	}	
	
	public static String toProperCase(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}
	
	/**
	 * 是否為數值字串
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			System.out.println(str.charAt(i));
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static String prettyPrintHTML(String rawHTML)
	{    
		
		Parser parser = Parser.htmlParser();
		parser.settings(new ParseSettings(true, true)); // tag, attribute preserve case
		//Document doc = parser.parseInput(rawHTML, baseUrl);
		Document doc = parser.parseInput(rawHTML, "http://example.com/");
		//Document doc = Jsoup.parseBodyFragment(rawHTML);
		doc.outputSettings().indentAmount(4); 
		String html = doc.body().html();
		return html;
	}
	
	
	public static String toProgramName(String pgName) { 
		  StringBuilder nameBuilder = new StringBuilder(pgName.length());    
		  boolean capitalizeNextChar = false;

		  for (char c: pgName.toCharArray()) {
		    if (c == '-') {
		      capitalizeNextChar = true;
		      continue;
		    }
		    if (capitalizeNextChar) {
		      nameBuilder.append(Character.toUpperCase(c));
		    } else {
		      nameBuilder.append(c);
		    }
		    capitalizeNextChar = false;
		  }
		  
		  String str = nameBuilder.toString();
		  str = str.substring(0,1).toUpperCase() + str.substring(1);
		  
		  return str;
		}
	
	
}	
