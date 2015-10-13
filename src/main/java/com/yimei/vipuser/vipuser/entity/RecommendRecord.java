package com.yimei.vipuser.vipuser.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.yimei.vipuser.vipuser.entity.account.User;
/****
 * 用户推荐关系管理表
 * @author jlusoft
 *
 */
@Entity
@Table(name = "vipuser_RecommendRecord")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RecommendRecord extends IdEntity {
	/**推荐人**/
	private User recommendUser;
	/**注册人**/
	private User newUser;
	/**创建时间**/
	private Date createDate;
	/**
	 * @return the recommendUser
	 */
	@ManyToOne
	public User getRecommendUser() {
		return recommendUser;
	}
	/**
	 * @param recommendUser the recommendUser to set
	 */
	public void setRecommendUser(User recommendUser) {
		this.recommendUser = recommendUser;
	}
	/**
	 * @return the newUser
	 */
	@ManyToOne
	public User getNewUser() {
		return newUser;
	}
	/**
	 * @param newUser the newUser to set
	 */
	public void setNewUser(User newUser) {
		this.newUser = newUser;
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
	
}
