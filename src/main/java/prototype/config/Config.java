package prototype.config;

import java.util.HashMap;
import java.util.Properties;

// Read variables from configuration file -> Source: https://www.opencodez.com/java/read-config-file-in-java.htm
public class Config {
	
	private Properties configFile;
	
	public Config() {
		
		this.configFile = new Properties();
		
		try {	
			configFile.load(Config.class.getResourceAsStream("/config.properties"));
		} catch (Exception eta){
		    eta.printStackTrace();
		}
	}
	   
	public HashMap<String, String> getAllProperties() {
		 HashMap<String, String> config = new HashMap<String, String>();
		  
		 for (String key: this.configFile.stringPropertyNames()) {
			 config.put(key, this.configFile.getProperty(key));
		 }
		  
		 return config;
	}
}