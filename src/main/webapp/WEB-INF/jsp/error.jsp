<%@page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@taglib prefix="u" tagdir="/WEB-INF/tags"%>
<c:set var="user_css" value="${'/style-guest.css'}"/>
<%--@elvariable id="session_user" type="by.vsu.ist.domain.User"--%>
<c:if test="${not empty session_user}">
	<c:choose>
		<c:when test="${session_user.role == 'MANAGER'}">
			<c:set var="user_css" value="${'/style-manager.css'}"/>
		</c:when>
		<c:when test="${session_user.role == 'CASHIER'}">
			<c:set var="user_css" value="${'/style-cashier.css'}"/>
		</c:when>
		<c:when test="${session_user.role == 'ADMIN'}">
			<c:set var="user_css" value="${'/style-admin.css'}"/>
		</c:when>
	</c:choose>
</c:if>
<u:page title="Гринготтс :: Ошибка" css="${[user_css]}">
	<h2 class="page_title primary-color">Ошибка</h2>
	<div class="buttons_block">
		<c:url var="url__index" value="${'/index.html'}"/>
		<a href="${url__index}" class="button button__secondary">На главную</a>
	</div>
</u:page>