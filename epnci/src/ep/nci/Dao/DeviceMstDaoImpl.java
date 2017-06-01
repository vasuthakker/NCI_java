package ep.nci.Dao;

import java.util.Date;
import java.util.List;


import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ep.nci.model.CorControlDtl;
import ep.nci.Dao.CorControlDtlDao;
import ep.nci.model.DeviceMst;

@Repository
public class DeviceMstDaoImpl implements DeviceMstDao {

	static final Logger logger = Logger.getLogger(DeviceMstDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public List<DeviceMst> getUserOtpList(String mobileNumber) {
		logger.debug("Start getting data with the mentioned OTP");
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(DeviceMst.class);
		//crit.add(Restrictions.eq("devOtp", devOtp));
		crit.add(Restrictions.eq("mobileNumber", mobileNumber));
		crit.addOrder(Order.desc("posDevOtpSentDatetime"));
		
		
		
		// TODO: add date criteria for expiry date of OTP
		
		List<DeviceMst> list =crit.list();
		return list;
	}
	
	@Override
	@Transactional
	public DeviceMst addInDB(DeviceMst usrOtp){
		 sessionFactory.getCurrentSession().save(usrOtp);
		 return usrOtp;
	}
	
	@Override
	@Transactional
	public DeviceMst updateinDB(DeviceMst usrotp){
		 sessionFactory.getCurrentSession().update(usrotp);
		 return usrotp;
	}
	
	@Override
	@Transactional
	public List<DeviceMst> DeviceStatusCheckForPinSetup(String mobileNumber,Integer DeviceStatusId){
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DeviceMst.class);
		criteria.add(Restrictions.eq("mobileNumber", mobileNumber));
		criteria.add(Restrictions.eq("devStatus.controlDtlId", DeviceStatusId));
		List<DeviceMst> list = criteria.list();
		
		return list;
		
	}

}