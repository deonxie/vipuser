/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.yimei.vipuser.vipuser.data;

import com.yimei.vipuser.vipuser.entity.account.User;

import cn.gd.thinkjoy.modules.security.utils.Digests;
import cn.gd.thinkjoy.modules.test.data.RandomData;
import cn.gd.thinkjoy.modules.utils.Encodes;

public class UserData {

	public static User randomNewUser() {
		User user = new User();
		user.setLoginName(RandomData.randomName("user"));
		user.setName(RandomData.randomName("User"));
		user.setPlainPassword(RandomData.randomName("password"));

		return user;
	}
	
	public static void main(String[] args) {
		User user = new User();
		user.setPlainPassword("123");
		        byte[] salt = Digests.generateSalt(8);
		        user.setSalt(Encodes.encodeHex(salt));

		        byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, 1024);
		        user.setPassword(Encodes.encodeHex(hashPassword));
		    System.out.println(user.getPassword());
		    System.out.println(user.getSalt());
	}
}
//3a2eed790a311fcf
//a27fd1c1422f4691
//757da7f3
//cf12546efd2e2b16
