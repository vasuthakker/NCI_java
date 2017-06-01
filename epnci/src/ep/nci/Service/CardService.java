package ep.nci.Service;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import epx.exception.SystemException;

public interface CardService {
	
	public ObjectNode generalServiceCore(JsonNode inputNode, String callableServiceName,String routeServiceName,String checksum) throws SystemException;
	
	public ObjectNode cardLoad(ObjectNode inputNode);
	
	public ObjectNode txnHistory(ObjectNode inputNode);
	
	public ObjectNode checkBalance(ObjectNode inputNode);
}

