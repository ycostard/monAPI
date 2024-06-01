package com.bibliotheque.bibliotheque.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bibliotheque.bibliotheque.services.security.AppAuthProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	 @Bean
	 public PasswordEncoder passwordEncoder() {
	 return new BCryptPasswordEncoder(10);
	 }

	@Bean
	public AuthenticationManager authenticationManager() {
		return new AppAuthProvider();
	}

	@Bean
	public JWTTokenProvider jwtTokenProvider() {
		return new JWTTokenProvider();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/clients/signup").permitAll()
						.requestMatchers("/clients/signin").permitAll()
						.requestMatchers("/swagger-ui/**").permitAll()
						.requestMatchers("/webjars/**").permitAll()
						.requestMatchers("/doc/**").permitAll()
						.requestMatchers("/actuator/**").hasAuthority("ROLE_ADMIN")
						.requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
						.requestMatchers("/commandes/**").hasAnyAuthority("ROLE_CLIENT", "ROLE_ADMIN")
						.anyRequest().authenticated())
				.addFilterBefore(new JWTTokenFilter(jwtTokenProvider()), UsernamePasswordAuthenticationFilter.class)
				.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.csrf((csrf) -> csrf.disable());
		http.cors((cors) -> cors.disable());

		return http.build();
	}
}