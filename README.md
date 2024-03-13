# lseg-coding-challenge-2
Predict the next 3 values of stock price.

## Overview
This coding challenge is loading a number of data sets containing stock prices from the file system, and calculates the next 3 data points for each data set, using a custom prediction algorithm. The data sets for each stock exchange were provided, as an input, and are organized in a multiple folder structure, each data set belonging to one folder.

## Objective

The challenge objective has been defined as following: "For each stock exchange, select the specified number of files, and for each file provided, predict the next 3 values of stock price for that specific file".

## How to Run

### Clone this repository

```
git clone git@github.com:cristian-oncaffeine/lseg-coding-challenge-2.git
```

### Pre-requisites
You need to have Java and Maven installed.
- [Java](https://openjdk.org/)

- [Maven](https://maven.apache.org/install.html)

### Build & Run
Assuming you have the Java SDK installed (I used openjdk 17.0.9) and assuming you have maven installed, go to the `Predict3` folder, build the application, package it into a jar file, and run it (don't forget to pass the parameter, 2 for example):

```
cd Predict3
mvn compile
mvn package
java -jar target/Predict3-1.0-SNAPSHOT.jar 2
```

The resulting data sets, containing the predictions should be now under the `output_files` directory. If you are on Linux or Mac, run:

```
ls output_files
```

Also, the input data sets can be found in the `stock_price_data_files` directory, under `Predict3`, for convenience.

## Implementation notes

### Analysis

The suggested prediction algorithm has been defined as following:
- first predicted (n+1) data point is same as the 2nd highest value present in the 10 data points
- n+2 data point has half the difference between n and n +1
- n+3 data point has 1/4th the difference between n+1 and n+2

#### Remarks
- For the first predicted value, it's clear.
- For the second, the difference between n and n+1 prices could be negative (assume for example that the last value is also the max value). Therefore it could be that n+2 has half the difference between n and n+1, `on top of the value of n+1`.
- The same reasoning applies for the n+3 value, the 1/4 difference between n+1 and n+2 is added `on top of the value of n+2`.

### Assumptions
- The implementation could be done in Java
    - as it was not specified otherwise
- The data points in the data sets are ordered by timestamp, in ascending order
    - This has implications on how we determine the last data point in a set. Computational complexity increases in case the data set is not ordered
- The size of the data sets is rather small (as provided in this example).
    - The implementation would not scale well for really large data sets.
- The suggested prediction algorithm has been interpreted as explained above, in the analysis: 
    - for n+2 and n+3, the differences are going to be added to the price of the previous data point
- The folder structure for the current project can be considered fixed
    - the name of root folders, for input and output, respectively, are currently hardcoded

### Solution

The API (the 2 functions for extracting 10 data points, and the prediction function) are implemented in the `AppServices` class from the `com.lseg.predict3.services` package:

- get10ConsecutiveDataPoints randomly selects 10 data points from the input dataset
- predictNext3DataPoints generates 3 data points based on the selected data points, and the timestamp of the last data point in the original data set  

Besides these 2, AppServices implements
- processData which reads all the input files, and writes the new data sets, which include the predictions, in the output folders
- readDataPoints is a utility method which reads an input data set and returns the data as a list of `StockDataPoint` domain objects

File IO adapter classes are implemented in `com.lseg.predict3.fileIO`. There is also a `dto` sub-package there, which contains data transfer objects for directories and files.

The data itself is modeled with the value objects implemented in the `com.lseg.predict3.domain` package. The value objects also provide validation.

### Possible optimisations
- It is not really necessary to read the entire dataset when selecting the 10 data points. If we would be able to know where the 10 records set starts (by knowing for example the number of data points in a data set, or if the line length would be constant and we know the line length, to be able to calculate the number of records), we could go directly to the start offset of the 10 records instead of creating an in-memory replica of entire CSV file.

### Final notes
- The implementation took around 10 hours, probably due to the solution complexity.
- I also left the IntelliJ settings in the repository, in case it may help.