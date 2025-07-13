package com.geetansh.journelApp.journel.config;

// The import for AntPathRequestMatcher is no longer needed and has been removed.
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Filter chain for all public endpoints.
     * This chain has a higher precedence (Order 1) and will be checked first.
     */
    @Bean
    @Order(1)
    public SecurityFilterChain publicEndpointsFilterChain(HttpSecurity http) throws Exception {
        return http
                // THIS IS THE CORRECT, NON-DEPRECATED WAY:
                // Pass the path pattern directly as a string.
                .securityMatcher("/public/**")
                .authorizeHttpRequests(request -> request
                        .anyRequest().permitAll() // Allow all requests that match the securityMatcher
                )
                // For public endpoints, we disable all security features.
                .sessionManagement(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .build();
    }

    /**
     * Default filter chain for all other secured endpoints.
     * This chain has a lower precedence (Order 2) and acts as a catch-all.
     */
    @Bean
    @Order(2)
    public SecurityFilterChain securedEndpointsFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/journal/**").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                // Apply httpBasic authentication ONLY to this secured chain
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}