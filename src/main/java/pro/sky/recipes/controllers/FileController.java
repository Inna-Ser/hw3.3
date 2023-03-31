package pro.sky.recipes.controllers;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.recipes.services.impl.FileIngredientImpl;
import pro.sky.recipes.services.impl.FileRecipeImpl;

import java.io.*;

@RestController
@RequestMapping("/files")
public class FileController {

    private FileRecipeImpl recipeFileService;
    private FileIngredientImpl ingredientFileService;

    public FileController() {
    }

    public FileController(FileRecipeImpl recipeFileService, FileIngredientImpl ingredientFileService) {
        this.recipeFileService = recipeFileService;
        this.ingredientFileService = ingredientFileService;
    }


    @GetMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
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
    public ResponseEntity<Void> uploadDateFileIngredient(@RequestParam MultipartFile file) {
        // Аналогично нужно сделать для импорта рецептов. Нужно использовать recipeFileService
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

    @PostMapping(value = "/recipe/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
