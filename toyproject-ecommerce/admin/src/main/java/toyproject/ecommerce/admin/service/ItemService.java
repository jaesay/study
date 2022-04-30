package toyproject.ecommerce.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import toyproject.ecommerce.core.domain.entity.Category;
import toyproject.ecommerce.core.domain.entity.Item;
import toyproject.ecommerce.core.repository.ItemRepository;

import java.io.IOException;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final AWSS3Service awss3Service;
    private final CategoryService categoryService;

    public Page<Item> findItems(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("id").descending());

        return itemRepository.findAll(pageable);
    }

    @Transactional
    public Long save(Item item, MultipartFile multipartFile, Long categoryId) throws IOException {

        String uploadImageUrl = awss3Service.upload(multipartFile, "static");
        Category category = categoryService.findById(categoryId);

        item.update(uploadImageUrl, category);
        itemRepository.save(item);

        return item.getId();
    }
}
