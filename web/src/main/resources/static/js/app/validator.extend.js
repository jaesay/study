$(document).ready(function () {

    $.validator.addMethod("regex", function (value, element, regexp) {
        if (regexp.constructor != RegExp) regexp = new RegExp(regexp);else if (regexp.global) regexp.lastIndex = 0;
        return this.optional(element) || regexp.test(value);
    }, "Please check your input.");

    $.validator.addMethod("pwcheck", function (value) {
        return (/^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{8,50}$/.test(value) && !/(\w)\1\1/.test(value)
        );
    });
});
