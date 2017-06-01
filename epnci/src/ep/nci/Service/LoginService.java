package ep.nci.Service;

import org.codehaus.jackson.JsonNode;

import epx.exception.SystemException;

public interface LoginService {
	
	public JsonNode loginDetails(JsonNode inputNode) throws SystemException;
}
