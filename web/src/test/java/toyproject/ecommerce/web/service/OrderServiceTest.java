package toyproject.ecommerce.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import toyproject.ecommerce.core.domain.entity.Address;
import toyproject.ecommerce.core.domain.entity.Cart;
import toyproject.ecommerce.core.domain.entity.Category;
import toyproject.ecommerce.core.domain.entity.Item;
import toyproject.ecommerce.core.domain.entity.Member;
import toyproject.ecommerce.core.domain.enums.Role;
import toyproject.ecommerce.core.repository.CartRepository;
import toyproject.ecommerce.core.repository.CategoryRepository;
import toyproject.ecommerce.core.repository.ItemRepository;
import toyproject.ecommerce.core.repository.MemberRepository;
import toyproject.ecommerce.web.config.auth.dto.SessionUser;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Transactional
public class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired MemberRepository memberRepository;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired CartRepository cartRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired ObjectMapper objectMapper;
    @Autowired WebApplicationContext context;
    MockMvc mockMvc;

    Member member;
    Item item1, item2;
    Cart cart;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        member = Member.builder()
                .email("user@test.com")
                .password(passwordEncoder.encode("1234"))
                .name("user1")
                .role(Role.USER)
                .build();

        memberRepository.save(member);

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

        cart = Cart.createCart(member);
        cart.addCartItem(item1, 2);
        cart.addCartItem(item2, 3);
        cartRepository.save(cart);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void order() throws Exception {
        //given
        Address address = new Address("city1", "street1", "111111");

        //when
        orderService.order(new SessionUser(member), address);

        //then

    }
}