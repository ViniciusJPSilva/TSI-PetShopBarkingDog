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
			<h2>Agendar Serviço</h2>
			<c:if test="${not empty message}">
				<h4 class="info-message">${message}</h4>
			</c:if>
			
			<form:form id="schedule-service-form" action="shedule-service-create" method="post" class="login-form" modelAttribute="service">				
				<input list="dog-list" id="dog" type="text" name="selectedDogName" placeholder="Cachorro" required>
				<datalist id="dog-list">
				    <c:forEach var="dogValue" items="${dogs}">
				        <option value="${dogValue.name}">
				            <c:out value="${dogValue.name}" />
				        </option>
				    </c:forEach>
				</datalist>
				<form:errors path="dog" id="dog" cssClass="form-error"></form:errors>

				<div class="multiple-choices">
				    <h4>Selecione o(s) Serviço(s)</h4>
				
				    <div>
				        <c:forEach var="serviceType" items="${serviceTypes}">
				            <input type="checkbox" name="services" id="service_${serviceType.id}" value="${serviceType.id}" />
				            <label for="service_${serviceType.id}">${serviceType.name}</label>
				        </c:forEach>
				    </div>
				</div>
				
				<input type="text" name="scheduledDate" class="date" placeholder="Data de Agendamento" value="<fmt:formatDate value="${service.scheduledDate.time}" pattern="dd/MM/yyyy"/>" required/>
 				<form:errors path="scheduledDate" id="scheduledDate" cssClass="form-error"></form:errors>
	
				<button form="schedule-service-form">Agendar</button>
			</form:form>
		</div>
	</main>
	
	<script type="text/javascript" src="resources/js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="resources/js/jquery-mask-1.14.16.min.js"></script>
	<script type="text/javascript" src="resources/js/validations.js" ></script>
</body>
</html>