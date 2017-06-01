package ep.nci.Dao;

import java.util.List;
import ep.nci.model.DeviceUserMapping;

public interface DeviceUserMappingDao {
	
	public List<DeviceUserMapping> checkMobileNumberAgainstMPin(String mobileNumber, String mPin);
	
	public DeviceUserMapping saveInDB(DeviceUserMapping usrPin);
	
	public DeviceUserMapping updateInDB(DeviceUserMapping usrPin);
	
	public List<DeviceUserMapping> findDuplcates(String mobileNumber);
	
	public List<DeviceUserMapping> changePin(String mobileNumber, String oldPin);
	
	public List<DeviceUserMapping> changePinForgotPin(String mobileNumber);
	

}
