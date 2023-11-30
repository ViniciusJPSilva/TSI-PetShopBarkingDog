<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
			<h2>Login Administrativo</h2>
			<c:if test="${not empty message}">
				<h4 class="info-message">${message}</h4>
			</c:if>
			<form action="employee-login" method="post" class="login-form" id="employee-login-form">
				<input type="text" name="login" placeholder="Login" required /> 
				<input type="password" class="password" name="password" placeholder="Senha" pattern=".{5,}"
					title="São necessários 5 ou mais caracteres." autocomplete="on" required />

				<button form="employee-login-form">Entrar</button>
			</form>
		</div>
	</main>
	
	<script type="text/javascript" src="resources/js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="resources/js/jquery-mask-1.14.16.min.js"></script>
	<script type="text/javascript" src="resources/js/validations.js" ></script>
</body>
</html>