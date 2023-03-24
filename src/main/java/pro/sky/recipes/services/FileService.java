package pro.sky.recipes.services;

public interface FileService {

    boolean saveToFile(String json);

    String readFromFile();
}
