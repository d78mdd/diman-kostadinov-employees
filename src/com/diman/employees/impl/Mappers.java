package com.diman.employees.impl;

import com.diman.employees.beans.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.diman.employees.impl.Utils.isEmpty;


public class Mappers {

    public static final int EXPECTED_RECORD_LENGTH = 4;

    public static List<WorkRecord> mapRecordsToBeans(List<List<String>> records) throws InvalidCsvException {
        if (isEmpty(records)) {
            return Collections.emptyList();
        }

        List<WorkRecord> workRecords = new ArrayList<>();

        for (int i = 0; i < records.size(); i++) {
            List<String> record = records.get(i);
            int finalI = i;
            workRecords.add(mapRecordToBean(record).orElseThrow(() -> new InvalidCsvException("invalid record : " + record + " at index " + finalI)));
        }

        return workRecords;
    }

    public static Optional<WorkRecord> mapRecordToBean(List<String> record) throws InvalidCsvException {
        if (isEmpty(record)) {
            return Optional.empty();
        }
        assertValidRecord(record);

        WorkRecord workRecord = new WorkRecord();

        workRecord.setEmployee(new Employee(Integer.parseInt(record.get(0))));
        workRecord.setProjectId(Integer.parseInt(record.get(1)));
        workRecord.setPeriod(new Period(
                LocalDate.parse(record.get(2)),
                Utils.parseNullDate(record.get(3))));

        return Optional.of(workRecord);
    }

    private static void assertValidRecord(List<String> record) throws InvalidCsvException {
        if (null == record) {
            return;
        }

        if (record.size() != EXPECTED_RECORD_LENGTH) {
            throw new InvalidCsvException("unexpected record length : " + record.size() + " ( " + record + " ) ");
        }

        try {
            Integer.parseInt(record.get(0));
            Integer.parseInt(record.get(1));
            LocalDate.parse(record.get(2));
            Utils.parseNullDate(record.get(3));
        } catch (NumberFormatException ex) {
            throw new InvalidCsvException("invalid value for person id or company id in record : " + record);
        } catch (DateTimeParseException ex) {
            throw new InvalidCsvException("invalid start/end date value in record : " + record);
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new InvalidCsvException("incomplete record / invalid end date in : " + record);
        }

        if (Integer.parseInt(record.get(0)) < 0 || Integer.parseInt(record.get(1)) < 0) {
            throw new InvalidCsvException("negative ID : " + record);
        }
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
