package pro.sky.recipes.controllers;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.recipes.services.impl.FileIngredientServiceImpl;
import pro.sky.recipes.services.impl.FileServiceImpl;

import java.io.*;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileServiceImpl fileService;
    private final FileIngredientServiceImpl fileIngredientService;

    public FileController(FileServiceImpl fileService, FileIngredientServiceImpl fileIngredientService) {
        this.fileService = fileService;
        this.fileIngredientService = fileIngredientService;
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
}
