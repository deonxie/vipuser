package com.yimei.vipuser.vipuser.web.account;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yimei.vipuser.vipuser.entity.account.User;
import com.yimei.vipuser.vipuser.service.account.ShiroDbRealm.ShiroUser;

/**
 * 登陆成功后，映射到的主页
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String list() {
        // 首页展示可以进行一些个性化的定制
    	Subject subject = SecurityUtils.getSubject();
 	    if(subject !=null){
 	    	Object obj = subject.getPrincipal();
 	    	if(obj !=null && obj instanceof ShiroUser){
 	    		ShiroUser shiroUser = (ShiroUser)obj;
 	    		if(shiroUser.typeStatus == User.TYPE_VIPUSER)
 	    			return "redirect:/vipuser/detail?id="+shiroUser.id;
 	    	}
 	    }
        return "/account/index";
    }

    @RequestMapping("/menuList/{firstLevel}")
    public String menuList(@PathVariable("firstLevel") String firstLevel) {
        return "/menu/" + firstLevel;
    }
}
