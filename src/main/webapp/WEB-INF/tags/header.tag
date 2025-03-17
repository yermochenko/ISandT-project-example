<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<div class="header primary-background">
	<h1 class="header__title">Волшебный банк «Гринготтс»</h1>
	<%--@elvariable id="session_user" type="by.vsu.ist.domain.User"--%>
	<c:if test="${not empty session_user}">
		<div style="float: right">
			<c:url var="url__logout_perform" value="${'/logout/perform.html'}"/>
			<a href="${url__logout_perform}">${session_user.login}</a>
		</div>
	</c:if>
</div>