package pro.sky.recipes.services;

import pro.sky.recipes.dto.IngredientDTO;
import pro.sky.recipes.model.Ingredients;


public interface IngredientsService {

    IngredientDTO addIngredient(Ingredients ingredients);

    IngredientDTO getIngredient(int id);

    IngredientDTO editIngredient(int id, Ingredients ingredients);

    boolean deleteIngredient(int id);

    IngredientDTO getAllIngredient();
}
