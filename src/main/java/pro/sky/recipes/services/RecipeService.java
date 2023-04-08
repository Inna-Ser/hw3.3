package pro.sky.recipes.services;

import pro.sky.recipes.dto.RecipeDTO;
import pro.sky.recipes.model.Recipe;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public interface RecipeService {

    RecipeDTO addRecipe(Recipe recipe);

    RecipeDTO getRecipe(int id);

    RecipeDTO editRecipe(int id, Recipe recipe);

    boolean deleteRecipe(int id);

    List<RecipeDTO> getAllRecipe();

    List<RecipeDTO> getRecipeByIngredient(String ingredientName);

    List<RecipeDTO> getRecipeByIngredients(List<String> ingredientsList);

    List<RecipeDTO> getPage(int pageNumber);

    void saveToFile();

    void readFromFile();
}
