package pck2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    public static InputStream getFileStream(String file) {
        return EmployeeService.class.getResourceAsStream(file);
    }

    public static List<List<String>> getRecordsFromFile(String file) throws IOException {

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
