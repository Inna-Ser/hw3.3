package pro.sky.recipes.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


public class Recipe {
    private String name;
    private int cookingTime;
    private List<Ingredients> ingredients;
    private List<String> instruction;

    public Recipe(String name, int cookingTime, List<Ingredients> ingredients, List<String> instruction) {
        this.name = name;
        this.cookingTime = cookingTime;
        this.ingredients = ingredients;
        this.instruction = instruction;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return cookingTime == recipe.cookingTime && Objects.equals(name, recipe.name) && Objects.equals(ingredients, recipe.ingredients) && Objects.equals(instruction, recipe.instruction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cookingTime, ingredients, instruction);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", cookingTime=" + cookingTime +
                ", ingredients=" + ingredients +
                ", instruction=" + instruction +
                '}';
    }
}
