package models;

import lombok.Data;

import java.util.List;

@Data
public class Ingredient {
    private boolean success;
    private List<DataIngredient> data;
}
