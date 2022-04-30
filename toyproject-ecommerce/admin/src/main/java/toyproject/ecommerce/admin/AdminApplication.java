package toyproject.ecommerce.admin;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = {"toyproject.ecommerce.admin","toyproject.ecommerce.core"})
public class AdminApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.yml,"
            + "classpath:aws.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(AdminApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }
}
