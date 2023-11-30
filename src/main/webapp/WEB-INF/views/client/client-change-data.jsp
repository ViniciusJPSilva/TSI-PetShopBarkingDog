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
			<h2>Atualizar dados da Conta</h2>
			<c:if test="${not empty message}">
				<h4 class="info-message">${message}</h4>
			</c:if>
			
			<form:form id="client-update-form" action="client-update" method="post" class="login-form" modelAttribute="client">
				<input type="text" name="name" placeholder="Nome" value="${client.name}" class="readonly-input" readonly/>
				
				<input type="text" name="cpf" id="cpf" placeholder="CPF" value="${client.cpf}" class="readonly-input" readonly/>
				
				<input type="text" name="phone" id="phone" placeholder="Telefone" value="${client.phone}"/>
				
				<input type="text" name="birthDate" class="date readonly-input" placeholder="Data de nascimento" value="<fmt:formatDate value="${client.birthDate.time}" pattern="dd/MM/yyyy" />" readonly/>
 				
				<input type="email" name="email" placeholder="E-mail" pattern="[a-z0-9._%+\-]+@[a-z0-9.\-]+\.[a-z]{2,}$" value="${client.email}" class="readonly-input" readonly />
				 
				<input type="password" class="password" name="oldPassword" placeholder="Senha atual" autocomplete="on" required />
				
				<input type="password" class="password" name="password" placeholder="Nova senha" autocomplete="on" required /> 
				<form:errors path="password" id="password" cssClass="form-error"></form:errors>
	
				<input type="number" name="id" placeholder="ID" value="${client.id}" hidden="true"/>
				<button form="client-update-form">Atualizar</button>
			</form:form>
		</div>
	</main>
	
	<script type="text/javascript" src="resources/js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="resources/js/jquery-mask-1.14.16.min.js"></script>
	<script type="text/javascript" src="resources/js/validations.js" ></script>
</body>
</html>