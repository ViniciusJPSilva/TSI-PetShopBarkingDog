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
			
			<h4 class="info-message">Para cancelamentos com menos de 24 horas, contate um de nossos funcionários.</h4>
			
			<c:if test="${not empty message}">
				<h4 class="info-message">${message}</h4>
			</c:if>
			
			<c:set var="today" value="<%= Utility.getTodayCalendar().getTime().getTime() %>"></c:set>
			<c:set var="afterTomorrow" value="${today + 86400000 * 2}"></c:set>
			
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>Data do Agendamento</th>
						<th>Cachorro</th>
						<th>Serviços</th>
						<th>Cancelar</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="service" items="${services}" varStatus="id">
						<tr id="service_${service.id}" bgcolor="${id.count % 2 != 0  ? 'ffffff' : 'e3e3e3'}">
							<td>${service.id}</td>
							<td>
								<fmt:formatDate value="${service.scheduledDate.time}" pattern="dd/MM/yyyy"/>
							</td>
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
							<td class="btn-td">
								<c:if test="${service.scheduledDate.time.time ge afterTomorrow}">
									<button class="button" onclick="cancelScheduledService(${service.id})">
										<span class="material-symbols-outlined">delete_forever</span>
									</button>
								</c:if>
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