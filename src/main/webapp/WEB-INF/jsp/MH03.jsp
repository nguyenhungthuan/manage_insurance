<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="<c:url value="/resources/js/MH03.js" />"></script>
<title><spring:message code="TITLE_03" /></title>
</head>
<jsp:include page="header.jsp"/>
<body>
	<h4><spring:message code="TITLE_03" /></h4>
    <input type="button" class="btn_02" name="back" value="<spring:message code="BTN_BACK" />" onclick="location.href='${pageContext.servletContext.contextPath}/ListUser/back.do?ssKey=${ssKey}'" />
    <br /><br />
    <form:form action="${pageContext.servletContext.contextPath}/DeleteUser.do" method="POST" modelAttribute="userInsurance" id="viewMain">
    <form:hidden path="userInternalID" />
    <input type="hidden" name="ssKey" value="${ssKey}"/>
    	<table class="tbl_display" width="80%">
        	<tr>
            	<td class="td3" width="28%"><spring:message code="INSURANCE_CODE" /></td>
                <td class="td2"><form:label path="insuranceNumber"><c:out value="${userInsurance.insuranceNumber}"/></form:label> </td>
            </tr>
            <tr>
            	<td class="td3"><spring:message code="FULLNAME" /></td>
                <td class="td2"><form:label path="fullName"><c:out value="${userInsurance.fullName}"/></form:label></td>
            </tr>
            <tr>
            	<td class="td3"><spring:message code="SEX" /></td>
                <td class="td2"><form:label path="sex"><c:out value="${userInsurance.sex}"/></form:label></td>
            </tr>
            <tr>
            	<td class="td3"><spring:message code="BIRTHDATE" /></td>
                <td class="td2"><form:label path="birthday"><c:out value="${userInsurance.birthday}"/></form:label></td>
            </tr>
            <tr>
            	<td class="td3"><spring:message code="COMPANY_NAME" /></td>
                <td class="td2"><form:label path="companyName"><c:out value="${userInsurance.companyName}"/></form:label></td>
            </tr><tr>
            	<td class="td3"><spring:message code="REGISTER_PLACE" /></td>
                <td class="td2"><form:label path="placeOfRegister"><c:out value="${userInsurance.placeOfRegister}"/></form:label></td>
            </tr>
            <tr>
            	<td class="td3"><spring:message code="TERM" /></td>
                <td class="td2"><c:out value="${userInsurance.insuranceStartDate}"/> <spring:message code="TO" /> <c:out value="${userInsurance.insuranceEndDate}"/></td>
            </tr>
            <tr>
            	<td align="center"><input class="btn_02" type="submit" name="delete" id="delete" value="<spring:message code="BTN_DEL" />" /></td>
                <td><input class="btn_02" type="button" name="update" id="update" value="<spring:message code="BTN_UPDATE" />" /></td>
            </tr>
        </table>
    </form:form>
    <input type="hidden" id="msg_delete" value="<spring:message code="CONFIRM_DELETE" />"/>
</body>
</html>
