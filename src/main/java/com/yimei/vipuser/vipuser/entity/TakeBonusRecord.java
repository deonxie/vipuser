package com.yimei.vipuser.vipuser.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.yimei.vipuser.vipuser.entity.account.User;

/****
 * 提现记录
 * @author jlusoft
 *
 */
@Entity
@Table(name = "vipuser_TakeBonusRecord")
public class TakeBonusRecord extends IdEntity {
	/**推荐人**/
	private User takeUser;
	/**提现金额**/
	private double bonus;
	/**余额**/
	private double balance;
	/**创建时间**/
	private Date createDate;
	/**备注*/
	private String remark;
	private int status;
	
	/**
	 * @return the takeUser
	 */
	@ManyToOne
	public User getTakeUser() {
		return takeUser;
	}
	/**
	 * @param takeUser the takeUser to set
	 */
	public void setTakeUser(User takeUser) {
		this.takeUser = takeUser;
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
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the balance
	 */
	public double getBalance() {
		return balance;
	}
	/**
	 * @param balance the balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
}
