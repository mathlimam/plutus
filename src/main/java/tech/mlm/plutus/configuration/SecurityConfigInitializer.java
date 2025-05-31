package tech.mlm.plutus.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class SecurityConfigInitializer {
    public static final String API_PREFIX = "/api/v1";
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .headers(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(API_PREFIX + "/auth/register").permitAll()
                    .requestMatchers(API_PREFIX + "/auth/login").permitAll()
                    .anyRequest().permitAll())
            .httpBasic(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
