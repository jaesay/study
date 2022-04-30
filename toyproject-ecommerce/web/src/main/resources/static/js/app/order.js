var order = function () {
    "use strict";

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

        CommonUtil.ajax({
            type: 'POST',
            url: '/order',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data),
            success: function (response) {
                alert('Your order has been placed.');
                location.href = "/my/orders";
            }, error: function (response) {
                alert(response.responseJSON.message);
            }
        });
    };

    return {
        init: init
    };

}();

order.init();