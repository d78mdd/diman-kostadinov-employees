package com.diman.employees;

import com.diman.employees.impl.EmployeeService;

import java.io.IOException;
import java.net.URISyntaxException;

public class App {

    private static final EmployeeService service = new EmployeeService();


    public static void main(String[] args) throws IOException, URISyntaxException {

        service.getLongestEmployeeRecord(App.class.getResource("sample_data2.csv"));

    }
}
