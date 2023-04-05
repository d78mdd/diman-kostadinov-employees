package com.diman.employees.impl;

import com.diman.employees.beans.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.diman.employees.impl.Utils.isEmpty;


public class Mappers {

    public static List<WorkRecord> mapRecordsToBeans(List<List<String>> records) {
        if (isEmpty(records)) {
            return Collections.emptyList();
        }

        List<WorkRecord> workRecords = new ArrayList<>();

        for (List<String> record : records) {
            workRecords.add(mapRecordToBean(record).orElseThrow(IllegalArgumentException::new));
        }

        return workRecords;
    }

    public static Optional<WorkRecord> mapRecordToBean(List<String> record) {
        if (isEmpty(record)) {
            return Optional.empty();
        }

        WorkRecord workRecord = new WorkRecord();

        workRecord.setEmployee(new Employee(Integer.parseInt(record.get(0))));
        workRecord.setProjectId(Integer.parseInt(record.get(1)));
        workRecord.setPeriod(new Period(
                LocalDate.parse(record.get(2)),
                Utils.parseNullDate(record.get(3))));

        return Optional.of(workRecord);
    }

    public static List<EmployeePairWithTotalPeriodLength> mapToEmployeePairWithTotalPeriodLength(List<WorkRecordPair> coincidingPairs) {
        if (isEmpty(coincidingPairs)) {
            return Collections.emptyList();
        }

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

    private static void increaseTotalPairPeriodLength(List<EmployeePairWithTotalPeriodLength> totalPeriodLengths, WorkRecordPair workRecordPair) {
        if (isEmpty(totalPeriodLengths)) {
            return;
        }
        if (null == workRecordPair) {
            return;
        }
        if (null == workRecordPair.getWorkRecord1() || null == workRecordPair.getWorkRecord2()) {
            return;
        }

        for (EmployeePairWithTotalPeriodLength totalPeriodLength : totalPeriodLengths) {
            if (null == totalPeriodLength.getEmployeePair()) {
                continue;
            }

            boolean exists = totalPeriodLength.getEmployeePair().equals(new EmployeePair(
                    workRecordPair.getWorkRecord1().getEmployee(),
                    workRecordPair.getWorkRecord2().getEmployee()
            ));

            if (exists) {
                totalPeriodLength.setTotalPeriodLength(totalPeriodLength.getTotalPeriodLength() + workRecordPair.getCoincidingPeriodLength());
            }
        }

    }

    private static boolean containsPair(List<EmployeePairWithTotalPeriodLength> totalPeriodLengths, WorkRecordPair workRecordPair) {
        if (isEmpty(totalPeriodLengths)) {
            return false;
        }
        if (null == workRecordPair) {
            return false;
        }
        if (null == workRecordPair.getWorkRecord1() || null == workRecordPair.getWorkRecord2()) {
            return false;
        }

        for (EmployeePairWithTotalPeriodLength totalPeriodLength : totalPeriodLengths) {
            if (null == totalPeriodLength.getEmployeePair()) {
                continue;
            }

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

    private static void add(List<EmployeePairWithTotalPeriodLength> totalPeriodLengths, WorkRecordPair workRecordPair) {
        if (null == totalPeriodLengths) {
            return;
        }
        if (null == workRecordPair) {
            return;
        }
        if (null == workRecordPair.getWorkRecord1() || null == workRecordPair.getWorkRecord2()) {
            return;
        }


        EmployeePairWithTotalPeriodLength employeePairWithTotalPeriodLength = new EmployeePairWithTotalPeriodLength();

        employeePairWithTotalPeriodLength.setEmployeePair(new EmployeePair(
                workRecordPair.getWorkRecord1().getEmployee(),
                workRecordPair.getWorkRecord2().getEmployee()
        ));
        employeePairWithTotalPeriodLength.setTotalPeriodLength(workRecordPair.getCoincidingPeriodLength());

        totalPeriodLengths.add(employeePairWithTotalPeriodLength);
    }


}
