package com.yimei.vipuser.vipuser.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yimei.vipuser.vipuser.entity.BonusRecord;
import com.yimei.vipuser.vipuser.repository.BonusRecordDao;

@Service
@Transactional(readOnly = true)
public class BonusRecordService extends GenericService<BonusRecord, BonusRecordDao> {
	@Autowired
	BonusRecordDao dao;

	public List<BonusRecord> findByUserId(long userid) {
		return dao.findByUserId(userid);
	}
}
