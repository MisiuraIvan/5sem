public class Date {
    private String month;
    private int year;
    private int workhours;

    public Date(String month, int year) {
        this.month = month;
        this.year = year;
    }
    public Date(String month, int year,int workhours) {
        this.month = month;
        this.year = year;
        this.workhours=workhours;
    }

    public int getWorkhours() {
        return workhours;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
