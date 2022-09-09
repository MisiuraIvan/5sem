public class Timesheet {
    private int empid;
    private String month;
    private int year;
    private int worktime;
    private int seak;
    private int holidays;
    private int overtime;

    public Timesheet(int empid, String month,int year, int worktime, int seak, int holidays, int overtime) {
        this.empid = empid;
        this.month = month;
        this.year=year;
        this.worktime = worktime;
        this.seak = seak;
        this.holidays = holidays;
        this.overtime = overtime;
    }

    public int getEmpid() {
        return empid;
    }

    public String getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getWorktime() {
        return worktime;
    }

    public int getSeak() {
        return seak;
    }

    public int getHolidays() {
        return holidays;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public void setWorktime(int worktime) {
        this.worktime = worktime;
    }

    public void setSeak(int seak) {
        this.seak = seak;
    }

    public void setHolidays(int holidays) {
        this.holidays = holidays;
    }

    public void setOvertime(int overtime) {
        this.overtime = overtime;
    }

    public int getOvertime() {
        return overtime;
    }
}
