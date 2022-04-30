package toyproject.ecommerce.core.repository.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * toyproject.ecommerce.core.repository.dto.QItemSummaryDto is a Querydsl Projection type for ItemSummaryDto
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QItemSummaryDto extends ConstructorExpression<ItemSummaryDto> {

    private static final long serialVersionUID = -1117605775L;

    public QItemSummaryDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<Integer> price, com.querydsl.core.types.Expression<Integer> stockQuantity, com.querydsl.core.types.Expression<String> picture, com.querydsl.core.types.Expression<String> categoryName) {
        super(ItemSummaryDto.class, new Class<?>[]{long.class, String.class, int.class, int.class, String.class, String.class}, id, name, price, stockQuantity, picture, categoryName);
    }

}

