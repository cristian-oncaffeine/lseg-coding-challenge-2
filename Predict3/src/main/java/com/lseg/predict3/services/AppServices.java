package com.lseg.predict3.services;

import com.lseg.predict3.domain.Predictor;
import com.lseg.predict3.domain.StockDataPoint;
import com.lseg.predict3.domain.Timestamp;
import com.lseg.predict3.fileIO.CSVFileAdapter;
import com.lseg.predict3.fileIO.DirectoryAdapter;
import com.lseg.predict3.fileIO.dto.Directory;

import java.util.List;

public class AppServices {
    public static void processData(String dataRoot, String outputRoot, int filesToSamplePerStockExchange) {
        // scan the input directory for the stock exchange data folders
        List<Directory> dirList = DirectoryAdapter.getDirectoryStructure(dataRoot, filesToSamplePerStockExchange);
        // process each data file
        for(Directory dir : dirList) {
            for(String file : dir.filePaths) {
                // read data file
                List<StockDataPoint> data = AppServices.readDataPoints(file);

                // predict the next 3 data points and add prediction to the original data set
                List<StockDataPoint> tenPoints = AppServices.get10ConsecutiveDataPoints(data);
                Timestamp lastTimestampInTheDataset = data.get(data.size() - 1).getTimestamp();
                List<StockDataPoint> prediction = AppServices.predictNext3DataPoints(tenPoints, lastTimestampInTheDataset);
                data.addAll(prediction);

                // create the output file
                DirectoryAdapter.createDirectory(outputRoot, dir.path);
                String fileName = CSVFileAdapter.extractFileName(file);
                String newFilePath = CSVFileAdapter.buildFilePath(outputRoot, dir.path, fileName);

                // write the old data points plus the predictions in the output file
                CSVFileAdapter fileAdapter = new CSVFileAdapter(newFilePath);
                fileAdapter.write(newFilePath, data);
            }
        }
    }
    public static List<StockDataPoint> readDataPoints(String filePath)  {
        // open the data file
        CSVFileAdapter reader = new CSVFileAdapter(filePath);
        // read the entire file
        return reader.read();
    }
    public static List<StockDataPoint> get10ConsecutiveDataPoints(List<StockDataPoint> dataPoints) {
        if(dataPoints == null || dataPoints.isEmpty()) {
            throw new IllegalArgumentException("The list of data points should not be empty");
        }
        // check that there are at least 10 data points in the file
        int lineCount = dataPoints.size();
        if(lineCount < 10) {
            throw new IllegalArgumentException("Could not find 10 data points in input file ");
        }
        // choose a start line randomly between 0 and the number of lines - 10
        int startLine = (int)(Math.random() * (lineCount - 10));
        // extract 10 data points starting from the start line
        return dataPoints.subList(startLine, startLine + 10);
    }
    public static List<StockDataPoint> predictNext3DataPoints(List<StockDataPoint> tenPoints, Timestamp lastTimestamp)  {
        return Predictor.predict3(tenPoints, lastTimestamp);
    }
}
