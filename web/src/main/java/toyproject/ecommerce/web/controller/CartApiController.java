package toyproject.ecommerce.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toyproject.ecommerce.web.config.oauth.LoginUser;
import toyproject.ecommerce.web.config.oauth.dto.SessionUser;
import toyproject.ecommerce.web.controller.dto.AddCartItemRequestDto;
import toyproject.ecommerce.web.controller.dto.AddCartItemResponseDto;
import toyproject.ecommerce.web.service.CartService;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class CartApiController {

    private final CartService cartService;
    private final HttpSession httpSession;

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
}
