package com.yimei.vipuser.vipuser.entity.account;

import cn.gd.thinkjoy.modules.utils.Collections3;
import com.yimei.vipuser.vipuser.entity.IdEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "vipuser_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends IdEntity {
    private String loginName;
    private String name;
    private String plainPassword;
    private String password;
    private String salt;
    private Date registerDate;
    private double balance;
    private String headImg;
    private String mobilPhone;
    private int typeStatus;
    private int status;
    private List<Role> roleList = Lists.newArrayList(); // 有序的关联对象集合
    /**后台管理类型用户*/
    public final static int TYPE_MANGAER = 9;
    /**vip会员类型用户*/
    public final static int TYPE_VIPUSER = 5;
    /**解冻状态**/
    public final static int freeze_status = 0;
    /**冻结状态**/
    public final static int freezed_status = 1;
    
    @Column(unique = true, nullable = false)
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Column(nullable =  false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @ManyToMany
    // 中间表定义,表名采用默认命名规则
    @JoinTable(name = "think_user_role", joinColumns = {@JoinColumn(name = "USER_ID")}, inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})
    public List<Role> getRoleList() {
        return roleList;
    }


    /**
     * 用户拥有的角色id字符串, 多个角色id用','分隔.
     */
    //非持久化属性.
    @Transient
    @JsonIgnore
    public List<Long> getRoleIds() {
        return Collections3.extractToList(roleList, "id");
    }

    @Transient
    @JsonIgnore
    public String getRoleNames() {
        return Collections3.extractToString(roleList, "name", ", ");
    }

    // 设定JSON序列化时的日期格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
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

	/**
	 * @return the headImg
	 */
	public String getHeadImg() {
		return headImg;
	}

	/**
	 * @return the typeStatus
	 */
	public int getTypeStatus() {
		return typeStatus;
	}

	/**
	 * @param typeStatus the typeStatus to set
	 */
	public void setTypeStatus(int typeStatus) {
		this.typeStatus = typeStatus;
	}

	/**
	 * @param headImg the headImg to set
	 */
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	
	/**
	 * @return the mobilPhone
	 */
	public String getMobilPhone() {
		return mobilPhone;
	}

	/**
	 * @param mobilPhone the mobilPhone to set
	 */
	public void setMobilPhone(String mobilPhone) {
		this.mobilPhone = mobilPhone;
	}

	/**
	 * @return the status
	 */
	@Column(columnDefinition="integer default 0")
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}