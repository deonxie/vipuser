package com.yimei.vipuser.vipuser.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 通用控制器层主要用来简化一些日常开发
 * Created by chenling on 14-7-30.
 */
public class GenericController {

    /**
     * 获取每个Controller类注解中的Mapper地址
     * 页面通过${symbol_dollar}{baseMapper}引用相关地址
     *
     * @return
     */
    @ModelAttribute("baseMapper")
    public String generateMapper() {
        String baseMapper = null;
        RequestMapping annotations = getClass().getAnnotation(RequestMapping.class);
        if (null != annotations) {
            String[] values = annotations.value();
            if (null != values && values.length > 0) {
                baseMapper = values[0];
            }
        }
        return baseMapper;
    }

    /**
     * 添加Model消息
     * @param messages 消息
     */
    protected void addMessage(Model model, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages){
            sb.append(message).append(messages.length>1?"<br/>":"");
        }
        model.addAttribute("message", sb.toString());
    }

    /**
     * 添加Flash消息
     * @param messages 消息
     */
    protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages){
            sb.append(message).append(messages.length>1?"<br/>":"");
        }
        redirectAttributes.addFlashAttribute("message", sb.toString());
    }
}
