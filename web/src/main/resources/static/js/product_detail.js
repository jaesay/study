var productDetail = function () {
    "use strict";

    var init = function init() {
        bindFunctions();
    };

    var bindFunctions = function bindFunctions() {
        $("#sizeOption").on("change", selectOption);
    };

    var selectOption = function () {
        var option = $('option:selected',this),
            myItemList = $("#myItemList");

        var optionName = option.text(),
            optionValue = option.val(),
            price = myItemList.data("price");

        if ($(".myItem").length == 0) {
            myItemList.after(markupTotalPrice());
        }

        if (!$("#myItem" + optionValue).length) {
            myItemList.append(markup(optionName, optionValue, price));
        } else {
            alert("이미 선택된 옵션입니다.");
        }

        calcTotalPrice();
        $(this).val("-1");
    };

    var markup = function (optionName, optionValue, price) {
        var html = "<div class='row myItem' id='myItem" + optionValue +"'>";
        html += "<div class='col-lg-5'>" + optionName + "</div>";
        html += "<div class='form-group col-lg-4'>";
        html += "<div class='input-group mb-3'>";
        html += "<div class='input-group-prepend'>";
        html += "<button class='quantity-left-minus btn btn-danger btn-number plusBtn' data-value='" + optionValue + "' type='button' onclick='productDetail.decreaseQty(this)'>";
        html += "<i class='fa fa-minus'></i></button></div>";
        html += "<input type='text' class='form-control' name='quantity' id='quantity" + optionValue + "' value='1' aria-label='' aria-describedby='basic-addon1'>";
        html += "<div class='input-group-append'>";
        html += "<button class='quantity-right-plus btn btn-success btn-number minusBtn' data-value='" + optionValue + "' type='button' onclick='productDetail.increaseQty(this)'>";
        html += "<i class='fa fa-plus'></i>";
        html += "</button></div></div></div>";
        html += "<div class='col-lg-2'><span class='subPrice' id='subPrice" + optionValue + "'>" + price+ "</span>$</div>";
        html += "<div class='col-lg-1'>";
        html += "<button type='button' class='close' aria-label='Close' data-value='" + optionValue + "' onclick='productDetail.closeItem(this)'>";
        html += "<span aria-hidden='true'>&times;</span>";
        html += "</button></div></div>";
        return html;
    };

    var markupTotalPrice = function () {
        var html = "<div class='pull-right'>Total Price: <span id='totalPrice'>0</span>$</div>";
        return html;
    };

    var closeItem = function($t) {
        var optionValue = $($t).data("value");
        $("#myItem" + optionValue).remove();

        if ($(".myItem").length == 0) {
            $("#totalPrice").parent().closest('div').remove();
        } else {
            calcTotalPrice();
        }

    };

    var increaseQty = function ($t) {
        var optionValue = $($t).data("value"),
            qtyInput = $("#quantity" + optionValue);

        var qty = parseInt(qtyInput.val());
        if (qty < 10) {
            qtyInput.val(qty + 1);
        } else {
            alert("최대 10개까지만 구매가능합니다.")
        }
        calcPrice(optionValue);
        calcTotalPrice();
    };

    var decreaseQty = function ($t) {
        var optionValue = $($t).data("value"),
            qtyInput = $("#quantity" + optionValue);

        var qty = parseInt(qtyInput.val());
        if (qty > 1) {
            qtyInput.val(qty - 1);
        } else {
            alert("1개 이상 구매 가능합니다.");
        }
        calcPrice(optionValue);
        calcTotalPrice();
    };

    var calcPrice = function (optionValue) {
        var price = $("#myItemList").data("price"),
            qty = $("#quantity" + optionValue).val();

        var subTotalPrice = price * qty;
        $("#subPrice" + optionValue).text(subTotalPrice.toFixed(2));
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