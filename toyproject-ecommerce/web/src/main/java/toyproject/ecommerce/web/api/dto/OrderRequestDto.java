package toyproject.ecommerce.web.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class OrderRequestDto {

    @NotBlank @Size(min = 2, max = 50, message = "{order.address.city.error}")
    private String city;

    @NotBlank @Size(min = 2, max = 100, message = "{order.address.street.error}")
    private String street;

    @NotBlank @Size(min = 2, max = 20, message = "{order.address.zipcode.error}")
    private String zipcode;
}
