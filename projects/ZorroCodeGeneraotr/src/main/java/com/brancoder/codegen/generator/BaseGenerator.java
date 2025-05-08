package com.brancoder.codegen.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

public class BaseGenerator {
	private static Logger logger = Logger.getLogger(BaseGenerator.class);
	protected PropertyProvider propertyProvider = PropertyProvider.instance();	
	
	public File getFile(final String filePath){
		if(filePath == null || filePath.trim().length() == 0){
			throw new RuntimeException("File path can't be empty!");
		}
		File file = new File(filePath);
		if (!file.exists()) {
			logger.error("File not found!");
			throw new RuntimeException("File not found!");
		}
		return file;
	}
	
	public boolean writeFile(String destFile, String content) throws IOException{
		
		if(destFile == null || content == null)
			return false;
		
		File descFile;
		try {
			descFile = new File(destFile);
			descFile.getParentFile().mkdirs();
			FileWriter fileWriter = new FileWriter(descFile);
			fileWriter.write(content);
			fileWriter.flush();
			fileWriter.close();
			logger.info(String.format("Generate File:[%s] success!", destFile));			
			return true;

		} catch (IOException e) {
			logger.error(String.format("Generate File:[%s] fail!", destFile), e);
			throw e;
		}
	}
	
}
