package pro.sky.recipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;
import java.util.TreeMap;

@Data
@AllArgsConstructor
public class Ingredients {
    private String name;
    private int count;
    private String unitOfMeasurement;

}