var my = function () {
    "use strict";

    var init = function () {
        bindFunctions();
    };

    var bindFunctions = function () {
        $(".cancel-btn").on('click', cancel);
    };

    var cancel = function () {
        var id = $(this).data('id');
        var form = document.createElement("form");
        form.setAttribute("method", "post");
        form.setAttribute("action", "/orders/" + id + "/cancel");
        document.body.appendChild(form);
        form.submit();
    };

    return {
        init: init
    };

}();

my.init();