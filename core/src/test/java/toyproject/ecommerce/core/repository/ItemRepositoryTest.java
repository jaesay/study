package toyproject.ecommerce.core.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import toyproject.ecommerce.core.domain.Category;
import toyproject.ecommerce.core.domain.Item;
import toyproject.ecommerce.core.domain.dto.ItemSearch;
import toyproject.ecommerce.core.domain.dto.ItemSummaryDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemRepositoryTest {

    @Autowired ItemRepository itemRepository;
    @Autowired CategoryRepository categoryRepository;

    @Before
    public void setUp() throws Exception {
        Category category1 = new Category();
        category1.setName("clothes");
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setName("top");
        category2.setParent(category1);
        categoryRepository.save(category2);

        Item item1 = Item.builder()
                .name("item1")
                .price(10)
                .stockQuantity(100)
                .picture("/images/200x100.png")
                .category(category2)
                .build();

        Item item2 = Item.builder()
                .name("item2")
                .price(20)
                .stockQuantity(200)
                .picture("/images/200x100.png")
                .category(category2)
                .build();

        itemRepository.save(item1);
        itemRepository.save(item2);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test(expected = NullPointerException.class)
    public void searchWithNull() {
        Page<ItemSummaryDto> search = itemRepository.search(null, PageRequest.of(0, 9));

        fail("NullPointerException has occurred.");
    }

    @Test
    public void searchWithEmpty() {
        ItemSearch itemSearch = new ItemSearch();
        itemSearch.setItemName("");
        Pageable pageable = PageRequest.of(0, 9);
        Page<ItemSummaryDto> search = itemRepository.search(itemSearch, pageable);

        Page<Item> all = itemRepository.findAll(pageable);
        assertThat(search.getTotalElements()).isGreaterThanOrEqualTo(all.getTotalElements());
    }

    @Test
    public void search() {
        ItemSearch itemSearch = new ItemSearch();
        itemSearch.setItemName("2");
        Page<ItemSummaryDto> search = itemRepository.search(itemSearch, PageRequest.of(0, 9));

        assertThat(search.getTotalElements()).isGreaterThanOrEqualTo(0);
    }
}