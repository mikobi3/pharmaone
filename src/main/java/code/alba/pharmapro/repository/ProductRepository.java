package code.alba.pharmapro.repository;

import code.alba.pharmapro.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {

}
