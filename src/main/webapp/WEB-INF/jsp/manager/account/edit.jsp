<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@taglib prefix="fn" uri="jakarta.tags.functions"%>
<%--@elvariable id="account" type="by.vsu.ist.domain.Account"--%>
<c:choose>
	<c:when test="${not empty account}">
		<c:set var="title" value="Изменение счёта"/>
	</c:when>
	<c:otherwise>
		<c:set var="title" value="Открытие счёта"/>
	</c:otherwise>
</c:choose>
<!DOCTYPE html>
<html lang="ru">
<head>
	<meta charset="UTF-8">
	<title>Гринготтс :: ${title}</title>
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
	<h2 class="page_title primary-color">${title}</h2>
	<c:url var="url__manager_account_save" value="${'/manager/account/save.html'}"/>
	<form action="${url__manager_account_save}" method="post" class="form">
		<c:if test="${not empty account}">
			<input type="hidden" name="id" value="${account.id}">
			<c:set var="accessibility" value="disabled"/>
		</c:if>
		<div class="input_block">
			<label for="number-input">Номер счёта:</label>
			<input type="text" id="number-input" name="number" value="${account.number}" ${accessibility}>
		</div>
		<div class="input_block">
			<label for="owner-input">Владелец счёта:</label>
			<input type="text" id="owner-input" name="owner" value="${account.owner}">
		</div>
		<c:if test="${not empty account}">
			<div class="input_block">
				<label for="balance-input">Баланс счёта:</label>
				<fmt:formatNumber var="balance_kopecks" value="${account.balance mod 100}" pattern="00"/>
				<fmt:formatNumber var="balance_rubles" value="${(account.balance - balance_kopecks) div 100}"/>
				<input type="text" id="balance-input" value="${balance_rubles} руб. ${balance_kopecks} коп." disabled class="balance">
			</div>
			<div class="input_block">
				<label>
					<c:if test="${account.active}">
						<c:set var="active" value="checked"/>
					</c:if>
					<input type="checkbox" name="active" ${active}> активный ли счёт
				</label>
			</div>
			<div class="input_block">
				<label for="operations-amount-input">Количество операций по счёту:</label>
				<input type="text" id="operations-amount-input" value="${fn:length(account.transfers)}" disabled>
			</div>
			<c:if test="${not empty account.transfers}">
				<div class="input_block">
					<label for="last-operation-date-input">Дата последней операции:</label>
					<fmt:formatDate var="last_operation_date" value="${account.transfers[0].date}" pattern="d MMMM yyyy г."/>
					<input type="text" id="last-operation-date-input" value="${last_operation_date}" disabled>
				</div>
			</c:if>
		</c:if>
		<div class="buttons_block">
			<button type="submit" class="button button__primary">Сохранить</button>
			<c:url var="url__manager_account_list" value="${'/manager/account/list.html'}"/>
			<a href="${url__manager_account_list}" class="button button__secondary">Назад</a>
			<c:if test="${not empty account}">
				<c:if test="${not empty account.transfers}">
					<c:set var="disabled" value="disabled"/>
				</c:if>
				<button type="submit" form="delete-form" class="button button__danger" ${disabled}>Удалить</button>
			</c:if>
		</div>
	</form>
	<c:if test="${not empty account}">
		<c:url var="url__manager_account_delete" value="${'/manager/account/delete.html'}"/>
		<form id="delete-form" action="${url__manager_account_delete}" method="post">
			<input type="hidden" name="id" value="${account.id}">
		</form>
	</c:if>
</div>
</body>
</html>