package pck;

public class Pair {
    private Employee employee1;
    private Employee employee2;

    private long periodInMonths;

    public Pair() {
    }

    public Pair(Employee employee1, Employee employee2) {
        this.employee1 = employee1;
        this.employee2 = employee2;
    }

    public Employee getEmployee1() {
        return employee1;
    }

    public void setEmployee1(Employee employee1) {
        this.employee1 = employee1;
    }

    public Employee getEmployee2() {
        return employee2;
    }

    public void setEmployee2(Employee employee2) {
        this.employee2 = employee2;
    }

    public long getPeriodInMonths() {
        return periodInMonths;
    }

    public void setPeriodInMonths(long periodInMonths) {
        this.periodInMonths = periodInMonths;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "employeeId1=" + employee1 +
                ", employeeId2=" + employee2 +
                ", periodInMonths=" + periodInMonths +
                '}';
    }
}
