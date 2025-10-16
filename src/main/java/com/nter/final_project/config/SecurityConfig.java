package com.nter.final_project.config;

import com.nter.final_project.config.security.JwtAuthFilter;
import com.nter.final_project.exception.UnauthenticatedException;
import com.nter.final_project.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry ->
                        registry
                                //acceso permitido a todos
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers(  "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/swagger-ui.html").permitAll()
                                //acceso users Admin completo, User acceso solo a search
                                .requestMatchers(HttpMethod.GET, "/users").permitAll()
                                .requestMatchers("/users/all").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/users/*").authenticated()
                                .requestMatchers(HttpMethod.POST, "/users/*").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/users/*").hasRole("ADMIN")
                                .requestMatchers("/users/*/country").authenticated()
                                .requestMatchers("/users/*/password").authenticated()

                                .requestMatchers("/users/**").hasRole("ADMIN")

                                //acceso products Admin completo, User solo get
                                .requestMatchers(HttpMethod.GET, "/products/search").authenticated()
                                .requestMatchers("/products/**").hasRole("ADMIN")
                                //acceso Orders Admin completo, User get and post solo los suyos
                                .requestMatchers("/orders").authenticated()
                                .requestMatchers("/orders/all").hasRole("ADMIN")
                                .requestMatchers("/orders/deleted").hasRole("ADMIN")
                                .requestMatchers("/orders/*/status").authenticated()
                                .requestMatchers("/orders/user/*").authenticated()
                                //acceso Countries Admin completo, user solo get
                                .requestMatchers(HttpMethod.GET, "/countries").authenticated()
                                .requestMatchers(HttpMethod.GET, "/countries/*").authenticated()
                                .requestMatchers("/countries/**").hasRole("ADMIN")
                                .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(this::configureExceptionHandling)
                .build();
    }

    private void configureExceptionHandling(ExceptionHandlingConfigurer<HttpSecurity> exceptionHandling) {
        exceptionHandling
                .accessDeniedHandler((request, response, accessDeniedException) ->
                        handlerExceptionResolver.resolveException(request, response, null,
                                new UnauthorizedException("Access denied: insufficient privileges, SC01")))

                .authenticationEntryPoint((request, response, authException) ->
                        handlerExceptionResolver.resolveException(request, response, null,
                                new UnauthenticatedException("Access denied: invalid credentials, SC02")));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
