package com.cn.entity.login;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
/**
 * 红包配置
 * @author songzhili
 * 2016年7月14日下午1:44:50
 */
@Entity
@Table(name="PLUS_HB_BRIBERYMONEY")
@GenericGenerator(name = "system-uuid", strategy = "uuid")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BriberyMoney implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5979155717889742562L;

	/** 主键 **/
	@Id
	@GeneratedValue(generator = "system-uuid")
	@Column(name = "BRIBERYMONEY_ID", length = 32)
	private String briberyMoneyId;
	/** 名称 **/
	@Column(name = "BRIBERYMONEY_NAME", length = 200)
	private String briberyMoneyName;
	/** 类型 **/
	@Column(name = "BRIBERYMONEY_TYPE", length = 10)
	private String briberyMoneyType;
	/** 说明 **/
	@Column(name = "BRIBERYMONEY_EXPLAIN", length = 1000)
	private String briberyMoneyExplain;
	/** 开始时间 **/
	@Column(name = "START_TIME", length = 30)
	private String startTime;
	/** 结束时间 **/
	@Column(name = "END_TIME", length = 30)
	private String endTime;
	/** 创建时间 **/
	@Column(name = "CREATE_TIME", length = 30)
	private Date createTime;
	/** 规则 **/
	@Column(name = "RULE", length = 3000)
	private String briberyMoneyRule;
	/** 扩展参数 **/
	@Column(name = "EXPAND_PARAM", length = 2000)
	private String expandParam;
	/** 第三方渠道Id **/
	@Column(name = "THIRD_PARTYID", length = 100)
	private String thirdPartyId;
	public String getBriberyMoneyId() {
		return briberyMoneyId;
	}
	public void setBriberyMoneyId(String briberyMoneyId) {
		this.briberyMoneyId = briberyMoneyId;
	}
	public String getBriberyMoneyName() {
		return briberyMoneyName;
	}
	public void setBriberyMoneyName(String briberyMoneyName) {
		this.briberyMoneyName = briberyMoneyName;
	}
	public String getBriberyMoneyType() {
		return briberyMoneyType;
	}
	public void setBriberyMoneyType(String briberyMoneyType) {
		this.briberyMoneyType = briberyMoneyType;
	}
	public String getBriberyMoneyExplain() {
		return briberyMoneyExplain;
	}
	public void setBriberyMoneyExplain(String briberyMoneyExplain) {
		this.briberyMoneyExplain = briberyMoneyExplain;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getBriberyMoneyRule() {
		return briberyMoneyRule;
	}
	public void setBriberyMoneyRule(String briberyMoneyRule) {
		this.briberyMoneyRule = briberyMoneyRule;
	}
	public String getExpandParam() {
		return expandParam;
	}
	public void setExpandParam(String expandParam) {
		this.expandParam = expandParam;
	}
	public String getThirdPartyId() {
		return thirdPartyId;
	}
	public void setThirdPartyId(String thirdPartyId) {
		this.thirdPartyId = thirdPartyId;
	}
	public BriberyMoney(){
		
	}
	public BriberyMoney(String briberyMoneyId, String briberyMoneyName,
			String briberyMoneyType, String briberyMoneyExplain,
			String startTime, String endTime, Date createTime,
			String briberyMoneyRule, String expandParam, String thirdPartyId) {
		super();
		this.briberyMoneyId = briberyMoneyId;
		this.briberyMoneyName = briberyMoneyName;
		this.briberyMoneyType = briberyMoneyType;
		this.briberyMoneyExplain = briberyMoneyExplain;
		this.startTime = startTime;
		this.endTime = endTime;
		this.createTime = createTime;
		this.briberyMoneyRule = briberyMoneyRule;
		this.expandParam = expandParam;
		this.thirdPartyId = thirdPartyId;
	}
	@Override
	public String toString() {
		return "BriberyMoney [briberyMoneyId=" + briberyMoneyId
				+ ", briberyMoneyName=" + briberyMoneyName
				+ ", briberyMoneyType=" + briberyMoneyType
				+ ", briberyMoneyExplain=" + briberyMoneyExplain
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", createTime=" + createTime + ", briberyMoneyRule="
				+ briberyMoneyRule + ", expandParam=" + expandParam
				+ ", thirdPartyId=" + thirdPartyId + "]";
	}
	
}
