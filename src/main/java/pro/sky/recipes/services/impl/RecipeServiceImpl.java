package pro.sky.recipes.services.impl;

import org.springframework.stereotype.Service;
import pro.sky.recipes.dto.RecipeDTO;
import pro.sky.recipes.model.Recipe;
import pro.sky.recipes.services.RecipeService;
import java.util.Map;
import java.util.TreeMap;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final Map<Integer, Recipe> recipeMap = new TreeMap<>();
    private int idGeneration = 1;
    private int id = idGeneration++;

    @Override
    public RecipeDTO addRecipe(Recipe recipe) {
        recipeMap.put(idGeneration, recipe);
        return RecipeDTO.from(id, recipe);
    }

    @Override
    public RecipeDTO getRecipe(int id) {
        if (recipeMap != null || !recipeMap.isEmpty()) {
            Recipe recipe = recipeMap.get(id);
            return RecipeDTO.from(id, recipe);
        } else {
            return null;
        }
    }
}
