package pck2;

public class WorkRecordPair {

    private WorkRecord workRecord1;
    private WorkRecord workRecord2;

    private long coincidingPeriodLength;

    public long getCoincidingPeriodLength() {
        return coincidingPeriodLength;
    }

    public void setCoincidingPeriodLength(long coincidingPeriodLength) {
        this.coincidingPeriodLength = coincidingPeriodLength;
    }

    public WorkRecordPair() {
    }

    public WorkRecordPair(WorkRecord workRecord1, WorkRecord workRecord2) {
        this.workRecord1 = workRecord1;
        this.workRecord2 = workRecord2;
    }

    public WorkRecord getWorkRecord1() {
        return workRecord1;
    }

    public void setWorkRecord1(WorkRecord workRecord1) {
        this.workRecord1 = workRecord1;
    }

    public WorkRecord getWorkRecord2() {
        return workRecord2;
    }

    public void setWorkRecord2(WorkRecord workRecord2) {
        this.workRecord2 = workRecord2;
    }

    @Override
    public String toString() {
        return "{" +
                "" + workRecord1 +
                ", " + workRecord2 +
                ", " + coincidingPeriodLength +
                '}' + "\n";
    }
}
