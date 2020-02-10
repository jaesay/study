package toyproject.ecommerce.web.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class OrderRequestDto {

    @NotBlank @Size(min = 2, max = 50)
    private String city;

    @NotBlank @Size(min = 2, max = 100)
    private String street;

    @NotBlank @Size(min = 2, max = 20)
    private String zipcode;
}
