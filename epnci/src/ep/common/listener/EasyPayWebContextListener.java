/**************************************************************************************************
 * File Name : EasyPayWebContextListener.java
 * @author : Pooja
 * Version Information : 1.0
 * Listener designed for WebContext initialize and destroy.
 * Modification History:
 * Date			Modified By		Description 
 * 15-13-16 	Pooja			Created listener to do init and destroy tasks.  
 ************************************************************************************************* */
package ep.common.listener;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import ep.nci.utils.Utils;
import epx.exception.ExceptionUtil;


/**
 * Listener created for WebContext initialize and destroy.
 */
@WebListener
public class EasyPayWebContextListener implements ServletContextListener {

	private Logger logger;

	public  Utils utils = null;

	/**
	 * context destroyed method, for closing connections and resources.
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			Utils.getInstance().clear();
			logger.debug("epscms properties cleared correctly");
		} catch (IOException e) {
			logger.fatal("error clearing the epscms properties");
		}
		logger.info("Shutting Down epscms Application.");
	}

	/**
	 * context initialized method, for initializing some properties 
	 * and other resources. 
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		PropertyConfigurator.configure(Thread.currentThread()
				.getContextClassLoader().getResourceAsStream("resources/log4j.properties"));
		logger = Logger.getLogger(EasyPayWebContextListener.class);

		// Set the message codes from exception properties
		Properties messagess = new Properties();
		try {
			messagess.load(Thread.currentThread()
					.getContextClassLoader().getResourceAsStream("exception.properties"));
		} catch (IOException e1) {
			logger.fatal("Can not proceed because unable to read exception properties from file");
			e1.printStackTrace();
		}
		logger.debug("Exception property messages has been loaded " + messagess.keys().hasMoreElements());
		ExceptionUtil.getInstance(messagess);

		// Set the properties from db
		try{
			// Get instance of utils
			utils =Utils.getInstance();
		} catch (IOException e) {
			logger.fatal("Can not proceed because unable to read epscms properties from db");
		}

		logger.info("Starting epscms application.");		
	}

}
