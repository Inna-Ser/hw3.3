package pro.sky.recipes.services.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pro.sky.recipes.dto.RecipeDTO;
import pro.sky.recipes.model.Recipe;
import pro.sky.recipes.services.RecipeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final Map<Integer, Recipe> recipeMap = new TreeMap<>();
    private int idGeneration = 1;
    private int id = idGeneration++;

    @Override
    public RecipeDTO addRecipe(Recipe recipe) {
        StringUtils.isBlank((CharSequence) recipe);
        recipeMap.put(idGeneration, recipe);
        return RecipeDTO.from(id, recipe);
    }

    @Override
    public RecipeDTO getRecipe(int id) {
        StringUtils.isBlank((CharSequence) recipeMap);
            Recipe recipe = recipeMap.get(id);
            return RecipeDTO.from(id, recipe);
    }

    @Override
    public RecipeDTO editRecipe(int id, Recipe recipe) {
        StringUtils.isBlank((CharSequence) recipeMap);
            recipeMap.put(id, recipe);
            return RecipeDTO.from(id, recipe);
    }

    @Override
    public boolean deleteRecipe(int id) {
        StringUtils.isBlank((CharSequence) recipeMap);
        if (recipeMap.containsKey(id)) {
            recipeMap.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public List<RecipeDTO> getAllRecipe() {
        List<RecipeDTO> recipeDTOList = new ArrayList<>();
        for (Map.Entry<Integer, Recipe> entry : recipeMap.entrySet()) {
            recipeDTOList.add(RecipeDTO.from(entry.getKey(),entry.getValue()));
        }
        return recipeDTOList;
    }
}
