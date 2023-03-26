package pro.sky.recipes.controllers;

import org.springframework.stereotype.Service;

@Service
public class RecipeServieController {
    private final RecipeServieController recipeServieController;

    public RecipeServieController(RecipeServieController recipeServieController) {
        this.recipeServieController = recipeServieController;
    }
}
