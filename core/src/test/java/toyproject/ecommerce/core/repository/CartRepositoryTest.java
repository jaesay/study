package toyproject.ecommerce.core.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import toyproject.ecommerce.core.config.TestConfig;
import toyproject.ecommerce.core.domain.entity.*;
import toyproject.ecommerce.core.domain.enums.Role;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Import(TestConfig.class)
@DataJpaTest
public class CartRepositoryTest {

    @Autowired CartRepository cartRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired CategoryRepository categoryRepository;
    Member user1;
    Item item1;
    Item item2;

    @Before
    public void setUp() {
        user1 = Member.builder()
                .email("user1@test.com")
                .name("user1")
                .role(Role.USER)
                .build();
        memberRepository.save(user1);

        Cart cart = Cart.createCart(user1);
        cartRepository.save(cart);

        Category category1 = new Category();
        category1.setName("clothes");
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setName("top");
        category2.setParent(category1);
        categoryRepository.save(category2);

        item1 = Item.builder()
                .name("item1")
                .price(10)
                .stockQuantity(100)
                .picture("/images/200x100.png")
                .category(category2)
                .build();

        item2 = Item.builder()
                .name("item2")
                .price(20)
                .stockQuantity(200)
                .picture("/images/200x100.png")
                .category(category2)
                .build();

        itemRepository.save(item1);
        itemRepository.save(item2);
    }

    @Test
    public void findByMember_Email() {
        //given
        Optional<Cart> cart = cartRepository.findByMember_Id(user1.getId());
        System.out.println("================================");

        cart.get().addCartItem(item1, 2);
        cart.get().addCartItem(item2, 3);

        cartRepository.save(cart.get());

        //when
        System.out.println("================================");
        Optional<Cart> byMember_email = cartRepository.findByMember_Email(user1.getEmail());
        Cart cart1 = byMember_email.get();
        List<CartItem> cartItems = cart1.getCartItems();

        //then
        assertThat(cartItems.size()).isEqualTo(2);
        assertThat(cartItems.get(0).getItem().getName()).isEqualTo("item1");
    }
}