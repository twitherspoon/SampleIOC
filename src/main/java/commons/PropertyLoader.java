package commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class PropertyLoader {
	
	String filename;
	public PropertyLoader(String filename) {
		this.filename = filename;
	}

	public String getPropertyValue(String key)
	{
		try
		{
			FileInputStream fis = new FileInputStream(new File(filename));
			Properties prop=new Properties();
			prop.load(fis);
			return prop.get(key).toString();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public void writePropertyValue(String key,String value) {
		try {
			Properties properties = new Properties();
			properties.setProperty(key, value);
			FileOutputStream fileOut = new FileOutputStream(new File(filename));
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
