<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<body>
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
</body>
</html>