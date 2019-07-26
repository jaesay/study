package com.jaesay.ecommerce.config;

import com.jaesay.ecommerce.common.AppProperties;
import com.jaesay.ecommerce.domain.entity.Account;
import com.jaesay.ecommerce.domain.entity.Category;
import com.jaesay.ecommerce.domain.enums.AccountRole;
import com.jaesay.ecommerce.service.CategoryService;
import com.jaesay.ecommerce.service.account.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

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

            }
        };
    }

}
