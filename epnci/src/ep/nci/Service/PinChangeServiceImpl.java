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
import ep.nci.Dao.DeviceUserMappingDao;
import ep.nci.model.CorControlDtl;
import ep.nci.model.DeviceUserMapping;

@Service
public class PinChangeServiceImpl implements PinChangeService {
	static final Logger logger = Logger.getLogger(PinChangeServiceImpl.class);
	ObjectMapper mapper = new ObjectMapper();
	
	Utils utils = new Utils();
	
	@Autowired
	DeviceUserMappingDao deviceUserMappingDao;
	
	@Override
	public JsonNode PinChange(JsonNode inputNode) throws SystemException{
		logger.info("Start of PinChange process");
		
		ObjectNode requestHeaderNode = mapper.createObjectNode();
		ObjectNode requestDataNode = mapper.createObjectNode();
		ObjectNode responseNode = mapper.createObjectNode();

		requestHeaderNode = (ObjectNode) inputNode.get("HEADER");
		requestDataNode = (ObjectNode) inputNode.get("DATA");

		String mobileNumber = requestDataNode.get("mobileNumber").asText();
		String mOldPin = requestDataNode.get("mOldPin").asText();
		String mNewPin = requestDataNode.get("mNewPin").asText();

		if (mOldPin == null || mOldPin.isEmpty()) {
			throw new SystemException(PINCHANGE.PIN_NOT_PROVIDED);
		}
		
		if (mNewPin == null || mNewPin.isEmpty()) {
			throw new SystemException(PINCHANGE.PIN_NOT_PROVIDED);
		}
		
		if (mobileNumber == null || mobileNumber.isEmpty()) {
			throw new SystemException(PINCHANGE.MOBILE_NUMBER_NOT_PROVIDED);
		}
		
		if (mOldPin.equals(mNewPin)) {
			throw new SystemException(PINCHANGE.PROVIDE_DIFFERENT_PIN);
		}
		
		List<DeviceUserMapping> list = deviceUserMappingDao.changePin(mobileNumber, mOldPin); 
		if(list.size()>0){
			DeviceUserMapping usrPinChange;
			usrPinChange = list.get(0);
			usrPinChange.setPIN(mNewPin);
			usrPinChange = deviceUserMappingDao.updateInDB(usrPinChange);
			
			responseNode.put(STATUS, "100");
			responseNode.put(STATUS_LINE, "REQUEST_COMPLETE");
			responseNode.put(STATUS_MESSAGE,"REQUEST complete");
			
		} else {
			throw new SystemException(PINCHANGE.INCORRECT_OLD_PIN);
		}
		
	   return responseNode;
	}

}
