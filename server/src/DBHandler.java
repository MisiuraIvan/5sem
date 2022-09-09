import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.io.File;
import java.io.FileOutputStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class DBHandler extends Configs{
    Connection dbConnection;
    public Connection getDbConnection(){
        try {
            String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
            Class.forName("com.mysql.jdbc.Driver");
            dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPassword);
        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return dbConnection;
    }
    public void addUser(User user,int id){

        String insert="INSERT INTO "+Const.USER_TABLE+"("+Const.USER_FIRSTNAME+","+Const.USER_LASTNAME+","+Const.USER_LOGIN+","+Const.USER_PASSWORD+","+Const.USER_MIDDLENAME+","+Const.USER_POST+")VALUES(?,?,?,?,?,?)";
        try{
            String pas="";
            PreparedStatement prSt=getDbConnection().prepareStatement(insert);
            try {
                MessageDigest digest=MessageDigest.getInstance("SHA-256");
                byte[] hash=digest.digest(user.getPassword().getBytes(StandardCharsets.UTF_8));
                for(int i=0;i<hash.length;i++){
                    pas+=hash[i];
                }
                System.out.println(pas);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            prSt.setString(1, user.getName());
            prSt.setString(2, user.getSname());
            prSt.setString(3, user.getLogin());
            prSt.setString(4, pas);
            prSt.setString(5, user.getMiddlename());
            prSt.setInt(6, id);
            prSt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public User getUser(User user){
        ResultSet resSet=null;
        String pas="";
        MessageDigest digest= null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash=digest.digest(user.getPassword().getBytes(StandardCharsets.UTF_8));
            for(int i=0;i<hash.length;i++){
                pas+=hash[i];
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println(pas);
        String select="SELECT "+Const.USER_LOGIN+","+Const.USER_PASSWORD+","+Const.USER_ID+","+Const.POSTS_POST+" FROM "+Const.USER_TABLE+" INNER JOIN "+Const.POSTS_TABLE+" ON "+Const.USER_TABLE+"."+Const.USER_POST+"="+Const.POSTS_TABLE+"."+Const.POSTS_ID+" WHERE "+Const.USER_LOGIN+"='"+user.getLogin()+"' AND "+Const.USER_PASSWORD+"='"+pas+"'";
        System.out.println(select);
        User us=new User();
        try{
        Statement st=getDbConnection().createStatement();
        resSet=st.executeQuery(select);
        while (resSet.next()) {
            System.out.println(resSet.getString(Const.POSTS_POST));
            us.setPost(resSet.getString(Const.POSTS_POST));
            us.setId(resSet.getInt(Const.USER_ID));
        }
    }catch(SQLException e){
        e.printStackTrace();
    }
        return us;
    }

    public int getPostId(String post){
        ResultSet resSet=null;
        String select="SELECT "+Const.POSTS_ID+" FROM "+Const.POSTS_TABLE+" WHERE "+Const.POSTS_POST+"='"+post+"'";
        System.out.println(select);
        int res=0;
        try{
            Statement st=getDbConnection().createStatement();
            resSet=st.executeQuery(select);
            while (resSet.next()) {
                System.out.println(resSet.getInt(Const.POSTS_ID));
                res=resSet.getInt(Const.POSTS_ID);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return res;
    }

    public boolean checkLogin(String login){
        ResultSet resSet=null;
        String select="SELECT "+Const.USER_LOGIN+" FROM "+Const.USER_TABLE;
        System.out.println(select);
        boolean res=true;
        try{
            Statement st=getDbConnection().createStatement();
            resSet=st.executeQuery(select);
            while (resSet.next()) {
                if(resSet.getString(Const.USER_LOGIN).equals(login)){
                    res=false;
                    break;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return res;
    }

    public ArrayList<String> getDataForEmpTable(String s){
        ResultSet resSet=null;
        ArrayList<String> str=new ArrayList<String>();
        String select;
        if(s.equals("t")) {
            select = "SELECT " + Const.USER_ID + "," + Const.USER_LASTNAME + "," + Const.USER_FIRSTNAME + "," + Const.USER_MIDDLENAME + "," + Const.POSTS_POST + "," + Const.POSTS_OKLAD + " FROM " + Const.USER_TABLE + " INNER JOIN " + Const.POSTS_TABLE + " ON " + Const.USER_TABLE + "." + Const.USER_POST + " = " + Const.POSTS_TABLE + "." + Const.POSTS_ID;
        }else{
            select = "SELECT " + Const.USER_ID + "," + Const.USER_LASTNAME + "," + Const.USER_FIRSTNAME + "," + Const.USER_MIDDLENAME + "," + Const.POSTS_POST + "," + Const.POSTS_OKLAD + " FROM " + Const.USER_TABLE + " INNER JOIN " + Const.POSTS_TABLE + " ON " + Const.USER_TABLE + "." + Const.USER_POST + " = " + Const.POSTS_TABLE + "." + Const.POSTS_ID+" WHERE "+Const.USER_ID+"='"+s/*Integer.parseInt(s)*/+"'";
        }
        System.out.println(select);
        try{
            Statement st=getDbConnection().createStatement();
            resSet=st.executeQuery(select);
            while (resSet.next()) {
                System.out.println(resSet.getInt(Const.USER_ID)+" "+resSet.getString(Const.USER_LASTNAME)+" "+resSet.getString(Const.USER_FIRSTNAME)+" "+resSet.getString(Const.USER_MIDDLENAME)+" "+resSet.getString(Const.POSTS_POST));
                str.add(resSet.getInt(Const.USER_ID)+" "+resSet.getString(Const.USER_LASTNAME)+" "+resSet.getString(Const.USER_FIRSTNAME)+" "+resSet.getString(Const.USER_MIDDLENAME)+" "+resSet.getString(Const.POSTS_POST)+" "+resSet.getInt(Const.POSTS_OKLAD));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return str;
    }

    public ArrayList<String> getDataForEmpTableBySname(String sname){
        ResultSet resSet=null;
        ArrayList<String> str=new ArrayList<String>();
        String select;
        select = "SELECT " + Const.USER_ID + "," + Const.USER_LASTNAME + "," + Const.USER_FIRSTNAME + "," + Const.USER_MIDDLENAME + "," + Const.POSTS_POST + "," + Const.POSTS_OKLAD + " FROM " + Const.USER_TABLE + " INNER JOIN " + Const.POSTS_TABLE + " ON " + Const.USER_TABLE + "." + Const.USER_POST + " = " + Const.POSTS_TABLE + "." + Const.POSTS_ID+" WHERE "+Const.USER_LASTNAME+"='"+sname+"'";
        System.out.println(select);
        try{
            Statement st=getDbConnection().createStatement();
            resSet=st.executeQuery(select);
            while (resSet.next()) {
                System.out.println(resSet.getInt(Const.USER_ID)+" "+resSet.getString(Const.USER_LASTNAME)+" "+resSet.getString(Const.USER_FIRSTNAME)+" "+resSet.getString(Const.USER_MIDDLENAME)+" "+resSet.getString(Const.POSTS_POST));
                str.add(resSet.getInt(Const.USER_ID)+" "+resSet.getString(Const.USER_LASTNAME)+" "+resSet.getString(Const.USER_FIRSTNAME)+" "+resSet.getString(Const.USER_MIDDLENAME)+" "+resSet.getString(Const.POSTS_POST)+" "+resSet.getInt(Const.POSTS_OKLAD));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return str;
    }


    public ArrayList<String> getDataForTimeSheetTable(String s){
        ResultSet resSet=null;
        ArrayList<String> str=new ArrayList<String>();
        String select;
        if(s.equals("t")) {
            select = "SELECT " + Const.TIMESHEET_ID + "," + Const.USER_ID + "," + Const.DATE_MONTH + "," + Const.DATE_YEAR + "," + Const.TIMESHEET_WORKTIME + "," + Const.TIMESHEET_SEAKTIME + "," + Const.TIMESHEET_HOLIDAY + "," + Const.TIMESHEET_OVERTIME + " FROM " + Const.TIMESHEET_TABLE + " INNER JOIN " + Const.DATE_TABLE + " ON " + Const.TIMESHEET_TABLE + "." + Const.TIMESHEET_DATE + " = " + Const.DATE_TABLE + "." + Const.DATE_ID;
        }else{
            select = "SELECT " + Const.TIMESHEET_ID + "," + Const.USER_ID + "," + Const.DATE_MONTH + "," + Const.DATE_YEAR + "," + Const.TIMESHEET_WORKTIME + "," + Const.TIMESHEET_SEAKTIME + "," + Const.TIMESHEET_HOLIDAY + "," + Const.TIMESHEET_OVERTIME + " FROM " + Const.TIMESHEET_TABLE + " INNER JOIN " + Const.DATE_TABLE + " ON " + Const.TIMESHEET_TABLE + "." + Const.TIMESHEET_DATE + " = " + Const.DATE_TABLE + "." + Const.DATE_ID+" WHERE "+Const.USER_ID+"='"+Integer.parseInt(s)+"'";
        }
        System.out.println(select);
        try{
            Statement st=getDbConnection().createStatement();
            resSet=st.executeQuery(select);
            while (resSet.next()) {
                str.add(resSet.getInt(Const.TIMESHEET_ID)+" "+resSet.getInt(Const.USER_ID)+" "+resSet.getString(Const.DATE_MONTH)+" "+resSet.getInt(Const.DATE_YEAR)+" "+resSet.getInt(Const.TIMESHEET_WORKTIME)+" "+resSet.getInt(Const.TIMESHEET_SEAKTIME)+" "+resSet.getInt(Const.TIMESHEET_HOLIDAY)+" "+resSet.getInt(Const.TIMESHEET_OVERTIME));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return str;
    }

    public ArrayList<String> getDataForSalaryTable(String s){
        ResultSet resSet=null;
        ArrayList<String> str=new ArrayList<String>();
        String select;
        if(s.equals("t")) {
            select = "SELECT " + Const.USER_ID + "," + Const.DATE_MONTH + "," + Const.DATE_YEAR + "," + Const.SALARY_AWARD + "," + Const.SALARY_SALARY + "," + Const.SALARY_TABLE + "." + Const.TIMESHEET_ID +","+Const.SALARY_FRESHSALARY+","+Const.SALARY_TAX+","+Const.SALARY_FSZN+","+Const.SALARY_VZNOS+" FROM " + Const.SALARY_TABLE + " INNER JOIN " + Const.TIMESHEET_TABLE + " ON " + Const.TIMESHEET_TABLE + "." + Const.TIMESHEET_ID + " = " + Const.SALARY_TABLE + "." + Const.SALARY_TIMESHEETID + " INNER JOIN " + Const.DATE_TABLE + " ON " + Const.DATE_TABLE + "." + Const.DATE_ID + " = " + Const.TIMESHEET_TABLE + "." + Const.TIMESHEET_DATE;
        }else {
                select = "SELECT " + Const.USER_ID + "," + Const.DATE_MONTH + "," + Const.DATE_YEAR + "," + Const.SALARY_AWARD + "," + Const.SALARY_SALARY + "," + Const.SALARY_TABLE + "." + Const.TIMESHEET_ID +","+Const.SALARY_FRESHSALARY+","+Const.SALARY_TAX+","+Const.SALARY_FSZN+","+Const.SALARY_VZNOS+" FROM " + Const.SALARY_TABLE + " INNER JOIN " + Const.TIMESHEET_TABLE + " ON " + Const.TIMESHEET_TABLE + "." + Const.TIMESHEET_ID + " = " + Const.SALARY_TABLE + "." + Const.SALARY_TIMESHEETID + " INNER JOIN " + Const.DATE_TABLE + " ON " + Const.DATE_TABLE + "." + Const.DATE_ID + " = " + Const.TIMESHEET_TABLE + "." + Const.TIMESHEET_DATE+ " WHERE " + Const.USER_ID + "='" + Integer.parseInt(s) + "'";
        }
        System.out.println(select);
        try{
            Statement st=getDbConnection().createStatement();
            resSet=st.executeQuery(select);
            if(s.equals("t")) {
                while (resSet.next()) {
                    str.add(resSet.getInt(Const.USER_ID) + " " + resSet.getString(Const.DATE_MONTH) + " " + resSet.getInt(Const.DATE_YEAR) + " " + resSet.getInt(Const.SALARY_AWARD) + " " + resSet.getDouble(Const.SALARY_SALARY) + " " + resSet.getInt(Const.TIMESHEET_ID) + " " + resSet.getDouble(Const.SALARY_FRESHSALARY) + " " + resSet.getDouble(Const.SALARY_TAX) + " " + resSet.getDouble(Const.SALARY_FSZN) + " " + resSet.getDouble(Const.SALARY_VZNOS));
                }
            }else{
                while (resSet.next()) {
                    str.add(resSet.getInt(Const.USER_ID) + " " + resSet.getString(Const.DATE_MONTH) + " " + resSet.getInt(Const.DATE_YEAR) + " " + resSet.getInt(Const.SALARY_AWARD) + " " + resSet.getDouble(Const.SALARY_SALARY) + " " + resSet.getInt(Const.TIMESHEET_ID));
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return str;
    }

    public ArrayList<String> getDataForDateTable(){
        ResultSet resSet=null;
        ArrayList<String> str=new ArrayList<String>();
        String select;
        select = "SELECT * FROM " + Const.DATE_TABLE;
        System.out.println(select);
        try{
            Statement st=getDbConnection().createStatement();
            resSet=st.executeQuery(select);
            while (resSet.next()) {
                str.add(resSet.getString(Const.DATE_MONTH)+" "+resSet.getInt(Const.DATE_YEAR)+" "+resSet.getInt(Const.DATE_WORKHOURS));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return str;
    }

    public String getDataForWorkHours(Date date){
        ResultSet resSet=null;
        String select;
        String str="";
        select = "SELECT * FROM " + Const.DATE_TABLE+" WHERE "+Const.DATE_YEAR+"='"+date.getYear()+"' AND "+Const.DATE_MONTH+"='"+date.getMonth()+"'";
        System.out.println(select);
        try{
            Statement st=getDbConnection().createStatement();
            resSet=st.executeQuery(select);
            while (resSet.next()) {
                str=resSet.getString(Const.DATE_WORKHOURS);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return str;
    }

    public String getDataForAvgSalary(int id,String month,int year){
        ResultSet resSet=null;
        int dateid=0;
        String select="SELECT "+Const.DATE_ID+" FROM "+Const.DATE_TABLE+" WHERE "+Const.DATE_MONTH+"='"+month+"' AND "+Const.DATE_YEAR+"='"+year+"'";
        try{
            Statement st=getDbConnection().createStatement();
            resSet=st.executeQuery(select);
            while (resSet.next()) {
                dateid=resSet.getInt(Const.DATE_ID);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        String str="";
        double res=0;
        select = "SELECT "+"SUM("+Const.SALARY_SALARY+"),COUNT("+Const.SALARY_SALARY+")"+" FROM " + Const.SALARY_TABLE+" INNER JOIN "+Const.TIMESHEET_TABLE+" ON "+Const.TIMESHEET_TABLE+"."+Const.TIMESHEET_ID+" = "+Const.SALARY_TABLE+"."+Const.SALARY_TIMESHEETID+" WHERE "+Const.USER_ID+"='"+id+"' AND "+Const.DATE_ID+"<'"+dateid+"'";
        try{
            Statement st=getDbConnection().createStatement();
            resSet=st.executeQuery(select);
            while (resSet.next()) {
                res=resSet.getDouble("SUM("+Const.SALARY_SALARY+")")/resSet.getDouble("COUNT("+Const.SALARY_SALARY+")");
                str=""+res;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return str;
    }

    public ArrayList<String> getDataForPostTable(){
        ResultSet resSet=null;
        ArrayList<String> str=new ArrayList<String>();
        String select;
        select = "SELECT * FROM " + Const.POSTS_TABLE;
        System.out.println(select);
        try{
            Statement st=getDbConnection().createStatement();
            resSet=st.executeQuery(select);
            while (resSet.next()) {
                str.add(resSet.getString(Const.POSTS_POST)+" "+resSet.getInt(Const.POSTS_OKLAD));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return str;
    }

    public ArrayList<String> getDataForDate(){
        ResultSet resSet=null;
        ArrayList<String> str=new ArrayList<String>();
        String select="SELECT "+Const.DATE_MONTH+","+Const.DATE_YEAR+" FROM "+Const.DATE_TABLE;
        System.out.println(select);
        try{
            Statement st=getDbConnection().createStatement();
            resSet=st.executeQuery(select);
            while (resSet.next()) {
                str.add(resSet.getString(Const.DATE_MONTH)+" "+resSet.getInt(Const.DATE_YEAR));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return str;
    }

    public void addSalary(Salary salary) {
        ResultSet resSet=null;
        boolean b=false;
        String select="SELECT "+Const.SALARY_TIMESHEETID+" FROM "+Const.SALARY_TABLE;
        System.out.println(select);
        try{
            Statement st=getDbConnection().createStatement();
            resSet=st.executeQuery(select);
            while (resSet.next()) {
                if(resSet.getInt(Const.SALARY_TIMESHEETID)==salary.getTimesheetid()){
                    b=true;
                    break;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        if(b==true) {
            String update = "UPDATE " + Const.SALARY_TABLE + " SET " + Const.SALARY_AWARD + "='" + salary.getAward() + "', " + Const.SALARY_SALARY + "='" + salary.getSalary()+ "', " + Const.SALARY_FRESHSALARY + "='" + salary.getFreshsalary()+ "', " + Const.SALARY_TAX + "='" + salary.getTax()+ "', " + Const.SALARY_FSZN + "='" + salary.getFszn()+ "', " + Const.SALARY_VZNOS + "='" + salary.getVznos() + "' WHERE " + Const.SALARY_TIMESHEETID + "='" + salary.getTimesheetid() + "'";
            System.out.println(update);
            try {
                PreparedStatement pr = getDbConnection().prepareStatement(update);
                pr.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else {
            String insert = "INSERT INTO " + Const.SALARY_TABLE + "(" + Const.SALARY_TIMESHEETID + "," + Const.SALARY_AWARD + "," + Const.SALARY_SALARY+ "," + Const.SALARY_FRESHSALARY+ "," + Const.SALARY_TAX+ "," + Const.SALARY_FSZN+ "," + Const.SALARY_VZNOS + ") VALUES(?,?,?,?,?,?,?)";
            System.out.println(insert);
            try {
                PreparedStatement pr = getDbConnection().prepareStatement(insert);
                pr.setInt(1, salary.getTimesheetid());
                pr.setInt(2, salary.getAward());
                pr.setDouble(3, salary.getSalary());
                pr.setDouble(4, salary.getFreshsalary());
                pr.setDouble(5, salary.getTax());
                pr.setDouble(6, salary.getFszn());
                pr.setDouble(7, salary.getVznos());
                pr.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> getDataForDiagram(){
        ResultSet resSet=null;
        ArrayList<String> str=new ArrayList<String>();
        String select="SELECT DISTINCT "+Const.DATE_MONTH+","+Const.DATE_YEAR+", SUM("+Const.SALARY_SALARY+")"+" FROM "+Const.SALARY_TABLE+" INNER JOIN "+Const.TIMESHEET_TABLE+" ON "+Const.TIMESHEET_TABLE+"."+Const.TIMESHEET_ID+" = "+Const.SALARY_TABLE+"."+Const.SALARY_TIMESHEETID+" INNER JOIN "+Const.DATE_TABLE+" ON "+Const.DATE_TABLE+"."+Const.DATE_ID+" = "+Const.TIMESHEET_TABLE+"."+Const.TIMESHEET_DATE+" GROUP BY "+Const.DATE_MONTH+","+Const.DATE_YEAR;
        try{
            Statement st=getDbConnection().createStatement();
            resSet=st.executeQuery(select);
            while (resSet.next()) {
                str.add(resSet.getString(Const.DATE_MONTH)+" "+resSet.getInt(Const.DATE_YEAR)+" "+resSet.getDouble("SUM("+Const.SALARY_SALARY+")"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return str;
    }

    public ArrayList<String> getDataForGraphic(int id){
        ResultSet resSet=null;
        ArrayList<String> str=new ArrayList<String>();
        String select="SELECT DISTINCT "+Const.DATE_MONTH+","+Const.DATE_YEAR+","+Const.SALARY_SALARY+" FROM "+Const.SALARY_TABLE+" INNER JOIN "+Const.TIMESHEET_TABLE+" ON "+Const.TIMESHEET_TABLE+"."+Const.TIMESHEET_ID+" = "+Const.SALARY_TABLE+"."+Const.SALARY_TIMESHEETID+" INNER JOIN "+Const.DATE_TABLE+" ON "+Const.DATE_TABLE+"."+Const.DATE_ID+" = "+Const.TIMESHEET_TABLE+"."+Const.TIMESHEET_DATE+" WHERE "+Const.USER_ID+"="+id;
        try{
            Statement st=getDbConnection().createStatement();
            resSet=st.executeQuery(select);
            while (resSet.next()) {
                str.add(resSet.getString(Const.DATE_MONTH)+" "+resSet.getInt(Const.DATE_YEAR)+" "+resSet.getDouble(Const.SALARY_SALARY));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return str;
    }

    public String addPost(Post post){
        ResultSet resSet=null;

        String str="ok";
        String insert = "INSERT INTO " + Const.POSTS_TABLE + "(" + Const.POSTS_POST + "," + Const.POSTS_OKLAD+") VALUES(?,?)";
        System.out.println(insert);
        try {
            PreparedStatement pr = getDbConnection().prepareStatement(insert);
            pr.setString(1, post.getPost());
            pr.setInt(2, post.getOklad());
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return str;
    }

    public String addDate(Date date){
        ResultSet resSet=null;
        String str="ok";
        String insert = "INSERT INTO " + Const.DATE_TABLE + "(" + Const.DATE_MONTH + "," + Const.DATE_YEAR+"," + Const.DATE_WORKHOURS+") VALUES(?,?,?)";
        System.out.println(insert);
        try {
            PreparedStatement pr = getDbConnection().prepareStatement(insert);
            pr.setString(1, date.getMonth());
            pr.setInt(2, date.getYear());
            pr.setInt(3, date.getWorkhours());
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return str;
    }

    public String addTimesheet(Timesheet timesheet){
        ResultSet resSet=null;
        int dateid=0;
        String select="SELECT "+Const.DATE_ID+" FROM "+Const.DATE_TABLE+" WHERE "+Const.DATE_MONTH+"='"+timesheet.getMonth()+"' AND "+Const.DATE_YEAR+"='"+timesheet.getYear()+"'";
        System.out.println(select);
        try{
            Statement st=getDbConnection().createStatement();
            resSet=st.executeQuery(select);
            while (resSet.next()) {
                dateid=resSet.getInt(Const.DATE_ID);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        String str="ok";
        String insert = "INSERT INTO " + Const.TIMESHEET_TABLE + "(" + Const.USER_ID + "," + Const.TIMESHEET_DATE+"," + Const.TIMESHEET_WORKTIME+"," + Const.TIMESHEET_SEAKTIME+"," + Const.TIMESHEET_HOLIDAY+"," + Const.TIMESHEET_OVERTIME+") VALUES(?,?,?,?,?,?)";
        System.out.println(insert);
        try {
            PreparedStatement pr = getDbConnection().prepareStatement(insert);
            pr.setInt(1, timesheet.getEmpid());
            pr.setInt(2, dateid);
            pr.setInt(3, timesheet.getWorktime());
            pr.setInt(4, timesheet.getSeak());
            pr.setInt(5, timesheet.getHolidays());
            pr.setInt(6, timesheet.getOvertime());
            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return str;
    }
    
    public String delPost(Post post){
        String str="ok";
        String delete="DELETE FROM "+Const.POSTS_TABLE+" WHERE "+Const.POSTS_POST+"='"+post.getPost()+"' AND "+Const.POSTS_OKLAD+"='"+post.getOklad()+"'";
        PreparedStatement pr= null;
        try {
            pr = getDbConnection().prepareStatement(delete);
            pr.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return str;
    }

    public String delDate(Date date){
        String str="ok";
        String delete="DELETE FROM "+Const.DATE_TABLE+" WHERE "+Const.DATE_MONTH+"='"+date.getMonth()+"' AND "+Const.DATE_YEAR+"='"+date.getYear()+"'";
        PreparedStatement pr= null;
        try {
            pr = getDbConnection().prepareStatement(delete);
            pr.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return str;
    }

    public String delEmp(User user){
        String str="ok";
        String delete="DELETE FROM "+Const.USER_TABLE+" WHERE "+Const.USER_FIRSTNAME+"='"+user.getName()+"' AND "+Const.USER_LASTNAME+"='"+user.getSname()+"' AND "+Const.USER_MIDDLENAME+"='"+user.getMiddlename()+"'";
        PreparedStatement pr= null;
        try {
            pr = getDbConnection().prepareStatement(delete);
            pr.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return str;
    }

    public String correctEmp(User user,String s){
        String str="ok";
        String update="UPDATE "+Const.USER_TABLE+" SET "+Const.USER_FIRSTNAME+"='"+user.getName()+"',"+Const.USER_LASTNAME+"='"+user.getSname()+"',"+Const.USER_MIDDLENAME+"='"+user.getMiddlename()+"' WHERE "+Const.USER_ID+"='"+Integer.parseInt(s)+"'";
        System.out.println(update);
        PreparedStatement pr= null;
        try {
            pr = getDbConnection().prepareStatement(update);
            pr.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return str;
    }

    public String delTimesheet(Timesheet timesheet){
        ResultSet resSet=null;
        int dateid=0;
        String select="SELECT "+Const.DATE_ID+" FROM "+Const.DATE_TABLE+" WHERE "+Const.DATE_MONTH+"='"+timesheet.getMonth()+"' AND "+Const.DATE_YEAR+"='"+timesheet.getYear()+"'";
        System.out.println(select);
        try{
            Statement st=getDbConnection().createStatement();
            resSet=st.executeQuery(select);
            while (resSet.next()) {
                dateid=resSet.getInt(Const.DATE_ID);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        String str="ok";
        String delete="DELETE FROM "+Const.TIMESHEET_TABLE+" WHERE "+Const.TIMESHEET_DATE+"='"+dateid+"' AND "+Const.USER_ID+"='"+timesheet.getEmpid()+"' AND "+Const.TIMESHEET_OVERTIME+"='"+timesheet.getOvertime()+"' AND "+Const.TIMESHEET_WORKTIME+"='"+timesheet.getWorktime()+"'";
        System.out.println(delete);
        PreparedStatement pr= null;
        try {
            pr = getDbConnection().prepareStatement(delete);
            pr.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return str;
    }

    public String correctTimesheet(Timesheet timesheet,String s){
        ResultSet resSet=null;
        int dateid=0;
        String select="SELECT "+Const.DATE_ID+" FROM "+Const.DATE_TABLE+" WHERE "+Const.DATE_MONTH+"='"+timesheet.getMonth()+"' AND "+Const.DATE_YEAR+"='"+timesheet.getYear()+"'";
        System.out.println(select);
        try{
            Statement st=getDbConnection().createStatement();
            resSet=st.executeQuery(select);
            while (resSet.next()) {
                dateid=resSet.getInt(Const.DATE_ID);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        String str="ok";
        String delete="UPDATE "+Const.TIMESHEET_TABLE+" SET "+Const.TIMESHEET_DATE+"='"+dateid+"',"+Const.USER_ID+"='"+timesheet.getEmpid()+"',"+Const.TIMESHEET_WORKTIME+"='"+timesheet.getWorktime()+"',"+Const.TIMESHEET_SEAKTIME+"='"+timesheet.getSeak()+"',"+Const.TIMESHEET_HOLIDAY+"='"+timesheet.getHolidays()+"',"+Const.TIMESHEET_OVERTIME+"='"+timesheet.getOvertime()+"' WHERE "+Const.TIMESHEET_ID+"='"+Integer.parseInt(s)+"'";
        System.out.println(delete);
        PreparedStatement pr= null;
        try {
            pr = getDbConnection().prepareStatement(delete);
            pr.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return str;
    }

    public void createReport(Date date1,Date date2){
        ResultSet resSet=null;
        int id1=0;
        String select="SELECT "+Const.DATE_ID+" FROM "+Const.DATE_TABLE+" WHERE "+Const.DATE_MONTH+"='"+date1.getMonth()+"' AND "+Const.DATE_YEAR+"='"+date1.getYear()+"'";
        System.out.println(select);
        try{
            Statement st=getDbConnection().createStatement();
            resSet=st.executeQuery(select);
            while (resSet.next()) {
                id1=resSet.getInt(Const.DATE_ID);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        int id2=0;
        select="SELECT "+Const.DATE_ID+" FROM "+Const.DATE_TABLE+" WHERE "+Const.DATE_MONTH+"='"+date2.getMonth()+"' AND "+Const.DATE_YEAR+"='"+date2.getYear()+"'";
        System.out.println(select);
        try{
            Statement st=getDbConnection().createStatement();
            resSet=st.executeQuery(select);
            while (resSet.next()) {
                id2=resSet.getInt(Const.DATE_ID);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        select = "SELECT "+ Const.USER_LASTNAME + "," + Const.USER_FIRSTNAME + "," + Const.USER_MIDDLENAME + "," +Const.POSTS_POST+","+Const.DATE_MONTH+","+Const.DATE_YEAR+","+Const.SALARY_SALARY+","+Const.SALARY_FRESHSALARY+" FROM " + Const.SALARY_TABLE +" INNER JOIN " + Const.TIMESHEET_TABLE + " ON " + Const.SALARY_TABLE + "." + Const.SALARY_TIMESHEETID + " = " + Const.TIMESHEET_TABLE + "." + Const.TIMESHEET_ID+" INNER JOIN " + Const.DATE_TABLE + " ON " + Const.TIMESHEET_TABLE + "." + Const.DATE_ID + " = " + Const.DATE_TABLE + "." + Const.DATE_ID+" INNER JOIN " + Const.USER_TABLE + " ON " + Const.TIMESHEET_TABLE + "." + Const.USER_ID + " = " + Const.USER_TABLE + "." + Const.USER_ID+" INNER JOIN " + Const.POSTS_TABLE + " ON " + Const.USER_TABLE + "." + Const.USER_POST + " = " + Const.POSTS_TABLE + "." + Const.POSTS_ID+" WHERE "+Const.TIMESHEET_TABLE+"."+Const.DATE_ID+">='"+id1+"' AND "+Const.TIMESHEET_TABLE+"."+Const.DATE_ID+"<='"+id2+"'";
        System.out.println(select);
        XWPFDocument document = new XWPFDocument();
        try {
            FileOutputStream out = new FileOutputStream(new File("report.docx"));

            document.createParagraph().createRun().setText("   Отчет расчета заработной платы за период: "+date1.getMonth()+" "+date1.getYear()+" по "+date2.getMonth()+" "+date2.getYear());
            document.createParagraph();
            XWPFTable table = document.createTable();
            XWPFTableRow tableRowOne = table.getRow(0);
            tableRowOne.getCell(0).setText("    Фамилия   ");
            tableRowOne.addNewTableCell().setText("   Имя   ");
            tableRowOne.addNewTableCell().setText("    Отчество    ");
            tableRowOne.addNewTableCell().setText("   Должность   ");
            tableRowOne.addNewTableCell().setText("   Дата   ");
            tableRowOne.addNewTableCell().setText("   ЗП   ");
            tableRowOne.addNewTableCell().setText("  ЗП к выдаче  ");
        try{
            Statement st=getDbConnection().createStatement();
            resSet=st.executeQuery(select);
            while (resSet.next()) {
                XWPFTableRow tableRowTwo = table.createRow();
                tableRowTwo.getCell(0).setText(resSet.getString(Const.USER_LASTNAME));
                tableRowTwo.getCell(1).setText(resSet.getString(Const.USER_FIRSTNAME));
                tableRowTwo.getCell(2).setText(resSet.getString(Const.USER_MIDDLENAME));
                tableRowTwo.getCell(3).setText(resSet.getString(Const.POSTS_POST));
                tableRowTwo.getCell(4).setText(resSet.getString(Const.DATE_MONTH) + " " + resSet.getString(Const.DATE_YEAR));
                tableRowTwo.getCell(5).setText(resSet.getString(Const.SALARY_SALARY));
                tableRowTwo.getCell(6).setText(resSet.getString(Const.SALARY_FRESHSALARY));
            }
                document.write(out);
                out.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void createReport1(Date date1,Date date2,int ID){
        ResultSet resSet=null;
        int id1=0;
        String select="SELECT "+Const.DATE_ID+" FROM "+Const.DATE_TABLE+" WHERE "+Const.DATE_MONTH+"='"+date1.getMonth()+"' AND "+Const.DATE_YEAR+"='"+date1.getYear()+"'";
        System.out.println(select);
        try{
            Statement st=getDbConnection().createStatement();
            resSet=st.executeQuery(select);
            while (resSet.next()) {
                id1=resSet.getInt(Const.DATE_ID);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        int id2=0;
        select="SELECT "+Const.DATE_ID+" FROM "+Const.DATE_TABLE+" WHERE "+Const.DATE_MONTH+"='"+date2.getMonth()+"' AND "+Const.DATE_YEAR+"='"+date2.getYear()+"'";
        System.out.println(select);
        try{
            Statement st=getDbConnection().createStatement();
            resSet=st.executeQuery(select);
            while (resSet.next()) {
                id2=resSet.getInt(Const.DATE_ID);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        select = "SELECT "+ Const.USER_LASTNAME + "," + Const.USER_FIRSTNAME + "," + Const.USER_MIDDLENAME + "," +Const.POSTS_POST+","+Const.DATE_MONTH+","+Const.DATE_YEAR+","+Const.SALARY_SALARY+","+Const.SALARY_FRESHSALARY+","+Const.SALARY_VZNOS+","+Const.SALARY_FSZN+","+Const.SALARY_TAX+" FROM " + Const.SALARY_TABLE +" INNER JOIN " + Const.TIMESHEET_TABLE + " ON " + Const.SALARY_TABLE + "." + Const.SALARY_TIMESHEETID + " = " + Const.TIMESHEET_TABLE + "." + Const.TIMESHEET_ID+" INNER JOIN " + Const.DATE_TABLE + " ON " + Const.TIMESHEET_TABLE + "." + Const.DATE_ID + " = " + Const.DATE_TABLE + "." + Const.DATE_ID+" INNER JOIN " + Const.USER_TABLE + " ON " + Const.TIMESHEET_TABLE + "." + Const.USER_ID + " = " + Const.USER_TABLE + "." + Const.USER_ID+" INNER JOIN " + Const.POSTS_TABLE + " ON " + Const.USER_TABLE + "." + Const.USER_POST + " = " + Const.POSTS_TABLE + "." + Const.POSTS_ID+" WHERE "+Const.TIMESHEET_TABLE+"."+Const.DATE_ID+">='"+id1+"' AND "+Const.TIMESHEET_TABLE+"."+Const.DATE_ID+"<='"+id2+"' AND "+Const.USER_TABLE+"."+Const.USER_ID+"='"+ID+"'";
        System.out.println(select);
        XWPFDocument document = new XWPFDocument();
        try {
            FileOutputStream out = new FileOutputStream(new File(String.valueOf(ID)+".docx"));

            document.createParagraph().createRun().setText("   Отчет расчета заработной платы за период: "+date1.getMonth()+" "+date1.getYear()+" по "+date2.getMonth()+" "+date2.getYear());
            document.createParagraph();
            XWPFTable table = document.createTable();
            XWPFTableRow tableRowOne = table.getRow(0);
            document.createParagraph();
            XWPFTable table2 = document.createTable();
            XWPFTableRow tableRowOne2 = table2.getRow(0);

            tableRowOne.getCell(0).setText("   Фамилия   ");
            tableRowOne.addNewTableCell().setText("   Имя   ");
            tableRowOne.addNewTableCell().setText("    Отчество    ");
            tableRowOne.addNewTableCell().setText("   Должность   ");

            tableRowOne2.getCell(0).setText("   Дата   ");
            tableRowOne2.addNewTableCell().setText("   ЗП   ");
            tableRowOne2.addNewTableCell().setText(" Подоходный налог ");
            tableRowOne2.addNewTableCell().setText("Пенсионные отч.");
            tableRowOne2.addNewTableCell().setText("ЗП к выдачи");
            tableRowOne2.addNewTableCell().setText("   ФСЗН   ");
            int k=0;
            try{
                Statement st=getDbConnection().createStatement();
                resSet=st.executeQuery(select);
                while (resSet.next()) {
                    if(k==0) {
                        XWPFTableRow tableRowTwo = table.createRow();
                        tableRowTwo.getCell(0).setText(resSet.getString(Const.USER_LASTNAME));
                        tableRowTwo.getCell(1).setText(resSet.getString(Const.USER_FIRSTNAME));
                        tableRowTwo.getCell(2).setText(resSet.getString(Const.USER_MIDDLENAME));
                        tableRowTwo.getCell(3).setText(resSet.getString(Const.POSTS_POST));
                    }
                    k++;
                    XWPFTableRow tableRowTwo2 = table2.createRow();
                    tableRowTwo2.getCell(0).setText(resSet.getString(Const.DATE_MONTH) + " " + resSet.getString(Const.DATE_YEAR));
                    tableRowTwo2.getCell(1).setText(resSet.getString(Const.SALARY_SALARY));
                    tableRowTwo2.getCell(2).setText(resSet.getString(Const.SALARY_TAX));
                    tableRowTwo2.getCell(3).setText(resSet.getString(Const.SALARY_FSZN));
                    tableRowTwo2.getCell(4).setText(resSet.getString(Const.SALARY_FRESHSALARY));
                    tableRowTwo2.getCell(5).setText(resSet.getString(Const.SALARY_VZNOS));
                }

                document.write(out);
                out.close();
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
