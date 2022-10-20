package net.nikolaj_fedulov.taco_cloud.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Table
public class Ingredient {

    @Id
    private String id;

    private String name;

    @Column("INGREDIENT_TYPE")
    private Type type;
}
