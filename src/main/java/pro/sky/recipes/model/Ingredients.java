package pro.sky.recipes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.TreeMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredients {
    private String name;
    private int count;
    private String unitOfMeasurement;

}