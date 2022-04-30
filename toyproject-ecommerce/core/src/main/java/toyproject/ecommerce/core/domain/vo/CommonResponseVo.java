package toyproject.ecommerce.core.domain.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommonResponseVo<T> {

    private String message;

    private T data;
}
