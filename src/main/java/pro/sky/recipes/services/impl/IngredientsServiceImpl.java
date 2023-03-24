package pro.sky.recipes.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pro.sky.recipes.dto.IngredientDTO;
import pro.sky.recipes.model.Ingredients;
import pro.sky.recipes.services.IngredientsService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class IngredientsServiceImpl implements IngredientsService {
    private final FileIngredientServiceImpl fileService;
    private int idGeneration = 1;
    private int id = idGeneration++;
    private TreeMap<Integer, Ingredients> ingredientsMap = new TreeMap<Integer, Ingredients>();

    public IngredientsServiceImpl(FileIngredientServiceImpl fileService) {
        this.fileService = fileService;
    }

    @PostConstruct
    private void unit() {
        readFromFile();
    }

    @Override
    public IngredientDTO addIngredient(Ingredients ingredients) {
        if (StringUtils.isBlank("")) {
            return null;
        } else {
            ingredientsMap.put(id, ingredients);
            return IngredientDTO.from(id, ingredients);
        }
    }

    @Override
    public IngredientDTO getIngredient(int id) {
        if (StringUtils.isBlank("")) {
            return null;
        } else {
            Ingredients ingredients = ingredientsMap.get(id);
            return IngredientDTO.from(id, ingredients);
        }
    }

    @Override
    public IngredientDTO editIngredient(int id, Ingredients ingredients) {
        if (StringUtils.isBlank("")) {
            return null;
        } else {
            ingredientsMap.put(id, ingredients);
            return IngredientDTO.from(id, ingredients);
        }
    }

    @Override
    public boolean deleteIngredient(int id) {
        if (StringUtils.isBlank("")) {
            return false;
        } else {
            ingredientsMap.remove(id);
            return true;
        }
    }

    @Override
    public List<IngredientDTO> getAllIngredient() {
        List<IngredientDTO> ingredientDTOList = new ArrayList<>();
        for (Map.Entry<Integer, Ingredients> entry : ingredientsMap.entrySet()) {
            ingredientDTOList.add(IngredientDTO.from(entry.getKey(), entry.getValue()));
        }
        return ingredientDTOList;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientsMap);
            fileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        try {
            String json = fileService.readFromFile();
            ingredientsMap = new ObjectMapper().readValue(json, new TypeReference<TreeMap<Integer, Ingredients>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

