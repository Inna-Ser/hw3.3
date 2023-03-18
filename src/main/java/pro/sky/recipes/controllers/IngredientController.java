package pro.sky.recipes.controllers;

import org.springframework.web.bind.annotation.*;
import pro.sky.recipes.dto.IngredientDTO;
import pro.sky.recipes.dto.RecipeDTO;
import pro.sky.recipes.model.Ingredients;
import pro.sky.recipes.model.Recipe;
import pro.sky.recipes.services.impl.IngredientsServiceImpl;


@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    private final IngredientsServiceImpl ingredientsService;

    public IngredientController(IngredientsServiceImpl ingredientsService) {
        this.ingredientsService = ingredientsService;
    }

    @GetMapping("/{id}")
    public IngredientDTO getIngredient(@PathVariable("id") int id) {
        return ingredientsService.getIngredient(id);
    }

    @GetMapping
    public IngredientDTO getAllIngredients() {
        return ingredientsService.getAllIngredient();
    }

    @GetMapping("/{id}")
    public IngredientDTO addRecipe(@RequestBody Ingredients ingredients) {
        return ingredientsService.addIngredient(ingredients);
    }

    @PutMapping("/{id}")
    public IngredientDTO editIngredient(@PathVariable int id, Ingredients ingredients) {
        return ingredientsService.editIngredient(id, ingredients);
    }

    @DeleteMapping("/id")
    public void deleteIngredient(@PathVariable int id) {
        ingredientsService.deleteIngredient(id);
    }
}

