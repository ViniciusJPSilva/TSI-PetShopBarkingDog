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
			<h2>Cadastrar um Doguinho</h2>
			<c:if test="${not empty message}">
				<h4 class="info-message">${message}</h4>
			</c:if>
			
			<form:form id="dog-create-form" action="dog-create" method="post" class="login-form" modelAttribute="dog">
				<input type="text" name="name" placeholder="Nome do Cachorro" value="${dog.name}" required/>
				<form:errors path="name" id="name" cssClass="form-error"></form:errors>
				
				<input type="text" name="breed" placeholder="Raça" value="${dog.breed}" required/>
				<form:errors path="breed" id="breed" cssClass="form-error"></form:errors>
				
				<input list="size-list" id="size" type="text" name="size" placeholder="Tamanho" required>
				<datalist id="size-list">
				    <c:forEach var="sizeCode" items="${DogSize.values()}">
				        <option value="${sizeCode.description}" data-acronym="${sizeCode.acronym}">
				            <c:out value="${sizeCode.description}" />
				        </option>
				    </c:forEach>
				</datalist>
	
				<button form="dog-create-form">Cadastrar</button>
			</form:form>
		</div>
	</main>
	
	<script type="text/javascript" src="resources/js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="resources/js/jquery-mask-1.14.16.min.js"></script>
	<script type="text/javascript" src="resources/js/validations.js" ></script>
</body>
</html>