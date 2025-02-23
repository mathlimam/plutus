package tech.mlm.plutus.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import tech.lastbox.lastshield.security.SecurityConfig;

import java.util.List;

@Configuration
@ComponentScan(basePackages="tech.lastbox.lastshield")
public class SecurityConfigInitializer {
    public SecurityConfigInitializer(SecurityConfig securityConfig){

        securityConfig.setCsrfProtection(false)
                      .corsAllowCredentials(true)
                      .corsAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"))
                      .corsAllowedHeaders(List.of("*"))
                      .corsAllowedOrigins(List.of("*"));

        securityConfig.addRouteAuthority("/auth/**")
                      .addRouteAuthority("/stores/**")
                      .addRouteAuthority("/sellers/**")
                      .addRouteAuthority("/operations/**")
                      .build();
    }
}
