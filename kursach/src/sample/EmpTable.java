package sample;

public class EmpTable {
    private int id;
    private String sname;
    private  String name;
    private String middlename;
    private  String post;
    private int oklad;

    public EmpTable(int id, String sname, String name, String middlename, String post,int oklad) {
        this.id = id;
        this.sname = sname;
        this.name = name;
        this.middlename = middlename;
        this.post = post;
        this.oklad=oklad;
    }

    public int getOklad(){
        return oklad;
    }

    public int getId() {
        return id;
    }

    public String getSname() {
        return sname;
    }

    public String getName() {
        return name;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getPost() {
        return post;
    }
}
