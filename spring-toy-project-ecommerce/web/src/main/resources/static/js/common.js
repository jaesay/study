var _slicedToArray = function () { function sliceIterator(arr, i) { var _arr = []; var _n = true; var _d = false; var _e = undefined; try { for (var _i = arr[Symbol.iterator](), _s; !(_n = (_s = _i.next()).done); _n = true) { _arr.push(_s.value); if (i && _arr.length === i) break; } } catch (err) { _d = true; _e = err; } finally { try { if (!_n && _i["return"]) _i["return"](); } finally { if (_d) throw _e; } } return _arr; } return function (arr, i) { if (Array.isArray(arr)) { return arr; } else if (Symbol.iterator in Object(arr)) { return sliceIterator(arr, i); } else { throw new TypeError("Invalid attempt to destructure non-iterable instance"); } }; }();

var CommonUtil = {
    ajaxOptions: {
        async: true,
        data: null,
        url: null,
        type: "POST",
        processData: true,
        dataType: "json",
        progressAlert: true,
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        loader: false,
        multiRequest: false,
        success: function success(result, status, xhr) {
            return true;
        },
        error: function error(xhr, status, _error) {
            if (xhr !== undefined && xhr.responseText !== undefined && xhr.responseText != "") {
                alert(xhr.responseText);
            } else if (xhr.responseText !== "") {
                alert("시스템 에러입니다.");
            }
            return true;
        },
        complete: function complete(xhr, status) {
            return true;
        }
    },
    ajaxCalling: { // ajax 중복호출 방지용
        callingUrl: {},
        start: function start(url, progressAlert) {
            var _this = this;
            var _isCalling = _this.callingUrl[url];
            if (_isCalling != "undefined" && _isCalling == true) {
                if (progressAlert == true) {
                    alert("처리중입니다.");
                }
                return false;
            }
            _this.callingUrl[url] = true;
            return true;
        },
        complete: function complete(url) {
            this.callingUrl[url] = false;
            return true;
        }
    },
    ajax: function ajax(options) {
        var _this = this;
        $.extend(_this.ajaxOptions, options);

        if (_this.ajaxOptions.url === null) {
            return false;
        }

        // var _csrf = getCsrfToken();

        $.ajax({
            async: _this.ajaxOptions.async,
            url: _this.ajaxOptions.url,
            type: _this.ajaxOptions.type,
            data: _this.ajaxOptions.data,
            contentType: _this.ajaxOptions.contentType,
            processData: _this.ajaxOptions.processData,
            beforeSend: function beforeSend(request) {
                if (_this.ajaxOptions.loader) {
                    $("#ajaxLoader").show();
                }
                // request.setRequestHeader("X-CSRF-TOKEN", _csrf);
                if (!_this.ajaxOptions.multiRequest) {
                    if (_this.ajaxCalling.start(_this.ajaxOptions.url, _this.ajaxOptions.progressAlert) === false) {
                        return false;
                    }
                }
                request.setRequestHeader("AJAX", "true");
            },
            success: function success(result, status, xhr) {
                _this.ajaxOptions.success(result, status, xhr);
            },
            error: function error(xhr, status, _error2) {
                _this.ajaxOptions.error(xhr, status, _error2);
            },
            complete: function complete(xhr, status) {
                if (!_this.ajaxOptions.multiRequest) {
                    _this.ajaxCalling.complete(_this.ajaxOptions.url);
                }
                _this.ajaxOptions.complete(xhr, status);

                if (_this.ajaxOptions.loader) {
                    $("#ajaxLoader").hide();
                }
            }
        });
    }
};

var VirtualForm = function VirtualForm(option) {
    var _this2 = this;

    this.form = document.createElement("form"), this.option = option;
    var init = function init() {
        createForm();
        createElement();
    };

    var createForm = function createForm() {
        _this2.form.action = _this2.option.action;
        _this2.form.target = _this2.option.target;
        _this2.form.method = typeof _this2.option.method === 'undefined' ? 'post' : _this2.option.method;
        _this2.form.acceptCharset = typeof _this2.option.charset === 'undefined' ? 'utf-8' : _this2.option.charset;
        document.body.appendChild(_this2.form);
    };

    var createElement = function createElement() {
        var _iteratorNormalCompletion = true;
        var _didIteratorError = false;
        var _iteratorError = undefined;

        try {
            for (var _iterator = _this2.option.data[Symbol.iterator](), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
                var _ref = _step.value;

                var _ref2 = _slicedToArray(_ref, 2);

                var key = _ref2[0];
                var value = _ref2[1];

                var hiddenField = document.createElement("input");
                hiddenField.setAttribute("type", "hidden");
                hiddenField.setAttribute("name", key);
                hiddenField.setAttribute("value", value);
                _this2.form.appendChild(hiddenField);
            }
        } catch (err) {
            _didIteratorError = true;
            _iteratorError = err;
        } finally {
            try {
                if (!_iteratorNormalCompletion && _iterator.return) {
                    _iterator.return();
                }
            } finally {
                if (_didIteratorError) {
                    throw _iteratorError;
                }
            }
        }
    };

    var submit = function submit() {
        _this2.form.submit();
    };

    return {
        init: init,
        submit: submit
    };
};