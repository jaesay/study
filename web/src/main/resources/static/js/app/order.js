var order = function () {
    "use strict";

    var orderForm = $('form[name="orderForm"]');

    var init = function () {
        bindFunctions();
    };

    var bindFunctions = function () {
        $('#checkoutBtn').on('click', showAddressModal);
        $('#orderBtn').on('click', submitOrderForm);
    };

    var showAddressModal = function () {
        if ($('#cart-item-list li').length > 0) {
            $('#addressModal').modal('show');
        } else {
            alert('Your shopping cart is empty.');
        }
    };

    var submitOrderForm = function () {
        var data = {
            city: $("#city").val(),
            street: $("#street").val(),
            zipcode: $("#zipcode").val()
        };

        $.ajax({
            type: 'POST',
            url: '/order',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(response) {
            alert('Your order has been placed.');
            location.href = "/my/orders";
        }).fail(function (error) {
            alert(error.responseJSON.message);
        });
    };

    return {
        init: init
    };

}();

order.init();