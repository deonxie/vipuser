package com.yimei.vipuser.vipuser.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yimei.vipuser.vipuser.entity.TakeBonusRecord;
import com.yimei.vipuser.vipuser.repository.TakeBonusRecordDao;

@Service
@Transactional(readOnly = true)
public class TakeBonusRecordService extends GenericService<TakeBonusRecord, TakeBonusRecordDao> {
	@Autowired
	TakeBonusRecordDao dao;

	public List<TakeBonusRecord> findByUserId(long userid) {
		return dao.findByUserId(userid);
	}
}
