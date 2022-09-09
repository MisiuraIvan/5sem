public class User extends Person{
    String login;
    String password;
    String post;
    int id;

    public User(String login, String password,String name, String sname, String middlename,  String post) {
        this.name = name;
        this.sname = sname;
        this.login = login;
        this.password = password;
        this.post = post;
        this.middlename = middlename;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getMiddlename() {
        return middlename;
    }

    @Override
    public String getSname() {
        return sname;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getPost() {
        return post;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", sname='" + sname + '\'' +
                ", middlename='" + middlename + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", post='" + post + '\'' +
                '}';
    }
}
