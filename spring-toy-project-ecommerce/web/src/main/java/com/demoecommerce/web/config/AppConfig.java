package com.demoecommerce.web.config;

import com.demoecommerce.domain.entity.*;
import com.demoecommerce.domain.enums.AccountRole;
import com.demoecommerce.repository.ProductOptionRepository;
import com.demoecommerce.repository.ProductRepository;
import com.demoecommerce.repository.ProductSkuRepository;
import com.demoecommerce.repository.ProductSkuValueRepository;
import com.demoecommerce.web.common.AppProperties;
import com.demoecommerce.web.service.CategoryService;
import com.demoecommerce.web.service.account.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
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
            ProductSkuRepository productSkuRepository;

            @Autowired
            ProductSkuValueRepository productSkuValueRepository;

            @Autowired
            ProductOptionRepository productOptionRepository;

            @Autowired
            AppProperties appProperties;

            @Autowired
            CategoryService categoryService;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                String adminUsername = appProperties.getAdminUsername();
                String userUsername = appProperties.getUserUsername();

                Account admin = accountService.getAccountByAccountName(adminUsername)
                        .orElse(Account.builder()
                                .accountName(adminUsername)
                                .password(appProperties.getAdminPassword())
                                .email(appProperties.getAdminEmail())
                                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                                .build());

                if (admin.getAccountId() == null) {
                    accountService.saveAccount(admin);
                }

                Account user = accountService.getAccountByAccountName(userUsername)
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

                List<ProductOption> productOptions = new ArrayList<>();
                ProductOption color = ProductOption.builder()
                        .optionName("color")
                        .build();

                ProductOption size = ProductOption.builder()
                        .optionName("size")
                        .build();

                productOptionRepository.save(color);
                productOptionRepository.save(size);

                // Product 100개 생성
                List<Product> products = new ArrayList<>();
                IntStream.range(0, 100).forEach(i -> {
                    String categoryId = "F101";
                    if (i >= 35) {
                        categoryId = "F102";
                    } else if (i >= 70){
                        categoryId = "F103";
                    }
                    Product product = Product.builder()
                            .productName("product" + i)
                            .category(Category.builder().categoryId(categoryId).build())
                            .price(BigInteger.valueOf(ThreadLocalRandom.current().nextInt(10, 20 + 1)))
                            .forSale(true)
                            .onSale(true)
                            .build();

                    products.add(product);
                });

                productRepository.saveAll(products);

                // option 초기화
                List<String> colors = List.of("white", "black", "red");
                ProductOptionValue productOptionValue1 = ProductOptionValue.builder()
                        .productOption(color)
                        .optionValue("white")
                        .build();
                List<ProductOptionValue> colorProductOptionValues = new ArrayList<>();
                colors.forEach(c -> colorProductOptionValues.add(ProductOptionValue.builder()
                        .productOption(color)
                        .optionValue(c)
                        .build()));



                List<String> sizes = List.of("S","M","L");
                List<ProductOptionValue> sizeProductOptionValues = new ArrayList<>();
                ProductOptionValue productOptionValue = ProductOptionValue.builder()
                        .productOption(size)
                        .optionValue("S")
                        .build();
                sizes.forEach(s -> sizeProductOptionValues.add(ProductOptionValue.builder()
                            .productOption(size)
                            .optionValue(s)
                            .build()));

                // ProductSku 생성, 3 * 3 * 100 = 900 product - color - size
                List<ProductSku> productSkus = new ArrayList<>();

                products.forEach(p -> {
                    String productName = p.getProductName();
                    for (int i = 0; i < colors.size(); i++) {
                        String colorName = colors.get(i);
                        String introduction = "Some quick example text to build on the card title and make up the bulk of the card's content." + i;
                        String description = "Le Lorem Ipsum est simplement du faux texte employé dans la composition et la mise en page avant impression. Le Lorem Ipsum est le faux texte standard de l'imprimerie depuis les années 1500, quand un peintre anonyme assembla ensemble des morceaux de texte pour réaliser un livre spécimen de polices de texte. Il n'a pas fait que survivre cinq siècles, mais s'est aussi adapté à la bureautique informatique, sans que son contenu n'en soit modifié. Il a été popularisé dans les années 1960 grâce à la vente de feuilles Letraset contenant des passages du Lorem Ipsum, et, plus récemment, par son inclusion dans des applications de mise en page de texte, comme Aldus PageMaker.<br /><br />Contrairement à une opinion répandue, le Lorem Ipsum n'est pas simplement du texte aléatoire. Il trouve ses racines dans une oeuvre de la littérature latine classique datant de 45 av. J.-C., le rendant vieux de 2000 ans. Un professeur du Hampden-Sydney College, en Virginie, s'est intéressé à un des mots latins les plus obscurs, consectetur, extrait d'un passage du Lorem Ipsum, et en étudiant tous les usages de ce mot dans la littérature classique, découvrit la source incontestable du Lorem Ipsum. Il provient en fait des sections 1.10.32 et 1.10.33 du \"De Finibus Bonorum et Malorum\" (Des Suprêmes Biens et des Suprêmes Maux) de Cicéron. Cet ouvrage, très populaire pendant la Renaissance, est un traité sur la théorie de l'éthique. Les premières lignes du Lorem Ipsum, \"Lorem ipsum dolor sit amet...\", proviennent de la section 1.10.32." + i;
                        for (int j = 0; j < sizes.size(); j++) {
                            ProductSku productSku = ProductSku.builder()
                                    .product(p)
                                    .skuName(productName + " (" + colorName + ")")
                                    .sku(productName + "-" + colorName + "-" + sizes.get(j))
                                    .introduction(introduction + " " + i)
                                    .description(description)
                                    .extraPrice(BigInteger.ZERO)
                                    .stock(100)
                                    .build();
                            productSkus.add(productSku);
                        }
                    }
                });

                productSkuRepository.saveAll(productSkus);

                // 900 * 2(color, size) productSku * optionValue
                List<ProductSkuValue> productSkuValues = new ArrayList<>();
                productSkus.forEach(sku -> {
                    List<String> skuInfo = Arrays.asList(sku.getSku().split("-"));

                    ProductSkuValue colorValue = ProductSkuValue.builder()
                                        .productSku(sku)
                                        .productOptionValue(colorProductOptionValues.stream()
                                                .filter(cpo -> cpo.getOptionValue().equals(skuInfo.get(1)))
                                                .findFirst().get())
                                        .build();

                    ProductSkuValue sizeValue = ProductSkuValue.builder()
                            .productSku(sku)
                            .productOptionValue(sizeProductOptionValues.stream()
                                    .filter(spo -> spo.getOptionValue().equals(skuInfo.get(2)))
                                    .findFirst().get())
                            .build();

                    productSkuValues.add(colorValue);
                    productSkuValues.add(sizeValue);

                });

                productSkuValueRepository.saveAll(productSkuValues);
            }
        };
    }

}
