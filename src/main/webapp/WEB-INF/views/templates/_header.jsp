<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="pt-br">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link type="text/css" href="resources/css/style.css" rel="stylesheet">

<script type="text/javascript" src="resources/js/script.js" ></script>

<title>PetShop - Cão Q-Late</title>
</head>

<body>

	<header class="header">
		<a href="employee-menu" class="header__logo">Cão Q-Late</a>
		<c:choose>
			<c:when test="${not empty logged}">
				<h4 class="message">Olá, ${logged.name}!</h4>
				<c:choose>
					<c:when test="${isClient}">
						<ul class="header__nav">
							<li class="header__list"><a class="header__link"
								href="client-menu">Menu</a></li>
							<li class="header__list"><a class="header__link"
								href="client-logout">Sair</a></li>
						</ul>
					</c:when>
					<c:otherwise>
						<ul class="header__nav">
							<li class="header__list"><a class="header__link"
								href="employee-menu">Menu</a></li>
							<li class="header__list"><a class="header__link"
								href="employee-logout">Sair</a></li>
						</ul>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<ul class="header__nav">
					<li class="header__list"><a class="header__link"
						href="client-login-page">Fazer Login</a></li>
					<li class="header__list"><a class="header__link"
						href="client-register">Criar Conta</a></li>
					<li class="header__list"><a class="header__link"
						href="employee-login-page">Admin</a></li>
				</ul>
			</c:otherwise>
		</c:choose>


	</header>

</body>

</html>