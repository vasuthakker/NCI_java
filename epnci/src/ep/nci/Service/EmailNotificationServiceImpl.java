package ep.nci.Service;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import ep.nci.utils.Utils;




@Service
public class EmailNotificationServiceImpl implements NotificationService{

	static final Logger logger = Logger.getLogger(EmailNotificationServiceImpl.class);
	
	@Autowired
	private MailSender mailSender;
	
	/**
	 * 
	 */
	@Override
	public Boolean sendNotification(String receiver, String txtmessage) {
		
		logger.info("sending email");
		SimpleMailMessage message = new SimpleMailMessage();
		try {
			Utils utils = Utils.getInstance();
			message.setFrom(utils.getSetting("email.message.from"));  
		    message.setTo(receiver);  
		    message.setSubject(utils.getSetting("email.message.subject"));  
		    message.setText(txtmessage);  
		    //sending message  
		    mailSender.send(message); 
		    return Boolean.TRUE;
		} catch (IOException e) {
			logger.fatal("Error while sending email ",e);
		}
	    
	    logger.info("msg is send successfully");
		return Boolean.FALSE;
	}

	@Override
	public Boolean isEnabled() {
		Utils utils;
		try {
			utils = Utils.getInstance();
			if ("no".equalsIgnoreCase(utils.getSetting("txn.notify.mail"))) {
				// DO not send EMAIL notification if the project is dev mode
				logger.error("Email is disabled for Transaction notification");
				return Boolean.FALSE;
			}
		} catch (IOException e) {
			logger.fatal("Error while sending email ",e);
		}
		return Boolean.TRUE;
	}
}

