package pck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.time.temporal.ChronoUnit.MONTHS;

public class Main {

    private static final String COMMA_DELIMITER = ",";

    public static void main(String[] args) throws IOException {

        List<List<String>> records = getRecords();

        List<Employee> employeeList = mapRecordsToEmployees(records);


        List<Pair> employeePairs = getEmployeePairsByProject(employeeList);

        List<Pair> coincidingPairs = getCoincidingEmployeePairs(employeePairs);

        output(coincidingPairs);

    }

    private static void output(List<Pair> pairs) {
        for (Pair pair : pairs) {
            System.out.println(pair.getEmployee1().getEmpId()
                    + ", " + pair.getEmployee2().getEmpId()
                    + ", " + pair.getPeriodInMonths());
        }
    }

    private static List<Pair> getCoincidingEmployeePairs(List<Pair> pairs) {
        List<Pair> coincidingPairs = new ArrayList<>();

        for (int i = 0; i < pairs.size(); i++) {

            Employee earlierEmployee;
            Employee laterEmployee;

            LocalDate emp1DateFrom = pairs.get(i).getEmployee1().getDateFrom();
            LocalDate emp2DateFrom = pairs.get(i).getEmployee2().getDateFrom();

            if (emp1DateFrom.compareTo(emp2DateFrom) < 0) {
                earlierEmployee = pairs.get(i).getEmployee1();
                laterEmployee = pairs.get(i).getEmployee2();
            } else {
                earlierEmployee = pairs.get(i).getEmployee2();
                laterEmployee = pairs.get(i).getEmployee1();
            }

            if (haveCoincidingPeriods(earlierEmployee, laterEmployee)) {

                coincidingPairs.add(pairs.get(i));

                if (laterEmployee.getDateTo().compareTo(earlierEmployee.getDateTo()) >= 0) {
                    // emp2 ended after emp1 ended

                    pairs.get(i).setPeriodInMonths(laterEmployee.getDateFrom().until(earlierEmployee.getDateTo(), MONTHS));

                } else {
                    // emp2 ended before emp1 ended

                    pairs.get(i).setPeriodInMonths(laterEmployee.getDateFrom().until(laterEmployee.getDateTo(), MONTHS));
                }
            }

        }

        return coincidingPairs;
    }

    private static boolean haveCoincidingPeriods(Employee emp1, Employee emp2) {
        return emp2.getDateFrom().compareTo(emp1.getDateTo()) < 0;
    }


    private static List<Pair> getEmployeePairsByProject(List<Employee> employees) {
        List<Pair> pairs = new ArrayList<>();

        for (int i = 0; i < employees.size() - 1; i++) {
            Employee emp1 = employees.get(i);

            for (int j = i + 1; j < employees.size(); j++) {
                Employee emp2 = employees.get(j);

                if (emp1.getProjectId() == emp2.getProjectId()) {
                    pairs.add(new Pair(emp1, emp2));
                }
            }

        }

        return pairs;
    }


    private static List<Employee> mapRecordsToEmployees(List<List<String>> records) {

        List<Employee> employeeList = new ArrayList<>();

        for (List<String> record : records) {
            employeeList.add(mapRecordToEmployee(record));
        }

        return employeeList;
    }


    private static Employee mapRecordToEmployee(List<String> record) {

        Employee employee = new Employee();

        employee.setEmpId(Integer.parseInt(record.get(0)));
        employee.setProjectId(Integer.parseInt(record.get(1)));
        employee.setDateFrom(LocalDate.parse(record.get(2)));
        employee.setDateTo(parseDateTo(record.get(3)));

        return employee;
    }

    private static LocalDate parseDateTo(String dateTo) {

        if (dateTo == null || dateTo.equals("NULL")) {
            return LocalDate.now();
        } else {
            return LocalDate.parse(dateTo);
        }
    }


    private static List<List<String>> getRecords() throws IOException {

        List<List<String>> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(getFileStream()))) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] values = line.split(COMMA_DELIMITER);

                records.add(Arrays.asList(values));

            }
        }

        return records;
    }

    private static InputStream getFileStream() {
        return Main.class.getResourceAsStream("sample_data1.csv");
    }
}
