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
			<h2>Serviços Cadastrados</h2>
			
			<c:if test="${not empty message}">
				<h4 class="info-message">${message}</h4>
			</c:if>
			
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>Nome</th>
						<th>Custo</th>
						<th>Salvar</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="service" items="${services}" varStatus="id">
						<tr id="service_${service.id}" bgcolor="${id.count % 2 != 0  ? 'ffffff' : 'cccccc'}">
							<td>${service.id}</td>					
							<td>${service.name}</td>					
							<td>
								<input type="number" name="cost" id="cost_${service.id}" placeholder="Custo (R$)" value="${service.cost}" step="0.01" max="10000.00" required/>
							</td>	
							<td class="btn-td">
								<button class="button" onclick="updateServiceType(${service.id}, '${service.name}')"> 
									<span class="material-symbols-outlined">edit_document</span>
								</button>
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