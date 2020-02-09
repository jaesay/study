var index = function () {
	"use strict";

	var init = function () {
		bindFunctions();
		initCart();
	};

	var initCart = function () {

	};

	var bindFunctions = function () {
		$(".add-cart-btn").on('click', addCart);
		$(".remove-cart-btn").on('click', removeCart);
	};

	var addCart = function () {
		var card = $(this).closest('.card');
		var item_cnt = card.find('.item-cnt').val();
		var item_name = card.find('.item-name').text();
		var item_price = card.find('.item-price').text();
		var item_id = card.data('item-id');

		$.ajax({
			type: 'POST',
			url: '/api/carts',
			dataType: 'json',
			contentType:'application/json; charset=utf-8',
			data: JSON.stringify({
				itemId: item_id,
				itemCnt: item_cnt
			})
		}).done(function() {
			alert(item_name + ' has been put in your shopping cart');

			card.find('.row').empty();
			var removeBtn = $('<div>', {'class': 'col-12'})
				.append($('<button>', {'class': 'btn btn-primary btn-block remove-cart-btn', 'click': removeCart})
					.text('Remove From Cart'));

			card.find('.row').html(removeBtn);

			//update the shopping-cart
			if ($('#cart-item-list li').length === 0) {
				$('#cart-item-list').show();
				$('#cart-empty-text').hide();
			}
			$('#cart-item-list').append($('<li>', {'id': 'cart-item-' + item_id}).text(item_name + ' - ' + item_cnt));
			$('#cart-price').text(parseInt($('#cart-price').text()) + (item_price * item_cnt));

		}).fail(function (error) {
			alert(error.responseJSON.message);
		});
	};

	var removeCart = function () {
		var card = $(this).closest('.card');
		var item_cnt = card.find('.item-cnt').val();
		var item_price = card.find('.item-price').text();
		var item_id = card.data('item-id');

		$('#cart-item-' + item_id).remove();
		$('#cart-price').text(parseInt($('#cart-price').text()) - (item_price * item_cnt));
		if ($('#cart-item-list li').length === 0) {
			$('#cart-item-list').hide();
			$('#cart-empty-text').show();
		}
	};

	return {
		init: init
	};

}();

index.init();