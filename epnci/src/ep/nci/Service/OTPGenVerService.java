package ep.nci.Service;

import javax.mail.MessagingException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import ep.nci.model.DeviceMst;
import epx.exception.SystemException;

public interface OTPGenVerService {
	
	public ObjectNode generateOtp(JsonNode inputNode) throws SystemException, MessagingException;
	public ObjectNode verifyOtp(JsonNode inputNode) throws SystemException; 
	

}
