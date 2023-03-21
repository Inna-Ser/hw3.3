package pro.sky.recipes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import pro.sky.recipes.dto.RecipeDTO;
import pro.sky.recipes.model.Ingredients;
import pro.sky.recipes.model.Recipe;
import pro.sky.recipes.services.impl.RecipeServiceImpl;
import java.util.List;


@RestController
@RequestMapping("/recipe")
@Tag(name="Recipe", description = "The list of recipe")
public class RecipeController {
    private final RecipeServiceImpl recipeService;

    public RecipeController(RecipeServiceImpl recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "getting the recipe",
            description = "we can get the recipe use parameter id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The recipe has been got",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }

            )
    })
    public RecipeDTO getRecipe(@PathVariable("id") int id) {
        return recipeService.getRecipe(id);
    }

    

    @GetMapping(value = "/allRecipes")
    @Operation(
            summary = "getting all the recipes",
            description = "we can get up all the recipes without the parameters"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "All the recipes have been got",
                    content = {
                            @Content(
                                    mediaType = "appLication/json",
                                    array = @ArraySchema(schema = @Schema(implementation = RecipeController.class))
                            )
                    }
            )
    })

    private List<RecipeDTO> getAllRecipe() {
        return recipeService.getAllRecipe();
    }

    @PostMapping
    @Operation(
            summary = "adding the recipe",
            description = "we can add the recipe whithout parameters"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "The recipe has been added",
                    content = {
                            @Content(
                                    mediaType = "appLication/json"
                            )
                    }
            )
    )
    public RecipeDTO addRecipe(@RequestBody Recipe resipe) {
        return recipeService.addRecipe(resipe);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "aditing the recipe",
            description = "we can edit the recipe use parameter id"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "The recipe has been edit",
                    content = {
                            @Content(
                                    mediaType = "appLication/json"
                            )
                    }
            )
    )
    public RecipeDTO editRecipe(@PathVariable int id, Recipe recipe) {
        return recipeService.editRecipe(id, recipe);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "deleting the recipe",
            description = "we can delete the recipe use parameter id"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "The recipe has been delete",
                    content = {
                            @Content(
                                    mediaType = "appLication/json"
                            )
                    }
            )
    )
    public void deleteRecipe(@PathVariable int id) {
        recipeService.deleteRecipe(id);
    }
}

