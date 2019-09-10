package com.demoecommerce.web.service.account;

import com.demoecommerce.domain.entity.Account;
import com.demoecommerce.domain.entity.Address;
import com.demoecommerce.domain.entity.CustomUserDetails;
import com.demoecommerce.repository.AddressRepository;
import com.demoecommerce.repository.account.AccountRepository;
import com.demoecommerce.web.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;

    private final AddressRepository addressRepository;

    private final PasswordEncoder passwordEncoder;

    private final CartService cartService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByAccountName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new CustomUserDetails(account);
}

    public void saveAccount(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
        cartService.save(account.getAccountId());
    }

    public Optional<Account> getAccountByAccountName(String accountName) {
        return accountRepository.findByAccountName(accountName);
    }

    public Optional<Account> getAccount(Long accountId) {
        return accountRepository.findById(accountId);
    }

    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }
}
