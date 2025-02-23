package tech.mlm.plutus.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.lastbox.jwt.ExpirationTimeUnit;
import tech.lastbox.jwt.JwtAlgorithm;
import tech.lastbox.jwt.JwtConfig;
import tech.lastbox.jwt.JwtService;
import tech.mlm.plutus.configuration.environment.JwtProperties;
import tech.mlm.plutus.repositories.TokenRepository;

@Configuration
public class JwtInitializer {
    private final JwtProperties jwtProperties;
    private final TokenRepository tokenRepository;

    public JwtInitializer(JwtProperties jwtProperties, TokenRepository tokenRepository){
        this.jwtProperties = jwtProperties;
        this.tokenRepository = tokenRepository;
    }

    private JwtConfig getJwtConfig(){
        return new JwtConfig(JwtAlgorithm.HMAC256,
                             jwtProperties.getSecretKey(),
                             jwtProperties.getIssuer(),
                5,
                             ExpirationTimeUnit.DAYS,
                             tokenRepository);
    }

    @Bean
    public JwtService jwtService(){
        return new JwtService(getJwtConfig());
    }
}
