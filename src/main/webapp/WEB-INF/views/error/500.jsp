<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<div id="page-wrapper">
    <div class="row">
        <div class="panel panel-danger" style="text-align: center;">
            <div class="panel-heading">500 - 系统内部错误!</div>
            <div class="panel-body">
                <p>非常抱歉，系统内部发生错误！可能是您没有权限，请联系系统管理员</p>
            </div>
            <div class="panel-footer"> <p><a href="${ctx}/">返回首页</a></p></div>
        </div>
    </div>
</div>