var signUp = function () {
    "use strict";

    var form = $("form[name=signUpForm]");

    var init = function init() {
        validation();
    };

    var validation = function validation() {
        form.validate({
            onfocus: true,
            errorElement: "p",
            errorClass: 'text-danger',
            rules: {
                email: {
                    email: true,
                    remote: {
                        url: "/members/username",
                        type: "post",
                        data: {
                            email: function email() {
                                return $("input[name=email]").val();
                            }
                        }
                    }
                },
                password: {
                    required: true,
                    rangelength: [4, 20]
                },
                passwordConfirm: {
                    required: true,
                    rangelength: [4, 20],
                    equalTo: "#password"
                },
                name: {
                    required: true,
                    rangelength: [4, 20]
                }
            },
            messages: {
                email: {
                    email: "Email must be valid.",
                    remote: "Email address already in use. Please use other email."
                },
                password: {
                    required: "Password is required.",
                    rangelength: "Password must be between 4 and 20 characters."
                },
                passwordConfirm: "Passwords needs to match.",
                name: {
                    required: "Name is required.",
                    rangelength: "Name must be between 4 and 20 characters."
                }
            }
        });
    };

    return {
        init: init
    };
}();

signUp.init();
