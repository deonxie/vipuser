package com.yimei.vipuser.vipuser.web.account;

import cn.gd.thinkjoy.modules.web.Servlets;
import cn.gd.thinkjoy.modules.web.pojo.PageRequest;

import com.yimei.vipuser.vipuser.entity.account.Role;
import com.yimei.vipuser.vipuser.entity.account.User;
import com.yimei.vipuser.vipuser.service.account.RoleServcie;
import com.yimei.vipuser.vipuser.service.account.UserService;
import com.yimei.vipuser.vipuser.web.GenericController;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import java.util.List;
import java.util.Map;

/**
 * 用户注册的Controller.
 *
 * @author calvin
 */
@Controller
@RequestMapping(value = "/account/user")
public class UserController extends GenericController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleServcie roleService;

    @RequiresPermissions("user:view")
    @RequestMapping(value = "")
    public String list(PageRequest pageRequest, Model model,
                       ServletRequest request) {
    	Map<String,Object> param = Servlets.getParametersStartingWith(request, "search_");
		 param.put("EQ_typeStatus", User.TYPE_MANGAER);
        Page<User> users = userService.search(param, pageRequest);
        model.addAttribute("page", users);
        model.addAttribute("pageRequest", pageRequest);
        return "account/user/userList";
    }

    @RequiresPermissions("user:view")
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.get(id));
        model.addAttribute("allRoles", roleService.getAll());
        return "account/user/userForm";
    }

    @RequiresPermissions("user:edit")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("user") User user, @RequestParam(value = "roleIds") List<Long> checkedRoleList,
                         RedirectAttributes redirectAttributes) {
        String message = userService.registerUser(user, checkedRoleList);
        addMessage(redirectAttributes, message);
        return "redirect:/account/user";
    }

    @RequiresPermissions("user:edit")
    @RequestMapping(value = "delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        userService.delete(id);
        addMessage(redirectAttributes, "删除用户成功");
        return "redirect:/account/user";
    }

    /**
     * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出User对象,再把Form提交的内容绑定到该对象上。
     * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
     */
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
}
