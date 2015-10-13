/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.yimei.vipuser.vipuser.repository.account;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.yimei.vipuser.vipuser.entity.account.User;
import com.yimei.vipuser.vipuser.repository.GenericDao;

public interface UserDao extends GenericDao<User> {
    User findByLoginName(String loginName);
    @Query("from User where typeStatus=?1")
	List<User> findUserByType(int typeVipuser);
}
