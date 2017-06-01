package ep.nci.Dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//import ep.biller.model.BillerChrgDtl;
//import ep.common.dao.GenericDAOImpl;
//import ep.common.enums.Status;
//import ep.common.util.AppSettings;
import ep.nci.model.CorUserOtp;

@Repository
public class CorUserOtpDaoImpl implements CorUserOtpDao {

	static final Logger logger = Logger.getLogger(CorUserOtpDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public List<CorUserOtp> getUserOtpList(String otp,String mobileNumber) {
		logger.debug("Start getting data with the mention otp");
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(CorUserOtp.class);
		crit.add(Restrictions.eq("otp", otp));
		crit.add(Restrictions.eq("mobileNumber", mobileNumber));
		
		// TODO: add date criteria for expiry date of OTP
		
		List<CorUserOtp> list =crit.list();
		return list;
	}

}
