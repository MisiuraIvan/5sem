package sample;

public class TimeSheet {
    private int timesheetid;
    private int empid;
    private String date;
    private int worktime;
    private int seaktime;
    private int holidays;
    private int overtime;

    public TimeSheet(int timesheetid, int empid, String date, int worktime, int sicktime, int holidays, int overtime) {
        this.timesheetid = timesheetid;
        this.empid = empid;
        this.date = date;
        this.worktime = worktime;
        this.seaktime = sicktime;
        this.holidays = holidays;
        this.overtime = overtime;
    }

    public int getTimesheetid() {
        return timesheetid;
    }

    public int getEmpid() {
        return empid;
    }

    public String getDate() {
        return date;
    }

    public int getWorktime() {
        return worktime;
    }

    public int getSeaktime() {
        return seaktime;
    }

    public int getHolidays() {
        return holidays;
    }

    public int getOvertime() {
        return overtime;
    }
}
