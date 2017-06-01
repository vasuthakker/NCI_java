package ep.nci.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import ep.nci.Dao.CorControlDtlDao;
import ep.nci.Dao.CorControlDtlDao;
import ep.nci.Dao.DeviceMstDao;
import ep.nci.enums.DEVICE;
import ep.nci.model.CorControlDtl;
import ep.nci.model.DeviceMst;
import ep.nci.utils.Constants;
import ep.nci.utils.MessageInfoCache;
import ep.nci.utils.StatusCodes;
import ep.nci.utils.Utils;
import epx.exception.SystemException;

@Service
public class OTPGenVerServiceImpl implements OTPGenVerService{
	static final Logger logger = Logger.getLogger(OTPGenVerServiceImpl.class);
	ObjectMapper mapper = new ObjectMapper();
	
	Utils utils = new Utils();
	
	
	@Autowired
	@Qualifier("notificationServiceSms")
	NotificationService smsNotificationService;

	@Autowired
	DeviceMstDao deviceMstDao;
	
	@Autowired
	CorControlDtlDao corControlDtlDao;
	
	public ObjectNode generateOtp(JsonNode inputNode) throws SystemException, MessagingException {
		logger.info("Start of generateOtp");

		ObjectNode responseNode = mapper.createObjectNode();
				/* Generate six digit random number */
				Integer randomOtp = Integer.valueOf(100000 + RandomUtils.nextInt(900000) + "");

		if (inputNode.has("DATA"))
			inputNode = inputNode.get("DATA");

		String mobileNumber = inputNode.get("mobileNumber").asText();
		String devMacAddr = inputNode.has("udid") ? inputNode.get("udid").asText(): "";

		String otpMessage = null;
		try{
		otpMessage = utils.getInstance().getSetting(Constants.CUSTOMER_OTP);
		}catch (IOException e) {
			e.printStackTrace();
		}
		logger.debug("otpmessage::" + otpMessage);
		otpMessage = otpMessage.replace("$OTP", randomOtp.toString());

		/* Send OTP to customer's mobile */
		
		Boolean isSend = Boolean.FALSE;
		isSend = smsNotificationService.sendNotification(mobileNumber, otpMessage);
		DeviceMst userOtp = new DeviceMst();

		if (isSend) {

			userOtp.setDevMobileNumber(mobileNumber);
			userOtp.setDevOtp(randomOtp);
			userOtp.setPosDevOtpSentDatetime(new Date());
			userOtp.setDevMacAddr(devMacAddr);
			userOtp.setDevOtpAttempt(0);
			userOtp.setDevOtpStatus(null);
			/*userOtp.setPosDevOtpSentDatetime(new Date());
			userOtp.setPosDevOtpUpdatedDatetime(new Date());*/
			userOtp.setDevOtpStatus(null);
			//userOtp.setDevOsVersion(null);
			userOtp = deviceMstDao.addInDB(userOtp);
			
			responseNode.put(Constants.STATUS, "100");
			responseNode.put(Constants.STATUS_LINE, "REQUEST_COMPLETE");
			responseNode.put(Constants.STATUS_MESSAGE,"REQUEST complete");
		}
		return responseNode;
	}

	
	public ObjectNode verifyOtp(JsonNode inputNode) throws SystemException {
		logger.info("Start of verifyOtp");
		ObjectNode responseNode = mapper.createObjectNode();
		
		if (inputNode.has("DATA"))
			inputNode = inputNode.get("DATA");
		
		String mobileNumber = inputNode.get("mobileNumber").asText();
		Integer devOtp = inputNode.get("otp").asInt();
		String devMacAddress = inputNode.has("udid") ? inputNode.get("udid").asText() : "";
		String productCode = inputNode.has("productCode") ? inputNode.get("productCode").asText() : "";
		ObjectNode dataNode = mapper.createObjectNode();
		
		if (devOtp.toString().length() != 6) {
			logger.error("OTP invalid");
			throw new SystemException(DEVICE.OTP_NOT_PROVIDED);
		}
		
		if (mobileNumber == null || mobileNumber.isEmpty()) {
			throw new SystemException(DEVICE.MOBILE_NUMBER_NOT_PROVIDED);
		}
		
		DeviceMst usrotp;
		int counter = 0;

		List<DeviceMst> otplist = deviceMstDao.getUserOtpList( mobileNumber);
		
		if(otplist.size()==0){
				throw new SystemException(DEVICE.INVALID_OTP_OR_MOBILE_NUMBER);
			}

		if (otplist.size() > 0) {
			usrotp = otplist.get(0);
			if (String.valueOf(usrotp.getDevOtp()).equals(String.valueOf(devOtp))) {
				Date otpSentDate = usrotp.getPosDevOtpSentDatetime();
				Date otpReveiceDate = new Date();
				long timeDiffInSec = (otpReveiceDate.getTime() - otpSentDate.getTime()) / 1000;
				try {
					logger.debug("otp valid minutes" + utils.getInstance().getSetting(Constants.OTP_VALID_MINUTES));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					if (timeDiffInSec > Long.parseLong(utils.getInstance().getSetting(Constants.OTP_VALID_MINUTES)) * 60) {
						throw new SystemException(DEVICE.OTP_EXPIRED);
					}
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					if ((int) usrotp.getDevOtpAttempt() >= Integer
							.parseInt(utils.getInstance().getSetting(Constants.OTP_VALID_ATTEMPTS))) {
						logger.info("End of verifyOtp service method.");
						throw new SystemException(DEVICE.CUSTOMER_OTP_ATTEMPTS_EXCEEDS);
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
			CorControlDtl deviceOtpStatus = corControlDtlDao.findControlDtlByCode("DeviceOTPStatus.Verified");
			usrotp.setDevOtpStatus(deviceOtpStatus);
			usrotp.setDevOtpAttempt(Integer.valueOf(String.valueOf(++counter)));
			CorControlDtl deviceStatus = corControlDtlDao.findControlDtlByCode("DeviceStatus.Active");
			usrotp.setDevStatus(deviceStatus);
			
				/*	responseNode.put(STATUS, "100");
					responseNode.put(STATUS_LINE, "REQUEST_COMPLETE");
					responseNode.put(STATUS_MESSAGE,"REQUEST complete");*/

			usrotp = deviceMstDao.updateinDB(usrotp);
			logger.debug("mappingstatus" + usrotp.getDevStatus().getControlDtlName());
			if ("Active".equals(usrotp.getDevStatus().getControlDtlName())) {
				dataNode.put("mappingStatus", true);
			} else {
				dataNode.put("mappingStatus", false);
			}
			
			
			/* * String messageStr = MessageInfoCache
			 * .getMessageString("OTP_SUCCESSFULLY_VERIFIED");
			 * responseNode.put(STATUS, StatusCodes.getCode(messageStr));
			 * responseNode.put(STATUS_LINE, "OTP_SUCCESSFULLY_VERIFIED");
			 * responseNode.put(STATUS_MESSAGE,
			 * StatusCodes.getMessage(messageStr)); */
			 
			SystemException ex = new SystemException(DEVICE.OTP_SUCCESSFULLY_VERIFIED);
			responseNode = (ObjectNode) StatusCodes.createStatusNode(ex);
			responseNode.put("DATA", dataNode);
			
			logger.info("End of verifyOtp");
			 return responseNode;
		} else {

			

			//dataNode.put("udid", deviceMacAddress);
			dataNode.put("mobileNumber", mobileNumber);
			//dataNode.put("token", token);
			usrotp.setPosDevOtpUpdatedDatetime(new Date());
			usrotp.setDevOtpAttempt((usrotp.getDevOtpAttempt() + (++counter)));

			usrotp = deviceMstDao.updateinDB(usrotp);

			
			  String messageStr = MessageInfoCache
			  .getMessageString("OTP_NOT_MATCH"); responseNode.put(Constants.STATUS,
			  StatusCodes.getCode(messageStr));
			  responseNode.put(Constants.STATUS_LINE, "OTP_NOT_MATCH");
			  responseNode.put(Constants.STATUS_MESSAGE,
			  StatusCodes.getMessage(messageStr));
			 
			SystemException ex = new SystemException(DEVICE.OTP_NOT_MATCH);
			responseNode = (ObjectNode) StatusCodes.createStatusNode(ex);
			responseNode.put("DATA", dataNode);

			logger.info("End of verifyOtp");
			 return responseNode;
			}

		}
		return responseNode;
	}


}