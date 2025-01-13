package com.project.System.Security.config;

import com.project.System.Security.components.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.*;


@Configuration
@EnableWebSecurity
@EnableWebMvc
public class WebSecurityConfig {
    @Value("${api.prefix}")
    private String apiPrefix;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public WebSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable) // Tắt CSRF
//                .cors(cors -> {
//                    CorsConfiguration corsConfiguration = new CorsConfiguration();
//                    corsConfiguration.setAllowedOrigins(List.of("http://localhost:4300")); // Địa chỉ frontend của bạn
//                    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//                    corsConfiguration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
//                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//                    source.registerCorsConfiguration("/**", corsConfiguration);
//                    cors.configurationSource(source);
//                })
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Thêm filter JWT
//                .authorizeHttpRequests(requests -> {
//                    requests
//                            .requestMatchers(
//                                    String.format("%s/users/login", apiPrefix),
//                                    String.format("%s/users/register", apiPrefix)
//                            )
//                            .permitAll() // Cho phép truy cập không cần xác thực
//                            .requestMatchers(POST,
//                                    String.format("%s/branches/**", apiPrefix)).hasRole("USER")
//                            .requestMatchers(GET,
//                                    String.format("%s/branches/**", apiPrefix)).hasRole("USER")
//                            .requestMatchers(PUT,
//                                    String.format("%s/branches/**", apiPrefix)).hasRole("USER")
//                            .requestMatchers(DELETE,
//                                    String.format("%s/branches/**", apiPrefix)).hasRole("USER")
//                            .anyRequest().authenticated(); // Các yêu cầu khác cần xác thực
//                });
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().and() // Đảm bảo CORS được cấu hình
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests
                            .requestMatchers(
                                    String.format("%s/users/login", apiPrefix),
                                    String.format("%s/users/register", apiPrefix),
                                    String.format("%s/branches", apiPrefix),
                                    String.format("%s/branches/**", apiPrefix)
                            )
                            .permitAll() // Cho phép truy cập không cần xác thực
                            .anyRequest().authenticated(); // Các yêu cầu khác cần xác thực
                });
        http
                .csrf(AbstractHttpConfigurer::disable) // Tắt CSRF
                .cors(cors -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(List.of("http://localhost:4300")); // Địa chỉ frontend của bạn
                    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                    corsConfiguration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", corsConfiguration);
                    cors.configurationSource(source);
                });

        return http.build();
    }

}