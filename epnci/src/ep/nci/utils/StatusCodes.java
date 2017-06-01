package ep.nci.utils;

import static ep.nci.utils.Constants.STATUS;
import static ep.nci.utils.Constants.STATUS_LINE;
import static ep.nci.utils.Constants.STATUS_MESSAGE;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import ep.nci.utils.MessageInfoCache;
import epx.exception.SystemException;

/**
 * Util class to get Integer code from MessageString and string message from
 * that.
 * 
 * @author VISHAL
 *
 */
public class StatusCodes {

	/**
	 * returns the Integer code from message string has code separated by '#'
	 * 
	 * @param messageString
	 * @return
	 */
	public static Integer getCode(String messageString) {
		messageString = messageString.trim();
		Integer status = 0;
		if (messageString.contains("#")) {
			status = Integer.valueOf(messageString.substring(0,
					messageString.indexOf("#")).trim());
		} else {
			messageString = MessageInfoCache.getMessageString(messageString);
			if (messageString.contains("#")) {
				status = Integer.valueOf(messageString.substring(0,
						messageString.indexOf("#")).trim());
			}
		}
		return status;
	}

	/**
	 * returns the string message from messageString has string message
	 * separated by '#'
	 * 
	 * @param messageString
	 *            String
	 * @return message String
	 */
	public static String getMessage(String messageString) {
		messageString = messageString.trim();
		if (messageString.contains("#")) {
			messageString = messageString.substring(
					messageString.indexOf("#") + 1, messageString.length());
		} else {
			messageString = MessageInfoCache.getMessageString(messageString);
			if (messageString.contains("#")) {
				messageString = messageString.substring(
						messageString.indexOf("#") + 1, messageString.length());
			}
		}
		return messageString;
	}

	
	/**
	 * Creates an ObjectNode and set the status, statusLine and statusMessage in
	 * the JsonNode and return that object so that from controller we can return
	 * this JSON string.
	 * 
	 * @param exception
	 * @return
	 */
	public static JsonNode createStatusNode(SystemException exception) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode responseNode = mapper.createObjectNode();
		responseNode.put(STATUS, exception.getErrorCode().getNumber().toString());
		responseNode.put(STATUS_LINE, exception.getErrorCode().toString());
		responseNode.put(STATUS_MESSAGE, exception.getErrorMessage());
		return responseNode;
	}
}
