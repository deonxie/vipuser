package com.yimei.vipuser.vipuser.web.account;


import cn.gd.thinkjoy.modules.web.Servlets;
import cn.gd.thinkjoy.modules.web.pojo.PageRequest;
import com.yimei.vipuser.vipuser.entity.account.Permission;
import com.yimei.vipuser.vipuser.entity.account.Role;
import com.yimei.vipuser.vipuser.entity.account.User;
import com.yimei.vipuser.vipuser.service.account.RoleServcie;
import com.yimei.vipuser.vipuser.web.GenericController;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/account/role")
public class RoleController extends GenericController {
    @Autowired
    private RoleServcie roleServcie;

    @RequiresPermissions("role:view")
    @RequestMapping(value = "")
    public String list(PageRequest pageRequest, Model model,
                       ServletRequest request) {

        if (StringUtils.isBlank(pageRequest.getOrderBy())) {
            pageRequest.setOrderBy("id");
            pageRequest.setOrderDir("desc");
        }

        Page<Role> roles = roleServcie.search(
                Servlets.getParametersStartingWith(request, "search_"),
                pageRequest);
        pageRequest.setPrePage(roles.hasPreviousPage());
        pageRequest.setNextPage(roles.hasNextPage());

        model.addAttribute("page", roles);
        model.addAttribute("pageRequest", pageRequest);
        return "account/role/roleList";
    }

    @RequiresPermissions("role:view")
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model model) {
        Map<String, List<Permission>> permissionListByType = Permission
                .getListMap();
        Role role = roleServcie.get(id);
        model.addAttribute("role", role);
        model.addAttribute("checkedPermission", role.getPermissions());
        model.addAttribute("permissionListByType", permissionListByType);
        return "account/role/roleForm";
    }

    @RequiresPermissions("role:edit")
    @RequestMapping(value = "update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String update(@Valid @ModelAttribute("role") Role role,
                         RedirectAttributes redirectAttributes) {
        String message = roleServcie.saveRole(role);
        addMessage(redirectAttributes, message);
        return "redirect:/account/role";
    }

    @RequiresPermissions("role:edit")
    @RequestMapping(value = "delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String delete(@PathVariable("id") Long id,
                         RedirectAttributes redirectAttributes) {
        roleServcie.delete(id);
        addMessage(redirectAttributes, "删除角色成功");
        return "redirect:/account/role";
    }

    @ModelAttribute
    public void getRole(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
        if (id != -1) {
            model.addAttribute("role", roleServcie.get(id));
        }
    }

    @ResponseBody
    @RequiresPermissions("role:edit")
    @RequestMapping(value = "validate")
    public String checkLoginName(String name) {
        if (StringUtils.isNotBlank(name)) {
            Role role = roleServcie.findRoleByName(name);
            return role == null ? Boolean.TRUE.toString() : Boolean.FALSE.toString();
        }
        return Boolean.FALSE.toString();
    }
}
