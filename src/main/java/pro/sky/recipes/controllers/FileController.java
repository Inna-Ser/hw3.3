package pro.sky.recipes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.recipes.dto.IngredientDTO;
import pro.sky.recipes.dto.RecipeDTO;
import pro.sky.recipes.model.Ingredients;
import pro.sky.recipes.model.Recipe;
import pro.sky.recipes.services.impl.FileIngredientServiceImpl;
import pro.sky.recipes.services.impl.FileServiceImpl;
import pro.sky.recipes.services.impl.IngredientsServiceImpl;
import pro.sky.recipes.services.impl.RecipeServiceImpl;

import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileServiceImpl fileService;
    private final FileIngredientServiceImpl fileIngredientService;

    private final RecipeServiceImpl recipeService;

    private final IngredientsServiceImpl ingredientsService;

    public FileController(FileServiceImpl fileService, FileIngredientServiceImpl fileIngredientService, RecipeServiceImpl recipeService, IngredientsServiceImpl ingredientsService) {
        this.fileService = fileService;
        this.fileIngredientService = fileIngredientService;
        this.recipeService = recipeService;
        this.ingredientsService = ingredientsService;
    }

    @GetMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InputStreamResource> downloadDateFile() throws FileNotFoundException {
        File file = fileService.getDataFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment, filename=\"Recipe.json")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadDateFile(@RequestParam MultipartFile file) {
        fileService.cleanDateFile();
        File dateFile = fileService.getDataFile();

        try {
            FileOutputStream fos = new FileOutputStream(dateFile);
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
            summary = "editing the ingredient",
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
