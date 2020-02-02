package toyproject.ecommerce.core.domain;

import lombok.Getter;
import lombok.Setter;
import toyproject.ecommerce.core.domain.enums.DeliveryStatus;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //ENUM [READY(준비), COMP(배송)]
}
