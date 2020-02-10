package toyproject.ecommerce.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import toyproject.ecommerce.web.api.dto.DeleteCartItemResponseDto;
import toyproject.ecommerce.web.config.oauth.LoginUser;
import toyproject.ecommerce.web.config.oauth.dto.SessionUser;
import toyproject.ecommerce.web.api.dto.AddCartItemRequestDto;
import toyproject.ecommerce.web.api.dto.AddCartItemResponseDto;
import toyproject.ecommerce.web.service.CartService;

@RequiredArgsConstructor
@RestController
public class CartApiController {

    private final CartService cartService;

    @PostMapping("/api/carts")
    public ResponseEntity<AddCartItemResponseDto> addCartItem(@RequestBody @Validated AddCartItemRequestDto requestDto,
                                                              BindingResult result,
                                                              @LoginUser SessionUser member) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        AddCartItemResponseDto responseDto = cartService.saveCartItem(requestDto, member);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/carts/{itemId}")
    public ResponseEntity<DeleteCartItemResponseDto> deleteCartItem(@PathVariable Long itemId, @LoginUser SessionUser member) {

        DeleteCartItemResponseDto responseDto = cartService.deleteCartItem(itemId, member);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
