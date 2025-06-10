package code.alba.pharmapro;

import code.alba.pharmapro.models.Product;
import code.alba.pharmapro.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PharmaproApplication {

	public static void main(String[] args) {
		SpringApplication.run(PharmaproApplication.class, args);
	}
    
}
