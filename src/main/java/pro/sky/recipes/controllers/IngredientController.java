package pro.sky.recipes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import pro.sky.recipes.dto.IngredientDTO;
import pro.sky.recipes.model.Ingredients;
import pro.sky.recipes.services.impl.IngredientsServiceImpl;

import java.util.List;


@RestController
@RequestMapping("/ingredient")
@Tag(name = "Ingredients", description = "The list and methods of ingredients")
public class IngredientController {
    private final IngredientsServiceImpl ingredientsService;

    public IngredientController(IngredientsServiceImpl ingredientsService) {
        this.ingredientsService = ingredientsService;
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "getting the ingredient",
            description = "we can get the ingredient use parameter id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "The ingredient has been got",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }

            )
    })
    public IngredientDTO getIngredient(@PathVariable("id") int id) {
        return ingredientsService.getIngredient(id);
    }

    @GetMapping(value = "/allIngregient")
    @Operation(
            summary = "getting all the ingredient used",
            description = "we can get up all the ingredients without the parameters"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "All the ingredients have been got",
                    content = {
                            @Content(
                                    mediaType = "appLication/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ingredients.class))
                            )
                    }
            )
    })

    public List<IngredientDTO> getAllIngredients() {
        return ingredientsService.getAllIngredient();
    }

    @PostMapping
    @Operation(
            summary = "adding the ingredient",
            description = "we can add the ingredient whithout parameters"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "The ingredient has been added",
                    content = {
                            @Content(
                                    mediaType = "appLication/json"
                            )
                    }
            )
    )
    public IngredientDTO addRecipe(@RequestBody Ingredients ingredients) {
        return ingredientsService.addIngredient(ingredients);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "aditing the ingredient",
            description = "we can edit the ingredient use parameter id"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "The ingredient has been edit",
                    content = {
                            @Content(
                                    mediaType = "appLication/json"
                            )
                    }
            )
    )
    public IngredientDTO editIngredient(@PathVariable int id, Ingredients ingredients) {
        return ingredientsService.editIngredient(id, ingredients);
    }

    @DeleteMapping("/id")
    @Operation(
            summary = "deleting the ingredient",
            description = "we can delete the ingredient use parameter id"
    )
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "The ingredient has been delete",
                    content = {
                            @Content(
                                    mediaType = "appLication/json"
                            )
                    }
            )
    )
    public void deleteIngredient(@PathVariable int id) {
        ingredientsService.deleteIngredient(id);
    }
}

