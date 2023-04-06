package com.diman.employees.impl;

import com.diman.employees.beans.EmployeePairWithTotalPeriodLength;
import com.diman.employees.beans.WorkRecord;
import com.diman.employees.beans.WorkRecordPair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.diman.employees.impl.Mappers.mapRecordsToBeans;
import static com.diman.employees.impl.Mappers.mapToEmployeePairWithTotalPeriodLength;
import static com.diman.employees.impl.Utils.*;
import static java.time.temporal.ChronoUnit.DAYS;


public class EmployeeService {


    public static final ChronoUnit TIME_UNIT = DAYS;


    public Optional<EmployeePairWithTotalPeriodLength> getLongestEmployeeRecord(URL resource) throws IOException, URISyntaxException, InvalidCsvException {

        List<List<String>> recordsFromFile = getRecordsFromFile(resource);

        List<WorkRecord> workRecords = mapRecordsToBeans(recordsFromFile);
        if (isEmpty(workRecords)) {
            return Optional.empty();
        }

        List<WorkRecordPair> workRecordPairs = getAllRecordPairsByProject(workRecords);
        if (isEmpty(workRecordPairs)) {
            return Optional.empty();
        }

        List<WorkRecordPair> coincidingPairs = filterByCoincidingPairs(workRecordPairs);
        if (isEmpty(coincidingPairs)) {
            return Optional.empty();
        }

        List<EmployeePairWithTotalPeriodLength> totalPeriodLengths = mapToEmployeePairWithTotalPeriodLength(coincidingPairs);
        if (isEmpty(totalPeriodLengths)) {
            return Optional.empty();
        }

        return getPairWithLongestPeriod(totalPeriodLengths);

    }





    public Optional<EmployeePairWithTotalPeriodLength> getPairWithLongestPeriod(List<EmployeePairWithTotalPeriodLength> pairs) {
        if (isEmpty(pairs)) {
            return Optional.empty();
        }

        EmployeePairWithTotalPeriodLength longestPair = pairs.get(0);

        for (int i = 1; i < pairs.size(); i++) {
            EmployeePairWithTotalPeriodLength pair = pairs.get(i);
            if (longestPair.getTotalPeriodLength() < pair.getTotalPeriodLength()) {
                longestPair = pair;
            }
        }

        return Optional.of(longestPair);
    }

    public List<WorkRecordPair> filterByCoincidingPairs(List<WorkRecordPair> pairs) {
        if (isEmpty(pairs)) {
            return Collections.emptyList();
        }

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
        if (null == rec1 || null == rec2
                || null == rec1.getPeriod() || null == rec1.getPeriod().getDateFrom()
                || null == rec2.getPeriod() || null == rec2.getPeriod().getDateTo()) {
            return false;
        }
        return rec2.getPeriod().getDateFrom().compareTo(rec1.getPeriod().getDateTo()) < 0;
    }


    public List<WorkRecordPair> getAllRecordPairsByProject(List<WorkRecord> employees) {
        if (isEmpty(employees)) {
            return Collections.emptyList();
        }

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
