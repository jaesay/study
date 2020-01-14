package toyproject.ecommerce.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@SpringBootApplication(scanBasePackages = {"toyproject.ecommerce.web","toyproject.ecommerce.core"})
@SpringBootApplication
@EntityScan("toyproject.ecommerce.*")
@EnableJpaRepositories("toyproject.ecommerce.*")
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
