package com.diman.employees.beans;

import java.util.Objects;

public class EmployeePair {

    private Employee employee1;
    private Employee employee2;

    public EmployeePair() {
    }

    public EmployeePair(Employee employee1, Employee employee2) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeePair that = (EmployeePair) o;
        return (employee1.equals(that.employee1) && employee2.equals(that.employee2)) ||
                (employee1.equals(that.employee2) && employee2.equals(that.employee1));
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee1, employee2);
    }

    @Override
    public String toString() {
        return "" +
                "" + employee1 +
                ", " + employee2 +
                '}';
    }
}
