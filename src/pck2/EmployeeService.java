package pck2;


import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;
import static pck2.Mappers.mapRecordsToBeans;
import static pck2.Utils.getRecordsFromFile;


public class EmployeeService {

    List<Employee> employees = new ArrayList<>();


    public static final ChronoUnit TIME_UNIT = DAYS;


    public void getLongestEmployeeRecord() throws IOException {

        List<List<String>> recordsFromFile = getRecordsFromFile("sample_data2.csv");

        List<WorkRecord> workRecords = mapRecordsToBeans(recordsFromFile);
        if (workRecords == null || workRecords.isEmpty()) {
            return;
        }
        System.out.println(workRecords);
        System.out.println();


        List<WorkRecordPair> workRecordPairs = getAllRecordPairsByProject(workRecords);
        if (workRecordPairs == null || workRecordPairs.isEmpty()) {
            return;
        }
        System.out.println(workRecordPairs);
        System.out.println();

        List<WorkRecordPair> coincidingPairs = filterByCoincidingPairs(workRecordPairs);
        if (coincidingPairs == null || coincidingPairs.isEmpty()) {
            return;
        }
        System.out.println(coincidingPairs);

        List<EmployeePairWithTotalPeriodLength> totalPeriodLengths = mapToEmployeePairWithTotalPeriodLength(coincidingPairs);
        System.out.println(totalPeriodLengths);

        EmployeePairWithTotalPeriodLength longestPair = getPairWithLongestPeriod(totalPeriodLengths);
        System.out.println(longestPair);

//        output(longestPair);

    }

    private List<EmployeePairWithTotalPeriodLength> mapToEmployeePairWithTotalPeriodLength(List<WorkRecordPair> coincidingPairs) {
        List<EmployeePairWithTotalPeriodLength> totalPeriodLengths = new ArrayList<>();

        for (WorkRecordPair coincidingPair : coincidingPairs) {
            if (containsPair(totalPeriodLengths, coincidingPair)) {
                increaseTotalPairPeriodLength(totalPeriodLengths, coincidingPair);
            } else {
                add(totalPeriodLengths, coincidingPair);
            }
        }

        return totalPeriodLengths;
    }

    private void increaseTotalPairPeriodLength(List<EmployeePairWithTotalPeriodLength> totalPeriodLengths, WorkRecordPair workRecordPair) {

        for (EmployeePairWithTotalPeriodLength totalPeriodLength : totalPeriodLengths) {

            boolean exists = totalPeriodLength.getEmployeePair().equals(new EmployeePair(
                    workRecordPair.getWorkRecord1().getEmployee(),
                    workRecordPair.getWorkRecord2().getEmployee()
            ));

            if (exists) {
                totalPeriodLength.setTotalPeriodLength(totalPeriodLength.getTotalPeriodLength() + workRecordPair.getCoincidingPeriodLength());
            }
        }

    }

    private boolean containsPair(List<EmployeePairWithTotalPeriodLength> totalPeriodLengths, WorkRecordPair workRecordPair) {

        for (EmployeePairWithTotalPeriodLength totalPeriodLength : totalPeriodLengths) {

            boolean exists = totalPeriodLength.getEmployeePair().equals(new EmployeePair(
                    workRecordPair.getWorkRecord1().getEmployee(),
                    workRecordPair.getWorkRecord2().getEmployee()
            ));

            if (exists) {
                return true;
            }
        }

        return false;
    }

    private void add(List<EmployeePairWithTotalPeriodLength> totalPeriodLengths, WorkRecordPair workRecordPair) {
        EmployeePairWithTotalPeriodLength employeePairWithTotalPeriodLength = new EmployeePairWithTotalPeriodLength();

        employeePairWithTotalPeriodLength.setEmployeePair(new EmployeePair(
                workRecordPair.getWorkRecord1().getEmployee(),
                workRecordPair.getWorkRecord2().getEmployee()
        ));
        employeePairWithTotalPeriodLength.setTotalPeriodLength(workRecordPair.getCoincidingPeriodLength());

        totalPeriodLengths.add(employeePairWithTotalPeriodLength);
    }

//    private List<Pair> mergeSummedPeriodsOfSameEmployees(List<Pair> pairs) {
//        List<Pair> mergedSummedPairs = new ArrayList<>();
//
//        for (Pair pair : pairs) {
//            if (!contains(mergedSummedPairs, pair)) {
//                mergedSummedPairs.add(pair);
//            } else {
//                int index = indexOf(mergedSummedPairs, pair);
//                mergedSummedPairs.get(index).setPeriod(mergedSummedPairs.get(index).getPeriod() + pair.getPeriod());
//            }
//
//        }
//
//        return mergedSummedPairs;
//    }

//    private int indexOf(List<Pair> pairs, Pair pair) {
//
//        for (int i = 0; i < pairs.size(); i++) {
//            if (pairs.get(i).getEmployee1().getEmpId() == pair.getEmployee1().getEmpId() &&
//                    pairs.get(i).getEmployee2().getEmpId() == pair.getEmployee2().getEmpId()) {
//                return i;
//            }
//        }
//
//        return -1;
//
//    }

//    private boolean contains(List<Pair> pairs, Pair pair) {
//
//        for (Pair value : pairs) {
//            if (value.getEmployee1().getEmpId() == pair.getEmployee1().getEmpId() &&
//                    value.getEmployee2().getEmpId() == pair.getEmployee2().getEmpId()) {
//                return true;
//            }
//        }
//
//        return false;
//    }

    public EmployeePairWithTotalPeriodLength getPairWithLongestPeriod(List<EmployeePairWithTotalPeriodLength> pairs) {
        EmployeePairWithTotalPeriodLength longestPair = pairs.get(0);

        for (int i = 1; i < pairs.size(); i++) {
            EmployeePairWithTotalPeriodLength pair = pairs.get(i);
            if (longestPair.getTotalPeriodLength() < pair.getTotalPeriodLength()) {
                longestPair = pair;
            }
        }

        return longestPair;
    }

//    public List<Pair> sumPeriodsOfSameEmployeesAndProject(List<WorkRecordPair> pairs) {
//
//        List<Pair> summedPairs = new ArrayList<>();
//
//        for (Pair pair : pairs) {
//            if (!summedPairs.contains(pair)) {
//                summedPairs.add(pair);
//
//            } else {
//                int addedPairIndex = summedPairs.indexOf(pair);
//                Pair summedPair = summedPairs.get(addedPairIndex);
//                long currentPeriod = summedPair.getPeriod();
//
//                summedPair.setPeriod(currentPeriod + pair.getPeriod());
//            }
//        }
//
//        return summedPairs;
//    }


    public List<WorkRecordPair> filterByCoincidingPairs(List<WorkRecordPair> pairs) {
        List<WorkRecordPair> coincidingPairs = new ArrayList<>();

        for (WorkRecordPair pair : pairs) {

            // 1. make sure the first in the pair is always the earlier to reduce possible combinations
            WorkRecord earlierRecord;
            WorkRecord laterRecord;

            LocalDate emp1DateFrom = pair.getWorkRecord1().getPeriod().getDateFrom();
            LocalDate emp2DateFrom = pair.getWorkRecord2().getPeriod().getDateFrom();

            if (emp1DateFrom.compareTo(emp2DateFrom) < 0) {
                earlierRecord = pair.getWorkRecord1();
                laterRecord = pair.getWorkRecord2();
            } else {
                earlierRecord = pair.getWorkRecord2();
                laterRecord = pair.getWorkRecord1();
            }


            // 2. check for coinciding/overlapping periods
            //, add them to the list
            //, set their length

            if (haveCoincidingPeriods(earlierRecord, laterRecord)) {

                coincidingPairs.add(pair);

                if (laterRecord.getPeriod().getDateTo().compareTo(earlierRecord.getPeriod().getDateTo()) >= 0) {
                    // emp2 ended after emp1 ended

                    pair.setCoincidingPeriodLength(laterRecord.getPeriod().getDateFrom().until(earlierRecord.getPeriod().getDateTo().plusDays(1), TIME_UNIT));

                } else {
                    // emp2 ended before emp1 ended

                    pair.setCoincidingPeriodLength(laterRecord.getPeriod().getDateFrom().until(laterRecord.getPeriod().getDateTo().plusDays(1), TIME_UNIT));
                }
            }

        }

        return coincidingPairs;
    }

    public boolean haveCoincidingPeriods(WorkRecord rec1, WorkRecord rec2) {
        return rec2.getPeriod().getDateFrom().compareTo(rec1.getPeriod().getDateTo()) < 0;
    }


    public List<WorkRecordPair> getAllRecordPairsByProject(List<WorkRecord> employees) {
        List<WorkRecordPair> pairs = new ArrayList<>();

        for (int i = 0; i < employees.size() - 1; i++) {
            WorkRecord rec1 = employees.get(i);

            for (int j = i + 1; j < employees.size(); j++) {
                WorkRecord rec2 = employees.get(j);

                if (rec1.getProjectId() == rec2.getProjectId() && rec1.getEmployee().getEmpId() != rec2.getEmployee().getEmpId()) {
                    pairs.add(new WorkRecordPair(rec1, rec2));
                }
            }

        }

        return pairs;
    }


}
