package com.myspring.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String uriPrefix = "/user";

	@Inject
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("jaesay").password(passwordEncoder().encode("1234")).roles("USER", "ADMIN");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*http.authorizeRequests()
				.antMatchers("/login")
				.permitAll()
				.antMatchers("/*")
				.access("hasRole('USER')")
			.and()
			.formLogin();*/
		http
	        .authorizeRequests()
	            .antMatchers("/resources/**").permitAll() 
	            .antMatchers("/board/**", "/comments/**").hasRole("USER")
	            .and()
			.formLogin()
				.loginPage("/user/login.do")
				.loginProcessingUrl("/login")
				.permitAll()
				.and()
			.logout()
				.permitAll();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
