package pck;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;
import static pck.Mappers.mapRecordsToEmployees;
import static pck.Utils.getRecordsFromFile;
import static pck.Utils.output;

public class EmployeeService {

    private static final ChronoUnit TIME_UNIT = DAYS;

    public static void main(String[] args) throws IOException {

        List<List<String>> records = getRecordsFromFile("sample_data1.csv");

        List<Employee> employeeList = mapRecordsToEmployees(records);
        if (employeeList == null || employeeList.isEmpty()) {
            return;
        }


        List<Pair> employeePairs = getEmployeePairsByProject(employeeList);
        if (employeePairs == null || employeePairs.isEmpty()) {
            return;
        }

        List<Pair> coincidingPairs = getCoincidingEmployeePairs(employeePairs);
        if (coincidingPairs == null || coincidingPairs.isEmpty()) {
            return;
        }

        List<Pair> summedPairs = sumPeriodsOfSamePairs(coincidingPairs);

        Pair longestPair = getPairWithLongestPeriod(summedPairs);

        output(longestPair);

    }

    private static Pair getPairWithLongestPeriod(List<Pair> pairs) {
        Pair longestPair = pairs.get(0);

        for (int i = 1; i < pairs.size(); i++) {
            Pair pair = pairs.get(i);
            if (longestPair.getPeriod() < pair.getPeriod()) {
                longestPair = pair;
            }
        }

        return longestPair;
    }

    private static List<Pair> sumPeriodsOfSamePairs(List<Pair> pairs) {

        List<Pair> summedPairs = new ArrayList<>();

        for (Pair pair : pairs) {
            if (!summedPairs.contains(pair)) {
                summedPairs.add(pair);

            } else {
                int addedPairIndex = summedPairs.indexOf(pair);
                Pair summedPair = summedPairs.get(addedPairIndex);
                long currentPeriod = summedPair.getPeriod();

                summedPair.setPeriod(currentPeriod + pair.getPeriod());
            }
        }

        return summedPairs;
    }


    private static List<Pair> getCoincidingEmployeePairs(List<Pair> pairs) {
        List<Pair> coincidingPairs = new ArrayList<>();

        for (Pair pair : pairs) {

            Employee earlierEmployee;
            Employee laterEmployee;

            LocalDate emp1DateFrom = pair.getEmployee1().getDateFrom();
            LocalDate emp2DateFrom = pair.getEmployee2().getDateFrom();

            if (emp1DateFrom.compareTo(emp2DateFrom) < 0) {
                earlierEmployee = pair.getEmployee1();
                laterEmployee = pair.getEmployee2();
            } else {
                earlierEmployee = pair.getEmployee2();
                laterEmployee = pair.getEmployee1();
            }

            if (haveCoincidingPeriods(earlierEmployee, laterEmployee)) {

                coincidingPairs.add(pair);

                if (laterEmployee.getDateTo().compareTo(earlierEmployee.getDateTo()) >= 0) {
                    // emp2 ended after emp1 ended

                    pair.setPeriod(laterEmployee.getDateFrom().until(earlierEmployee.getDateTo().plusDays(1), TIME_UNIT));

                } else {
                    // emp2 ended before emp1 ended

                    pair.setPeriod(laterEmployee.getDateFrom().until(laterEmployee.getDateTo().plusDays(1), TIME_UNIT));
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

}
