<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<html>
<head>
    <title>会员管理</title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/dialog.jsp" %>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#inputForm").validate({
                <c:if test="${user.id == 0}">
                rules: {
                    loginName: {remote: "${ctx}${baseMapper}/validate"}
                },
                messages: {
                    loginName: {remote: "该登录名已存在"}
                },
                </c:if>
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    error.insertAfter(element);
                }
            });
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}${baseMapper}">会员列表</a></li>
    <li class="active"><a href="#">新会员注册</a></li>
</ul>
<br/>

<form id="inputForm" action="${ctx}${baseMapper}/update" method="post" class="form-horizontal">
    <input type="hidden" name="id" value="${user.id}"/>
    <tags:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">用户名:</label>
        <div class="controls">
            <input type="text" maxlength="50" class="required" id="loginName"
                   name="loginName" value="${user.loginName}"
            <c:choose>
            <c:when test="${empty user.loginName}"> placeholder="请填写数字或者字母"</c:when>
                   <c:otherwise>readonly="readonly" </c:otherwise>
            </c:choose>>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">姓名:</label>

        <div class="controls">
            <input type="text" maxlength="50" class="required" id="name" name="name"
                   value="${user.name}"
            <c:if test="${empty user.name }"> placeholder="请填写中文"</c:if>>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">密码:</label>

        <div class="controls">
            <input type="password" maxlength="50" class="${user.id == 0 ?'required':''}" id="plainPassword"
                   name="plainPassword" value="${user.plainPassword}">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">
        <a class="btn btn-info" onclick="upload()">上传头像</a>
        </label>
        <div class="controls">
        <input type="hidden" id="headImg" name="headImg"/>
        <img style="width: 200px;height: 100px;" title="头像"  alt=""
        onclick="" id="headimg" />
        </div>
    </div>
	<script type="text/javascript">
		function upload(){
			$.jBox("iframe:${ctx }/updload/frm",
				{id:'uploadimg',
			    title: "上传头像",
			    width: 400,
			    height: 300
			});
		}
		function setUploadSuccess(file){
			$("#headimg").attr('src',"${ctx}/static/upload/head/"+file);
			$("#headImg").val('/static/upload/head/'+file);
			$.jBox.close("uploadimg");
		}
	</script>
	<div class="control-group">
        <label class="control-label">推荐人:</label>
        <div class="controls">
        	<select name="recommend">
        		<option value="">&nbsp;&nbsp;&nbsp;&nbsp; 无 </option>
        		<c:forEach items="${allVipUser }" var="vipUser">
        		<option value="${vipUser.id }">${vipUser.name }</option>
        		</c:forEach>
        	</select>
        </div>
    </div>

    <div class="form-actions">
        <shiro:hasPermission name="user:edit">
        <input id="btnSubmit" class="btn btn-primary" type="submit"value="注  册"/>&nbsp;
        </shiro:hasPermission>
    </div>
</form>
</body>
</html>