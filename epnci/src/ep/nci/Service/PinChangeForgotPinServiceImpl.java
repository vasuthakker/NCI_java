package ep.nci.Service;

import static ep.nci.utils.Constants.STATUS;
import static ep.nci.utils.Constants.STATUS_LINE;
import static ep.nci.utils.Constants.STATUS_MESSAGE;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ep.nci.utils.Utils;
import epx.exception.SystemException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;

import ep.nci.enums.PINCHANGE;
import ep.nci.enums.PINSETUP;
import ep.nci.Dao.DeviceUserMappingDao;
import ep.nci.model.CorControlDtl;
import ep.nci.model.DeviceMst;
import ep.nci.model.DeviceUserMapping;

@Service
public class PinChangeForgotPinServiceImpl implements PinChangeForgotPinService {
	static final Logger logger = Logger.getLogger(PinChangeForgotPinServiceImpl.class);
	ObjectMapper mapper = new ObjectMapper();
	
	Utils utils = new Utils();
	
	@Autowired
	DeviceUserMappingDao deviceUserMappingDao;
	
	
	@Override
	public JsonNode PinChangeForgotPin(JsonNode inputNode) throws SystemException{
		logger.info("Start of PinChange process");
		
	/*	@SuppressWarnings("unused")
		ObjectNode requestHeaderNode = mapper.createObjectNode();
		ObjectNode requestDataNode = mapper.createObjectNode();
		ObjectNode responseNode = mapper.createObjectNode();

		requestHeaderNode = (ObjectNode) inputNode.get("HEADER");
		requestDataNode = (ObjectNode) inputNode.get("DATA");

		String mobileNumber = requestDataNode.get("mobileNumber").asText();
		String mNewPin = requestDataNode.get("mNewPin").asText();

		if (mNewPin == null || mNewPin.isEmpty()) {
			throw new SystemException(PINCHANGE.PIN_NOT_PROVIDED);
		}
		
		if (mobileNumber == null || mobileNumber.isEmpty()) {
			throw new SystemException(PINCHANGE.MOBILE_NUMBER_NOT_PROVIDED);
		}
		
		/*if (mOldPin.equals(mNewPin)) {
			throw new SystemException(PINCHANGE.PROVIDE_DIFFERENT_PIN);
		}*/
		
	/*	List<DeviceUserMapping> list = deviceUserMappingDao.changePinForgotPin(mobileNumber); 
		
		if(list.size()==0){
			throw new SystemException(PINCHANGE.NOT_REGISTERED);
		}
		if(list.size()>0){
			DeviceUserMapping usrPinChange;
			usrPinChange = list.get(0);
			usrPinChange.setPIN(mNewPin);
			usrPinChange = deviceUserMappingDao.updateInDB(usrPinChange);
			
			responseNode.put(STATUS, "100");
			responseNode.put(STATUS_LINE, "REQUEST_COMPLETE");
			responseNode.put(STATUS_MESSAGE,"REQUEST complete");
			
		} */
		
		
		@SuppressWarnings("unused")
		ObjectNode requestHeaderNode = mapper.createObjectNode();
		ObjectNode requestDataNode = mapper.createObjectNode();
		ObjectNode responseNode = mapper.createObjectNode();
		
		requestHeaderNode = (ObjectNode) inputNode.get("HEADER");
		requestDataNode = (ObjectNode) inputNode.get("DATA");
		
		String mobileNumber = requestDataNode.get("mobileNumber").asText();
		String mPin1 = requestDataNode.get("mPin1").asText();
		String mPin2 = requestDataNode.get("mPin2").asText();
		
		if(mobileNumber == null || mobileNumber.isEmpty()){
			throw new SystemException(PINSETUP.MOBILE_NUMBER_NOT_PROVIDED);
		}
		if(mPin1 == null || mPin1.isEmpty()){
			throw new SystemException(PINSETUP.PIN_NOT_PROVIDED);
		}
		if(mPin2 == null || mPin2.isEmpty()){
			throw new SystemException(PINSETUP.PIN_NOT_PROVIDED);
		}
		
	/*	List<DeviceUserMapping> duplicateEntryList = deviceUserMappingDao.findDuplcates(mobileNumber);
		if(duplicateEntryList.size()>0){
			throw new SystemException(PINSETUP.DEVICE_IS_ALREADY_SETUP);
		}   */
		
		
		List<DeviceUserMapping> list = deviceUserMappingDao.changePinForgotPin(mobileNumber);
		
		if(list.size()>0){
			list.get(0);
			if(String.valueOf(mPin1).equals(String.valueOf(mPin2))){
				String mPin = mPin1;
				
				DeviceUserMapping usrPin = new DeviceUserMapping();
				
				usrPin.setMobileNo(mobileNumber);
				usrPin.setPIN(mPin);
				
			//	CorControlDtl devicePINStatus = corControlDtlDao.findControlDtlByCode("DevicePINStatus.Active");
			//	usrPin.setPinStatus(devicePINStatus);
				usrPin = deviceUserMappingDao.updateInDB(usrPin);
				
				responseNode.put(STATUS, "100");
				responseNode.put(STATUS_LINE, "REQUEST_COMPLETE");
				responseNode.put(STATUS_MESSAGE,"REQUEST complete");
				
				
			} else {
				throw new SystemException(PINSETUP.PIN_NOT_MATCHING);	
			}	
		} else {
			throw new SystemException(PINSETUP.MOBILE_NUMBER_NOT_REGISTERED);
		}	
		
	   return responseNode;
	}

}

