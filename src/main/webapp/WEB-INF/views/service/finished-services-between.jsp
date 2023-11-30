<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<jsp:include page="../templates/_header.jsp" />

	<main class="login-page">
		<div class="form">
			<h2>Listar Serviços Finalizados e Cancelados</h2>

			<c:if test="${not empty message}">
				<h4 class="info-message">${message}</h4>
			</c:if>
			
			
			<form:form id="between-date-form" action="finished-services-list" method="post" class="login-form">				
				
				<input type="text" name="startDate" class="date-start" placeholder="Data Inicial" required/>
				<input type="text" name="endDate" class="date" placeholder="Data Final" required/>
	
				<button form="between-date-form">Listar</button>
			</form:form>
		</div>
	</main>
	
	<script type="text/javascript" src="resources/js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="resources/js/jquery-mask-1.14.16.min.js"></script>
	<script type="text/javascript" src="resources/js/validations.js" ></script>
</body>
</html>