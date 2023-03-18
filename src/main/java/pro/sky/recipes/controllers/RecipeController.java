package pro.sky.recipes.controllers;

import org.springframework.web.bind.annotation.*;
import pro.sky.recipes.dto.RecipeDTO;
import pro.sky.recipes.model.Recipe;
import pro.sky.recipes.services.RecipeService;
import pro.sky.recipes.services.impl.RecipeServiceImpl;


@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeServiceImpl recipeService;

    public RecipeController(RecipeServiceImpl recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public RecipeDTO getRecipe(@PathVariable("id") int id) {
        return recipeService.getRecipe(id);
    }

    @GetMapping
    public RecipeDTO getAllRecipe() {
        return recipeService.getAllRecipe();
    }

    @PostMapping
    public RecipeDTO addRecipe(@RequestBody Recipe resipe) {
        return recipeService.addRecipe(resipe);
    }

    @PutMapping("/{id}")
    public RecipeDTO editRecipe(@PathVariable int id, Recipe recipe) {
        return recipeService.editRecipe(id, recipe);
    }

    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable int id) {
        recipeService.deleteRecipe(id);
    }
}

