<%@tag pageEncoding="UTF-8"%>
<%@attribute name="title" type="java.lang.String" required="true" rtexprvalue="true"%>
<%@attribute name="css" type="java.util.List" required="false" rtexprvalue="true"%>
<%@attribute name="js" type="java.util.List" required="false" rtexprvalue="true"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<%@taglib prefix="u" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="ru">
<head>
	<meta charset="UTF-8">
	<title>Гринготтс :: ${title}</title>
	<c:url var="url__style_css" value="${'/style.css'}"/>
	<link rel="stylesheet" href="${url__style_css}">
	<c:if test="${not empty css}">
		<c:forEach var="css_item" items="${css}">
			<c:url var="url_css" value="${css_item}"/>
			<link rel="stylesheet" href="${url_css}">
		</c:forEach>
	</c:if>
	<c:if test="${not empty js}">
		<c:forEach var="js_item" items="${js}">
			<c:url var="url_js" value="${js_item}"/>
			<script type="text/javascript" src="${url_js}"></script>
		</c:forEach>
	</c:if>
</head>
<body>
<u:header/>
<div class="content">
	<jsp:doBody/>
</div>
</body>
</html>