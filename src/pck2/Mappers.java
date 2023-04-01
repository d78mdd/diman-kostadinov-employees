package pck2;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static pck2.Utils.parseNullDate;


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
                parseNullDate(record.get(3))));

        return workRecord;
    }

}
