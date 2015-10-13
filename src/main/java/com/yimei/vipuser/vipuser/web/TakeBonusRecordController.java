package com.yimei.vipuser.vipuser.web;

import java.util.Date;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimei.vipuser.vipuser.entity.TakeBonusRecord;
import com.yimei.vipuser.vipuser.entity.account.User;
import com.yimei.vipuser.vipuser.service.TakeBonusRecordService;
import com.yimei.vipuser.vipuser.service.account.UserService;

@Controller
@RequestMapping("/takerecord")
public class TakeBonusRecordController extends GenericController {
	@Autowired
	UserService userSer;
	@Autowired
	TakeBonusRecordService takebonusSer;
	
	@RequiresPermissions("user:edit")
	@RequestMapping(value="take",method=RequestMethod.POST)
	@ResponseBody
	public boolean takeoff(@RequestParam("id")long userid,@RequestParam("money")int money,
			@RequestParam(value="remark",required=false)String remark){
		User user = userSer.get(userid);
		if(user!=null && money>0 && user.getBalance()>=money){
			TakeBonusRecord record = new TakeBonusRecord();
			record.setBonus(money);
			record.setCreateDate(new Date());
			record.setTakeUser(user);
			record.setRemark(remark);
			record.setBalance(user.getBalance()-money);
			takebonusSer.save(record);
			
			user.setBalance(user.getBalance()-money);
			userSer.save(user);
			return true;
		}
		return false;
	}
}
