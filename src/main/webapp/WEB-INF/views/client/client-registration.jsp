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
			<h2>Criar Conta</h2>
			<c:if test="${not empty message}">
				<h4 class="info-message">${message}</h4>
			</c:if>
			
			<form:form id="client-create-form" action="client-create" method="post" class="login-form" modelAttribute="client">
				<input type="text" name="name" placeholder="Nome" value="${client.name}" required/>
				<form:errors path="name" id="name" cssClass="form-error"></form:errors>
				
				<input type="text" name="cpf" id="cpf" placeholder="CPF" value="${client.cpf}" required/>
				
				<input type="text" name="phone" id="phone" placeholder="Telefone" value="${client.phone}" required/>
				
				<input type="text" name="birthDate" class="date" placeholder="Data de nascimento" value="<fmt:formatDate value="${client.birthDate.time}" pattern="dd/MM/yyyy"/>" required/>
 				<form:errors path="birthDate" id="birthDate" cssClass="form-error"></form:errors>
 				
				<input type="email" name="email" placeholder="E-mail" pattern="[a-z0-9._%+\-]+@[a-z0-9.\-]+\.[a-z]{2,}$" value="${client.email}" required />
				 
				<input type="password" class="password" name="password" placeholder="Nova senha" autocomplete="on" required /> 
				<form:errors path="password" id="password" cssClass="form-error"></form:errors>
	
				<button form="client-create-form">Registrar</button>
			</form:form>
		</div>
	</main>
	
	<script type="text/javascript" src="resources/js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="resources/js/jquery-mask-1.14.16.min.js"></script>
	<script type="text/javascript" src="resources/js/validations.js" ></script>
</body>
</html>