<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:set value="${pageContext.request.contextPath}" var="url"></c:set>
	<c:if test="${customerMsg.id!=null}">
		<c:set
			value="${pageContext.request.contextPath}/msg/${customerMsg.id}"
			var="url"></c:set>
	</c:if>
	<form:form action="${url}" modelAttribute="customerMsg" method="POST">
		<c:if test="${customerMsg.id!=null}">
			<input type="hidden" name="_method" value="PUT" />
		</c:if>
		<table>
			<tr align="right">
				<td>customer:</td>
				<td><form:input path="customer" /><form:errors path="customer"></form:errors></td>
			</tr>
			<tr align="right">
				<td>adrress:</td>
				<td><form:input path="address" /><form:errors path="address"></form:errors></td>
			</tr>
			<tr align="right">
				<td>cnee:</td>
				<td><form:input path="cneeMsg.cnee" /><form:errors path="cneeMsg.cnee"></form:errors></td>
			</tr>
			<tr align="right">
				<td>update_user:</td>
				<td><form:input path="update_user" disabled="true" /></td>
			</tr>
			<tr align="right">
				<td>update_time:</td>
				<td><form:input path="update_time"  disabled="true"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit"
					value="Submit" /><br></td>
			</tr>
		</table>






	</form:form>
</body>
</html>