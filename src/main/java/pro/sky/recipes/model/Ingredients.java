package pro.sky.recipes.model;

import java.util.Objects;

public class Ingredients {
    private String name;
    private int count;
    private String unitOfMeasurement;

    public Ingredients(String name, int count, String unitOfMeasurement) {
        this.name = name;
        this.count = count;
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredients that = (Ingredients) o;
        return count == that.count && Objects.equals(name, that.name) && Objects.equals(unitOfMeasurement, that.unitOfMeasurement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, count, unitOfMeasurement);
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", unitOfMeasurement='" + unitOfMeasurement + '\'' +
                '}';
    }
}

