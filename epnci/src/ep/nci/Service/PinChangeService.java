package ep.nci.Service;

import org.codehaus.jackson.JsonNode;

import epx.exception.SystemException;

public interface PinChangeService {
	
	public JsonNode PinChange(JsonNode inputNode) throws SystemException;

}
