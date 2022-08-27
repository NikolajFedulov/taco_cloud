package net.nikolaj_fedulov.taco_cloud.model;

import lombok.Data;

@Data
public class Ingredient {

    private final String id;
    private final String name;
    private final Type type;
}
