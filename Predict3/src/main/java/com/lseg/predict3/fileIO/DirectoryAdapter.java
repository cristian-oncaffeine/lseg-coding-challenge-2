package com.lseg.predict3.fileIO;

import com.lseg.predict3.fileIO.dto.Directory;
import com.sun.jdi.InternalException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class DirectoryAdapter {

    public static List<Directory> getDirectoryStructure(String path, int maxFilesPerDirectory) {
        List<Directory> result = new ArrayList<>();
        // list all the stock exchange directories in the root path
        for(String dirPath : DirectoryAdapter.listDirectories(path)) {
            Directory dir = new Directory();
            dir.path = dirPath;
            // limit the number of files per directory to max files
            dir.filePaths = listCSVFiles(path, dirPath).stream().limit(maxFilesPerDirectory).toList();
            result.add(dir);
        }
        return result;
    }

    public static void createDirectory(String parentName, String dirName) {
        // create parent directory if it does not exist
        new File(Path.of(parentName).toString()).mkdir();
        // create directory if it does not exist
        new File(Path.of(parentName, dirName).toString()).mkdir();
    }
    private static List<String> listDirectories(String path) {
        Path rootPath = Paths.get(path);
        try {
            return Files.list(rootPath).filter(Files::isDirectory).map(f -> f.getFileName().toString()).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> listCSVFiles(String parentName, String dirName) {
        Path rootPath = Paths.get(parentName, dirName);
        try {
            return Files.list(rootPath).map(Path::toString).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
