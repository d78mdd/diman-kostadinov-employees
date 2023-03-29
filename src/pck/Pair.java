package pck;

public class Pair {
    private Employee employeeId1;
    private Employee employeeId2;
    private int periodInMonths;

    public Pair() {
    }

    public Pair(Employee employeeId1, Employee employeeId2) {
        this.employeeId1 = employeeId1;
        this.employeeId2 = employeeId2;
    }

    public Employee getEmployeeId1() {
        return employeeId1;
    }

    public void setEmployeeId1(Employee employeeId1) {
        this.employeeId1 = employeeId1;
    }

    public Employee getEmployeeId2() {
        return employeeId2;
    }

    public void setEmployeeId2(Employee employeeId2) {
        this.employeeId2 = employeeId2;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "employeeId1=" + employeeId1 +
                ", employeeId2=" + employeeId2 +
                ", periodInMonths=" + periodInMonths +
                '}';
    }
}
