var productDetail = function () {
    "use strict";

    var init = function init() {
        bindFunctions();
    };

    var bindFunctions = function bindFunctions() {
        $("#sizeOption").on("change", selectOption);
        $("#cartBtn").on("click", addCartProduct);
    };

    var addCartProduct = function () {
        var skuIds = $("#sizeOption>option").map(function(){
            return $(this).val();
        }).get();
        skuIds.shift();

        var cartProducts = [];
        skuIds.forEach(function(productSkuId) {
            if ($("#quantity" + productSkuId).length) {
                var cartProduct = {};
                cartProduct["productSkuId"] = productSkuId;
                cartProduct['quantity'] = $("#quantity" + productSkuId).val();
                cartProducts.push(cartProduct);
            }
        });

        console.log(JSON.stringify(cartProducts));

        $.ajax({
            url:'/carts',
            contentType: 'application/json',
            dataType: "json",
            type: "post",
            data: JSON.stringify(cartProducts),
            success:function(data){
                if (window.confirm("Do you want to see your cart?")) {
                    window.location.href = "/carts";
                }
            }
        });
    };

    var selectOption = function () {
        var option = $('option:selected',this),
            myItemList = $("#myItemList");

        var optionName = option.text(),
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

    var markup = function (optionName, skuId, price) {
        var html = "<div class='row myItem' id='myItem" + skuId +"'>";
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
        html += "<div class='col-lg-2'><span class='subPrice' id='subPrice" + skuId + "'>" + price+ "</span>$</div>";
        html += "<div class='col-lg-1'>";
        html += "<button type='button' class='close' aria-label='Close' data-value='" + skuId + "' onclick='productDetail.closeItem(this)'>";
        html += "<span aria-hidden='true'>&times;</span>";
        html += "</button></div></div>";
        return html;
    };

    var markupTotalPrice = function () {
        var html = "<div class='pull-right'>Total Price: <span id='totalPrice'>0</span>$</div>";
        return html;
    };

    var closeItem = function($t) {
        var skuId = $($t).data("value");
        $("#myItem" + skuId).remove();

        if ($(".myItem").length == 0) {
            $("#totalPrice").parent().closest('div').remove();
        } else {
            calcTotalPrice();
        }

    };

    var increaseQty = function ($t) {
        var skuId = $($t).data("value"),
            qtyInput = $("#quantity" + skuId);

        var qty = parseInt(qtyInput.val());
        if (qty < 10) {
            qtyInput.val(qty + 1);
        } else {
            alert("최대 10개까지만 구매가능합니다.")
        }
        calcPrice(skuId);
        calcTotalPrice();
    };

    var decreaseQty = function ($t) {
        var skuId = $($t).data("value"),
            qtyInput = $("#quantity" + skuId);

        var qty = parseInt(qtyInput.val());
        if (qty > 1) {
            qtyInput.val(qty - 1);
        } else {
            alert("1개 이상 구매 가능합니다.");
        }
        calcPrice(skuId);
        calcTotalPrice();
    };

    var calcPrice = function (skuId) {
        var price = $("#myItemList").data("price"),
            qty = $("#quantity" + skuId).val();

        var subTotalPrice = price * qty;
        $("#subPrice" + skuId).text(subTotalPrice.toFixed(2));
    };

    var calcTotalPrice = function () {
        var totalPrice = 0,
            subPrices = $(".subPrice");

        $.each(subPrices, function(key) {
            totalPrice += parseFloat(subPrices[key].textContent);
        });

        $("#totalPrice").text(totalPrice.toFixed(2));
    };

    return {
        init: init,
        increaseQty: increaseQty,
        decreaseQty: decreaseQty,
        closeItem: closeItem
    };

}();

productDetail.init();