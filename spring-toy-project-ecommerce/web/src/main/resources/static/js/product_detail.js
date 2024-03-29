let productDetail = function () {
    "use strict";

    const init = function init() {
        bindFunctions();
    };

    const bindFunctions = function bindFunctions() {
        $("#sizeOption").on("change", selectOption);
        $("#cartBtn").on("click", addCartProduct);
    };

    const addCartProduct = function () {
        let skuIds = $("#sizeOption>option").map(function(){
            return $(this).val();
        }).get();
        skuIds.shift();

        let cartProducts = [];
        skuIds.forEach(function(productSkuId) {
            if ($("#quantity" + productSkuId).length) {
                let cartProduct = {};
                cartProduct["productSkuId"] = productSkuId;
                cartProduct['quantity'] = $("#quantity" + productSkuId).val();
                cartProducts.push(cartProduct);
            }
        });

        CommonUtil.ajax({
            url: "/carts",
            type: "POST",
            progressAlert: false,
            contentType: 'application/json',
            data: JSON.stringify(cartProducts),
            success: function success(res) {
                let $cartProductNum = $("#cartProductNum");
                $cartProductNum.text(parseInt($cartProductNum.text()) + res);
                if (window.confirm("장바구니로 이동하시겠습니까?")) {
                    window.location.href = "/carts";
                }
            }, error: function error(res) {
                alert(res["responseJSON"]["message"]);
            }
        });
    };

    const selectOption = function () {
        let option = $('option:selected',this),
            myItemList = $("#myItemList");

        let optionName = option.text(),
            skuId = option.val(),
            price = myItemList.data("price");

        if ($(".myItem").length == 0) {
            myItemList.after(markupTotalPrice());
        }

        if (!$("#myItem" + skuId).length) {
            myItemList.append(markup(optionName, skuId, price));
        } else {
            alert("이미 선택된 옵션입니다.");
        }

        calcTotalPrice();
        $(this).val("-1");
    };

    const markup = function (optionName, skuId, price) {
        let html = "<div class='row myItem' id='myItem" + skuId +"'>";
        html += "<div class='col-lg-5'>" + optionName + "</div>";
        html += "<div class='form-group col-lg-4'>";
        html += "<div class='input-group mb-3'>";
        html += "<div class='input-group-prepend'>";
        html += "<button class='quantity-left-minus btn btn-danger btn-number plusBtn' data-value='" + skuId + "' type='button' onclick='productDetail.decreaseQty(this)'>";
        html += "<i class='fa fa-minus'></i></button></div>";
        html += "<input type='text' class='form-control' name='quantity' id='quantity" + skuId + "' value='1' aria-label='' aria-describedby='basic-addon1'>";
        html += "<div class='input-group-append'>";
        html += "<button class='quantity-right-plus btn btn-success btn-number minusBtn' data-value='" + skuId + "' type='button' onclick='productDetail.increaseQty(this)'>";
        html += "<i class='fa fa-plus'></i>";
        html += "</button></div></div></div>";
        html += "<div class='col-lg-2'><span class='subPrice' id='subPrice" + skuId + "'>" + price+ "</span>won</div>";
        html += "<div class='col-lg-1'>";
        html += "<button type='button' class='close' aria-label='Close' data-value='" + skuId + "' onclick='productDetail.closeItem(this)'>";
        html += "<span aria-hidden='true'>&times;</span>";
        html += "</button></div></div>";
        return html;
    };

    const markupTotalPrice = function () {
        let html = "<div class='pull-right'>Total Price: <span id='totalPrice'>0</span>won</div>";
        return html;
    };

    const closeItem = function($t) {
        let skuId = $($t).data("value");
        $("#myItem" + skuId).remove();

        if ($(".myItem").length == 0) {
            $("#totalPrice").parent().closest('div').remove();
        } else {
            calcTotalPrice();
        }

    };

    const increaseQty = function ($t) {
        let skuId = $($t).data("value"),
            qtyInput = $("#quantity" + skuId);

        let qty = parseInt(qtyInput.val());
        if (qty < 10) {
            qtyInput.val(qty + 1);
        } else {
            alert("최대 10개까지만 구매가능합니다.")
        }
        calcPrice(skuId);
        calcTotalPrice();
    };

    const decreaseQty = function ($t) {
        let skuId = $($t).data("value"),
            qtyInput = $("#quantity" + skuId);

        let qty = parseInt(qtyInput.val());
        if (qty > 1) {
            qtyInput.val(qty - 1);
        } else {
            alert("1개 이상 구매 가능합니다.");
        }
        calcPrice(skuId);
        calcTotalPrice();
    };

    const calcPrice = function (skuId) {
        let price = $("#myItemList").data("price"),
            qty = $("#quantity" + skuId).val();

        let subTotalPrice = price * qty;
        $("#subPrice" + skuId).text(subTotalPrice);
    };

    const calcTotalPrice = function () {
        let totalPrice = 0,
            subPrices = $(".subPrice");

        $.each(subPrices, function(key) {
            totalPrice += parseInt(subPrices[key].textContent);
        });

        $("#totalPrice").text(totalPrice);
    };

    return {
        init: init,
        increaseQty: increaseQty,
        decreaseQty: decreaseQty,
        closeItem: closeItem
    };

}();

productDetail.init();