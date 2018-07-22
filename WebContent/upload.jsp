<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-1.9.1.min.js"></script>
<script>
   $(function(){
	   $(":button[name=download]").click(function(){
		   location="${pageContext.request.contextPath}/msg/download/module";
	   });
   });
</script>
<script src="${pageContext.request.contextPath}/scripts/Ajax.js"></script>
<script src="${pageContext.request.contextPath}/scripts/jquery.blockUI.js"></script>
<script src="${pageContext.request.contextPath}/scripts/php_server_valiate.js"></script>

<title>Insert title here</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/msg/upload" method="POST"
		enctype="multipart/form-data">
		<table border="0px">
		    <tr>
		       <td><input type="button" name="download" value="模板下载"/></td><td></td>
		    </tr>
			<tr>
				<td>请上传 CustomerMessage:</td>
				<td><input type="file" name="file" /></td>
			</tr>
			<tr>
				<td align="center" colspan="2"><input type="submit" value="上传" /></td>
			</tr>
		</table>
	</form>

</body>
</html>