package toyproject.ecommerce.core.domain.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PersistentLogins {

    @Id
    private String series;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime lastUsed;
}
