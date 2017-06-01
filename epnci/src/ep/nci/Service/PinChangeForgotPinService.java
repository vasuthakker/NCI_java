package ep.nci.Service;

import org.codehaus.jackson.JsonNode;

import epx.exception.SystemException;

public interface PinChangeForgotPinService {
	
	public JsonNode PinChangeForgotPin(JsonNode inputNode) throws SystemException;


}
