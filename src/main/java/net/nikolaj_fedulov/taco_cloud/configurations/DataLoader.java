package net.nikolaj_fedulov.taco_cloud.configurations;

import net.nikolaj_fedulov.taco_cloud.models.Ingredient;
import net.nikolaj_fedulov.taco_cloud.models.Type;
import net.nikolaj_fedulov.taco_cloud.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataLoader implements CommandLineRunner{

    private final IngredientRepository ingredientRepository;

    @Autowired
    public DataLoader(IngredientRepository ingredientRepository) throws Exception {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Ingredient> ingredientList = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );
        for (Ingredient ingredients: ingredientList) {
            ingredientRepository.insertIngredient(ingredients.getId(), ingredients.getName(), ingredients.getType().toString());
        }
    }
}
