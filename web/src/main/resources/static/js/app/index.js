var index = function () {
	"use strict";

	var init = function () {
		bindFunctions();
	};

	var bindFunctions = function () {
		$(".add-cart-btn").on('click', addCart);
		$(".remove-cart-btn").on('click', removeCart);
	};

	var addCart = function () {
		var card = $(this).closest('.card');
		var data = {
			itemId: card.data('item-id'),
			itemCnt: card.find('.item-cnt').val()
		};

		$.ajax({
			type: 'POST',
			url: '/api/carts',
			dataType: 'json',
			contentType:'application/json; charset=utf-8',
			data: JSON.stringify(data)
		}).done(function(response) {
			alert(response.itemName + ' has been put into your shopping cart');

			//update the shopping-cart
			if ($('#cart-item-list li').length === 0) {
				toggleCart();
			}
			toggleItem(card);
			$('#cart-item-list').append($('<li>', {'id': 'cart-item-' + response.itemId}).text(response.itemName + ' - ' + response.itemCnt));
			$('#cart-price').text(parseInt($('#cart-price').text()) + (response.itemPrice * response.itemCnt));

		}).fail(function (error) {
			alert(error.responseJSON.message);
		});
	};

	var removeCart = function () {
		var card = $(this).closest('.card');
		var item_id = card.data('item-id');

		$.ajax({
			type: 'DELETE',
			url: '/api/carts/' + item_id,
			dataType: 'json'
		}).done(function(response) {
			alert(response.itemName + ' has been deleted from your shopping cart');

			$('#cart-item-' + response.itemId).remove();
			$('#cart-price').text(parseInt($('#cart-price').text()) - (response.totalPrice));
			if ($('#cart-item-list li').length === 0) {
				toggleCart();
			}
			toggleItem(card);
			card.find('.item-cnt').val(0);

		}).fail(function (error) {
			alert(error.responseJSON.message);
		});

	};

	var toggleItem = function (card) {
		card.find('.add-cart').toggle();
		card.find('.remove-cart').toggle();
	};

	var toggleCart = function () {
		$('#cart-item-list').toggle();
		$('#cart-empty-text').toggle();
	};

	return {
		init: init
	};

}();

index.init();