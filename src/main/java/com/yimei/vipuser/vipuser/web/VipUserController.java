package com.yimei.vipuser.vipuser.web;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.gd.thinkjoy.modules.web.Servlets;
import cn.gd.thinkjoy.modules.web.pojo.PageRequest;

import com.yimei.vipuser.vipuser.entity.account.User;
import com.yimei.vipuser.vipuser.service.BonusRecordService;
import com.yimei.vipuser.vipuser.service.RecommendRecordService;
import com.yimei.vipuser.vipuser.service.TakeBonusRecordService;
import com.yimei.vipuser.vipuser.service.account.UserService;
import com.yimei.vipuser.vipuser.service.account.ShiroDbRealm.ShiroUser;

/**
 * 用户注册的Controller.
 *
 * @author calvin
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/vipuser")
public class VipUserController extends GenericController {
	 @Autowired
	 private UserService userService;
	 @Autowired
	 RecommendRecordService recommendSer;
	 @Autowired
	 BonusRecordService bonusRecordSer;
	 @Autowired
	 TakeBonusRecordService takeoffSer;
	 
	 @RequiresPermissions("user:view")
	 @RequestMapping(value = "")
	 public String list(PageRequest pageRequest, Model model,ServletRequest request) {
		 Map<String,Object> param = Servlets.getParametersStartingWith(request, "search_");
		 param.put("EQ_typeStatus", User.TYPE_VIPUSER);
        Page<User> users = userService.search(param, pageRequest);
        model.addAttribute("page", users);
        model.addAttribute("pageRequest", pageRequest);
        return "vipuserList";
    }
	@RequiresPermissions("user:view")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model){
	    model.addAttribute("user", userService.get(id));
	    model.addAttribute("allVipUser", userService.findUserByType(User.TYPE_VIPUSER));
	    return "vipuserForm";
	}
	
	@RequiresPermissions("user:edit")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("user") User user,
		@RequestParam(value="recommend",defaultValue="0")long recommend,
		RedirectAttributes redirectAttributes){
	    addMessage(redirectAttributes, userService.createVipUser(user, recommend));
	    recommendSer.saveUserRecommend(userService.findUserByLoginName(user.getLoginName()), recommend);
	    return "redirect:/vipuser";
	}
	
	@RequestMapping("detail")
	public String detail(@ModelAttribute("user") User user,Model model){
		Subject subject = SecurityUtils.getSubject();
		if(subject !=null){
			Object obj = subject.getPrincipal();
			if(obj !=null && obj instanceof ShiroUser){
				ShiroUser shiroUser = (ShiroUser)obj;
				if(shiroUser.typeStatus == User.TYPE_VIPUSER){
					if(shiroUser.id != user.getId()){
						return "redirect:/vipuser/detail?id="+shiroUser.id;
					}
					model.addAttribute("needLogout", true);
				}
			}
		}
	    model.addAttribute("user", user);
	    model.addAttribute("newUsers", recommendSer.findByNewUserRecords(user.getId()));
	    model.addAttribute("bonusRecords", bonusRecordSer.findByUserId(user.getId()));
	    model.addAttribute("takeoffRecords", takeoffSer.findByUserId(user.getId()));
	    return "vipuserDetail";
	}
	
	@RequestMapping(value="resetPwd",method=RequestMethod.POST)
	@ResponseBody
	public boolean resetPassword(@RequestParam("id")long userid,@RequestParam("pwd")String pwd){
		User user = userService.get(userid);
		if(user!=null && StringUtils.isNotBlank(pwd)){
			user.setPlainPassword(pwd);
			userService.resetPwd(user);
			return true;
		}
		return false;
	}
	
	@ModelAttribute
    public void getUser(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
        if (id != -1) {
            model.addAttribute("user", userService.get(id));
        }
    }
	
	@ResponseBody
	@RequiresPermissions("user:edit")
	@RequestMapping(value = "validate")
	public String checkLoginName(String loginName) {
	    if (StringUtils.isNotBlank(loginName)) {
	        User user = userService.findUserByLoginName(loginName);
	        return user == null ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
	     }
	     return Boolean.FALSE.toString();
	}
	
	@RequiresPermissions("user:edit")
	@RequestMapping(value = "freezeUser")
	public String freezeUser(@RequestParam("id")long id,@RequestParam("type")int type){
		switch (type) {
		case User.freezed_status:
			userService.updateUserStatus(id,User.freezed_status);
			break;
		case User.freeze_status:
			userService.updateUserStatus(id,User.freeze_status);
			break;
		default:
			break;
		}
		return "redirect:/vipuser";
	}
	
	@RequiresPermissions("user:edit")
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String modify(@ModelAttribute("user") User user){
		userService.modify(user);
		return "redirect:/vipuser";
	}
	@RequiresPermissions("user:edit")
	@RequestMapping(value = "modify/{id}", method = RequestMethod.GET)
	public String modify(@PathVariable ("id")long id,Model model){
		model.addAttribute("user", userService.get(id));
		return "vipuserUpdate";
	}
}
