package tech.mlm.plutus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan(basePackages={"tech.mlm.plutus.entities","tech.lastbox.jwt"})
public class PlutusApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlutusApplication.class, args);
	}
}
