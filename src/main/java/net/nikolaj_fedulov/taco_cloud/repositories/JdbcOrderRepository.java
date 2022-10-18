package net.nikolaj_fedulov.taco_cloud.repositories;

import net.nikolaj_fedulov.taco_cloud.models.Ingredient;
import net.nikolaj_fedulov.taco_cloud.models.IngredientRef;
import net.nikolaj_fedulov.taco_cloud.models.Taco;
import net.nikolaj_fedulov.taco_cloud.models.TacoOrder;
import org.springframework.asm.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JdbcOrderRepository implements OrderRepository{

    private final JdbcOperations jdbcOperations;

    @Autowired
    public JdbcOrderRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    @Transactional
    public TacoOrder save(TacoOrder tacoOrder) {
        PreparedStatementCreatorFactory preparedStatementCreatorFactory = new PreparedStatementCreatorFactory(
                "insert into Taco_Order ("
                        + "delivery_name, "
                        + "delivery_street, "
                        + "delivery_city, "
                        + "delivery_state, "
                        + "delivery_zip, "
                        + "cc_number, "
                        + "cc_expiration, "
                        + "cc_cvv, "
                        + "placed_at) "
                        + "values (?,?,?,?,?,?,?,?,?)",
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.TIMESTAMP
        );
        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);

        tacoOrder.setPlacedAt(new Date());

        PreparedStatementCreator preparedStatementCreator = preparedStatementCreatorFactory.newPreparedStatementCreator(
                Arrays.asList(
                        tacoOrder.getDeliveryName(),
                        tacoOrder.getDeliveryStreet(),
                        tacoOrder.getDeliveryCity(),
                        tacoOrder.getDeliveryState(),
                        tacoOrder.getDeliveryZip(),
                        tacoOrder.getCcNumber(),
                        tacoOrder.getCcExpiration(),
                        tacoOrder.getCcCVV(),
                        tacoOrder.getPlacedAt()
                )
        );

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(preparedStatementCreator, generatedKeyHolder);
        long orderID = generatedKeyHolder.getKey().longValue();
        tacoOrder.setId(orderID);

        List<Taco> tacos = tacoOrder.getTacos();
        int i = 0;
        for (Taco taco: tacos) {
            saveTaco(orderID, i++, taco);
        }
        return tacoOrder;
    }

    private long saveTaco(Long orderID, int orderKey, Taco taco) {
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory preparedStatementCreatorFactory = new PreparedStatementCreatorFactory(
          "insert into Taco (" +
                  "name, " +
                  "created_at, " +
                  "taco_order, " +
                  "taco_order_key) " +
                  "values (?, ?, ?, ?)",
                Types.VARCHAR,
                Types.TIMESTAMP,
                Type.LONG,
                Type.LONG
        );
        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);

        PreparedStatementCreator preparedStatementCreator = preparedStatementCreatorFactory.newPreparedStatementCreator(
                Arrays.asList(
                        taco.getName(),
                        taco.getCreatedAt(),
                        orderID,
                        orderKey
                )
        );

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(preparedStatementCreator, generatedKeyHolder);
        long tacoID = generatedKeyHolder.getKey().longValue();
        taco.setId(tacoID);

//      this method use with saveIngredientRefs(long tacoID, List<Ingredient> ingredients)
//        saveIngredientRefs(tacoID, taco.getIngredients());

        List<Ingredient> ingredients = taco.getIngredients();
        int i = 0;
        for (Ingredient ingredient: ingredients) {
            saveIngredientRefs(tacoID, i++, ingredient);
        }

        return tacoID;
    }

//    this option is specified in the book
//
//    private void saveIngredientRefs(long tacoID, List<IngredientRef> ingredientRefs) {
//        int key = 0;
//        for (IngredientRef ingredientRef : ingredientRefs) {
//            jdbcOperations.update(
//                    "insert into Ingredient_Ref (" +
//                            "ingredient, " +
//                            "taco, " +
//                            "taco_key) " +
//                            "values (?, ?, ?)",
//                    ingredientRef.getIngredient(),
//                    tacoID,
//                    key++
//            );
//        }
//    }

//  this method use with saveIngredientRefs(tacoID, taco.getIngredients());
    private void saveIngredientRefs(long tacoID, List<Ingredient> ingredients) {
        int key = 0;
        for (Ingredient ingredient : ingredients) {
            jdbcOperations.update(
                    "insert into Ingredient_Ref (" +
                            "ingredient, " +
                            "taco, " +
                            "taco_key) " +
                            "values (?, ?, ?)",
                    ingredient.getId(),
                    tacoID,
                    key++
            );
        }
    }

    private void saveIngredientRefs(long tacoID, int tacoKey, Ingredient ingredient) {
        jdbcOperations.update(
                "insert into Ingredient_Ref (" +
                        "ingredient, " +
                        "taco, " +
                        "taco_key) " +
                        "values (?, ?, ?)",
                ingredient.getId(),
                tacoID,
                tacoKey
        );
    }

    private List<IngredientRef> ingredientListToIngredientRefList (List<Ingredient> ingredientList) {
        return ingredientList.
                stream().
                map(x -> new IngredientRef(x.getId())).
                collect(Collectors.toList());
    }
}
