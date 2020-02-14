package toyproject.ecommerce.api.carts;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import toyproject.ecommerce.core.domain.CartItem;
import toyproject.ecommerce.core.repository.CartItemRepository;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequestMapping(value = "/api/carts", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
@Controller
public class CartController {

    private final CartItemRepository cartItemRepository;

    @PostMapping
    public ResponseEntity addCartItem(@RequestBody CartItem cartItem) {
        CartItem newCartItem = cartItemRepository.save(cartItem);
        URI createUri = linkTo(CartController.class).slash(newCartItem.getId()).toUri();
        return ResponseEntity.created(createUri).body(cartItem);
    }
}
