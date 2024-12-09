<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html lang="ru">
<head>
	<meta charset="UTF-8">
	<title>Гринготтс :: Банковские счета</title>
	<c:url var="url__style_css" value="${'/style.css'}"/>
	<link rel="stylesheet" href="${url__style_css}">
	<c:url var="url__style_manager_css" value="${'/style-manager.css'}"/>
	<link rel="stylesheet" href="${url__style_manager_css}">
</head>
<body>
<div class="header primary-background">
	<h1 class="header__title">Банк «Гринготтс»</h1>
</div>
<div class="content">
	<h2 class="page_title primary-color">Банковские счета</h2>
	<table class="data_table">
		<tr class="secondary-background">
			<th>Номер</th>
			<th>Владелец</th>
			<th>Баланс</th>
			<th></th>
		</tr>
		<%--@elvariable id="accounts" type="java.util.List"--%>
		<c:forEach var="account" items="${accounts}">
			<%--@elvariable id="account" type="by.vsu.ist.domain.Account"--%>
			<c:choose>
				<c:when test="${not account.active}"><c:set var="css_class" value="data_table__not_active"/></c:when>
				<c:otherwise><c:remove var="css_class"/></c:otherwise>
			</c:choose>
			<tr class="${css_class}">
				<td>${account.number}</td>
				<td>${account.owner}</td>
				<td class="balance"><fmt:formatNumber value="${account.balance div 100}" pattern="#,##0.00"/></td>
				<td>
					<c:url var="url__manager_account_edit" value="${'/manager/account/edit.html'}">
						<c:param name="id" value="${account.id}"/>
					</c:url>
					<a href="${url__manager_account_edit}" class="text-link primary-color">изменить</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<div class="buttons_block">
		<c:url var="url__manager_account_edit" value="${'/manager/account/edit.html'}"/>
		<a href="${url__manager_account_edit}" class="button button__secondary">Открыть счёт</a>
	</div>
</div>
</body>
</html>