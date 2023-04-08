package pro.sky.recipes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.recipes.dto.RecipeDTO;
import pro.sky.recipes.model.Recipe;
import pro.sky.recipes.services.impl.RecipeServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/recipe")
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
                                    array = @ArraySchema(schema = @Schema(implementation = RecipeServiceImpl.class))
                            )
                    }
            )
    })
    public List<RecipeDTO> getAllRecipe() {
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

    @GetMapping("/byIngredient{name}")
    @Operation(
            summary = "getting recipes by ingredient",
            description = "we can get recipe use parameter name ingredient"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "The recipe received",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = RecipeServiceImpl.class))
                            )
                    }
            )
    )
    public List<RecipeDTO> getRecipeByIngredient(@PathVariable("name") String name) {
        return recipeService.getRecipeByIngredient(name);
    }

    @GetMapping("/byIngredients")
    @Operation(
            summary = "Getting the recipe by some ingredients",
            description = "We can get recipes by some parameters ingredients"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "The recipes received",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = RecipeServiceImpl.class))
                            )
                    }
            )
    )
    public List<RecipeDTO> getRecipeByIngredients(@RequestParam("name") List<String> names) {
        return recipeService.getRecipeByIngredients(names);
    }

    @GetMapping("/page/{pageNumber}")
    @Operation(
            summary = "Getting the page",
            description = "We can get the page by page number"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "The page received",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = RecipeServiceImpl.class))
                            )
                    }
            )
    )
    public List<RecipeDTO> getPage(@PathVariable("pageNumber") int pageNumber) {
        return recipeService.getPage(pageNumber);
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> addRecipeFromFile(@RequestParam MultipartFile file) {
        try (InputStream stream = file.getInputStream()) {
            recipeService.addRecipeFromInputStream(stream);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}