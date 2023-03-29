package pck;
/*
Create an application that identifies the pair of employees who have worked
together on common projects for the longest period of time.
Input data:
 A CSV file with data in the following format:
 EmpID, ProjectID, DateFrom, DateTo
Sample data:
143, 12, 2013-11-01, 2014-01-05
218, 10, 2012-05-16, NULL
143, 10, 2009-01-01, 2011-04-27
...
Sample output:
 143, 218, 8
Specific requirements
1) DateTo can be NULL, equivalent to today
2) The input data must be loaded to the program from a CSV file
3) The task solution needs to be uploaded in github.com, repository name must be in
format: {FirstName}-{LastName}-employees
Bonus points
1) Create an UI:
The user picks up a file from the file system and, after selecting it, all common
projects of the pair are displayed in datagrid with the following columns:
Employee ID #1, Employee ID #2, Project ID, Days worked
2) More than one date format to be supported, extra points will be given if all date formats
are supported
Delivery time
 One day after receiving the task
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String COMMA_DELIMITER = ",";

    public static void main(String[] args) throws IOException {

        List<List<String>> records = getRecords();

        List<Employee> employeeList = mapRecordsToEmployees(records);

        System.out.println(employeeList.toString());


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

        if ( dateTo == null || dateTo.equals("NULL")) {
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

                System.out.println(Arrays.toString(values));
            }
        }

        return records;
    }

    private static InputStream getFileStream() {
        return Main.class.getResourceAsStream("sample_data1.csv");
    }
}
