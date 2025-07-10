# CSV to JSON Converter

## Overview

A command-line interface which accepts a CSV input file, processes it and produces an output in the form of a JSON file. User can define whether the output file should be in pretty-printed format 
or raw JSON (default)


## Technologies
* Java 17
* Jackson Core
* Apache commons CSV
* Maven
* JUnit & Mockito for testing


## Features
* Accepts a CSV file as input and writes a JSON equivalent of the file as output
* Optional "--pretty" toggle of type boolean to enable pretty-printing
* File validation before processing in case of invalid file, non-CSV file or missing file
* Auto-detect delimiters from the input file ("," / ";" / "|" / "\t")
* Can handle large input data


## Gettiing Started
### Requirements
* Java 17+
* Maven for building

### Installation
1. **Clone the repository**

    ```
    git clone https://github.com/mishrahul/csvToJson.git
    cd csvToJson
    mvn clean package
    ```
    This adds an executable .jar file in the /target directory

2. **Usage**
    ```
    java -jar csv2json-1.0-SNAPSHOT-jar-with-dependencies.jar [input-file-path] [custom-output-file-path] [--pretty]
    ```
    Argument --pretty is optional, enables pretty-printing if passed.

