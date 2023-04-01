package pck2;

import java.time.LocalDate;

import static java.lang.Math.abs;
import static pck2.EmployeeService.TIME_UNIT;

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
