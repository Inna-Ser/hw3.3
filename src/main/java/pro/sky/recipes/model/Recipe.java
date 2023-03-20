package pro.sky.recipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class Recipe {
    private String name;
    private int cookingTime;
    private List<Ingredients> ingredients;
    private List<String> instruction;

}