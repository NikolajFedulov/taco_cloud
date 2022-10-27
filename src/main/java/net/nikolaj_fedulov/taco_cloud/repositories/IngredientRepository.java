package net.nikolaj_fedulov.taco_cloud.repositories;

import net.nikolaj_fedulov.taco_cloud.models.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
