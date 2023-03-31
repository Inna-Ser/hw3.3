package pro.sky.recipes.services.impl;

import org.springframework.stereotype.Service;
import pro.sky.recipes.services.FileService;

import java.io.File;
import java.nio.file.Path;

 @Service
public class FileServiceImpl implements FileService {
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
