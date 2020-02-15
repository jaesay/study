package toyproject.ecommerce.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import toyproject.ecommerce.core.domain.dto.CommonResponseDto;
import toyproject.ecommerce.web.api.dto.AddCartItemRequestDto;
import toyproject.ecommerce.web.api.dto.AddCartItemResponseDto;
import toyproject.ecommerce.web.api.dto.DeleteCartItemResponseDto;
import toyproject.ecommerce.web.config.oauth.LoginUser;
import toyproject.ecommerce.web.config.oauth.dto.SessionUser;
import toyproject.ecommerce.web.service.CartService;

@RequiredArgsConstructor
@RestController
public class CartApiController {

    private final CartService cartService;

    @PostMapping("/api/carts")
    public ResponseEntity<CommonResponseDto> addCartItem(@RequestBody @Validated AddCartItemRequestDto requestDto,
                                                         BindingResult result,
                                                         @LoginUser SessionUser member) {

        if (result.hasErrors()) {
            return new ResponseEntity<>(
                CommonResponseDto.builder()
                        .message(result.getAllErrors().get(0).getDefaultMessage())
                        .build(),
                HttpStatus.BAD_REQUEST);
        }

        AddCartItemResponseDto responseDto = cartService.saveCartItem(requestDto, member);

        return new ResponseEntity<>(
                CommonResponseDto.builder()
                        .message(HttpStatus.CREATED.getReasonPhrase())
                        .data(responseDto)
                        .build(),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/api/carts/{itemId}")
    public ResponseEntity<CommonResponseDto> deleteCartItem(@PathVariable Long itemId, @LoginUser SessionUser member) {

        DeleteCartItemResponseDto responseDto = cartService.deleteCartItem(itemId, member);

        return new ResponseEntity<>(
                CommonResponseDto.builder()
                        .message(HttpStatus.OK.getReasonPhrase())
                        .data(responseDto)
                        .build(),
                HttpStatus.OK);
    }
}
