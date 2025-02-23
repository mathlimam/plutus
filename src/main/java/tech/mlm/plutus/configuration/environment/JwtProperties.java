package tech.mlm.plutus.configuration.environment;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="jwt")
public class JwtProperties {
    private String issuer;
    private String secretKey;

    public String getIssuer(){
        return this.issuer;
    }

    public void setIssuer(String issuer){
        this.issuer = issuer;
    }

    public String getSecretKey(){
        return this.secretKey;
    }

    public void setSecretKey(String secretKey){
        this.secretKey = secretKey;
    }
}
