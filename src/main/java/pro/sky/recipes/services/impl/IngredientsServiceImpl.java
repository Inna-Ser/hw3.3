package pro.sky.recipes.services.impl;

import org.springframework.stereotype.Service;
import pro.sky.recipes.dto.IngredientDTO;
import pro.sky.recipes.model.Ingredients;
import pro.sky.recipes.services.IngredientsService;

import java.util.Map;
import java.util.TreeMap;

@Service
public class IngredientsServiceImpl implements IngredientsService {
    private int idGeneration = 1;
    private int id = idGeneration++;
    private final Map<Integer, Ingredients> ingredientsMap = new TreeMap<Integer, Ingredients>();

    @Override
    public IngredientDTO addIngredient(Ingredients ingredients) {
        ingredientsMap.put(id, ingredients);
        return IngredientDTO.from(id, ingredients);
    }

    @Override
    public IngredientDTO getIngredient(int id) {
        if (ingredientsMap != null || !ingredientsMap.isEmpty()) {
            Ingredients ingredients = ingredientsMap.get(id);
            return IngredientDTO.from(id, ingredients);
        } else {
            return null;
        }
    }

    @Override
    public IngredientDTO editIngredient(int id, Ingredients ingredients) {
        if (ingredientsMap.containsKey(id)) {
            ingredientsMap.put(id, ingredients);
            return IngredientDTO.from(id, ingredients);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteIngredient(int id) {
        if (ingredientsMap.containsKey(id)) {
            ingredientsMap.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public IngredientDTO getAllIngredient() {
        for (Ingredients ingredients : ingredientsMap.values()){
            Ingredients ingredients1 = ingredientsMap.get(id);
        }
        return (IngredientDTO) ingredientsMap;
    }
}
