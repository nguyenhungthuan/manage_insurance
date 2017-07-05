<%@page import="net.luvina.manageinsurances.utils.Constant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    <jsp:useBean id="cons" class="net.luvina.manageinsurances.utils.Constant" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="<c:url value="/resources/js/MH02.js" />"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet" type="text/css" />
<title><spring:message code="TITLE_02" /></title>
</head>
<body>
<jsp:include page="header.jsp" />
<h4><spring:message code="TITLE_02" /></h4>
<input type="hidden" value="<%=Constant.BACK %>" id="backconst"/>
<!-- Begin vung dieu kien tim kiem -->
<form:form modelAttribute="inforSearchFormBean" action="${pageContext.servletContext.contextPath}/ListUser/search.do" method="GET"  id="listMain">
<form:hidden path="sortType" />
<form:hidden path="currentPage" />
<input type="hidden" id="ssKey" name="ssKey" value="${ssKey}"/>
	<table  class="tbl_input" border="0" width="15%">
        <tr>
        <td>
      		 <table align="center">
             <tr>
             <td width="30"></td>
                <td width="100" class="lbl_left" style="font-weight: bold">
                    <spring:message code="COMPANY_NAME" />
                </td>
               </tr>
               <tr>
               <td><div id="ajax"></div></td>
               <td class="lbl_left">
                   <form:select id="selectCom" path="companyInternalID" items="${lstCompany}" itemValue="companyInternalId" itemLabel="companyName" />
                </td>
               </tr>
            </table>
            </td>
        </tr>
	</table>
	<br/>
    <div style="font-weight: bold"><spring:message code="INFORMATION_SEARCH" /></div>
    <table class="tbl" cellpadding="6">
					<tr>
                    	<td width="20"></td>
						<td width="150"><spring:message code="NAME_SEARCH" /></td>
						<td width="250" align="left">
						<form:input cssClass="txBox" path="fullName"  />
						</td>
					</tr>
                    <tr>
                    	<td></td>
						<td><spring:message code="INSURANCE_CODE" /></td>
						<td align="left">
						<form:input class="txBox" path="insuranceNumber"/>
						</td>
					</tr>
                    <tr>
                   		<td></td>
						<td><spring:message code="PLACE_SEARCH" /></td>
						<td align="left">
						<form:input class="txBox" path="placeOfRegister" />
						</td>
					</tr>
                    <tr>
                    	<td colspan="3" align="center" >
                        <input type="submit" id="btnSearch" class="btn_02" value="<spring:message code="BUTTON_SEARCH" />"  />
                        </td>
                    </tr>
				</table>
                <br/>
       <table width="100%">
       		<tr>
            <td><input class="btn_02" type="button" name="register" id="register" value="<spring:message code="BUTTON_REGISTER" />" /></td>
            <c:if test="${not empty listInfor}">
            <td align="right" style="padding-right: 300px"><input class="btn_02" type="button" id="export"  name="export" onclick="javascript:downloadCSV()" value="<spring:message code="BUTTON_EXPORT" />"/></td>
            </c:if>
            </tr>
       </table>
	<!-- End vung dieu kien tim kiem -->
</form:form>
<div class="errMsg"><c:out value="${error}"/></div>
<table class="tbl_list" id="table" border="1" cellpadding="4" cellspacing="0" width="100%">
	<c:if test="${empty listInfor }">
	<tr ><td class="errMsg">
		<spring:message code="NO_RECORD"/> 
	</td></tr>
	</c:if>
	<c:if test="${not empty listInfor}">
		<tr>
			<th width="110px">
				<spring:message code="NAME_SEARCH"></spring:message>
				<a href = "javascript: actionMH02('sortType','${inforSearchFormBean.sortType}')">${iconSort}</a>
			</th>
			<th width="55px" align="center">
				<spring:message code="SEX"></spring:message>
			</th>
			<th width="80px" align="center">
				<spring:message code="BIRTHDATE"></spring:message>
			</th>
			<th width="90px" align="center">
				<spring:message code="INSURANCE_CODE"></spring:message>
			</th>
			<th width="110px" align="center">
				<spring:message code="TERM"></spring:message>
			</th>
			<th width="80px" align="center" width="70px">
				<spring:message code="REGISTER_PLACE"></spring:message>
			</th>
		</tr>
		<c:forEach items="${listInfor}" var="user">
		<tr class="tr2">
			<td><a href="${pageContext.servletContext.contextPath}/ViewDetails/${user.userInternalID}/${ssKey}"><c:out value="${user.fullName}"/></a></td>
			<td align="center"><c:out value="${user.sex}"/></td>
			<td align="center"><c:out value="${user.birthday}"/></td>
			<td align="center"><c:out value="${user.insuranceNumber}"/></td>
			<td align="center"><c:out value="${user.insuranceStartDate}"/><spring:message code="SYMBOL" /><c:out value="${user.insuranceEndDate}"/></td>
			<td><c:out value="${user.placeOfRegister}"></c:out></td>
		</tr>
		</c:forEach>
	</table>
	<!-- End vung hien thi danh sach user -->
	<!-- Begin vung paging -->
	<table>
		<tr>
			<td class = "lbl_paging">
			<c:forEach items="${listPaging}" var="num" varStatus="loop">
				<c:if test="${loop.first && num != 1}">
				<a href = "javascript: actionMH02('currentPage','${crPage - 1}')"><<</a> &nbsp;
				</c:if>
				<c:if test="${crPage != num}">
				<a href = "javascript: actionMH02('currentPage','${num}')">| ${num}</a> &nbsp;
				</c:if>
				<c:if test="${crPage == num}">
				<b><c:out value="| ${num}" /></b> &nbsp;
				</c:if>
				<c:if test="${loop.last && num < totalPages}">
				<a href = "javascript: actionMH02('currentPage','${crPage + 1}')">>></a> &nbsp;
				</c:if>
			</c:forEach>
			</td>
		</tr>
	</table>
	</c:if>
	<!-- End vung paging -->
</body>

</html>