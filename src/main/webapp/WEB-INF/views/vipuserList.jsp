<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<html>
<head>
	<title>会员管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<script type="text/javascript">
		function page(n){
			$("#pageNo").val(n);
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}${baseMapper}">会员列表</a></li>
		<shiro:hasPermission name="user:edit"><li><a href="${ctx}${baseMapper}/update/0">新会员注册</a></li></shiro:hasPermission>
	</ul>
	<form id="searchForm" action="${ctx}${baseMapper}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.number+1}"/>
		<input id="orderDir" name="orderDir" type="hidden" value="${pageRequest.orderDir}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${pageRequest.orderBy}"/>
		<div>
			<%-- <label>登录名：</label><input type="text" name="search_EQ_loginName" class="input-small" value="${param.search_EQ_loginName}"> --%>
            <label>姓&nbsp;&nbsp;&nbsp;名：</label><input type="text" name="search_LIKE_name" class="input-small" value="${param.search_LIKE_name}">
            &nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		</div>
	</form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead>
        <tr>
            <th>姓名</th>
            <th>余额</th>
            <th>操作</th>
        </tr>
        </thead>
		<tbody>
        <c:forEach items="${page.content}" var="user" varStatus="status">
            <tr>
                <td>${user.name}&nbsp;</td>
                <td id="m${user.id }">￥${user.balance}&nbsp;元</td>
                <td>
                <a class="btn btn-primary" onclick="resetPwd('${user.name}','${user.id}')">重置密码</a>
                <a class="btn btn-primary" onclick="takeoff('${user.name}','${user.id}','${user.balance}')">取现</a>
                <a class="btn btn-primary" href="${ctx}${baseMapper}/detail?id=${user.id}">详情</a>
                
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

    <script type="text/javascript">
    function resetPwd(name,id){
    	var html = "<div style='padding:10px;'>  密码："+
    	"<input type='password' id='password' name='password'/></div>";
    	var submit = function (v, h, f) {
    	    if (f.password == '') {
    	        $.jBox.tip("请输入密码。", 'error', { focusId: "password" });
    	        return false;
    	    }
    	    $.ajax({url:'${ctx}/vipuser/resetPwd',dataType:'json',data:{'id':id,'pwd':f.password},
    	    	type:'post',async:true,success:function(data){
    	    		$.jBox.tip('重置密码'+data?'成功':'失败',"info");
    	    	}});
    	    return true;
    	};
    	$.jBox(html, { title: name+" : 重置密码", submit: submit });
    }
    function takeoff(name,id,yue){
    	var html = "<div style='padding:10px;'>  取现 ："+
    	"<input type='number' id='balance' name='balance' /></div>";
    	var submit = function (v, h, f) {
    	    if (f.balance == '') {
    	        $.jBox.tip("请输入取现金额。", 'error', { focusId: "balance" });
    	        return false;
    	    }
    	    if(parseFloat(f.balance)>yue){
    	    	$.jBox.tip("取现金额大于余额。","error");
    	        return false;
    	    }
    	    	
    	    $.ajax({url:'${ctx}/takerecord/take',dataType:'json',data:{'id':id,'money':f.balance},
    	    	type:'post',async:true,success:function(data){
    	    		$("#m"+id).html('￥'+(yue-f.balance)+' 元');
    	    		$.jBox.tip('取现 ￥'+f.balance+data?'元，成功':'元，失败',"info");
    	    	}});
    	    return true;
    	};

    	$.jBox(html, { title: name+" : 现有余额 ￥"+yue+"元", submit: submit });
    }
   
    </script>
</body>
</html>