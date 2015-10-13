package com.yimei.vipuser.vipuser.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.yimei.vipuser.vipuser.entity.TakeBonusRecord;

public interface TakeBonusRecordDao extends GenericDao<TakeBonusRecord> {

	@Query("from TakeBonusRecord tbr where tbr.takeUser.id=?1")
	List<TakeBonusRecord> findByUserId(long userid);

}
