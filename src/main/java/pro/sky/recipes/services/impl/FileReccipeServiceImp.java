package pro.sky.recipes.services.impl;

import org.springframework.beans.factory.annotation.Value;
import pro.sky.recipes.services.FileService;

import java.io.File;
import java.nio.file.Path;

public class FileReccipeServiceImp implements FileService {

    @Value("${path.to.date.file}")
    private String dataFilePath;
    @Value("${name.to.date.file1}")
    private String dataFileName;
    @Override
    public boolean saveToFile(String json) {
        return false;
    }

    @Override
    public String readFromFile() {
        return null;
    }

    @Override
    public boolean cleanDateFile() {
        return false;
    }

    @Override
    public Path creatTempFile(String suffix) {
        return null;
    }

    @Override
    public File getDataFile() {
        return null;
    }
}
