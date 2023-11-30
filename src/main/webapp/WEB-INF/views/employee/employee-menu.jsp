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
	<main>
		<section class="hero-section">
			<div class="card-grid">
				<button class="card" onclick="location.href='service-type-register'">
					<span class="card__background"
						style="background-image: url(resources/images/adm_new_service.jpg)"></span>
					<div class="card__content">
						<p class="card__category">Serviços</p>
						<h3 class="card__heading">Cadastrar Serviços</h3>
					</div>
				</button>
				<button class="card"
					onclick="location.href='service-type-table'">
					<span class="card__background"
						style="background-image: url(resources/images/adm_change_service.jpg)"></span>
					<div class="card__content">
						<p class="card__category">Serviços</p>
						<h3 class="card__heading">Alterar Serviços</h3>
					</div>
				</button>
				<button class="card"
					onclick="location.href='scheduled-services-data'">
					<span class="card__background"
						style="background-image: url(resources/images/adm_scheduled.jpg)"></span>
					<div class="card__content">
						<p class="card__category">Serviços</p>
						<h3 class="card__heading">Serviços Agendados</h3>
					</div>
				</button>
				<button class="card"
					onclick="location.href='finished-services-between'">
					<span class="card__background"
						style="background-image: url(resources/images/adm_employee.jpg)"></span>
					<div class="card__content">
						<p class="card__category">Relatórios</p>
						<h3 class="card__heading">Serviços Prestados</h3>
					</div>
				</button>
			</div>
		</section>
	</main>
</body>
</html>