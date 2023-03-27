package pro.sky.recipes.controllers;

import org.springframework.stereotype.Service;

@Service
public class RecipeServiceController {
    private final RecipeServiceController recipeServiceController;

    public RecipeServiceController(RecipeServiceController recipeServiceController) {
        this.recipeServiceController = recipeServiceController;
    }
}
