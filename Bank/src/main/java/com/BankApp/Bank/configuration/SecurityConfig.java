//package com.BankApp.Bank.configuration;
//
//
//import com.BankApp.Bank.services.implementations.CustomerServiceImpl;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Autowired
//    private CustomerServiceImpl customerService;

//    @Bean
//    public static PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
//        httpSecurity
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(authz -> authz.requestMatchers("/register", "/login").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .loginProcessingUrl("/login")
//                        .defaultSuccessUrl("/dashboard", true)
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .invalidateHttpSession(true)
//                        .clearAuthentication(true)
//                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                        .logoutSuccessUrl("/login?logout")
//                        .permitAll()
//                )
//                .headers(header -> header
//                        .frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin())
//                );
//
//        return httpSecurity.build();
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(authz -> authz
//                        .requestMatchers("/login", "/register", "/css/**", "/js/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .loginProcessingUrl("/login")
//                        .defaultSuccessUrl("/dashboard", true)
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout")
//                        .permitAll()
//                );
//
//        return http.build();
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // Disable CSRF for APIs (not recommended for forms)
//                .authorizeHttpRequests(authz -> authz
//                        .requestMatchers("/api/auth/login", "/api/auth/register").permitAll() // Allow public access
//                        .anyRequest().authenticated() // Secure all other endpoints
//                )
//                .formLogin(form -> form
//                        .loginProcessingUrl("/api/auth/login") // Endpoint for login (Spring Security handles POST requests here)
//                        .successHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_OK)) // Custom success handler
//                        .failureHandler((req, res, ex) -> res.setStatus(HttpServletResponse.SC_UNAUTHORIZED)) // Custom failure handler
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/api/auth/logout") // Endpoint for logout
//                        .invalidateHttpSession(true) // Clear session on logout
//                        .clearAuthentication(true)
//                        .logoutSuccessHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_OK)) // Custom logout handler
//                );
//
//        return http.build();
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(authz -> authz
//                        .requestMatchers("/api/auth/register").permitAll()
//                        .anyRequest().authenticated()
//                );
////                .formLogin(form -> form
////                        .loginProcessingUrl("/api/auth/login")
////                        .successHandler((req, res, auth) -> res.setStatus(200))
////                        .failureHandler((req, res, ex) -> res.setStatus(401))
////                )
////                .logout(logout -> logout
////                        .logoutUrl("/api/auth/logout")
////                        .logoutSuccessHandler((req, res, auth) -> res.setStatus(200))
////                );
//        return http.build();
//    }

//    @Bean
//    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(customerService)
//                .passwordEncoder(passwordEncoder)
//                .and()
//                .build();
//    }

//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(customerService::findByUsername)
//                .passwordEncoder(passwordEncoder())
//                .and()
//                .build();
//    }
//}

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        authenticationManagerBuilder.userDetailsService(customerService).passwordEncoder(passwordEncoder());
//    }}
