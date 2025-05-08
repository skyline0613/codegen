package com.brancoder.codegen.util;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesUtil {
	public Properties loadTraditionalProperties(final String filePathAndName) {
		final Properties properties = new Properties();
		try {
			//final FileInputStream in = this.getClass().getResourceAsStream(filePathAndName);
			final FileInputStream in = new FileInputStream(filePathAndName);
			properties.load(in);
			in.close();
		} catch (FileNotFoundException fnfEx) {
			System.err.println("Could not read properties from file "
					+ filePathAndName);
		} catch (IOException ioEx) {
			System.err.println("IOException encountered while reading from "
					+ filePathAndName);
		}
		return properties;
	}
	
    public void saveProperties(final String filePathAndName, Properties p) throws IOException
    {
        FileOutputStream fr = new FileOutputStream(filePathAndName,true);
        p.store(fr, "Properties");
        fr.close();
    }	
	

	/**
	 * Store provided properties in XML format.
	 *
	 * @param sourceProperties
	 *            Properties to be stored in XML format.
	 * @param out
	 *            OutputStream to which to write XML formatted properties.
	 */
	public void storeXmlProperties(final Properties sourceProperties,
			final OutputStream out) {
		try {
			sourceProperties.storeToXML(out, "This is easy!");
		} catch (IOException ioEx) {
			System.err.println("ERROR trying to store properties in XML!");
		}
	}

	/**
	 * Store provided properties in XML format to provided file.
	 *
	 * @param sourceProperties
	 *            Properties to be stored in XML format.
	 * @param pathAndFileName
	 *            Path and name of file to which XML-formatted properties will
	 *            be written.
	 */
	public void storeXmlPropertiesToFile(final Properties sourceProperties,
			final String pathAndFileName) {
		try {
			FileOutputStream fos = new FileOutputStream(pathAndFileName);
			storeXmlProperties(sourceProperties, fos);
			fos.close();
		} catch (FileNotFoundException fnfEx) {
			System.err.println("ERROR writing to " + pathAndFileName);
		} catch (IOException ioEx) {
			System.err.println("ERROR trying to write XML properties to file "
					+ pathAndFileName);
		}
	}
}
