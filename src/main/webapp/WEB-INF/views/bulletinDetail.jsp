<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<html>
<head>
	<title>一美光电会员公告</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n){
			$("#pageNo").val(n);
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
<div style="padding: 20px;">
	<ul class="nav nav-tabs">
		<li class="active"><a> 公 告 </a></li>
		<li><a href="${ctx}/index">个人信息</a></li>
	</ul>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead>
        <tr>
            <th width="15%">标题</th>
            <th width="70%">内容</th>
            <th width="15%">发布时间</th>
        </tr>
        </thead>
		<tbody>
        <c:forEach items="${page.content}" var="bull" varStatus="status">
            <tr>
                <td>${bull.title}&nbsp;</td>
                <td><pre id="td${bull.id }">${bull.content}</pre></td>
                <td><fmt:formatDate value="${bull.createDate}" pattern="yyyy-MM-dd"/> &nbsp;</td>
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
                                onClick="page(${page.number-1})">上一页</a></li>
            </c:if>
            <li class="controls"><strong>总页数${page.totalPages},当前第${page.number
                    + 1}页 ${page[hasNextPage]}</strong></li>
            <c:if test="${!pageRequest.nextPage}">
                <li class="disabled"><a href="#">下一页 </a></li>
            </c:if>
            <c:if test="${pageRequest.nextPage}">
                <li class=""><a href="#" id="nextPage"
                                onclick="page(${page.number+2})">下一页</a></li>
            </c:if>
        </ul>
    </div>
</div>

</body>
</html>