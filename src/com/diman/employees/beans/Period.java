package com.diman.employees.beans;

import java.time.LocalDate;

import static com.diman.employees.impl.EmployeeService.TIME_UNIT;
import static java.lang.Math.abs;

public class Period {

    private LocalDate dateFrom;
    private LocalDate dateTo;

    public Period() {
    }

    public Period(LocalDate dateFrom, LocalDate dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public long getLength(){
        return abs(dateFrom.until(dateTo, TIME_UNIT));
    }

    @Override
    public String toString() {
        return "{" +
                "" + dateFrom +
                ", " + dateTo +
                '}';
    }
}
