import org.junit.Assert;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DBHandlerTest extends Configs{
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

    @Test
    public void getDataForAvgSalary() {
        ResultSet resSet=null;
        int dateid=0;
        String month="сентябрь";
        int year=2021;
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
        select = "SELECT "+"SUM("+Const.SALARY_SALARY+"),COUNT("+Const.SALARY_SALARY+")"+" FROM " + Const.SALARY_TABLE+" INNER JOIN "+Const.TIMESHEET_TABLE+" ON "+Const.TIMESHEET_TABLE+"."+Const.TIMESHEET_ID+" = "+Const.SALARY_TABLE+"."+Const.SALARY_TIMESHEETID+" WHERE "+Const.USER_ID+"='"+1+"' AND "+Const.DATE_ID+"<'"+dateid+"'";
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
        DBHandler ob=new DBHandler();
        String s=ob.getDataForAvgSalary(1,month, year);
        Assert.assertEquals(s,str);
    }

    @Test
    public void getDataForDiagram() {
        ResultSet resSet=null;
        ArrayList<String> str=new ArrayList<String>();
        String select="SELECT DISTINCT "+Const.DATE_MONTH+","+Const.DATE_YEAR+", SUM("+Const.SALARY_SALARY+")"+" FROM "+Const.SALARY_TABLE+" INNER JOIN "+Const.TIMESHEET_TABLE+" ON "+Const.TIMESHEET_TABLE+"."+Const.TIMESHEET_ID+" = "+Const.SALARY_TABLE+"."+Const.SALARY_TIMESHEETID+" INNER JOIN "+Const.DATE_TABLE+" ON "+Const.DATE_TABLE+"."+Const.DATE_ID+" = "+Const.TIMESHEET_TABLE+"."+Const.TIMESHEET_DATE+" GROUP BY "+Const.DATE_MONTH+","+Const.DATE_YEAR;
        System.out.println(select);
        try{
            Statement st=getDbConnection().createStatement();
            resSet=st.executeQuery(select);
            while (resSet.next()) {
                str.add(resSet.getString(Const.DATE_MONTH)+" "+resSet.getInt(Const.DATE_YEAR)+" "+resSet.getDouble("SUM("+Const.SALARY_SALARY+")"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        DBHandler ob=new DBHandler();
        ArrayList<String> s=ob.getDataForDiagram();
        Assert.assertEquals(s,str);
    }

    @Test
    public void getDataForGraphic() {
        ResultSet resSet=null;
        ArrayList<String> str=new ArrayList<String>();
        String select="SELECT DISTINCT "+Const.DATE_MONTH+","+Const.DATE_YEAR+","+Const.SALARY_SALARY+" FROM "+Const.SALARY_TABLE+" INNER JOIN "+Const.TIMESHEET_TABLE+" ON "+Const.TIMESHEET_TABLE+"."+Const.TIMESHEET_ID+" = "+Const.SALARY_TABLE+"."+Const.SALARY_TIMESHEETID+" INNER JOIN "+Const.DATE_TABLE+" ON "+Const.DATE_TABLE+"."+Const.DATE_ID+" = "+Const.TIMESHEET_TABLE+"."+Const.TIMESHEET_DATE+" WHERE "+Const.USER_ID+"="+1;
        try{
            Statement st=getDbConnection().createStatement();
            resSet=st.executeQuery(select);
            while (resSet.next()) {
                str.add(resSet.getString(Const.DATE_MONTH)+" "+resSet.getInt(Const.DATE_YEAR)+" "+resSet.getDouble(Const.SALARY_SALARY));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        DBHandler ob=new DBHandler();
        ArrayList<String> s=ob.getDataForGraphic(1);
        Assert.assertEquals(s,str);
    }
}