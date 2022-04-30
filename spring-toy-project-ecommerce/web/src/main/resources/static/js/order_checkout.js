const orderCheckout = function () {
    "use strict";

    const init = function init() {
        bindFunctions();
    };

    const bindFunctions = function bindFunctions() {
        $("#postcodeBtn").on("click", popupPostcode);
        $("#saveAddrBtn").on("click", saveAddress);
        $("#payBtn").on("click", payForOrder);
    };

    const makeAddress = function (address, detailAddress, extraAddress) {
        return address + " " + detailAddress + extraAddress;
    };

    const payForOrder = function () {

        let IMP = window.IMP; // 생략가능
        IMP.init('imp43000163'); // 'iamport' 대신 부여받은 "가맹점 식별코드"를 사용
        let msg;

        let pqyRequestDto = {
            pg : 'html5_inicis',
            pay_method : 'card',
            merchant_uid : 'merchant_' + new Date().getTime(),
            name : 'Simple e-commerce 테스트 결제',
            amount : parseInt($("#totalPrice").text()),
            buyer_email :  $("#email").val(),
            buyer_name : $("#name").val(),
            buyer_tel : $("#phone").val(),
            buyer_addr : makeAddress($("#address").val(), $("#detailAddress").val(), $("#extraAddress").val()),
            buyer_postcode : $("#postcode").val()
        };

        IMP.request_pay(pqyRequestDto, function (rsp) {
            if ( rsp.success ) {
                //[1] 서버단에서 결제정보 조회를 위해 jQuery ajax로 imp_uid 전달하기
                jQuery.ajax({
                    url: "/orders/process", //cross-domain error가 발생하지 않도록 주의해주세요
                    type: 'POST',
                    contentType: 'application/json',
                    dataType: 'json',
                    data: JSON.stringify({
                        impUid: rsp.imp_uid,
                        paidAmount: rsp.paid_amount,
                        buyerAdrress: rsp.buyer_addr,
                        buyerTel: rsp.buyer_tel
                        //기타 필요한 데이터가 있으면 추가 전달
                    })
                }).done(function(res) {
                    //[2] 서버에서 REST API로 결제정보확인 및 서비스루틴이 정상적인 경우
                    msg = '결제가 완료되었습니다.';
                    msg += '\n고유ID : ' + rsp.imp_uid;
                    msg += '\n상점 거래ID : ' + rsp.merchant_uid;
                    msg += '\n결제 금액 : ' + rsp.paid_amount;
                    msg += '\n카드 승인번호 : ' + rsp.apply_num;

                    let orderData = [["orderId", res.orderId]];

                    if (res.orderStatus === "PAID") {
                        let options = {
                            action: "/order/complete",
                            method: "POST",
                            target: "_self",
                            data: orderData
                        };

                        let virtualForm = new VirtualForm(options);
                        virtualForm.init();
                        virtualForm.submit();
                    } else {
                        alert('결제에 실패하였습니다.');
                    }

                }).fail(function (res) {
                    alert('결제에 실패하였습니다.');
                });

            } else {
                msg = '결제에 실패하였습니다.';
                msg += '에러내용 : ' + rsp.error_msg;
                //실패시 이동할 페이지
                alert(msg);
            }
        });

    };

    const saveAddress = function () {
        $.ajax({
            url:'/accounts/addresses',
            contentType: 'application/json',
            type: "post",
            data: $("form[name=addressForm]").serialize()
        }).done(function(res){
            alert("저장되었습니다.");
        }).fail(function (jqXHR, textStatus, errorThrown) {
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
        });
    };

    const popupPostcode =  function () {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                let addr = ''; // 주소 변수
                let extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    $("#extraAddress").val(extraAddr);

                } else {
                    $("#extraAddress").val("");
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                $("#postcode").val(data.zonecode);
                $("#address").val(addr);
                // 커서를 상세주소 필드로 이동한다.
                $("#detailAddress").focus();
            }
        }).open();
    };

    return {
        init: init
    };

}();

orderCheckout.init();