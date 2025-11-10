package com.library.libraryManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. Define the SecurityFilterChain bean to replace 'configure(HttpSecurity http)'
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Allow all requests to the login page and access denied page
                        .requestMatchers("/login", "/access-denied").permitAll()
                        // All other requests must be authenticated
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")             // Specify the custom login page URL
                        .loginProcessingUrl("/index")    // The URL where Spring Security posts login data
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()
                )
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedPage("/access-denied")
                );

        return http.build();
    }

    // 2. Define the InMemoryUserDetailsManager bean to replace 'configure(AuthenticationManagerBuilder auth)'
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        // Use PasswordEncoderFactories to get a default, safe password encoder
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        // Define users and roles
        UserDetails bilal = User.builder()
                .username("bilal")
                .password(encoder.encode("test123")) // Passwords must be encoded!
                .roles("USER")
                .build();

        UserDetails merve = User.builder()
                .username("merve")
                .password(encoder.encode("test123")) // Passwords must be encoded!
                .roles("USER", "ADMIN")
                .build();

        // Return a manager with your in-memory users
        return new InMemoryUserDetailsManager(bilal, merve);
    }
}