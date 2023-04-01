package com.diman.employees.impl;

import com.diman.employees.beans.EmployeePairWithTotalPeriodLength;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    private static final String COMMA_DELIMITER = ",";


    public static void output(EmployeePairWithTotalPeriodLength pair) {
        System.out.println(pair.getEmployeePair().getEmployee1().getEmpId()
                + ", " + pair.getEmployeePair().getEmployee2().getEmpId()
                + ", " + pair.getTotalPeriodLength());
    }

    public static LocalDate parseNullDate(String date) {

        if (date == null || date.equals("NULL")) {
            return LocalDate.now();
        } else {
            return LocalDate.parse(date);
        }
    }

    private static FileInputStream getFileStream(URL file) throws FileNotFoundException, URISyntaxException {
        return new FileInputStream(new File(file.toURI()));
    }

    public static List<List<String>> getRecordsFromFile(URL file) throws IOException, URISyntaxException {

        List<List<String>> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(getFileStream(file)))) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] values = line.split(COMMA_DELIMITER);

                records.add(Arrays.asList(values));

            }
        }

        return records;
    }

}
