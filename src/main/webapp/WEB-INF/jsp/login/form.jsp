<%@page contentType="text/html;charset=UTF-8"%>
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
<u:page title="Гринготтс :: Авторизация" css="${[user_css, '/popup.css']}" js="${['/popup.js']}">
	<h2 class="page_title primary-color">Авторизация</h2>
	<c:url var="url__login_perform" value="${'/login/perform.html'}"/>
	<form action="${url__login_perform}" method="post" class="form">
		<div class="input_block">
			<label for="login-input">Имя пользователя:</label>
			<input type="text" id="login-input" name="login">
		</div>
		<div class="input_block">
			<label for="password-input">Пароль:</label>
			<input type="password" id="password-input" name="password">
		</div>
		<div class="buttons_block">
			<button type="submit" class="button button__primary">Войти</button>
		</div>
	</form>
	<c:if test="${not empty param['msg']}">
		<script type="text/javascript">showMessage('${param['msg']}')</script>
	</c:if>
</u:page>