package pro.sky.recipes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.recipes.services.impl.FileIngredientImpl;
import pro.sky.recipes.services.impl.FileRecipeImpl;
import pro.sky.recipes.services.impl.RecipeServiceImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/files")
public class FileController {

    private FileRecipeImpl recipeFileService;
    private FileIngredientImpl ingredientFileService;
    private RecipeServiceImpl recipeService;

    public FileController(FileRecipeImpl recipeFileService, FileIngredientImpl ingredientFileService) {
        this.recipeFileService = recipeFileService;
        this.ingredientFileService = ingredientFileService;
    }


    @GetMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "download recipe file",
            description = "we have to select a file to download"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "file has been downloaded",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            )
    })
    public ResponseEntity<InputStreamResource> downloadDateFile() throws FileNotFoundException {
        File file = recipeFileService.getDataFile();
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

    @PostMapping(value = "/ingredient/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "upload ingredient file",
            description = "we have to select a file to upload"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "file has been uploaded",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            )
    })
    public ResponseEntity<Void> uploadDateFileIngredient(@RequestParam MultipartFile file) {
        ingredientFileService.cleanDateFile();
        File dateFile = ingredientFileService.getDataFile();
        try {
            FileOutputStream fos = new FileOutputStream(dateFile);
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping(value = "/recipe/import", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "upload recipe file",
            description = "we have to select a file to upload"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "file has been uploaded",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            )
    })
    public ResponseEntity<Void> uploadDateFileRecipe(@RequestParam MultipartFile file) {
        recipeFileService.cleanDateFile();
        File dateFile = recipeFileService.getDataFile();
        try {
            FileOutputStream fos = new FileOutputStream(dateFile);
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/recipe/exportTxt")
    @Operation(
            summary = "getting recipes in text format",
            description = "without parameters"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "recipes were received",
                    content = {
                            @Content(
                                    mediaType = "application/txt"
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "we have an error in the query parameters"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "URL incorrect or there is no such action by the web application"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "an error occurred on the server during the execution of the request"
            ),
    })
    public void downloadRecipes(HttpServletResponse response) throws IOException {
        ContentDisposition disposition = ContentDisposition.attachment()
                .name("recipes.txt")
                .build();
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, disposition.toString());
        response.setContentType("text/plain");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        recipeService.addRecipeStringFormat(response.getWriter());
    }

}
