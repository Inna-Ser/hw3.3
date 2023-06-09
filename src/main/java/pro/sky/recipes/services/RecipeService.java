package pro.sky.recipes.services;

import pro.sky.recipes.dto.RecipeDTO;
import pro.sky.recipes.model.Recipe;

public interface RecipeService {

    RecipeDTO addRecipe(Recipe recipe);

    RecipeDTO getRecipe(int id);

}
