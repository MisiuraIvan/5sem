package sample;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.regex.Matcher;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static java.lang.Double.parseDouble;

public class AdminController {

    ObservableList<EmpTable> emplist= FXCollections.observableArrayList();
    ObservableList<TimeSheet> timesheetlist= FXCollections.observableArrayList();
    ObservableList<Salary> salarylist= FXCollections.observableArrayList();
    ObservableList<Date> datelist= FXCollections.observableArrayList();
    ObservableList<Post> postlist= FXCollections.observableArrayList();
    int m_empid;
    int m_timesheetid;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField salarysearch;

    @FXML
    private TextField empsearch;

    @FXML
    private TextField analyssearch;

    @FXML
    private TextField timesheetsearch;

    @FXML
    private TextField accountsearch;

    @FXML
    private Button empidsearch;

    @FXML
    private Button empsnamesearch;

    @FXML
    private Button timesheetempsearch;

    @FXML
    private Button salaryempsearch;

    @FXML
    private Button analysempsearch;

    @FXML
    private Button adddate;

    @FXML
    private Button addpost;

    @FXML
    private Button addtimesheet;

    @FXML
    private Button correctemp;

    @FXML
    private Button correctpost;

    @FXML
    private Button correcttimesheet;

    @FXML
    private ComboBox<String> date;

    @FXML
    private ComboBox<String> posts;

    @FXML
    private TableColumn<Date, String> datemonth;

    @FXML
    private TableColumn<Date, Integer> dateworkhours;

    @FXML
    private TableView<Date> datetable;

    @FXML
    private TableColumn<Date, Integer> dateyear;

    @FXML
    private Button deldate;

    @FXML
    private Button delemp;

    @FXML
    private Button delpost;

    @FXML
    private Button deltimesheet;

    @FXML
    private TableColumn<EmpTable, Integer> empid;

    @FXML
    private TableColumn<EmpTable, String> empmiddlename;

    @FXML
    private TableColumn<EmpTable, String> empname;

    @FXML
    private TableColumn<EmpTable, Integer> empoklad;

    @FXML
    private TableColumn<EmpTable, String> emppost;

    @FXML
    private Button emprefresh;

    @FXML
    private TableColumn<EmpTable, String> empsname;

    @FXML
    private TableView<EmpTable> emptable;

    @FXML
    private Button exit;

    @FXML
    private TextField fiopost;

    @FXML
    private Pane graphic;

    @FXML
    private ComboBox<Integer> graphicid;

    @FXML
    private TextField holiday;

    @FXML
    private ComboBox<Integer> listid;

    @FXML
    private TextField mname;

    @FXML
    private TextField month;

    @FXML
    private TextField workhours;

    @FXML
    private TextField name;

    @FXML
    private TextField oklad;

    @FXML
    private TableColumn<Post, Integer> okladcol;

    @FXML
    private TextField overtime;

    @FXML
    private Pane pane;

    @FXML
    private TextField post;

    @FXML
    private TableColumn<Post, String> postcol;

    @FXML
    private TableView<Post> posttable;

    @FXML
    private Button refreshdiagram;

    @FXML
    private TableColumn<Salary, Integer> salaryaward;

    @FXML
    private TableColumn<Salary, String> salarydate;

    @FXML
    private TableColumn<Salary, Integer> salaryemp;

    @FXML
    private Button salaryrefresh;

    @FXML
    private TableColumn<Salary, Double> salarytax;

    @FXML
    private TableColumn<Salary, Double> salaryfszn;

    @FXML
    private TableColumn<Salary, Double> salaryfresh;

    @FXML
    private TableColumn<Salary, Double> salaryvznos;

    @FXML
    private Button daterefresh;

    @FXML
    private TableColumn<Salary, Double> salaryresult;

    @FXML
    private TableView<Salary> salarytable;

    @FXML
    private TableColumn<Salary, Integer> salarytimesheet;

    @FXML
    private TextField seak;

    @FXML
    private TextField sname;

    @FXML
    private TableColumn<TimeSheet, String> timesheetdate;

    @FXML
    private TableColumn<TimeSheet, Integer> timesheetemp;

    @FXML
    private TableColumn<TimeSheet, Integer> timesheetholiday;

    @FXML
    private TableColumn<TimeSheet, Integer> timesheetid;

    @FXML
    private TableColumn<TimeSheet, Integer> timesheetovertime;

    @FXML
    private Button timesheetrefresh;

    @FXML
    private TableColumn<TimeSheet, Integer> timesheetseak;

    @FXML
    private TableView<TimeSheet> timesheettable;

    @FXML
    private TableColumn<TimeSheet, Integer> timesheetworktime;

    @FXML
    private TextField worktime;

    @FXML
    private TextField year;

    @FXML
    void initialize() {
        dataForEmpTable("t",emplist,true);
        dataForTimeSheetTable("t",timesheetlist);
        dataForSalaryTable("t",salarylist);
        createDiagram();
        dataForDateTable();
        dataForPostTable();

        daterefresh.setOnAction(e->{
           dataForDateTable();
        });

        graphicid.setOnAction(e->{
            createGraphic();
        });

        refreshdiagram.setOnAction(e->{
            createDiagram();
        });

        posttable.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->{
            if(newVal!=null){
                post.setText(newVal.getPost());
                oklad.setText(String.valueOf(newVal.getOklad()));
            }
        });


        addpost.setOnAction(e->{
            addPost();
        });

        adddate.setOnAction(e->{
            addDate();
        });

        addtimesheet.setOnAction(e->{
            timesheetlist.clear();
            addTimesheet();
        });

        delpost.setOnAction(e->{
            delPost();
        });

        deldate.setOnAction(e->{
            delDate();
        });

        deltimesheet.setOnAction(e->{
            timesheetlist.clear();
            delTimesheet();
        });

        delemp.setOnAction(e->{
            emplist.clear();
            delEmp();
        });


        correcttimesheet.setOnAction(e->{
            correctTimesheet();
        });

        correctemp.setOnAction(e->{
            emplist.clear();
            correctEmp();
        });

        emprefresh.setOnAction(e->{
            emplist.clear();
            dataForEmpTable("t",emplist,true);
        });

        timesheetrefresh.setOnAction(e->{
            timesheetlist.clear();
            dataForTimeSheetTable("t",timesheetlist);
        });

        salaryrefresh.setOnAction(e->{
            salarylist.clear();
            dataForSalaryTable("t",salarylist);
        });

        exit.setOnAction(e->{
            goBack();
        });

        empidsearch.setOnAction(e->{
            empIdSearch();
        });

        empsnamesearch.setOnAction(e->{
            empSnameSearch();
        });


        timesheetempsearch.setOnAction(e->{
            timesheetEmpSearch();
        });

        analysempsearch.setOnAction(e->{
            analysEmpSearch();
        });

        salaryempsearch.setOnAction(e->{
            salaryEmpSearch();
        });


        emptable.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->{
            if(newVal!=null){
                m_empid=newVal.getId();
                sname.setText(newVal.getSname());
                name.setText(newVal.getName());
                mname.setText(newVal.getMiddlename());
                posts.getSelectionModel().select(newVal.getPost());
            }
        });

        datetable.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->{
            if(newVal!=null){
                month.setText(newVal.getMonth());
                year.setText(String.valueOf(newVal.getYear()));
                workhours.setText(String.valueOf(newVal.getWorkhours()));
            }
        });

        timesheettable.getSelectionModel().selectedItemProperty().addListener((obs,oldVal,newVal)->{
            if(newVal!=null){
                m_timesheetid= newVal.getTimesheetid();
                date.getSelectionModel().select(newVal.getDate());
                worktime.setText(String.valueOf(newVal.getWorktime()));
                seak.setText(String.valueOf(newVal.getSeaktime()));
                holiday.setText(String.valueOf(newVal.getHolidays()));
                overtime.setText(String.valueOf(newVal.getOvertime()));
                Integer integer= newVal.getEmpid();
                listid.getSelectionModel().select(integer);
            }
        });
    }

    private boolean isAlpha(String str){
        return str.matches("[а-яА-Я]+");
    }
    private boolean isNumber(String str){
        return str.matches("[0-9]+");
    }

    private void delPost(){
        try {
            Connection.writer.writeUTF("delpost "+post.getText()+" "+oklad.getText());
            Connection.writer.flush();
            String response = Connection.reader.readUTF();
            if(response.equals("ok")){
                dataForPostTable();
            }else{
                Alerts.errorAlert("Ошибка удаления");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void delDate() {
        try {
            Connection.writer.writeUTF("deldate "+month.getText()+" "+year.getText());
            Connection.writer.flush();
            String response = Connection.reader.readUTF();
            if(response.equals("ok")){
                dataForDateTable();
            }else{
                Alerts.errorAlert("Ошибка удаления");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void addDate() {
        if(isAlpha(month.getText())&&isNumber(year.getText())&& isNumber(workhours.getText())) {
            try {
                Connection.writer.writeUTF("adddate " + month.getText() + " " + year.getText() + " " + workhours.getText());
                Connection.writer.flush();
                String response = Connection.reader.readUTF();
                if (response.equals("ok")) {
                    dataForDateTable();
                } else {
                    Alerts.errorAlert("Ошибка добавления");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Alerts.errorAlert("Введите корректные данные");
        }
    }

    private void delEmp() {
        try {
            Connection.writer.writeUTF("delemp "+sname.getText()+" "+name.getText()+" "+mname.getText()+" "+posts.getValue());
            Connection.writer.flush();
            String response = Connection.reader.readUTF();
            if(response.equals("ok")){
                dataForEmpTable("t",emplist,true);
            }else{
                Alerts.errorAlert("Ошибка удаления");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void correctEmp() {
        if(isAlpha(sname.getText())&& isAlpha(name.getText())&& isAlpha(mname.getText())&& !posts.getValue().equals("")) {
            try {
                Connection.writer.writeUTF("correctemp " + m_empid + " " + sname.getText() + " " + name.getText() + " " + mname.getText() + " " + posts.getValue());
                Connection.writer.flush();
                String response = Connection.reader.readUTF();
                if (response.equals("ok")) {
                    dataForEmpTable("t", emplist, true);
                } else {
                    Alerts.errorAlert("Ошибка редактирования");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Alerts.errorAlert("Введите корректные данные");
        }
    }

    private void correctTimesheet() {
        if(listid.getValue()!=null&&isNumber(workhours.getText())&&isNumber(seak.getText())&&isNumber(holiday.getText())&&isNumber(overtime.getText())) {
            try {
                Connection.writer.writeUTF("correcttimesheet " + m_timesheetid + " " + listid.getValue() + " " + date.getValue() + " " + worktime.getText() + " " + seak.getText() + " " + holiday.getText() + " " + overtime.getText());
                Connection.writer.flush();
                String response = Connection.reader.readUTF();
                if (response.equals("ok")) {
                    dataForTimeSheetTable("t", timesheetlist);
                } else {
                    Alerts.errorAlert("Ошибка редактирования");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Alerts.errorAlert("Введите корректные данные");
        }
    }

    private void delTimesheet() {
        try {
            Connection.writer.writeUTF("deltimesheet "+listid.getValue()+" "+date.getValue()+" "+worktime.getText()+" "+seak.getText()+" "+holiday.getText()+" "+overtime.getText());
            Connection.writer.flush();
            String response = Connection.reader.readUTF();
            if(response.equals("ok")){
                dataForTimeSheetTable("t",timesheetlist);
            }else{
                Alerts.errorAlert("Ошибка удаления");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    private void addTimesheet() {
        if(listid.getValue()!=null&&isNumber(workhours.getText())&&isNumber(seak.getText())&&isNumber(holiday.getText())&&isNumber(overtime.getText())) {
            try {
                Connection.writer.writeUTF("addtimesheet " + listid.getValue() + " " + date.getValue() + " " + worktime.getText() + " " + seak.getText() + " " + holiday.getText() + " " + overtime.getText());
                Connection.writer.flush();
                String response = Connection.reader.readUTF();
                if (response.equals("ok")) {
                    dataForTimeSheetTable("t", timesheetlist);
                } else {
                    Alerts.errorAlert("Ошибка добавления");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Alerts.errorAlert("Введите корректные данные");
        }
    }

    private void addPost() {
        if(isAlpha(post.getText())&& isNumber(oklad.getText())) {
            try {
                Connection.writer.writeUTF("addpost " + post.getText() + " " + oklad.getText());
                Connection.writer.flush();
                String response = Connection.reader.readUTF();
                if (response.equals("ok")) {
                    dataForPostTable();
                } else {
                    Alerts.errorAlert("Ошибка добавления");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Alerts.errorAlert("Введите корректные данные");
        }
    }

    private void dataForDateTable() {
        try {
            datelist.clear();
            Connection.writer.writeUTF("datafordatetable t");
            Connection.writer.flush();
            String end="";
            String response;
            ObservableList<String> listdate= FXCollections.observableArrayList();
            while(!end.equals("end")) {
                response = Connection.reader.readUTF();
                System.out.println(response);
                String[] res=response.split(" ");
                datelist.add(new Date(res[0],Integer.parseInt(res[1]),Integer.parseInt(res[2])));
                listdate.add(res[0]+" "+res[1]);
                end=res[3];
            }
            datemonth.setCellValueFactory(new PropertyValueFactory<Date, String>("month"));
            dateyear.setCellValueFactory(new PropertyValueFactory<Date, Integer>("year"));
            dateworkhours.setCellValueFactory(new PropertyValueFactory<Date, Integer>("workhours"));
            datetable.setItems(datelist);
            date.setItems(listdate);
            date.getSelectionModel().selectFirst();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void dataForPostTable() {
        try {
            postlist.clear();
            Connection.writer.writeUTF("dataforposttable t");
            Connection.writer.flush();
            String end="";
            String response;
            ObservableList<String> pos= FXCollections.observableArrayList();
            while(!end.equals("end")) {
                response = Connection.reader.readUTF();
                System.out.println(response);
                String[] res=response.split(" ");
                postlist.add(new Post(res[0],Integer.parseInt(res[1])));
                pos.add(res[0]);
                end=res[2];
            }
            postcol.setCellValueFactory(new PropertyValueFactory<Post, String>("post"));
            okladcol.setCellValueFactory(new PropertyValueFactory<Post, Integer>("oklad"));
            posttable.setItems(postlist);
            posts.setItems(pos);
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    private void createGraphic() {
        graphic.getChildren().clear();
        CategoryAxis xAxis=new CategoryAxis();
        xAxis.setLabel("Месяц");
        NumberAxis yAxis=new NumberAxis();
        yAxis.setLabel("Величина(руб.)");
        LineChart chart=new LineChart(xAxis,yAxis);
        chart.setTitle("График размера выплат ЗП");
        XYChart.Series series=new XYChart.Series();
        series.setName("Размер выплат");
        try {
            Connection.writer.writeUTF("dataforgraphic "+graphicid.getValue());
            System.out.println(graphicid.getValue());
            Connection.writer.flush();
            String end="";
            String response;
            while(!end.equals("end")) {
                response = Connection.reader.readUTF();
                System.out.println(response);
                String[] res=response.split(" ");
                series.getData().add(new XYChart.Data<>(res[0]+" "+res[1],Double.parseDouble(res[2])));
                end=res[3];
            }
            chart.getData().add(series);
            graphic.getChildren().add(chart);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void dataForSalaryTable(String str,ObservableList<Salary> slist) {
        try {
            Connection.writer.writeUTF("dataforsalarytable "+str);
            Connection.writer.flush();
            String end="";
            String response;
            while(!end.equals("end")) {
                response = Connection.reader.readUTF();
                System.out.println(response);
                String[] res=response.split(" ");
                slist.add(new Salary(Integer.parseInt(res[0]),res[1]+" "+res[2],Integer.parseInt(res[3]), parseDouble(res[4]),Integer.parseInt(res[5]), parseDouble(res[6]), parseDouble(res[7]), parseDouble(res[8]), parseDouble(res[9])));
                end=res[10];
            }
            salaryemp.setCellValueFactory(new PropertyValueFactory<Salary, Integer>("empid"));
            salarydate.setCellValueFactory(new PropertyValueFactory<Salary, String>("date"));
            salaryaward.setCellValueFactory(new PropertyValueFactory<Salary, Integer>("award"));
            salaryresult.setCellValueFactory(new PropertyValueFactory<Salary, Double>("salary"));
            salarytimesheet.setCellValueFactory(new PropertyValueFactory<Salary, Integer>("timesheetid"));
            salaryfresh.setCellValueFactory(new PropertyValueFactory<Salary, Double>("freshsalary"));
            salarytax.setCellValueFactory(new PropertyValueFactory<Salary, Double>("tax"));
            salaryfszn.setCellValueFactory(new PropertyValueFactory<Salary, Double>("fszn"));
            salaryvznos.setCellValueFactory(new PropertyValueFactory<Salary, Double>("vznos"));
            salarytable.setItems(slist);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void dataForTimeSheetTable(String str,    ObservableList<TimeSheet> tlist) {
        try {
            Connection.writer.writeUTF("datafortimesheettable "+str);
            Connection.writer.flush();
            String end="";
            String response;
            while(!end.equals("end")) {
                response = Connection.reader.readUTF();
                System.out.println(response);
                String[] res=response.split(" ");
                tlist.add(new TimeSheet(Integer.parseInt(res[0]),Integer.parseInt(res[1]),res[2]+" "+res[3],Integer.parseInt(res[4]),Integer.parseInt(res[5]),Integer.parseInt(res[6]),Integer.parseInt(res[7])));
                end=res[8];
            }
            timesheetid.setCellValueFactory(new PropertyValueFactory<TimeSheet, Integer>("timesheetid"));
            timesheetemp.setCellValueFactory(new PropertyValueFactory<TimeSheet, Integer>("empid"));
            timesheetdate.setCellValueFactory(new PropertyValueFactory<TimeSheet, String>("date"));
            timesheetworktime.setCellValueFactory(new PropertyValueFactory<TimeSheet, Integer>("worktime"));
            timesheetseak.setCellValueFactory(new PropertyValueFactory<TimeSheet, Integer>("seaktime"));
            timesheetholiday.setCellValueFactory(new PropertyValueFactory<TimeSheet, Integer>("holidays"));
            timesheetovertime.setCellValueFactory(new PropertyValueFactory<TimeSheet, Integer>("overtime"));
            timesheettable.setItems(tlist);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void createDiagram(){
        pane.getChildren().clear();
        CategoryAxis xAxis=new CategoryAxis();
        xAxis.setLabel("Месяц");
        NumberAxis yAxis=new NumberAxis();
        yAxis.setLabel("Величина(руб.)");
        BarChart сhart=new BarChart(xAxis,yAxis);
        сhart.setTitle("Диаграмма размера выплат ЗП");
        XYChart.Series series=new XYChart.Series();
        series.setName("Размер выплат");
        try {
            Connection.writer.writeUTF("datafordiagram");
            Connection.writer.flush();
            String end="";
            String response;
            while(!end.equals("end")) {
                response = Connection.reader.readUTF();
                System.out.println(response);
                String[] res=response.split(" ");
                series.getData().add(new XYChart.Data<>(res[0]+" "+res[1],Double.parseDouble(res[2])));
                end=res[3];
            }
            сhart.getData().add(series);
            pane.getChildren().add(сhart);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private void dataForEmpTable(String str,    ObservableList<EmpTable> elist,boolean bool) {
        try {
            Connection.writer.writeUTF("dataforemptables "+str);
            Connection.writer.flush();
            String end="";
            String response;
            ObservableList<Integer> combobox= FXCollections.observableArrayList();
            while(!end.equals("end")) {
                response = Connection.reader.readUTF();
                String[] res=response.split(" ");
                if(bool==true) {
                    combobox.add(Integer.parseInt(res[0]));
                }
                elist.add(new EmpTable(Integer.parseInt(res[0]),res[1],res[2],res[3],res[4],Integer.parseInt(res[5])));
                end=res[6];
            }
            empid.setCellValueFactory(new PropertyValueFactory<EmpTable, Integer>("id"));
            empsname.setCellValueFactory(new PropertyValueFactory<EmpTable, String>("sname"));
            empname.setCellValueFactory(new PropertyValueFactory<EmpTable, String>("name"));
            empmiddlename.setCellValueFactory(new PropertyValueFactory<EmpTable, String>("middlename"));
            emppost.setCellValueFactory(new PropertyValueFactory<EmpTable, String>("post"));
            empoklad.setCellValueFactory(new PropertyValueFactory<EmpTable, Integer>("oklad"));
            emptable.setItems(elist);
            if(bool==true) {
                Collections.sort(combobox, Comparator.comparing(Integer::intValue));
                listid.setItems(combobox);
                graphicid.setItems(combobox);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void goBack() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage window=(Stage)exit.getScene().getWindow();
        window.setScene(new Scene(root,600,400));
    }

    private void analysEmpSearch() {
        boolean bool=false;
        int i=0;
        try {
            for (i = 0; i < emplist.size(); i++) {
                if (analyssearch.getText() != "" && emplist.get(i).getId() == Integer.parseInt(analyssearch.getText())) {
                    bool = true;
                    break;
                }
            }
        }catch(NumberFormatException e){
        }
        if(bool==false){
            Alerts.errorAlert("Введите корректные данные в строку поиска");
        }else{
            graphicid.getSelectionModel().select(i);
            createGraphic();
        }
    }

    private void salaryEmpSearch() {
        boolean bool=false;
        try {
            for (int i = 0; i < salarylist.size(); i++) {
                if (salarysearch.getText() != "" && salarylist.get(i).getEmpid() == Integer.parseInt(salarysearch.getText()))
                    bool = true;
            }
        }catch(NumberFormatException e){

        }
        if(bool==false){
            Alerts.errorAlert("Введите корректные данные в строку поиска");
        }else{
            ObservableList<Salary> slist= FXCollections.observableArrayList();
            dataForSalaryTable(salarysearch.getText(),slist);
        }
    }

    private void empSnameSearch() {
        boolean bool=false;
        try {
            for (int i = 0; i < emplist.size(); i++) {
                if (empsearch.getText() != "" && emplist.get(i).getSname().equals(empsearch.getText()))
                    bool = true;
            }
        }catch(NumberFormatException e){

        }
        if(bool==false){
            Alerts.errorAlert("Введите корректные данные в строку поиска");
        }else{
            ObservableList<EmpTable> elist= FXCollections.observableArrayList();
            dataForEmpTableBySname(elist);
        }
    }

    private void dataForEmpTableBySname(ObservableList<EmpTable> elist){
        try {
            Connection.writer.writeUTF("dataforemptablebysname "+empsearch.getText());
            Connection.writer.flush();
            String end="";
            String response;
            while(!end.equals("end")) {
                response = Connection.reader.readUTF();
                String[] res=response.split(" ");
                elist.add(new EmpTable(Integer.parseInt(res[0]),res[1],res[2],res[3],res[4],Integer.parseInt(res[5])));
                end=res[6];
            }
            empid.setCellValueFactory(new PropertyValueFactory<EmpTable, Integer>("id"));
            empsname.setCellValueFactory(new PropertyValueFactory<EmpTable, String>("sname"));
            empname.setCellValueFactory(new PropertyValueFactory<EmpTable, String>("name"));
            empmiddlename.setCellValueFactory(new PropertyValueFactory<EmpTable, String>("middlename"));
            emppost.setCellValueFactory(new PropertyValueFactory<EmpTable, String>("post"));
            empoklad.setCellValueFactory(new PropertyValueFactory<EmpTable, Integer>("oklad"));
            emptable.setItems(elist);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void timesheetEmpSearch() {
        boolean bool=false;
        try {
            for (int i = 0; i < timesheetlist.size(); i++) {
                if (timesheetsearch.getText() != "" && timesheetlist.get(i).getEmpid() == Integer.parseInt(timesheetsearch.getText()))
                    bool = true;
            }
        }catch(NumberFormatException e){

        }
        if(bool==false){
            Alerts.errorAlert("Введите корректные данные в строку поиска");
        }else{
            ObservableList<TimeSheet> tlist= FXCollections.observableArrayList();
            dataForTimeSheetTable(timesheetsearch.getText(),tlist);
        }
    }

    private void empIdSearch() {
        boolean bool=false;
        try {
            for (int i = 0; i < emplist.size(); i++) {
                if (empsearch.getText() != "" && emplist.get(i).getId() == Integer.parseInt(empsearch.getText()))
                    bool = true;
            }
        }catch(NumberFormatException e){

        }
        if(bool==false){
            Alerts.errorAlert("Введите корректные данные в строку поиска");
        }else{
            ObservableList<EmpTable> elist= FXCollections.observableArrayList();
            dataForEmpTable(empsearch.getText(),elist,false);
        }
    }

}
