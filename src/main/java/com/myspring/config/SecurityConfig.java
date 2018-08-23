package com.myspring.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Inject
	UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/webjars/**");
	}	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
	        .authorizeRequests()
	        	.antMatchers("/", "/board/get*", "/comments/cnt/**", "/comments/all/**").permitAll()
	        	.antMatchers("/admin/**").hasRole("ADMIN")
	            .antMatchers("/board/**", "/comments/**").hasRole("USER")
	            .anyRequest().authenticated()
	            .and()
			.formLogin()
				.loginPage("/user/login.do")
				.loginProcessingUrl("/login")
				.usernameParameter("memberId")
				.passwordParameter("password")
				.permitAll()
				.and()
			.logout()
				.permitAll()
				.and()
			.exceptionHandling()
				.accessDeniedPage("/error/access");
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
