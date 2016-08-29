package com.cn.dao.impl.login;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cn.common.Utils;
import com.cn.dao.inter.login.BriberyMoneyDao;
import com.cn.entity.login.BriberyMoney;
/**
 * 红包配置DAO
 * @author songzhili
 * 2016年7月14日下午2:03:36
 */
@Transactional
@Repository("briberyMoneyDao")
public class BriberyMoneyDaoImpl implements BriberyMoneyDao {
    
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public boolean add(BriberyMoney money) throws Exception {
		if(money == null){
			return false;
		}
		getSession().save(money);
		return false;
	}

	@Override
	public boolean delete(String id) throws Exception {
		if(Utils.isEmpty(id)){
		   return false;	
		}
		BriberyMoney money = obtain(id);
		getSession().delete(money);
		return true;
	}

	@Override
	public boolean update(BriberyMoney money) throws Exception {
		
		if(money == null){
			return false;
		}
		getSession().update(money);
		return false;
	}

	@Override
	public BriberyMoney obtain(String id) throws Exception {
		
		if(Utils.isEmpty(id)){
		   return new BriberyMoney();	
		}
		BriberyMoney money = (BriberyMoney)getSession().get(BriberyMoney.class, id);
		return money;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> obtainList(Class<T> clazz,Integer currentPage,Integer pageSize) throws Exception {
		Criteria criteria = getSession().createCriteria(clazz);
		criteria.setFirstResult(currentPage*pageSize);
		criteria.setMaxResults(pageSize);
		criteria.setCacheable(false);
		criteria.addOrder(Order.desc("createTime"));
		List<T> list = criteria.list();
		return list;
	}

	@Override
	public int count() throws Exception {
		Criteria criteria = getSession().createCriteria(BriberyMoney.class);
		criteria.setCacheable(false);
		criteria.setProjection(Projections.rowCount());
		int result = Integer.parseInt(criteria.uniqueResult().toString());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> obtainListByBriberyMoneyName(String briberyMoneyName,
			Class<T> clazz, Integer currentPage, Integer pageSize)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(clazz);
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.like("briberyMoneyName", briberyMoneyName, MatchMode.ANYWHERE));
		criteria.setFirstResult(currentPage*pageSize);
		criteria.setMaxResults(pageSize);
		criteria.setCacheable(false);
		criteria.addOrder(Order.desc("createTime"));
		criteria.add(conjunction);
		List<T> list = criteria.list();
		return list;
	}

	@Override
	public int countByBriberyMoneyName(String briberyMoneyName)
			throws Exception {
		
		Criteria criteria = getSession().createCriteria(BriberyMoney.class);
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.like("briberyMoneyName", briberyMoneyName, MatchMode.ANYWHERE));
		criteria.add(conjunction);
		criteria.setCacheable(false);
		criteria.setProjection(Projections.rowCount());
		int result = Integer.parseInt(criteria.uniqueResult().toString());
		return result;
	}
}
