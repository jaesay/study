package com.demoecommerce.web.config;

import com.demoecommerce.domain.entity.Product;
import com.demoecommerce.repository.ProductRepository;
import com.demoecommerce.web.common.AppProperties;
import com.demoecommerce.domain.entity.Account;
import com.demoecommerce.domain.entity.Category;
import com.demoecommerce.domain.enums.AccountRole;
import com.demoecommerce.web.service.CategoryService;
import com.demoecommerce.web.service.ProductService;
import com.demoecommerce.web.service.account.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {

            @Autowired
            AccountService accountService;

            @Autowired
            ProductRepository productRepository;

            @Autowired
            AppProperties appProperties;

            @Autowired
            CategoryService categoryService;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                String adminUsername = appProperties.getAdminUsername();
                String userUsername = appProperties.getUserUsername();

                Account admin = accountService.getAccount(adminUsername)
                        .orElse(Account.builder()
                                .accountName(adminUsername)
                                .password(appProperties.getAdminPassword())
                                .email(appProperties.getAdminEmail())
                                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                                .build());

                if (admin.getAccountId() == null) {
                    accountService.saveAccount(admin);
                }

                Account user = accountService.getAccount(userUsername)
                        .orElse(Account.builder()
                                .accountName(userUsername)
                                .password(appProperties.getUserPassword())
                                .email(appProperties.getUserEmail())
                                .roles(Set.of(AccountRole.USER))
                                .build());

                if (user.getAccountId() == null) {
                    accountService.saveAccount(user);
                }

                appProperties.getCategories().forEach(c -> {
                    if (!categoryService.existsById(c.get("id"))) {
                        categoryService.saveCategory(Category.builder()
                                .categoryId(c.get("id"))
                                .categoryName(c.get("name"))
                                .build());
                    }
                });

                List<Product> products = new ArrayList<>();
                int productSize = 100;
                String introduction = "Some quick example text to build on the card title and make up the bulk of the card's content.";
                String description = "Le Lorem Ipsum est simplement du faux texte employé dans la composition et la mise en page avant impression. Le Lorem Ipsum est le faux texte standard de l'imprimerie depuis les années 1500, quand un peintre anonyme assembla ensemble des morceaux de texte pour réaliser un livre spécimen de polices de texte. Il n'a pas fait que survivre cinq siècles, mais s'est aussi adapté à la bureautique informatique, sans que son contenu n'en soit modifié. Il a été popularisé dans les années 1960 grâce à la vente de feuilles Letraset contenant des passages du Lorem Ipsum, et, plus récemment, par son inclusion dans des applications de mise en page de texte, comme Aldus PageMaker.<br /><br />Contrairement à une opinion répandue, le Lorem Ipsum n'est pas simplement du texte aléatoire. Il trouve ses racines dans une oeuvre de la littérature latine classique datant de 45 av. J.-C., le rendant vieux de 2000 ans. Un professeur du Hampden-Sydney College, en Virginie, s'est intéressé à un des mots latins les plus obscurs, consectetur, extrait d'un passage du Lorem Ipsum, et en étudiant tous les usages de ce mot dans la littérature classique, découvrit la source incontestable du Lorem Ipsum. Il provient en fait des sections 1.10.32 et 1.10.33 du \"De Finibus Bonorum et Malorum\" (Des Suprêmes Biens et des Suprêmes Maux) de Cicéron. Cet ouvrage, très populaire pendant la Renaissance, est un traité sur la théorie de l'éthique. Les premières lignes du Lorem Ipsum, \"Lorem ipsum dolor sit amet...\", proviennent de la section 1.10.32.";
                IntStream.range(0, productSize).forEach(i -> {
                    String categoryId = "F101";
                    if (i >= 35) {
                        categoryId = "F102";
                    } else if (i >= 70){
                        categoryId = "F103";
                    }

                    products.add(Product.builder()
                            .category(Category.builder().categoryId(categoryId).build())
                            .productName("product" + i)
                            .introduction(introduction + " " + i)
                            .description(description + " " + i)
                            .price(BigDecimal.valueOf(Math.random() * 25))
                            .imageUrl("")
                            .build());
                });

                if (productRepository.count() <= productSize) {
                    productRepository.saveAll(products);
                }
            }
        };
    }

}
