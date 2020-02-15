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
                alert("System Error");
            }
            return true;
        },
        complete: function complete(xhr, status) {
            return true;
        }
    },
    ajaxCalling: { // prevent ajax on multiple events
        callingUrl: {},
        start: function start(url, progressAlert) {
            var _this = this;
            var _isCalling = _this.callingUrl[url];
            if (_isCalling != "undefined" && _isCalling == true) {
                if (progressAlert == true) {
                    alert("Processing...");
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