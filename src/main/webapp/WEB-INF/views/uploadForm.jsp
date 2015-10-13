<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<html>
<head>
    <title>上传文件</title>
    <meta name="decorator" content="default"/>
</head>
<body>
<c:choose>
<c:when test="${result}">
<script type="text/javascript">
	$(function(){
		window.parent.setUploadSuccess('${message}');
	});
</script>
</c:when><c:otherwise>
<form id="inputForm" action="${ctx}/updload/frm" method="post" enctype="multipart/form-data">
    <div class="form-actions">
    	<ul style="list-style: none;">
	    	<li><input type="file" id="uploadFile" name="file"/></li>
	    	<li style="text-align: right;"><font color="red">${message }</font>
	        	<input id="btnSubmit" class="btn btn-primary" type="submit"value="保 存"/>
	    	</li>
    	</ul>
    </div>
</form>
</c:otherwise>
</c:choose>
</body>
</html>