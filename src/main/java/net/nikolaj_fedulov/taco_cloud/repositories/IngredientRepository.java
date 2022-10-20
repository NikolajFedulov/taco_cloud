package net.nikolaj_fedulov.taco_cloud.repositories;

import net.nikolaj_fedulov.taco_cloud.models.Ingredient;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

    @Modifying
    @Query ("INSERT INTO Ingredient (id, name, ingredient_type) VALUES (:1, :2, :3)")
    public void insertIngredient(
            @Param("1") String id,
            @Param("2") String name,
            @Param("3") String type);
}
