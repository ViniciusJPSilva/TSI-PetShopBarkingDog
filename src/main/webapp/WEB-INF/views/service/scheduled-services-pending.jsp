<%@page import="br.vjps.tsi.psbd.utility.Utility"%>
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

<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
</head>
<body>
	<jsp:include page="../templates/_header.jsp" />

	<main class="login-page">
		<div class="table">
			<h2>Agendamentos em Aberto</h2>
			
			<c:if test="${not empty message}">
				<h4 class="info-message">${message}</h4>
			</c:if>
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>Data do Agendamento</th>
						<th>Cliente</th>
						<th>CPF</th>
						<th>Cachorro</th>
						<th>Serviços</th>
						<th>Total (R$)</th>
						<th>Lançar</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="service" items="${services}" varStatus="id">
						<tr id="service_${service.id}" bgcolor="${id.count % 2 != 0  ? 'ffffff' : 'e3e3e3'}">
							<td>${service.id}</td>
							<td>
								<fmt:formatDate value="${service.scheduledDate.time}" pattern="dd/MM/yyyy"/>
							</td>
							<td>${service.dog.client.name}</td>
							<td>${service.dog.client.cpf}</td>
							<td>${service.dog.name}</td>
							<td>
								<fmt:setLocale value="pt_BR"/>
								<select >
									<option value="" disabled selected>Ver serviços</option>
									<c:forEach var="item" items="${service.serviceTypes}">
										<option value="${item.name}" disabled>${item.name} - <fmt:formatNumber value="${item.cost}" type="currency"/></option>
									</c:forEach>
								</select>
							</td>
							<td>
								<fmt:formatNumber value="${service.total}" type="currency"/>
							</td>
							<td class="btn-td">
								<form id ="form_${service.id}" action="scheduled-service-finish" method="post" >
									<input hidden="hidden" type="text" name="id" value="${service.id}">
									<button class="button" form="form_${service.id}">
										<span class="material-symbols-outlined">done</span>
									</button>
								</form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</main>
	
	<script type="text/javascript" src="resources/js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="resources/js/jquery-mask-1.14.16.min.js"></script>
	<script type="text/javascript" src="resources/js/validations.js" ></script>
</body>
</html>