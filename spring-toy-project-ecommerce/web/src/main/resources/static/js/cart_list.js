var cartList = function () {
    "use strict";

    var init = function init() {
        bindFunctions();
    };

    var bindFunctions = function bindFunctions() {
        $(".minusBtn").on('click', decreaseQty);
        $(".plusBtn").on('click', increaseQty);
        $(".deleteBtn").on('click', deleteCartProduct);
        $("#checkoutBtn").on('click', checkout);
    };

    var checkout = function () {
        // To Do
        // 카트 저장 후 이동
        window.location.href="/orders/checkout";
    };

    var deleteCartProduct = function () {
        $(this).parent().closest("tr").remove();
        var rowCount = $("tbody > tr").length;
        if (rowCount == 1) {
            $("tbody > tr").remove();
            $("tbody").append("<tr><td style='text-align: center' colspan='6'>장바구니가 비었습니다.</td></tr>");
        }
        calcTotalPrice();
    };

    var increaseQty = function () {
        var skuId = $(this).data("value"),
            qtyInput = $("#quantity" + skuId);

        var qty = parseInt(qtyInput.val());
        if (qty < 10) {
            qtyInput.val(qty + 1);
        } else {
            alert("최대 10개까지만 구매가능합니다.")
        }
        calcSubPrice(skuId);
        calcTotalPrice();
    };

    var decreaseQty = function () {
        var skuId = $(this).data("value"),
            qtyInput = $("#quantity" + skuId);

        var qty = parseInt(qtyInput.val());
        if (qty > 1) {
            qtyInput.val(qty - 1);
        } else {
            alert("1개 이상 구매 가능합니다.");
        }
        calcSubPrice(skuId);
        calcTotalPrice();
    };

    var calcSubPrice = function (skuId) {
        var price = $("#myCartProduct" + skuId).data("price"),
            qty = $("#quantity" + skuId).val();

        var subTotalPrice = price * qty;
        $("#subTotalPrice" + skuId).text(subTotalPrice);
    };

    var calcTotalPrice = function () {
        var totalPrice = 0,
            subTotalPrices = $(".subTotalPrice");

        $.each(subTotalPrices, function(key) {
            totalPrice += parseInt(subTotalPrices[key].textContent);
        });

        $("#totalPrice").text(totalPrice);
    };

    return {
        init: init
    };

}();

cartList.init();