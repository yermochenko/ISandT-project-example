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
	<c:url var="url__style_cashier_css" value="${'/style-cashier.css'}"/>
	<link rel="stylesheet" href="${url__style_cashier_css}">
</head>
<body>
<div class="header primary-background">
	<h1 class="header__title">Банк «Гринготтс»</h1>
</div>
<div class="content">
	<h2 class="page_title primary-color">Активные банковские счета</h2>
	<table class="data_table">
		<tr class="secondary-background">
			<th>Номер</th>
			<th>Владелец</th>
			<th>Баланс</th>
			<th>Дата последней операции</th>
		</tr>
		<%--@elvariable id="accounts" type="java.util.List"--%>
		<c:forEach var="account" items="${accounts}">
			<%--@elvariable id="account" type="by.vsu.ist.domain.Account"--%>
			<tr>
				<td>
					<c:url var="url__cashier_account_view" value="${'/cashier/account/view.html'}">
						<c:param name="id" value="${account.id}"/>
					</c:url>
					<a href="${url__cashier_account_view}" class="text-link primary-color">${account.number}</a>
				</td>
				<td>${account.owner}</td>
				<td class="balance"><fmt:formatNumber value="${account.balance div 100}" pattern="#,##0.00"/></td>
				<td>
					<c:if test="${not empty account.transfers}">
						<fmt:formatDate value="${account.transfers[0].date}" pattern="dd.MM.yyyy"/>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>
</body>
</html>