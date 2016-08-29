package com.cn.dao.inter.login;

import java.util.List;

import com.cn.entity.login.BriberyMoney;

/**
 * 红包配置DAO
 * @author songzhili
 * 2016年7月14日下午2:00:09
 */
public interface BriberyMoneyDao {
   
	public boolean add(BriberyMoney money)throws Exception;
	/****/
	public boolean delete(String id)throws Exception;
	/****/
	public boolean update(BriberyMoney money)throws Exception;
	/****/
	public BriberyMoney obtain(String id)throws Exception;
	/****/
	public <T> List<T> obtainList(Class<T> clazz,Integer currentPage,Integer pageSize) throws Exception;
	/****/
	public <T> List<T> obtainListByBriberyMoneyName(String briberyMoneyName,Class<T> clazz,Integer currentPage,Integer pageSize) throws Exception;
	/****/
	public int countByBriberyMoneyName(String briberyMoneyName) throws Exception;
	/****/
	public int count()throws Exception;
}
