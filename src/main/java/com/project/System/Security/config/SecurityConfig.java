/*
package com.project.System.Security.config;

import com.project.System.Security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserRepository userRepository;

    @Autowired
    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> (org.springframework.security.core.userdetails.UserDetails) userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException
                                ("Cannot find user with username " + username));
    }
    // Cấu hình PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }
}










//http.csrf()
//    .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/api/**"))  // Yêu cầu CSRF bảo vệ cho các endpoint API
//        .and()
//    .authorizeRequests()
//        .antMatchers("/api/auth/login").permitAll()
//        .anyRequest().authenticated();



//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    @Autowired
//    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
//        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
//    }
//    // Cấu hình bảo mật chính cho ứng dụng của bạn
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/auth/login").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }





// Cấu hình cho các trang quản lý (nếu có)
//    @Bean
//    public SecurityFilterChain managementSecurityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorizeRequests ->
//                        authorizeRequests
//                                .requestMatchers("/actuator/**").hasRole("ADMIN")  // Chỉ cho phép ADMIN truy cập vào các endpoint actuator
//                                .anyRequest().authenticated()  // Yêu cầu xác thực cho tất cả các request khác
//                );
//        return http.build();
//    }




















//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers("/api/branches/**")  // Bỏ qua CSRF cho /api/branches
//                )
//                .authorizeHttpRequests(auth -> auth
//                                .requestMatchers("/api/branches/**").permitAll() // Cho phép truy cập không cần xác thực vào /api/branches/**
//                                .anyRequest().authenticated() // Yêu cầu xác thực cho tất cả các yêu cầu khác
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/home", true)
//                )
//                .httpBasic(Customizer.withDefaults()); // Sử dụng xác thực cơ bản
//
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
//        UserDetails user = User.builder()
//                .username("user")
//                .password(passwordEncoder.encode("123456"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
//}
////        http
////                .csrf().disable()  // Tắt CSRF protection
////                .authorizeRequests()
////                .antMatchers("/api/branches/**").permitAll()  // Cho phép truy cập vào /api/branches mà không cần xác thực
////                .anyRequest().authenticated()  // Yêu cầu xác thực cho tất cả các yêu cầu khác
////                .and()
////                .formLogin()  // Sử dụng form login thay vì Basic Auth
////                .loginPage("/login")  // Cung cấp trang đăng nhập tùy chỉnh
////                .defaultSuccessUrl("/home", true)  // Chuyển hướng tới /home sau khi đăng nhập thành công
////                .and()
////                .httpBasic();  // Nếu bạn vẫn muốn sử dụng Basic Authentication cho API*/
