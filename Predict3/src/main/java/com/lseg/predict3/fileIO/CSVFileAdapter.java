package com.lseg.predict3.fileIO;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.lseg.predict3.domain.*;

public class CSVFileAdapter {

    private final String filePath;
    public CSVFileAdapter(String filePath) {
        if(filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("ERROR: empty file name");
        }
        this.filePath = filePath;
    }

    public List<StockDataPoint> read() {
        List<StockDataPoint> result = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(
                new FileReader(filePath))) {
            String line;
            while((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                result.add(StockDataPoint.from(data[0], data[1], data[2]));
            }
        } catch (FileNotFoundException e) {
            System.out.printf("ERROR: could not find file %s.\n", filePath);
        } catch (IOException e) {
            System.out.printf("ERROR: error reading file %s.\n", filePath);
        }
        return result;
    }

    public void write(String filePath, List<StockDataPoint> data) {
        File outputFile = new File(filePath);
        try (PrintWriter writer = new PrintWriter(outputFile)) {
            data.stream().map(StockDataPoint::toCSV).forEach(writer::println);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String buildFilePath(String path1, String path2, String path3) {
        var path = Path.of(path1, path2, path3);
        return path.toString();
    }

    public static String extractFileName(String filePath) {
        return Path.of(filePath).getFileName().toString();
    }
}
