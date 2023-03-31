package pck;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static pck.Utils.parseNullDate;

public class Mappers {

    public static List<Employee> mapRecordsToEmployees(List<List<String>> records) {

        List<Employee> employeeList = new ArrayList<>();

        for (List<String> record : records) {
            employeeList.add(mapRecordToEmployee(record));
        }

        return employeeList;
    }

    public static Employee mapRecordToEmployee(List<String> record) {

        Employee employee = new Employee();

        employee.setEmpId(Integer.parseInt(record.get(0)));
        employee.setProjectId(Integer.parseInt(record.get(1)));
        employee.setDateFrom(LocalDate.parse(record.get(2)));
        employee.setDateTo(parseNullDate(record.get(3)));

        return employee;
    }

}
