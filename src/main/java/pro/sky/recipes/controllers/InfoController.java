package pro.sky.recipes.controllers;

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

    @GetMapping("/info")
    public String info() {
        return "Inna Serebriakova, Recipe App, 14.03.2023, application of recipes";
    }
}
