package com.hbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by igor on 1/17/2015.
 */
public class CentersReader {
    public CentersReader() {
    }

    public static void main(String[] args) {
        CentersReader cr = new CentersReader();
        cr.readCenters(args[0], args[1]);
    }

    private void readCenters(String inFile, String outFile) {
        try {
            Map<String,List<String>> result = new HashMap<>();
            Files.lines(Paths.get(inFile)).forEach((line)->parseCenter(line, result));
            PrintWriter writer = new PrintWriter(outFile, "UTF-8");
            result.forEach((country,centers)->printCountry(writer, country, centers));
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    private void parseCenter(String line, Map<String,List<String>> centers) {
        String[] values = line.split(";");
        if ((values.length >= 6) && (values[0].compareTo("CENTERS") == 0)) {
            String country = values[2];
            String city = values[5];

            if (centers.containsKey(country)) {
                centers.get(country).add(city);
            } else {
                List<String> countryCenters = new ArrayList<>();
                countryCenters.add(city);
                centers.put(country, countryCenters);
            }
        }
    }

    private void printCountry(PrintWriter writer, String country, List<String> centers) {
        writer.println(country);
        for (int i=0; i < centers.size(); i++) {
            writer.print(centers.get(i));

            if (i < centers.size()-1) {
                writer.print(",");
            } else {
                writer.println();
            }
        }
    }

}
