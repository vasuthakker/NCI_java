/**************************************************************************************************
 * File Name : MessageInfoCache.java
 * @author   : POOJA 
 * Version Information : 1.0
 * Reads the Application Messages properties file
 * Modification History:
 * Date		 Modified By		Description 
 *18-03-2015  POOJA			Reads the Application Messages properties file
 ************************************************************************************************* */
package ep.nci.exceptions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class defines method to read the Application Messages properties file and
 * store in a singleton instance
 * 
 * @author POOJA
 */
public class MessageInfoCache {
	private MessageInfoCache() {
	}

	private static MessageInfoCache instance = null;
	private static Properties properties = null;

	public static MessageInfoCache getInstance() {
		/**
		 * Creates new instance if not created and reads the properties file
		 */
		if (instance == null) {
			instance = new MessageInfoCache();
			properties = new MessageInfoCache()
					.getProperty("applicationMessages.properties");
		}
		return instance;
	}

	public Properties getProperty(String propertyFileName) {
		Properties properties = new Properties();
		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream(propertyFileName);

		if (inputStream != null) {
			try {
				properties.load(inputStream);
			} catch (IOException e) {
			}

		} else {
			try {
				throw new FileNotFoundException("property file '"
						+ propertyFileName + "' not found in the classpath");
			} catch (FileNotFoundException e) {
			}
		}
		return properties;
	}

	/**
	 * Gets the message string corresponding to the given message code
	 * 
	 * @param messageCode
	 * @return
	 */
	public static String getMessageString(String messageCode) {
		getInstance();
		return properties.getProperty(messageCode);
	}

}

