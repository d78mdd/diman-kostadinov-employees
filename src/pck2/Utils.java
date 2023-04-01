package pck2;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static pck2.EmployeeService.TIME_UNIT;

public class Utils {

    public long sumPeriods(List<Period> periods) {
        long sum = 0;
        for (Period period : periods) {
            sum += period.getDateFrom().until(period.getDateTo(), TIME_UNIT) + 1;
        }
        return sum;
    }

    private static final String COMMA_DELIMITER = ",";


//    public static void output(Pair pair) {
//        System.out.println(pair.getEmployee1().getEmpId()
//                + ", " + pair.getEmployee2().getEmpId()
//                + ", " + pair.getPeriod());
//    }

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
