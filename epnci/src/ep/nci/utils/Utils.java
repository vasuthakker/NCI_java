package ep.nci.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;

import com.google.common.base.Strings;

import ep.nci.enums.INPUT;
import epx.exception.SystemException;

public class Utils {

	static final Logger logger = Logger.getLogger(Utils.class);
		
	private Properties properties;	
	public static Utils instance;
	
	public Utils() {
		this.properties = new Properties();
	}
	
	/**
	 * call this method only in web app listener :: initialized
	 * @return
	 * @throws IOException
	 */
	public static Utils getInstance () throws IOException {
		if (instance == null) {
			instance = new Utils();
			InputStream stream = instance.getClass().getClassLoader()
					.getResourceAsStream("/epnci.properties");
			instance.properties.load(stream);
		}
		return instance;
	}
	
	public String getSetting(String key)  {
			
		return this.properties.getProperty(key) != null 
				? this.properties.getProperty(key).trim() : "";
	}
	
	public void clear() {
		properties.clear();
		properties = null;
		Utils.instance = null;
	}
	
	public void put(String key, String value) {
		this.properties.put(key, value);
	}
	
	
	public void verifyJsonForRequiredParam(JsonNode dataNode,
			String... paramList) throws SystemException{
		for(String reqParam : paramList){
			if(!dataNode.has(reqParam)){
				logger.error("EasyPay Exception :----------> Parameter not found in response : \""+reqParam+"\"");
				throw new SystemException(INPUT.REQUIRED_PARAMETER_NOT_FOUND);
			}
			if(dataNode.get(reqParam).asText().isEmpty() || dataNode.get(reqParam).asText() == null){
				logger.error("EasyPay Exception :----------> Parameter is empty or null : \""+reqParam+"\"");
				throw new SystemException(INPUT.REQUIRED_PARAMETER_NOT_FOUND);
			}
		}
	}
	
	public String parseDateFormat(String stringDate, String inputStringFormat, String outputStringFormat){

		String formatedString = "";
		try
		{
			SimpleDateFormat sdfSource = new SimpleDateFormat(inputStringFormat);
			Date date = sdfSource.parse(stringDate);
			SimpleDateFormat sdfDestination = new SimpleDateFormat(outputStringFormat);
			formatedString  = sdfDestination.format(date);
		}
		catch(ParseException pe)
		{

		}
		return formatedString;
	}
	
	public Date parseFromDate(String fromDate, String pattern) {

		DateFormat reqFormat = new SimpleDateFormat(pattern);		

		if (Strings.isNullOrEmpty(fromDate)) {
			Date date = new Date();
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
			return date;
		} else {
			try {
				Date frDate = reqFormat.parse(fromDate);
				frDate.setHours(0);
				frDate.setMinutes(0);
				frDate.setSeconds(0);
				return frDate;
			} catch (ParseException ex) {
				Date date = new Date();
				date.setHours(0);
				date.setMinutes(0);
				date.setSeconds(0);
				return date;
			}
		}
	}
	
	public Date parseToDate(String toDate, String pattern) {

		DateFormat reqFormat = new SimpleDateFormat(pattern);

		if (Strings.isNullOrEmpty(toDate)) {
			Date now = new Date();
			now.setHours(23);
			now.setMinutes(59);
			now.setSeconds(59);
			return now;
		} else {
			try {
				Date tDate = reqFormat.parse(toDate);
				tDate.setHours(23);
				tDate.setMinutes(59);
				tDate.setSeconds(59);
				return tDate;
			} catch (ParseException ex) {
				Date date = new Date();
				date.setHours(23);
				date.setMinutes(59);
				date.setSeconds(59);
				return date;
			}
		}
	}
	
}
