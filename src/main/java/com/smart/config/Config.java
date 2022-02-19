package com.smart.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class Config extends WebSecurityConfigurerAdapter {
  
	 @Bean
	protected UserDetailsService getUserDetailsService() {
	
		return new UserDetailsServiceImp();
	} 
	 
	 @Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		 return new BCryptPasswordEncoder();
	 }
	 
	 @Bean
	 DaoAuthenticationProvider daoAuthenticationProvider() {
		 DaoAuthenticationProvider authenticationProvider=  new DaoAuthenticationProvider();
		 authenticationProvider.setUserDetailsService(this.getUserDetailsService());
		 authenticationProvider.setPasswordEncoder(this.getPasswordEncoder());
		 return authenticationProvider;
	 }

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
     auth.authenticationProvider(daoAuthenticationProvider());
	}
  
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/user/**").hasRole("USER")
		.antMatchers("/**")
		.permitAll().and()
		.formLogin().loginPage("/sigin")
		.loginProcessingUrl("/dologin")
		.defaultSuccessUrl("/user/index")	
		.and()
		.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/sigin")
		.and()
		.cors()
		.disable();
	}

	
	 
}
