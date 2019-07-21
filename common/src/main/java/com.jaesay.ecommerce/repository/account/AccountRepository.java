package com.jaesay.ecommerce.repository.account;

import com.jaesay.ecommerce.domain.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByAccountName(String username);
}
