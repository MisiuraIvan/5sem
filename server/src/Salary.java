public class Salary {
    int timesheetid;
    int award;
    double salary;
    double freshsalary;
    double tax;
    double fszn;
    double vznos;

    public Salary() {

    }

    public void setTimesheetid(int timesheetid) {
        this.timesheetid = timesheetid;
    }

    public void setAward(int award) {
        this.award = award;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setFreshsalary(double freshsalary) {
        this.freshsalary = freshsalary;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public void setFszn(double fszn) {
        this.fszn = fszn;
    }

    public void setVznos(double vznos) {
        this.vznos = vznos;
    }

    public int getTimesheetid() {
        return timesheetid;
    }

    public int getAward() {
        return award;
    }

    public double getSalary() {
        return salary;
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

    public Salary(int timesheetid, int award, double salary, double freshsalary, double tax, double fszn, double vznos) {
        this.timesheetid = timesheetid;
        this.award = award;
        this.salary = salary;
        this.freshsalary = freshsalary;
        this.tax = tax;
        this.fszn = fszn;
        this.vznos = vznos;
    }
}
