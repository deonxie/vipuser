/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.yimei.vipuser.vipuser.service.account;

import static org.assertj.core.api.Assertions.*;

import com.yimei.vipuser.vipuser.repository.account.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.yimei.vipuser.vipuser.data.UserData;
import com.yimei.vipuser.vipuser.entity.account.User;
import com.yimei.vipuser.vipuser.service.ServiceException;
import com.yimei.vipuser.vipuser.service.account.ShiroDbRealm.ShiroUser;
import cn.gd.thinkjoy.modules.test.security.shiro.ShiroTestUtils;
import org.springframework.test.annotation.Rollback;

/**
 * AccountService的测试用例, 测试Service层的业务逻辑.
 * 
 * @author calvin
 */
public class AccountServiceTest {

	@InjectMocks
	private UserService accountService;

	@Mock
	private UserDao mockUserDao;


	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		ShiroTestUtils.mockSubject(new ShiroUser(3L, "foo", "Foo",9));
	}


	@Test
	public void updateUser() {
		// 如果明文密码不为空，加密密码会被更新.
		User user = UserData.randomNewUser();
		accountService.save(user);
		assertThat(user.getSalt()).isNotNull();

		// 如果明文密码为空，加密密码无变化。
		User user2 = UserData.randomNewUser();
		user2.setPlainPassword(null);
		accountService.save(user2);
		assertThat(user2.getSalt()).isNull();
	}

	@Test
	public void deleteUser() {
		// 正常删除用户.
		accountService.delete(2L);
		Mockito.verify(mockUserDao).delete(2L);

		// 删除超级管理用户抛出异常, userDao没有被执行
		try {
			accountService.delete(1L);
			failBecauseExceptionWasNotThrown(ServiceException.class);
		} catch (ServiceException e) {
			// expected exception
		}
		Mockito.verify(mockUserDao, Mockito.never()).delete(1L);
	}

}
