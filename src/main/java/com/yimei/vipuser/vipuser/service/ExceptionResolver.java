package com.yimei.vipuser.vipuser.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通用的异常处理类,用于记录异常信息
 * 特定的异常信息通过自定义异常进行处理，不经过这里处理
 *
 * @author chenling
 */
@Service
public class ExceptionResolver implements HandlerExceptionResolver {
    private static Logger logger = LoggerFactory
            .getLogger(ExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object arg2, Exception exception) {

        recodeErrorMessage(exception);
        //如果返回一个空的视图终止对于异常处理器的寻找
        return new ModelAndView("error/500");
    }

    private void recodeErrorMessage(Exception ex) {
        ex.printStackTrace();
        StringBuilder builder = new StringBuilder();
        StackTraceElement[] eles = ex.getStackTrace();
        System.out.println(eles[0].toString());
        if (null != eles && eles.length > 0) {
            builder.append("【").append(ex.getClass().getName()).append("  ");
            builder.append("类名：").append(eles[0].getClassName())
                    .append(" 方法名：").append(eles[0].getMethodName())
                    .append(" 行数:").append(eles[0].getLineNumber())
                    .append(" 】");
            logger.error(builder.toString());
        }

    }
}
