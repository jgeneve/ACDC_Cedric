package com.acdc.cnoyel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Class Markdown that contains the methods relatives to the markdown
 * @author Jordan GENEVE
 * @version 1.0
 * @since 1.0
 */

public class PropertiesAccess {
	
	private static PropertiesAccess SINGLETON = null;
	private String propFileName = "config.properties";
	
	public static String LOCAL_REPOSITORY = "localRepository";

	ArrayList<String> result = new ArrayList<>();
	InputStream inputStream;
	FileOutputStream outputStream;
	
	public PropertiesAccess() {
		result = getPropValues();
	}
	
	public static PropertiesAccess getInstance() {
		if (SINGLETON == null) {
			SINGLETON = new PropertiesAccess();
		}
		
		return SINGLETON;
	}
	
	public String getLocalRepository() {
		return result.get(0);
	}
	
	public void changeLocalRepository(String value) {
		this.changePropertyValue(PropertiesAccess.LOCAL_REPOSITORY, value);
	}
 
	public ArrayList<String> getPropValues() {
		Properties prop = new Properties();
		
		try {
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			try {
				result.add(prop.getProperty(PropertiesAccess.LOCAL_REPOSITORY));
				prop.clear();
				inputStream.close();
			} catch (IOException e) {
				System.out.println("Exception: " + e);
			}
		}
		return result;
	}
	
	private void changePropertyValue(String key, String value) {
		Properties props = new Properties();
		
	    try {
	      //first load old one:
	      inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
	      //FileInputStream configStream = new FileInputStream(propsFileName);
	      props.load(inputStream);
	      inputStream.close();

	      //modifies existing or adds new property
	      props.setProperty(key, value);

	      //save modified property file
	      FileOutputStream output = new FileOutputStream(propFileName);
	      props.store(output, null);
	      output.close();
	      
	      result.clear();
	      result.add(value);

	    } catch (IOException ex) {
	      ex.printStackTrace();
	    }
	}
}
