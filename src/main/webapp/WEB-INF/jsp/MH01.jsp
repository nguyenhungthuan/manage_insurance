<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="<c:url value="/resources/js/common.js" />"></script>
<title><spring:message code="TITLE_01" /></title>
</head>
<body>
<form:form action="login" method="POST" modelAttribute="account">
	<center>
		<table class="tbl" cellpadding="4" cellspacing="0" width="400px">
				<tr align="center">
					<td class="td1" colspan="2"><form:errors cssClass="errMsg" path="*" /></td>
				</tr>
				<tr>
					<td class="td1" colspan="2" align="center" style="font-size: 17px"><spring:message code="TITLE_01" /></td>
				</tr>
				<tr align="left">
					<td class="td1, lbl_left"><spring:message code="LOGIN_NAME" /></td>
					<td class="td1" align="left"><form:input path="userName" cssClass="txtBox" /></td>
				</tr>
				<tr>
					<td class="td1, lbl_left"><spring:message code="PASSWORD" /></td>
					<td class="td1" align="left"><form:password path="password" cssClass="txtBox"  /></td>
				</tr>
				<tr>
					<td class="td1" colspan="2" align="center"><input class="btn" type="submit"
						value="<spring:message code="BUTTON_LOGIN" />" />
					</td>
				</tr>
		</table>
	</center>
</form:form>
</body>
</html>