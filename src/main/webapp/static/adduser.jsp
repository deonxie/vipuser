<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="com.yimei.vipuser.vipuser.entity.account.User"%>
<%@page import="com.yimei.vipuser.vipuser.service.account.UserService"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<html>
<head>
    <title>用户管理</title>
    <meta name="decorator" content="default"/>
</head>
<body>
<%
	ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
	UserService userSer = (UserService)context.getBean("userService");
	User user = new User();
	user.setId(0l);
	user.setBalance(0);
	user.setLoginName("deon1");
	user.setName("deon1");
	user.setPlainPassword("123");
	user.setRegisterDate(new Date());
	user.setTypeStatus(User.TYPE_MANGAER);
	List<Long> list = new ArrayList<Long>();
	list.add(1l);
	
 	String msg = userSer.registerUser(user, list);
%>
<%=msg %>
</body>
</html>