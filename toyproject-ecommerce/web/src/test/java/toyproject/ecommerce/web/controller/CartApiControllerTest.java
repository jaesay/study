package toyproject.ecommerce.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import toyproject.ecommerce.core.domain.entity.Cart;
import toyproject.ecommerce.core.domain.entity.Category;
import toyproject.ecommerce.core.domain.entity.Item;
import toyproject.ecommerce.core.domain.entity.Member;
import toyproject.ecommerce.core.domain.enums.Role;
import toyproject.ecommerce.core.repository.CartRepository;
import toyproject.ecommerce.core.repository.CategoryRepository;
import toyproject.ecommerce.core.repository.ItemRepository;
import toyproject.ecommerce.core.repository.MemberRepository;
import toyproject.ecommerce.web.api.dto.AddCartItemRequestDto;
import toyproject.ecommerce.web.config.auth.dto.SessionUser;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Transactional
public class CartApiControllerTest {

    @Autowired MemberRepository memberRepository;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired CartRepository cartRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired ObjectMapper objectMapper;
    @Autowired WebApplicationContext context;
    MockMvc mockMvc;
    MockHttpSession session;

    Member member;
    Item item1, item2;
    Cart cart;

    @Test
    @WithMockUser(roles="USER")
    public void addCartItem() throws Exception {
        //given
        AddCartItemRequestDto requestDto = AddCartItemRequestDto.builder()
                .itemId(item1.getId())
                .itemCnt(2)
                .build();

        session = new MockHttpSession();
        session.setAttribute("member", new SessionUser(member));

        String json = objectMapper.writeValueAsString(requestDto);

        //when
        mockMvc.perform(post("/api/carts")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print()) //then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("data.itemId").value(item1.getId()))
                .andExpect(jsonPath("data.itemName").value(item1.getName()))
                .andExpect(jsonPath("data.itemCnt").value(2))
                .andExpect(jsonPath("data.itemPrice").value(10))
        ;
    }

    @Test
    @WithMockUser(roles="USER")
    public void addCartItemWithInvalidItemCountZero() throws Exception {
        //given
        AddCartItemRequestDto requestDto = AddCartItemRequestDto.builder()
                .itemId(item1.getId())
                .itemCnt(0)
                .build();

        session = new MockHttpSession();
        session.setAttribute("member", new SessionUser(member));

        String json = objectMapper.writeValueAsString(requestDto);

        //when
        mockMvc.perform(post("/api/carts")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print()) //then
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @WithMockUser(roles="USER")
    public void deleteCartItem() throws Exception {
        //given
        cart.addCartItem(item1, 3);
        cartRepository.save(cart);

        session = new MockHttpSession();
        session.setAttribute("member", new SessionUser(member));

        //when
        mockMvc.perform(delete("/api/carts/" + item1.getId())
                .session(session))
                .andDo(print()) //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.itemId").value(item1.getId()))
                .andExpect(jsonPath("data.itemName").value(item1.getName()))
                .andExpect(jsonPath("data.totalPrice").value(item1.getPrice() * 3))
        ;
    }

    @Before
    public void setUp() {
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
        cart = cartRepository.save(Cart.createCart(member));

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
}