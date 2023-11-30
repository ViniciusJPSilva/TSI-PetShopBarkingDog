/*
	JQuery
*/

$(document).ready(function() {

	function restrictToDatalistValues(inputField, datalistID) {
		$(inputField).on('input', function() {
			const enteredValue = $(this).val();
			const options = $(`${datalistID} option`).map(function() {
				return $(this).val();
			}).get();

			if (!options.includes(enteredValue)) {
				$(this).val('');
			}
		});
	};

	function validateDate(dateString) {
		var dateRegex = /^\d{2}\/\d{2}\/\d{4}$/;

		if (!dateRegex.test(dateString)) {
			return false;
		}

		var dateParts = dateString.split('/');
		var day = parseInt(dateParts[0], 10);
		var month = parseInt(dateParts[1], 10) - 1; // Month is zero-based (January = 0)
		var year = parseInt(dateParts[2], 10);

		var dateObj = new Date(year, month, day);

		return (
			dateObj.getFullYear() === year &&
			dateObj.getMonth() === month &&
			dateObj.getDate() === day
		);
	}


	$('#cpf').mask('000.000.000-00', {
		reverse: true
	});

	$('#phone').mask('(99) 9 9999-9999');

	var inputDate = $('.date');
	var startDate = $('.date-start');

	inputDate.mask('00/00/0000');
	startDate.mask('00/00/0000');

	inputDate.on('blur', function() {
		var userInput = $(this).val();
		if (!validateDate(userInput)) {
			inputDate.val('');
			inputDate.addClass('date-error');
			inputDate.removeClass('date-success');
		} else {
			inputDate.addClass('date-success');
			inputDate.removeClass('date-error');
		};
	});
	
	startDate.on('blur', function() {
		var userInput = $(this).val();
		if (!validateDate(userInput)) {
			startDate.val('');
			startDate.addClass('date-error');
			startDate.removeClass('date-success');
		} else {
			startDate.addClass('date-success');
			startDate.removeClass('date-error');
		};
	});

	restrictToDatalistValues('#size', '#size-list');

	restrictToDatalistValues('#dog', '#dog-list');

	var passwordInput = $('.password');
	passwordInput.on('mouseenter', function() {
		passwordInput.attr('type', 'text');
	});
	passwordInput.on('mouseleave', function() {
		passwordInput.attr('type', 'password');
	});

});

function updateServiceType(id, name) {
	// Obtém o novo custo do input
	var cost = $("#cost_" + id).val();

	// Envia a solicitação POST com o ID do serviço e o novo custo
	$.post("service-type-change-cost", { "id": id, "cost": cost, "name": name }, function(response) {
		$("#service_" + id).html(response);
	});
}


function cancelScheduledService(id) {
	$.post("scheduled-service-cancel", { "id": id }, function() {
		$("#service_" + id).closest("tr").hide();
	})
}



