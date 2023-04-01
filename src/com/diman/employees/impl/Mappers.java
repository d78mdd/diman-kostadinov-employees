package com.diman.employees.impl;

import com.diman.employees.beans.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Mappers {

    public static List<WorkRecord> mapRecordsToBeans(List<List<String>> records) {

        List<WorkRecord> workRecords = new ArrayList<>();

        for (List<String> record : records) {
            workRecords.add(mapRecordToBean(record));
        }

        return workRecords;
    }

    public static WorkRecord mapRecordToBean(List<String> record) {

        WorkRecord workRecord = new WorkRecord();

        workRecord.setEmployee(new Employee(Integer.parseInt(record.get(0))));
        workRecord.setProjectId(Integer.parseInt(record.get(1)));
        workRecord.setPeriod(new Period(
                LocalDate.parse(record.get(2)),
                Utils.parseNullDate(record.get(3))));

        return workRecord;
    }

    public static List<EmployeePairWithTotalPeriodLength> mapToEmployeePairWithTotalPeriodLength(List<WorkRecordPair> coincidingPairs) {
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

    private static boolean containsPair(List<EmployeePairWithTotalPeriodLength> totalPeriodLengths, WorkRecordPair workRecordPair) {

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

    private static void add(List<EmployeePairWithTotalPeriodLength> totalPeriodLengths, WorkRecordPair workRecordPair) {
        EmployeePairWithTotalPeriodLength employeePairWithTotalPeriodLength = new EmployeePairWithTotalPeriodLength();

        employeePairWithTotalPeriodLength.setEmployeePair(new EmployeePair(
                workRecordPair.getWorkRecord1().getEmployee(),
                workRecordPair.getWorkRecord2().getEmployee()
        ));
        employeePairWithTotalPeriodLength.setTotalPeriodLength(workRecordPair.getCoincidingPeriodLength());

        totalPeriodLengths.add(employeePairWithTotalPeriodLength);
    }





}
