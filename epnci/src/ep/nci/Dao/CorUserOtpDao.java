package ep.nci.Dao;

import java.util.Date;
import java.util.List;

//import ep.common.dao.GenericDAO;
import ep.nci.model.CorUserOtp;

public interface CorUserOtpDao  {
	
	public List<CorUserOtp> getUserOtpList(String otp,String mobileNumber);

}

