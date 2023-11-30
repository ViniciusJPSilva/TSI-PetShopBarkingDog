<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page import="br.vjps.tsi.psbd.enumeration.DogSize"%>
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
			<h2>Cadastrar um Serviço</h2>
			<c:if test="${not empty message}">
				<h4 class="info-message">${message}</h4>
			</c:if>
			
			<form:form id="service-type-registration-form" action="service-type-create" method="post" class="login-form" modelAttribute="serviceType">
				<input type="text" name="name" placeholder="Nome" value="${serviceType.name}" required/>
				<form:errors path="name" id="name" cssClass="form-error"></form:errors>
				
				<input type="number" name="cost" placeholder="Custo (R$)" value="${serviceType.cost}" step="0.01" max="10000.00" required/>
				<form:errors path="cost" id="cost" cssClass="form-error"></form:errors>
	
				<button form="service-type-registration-form">Cadastrar</button>
			</form:form>
		</div>
	</main>
	
	<script type="text/javascript" src="resources/js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="resources/js/jquery-mask-1.14.16.min.js"></script>
	<script type="text/javascript" src="resources/js/validations.js" ></script>
</body>
</html>