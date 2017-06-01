package ep.nci.Dao;

import ep.nci.model.CorControlDtl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CorControlDtlDaoImpl implements CorControlDtlDao {
	
	public static final Logger logger = Logger.getLogger(CorControlDtlDaoImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public CorControlDtl findControlDtlByCode(String verificicationMsg){
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(CorControlDtl.class);
		crit.add(Restrictions.eq("controlDtlCode",verificicationMsg));
		List<CorControlDtl> list =crit.list();
		return list.get(0);
		
		
	} 
}
