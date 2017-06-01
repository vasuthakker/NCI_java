package ep.nci.Service;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import epx.exception.SystemException;

public interface PinSetupService {
	
	public ObjectNode PinSet(JsonNode inputNode) throws SystemException; 

}
