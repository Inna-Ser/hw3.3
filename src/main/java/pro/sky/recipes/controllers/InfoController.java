package pro.sky.recipes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class InfoController {
    @GetMapping
    public String hello() {
        return "Application is started";
    }

    @GetMapping("src/main/java/pro.sky.recipes/controllers/info")
    @Operation(
            summary = "info about project"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "information displayed on the screen",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }

            )
    })
    public String info() {
        return "Inna Serebriakova, Recipe App, 14.03.2023, application of recipes";
    }
}
