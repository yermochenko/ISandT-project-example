<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%--@elvariable id="account" type="by.vsu.ist.domain.Account"--%>
<!DOCTYPE html>
<html lang="ru">
<head>
	<meta charset="UTF-8">
	<title>Гринготтс :: Операции по счёту ${account.number}</title>
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
	<h2 class="page_title primary-color">Операции по счёту ${account.number}</h2>
	<c:url var="url__cashier_account_list" value="${'/cashier/account/list.html'}"/>
	<p><a href="${url__cashier_account_list}" class="button button__secondary">Назад</a></p>
	<c:if test="${not account.active}">
		<p class="warning">Счёт не активен</p>
	</c:if>
	<p><b>Владелец счёта</b>: ${account.owner}</p>
	<fmt:formatNumber var="balance_kopecks" value="${account.balance mod 100}" pattern="00"/>
	<fmt:formatNumber var="balance_rubles" value="${(account.balance - balance_kopecks) div 100}"/>
	<p><b>Баланс счёта</b>: <span class="view-balance">${balance_rubles}</span> руб. <span class="view-balance">${balance_kopecks}</span> коп.</p>
	<c:choose>
		<c:when test="${not empty account.transfers}">
			<table class="data_table">
				<tr class="secondary-background">
					<th>Дата операции</th>
					<th>Время операции</th>
					<th>Сумма операции</th>
					<th>Тип операции</th>
					<th>Назначение платежа</th>
				</tr>
					<%--@elvariable id="accounts" type="java.util.List"--%>
				<c:forEach var="transfer" items="${account.transfers}">
					<%--@elvariable id="transfer" type="by.vsu.ist.domain.Transfer"--%>
					<tr>
						<td><fmt:formatDate value="${transfer.date}" pattern="dd.MM.yyyy"/></td>
						<td><fmt:formatDate value="${transfer.date}" pattern="HH:mm:ss"/></td>
						<td class="balance">
							<c:choose>
								<c:when test="${transfer.sender.present and transfer.sender.get().id == account.id}">
									<span class="outcoming-sum">&minus;<fmt:formatNumber value="${transfer.sum div 100}" pattern="#,##0.00"/></span>
								</c:when>
								<c:otherwise>
									<span class="incoming-sum">&plus;<fmt:formatNumber value="${transfer.sum div 100}" pattern="#,##0.00"/></span>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${transfer.sender.present}">
									<c:choose>
										<c:when test="${transfer.sender.get().id == account.id}">
											<c:choose>
												<c:when test="${transfer.receiver.present}">
													<c:url var="url__cashier_account_view" value="${'/cashier/account/view.html'}">
														<c:param name="id" value="${transfer.receiver.get().id}"/>
													</c:url>
													исходящий перевод на счёт <a href="${url__cashier_account_view}" class="text-link primary-color">${transfer.receiver.get().number}</a>
												</c:when>
												<c:otherwise>снятие наличных</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<c:url var="url__cashier_account_view" value="${'/cashier/account/view.html'}">
												<c:param name="id" value="${transfer.sender.get().id}"/>
											</c:url>
											входящий перевод со счёта <a href="${url__cashier_account_view}" class="text-link primary-color">${transfer.sender.get().number}</a>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>пополнение наличными</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:if test="${transfer.purpose.present}">${transfer.purpose.get()}</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<p>По счёту пока нет ни одной операции</p>
		</c:otherwise>
	</c:choose>
</div>
</body>
</html>