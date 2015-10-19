<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<html>
<head>
	<title>公告列表</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<script type="text/javascript">
		function page(n){
			$("#pageNo").val(n);
			$("#searchForm").submit();
	    	return false;
	    }
		
		function addBulletin(id,title){
			var content = '';
			if(id>0){
				content = $("#td"+id).html();
			}
			
			var html = '<form id="publishNotice" action="${ctx}${baseMapper}/update"'+
			' method="post" style="width:450px;height:300px;padding:20px;">'
			+'<div class="control-group"><label class="control-label">标题:</label>'
			+'<div class="controls"><input type="text" maxlength="200" class="required" style="width:100%;" '
			+'id="title" name="title" value="'+title+'" placeholder="请填输入公告标题"/></div></div>'
			+'<div class="control-group"><label class="control-label">内容:</label>'
			+'<div class="controls"><textarea id="content" name="content" class="required" '
			+'style="width:100%;height:150px;" '
			+'placeholder="请填输入公告内容">'+content+'</textarea></div></div>'
			+'<input type="hidden" name="id" value="'+id+'" /></form>';
			
			var submit = function (v, h, f) {
	    	    if(f.title==''){
	    	    	$.jBox.tip("请输入公告标题。", 'error', { focusId: "title" });
	    	    	return false;
	    	    }if(f.content==''){
	    	    	$.jBox.tip("请输入公告内容。", 'error', { focusId: "content" });
	    	    	return false;
	    	    }
	    	    $("#publishNotice").submit();
	    	};
			$.jBox(html,{title:'发布公告',width:500,height:400, submit:submit});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li></li>
	</ul>
<div class="breadcrumb form-search">
	<input id="btnSubmit" onclick="addBulletin(0,'')" class="btn btn-primary" type="button" value="发布公告"/>
</div>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead>
        <tr>
            <th>标题</th>
            <th>内容</th>
            <th>发布时间</th>
            <th>操作</th>
        </tr>
        </thead>
		<tbody>
        <c:forEach items="${page.content}" var="bull" varStatus="status">
            <tr>
                <td>${bull.title}&nbsp;</td>
                <td><pre id="td${bull.id }">${bull.content}</pre></td>
                <td><fmt:formatDate value="${bull.createDate}" pattern="yyyy-MM-dd"/> &nbsp;</td>
                <td>
                <a class="btn btn-danger" href="${ctx}${baseMapper}/delete/${bull.id}">删除</a>
                <button class="btn btn-primary" onclick="addBulletin('${bull.id}','${bull.title}')" >修改</button>
                </td>
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
</body>
</html>