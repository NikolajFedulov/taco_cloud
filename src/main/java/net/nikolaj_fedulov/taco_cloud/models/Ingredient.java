package net.nikolaj_fedulov.taco_cloud.models;

import lombok.Data;

@Data
public class Ingredient {

    private final String id;
    private final String name;
    private final Type type;
}
