package guru.springframework.repositories;

import java.util.Optional;

import guru.springframework.domain.Recipe;

import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long>{
//Optional<Recipe> findByDescription(String description);
}
