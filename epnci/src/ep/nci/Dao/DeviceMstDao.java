package ep.nci.Dao;

import java.util.List;

import ep.nci.model.DeviceMst;

public interface DeviceMstDao {
	
	public List<DeviceMst> getUserOtpList(String mobileNumber);

	public DeviceMst addInDB(DeviceMst usrOtp);
	
	public DeviceMst updateinDB(DeviceMst usrotp);
	
	public List<DeviceMst> DeviceStatusCheckForPinSetup(String mobileNumber ,Integer DeviceStatusId );

}
