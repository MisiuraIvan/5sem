import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class Server {
    static String r;
    static int counter=0;
    public static void main(String[] args){
        Port port=new Port();
        try(ServerSocket server=new ServerSocket(Port.getPort())){
            System.out.println("Server started");
            while (true) {
                try {
                    Socket socket = server.accept();
                        counter++;
                        System.out.println("Client " + counter + " connected");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try (DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
                                     DataInputStream reader = new DataInputStream(socket.getInputStream())) {
                                    while(!socket.isClosed()) {
                                        try {
                                            r = reader.readUTF();
                                            String request[] = r.split(" ");
                                            System.out.println(request[0]);
                                            dataForEmpTable(request, writer);
                                            dataForTimeSheetTable(request, writer);
                                            dataForDate(request, writer);
                                            regUser(request, writer);
                                            logUser(request, writer);
                                            salarySave(request, writer);
                                            dataForSalaryTable(request, writer);
                                            dataForDiagram(request,writer);
                                            dataForGraphic(request,writer);
                                            dataForDateTable(request,writer);
                                            dataForPostTable(request,writer);
                                            addPost(request,writer);
                                            addDate(request,writer);
                                            addTimesheet(request,writer);
                                            delPost(request,writer);
                                            delDate(request,writer);
                                            delTimesheet(request,writer);
                                            delEmp(request,writer);
                                            correctTimesheet(request,writer);
                                            correctEmp(request,writer);
                                            dataForWorkHours(request,writer);
                                            dataForAvgSalary(request,writer);
                                            dataForEmpTableBySname(request,writer);
                                            createReport(request,writer);
                                            createReport1(request,writer);
                                        }catch (SocketException ex){
                                            System.out.println("Client " + counter + " disconnected");
                                            try {
                                                socket.close();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } finally {
                                    try {
                                        socket.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).start();
                }catch(NullPointerException e){
                    e.printStackTrace();
                }
            }
        }catch(IOException e){
            throw new RuntimeException();
        }
    }

    private static void createReport(String[] request, DataOutputStream writer) {
        if(request[0].equals("createreport")){
            Date date1=new Date(request[1],Integer.parseInt(request[2]));
            Date date2=new Date(request[3],Integer.parseInt(request[4]));
            DBHandler db=new DBHandler();
            db.createReport(date1,date2);
        }
    }

    private static void createReport1(String[] request, DataOutputStream writer) {
        if(request[0].equals("createreport1")){
            Date date1=new Date(request[1],Integer.parseInt(request[2]));
            Date date2=new Date(request[3],Integer.parseInt(request[4]));
            DBHandler db=new DBHandler();
            db.createReport1(date1,date2,Integer.parseInt(request[5]));
        }
    }

    private static void dataForAvgSalary(String[] request, DataOutputStream writer) {
        if(request[0].equals("dataforavgsalary")){
            DBHandler db=new DBHandler();
            String str=db.getDataForAvgSalary(Integer.parseInt(request[1]),request[2],Integer.parseInt(request[3]));
            universwriter(writer,str);
        }
    }

    private static void dataForWorkHours(String[] request, DataOutputStream writer) {
        if(request[0].equals("dataforworkhours")){
            DBHandler db=new DBHandler();
            Date date=new Date(request[1],Integer.parseInt(request[2]));
            String str=db.getDataForWorkHours(date);
            universwriter(writer,str);
        }
    }


    private static void delPost(String[] request, DataOutputStream writer) {
        if(request[0].equals("delpost")){
            DBHandler db=new DBHandler();
            Post post=new Post(request[1],Integer.parseInt(request[2]));
            String str=db.delPost(post);
            universwriter(writer,str);
        }
    }
    private static void delDate(String[] request, DataOutputStream writer) {
        if(request[0].equals("deldate")){
            DBHandler db=new DBHandler();
            Date date=new Date(request[1],Integer.parseInt(request[2]));
            String str=db.delDate(date);
            universwriter(writer,str);
        }
    }

    private static void delEmp(String[] request, DataOutputStream writer) {
        if(request[0].equals("delemp")){
            DBHandler db=new DBHandler();
            User user=new UserClassBuilser().setSname(request[1]).setName(request[2]).setMiddlename(request[3]).setPost(request[4]).build();
            String str=db.delEmp(user);
            universwriter(writer,str);
        }
    }

    private static void delTimesheet(String[] request, DataOutputStream writer) {
        if(request[0].equals("deltimesheet")){
            DBHandler db=new DBHandler();
            Timesheet timesheet=new Timesheet(Integer.parseInt(request[1]),request[2],Integer.parseInt(request[3]),Integer.parseInt(request[4]),Integer.parseInt(request[5]),Integer.parseInt(request[6]),Integer.parseInt(request[7]));
            String str=db.delTimesheet(timesheet);
            universwriter(writer,str);
        }
    }

    private static void correctEmp(String[] request, DataOutputStream writer) {
        if(request[0].equals("correctemp")){
            DBHandler db=new DBHandler();
            User user=new UserClassBuilser().setSname(request[2]).setName(request[3]).setMiddlename(request[4]).setPost(request[5]).build();
            String str=db.correctEmp(user,request[1]);
            universwriter(writer,str);
        }
    }

    private static void correctTimesheet(String[] request, DataOutputStream writer) {
        if(request[0].equals("correcttimesheet")){
            DBHandler db=new DBHandler();
            Timesheet timesheet=new Timesheet(Integer.parseInt(request[2]),request[3],Integer.parseInt(request[4]),Integer.parseInt(request[5]),Integer.parseInt(request[6]),Integer.parseInt(request[7]),Integer.parseInt(request[8]));
            String str=db.correctTimesheet(timesheet,request[1]);
            universwriter(writer,str);
        }
    }


    private static void addPost(String[] request, DataOutputStream writer) {
        if(request[0].equals("addpost")){
            DBHandler db=new DBHandler();
            Post post=new Post(request[1],Integer.parseInt(request[2]));
            String str=db.addPost(post);
            universwriter(writer,str);
        }
    }
    private static void addDate(String[] request, DataOutputStream writer) {
        if(request[0].equals("adddate")){
            DBHandler db=new DBHandler();
            Date date=new Date(request[1],Integer.parseInt(request[2]),Integer.parseInt(request[3]));
            String str=db.addDate(date);
            universwriter(writer,str);
        }
    }
    private static void addTimesheet(String[] request, DataOutputStream writer) {
        if(request[0].equals("addtimesheet")){
            DBHandler db=new DBHandler();
            Timesheet timesheet=new Timesheet(Integer.parseInt(request[1]),request[2],Integer.parseInt(request[3]),Integer.parseInt(request[4]),Integer.parseInt(request[5]),Integer.parseInt(request[6]),Integer.parseInt(request[7]));
            String str=db.addTimesheet(timesheet);
            universwriter(writer,str);
        }
    }
    private static void dataForDiagram(String[] request, DataOutputStream writer) {
        if(request[0].equals("datafordiagram")){
            DBHandler db=new DBHandler();
            ArrayList<String> data=db.getDataForDiagram();
            for(int i=0;i< data.size();i++){
                if(i==data.size()-1){
                    universwriter(writer,data.get(i)+" end");
                }
                else
                    universwriter(writer,data.get(i)+" q");
            }
        }
    }

    private static void dataForGraphic(String[] request, DataOutputStream writer) {
        if(request[0].equals("dataforgraphic")){
            DBHandler db=new DBHandler();
            ArrayList<String> data=db.getDataForGraphic(Integer.parseInt(request[1]));
            for(int i=0;i< data.size();i++){
                if(i==data.size()-1){
                    universwriter(writer,data.get(i)+" end");
                }
                else
                    universwriter(writer,data.get(i)+" q");
            }
        }
    }

    private static void dataForSalaryTable(String[] request, DataOutputStream writer) {
        if(request[0].equals("dataforsalarytable")){
            DBHandler db=new DBHandler();
            ArrayList<String> data=db.getDataForSalaryTable(request[1]);
            for(int i=0;i< data.size();i++){
                if(i==data.size()-1){
                    universwriter(writer,data.get(i)+" end");
                }
                else
                    universwriter(writer,data.get(i)+" q");
            }
        }
    }


    private static void dataForDateTable(String[] request, DataOutputStream writer) {
        if(request[0].equals("datafordatetable")){
            DBHandler db=new DBHandler();
            ArrayList<String> data=db.getDataForDateTable();
            for(int i=0;i< data.size();i++){
                if(i==data.size()-1){
                    universwriter(writer,data.get(i)+" end");
                }
                else
                    universwriter(writer,data.get(i)+" q");
            }
        }
    }

    private static void dataForPostTable(String[] request, DataOutputStream writer) {
        if(request[0].equals("dataforposttable")){
            DBHandler db=new DBHandler();
            ArrayList<String> data=db.getDataForPostTable();
            for(int i=0;i< data.size();i++){
                if(i==data.size()-1){
                    universwriter(writer,data.get(i)+" end");
                }
                else
                    universwriter(writer,data.get(i)+" q");
            }
        }
    }

    public static void salarySave(String[] request,DataOutputStream writer){
        if(request[0].equals("salarysave")) {
            Salary salary=new Salary();
            salary.setTimesheetid(Integer.parseInt(request[1]));
            salary.setAward(Integer.parseInt(request[2]));
            salary.setSalary(Double.parseDouble(request[3]));
            salary.setFreshsalary(Double.parseDouble(request[4]));
            salary.setTax(Double.parseDouble(request[5]));
            salary.setFszn(Double.parseDouble(request[6]));
            salary.setVznos(Double.parseDouble(request[7]));
            DBHandler db=new DBHandler();
            db.addSalary(salary);
            universwriter(writer,"ok");
        }
    }

    public static void dataForDate(String[] request,DataOutputStream writer){
        if(request[0].equals("datafordate")){
            DBHandler db=new DBHandler();
            ArrayList<String> data=db.getDataForDate();
            for(int i=0;i< data.size();i++){
                if(i==data.size()-1){
                    universwriter(writer,data.get(i)+" end");
                }
                else
                    universwriter(writer,data.get(i)+" q");
            }
        }
    }
    public static void dataForTimeSheetTable(String[] request,DataOutputStream writer){
        if(request[0].equals("datafortimesheettable")){
            DBHandler db=new DBHandler();
            ArrayList<String> data=db.getDataForTimeSheetTable(request[1]);
            for(int i=0;i< data.size();i++){
                if(i==data.size()-1){
                    universwriter(writer,data.get(i)+" end");
                }
                else
                    universwriter(writer,data.get(i)+" q");
            }
        }
    }

    public static void dataForEmpTable(String[] request,DataOutputStream writer){
        if(request[0].equals("dataforemptables")){
            DBHandler db=new DBHandler();
            ArrayList<String> data=db.getDataForEmpTable(request[1]);
            for(int i=0;i< data.size();i++){
                if(i==data.size()-1){
                    universwriter(writer,data.get(i)+" end");
                }
                else
                    universwriter(writer,data.get(i)+" q");
            }
        }
    }

    public static void dataForEmpTableBySname(String[] request,DataOutputStream writer){
        if(request[0].equals("dataforemptablebysname")){
            DBHandler db=new DBHandler();
            ArrayList<String> data=db.getDataForEmpTableBySname(request[1]);
            for(int i=0;i< data.size();i++){
                if(i==data.size()-1){
                    universwriter(writer,data.get(i)+" end");
                }
                else
                    universwriter(writer,data.get(i)+" q");
            }
        }
    }

    public static void logUser(String[] request,DataOutputStream writer){
        if(request[0].equals("loginuser")) {
            User user=new UserClassBuilser().setLogin(request[1]).setPassword(request[2]).build();
            DBHandler db = new DBHandler();
            User result = db.getUser(user);
            if (result.getPost()==null) {
                universwriter(writer, "no");
            } else {
                if (result.getPost().equals("Директор")) {
                    universwriter(writer, "dir");
                } else {
                    if (result.getPost().equals("Бухгалтер"))
                        universwriter(writer, "accounter");
                    else {
                        universwriter(writer, "user "+result.getId());
                    }
                }
            }
        }
    }

    public static void universwriter(DataOutputStream writer,String str){
        try {
            writer.writeUTF(str);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void regUser(String[] request,DataOutputStream writer){
        if(request[0].equals("reguser")){
            User user=new User(request[1],request[2],request[3],request[4],request[5],request[6]);
            System.out.println(user.toString());
            DBHandler db=new DBHandler();
            if(user.getPost().equals("Директор")||user.getPost().equals("Бухгалтер")) {
                universwriter(writer, "nopost");
            }else {
                int result = db.getPostId(user.getPost());
                if (db.checkLogin(user.getLogin()) == false) {
                    universwriter(writer, "no");
                } else {
                    if (result > 0) {
                        db.addUser(user, result);
                        universwriter(writer, "ok");
                    } else {
                        universwriter(writer, "nopost");
                    }
                }
            }
        }
    }
}
