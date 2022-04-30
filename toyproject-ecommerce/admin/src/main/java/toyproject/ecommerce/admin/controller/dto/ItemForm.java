package toyproject.ecommerce.admin.controller.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import toyproject.ecommerce.core.domain.entity.Item;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class ItemForm {

    @NotBlank
    @Size(min = 4, max = 20, message = "Name must be between 4 and 20 characters")
    private String name;

    @Positive
    private int price;

    @Positive
    private int stockQuantity;

    @NotNull
    private MultipartFile image;

    @Positive
    private Long categoryId;

    public Item toEntity() {
        return Item.builder()
                .name(this.name)
                .price(this.price)
                .stockQuantity(this.stockQuantity)
                .build();
    }
}
