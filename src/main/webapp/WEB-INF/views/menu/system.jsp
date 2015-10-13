<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<html>
<head>
    <title>菜单导航</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $(".accordion-heading a").click(function () {
                $('.accordion-toggle i').removeClass('icon-chevron-down');
                $('.accordion-toggle i').addClass('icon-chevron-right');
                if (!$($(this).attr('href')).hasClass('in')) {
                    $(this).children('i').removeClass('icon-chevron-right');
                    $(this).children('i').addClass('icon-chevron-down');
                }
            });
            $(".accordion-body a").click(function () {
                $("#menu li").removeClass("active");
                $("#menu li i").removeClass("icon-white");
                $(this).parent().addClass("active");
                $(this).children("i").addClass("icon-white");
            });
            $(".accordion-body a:first i").click();
        });
    </script>
</head>
<body>
<div class="accordion" id="menu">
    <div class="accordion-group">
        <div class="accordion-heading">
            <a class="accordion-toggle" data-toggle="collapse" data-parent="#menu" href="#collapse1"><i
                    class="icon-chevron-down"></i>&nbsp;账号管理</a>
        </div>
        <div id="collapse1" class="accordion-body collapse in">
            <div class="accordion-inner">
                <ul class="nav nav-list">
                	<shiro:hasPermission name="user:view">
                    <li><a href="${ctx}/account/user/" target="mainFrame"><i class="icon-heart icon-white"></i>&nbsp;用户管理</a></li>
                	</shiro:hasPermission><shiro:hasPermission name="role:view">
                    <li><a href="${ctx}/account/role/" target="mainFrame"><i class="icon-heart"></i>&nbsp;角色管理</a></li>
                	</shiro:hasPermission>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>
