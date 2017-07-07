<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    <%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript" src="<c:url value="/resources/js/MH04.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/datePicker.js" />"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<jsp:include page="header.jsp"></jsp:include>
<title><spring:message code="ADD_INSURANCE" /></title>
</head>
<body>
	<H3><p><spring:message code="ADD_INSURANCE" /></p></H3>
<form:form action="${pageContext.servletContext.contextPath}/Update.do" method="POST" modelAttribute="userInsurance" id="registerMain">
<form:hidden path="userInternalID"/>
<input type="hidden" id="companyID" value="${userInsurance.companyInternalID}" />
<input type="hidden" name="ssKey" value="${ssKey}">
<table width="40%">
	<tr><td align="center"><form:errors cssClass="errMsg" path="insuranceNumber"  /></td></tr>
	<tr><td align="center"><form:errors cssClass="errMsg" path="fullName"  /></td></tr>
	<tr><td align="center"><form:errors cssClass="errMsg" path="sex"  /></td></tr>
	<tr><td align="center"><form:errors cssClass="errMsg" path="birthday"  /></td></tr>
	<tr><td align="center"><form:errors cssClass="errMsg" path="companyName"  /></td></tr>
	<tr><td align="center"><form:errors cssClass="errMsg" path="companyAddress"  /></td></tr>
	<tr><td align="center"><form:errors cssClass="errMsg" path="email"  /></td></tr>
	<tr><td align="center"><form:errors cssClass="errMsg" path="telephone"  /></td></tr>
	<tr><td align="center"><form:errors cssClass="errMsg" path="placeOfRegister"  /></td></tr>
	<tr><td align="center"><form:errors cssClass="errMsg" path="insuranceStartDate"  /></td></tr>
	<tr><td align="center"><form:errors cssClass="errMsg" path="insuranceEndDate"  /></td></tr>
</table>
    <table class="tbl_input" cellpadding="6px" width="100%" >
    	<tr>
        	<td width="15%"><spring:message code="INSURANCE_CODE" /></td>
            <td><form:input path="insuranceNumber"/></td>
        </tr>
        <tr>
       		<td><spring:message code="FULLNAME" /></td>
            <td><form:input path="fullName"/></td>
        </tr>
        <tr>
        	<td><spring:message code="SEX" /></td>
            <td> <form:select path="sex">
	            	<form:option value="1" ><spring:message code="MALE" /> </form:option>
	            	<form:option value="2" ><spring:message code="FEMALE" /></form:option>
            	</form:select>
            </td>
        </tr>
        <tr>
        	<td><spring:message code="BIRTHDATE" /></td>
            <td><form:input path="birthday" /></td>
        </tr>
        <tr>
        	<td valign="top"><spring:message code="COMPANY" /></td>
        	<td><table width="100%"><tr><td colspan="2">
        
            <input type="radio" id="radioExistedXCom" name="radioCom"  value="existedCom"><spring:message code="EXISTED_COM"  />
            </td></tr>
            <tr><td> 
            <table id="existedCom" width="100%" >           
        	<tr>
	        	<td width="20%"><spring:message code="COMPANY" /></td>
	        	<td><form:select path="companyInternalID" onchange="javascript:loadComInfor();" /> </td>
            </tr>
            <tr>
            	<td><spring:message code="COMPANY_NAME" /></td>
        		<td id="comName"> </td>
            </tr>
            <tr>
            	<td><spring:message code="ADDRESS" /></td>
        		<td id="addr"></td>
            </tr>
            <tr>
            	<td><spring:message code="EMAIL" /></td>
        		<td ><a id="email"></a></td>
            </tr>
            <tr>
            	<td><spring:message code="TEL" /></td>
        		<td id="tel"></td>
            </tr>
       		</table>
        </td>
        </tr>
        </table>
        <tr>
        	<td></td>
        	<td>
        		<table width="100%">
        			<tr><td colspan="2">
        				<input type="radio" name="radioCom" value="newCom"><spring:message code="NEW_COM" />
        			</td></tr>
					<tr><td>
					<table id="newComTbl" width="100%"  >
					<tr>
                    	<td width="10%">
                    	  <spring:message code="COMPANY_NAME" /></td>
                        <td><form:input path="companyName"/></td>
                    </tr>
                    <tr>
                    	<td><spring:message code="ADDRESS" /></td>
                        <td><form:input path="companyAddress"/></td>
                    </tr>
                    <tr>
                    	<td><spring:message code="EMAIL" /></td>
                        <td><form:input path="email"/></td>
                    </tr>
                    <tr>
                    	<td><spring:message code="TEL" /></td>
                        <td><form:input path="telephone"/></td>
                    </tr>
        		</table>
        	</td>
        </tr></table>
        <tr>
        	<td><spring:message code="REGISTER_PLACE" /></td>
            <td><form:input path="placeOfRegister"/></td>
        </tr>
        <tr>
        	<td><spring:message code="START_DATE" /></td>
            <td><form:input path="insuranceStartDate"/></td>
        </tr>
        <tr>
        	<td><spring:message code="END_DATE" /></td>
            <td><form:input path="insuranceEndDate"/></td>
        </tr>
        <tr>
        	<td align="center">
        		<input type="button" class="btn_02" value="<spring:message code="BTN_CANCEL" />" onclick="location.href='${pageContext.servletContext.contextPath}/ListUser/back.do?ssKey=${ssKey}'" name="cancel" />
        	</td><td>
        		<input type="submit" class="btn_02" value="<spring:message code="BUTTON_REGISTER" />" name="register"/>
        	</td>
        </tr>
    </table>
</form:form>
</body>
</html>