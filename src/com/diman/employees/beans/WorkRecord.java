package com.diman.employees.beans;

public class WorkRecord {

    private Employee employee;

    private int projectId;

    private Period period;

    public WorkRecord() {
    }

    public WorkRecord(Employee employee, int projectId, Period period) {
        this.employee = employee;
        this.projectId = projectId;
        this.period = period;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    @Override
    public String toString() {
        return "{" +
                "" + employee +
                ", " + projectId +
                ", " + period +
                '}' + "\n";
    }
}
