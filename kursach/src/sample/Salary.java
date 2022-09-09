package sample;

public class Salary {
    private int empid;
    private String date;
    private int award;
    private double salary;
    private int timesheetid;
    private double freshsalary;
    private double tax;
    private double fszn;
    private double vznos;

    public Salary(int empid, String date, int award, double salary, int timesheetid) {
        this.empid = empid;
        this.date = date;
        this.award = award;
        this.salary = salary;
        this.timesheetid = timesheetid;
    }

    public Salary(int empid, String date, int award, double salary, int timesheetid, double freshsalary, double tax, double fszn, double vznos) {
        this.empid = empid;
        this.date = date;
        this.award = award;
        this.salary = salary;
        this.timesheetid = timesheetid;
        this.freshsalary = freshsalary;
        this.tax = tax;
        this.fszn = fszn;
        this.vznos = vznos;
    }

    public int getEmpid() {
        return empid;
    }

    public String getDate() {
        return date;
    }

    public int getAward() {
        return award;
    }

    public double getSalary() {
        return salary;
    }

    public int getTimesheetid() {
        return timesheetid;
    }

    public double getFreshsalary() {
        return freshsalary;
    }

    public double getTax() {
        return tax;
    }

    public double getFszn() {
        return fszn;
    }

    public double getVznos() {
        return vznos;
    }
}
