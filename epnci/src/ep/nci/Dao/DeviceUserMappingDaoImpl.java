package ep.nci.Dao;

import java.util.Date;
import java.util.List;


import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ep.nci.model.DeviceUserMapping;

@Repository
public class DeviceUserMappingDaoImpl implements DeviceUserMappingDao {
	
	static final Logger logger = Logger.getLogger(DeviceUserMappingDaoImpl.class);
	 
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public List<DeviceUserMapping> checkMobileNumberAgainstMPin(String mobileNumber, String mPin){
		logger.debug("Checking MobileNumber against PassWord");
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(DeviceUserMapping.class);
		crit.add(Restrictions.eq("mobileNo", mobileNumber));
		crit.add(Restrictions.eq("mPin", mPin));
		
		List<DeviceUserMapping> list = crit.list();
	
		return list;
		
	}
	
	@Override
	@Transactional
	public DeviceUserMapping saveInDB(DeviceUserMapping usrPin){
		logger.debug("Saving PIN");
		 sessionFactory.getCurrentSession().save(usrPin);
		 return usrPin;
	}
	
	@Override
	@Transactional
	public List<DeviceUserMapping> findDuplcates(String mobileNumber){
		logger.debug("Checking for Duplicate Entries");
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(DeviceUserMapping.class);
		crit.add(Restrictions.eq("mobileNo", mobileNumber));
		List<DeviceUserMapping> list = crit.list();
		
		return list;	
	}
	
	@Override
	@Transactional
	public DeviceUserMapping updateInDB(DeviceUserMapping usrPin){
		sessionFactory.getCurrentSession().update(usrPin);
		 return usrPin;
	}
	
	@Override
	@Transactional
	public List<DeviceUserMapping> changePin(String mobileNumber, String oldPin){
		logger.debug("Setting up New PIN");
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(DeviceUserMapping.class);
		crit.add(Restrictions.eq("mobileNo", mobileNumber));
		crit.add(Restrictions.eq("mPin", oldPin));
		List<DeviceUserMapping> list = crit.list();
		
		return list;	
	}
	
	@Override
	@Transactional
	public List<DeviceUserMapping> changePinForgotPin(String mobileNumber){
		logger.debug("Setting up New PIN");
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(DeviceUserMapping.class);
		crit.add(Restrictions.eq("mobileNo", mobileNumber));
		List<DeviceUserMapping> list = crit.list();
		
		return list;
	}
	
}
