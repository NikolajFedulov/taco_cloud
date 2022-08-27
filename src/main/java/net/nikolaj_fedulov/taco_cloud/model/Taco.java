package net.nikolaj_fedulov.taco_cloud.model;

import lombok.Data;

import java.util.List;

@Data
public class Taco {

    private String name;
    private List<Ingredient> ingredients;
}
