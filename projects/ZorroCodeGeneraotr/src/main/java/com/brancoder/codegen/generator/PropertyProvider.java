package com.brancoder.codegen.generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;

import com.brancoder.codegen.util.PropertiesUtil;

public class PropertyProvider {
	static private PropertyProvider _instance = null;
	private static Map<String,String> map = new HashMap<String,String>();
	private Configurations configs = new Configurations();
	private Configuration config = null;
	private PropertiesUtil propertiesUtil = new PropertiesUtil();
	private static Properties props = new Properties();
			//propertiesUtil.loadTraditionalProperties("bin/Resource.properties");
	
	/**
	public PropertyProvider(String propertyFilePath){
		this.props = propertiesUtil.loadTraditionalProperties(propertyFilePath);	
	}
	**/

	public static String getValue(String key){
		if(props.containsKey(key)){
			return (String)props.get(key);
		}
		
		return "";
	}
	
    protected PropertyProvider(){
    	refresh();
    }
	
	
	
	public static Properties getProperties(){
		return props;
	}

	public static PropertyProvider instance(){
        if (_instance == null) {
            _instance = new PropertyProvider();
        }
        return _instance;
    }
    public static void refresh(){
		try {
			InputStream file = new FileInputStream(new File(
					"bin/Resource.properties"));
			props.load(file);

		} catch (Exception e) {
			System.out.println("error" + e);
		}    	
    }
}
