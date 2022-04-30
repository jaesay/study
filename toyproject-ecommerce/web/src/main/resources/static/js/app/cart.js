var cart = function () {
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

		CommonUtil.ajax({
			type: 'POST',
			url: '/api/carts',
			dataType: 'json',
			contentType:'application/json; charset=utf-8',
			data: JSON.stringify(data),
			success: function (response) {
				var data = response.data;
				alert(data.itemName + ' has been put into your shopping cart');

				//update the shopping-cart
				if ($('#cart-item-list li').length === 0) {
					toggleCart();
				}
				toggleItem(card);
				$('#cart-item-list').append($('<li>', {'id': 'cart-item-' + data.itemId}).text(data.itemName + ' - ' + data.itemCnt));
				$('#cart-price').text(parseInt($('#cart-price').text()) + (data.itemPrice * data.itemCnt));
			}, error: function (response) {
				alert(response.responseJSON.message);
			}
		});
	};

	var removeCart = function () {
		var card = $(this).closest('.card');
		var item_id = card.data('item-id');

		CommonUtil.ajax({
			type: 'DELETE',
			url: '/api/carts/' + item_id,
			dataType: 'json',
			success: function (response) {
				let data = response.data;
				alert(data.itemName + ' has been deleted from your shopping cart');

				$('#cart-item-' + data.itemId).remove();
				$('#cart-price').text(parseInt($('#cart-price').text()) - data.totalPrice);
				if ($('#cart-item-list li').length === 0) {
					toggleCart();
				}
				toggleItem(card);
				card.find('.item-cnt').val(0);
			}, error: function (response) {
				alert(response.responseJSON.message);
			}
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

cart.init();