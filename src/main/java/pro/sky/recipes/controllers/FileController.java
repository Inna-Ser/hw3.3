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

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/files")
public class FileController {

    private FileRecipeImpl recipeFileService;
    private FileIngredientImpl ingredientFileService;

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
}
