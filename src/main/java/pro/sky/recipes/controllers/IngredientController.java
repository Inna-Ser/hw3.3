package pro.sky.recipes.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.recipes.dto.IngredientDTO;
import pro.sky.recipes.model.Ingredients;
import pro.sky.recipes.services.impl.IngredientsServiceImpl;


@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    private final IngredientsServiceImpl ingredientsService;

    public IngredientController(IngredientsServiceImpl ingredientsService) {
        this.ingredientsService = ingredientsService;
    }

    @GetMapping("/{id}")
    public IngredientDTO addRecipe(@RequestBody Ingredients ingredients) {
        return ingredientsService.addIngredient(ingredients);
    }
}
