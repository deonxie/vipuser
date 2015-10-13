<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<html>
<head>
    <title>角色管理</title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/dialog.jsp" %>
    <script type="text/javascript">
        function page(n) {
            $("#pageNo").val(n);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}${baseMapper}">角色列表</a></li>
    <shiro:hasPermission name="role:edit">
        <li><a href="${ctx}${baseMapper}/update/0">角色添加</a></li>
    </shiro:hasPermission>
</ul>
<form id="searchForm" action="${ctx}${baseMapper}" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.number+1}"/>
    <input id="orderDir" name="orderDir" type="hidden" value="${pageRequest.orderDir}"/>
    <input id="orderBy" name="orderBy" type="hidden" value="${pageRequest.orderBy}"/>

    <div>
        <label>角色名：</label>
        <input type="text" name="search_LIKE_name" class="input-small" placeholder="名称"
               value="${param.search_LIKE_name}">
        &nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
    </div>
</form>
<tags:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>#</th>
        <th>名称</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.content}" var="role" varStatus="status">
        <tr>
            <td>${status.count}</td>
            <td>${role.name}&nbsp;</td>
            <td><a href="${ctx}${baseMapper}/update/${role.id}">详情</a>
                <shiro:hasPermission name="role:edit">
                    <a href="${ctx}${baseMapper}/delete/${role.id}"
                       onclick="return confirmx('确认要删除该角色吗？', this.href)">删除</a>
                </shiro:hasPermission></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="row">
    <ul class="pager">
        <c:if test="${!pageRequest.prePage}">
            <li class=" disabled"><a href="#">上一页</a></li>
        </c:if>
        <c:if test="${pageRequest.prePage}">
            <li class=""><a href="#" id="prePage"
                            onClick="submitForm(${page.number-1})">上一页</a></li>
        </c:if>
        <li class="controls"><strong>总页数${page.totalPages},当前第${page.number
                + 1}页 ${page[hasNextPage]}</strong></li>
        <c:if test="${!pageRequest.nextPage}">
            <li class="disabled"><a href="#">下一页 </a></li>
        </c:if>
        <c:if test="${pageRequest.nextPage}">
            <li class=""><a href="#" id="nextPage"
                            onclick="submitForm(${page.number+2})">下一页</a></li>
        </c:if>
    </ul>
</div>
</body>
</html>