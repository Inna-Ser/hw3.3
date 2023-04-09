package pro.sky.recipes.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.Category;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pro.sky.recipes.dto.IngredientDTO;
import pro.sky.recipes.dto.RecipeDTO;
import pro.sky.recipes.model.Ingredients;
import pro.sky.recipes.model.Recipe;
import pro.sky.recipes.services.FileService;
import pro.sky.recipes.services.RecipeService;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class RecipeServiceImpl implements RecipeService {

    private final FileService fileService;
    private final IngredientsServiceImpl ingredientsService;
    private TreeMap<Integer, Recipe> recipeMap = new TreeMap<>();
    private int idGeneration = 1;

    public RecipeServiceImpl(FileRecipeImpl fileService, IngredientsServiceImpl ingredientsService) {
        this.fileService = fileService;
        this.ingredientsService = ingredientsService;
    }

    @PostConstruct
    private void unit() {
        readFromFile();
    }

    @Override
    public RecipeDTO addRecipe(Recipe recipe) {
        if (StringUtils.isBlank(recipe.getName())) {
            return null;
        } else {
            int id = idGeneration++;
            recipeMap.put(id, recipe);
            saveToFile();
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
            saveToFile();
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
//        IngredientDTO ingredients = this.ingredientsService.getIngredient(Integer.parseInt(ingredientName));
//        if (StringUtils.isBlank("")) {
//            return null;
//        } else {
//            return this.recipeMap.entrySet().stream()
//                    .filter(e -> e.getValue().getIngredients().stream()
//                            .anyMatch(i -> i.getName().equals(ingredients.getName())))
//                    .map(e -> RecipeDTO.from(e.getKey(), e.getValue()))
//                    .collect(Collectors.toList());
//        }
        return null;
    }

    public void addRecipeStringFormat(PrintWriter writer) throws IOException{
        for (Recipe recipe : this.recipeMap.values()) {
            writer.println(recipe.getName());
            writer.println("The cooking time: " + recipe.getCookingTime());
            writer.println("The ingredients: ");
            for (Ingredients ingredients : recipe.getIngredients()) {
                writer.println("\t" + idGeneration + ingredients.getName() +
                        "Count:" + ingredients.getCount() +
                        "Measure: " + ingredients.getUnitOfMeasurement());
            }
            writer.println("The instruction");
            for (String recipe1 : recipe.getInstruction()) {
                writer.println(recipe.getInstruction());
            }
        }
        writer.flush();
    }

    @Override
    public List<RecipeDTO> getRecipeByIngredients(List<String> ingredientsList) {
        return recipeMap.entrySet()
                .stream()
                .filter(e -> {
                    var names = e.getValue().getIngredients().stream().map(Ingredients::getName)
                            .collect(Collectors.toList());
                    return names.containsAll(ingredientsList);
                })
                .map(e -> RecipeDTO.from(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<RecipeDTO> getPage(int pageNumber) {
        return this.getAllRecipe()
                .stream()
                .skip(pageNumber = 1 * 10)
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipeMap);
            fileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void readFromFile() {
        try {
            String json = fileService.readFromFile();
            if (json == null || json.isBlank()) {
                recipeMap = new TreeMap<Integer, Recipe>();
            } else {
                recipeMap = new ObjectMapper().readValue(json, new TypeReference<TreeMap<Integer, Recipe>>() {
                });
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
