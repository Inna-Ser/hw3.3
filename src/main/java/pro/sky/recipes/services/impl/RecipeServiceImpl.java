package pro.sky.recipes.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pro.sky.recipes.dto.IngredientDTO;
import pro.sky.recipes.dto.RecipeDTO;
import pro.sky.recipes.model.Ingredients;
import pro.sky.recipes.model.Recipe;
import pro.sky.recipes.services.RecipeService;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final FileRecipeServiceImpl fileService;
    private int idGeneration = 1;
    private final int id = idGeneration++;
    private final IngredientsServiceImpl ingredientsService;
    private Map<Integer, Recipe> recipeMap = new TreeMap<>();

    public RecipeServiceImpl(FileRecipeServiceImpl fileService, IngredientsServiceImpl ingredientsService) {
        this.fileService = fileService;
        this.ingredientsService = ingredientsService;
    }

    @PostConstruct
    private void unit() {
        readFromFile();
    }

    @Override
    public RecipeDTO addRecipe(Recipe recipe) {
        if (StringUtils.isBlank("")) {
            return null;
        } else {
            recipeMap.put(idGeneration, recipe);
            for (Ingredients ingredients : recipe.getIngredients()) {
                this.ingredientsService.addIngredient(ingredients);
                saveToFile();
            }
            return RecipeDTO.from(id, recipe);
        }
    }

    @Override
    public RecipeDTO getRecipe(int id) {
        if (StringUtils.isBlank("")) {
            return null;
        } else {
            Recipe recipe = recipeMap.get(id);
            return RecipeDTO.from(id, recipe);
        }
    }

    @Override
    public RecipeDTO editRecipe(int id, Recipe recipe) {
        if (StringUtils.isBlank("")) {
            return null;
        } else {
            recipeMap.put(id, recipe);
            saveToFile();
            return RecipeDTO.from(id, recipe);
        }
    }

    @Override
    public boolean deleteRecipe(int id) {
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
            recipeDTOList.add(RecipeDTO.from(entry.getKey(), entry.getValue()));
        }
        return recipeDTOList;
    }

    @Override
    public List<RecipeDTO> getRecipeByIngredient(String ingredientName) {
        IngredientDTO ingredients = this.ingredientsService.getIngredient(Integer.parseInt(ingredientName));
        if (StringUtils.isBlank("")) {
            return null;
        } else {
            return this.recipeMap.entrySet().stream()
                    .filter(e -> e.getValue().getIngredients().stream()
                            .anyMatch(i -> i.getName().equals(ingredients.getName())))
                    .map(e -> RecipeDTO.from(e.getKey(), e.getValue()))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<RecipeDTO> getRecipeByIngredients(List<String> ingredientsList) {
//        List<IngredientDTO> ingredients = ingredientsList.stream()
//                .map(this.ingredientsService::getIngredient)
//                .filter(Objects::nonNull)
//                .collect(Collectors.toList());
//        return this.recipeMap.entrySet()
//                .stream()
//                .filter(e -> {
//                    Set<String> recipeIngredientsName = e.getValue()
//                            .getIngredients()
//                            .stream()
//                            .map(i -> i.getName())
//                            .collect(Collectors.toSet());
//                    return new HashSet<>(recipeIngredientsName).containsAll(ingredientsList);
//                })
//                .map(e -> RecipeDTO.from(e.getKey(), e.getValue()))
//                .collect(Collectors.toList());
        return null;
    }

    @Override
    public List<RecipeDTO> getPage(int pageNumber) {
        return this.getAllRecipe()
                .stream()
                .skip(pageNumber = 1 * 10)
                .limit(10)
                .collect(Collectors.toList());
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipeMap);
            fileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromFile() {
        try {
            String json = fileService.readFromFile();
            recipeMap = new ObjectMapper().readValue(json, new TypeReference<TreeMap<Integer, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
