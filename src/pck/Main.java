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

import static java.time.temporal.ChronoUnit.MONTHS;

/*
task sample output seems incorrect

the only common project between employee 143 and employee 218 is project 10
but
employee 143 left the project in 2011
employee 218 joined it later - in 2012

output should be empty
 */
public class Main {

    private static final String COMMA_DELIMITER = ",";

    public static void main(String[] args) throws IOException {

        List<List<String>> records = getRecords();

        List<Employee> employeeList = mapRecordsToEmployees(records);


        List<Pair> employeePairs = getEmployeePairsByProject(employeeList);

        List<Pair> coincidingEmployeePairs = getCoincidingEmployeePairs(employeePairs);
        getCoincidingEmployeePairs2(employeePairs);


    }

    private static List<Pair> getCoincidingEmployeePairs2(List<Pair> pairs){

        for (int i = 0; i < pairs.size(); i++) {

            Employee emp1 = getEarlierEmployee(pairs.get(i));
            Employee emp2 = getLaterEmployee(pairs.get(i));

            if (emp2.getDateFrom().compareTo(emp1.getDateTo()) >= 0 ) {
                // do nothing - they don't coincide
            } else {
                // they coincide

                if (emp2.getDateTo().compareTo(emp1.getDateTo()) >= 0) {   // emp2 ended after emp1 ended

                    pairs.get(i).setPeriodInMonths(getPeriod2(emp2.getDateFrom(), emp1.getDateTo()));

                } else {    // emp2 ended before emp1 ended

                    pairs.get(i).setPeriodInMonths(getPeriod2(emp2.getDateFrom(), emp2.getDateTo()));
                }

//                pair.setPeriodInMonths(getPeriod(pair));
            }

        }


        return new ArrayList<>();
    }

    private static long getPeriod2(LocalDate date1, LocalDate date2) {

        return date1.until(date2, MONTHS);
    }


    private static Employee getEarlierEmployee(Pair pair) {
        LocalDate emp1DateFrom = pair.getEmployee1().getDateFrom();
        LocalDate emp2DateFrom = pair.getEmployee2().getDateFrom();
        if (emp1DateFrom.compareTo(emp2DateFrom) < 0 )
        {
            return pair.getEmployee1();
        } else {
            return pair.getEmployee2();
        }
    }

    private static Employee getLaterEmployee(Pair pair) {
        LocalDate emp1DateFrom = pair.getEmployee1().getDateFrom();
        LocalDate emp2DateFrom = pair.getEmployee2().getDateFrom();
        if (emp1DateFrom.compareTo(emp2DateFrom) >= 0 )
        {
            return pair.getEmployee1();
        } else {
            return pair.getEmployee2();
        }
    }

    private static List<Pair> getCoincidingEmployeePairs(List<Pair> employeePairs) {
        List<Pair> pairs = new ArrayList<>();

        for (int i = 0; i < employeePairs.size(); i++) {
            Pair pair = employeePairs.get(i);

            Employee emp1 = pair.getEmployee1();
            Employee emp2 = pair.getEmployee2();

            if (emp2StartedAfterEmp1To(emp1, emp2)) {
                // do nothing

            } else if (emp2StartedAtEmp1To(emp1, emp2)) {
                pair.setPeriodInMonths(1);

            } else if (emp2StartedBeforeEmp1To(emp1, emp2)) {
                pair.setPeriodInMonths(getPeriod(pair));
            }
        }

        return pairs;
    }

    private static int getPeriod(Pair pair) {

        pair.getEmployee1().getDateTo().until(pair.getEmployee2().getDateTo(), MONTHS);

        return 0;
    }

    private static boolean emp2StartedBeforeEmp1To(Employee emp1, Employee emp2) {
        return compareToWithFrom(emp1, emp2) > 0;
    }

    private static boolean emp2StartedAtEmp1To(Employee emp1, Employee emp2) {
        return compareToWithFrom(emp1, emp2) == 0;
    }

    private static boolean emp2StartedAfterEmp1To(Employee emp1, Employee emp2) {
        return compareToWithFrom(emp1, emp2) < 0;
    }

    private static int compareToWithFrom(Employee emp1, Employee emp2) {
        return emp1.getDateTo().compareTo(emp2.getDateFrom());
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

        System.out.println(pairs);
        return pairs;
    }


    private static List<Employee> mapRecordsToEmployees(List<List<String>> records) {

        List<Employee> employeeList = new ArrayList<>();

        for (List<String> record : records) {
            employeeList.add(mapRecordToEmployee(record));
        }

        System.out.println(employeeList.toString());
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

                System.out.println(Arrays.toString(values));
            }
        }

        System.out.println();
        return records;
    }

    private static InputStream getFileStream() {
        return Main.class.getResourceAsStream("sample_data1.csv");
    }
}
