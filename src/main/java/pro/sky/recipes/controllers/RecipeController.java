package pro.sky.recipes.controllers;

import org.springframework.web.bind.annotation.*;
import pro.sky.recipes.dto.RecipeDTO;
import pro.sky.recipes.model.Recipe;
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

    @PostMapping
    public RecipeDTO addRecipe(@RequestBody Recipe resipe) {
        return recipeService.addRecipe(resipe);
    }
}
