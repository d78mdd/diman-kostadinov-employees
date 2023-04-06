package com.diman.employees;

import com.diman.employees.beans.EmployeePairWithTotalPeriodLength;
import com.diman.employees.impl.EmployeeService;
import com.diman.employees.impl.InvalidCsvException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import static com.diman.employees.impl.Utils.output;

public class App {

    private static final EmployeeService service = new EmployeeService();


    public static void main(String[] args) throws IOException, URISyntaxException, InvalidCsvException {

        Optional<EmployeePairWithTotalPeriodLength> result = service.getLongestEmployeeRecord(App.class.getResource("sample_data2.csv"));

        output(result.get());

    }
}
