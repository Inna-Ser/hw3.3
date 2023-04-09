package pro.sky.recipes.services;

import java.io.File;
import java.nio.file.Path;

public interface FileService {

    boolean saveToFile(String json);

    String readFromFile();

    boolean cleanDateFile();

    Path creatTempFile(String suffix);

    File getDataFile();
}
