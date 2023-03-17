package pro.sky.recipes.dto;

import pro.sky.recipes.model.Ingredients;

public class IngredientDTO {
    private final String name;
    private final int count;
    private final String unitOfMeasurement;
    private final int id;
    private static Ingredients ingredients;

    public IngredientDTO(int id, String name, int count, String unitOfMeasurement) {
        this.name = name;
        this.count = count;
        this.unitOfMeasurement = unitOfMeasurement;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public int getId() {
        return id;
    }

    public static IngredientDTO from(int id, Ingredients ingredients) {
        return new IngredientDTO(id, ingredients.getName(), ingredients.getCount(), ingredients.getUnitOfMeasurement());
    }
}
