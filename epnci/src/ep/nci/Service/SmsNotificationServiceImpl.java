package ep.nci.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

//import ep.merchantapp.dao.SMSHistoryDao;
//import ep.merchantapp.model.SMSHistory;
import ep.nci.utils.Utils;

@Service
public class SmsNotificationServiceImpl implements NotificationService {

	static final Logger logger = Logger.getLogger(SmsNotificationServiceImpl.class);
	
	//@Autowired
	//private SMSHistoryDao smsHistoryDao;
	
	@Override
	public Boolean sendNotification(String contactNo, String message) {

		logger.info("sending sms");
		String text1 = message.replace(" ", "%20");
		logger.debug("::::::" + text1);
		
		
		//logger.debug("::::::" + message);

		
		try {
			Utils utils = Utils.getInstance();			
			String urlTemplate = utils.getSetting("mobile.message.service");
			String sendUrl = urlTemplate.replace("${text}", text1);
			//String sendUrl = urlTemplate.replace("${text}", message);
			sendUrl = sendUrl.replace("${mobile}", contactNo);
			logger.debug("sendUrl"+sendUrl);
			HttpClient client = new DefaultHttpClient();			
			HttpGet req = new HttpGet(sendUrl);
			HttpResponse response = client.execute(req);
			InputStream output =  response.getEntity().getContent();
			
			Scanner scanner = new Scanner(output);
			StringBuffer responseStr = new StringBuffer(); 
			while(scanner.hasNext()) {
				responseStr.append(scanner.nextLine());
			}
			scanner.close();			
			Boolean sent = response.getStatusLine() != null && 
					response.getStatusLine().getStatusCode() == 200;
			if (sent) {
				/*SMSHistory smsHistory = new SMSHistory();
				smsHistory.setMobileNo(contactNo);
				smsHistory.setSmsText(message);
				smsHistory.setSmsServiceResponse(responseStr.toString());
				smsHistoryDao.add(smsHistory);*/
			}
		} catch (Exception e) {			
			logger.fatal("Error in Sending SMS", e);
			return Boolean.FALSE;
		} 
		return Boolean.TRUE;
	}
	@Override
	public Boolean isEnabled() {
		
		Utils utils;
		try {
			utils = Utils.getInstance();
			if ("no".equalsIgnoreCase(utils.getSetting("txn.notify.sms"))) {
				logger.error("SMS is disabled for Transaction notification");
				// Do not send SMS notification if the project is in 'dev' mode
				return Boolean.FALSE;
			}
		} catch (IOException e) {
			logger.error("error reading Transaction send SMS notification status", e);
		}
		
		return Boolean.TRUE;
	}
}