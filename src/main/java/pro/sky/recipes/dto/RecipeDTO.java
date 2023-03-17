package pro.sky.recipes.dto;

import pro.sky.recipes.model.Ingredients;
import pro.sky.recipes.model.Recipe;
import java.util.List;

public class RecipeDTO {
    private final String name;
    private final int cookingTime;
    private final List<Ingredients> ingredients;
    private final List<String> instruction;
    private final int id;

    public RecipeDTO(int id, String name, int cookingTime, List<Ingredients> ingredients, List<String> instruction) {
        this.name = name;
        this.cookingTime = cookingTime;
        this.ingredients = ingredients;
        this.instruction = instruction;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public List<String> getInstruction() {
        return instruction;
    }

    public int getId() {
        return id;
    }

    public static RecipeDTO from(int id, Recipe recipe) {
        return new RecipeDTO(id, recipe.getName(), recipe.getCookingTime(), recipe.getIngredients(), recipe.getInstruction());
    }
}
