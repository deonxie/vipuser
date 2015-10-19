package com.yimei.vipuser.vipuser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yimei.vipuser.vipuser.entity.Bulletin;
import com.yimei.vipuser.vipuser.repository.BulletinDao;

@Service
@Transactional(readOnly = true)
public class BulletinService extends GenericService<Bulletin, BulletinDao> {
	@Autowired
	BulletinDao dao;
	
}
