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

import ep.nci.enums.LOGIN;
import ep.nci.Dao.*;
import ep.nci.model.CorControlDtl;
import ep.nci.model.DeviceUserMapping;

@Service
public class LoginServiceImpl implements LoginService {
	static final Logger logger = Logger.getLogger(LoginServiceImpl.class);
	ObjectMapper mapper = new ObjectMapper();

	Utils utils = new Utils();

	@Autowired
	DeviceUserMappingDao deviceUserMappingDao;
	
	@Autowired
	CorControlDtlDao corControlDtlDao;

	@Override
	public JsonNode loginDetails(JsonNode inputNode) throws SystemException {
		logger.info("Start of Login process");
		
		@SuppressWarnings("unused")
		
		ObjectNode requestHeaderNode = mapper.createObjectNode();
		ObjectNode requestDataNode = mapper.createObjectNode();
		ObjectNode responseNode = mapper.createObjectNode();

		requestHeaderNode = (ObjectNode) inputNode.get("HEADER");
		requestDataNode = (ObjectNode) inputNode.get("DATA");

		String mobileNumber = requestDataNode.get("mobileNumber").asText();
		String mPin = requestDataNode.get("mPin").asText();

		if (mPin == null || mPin.isEmpty()) {
			throw new SystemException(LOGIN.PIN_NOT_PROVIDED);
		}
		
		if (mobileNumber == null || mobileNumber.isEmpty()) {
			throw new SystemException(LOGIN.MOBILE_NUMBER_NOT_PROVIDED);
		}
		    
			List<DeviceUserMapping> ValidList = deviceUserMappingDao.checkMobileNumberAgainstMPin(mobileNumber, mPin);
			if (ValidList.size() == 0) {
				throw new SystemException(LOGIN.MOBILE_NUMBER_AND_PIN_NOT_MATCHING);
			}
			if(ValidList.size()>0){
				
				responseNode.put(STATUS, "100");
				responseNode.put(STATUS_LINE, "REQUEST_COMPLETE");
				responseNode.put(STATUS_MESSAGE,"REQUEST complete");
			}
			
		return responseNode;
	}

}
