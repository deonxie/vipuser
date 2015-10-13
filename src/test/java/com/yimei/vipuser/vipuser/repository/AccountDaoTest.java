/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.yimei.vipuser.vipuser.repository;

import cn.gd.thinkjoy.modules.test.spring.SpringTransactionalTestCase;
import com.yimei.vipuser.vipuser.entity.account.Role;
import com.yimei.vipuser.vipuser.entity.account.User;
import com.yimei.vipuser.vipuser.service.account.RoleServcie;
import com.yimei.vipuser.vipuser.service.account.UserService;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class AccountDaoTest extends SpringTransactionalTestCase {

	@Autowired
	private UserService userService;
    @Autowired
    private RoleServcie roleService;

    @Test
    @Rollback(false)
    public void addRole() {
        Role role = new Role();
        role.setName("管理员");
        role.setPermissions("role:view,role:edit,user:view,user:edit");

        roleService.save(role);

    }

    @Test
    @Rollback(false)
    public void registerUser(){

        List<Role> roleList = roleService.getAll();

        List<Long> roleIds = Lists.newArrayList();

        for(Role role : roleList){
            roleIds.add(role.getId());
        }

        User user = new User();
        user.setId(0L);
        user.setLoginName("admin");
        user.setName("admin");
        user.setPlainPassword("123");
        user.setTypeStatus(User.TYPE_MANGAER);
        System.out.println(userService.registerUser(user, roleIds));
    }
    @Test
    @Rollback(false)
    public void registerVipUser(){
    	User user = new User();
        user.setId(0L);
        user.setLoginName("vipuser");
        user.setName("vip");
        user.setPlainPassword("123");
        user.setTypeStatus(User.TYPE_VIPUSER);userService.save(user);
        System.out.println("success");
    
    }
}
