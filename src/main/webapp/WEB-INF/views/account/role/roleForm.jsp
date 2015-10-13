<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<html>
<head>
    <title>角色管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#inputForm").validate({
                <c:if test="${role.id == 0}">
                rules: {
                    name: {remote: "${ctx}${baseMapper}/validate"}
                },
                messages: {
                    name: {remote: "角色名已存在"}
                },
                </c:if>
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}${baseMapper}">角色列表</a></li>
    <li class="active"><a href="#">角色<shiro:hasPermission
            name="role:edit">${role.id > 0 ? '修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="role:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>

<form id="inputForm" action="${ctx}${baseMapper}/update" method="post" class="form-horizontal">
    <input type="hidden" name="id" value="${role.id}"/>
    <tags:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">角色名:</label>
        <div class="controls">
            <input type="text" maxlength="50" class="required" id="name"
                   name="name" value="${role.name}"
            <c:choose>
            <c:when test="${empty role.name}"> placeholder="请填写数字或者字母"</c:when>
                   <c:otherwise>readonly="readonly" </c:otherwise>
            </c:choose>>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">权限信息:</label>

        <div class="controls">
            <table class="table table-striped table-bordered table-condensed">
                <thead>
                <tr>
                    <th>权限组</th>
                    <th>权限</th>
                </tr>
                </thead>
                <tbody id="aus">
                <tr>
                    <c:forEach items="${permissionListByType}" var="perType">
                    <td>${perType.key}</td>
                    <td><c:forEach items="${perType.value}" var="per">
                        <input type="checkbox" id="permissions" name="permissions"
                               value="${per.value}"
                                <c:if test="${fn:contains(checkedPermission,per.value)}"> checked </c:if>
                               class="required"/>${per.displayName}

                    </c:forEach></td>
                </tr>
                <tr>
                    </c:forEach>
                </tr>
                </tbody>
            </table>
        </div>
    </div>

    <div class="form-actions">
        <shiro:hasPermission name="role:edit"><input id="btnSubmit" class="btn btn-primary" type="submit"
                                                     value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form>
</body>
</html>