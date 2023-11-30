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
			<h2>Serviços Finalizados e Cancelados</h2>
			
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>Status</th>
						<th>Data do Agendamento</th>
						<th>Data de Execução/Cancelamento</th>
						<th>Cliente</th>
						<th>Cachorro</th>
						<th>Serviços</th>
						<th>Total (R$)</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="sum" value="0.0"/>
					<c:forEach var="service" items="${services}" varStatus="id">
						<tr id="service_${service.id}" bgcolor="${id.count % 2 != 0  ? 'ffffff' : 'e3e3e3'}">
							<td>${service.id}</td>
							<td>${service.status.description}</td>
							<td>
								<fmt:formatDate value="${service.scheduledDate.time}" pattern="dd/MM/yyyy"/>
							</td>
							<td>
								<fmt:formatDate value="${service.releaseDate.time}" pattern="dd/MM/yyyy"/>
							</td>
							<td>${service.dog.client.name}</td>
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
						</tr>
						<c:set var="sum" value="${sum + service.total}" />
					</c:forEach>
				</tbody>
			</table>
			
			<h4 class="info-message">Total Faturado: <fmt:formatNumber value="${sum}" type="currency"/></h4>
		</div>
	</main>
	
	<script type="text/javascript" src="resources/js/jquery-3.6.0.min.js"></script>
	<script type="text/javascript" src="resources/js/jquery-mask-1.14.16.min.js"></script>
	<script type="text/javascript" src="resources/js/validations.js" ></script>
</body>
</html>