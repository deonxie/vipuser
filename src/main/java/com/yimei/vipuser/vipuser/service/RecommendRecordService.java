package com.yimei.vipuser.vipuser.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yimei.vipuser.vipuser.entity.BonusRecord;
import com.yimei.vipuser.vipuser.entity.RecommendRecord;
import com.yimei.vipuser.vipuser.entity.account.User;
import com.yimei.vipuser.vipuser.repository.RecommendRecordDao;
import com.yimei.vipuser.vipuser.service.account.UserService;

@Service
@Transactional(readOnly = true)
public class RecommendRecordService extends GenericService<RecommendRecord, RecommendRecordDao> {
	@Autowired
	RecommendRecordDao dao;
	@Autowired
	UserService userSer;
	@Autowired
	BonusRecordService bonusSer;

	public User findUpLevelUser(long userid) {
		return dao.findUpLevelUser(userid);
	}
	
	@Transactional(readOnly = false)
	public void saveUserRecommend(User user,long recommendUserId) {
		User recommendUser = userSer.get(recommendUserId);
		if(recommendUser != null && recommendUser.getId()>0){
			RecommendRecord record = new RecommendRecord();
			record.setRecommendUser(recommendUser);
			record.setNewUser(user);
			record.setCreateDate(new Date());
			dao.save(record);
			
			//按推荐层次发放奖金 1级1000
			recommendUser.setBalance(recommendUser.getBalance()+1000);
			userSer.save(recommendUser);
			
			BonusRecord bonusRecord = new BonusRecord();
			bonusRecord.setBonus(1000);
			bonusRecord.setCreateDate(new Date());
			bonusRecord.setLevel(1);
			bonusRecord.setNewUserId(user.getId());
			bonusRecord.setNewUserName(user.getName());
			bonusRecord.setRecommendUserId(recommendUser.getId());
			bonusRecord.setRecommendUserName(recommendUser.getName());
			bonusSer.save(bonusRecord);
			
			//按推荐层次发放奖金 2级300
			User upUser = findUpLevelUser(recommendUser.getId());
			if(upUser != null){
				upUser.setBalance(upUser.getBalance()+300);
				userSer.save(upUser);
				BonusRecord bonusRecord2 = new BonusRecord();
				bonusRecord2.setBonus(300);
				bonusRecord2.setCreateDate(new Date());
				bonusRecord2.setLevel(2);
				bonusRecord2.setNewUserId(user.getId());
				bonusRecord2.setNewUserName(user.getName());
				bonusRecord2.setRecommendUserId(upUser.getId());
				bonusRecord2.setRecommendUserName(upUser.getName());
				bonusSer.save(bonusRecord2);
				
				//按推荐层次发放奖金 3级200
				User upUpUser = findUpLevelUser(upUser.getId());
				if(upUpUser != null){
					upUpUser.setBalance(upUpUser.getBalance()+200);
					userSer.save(upUpUser);
					BonusRecord bonusRecord3 = new BonusRecord();
					bonusRecord3.setBonus(200);
					bonusRecord3.setCreateDate(new Date());
					bonusRecord3.setLevel(3);
					bonusRecord3.setNewUserId(user.getId());
					bonusRecord3.setNewUserName(user.getName());
					bonusRecord3.setRecommendUserId(upUpUser.getId());
					bonusRecord3.setRecommendUserName(upUpUser.getName());
					bonusSer.save(bonusRecord3);
				}
			}
		}
	}

	public Map<String, List<User>> findByNewUsers(long recommendUserId) {
		Map<String, List<User>> map = new HashMap<String, List<User>>();
		List<Long> userIds = new ArrayList<Long>();
		userIds.add(recommendUserId);
		List<User> fristLevel = dao.findLevelNewUsers(userIds);
		if(fristLevel!= null && fristLevel.size()>0){
			userIds.clear();
			for(User tmp : fristLevel){
				userIds.add(tmp.getId());
			}
			List<User> secondLevel = dao.findLevelNewUsers(userIds);
			if(secondLevel!= null && secondLevel.size()>0){
				userIds.clear();
				for(User tmp : secondLevel){
					userIds.add(tmp.getId());
				}
				List<User> threeLevel = dao.findLevelNewUsers(userIds);
				map.put("threeLevel", threeLevel);
			}
			map.put("secondLevel", secondLevel);
		}
		map.put("fristLevel", fristLevel);
		return map;
	}
	
	public Map<String, List<RecommendRecord>> findByNewUserRecords(long recommendUserId) {
		Map<String, List<RecommendRecord>> map = new HashMap<String, List<RecommendRecord>>();
		List<Long> userIds = new ArrayList<Long>();
		userIds.add(recommendUserId);
		List<RecommendRecord> fristLevel = dao.findLevelNewUserRecords(userIds);
		if(fristLevel!= null && fristLevel.size()>0){
			userIds.clear();
			for(RecommendRecord tmp : fristLevel){
				userIds.add(tmp.getNewUser().getId());
			}
			List<RecommendRecord> secondLevel = dao.findLevelNewUserRecords(userIds);
			if(secondLevel!= null && secondLevel.size()>0){
				userIds.clear();
				for(RecommendRecord tmp : secondLevel){
					userIds.add(tmp.getNewUser().getId());
				}
				map.put("threeLevel", dao.findLevelNewUserRecords(userIds));
			}
			map.put("secondLevel", secondLevel);
		}
		map.put("fristLevel", fristLevel);
		return map;
	}
}
