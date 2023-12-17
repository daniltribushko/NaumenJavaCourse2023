package com.example.naumen2023.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/registration","/login", "/logout", "/access-denied").permitAll()
                        .requestMatchers("/webjars/**").permitAll()
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/js/**").permitAll()
                        .requestMatchers("/statistics/graphics").permitAll()
                        .requestMatchers("/moderator/**").hasRole("MODERATOR")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                        .failureUrl("/login?error=true")
                    .successHandler(authenticationSuccessHandler()))
                .logout(log -> log
                        .logoutUrl("/logout"))
                .exceptionHandling(exp-> exp
                        .authenticationEntryPoint(authenticationEntryPoint()));
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new urlAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
