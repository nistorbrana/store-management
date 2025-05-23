package com.learning.storemanagement.configuration;

import com.learning.storemanagement.model.Role;
import com.learning.storemanagement.security.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private static final String USERS_ENDPOINT = "/users";
    private static final String PRODUCTS_ENDPOINT = "/products";

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, USERS_ENDPOINT).hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, USERS_ENDPOINT).hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, PRODUCTS_ENDPOINT).hasAnyRole(Role.ADMIN.name(), Role.REGULAR.name())
                        .requestMatchers(HttpMethod.POST, PRODUCTS_ENDPOINT).hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PATCH, PRODUCTS_ENDPOINT). hasRole(Role.ADMIN.name())
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
