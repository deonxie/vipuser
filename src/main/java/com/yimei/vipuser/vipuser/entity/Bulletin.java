package com.yimei.vipuser.vipuser.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="vipuser_bulletin")
public class Bulletin extends IdEntity {
	private String title;
	private String content;
	private Date createDate;
	private int status;
	private String pulishUser;
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the content
	 */
	@Column(columnDefinition="text")
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
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
	 * @return the pulishUser
	 */
	public String getPulishUser() {
		return pulishUser;
	}
	/**
	 * @param pulishUser the pulishUser to set
	 */
	public void setPulishUser(String pulishUser) {
		this.pulishUser = pulishUser;
	}
	
	
}
