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
	<c:url var="url__popup_css" value="${'/popup.css'}"/>
	<link rel="stylesheet" href="${url__popup_css}">
	<c:url var="url__tabs_js" value="${'/tabs.js'}"/>
	<script type="text/javascript" src="${url__tabs_js}"></script>
	<c:url var="url__popup_js" value="${'/popup.js'}"/>
	<script type="text/javascript" src="${url__popup_js}"></script>
</head>
<body>
<div class="header primary-background">
	<h1 class="header__title">Банк «Гринготтс»</h1>
</div>
<div class="content">
	<h2 class="page_title primary-color">Активные банковские счета</h2>
	<div class="flex">
		<div>
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
		<div>
			<div class="form-tab-container">
				<div id="tabs">
					<button class="form-tab-link-active">Снятие наличных</button>
					<button class="form-tab-link">Пополнение наличными</button>
					<button class="form-tab-link">Перевод</button>
					<div class="form-tab-rest"></div>
				</div>
				<div class="form-tab">
					<c:url var="url__cashier_cash_withdraw" value="${'/cashier/cash/withdraw.html'}"/>
					<form action="${url__cashier_cash_withdraw}" method="post">
						<div class="form-tab-input-block">
							<label for="withdraw-account-number" class="form-tab-label-up">Номер счёта:</label>
							<input type="text" name="account" id="withdraw-account-number" class="form-tab-input-wide"/>
						</div>
						<label class="form-tab-label-up">Сумма:</label>
						<div class="form-tab-input-block flex">
							<input type="text" name="sum-rubles" id="withdraw-sum-rubles" class="form-tab-input-rubles">
							<label for="withdraw-sum-rubles" class="form-tab-label-rubles">руб.,</label>
							<input type="text" name="sum-kopecks" id="withdraw-sum-kopecks" class="form-tab-input-kopecks">
							<label for="withdraw-sum-kopecks" class="form-tab-label-kopecks">коп.</label>
						</div>
						<div class="buttons_block">
							<button type="submit" class="button button__primary">Снять</button>
						</div>
					</form>
				</div>
				<div class="form-tab">
					<c:url var="url__cashier_cash_deposit" value="${'/cashier/cash/deposit.html'}"/>
					<form action="${url__cashier_cash_deposit}" method="post">
						<div class="form-tab-input-block">
							<label for="deposit-account-number" class="form-tab-label-up">Номер счёта:</label>
							<input type="text" name="account" id="deposit-account-number" class="form-tab-input-wide"/>
						</div>
						<label class="form-tab-label-up">Сумма:</label>
						<div class="form-tab-input-block flex">
							<input type="text" name="sum-rubles" id="deposit-sum-rubles" class="form-tab-input-rubles">
							<label for="deposit-sum-rubles" class="form-tab-label-rubles">руб.,</label>
							<input type="text" name="sum-kopecks" id="deposit-sum-kopecks" class="form-tab-input-kopecks">
							<label for="deposit-sum-kopecks" class="form-tab-label-kopecks">коп.</label>
						</div>
						<div class="buttons_block">
							<button type="submit" class="button button__primary">Пополнить</button>
						</div>
					</form>
				</div>
				<div class="form-tab">
					<c:url var="url__cashier_transfer" value="${'/cashier/transfer.html'}"/>
					<form action="${url__cashier_transfer}" method="post">
						<div class="form-tab-input-block">
							<label for="sender-account-number" class="form-tab-label-up">Номер счёта-отправителя:</label>
							<input type="text" name="sender" id="sender-account-number" class="form-tab-input-wide"/>
						</div>
						<div class="form-tab-input-block">
							<label for="receiver-account-number" class="form-tab-label-up">Номер счёта-получателя:</label>
							<input type="text" name="receiver" id="receiver-account-number" class="form-tab-input-wide"/>
						</div>
						<label class="form-tab-label-up">Сумма:</label>
						<div class="form-tab-input-block flex">
							<input type="text" name="sum-rubles" id="transfer-sum-rubles" class="form-tab-input-rubles">
							<label for="transfer-sum-rubles" class="form-tab-label-rubles">руб.,</label>
							<input type="text" name="sum-kopecks" id="transfer-sum-kopecks" class="form-tab-input-kopecks">
							<label for="transfer-sum-kopecks" class="form-tab-label-kopecks">коп.</label>
						</div>
						<div class="form-tab-input-block">
							<label for="transfer-purpose" class="form-tab-label-up">Назначение платежа:</label>
							<textarea name="purpose" id="transfer-purpose" rows="3" class="form-tab-input-wide"></textarea>
						</div>
						<div class="buttons_block">
							<button type="submit" class="button button__primary">Перевести</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<c:if test="${not empty param['msg']}">
		<script type="text/javascript">showMessage('${param['msg']}')</script>
	</c:if>
</div>
</body>
</html>