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
				<button class="card" onclick="location.href='dog-register'">
					<span class="card__background"
						style="background-image: url(resources/images/new_dog.jpg)"></span>
					<div class="card__content">
						<p class="card__category">Dados</p>
						<h3 class="card__heading">Cadastrar Cachorro</h3>
					</div>
				</button>
				<button class="card"
					onclick="location.href='client-change-data-page'">
					<span class="card__background"
						style="background-image: url(resources/images/change_data.jpg)"></span>
					<div class="card__content">
						<p class="card__category">Dados</p>
						<h3 class="card__heading">Alterar Dados do Perfil</h3>
					</div>
				</button>
				<button class="card" onclick="location.href='schedule-service-page'">
					<span class="card__background"
						style="background-image: url(resources/images/schedule.jpg)"></span>
					<div class="card__content">
						<p class="card__category">Serviços</p>
						<h3 class="card__heading">Agendar Serviço</h3>
					</div>
				</button>
				<button class="card"
					onclick="location.href='sheduled-services-open'">
					<span class="card__background"
						style="background-image: url(resources/images/scheduled.jpg)"></span>
					<div class="card__content">
						<p class="card__category">Serviços</p>
						<h3 class="card__heading">Serviços Agendados</h3>
					</div>
				</button>
				<button class="card"
					onclick="location.href='scheduled-services-finished'">
					<span class="card__background"
						style="background-image: url(resources/images/finalized.jpg)"></span>
					<div class="card__content">
						<p class="card__category">Serviços</p>
						<h3 class="card__heading">Serviços Finalizados</h3>
					</div>
				</button>
			</div>
		</section>
	</main>
</body>
</html>