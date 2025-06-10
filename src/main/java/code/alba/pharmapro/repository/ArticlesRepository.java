package code.alba.pharmapro.repository;

import code.alba.pharmapro.models.Articles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticlesRepository extends JpaRepository<Articles,Integer> {
}
