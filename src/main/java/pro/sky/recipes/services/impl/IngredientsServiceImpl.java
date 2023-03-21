package pro.sky.recipes.services.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pro.sky.recipes.dto.IngredientDTO;
import pro.sky.recipes.model.Ingredients;
import pro.sky.recipes.services.IngredientsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class IngredientsServiceImpl implements IngredientsService {
    private int idGeneration = 1;
    private int id = idGeneration++;
    private final Map<Integer, Ingredients> ingredientsMap = new TreeMap<Integer, Ingredients>();

    @Override
    public IngredientDTO addIngredient(Ingredients ingredients) {
        StringUtils.isBlank((CharSequence) ingredients);
        ingredientsMap.put(id, ingredients);
        return IngredientDTO.from(id, ingredients);
    }

    @Override
    public IngredientDTO getIngredient(int id) {
        StringUtils.isBlank((CharSequence) ingredientsMap);
            Ingredients ingredients = ingredientsMap.get(id);
            return IngredientDTO.from(id, ingredients);
    }

    @Override
    public IngredientDTO editIngredient(int id, Ingredients ingredients) {
        StringUtils.isBlank((CharSequence) ingredientsMap);
            ingredientsMap.put(id, ingredients);
            return IngredientDTO.from(id, ingredients);
    }

    @Override
    public boolean deleteIngredient(int id) {
        StringUtils.isBlank((CharSequence) ingredientsMap);
            ingredientsMap.remove(id);
            return true;
    }

    @Override
    public List<IngredientDTO> getAllIngredient() {
        List<IngredientDTO> ingredientDTOList = new ArrayList<>();
        for (Map.Entry<Integer, Ingredients> entry: ingredientsMap.entrySet()) {
            ingredientDTOList.add(IngredientDTO.from(entry.getKey(), entry.getValue()));
        }
        return ingredientDTOList;
    }
}

