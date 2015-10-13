package com.yimei.vipuser.vipuser.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/****
 * 推荐奖金发放记录
 * @author jlusoft
 *
 */
@Entity
@Table(name = "vipuser_BonusRecord")
public class BonusRecord extends IdEntity {
	/**推荐人**/
	private long recommendUserId;
	private String recommendUserName;
	/**注册人**/
	private long newUserId;
	private String newUserName;
	/**奖金**/
	private double bonus;
	/**创建时间**/
	private Date createDate;
	/**备注*/
	private String remark;
	/**推荐等级**/
	private int level;
	/**
	 * @return the recommendUserId
	 */
	public long getRecommendUserId() {
		return recommendUserId;
	}
	/**
	 * @param recommendUserId the recommendUserId to set
	 */
	public void setRecommendUserId(long recommendUserId) {
		this.recommendUserId = recommendUserId;
	}
	/**
	 * @return the recommendUserName
	 */
	public String getRecommendUserName() {
		return recommendUserName;
	}
	/**
	 * @param recommendUserName the recommendUserName to set
	 */
	public void setRecommendUserName(String recommendUserName) {
		this.recommendUserName = recommendUserName;
	}
	/**
	 * @return the newUserId
	 */
	public long getNewUserId() {
		return newUserId;
	}
	/**
	 * @param newUserId the newUserId to set
	 */
	public void setNewUserId(long newUserId) {
		this.newUserId = newUserId;
	}
	/**
	 * @return the newUserName
	 */
	public String getNewUserName() {
		return newUserName;
	}
	/**
	 * @param newUserName the newUserName to set
	 */
	public void setNewUserName(String newUserName) {
		this.newUserName = newUserName;
	}
	/**
	 * @return the bonus
	 */
	public double getBonus() {
		return bonus;
	}
	/**
	 * @param bonus the bonus to set
	 */
	public void setBonus(double bonus) {
		this.bonus = bonus;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the remark
	 */
	@Column(columnDefinition="text")
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}
}
