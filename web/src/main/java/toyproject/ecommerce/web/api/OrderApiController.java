package toyproject.ecommerce.web.api;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toyproject.ecommerce.core.domain.Address;
import toyproject.ecommerce.web.api.dto.OrderRequestDto;
import toyproject.ecommerce.web.api.dto.OrderResponseDto;
import toyproject.ecommerce.web.config.oauth.LoginUser;
import toyproject.ecommerce.web.config.oauth.dto.SessionUser;
import toyproject.ecommerce.web.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<OrderResponseDto> order(@LoginUser SessionUser member,
                                                  @RequestBody @Validated OrderRequestDto requestDto,
                                                  BindingResult result) {

        if (result.hasErrors()) {
            throw new IllegalArgumentException("You must enter a valid address.");
        }

        Address address = new Address(requestDto.getCity(), requestDto.getStreet(), requestDto.getZipcode());
        OrderResponseDto responseDto = orderService.order(member, address);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
