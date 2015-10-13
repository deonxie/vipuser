package com.yimei.vipuser.vipuser.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.yimei.vipuser.vipuser.entity.RecommendRecord;
import com.yimei.vipuser.vipuser.entity.account.User;

public interface RecommendRecordDao extends GenericDao<RecommendRecord> {
	@Query("select rr.recommendUser from RecommendRecord rr where rr.newUser.id=?1")
	User findUpLevelUser(long userid);
	@Query("select rr.newUser from RecommendRecord rr where rr.recommendUser.id in(?1)")
	List<User> findLevelNewUsers(List<Long> recommendUserIds);

	@Query("from RecommendRecord rr where rr.recommendUser.id in(?1)")
	List<RecommendRecord> findLevelNewUserRecords(List<Long> recommendUserIds);
}
