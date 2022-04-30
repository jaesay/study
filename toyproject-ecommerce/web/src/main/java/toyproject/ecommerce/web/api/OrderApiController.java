package toyproject.ecommerce.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toyproject.ecommerce.core.domain.entity.Address;
import toyproject.ecommerce.core.domain.vo.CommonResponseVo;
import toyproject.ecommerce.web.api.dto.OrderRequestDto;
import toyproject.ecommerce.web.api.dto.OrderResponseDto;
import toyproject.ecommerce.web.config.auth.LoginUser;
import toyproject.ecommerce.web.config.auth.dto.SessionUser;
import toyproject.ecommerce.web.service.OrderService;

@RequiredArgsConstructor
@RestController
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<CommonResponseVo> order(@LoginUser SessionUser member,
                                                  @RequestBody @Validated OrderRequestDto requestDto,
                                                  BindingResult result) {

        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    CommonResponseVo.builder()
                            .message(result.getAllErrors().get(0).getDefaultMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST);
        }

        Address address = new Address(requestDto.getCity(), requestDto.getStreet(), requestDto.getZipcode());
        OrderResponseDto responseDto = orderService.order(member, address);

        return new ResponseEntity<>(
                CommonResponseVo.builder()
                        .message(HttpStatus.OK.getReasonPhrase())
                        .data(responseDto)
                        .build(),
                HttpStatus.OK);
    }
}
