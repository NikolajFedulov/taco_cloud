package net.nikolaj_fedulov.taco_cloud.repositories;

import net.nikolaj_fedulov.taco_cloud.models.Ingredient;

import java.util.Optional;

public interface IngredientRepository {

    Iterable<Ingredient> findAll();
    Optional<Ingredient> findById(String id);
    Ingredient save(Ingredient ingredient);
}
