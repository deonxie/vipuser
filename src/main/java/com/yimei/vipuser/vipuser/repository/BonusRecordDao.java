package com.yimei.vipuser.vipuser.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.yimei.vipuser.vipuser.entity.BonusRecord;

public interface BonusRecordDao extends GenericDao<BonusRecord> {
	@Query("from BonusRecord br where br.recommendUserId =?1")
	List<BonusRecord> findByUserId(long userid);

}
