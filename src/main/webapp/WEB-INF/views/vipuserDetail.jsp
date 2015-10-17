<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<html>
<head>
    <title>一美光电会员详细信息</title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/dialog.jsp" %>
    <script type="text/javascript">
    $(document).ready(function (){
    	var he = $("#thHeadImg").height();
    	var wd = $("#thHeadImg").width();
    	var jImg = $("#headImg");
    	var imgh = jImg.height();
    	var imgw = jImg.width();
    	var rh = he/imgh ,rd = wd/imgw;
    	if(rh> rd){
    		jImg.css({'width':wd,'height':parseInt(imgh*rd),'display':'block'});
    	}else{
    		jImg.css({'width':parseInt(imgw*rh),'height':he,'display':'block'});
    	}
    });
    function updatePwd(){
    	var html = "<div style='padding:10px;'> 密 &nbsp; &nbsp; &nbsp;&nbsp;码："+
    	"<input type='password' id='password1' name='password1'/><br>"+
    	"确认密码：<input type='password' id='password2' name='password2'/></div>";
    	var submit = function (v, h, f) {
    	    if (f.password1 == '') {
    	        $.jBox.tip("请输入密码。", 'error', { focusId: "password1" });
    	        return false;
    	    }if (f.password2 == '' || f.password2 != f.password1) {
    	        $.jBox.tip("请确认两次密码相同。", 'error', { focusId: "password2" });
    	        return false;
    	    }
    	    $.ajax({url:'${ctx}/vipuser/resetPwd',dataType:'json',data:{'id':'${user.id}','pwd':f.password2},
    	    	type:'post',async:true,success:function(data){
    	    		$.jBox.tip('修改密码'+data?'成功':'失败',"info");
    	    	}});
    	    return true;
    	};
    	$.jBox(html, { title: "修改密码", submit: submit });
    }
    </script>
</head>
<body>
<div class="panel panel-info" style="padding: 15px;">
	<div class="panel-heading" style="padding: 15px;">
		<table class="table table-condensed" cellPadding="0" cellSpacing="0">
			<tr align="right"><th colspan="4" style="text-align: right;" width="80%">
			<button class="btn btn-defalut" onclick="updatePwd()">修改密码</button>
			<c:if test="${needLogout}">
			<a href="${ctx}/logout" title="退出登录" class="btn btn-defalut">退出</a>
			</c:if>
			</th><th width="20%" rowspan="5" id="thHeadImg" style="text-align: center;">
				<img id="headImg" src="${ctx }${user.headImg }" style="display: none;">
			</th></tr>
			<tr>
				<td width="10%">姓 &nbsp;&nbsp; &nbsp;&nbsp; 名：</td>
				<td width="70%">${user.name }</td>
			</tr>
			<tr><td>电话号码：</td><td>${user.mobilPhone }</td></tr>
			<tr><td>账号余额：</td><td>￥${user.balance }元</td></tr>
			<tr><td>我的下属：</td><td>${fn:length(bonusRecords) }人</td></tr>
			<tr><td></td><td></td></tr>
		</table>
	</div>
	<div class="panel-body">
		<div class="well well-info">一级下属
		<table class="table table-striped table-bordered table-condensed" style="max-height: 400px;">
			<tr><th>姓 名</th><th>加入时间</th><th>贡献值</th></tr>
			<c:forEach items="${newUsers.fristLevel }" var="frist">
				<tr><td>${frist.newUser.name }</td>
				<td><fmt:formatDate value="${frist.newUser.registerDate }" pattern="yyyy年MM月dd日"/></td>
				<td>1000</td></tr>
			</c:forEach>
		</table>
		</div>
		<div class="well">二级下属
		<table class="table table-striped table-bordered table-condensed">
			<tr><th>姓 名</th><th>推荐人</th><th>加入时间</th><th>贡献值</th></tr>
			<c:forEach items="${newUsers.secondLevel }" var="second">
				<tr><td>${second.newUser.name }</td><td>${second.recommendUser.name }</td>
				<td><fmt:formatDate value="${second.newUser.registerDate }" pattern="yyyy年MM月dd日"/></td>
				<td>300</td></tr>
			</c:forEach>
		</table>
		</div>
		<div class="well">三级下属
		<table class="table table-striped table-bordered table-condensed">
			<tr><th>姓  名</th><th>推荐人</th><th>加入时间</th><th>贡献值</th></tr>
			<c:forEach items="${newUsers.threeLevel }" var="three">
				<tr><td>${three.newUser.name }</td><td>${three.recommendUser.name }</td>
				<td><fmt:formatDate value="${three.newUser.registerDate }" pattern="yyyy年MM月dd日"/></td>
				<td>200</td></tr>
			</c:forEach>
		</table>
		</div>
		<div class="well">收款记录
		<table class="table table-striped table-bordered table-condensed">
			<tr><th>时  间</th><th>描  述</th><th>数  值</th></tr>
			<c:forEach items="${bonusRecords }" var="bonusRecord">
				<tr><td><fmt:formatDate value="${bonusRecord.createDate }" pattern="yyyy年MM月dd日"/></td>
				<td>${bonusRecord.remark }</td>
				<td>${bonusRecord.bonus }</td></tr>
			</c:forEach>
		</table>
		</div>
		<div class="well">取现记录
		<table class="table table-striped table-bordered table-condensed">
			<tr><th>时  间</th><th>取  现</th><th>余  额</th></tr>
			<c:forEach items="${takeoffRecords }" var="takeoff">
				<tr><td><fmt:formatDate value="${takeoff.createDate }" pattern="yyyy年MM月dd日"/></td>
				<td>${takeoff.bonus }</td>
				<td>${takeoff.balance }</td></tr>
			</c:forEach>
		</table>
		</div>
	</div>
</div>
</body>
</html>