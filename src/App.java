import pck2.EmployeeService;

import java.io.IOException;

public class App {

    private static final EmployeeService service = new EmployeeService();


    public static void main(String[] args) throws IOException {

        service.getLongestEmployeeRecord();

    }
}
