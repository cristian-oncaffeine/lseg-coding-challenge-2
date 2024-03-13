package com.lseg.predict3;
import com.lseg.predict3.services.AppServices;

public class Main {
    // hard-coding the directory structure for the coding challenge
    public static final String rootDataDirectory = "stock_price_data_files";
    public static final String rootOutputDirectory = "output_files";

    public static void main(String[] args) {
        // checking mandatory argument, number of files to process per Stock Exchange folder
        if(args.length < 1) {
            System.out.println("Missing mandatory parameter.\nUsage: Predict3 <number of files to sample per stock exchange>");
            return;
        }
        try {
            int filesToSamplePerSE = Integer.parseInt(args[0]);
            if(filesToSamplePerSE < 1 || filesToSamplePerSE > 2) {
                System.out.println("Invalid parameter value, should be 1 or 2");
                return;
            }
            AppServices.processData(rootDataDirectory, rootOutputDirectory, filesToSamplePerSE);
        } catch (NumberFormatException ex) {
            // there was a problem with the argument to main (number of files to process)
            System.out.println("Invalid parameter value, should be an integer.\nUsage: Predict3 <number of files to sample per stock exchange>");
        }
    }
}