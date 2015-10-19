package com.yimei.vipuser.vipuser.web;

import java.util.Date;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.gd.thinkjoy.modules.web.Servlets;
import cn.gd.thinkjoy.modules.web.pojo.PageRequest;

import com.yimei.vipuser.vipuser.entity.Bulletin;
import com.yimei.vipuser.vipuser.service.BulletinService;
import com.yimei.vipuser.vipuser.service.account.ShiroDbRealm.ShiroUser;

@Controller
@RequestMapping("/bulletin")
public class BulletinController extends GenericController{
	@Autowired
	BulletinService ser;
	
	@RequiresPermissions("notice:view")
	@RequestMapping("")
	public String list(PageRequest pageRequest, Model model,ServletRequest request) {
		Map<String,Object> param = Servlets.getParametersStartingWith(request, "search_");
		pageRequest.setOrderBy("createDate");
		pageRequest.setOrderDir("desc");
		Page<Bulletin> users = ser.search(param, pageRequest);
		model.addAttribute("page", users);
		model.addAttribute("pageRequest", pageRequest);
		return "bulletinList";
	}
	
	@RequestMapping(value="detail",method=RequestMethod.GET)
	public String detail(PageRequest pageRequest, Model model,ServletRequest request) {
		Map<String,Object> param = Servlets.getParametersStartingWith(request, "search_");
		pageRequest.setOrderBy("createDate");
		pageRequest.setOrderDir("desc");
		Page<Bulletin> users = ser.search(param, pageRequest);
		model.addAttribute("page", users);
		model.addAttribute("pageRequest", pageRequest);
		return "bulletinDetail";
	}
	
	@RequiresPermissions("notice:edit")
	@RequestMapping(value="update",method=RequestMethod.POST)
	public String update(@Valid @ModelAttribute("bulletin")Bulletin bulletin,
			RedirectAttributes model){
		if(bulletin.getId()<1){
			Subject subject = SecurityUtils.getSubject();
	 	    if(subject !=null){
	 	    	Object obj = subject.getPrincipal();
	 	    	if(obj !=null && obj instanceof ShiroUser){
	 	    		bulletin.setPulishUser(((ShiroUser)obj).name);
	 	    	}
	 	    }
	 	    bulletin.setCreateDate(new Date());
 	    }
		ser.save(bulletin);
		addMessage(model, "发布成功！");
		return "redirect:/bulletin";
	}
	
	@RequiresPermissions("notice:edit")
	@RequestMapping(value="delete/{id}",method=RequestMethod.GET)
	public String delete(@PathVariable("id")long id,RedirectAttributes model){
		ser.delete(id);
		addMessage(model, "删除成功！");
		return "redirect:/bulletin";
	}
	
	@ModelAttribute
    public void getUser(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
        if (id != -1) {
            model.addAttribute("bulletin", ser.get(id));
        }
    }
}
