package pck2;

import java.util.List;

public class EmployeePairWithTotalPeriodLength {

    private EmployeePair employeePair;

    private long totalPeriodLength;

    public EmployeePairWithTotalPeriodLength() {
    }

    public EmployeePairWithTotalPeriodLength(EmployeePair employeePair, long totalPeriodLength) {
        this.employeePair = employeePair;
        this.totalPeriodLength = totalPeriodLength;
    }

    public EmployeePair getEmployeePair() {
        return employeePair;
    }

    public void setEmployeePair(EmployeePair employeePair) {
        this.employeePair = employeePair;
    }

    public long getTotalPeriodLength() {
        return totalPeriodLength;
    }

    public void setTotalPeriodLength(long totalPeriodLength) {
        this.totalPeriodLength = totalPeriodLength;
    }

    @Override
    public String toString() {
        return "{" +
                "" + employeePair +
                "," + totalPeriodLength +
                '}' + "\n";
    }
}
