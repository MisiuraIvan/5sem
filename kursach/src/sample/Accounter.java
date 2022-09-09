package sample;

import java.io.*;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jdk.jfr.Category;

import static java.lang.Double.parseDouble;

public class Accounter {

    ObservableList<EmpTable> emplist= FXCollections.observableArrayList();
    ObservableList<TimeSheet> timesheetlist= FXCollections.observableArrayList();
    ObservableList<Salary> salarylist= FXCollections.observableArrayList();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exit;

    @FXML
    private Button refreshdiagram;

    @FXML
    private TextField allsalary;

    @FXML
    private TextField salarysearch;

    @FXML
    private TextField empsearch;

    @FXML
    private TextField analyssearch;

    @FXML
    private TextField analyssearch1;

    @FXML
    private TextField timesheetsearch;

    @FXML
    private TextField accountsearch;

    @FXML
    private Button empidsearch;

    @FXML
    private Button empsnamesearch;

    @FXML
    private Button accounteridsearch;

    @FXML
    private Button timesheetempsearch;

    @FXML
    private Button salaryempsearch;

    @FXML
    private Button analysempsearch;

    @FXML
    private Button analysempsearch1;
    
    @FXML
    private TextField award;

    @FXML
    private Button clrbutton;

    @FXML
    private ComboBox<String> date;

    @FXML
    private ComboBox<String> date1;

    @FXML
    private ComboBox<String> date2;

    @FXML
    private Button createreport;

    @FXML
    private Button createreport1;

    @FXML
    private ComboBox<Integer> graphicid;

    @FXML
    private ComboBox<Integer> graphicid1;

    @FXML
    private TableColumn<EmpTable, Integer> empid;

    @FXML
    private TableColumn<EmpTable, String> empmiddlename;

    @FXML
    private Pane pane;

    @FXML
    private Pane graphic;

    @FXML
    private TableColumn<EmpTable, String> empname;

    @FXML
    private TableColumn<EmpTable, String> emppost;

    @FXML
    private TableColumn<EmpTable, String> empsname;

    @FXML
    private TableColumn<EmpTable, Integer> empoklad;

    @FXML
    private TableView<EmpTable> emptable;

    @FXML
    private TextField holiday;

    @FXML
    private ComboBox<Integer> listid;

    @FXML
    private TextField middlename;

    @FXML
    private TextField name;

    @FXML
    private TextField oklad;

    @FXML
    private TextField overtime;

    @FXML
    private TextField post;

    @FXML
    private TextField freshsalary;

    @FXML
    private TextField tax;

    @FXML
    private TextField fszn;

    @FXML
    private TextField pension;

    @FXML
    private TextField social;

    @FXML
    private TableColumn<Salary, Integer> salaryaward;

    @FXML
    private Button salarybutton;

    @FXML
    private TableColumn<Salary, String> salarydate;

    @FXML
    private TableColumn<Salary, Integer> salaryemp;

    @FXML
    private TableColumn<Salary, Double> salaryresult;

    @FXML
    private TableColumn<Salary, Double> salarytax;

    @FXML
    private TableColumn<Salary, Double> salaryfszn;

    @FXML
    private TableColumn<Salary, Double> salaryfresh;

    @FXML
    private TableColumn<Salary, Double> salaryvznos;

    @FXML
    private TableView<Salary> salarytable;

    @FXML
    private TableColumn<Salary, Integer> salarytimesheet;

    @FXML
    private Button savebutton;

    @FXML
    private TextField sickleave;

    @FXML
    private TextField sname;

    @FXML
    private TextField timenorm;

    @FXML
    private TableColumn<TimeSheet, String> timesheetdate;

    @FXML
    private TableColumn<TimeSheet, Integer> timesheetemp;

    @FXML
    private TableColumn<TimeSheet, Integer> timesheetholiday;

    @FXML
    private TableColumn<TimeSheet, Integer> timesheetid;

    @FXML
    private TableColumn<TimeSheet, Integer> timesheetseak;

    @FXML
    private TableView<TimeSheet> timesheettable;

    @FXML
    private TableColumn<TimeSheet, Integer> timesheetworktime;

    @FXML
    private TableColumn<TimeSheet, Integer> timesheetovertime;

    @FXML
    private Button emprefresh;

    @FXML
    private Button timesheetrefresh;

    @FXML
    private Button salaryrefresh;

    @FXML
    private TextField worktime;

    @FXML
    void initialize() {
        dataForEmpTable("t",emplist,true);
        dataForTimeSheetTable("t",timesheetlist);
        dataForSalaryTable("t",salarylist);
        dataForDate();
        createDiagram();

        empidsearch.setOnAction(e->{
            empIdSearch();
        });

        empsnamesearch.setOnAction(e->{
            empSnameSearch();
        });

        accounteridsearch.setOnAction(e->{
            accounterIdSearch();
        });

        timesheetempsearch.setOnAction(e->{
            timesheetEmpSearch();
        });

        analysempsearch.setOnAction(e->{
            analysEmpSearch();
        });

        analysempsearch1.setOnAction(e->{
            analysEmpSearch1();
        });

        salaryempsearch.setOnAction(e->{
            salaryEmpSearch();
        });

        graphicid.setOnAction(e->{
            createGraphic();
        });

        graphicid1.setOnAction(e->{
        });

        refreshdiagram.setOnAction(e->{
            createDiagram();
        });

        listid.setOnAction(e->{
            clrData();
            listOnChange();
        });

        createreport.setOnAction(e->{
            createReport();
        });

        createreport1.setOnAction(e->{
            createReport1();
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

        clrbutton.setOnAction(actionEvent->{
            clrData();
        });

        date.setOnAction(actionEvent -> {
            dateOnChange();
        });

        salarybutton.setOnAction(actionEvent -> {
            salaryCount();
        });
        savebutton.setOnAction(actionEvent -> {
            salarySave();
        });
    }

    private void createReport() {
        try {
            Connection.writer.writeUTF("createreport " + date1.getValue()+" "+date2.getValue());
            Connection.writer.flush();
            Alerts.informationAlert("Отчет сформирован и сохранен");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void createReport1() {
        try {
            Connection.writer.writeUTF("createreport1 " + date1.getValue()+" "+date2.getValue()+" "+graphicid1.getValue());
            Connection.writer.flush();
            Alerts.informationAlert("Отчет сформирован и сохранен");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void clrData() {
        name.setText("");
        sname.setText("");
        middlename.setText("");
        post.setText("");
        oklad.setText("");
        timenorm.setText("");
        worktime.setText("");
        sickleave.setText("");
        holiday.setText("");
        overtime.setText("");
        award.setText("");
        freshsalary.setText("");
        allsalary.setText("");
        fszn.setText("");
        tax.setText("");
        pension.setText("");
        social.setText("");
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

    private void analysEmpSearch1() {
        boolean bool=false;
        int i=0;
        try {
            for (i = 0; i < emplist.size(); i++) {
                if (!analyssearch1.getText().equals("") && emplist.get(i).getId() == Integer.parseInt(analyssearch1.getText())) {
                    bool = true;
                    break;
                }
            }
        }catch(NumberFormatException e){
        }
        if(bool==false){
            Alerts.errorAlert("Введите корректные данные в строку поиска");
        }else{
            graphicid1.getSelectionModel().select(i);
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
    private void accounterIdSearch() {
        boolean bool=false;
        int i=0;
        try {
            for (i = 0; i < emplist.size(); i++) {
                if (accountsearch.getText() != "" && emplist.get(i).getId() == Integer.parseInt(accountsearch.getText())) {
                    bool = true;
                    break;
                }
            }
        }catch(NumberFormatException e){}
        if(bool==false){
            Alerts.errorAlert("Введите корректные данные в строку поиска");
        }else{
            listid.getSelectionModel().select(i);
            listOnChange();
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

    private void createGraphic() {
        int k=0;
        for(k=0;k<salarylist.size();k++){
            if(salarylist.get(k).getEmpid()==graphicid.getValue()){
                k=-10;
                break;
            }
        }
        if(k==-10) {
            graphic.getChildren().clear();
            CategoryAxis xAxis = new CategoryAxis();
            xAxis.setLabel("Месяц");
            NumberAxis yAxis = new NumberAxis();
            yAxis.setLabel("Величина(руб.)");
            LineChart chart = new LineChart(xAxis, yAxis);
            chart.setTitle("График размера выплат ЗП сотруднику");
            XYChart.Series series = new XYChart.Series();
            series.setName("Размер выплат");
            try {
                Connection.writer.writeUTF("dataforgraphic " + graphicid.getValue());
                System.out.println(graphicid.getValue());
                Connection.writer.flush();
                String end = "";
                String response;
                while (!end.equals("end")) {
                    response = Connection.reader.readUTF();
                    System.out.println(response);
                    String[] res = response.split(" ");
                    series.getData().add(new XYChart.Data<>(res[0] + " " + res[1], parseDouble(res[2])));
                    end = res[3];
                }
                chart.getData().add(series);
                graphic.getChildren().add(chart);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Alerts.informationAlert("Нет данных о ЗП данного сотрудника");
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

    private void salarySave() {
        try {
            Integer tsid = 0;
            for(int i=0;i<timesheetlist.size();i++){
                if(timesheetlist.get(i).getEmpid()==listid.getValue()&& timesheetlist.get(i).getDate().equals(date.getValue())){
                    tsid=timesheetlist.get(i).getTimesheetid();
                    System.out.println(tsid);
                    break;
                }
            }
            String pen=pension.getText().replace(',', '.');
            String soc=social.getText().replace(',', '.');
            Double vznos;
            vznos = Double.parseDouble(pen)+Double.parseDouble(soc);
            Connection.writer.writeUTF(String.format(String.format("salarysave " + tsid + " " + award.getText() + " " + allsalary.getText().replace(',', '.') + " " + freshsalary.getText().replace(',', '.') + " " + tax.getText().replace(',', '.') + " " + fszn.getText().replace(',', '.') + " " + (double)Math.round(vznos*100)/100)));
            System.out.println("salarysave "+tsid+" "+award.getText()+" "+allsalary.getText()+" "+freshsalary.getText()+" "+tax.getText()+" "+fszn.getText()+" "+vznos);
            Connection.writer.flush();
            String response = Connection.reader.readUTF();
            System.out.println(response);
            if(response.equals("ok")){
                Alerts.informationAlert("Сохранение прошло успешно");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void dataForDate() {
        try {
            Connection.writer.writeUTF("datafordate");
            Connection.writer.flush();
            String end="";
            String response;
            ObservableList<String> datelist= FXCollections.observableArrayList();
            while(!end.equals("end")) {
                response = Connection.reader.readUTF();
                System.out.println(response);
                String[] res=response.split(" ");
                datelist.add(res[0]+" "+res[1]);
                end=res[2];
            }
            date.setItems(datelist);
            date.getSelectionModel().selectFirst();
            date1.setItems(datelist);
            date1.getSelectionModel().selectFirst();
            date2.setItems(datelist);
            date2.getSelectionModel().selectFirst();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void salaryCount() {
        if(!timenorm.getText().equals("")&&!oklad.getText().equals("")&&!worktime.getText().equals("")&&!sickleave.getText().equals("")&&!holiday.getText().equals("")&&!overtime.getText().equals("")&&!award.getText().equals("")) {
            int norm = Integer.parseInt(timenorm.getText());
            int okl = Integer.parseInt(oklad.getText());
            int workt = Integer.parseInt(worktime.getText());
            double proc = (1 + (Integer.parseInt(award.getText()) * 0.01));
            double swork = okl * workt*proc/norm;
            int seakleave=Integer.parseInt(sickleave.getText());
            double avgsalary=0;
            double sseak;
            if(seakleave>40) {
                sseak = 0.8 * okl*40/norm;
                sseak+=(seakleave-40) * okl/norm;
            }else{
                sseak = seakleave* 0.8 * okl/norm;
            }
            try {
                Connection.writer.writeUTF("dataforavgsalary "+listid.getValue()+" "+date.getValue());
                Connection.writer.flush();
                String response = Connection.reader.readUTF();
                avgsalary= parseDouble(response);
                System.out.println(avgsalary);
            }catch(IOException e){
                e.printStackTrace();
            }
            double sholiday;
            if(avgsalary>0) {
                sholiday = (Integer.parseInt(holiday.getText())) * avgsalary / norm;
            }else{
                sholiday=0;
            }
            double sover = (Integer.parseInt(overtime.getText())) * 2 * okl*proc/norm;
            System.out.println("swork "+swork+ " sseak "+sseak+ " sholiday "+sholiday+ " sover " +sover );
            double result=swork + sseak + sholiday + sover;
            double freshs=result*0.86;
            double m_fszn=result*0.01;
            double m_tax=result*0.13;
            double m_pension=result*0.29;
            double m_social=result*0.06;
            fszn.setText(String.format("%.2f",m_fszn));
            tax.setText(String.format("%.2f",m_tax));
            pension.setText(String.format("%.2f",m_pension));
            social.setText(String.format("%.2f",m_social));
            freshsalary.setText(String.format("%.2f",freshs));
            allsalary.setText(String.format("%.2f",result));
        }else{
            Alerts.errorAlert("Для расчета ЗП укажите все необходимые данные");
        }
    }

    private void dateOnChange(){
        try {
            Connection.writer.writeUTF("dataforworkhours "+date.getValue());
            Connection.writer.flush();
            String response = Connection.reader.readUTF();
            timenorm.setText(response);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void listOnChange() {
        for(int i=0;i<emplist.size();i++){
            if(listid.getValue()==emplist.get(i).getId()){
                name.setText(emplist.get(i).getName());
                sname.setText(emplist.get(i).getSname());
                middlename.setText(emplist.get(i).getMiddlename());
                post.setText(emplist.get(i).getPost());
                oklad.setText(String.valueOf(emplist.get(i).getOklad()));
                award.setText("0");
                dateOnChange();
            }
        }
        for(int i=0;i<timesheetlist.size();i++){
            if(listid.getValue()==timesheetlist.get(i).getEmpid()&& date.getValue().equals(timesheetlist.get(i).getDate())){
                worktime.setText(String.valueOf(timesheetlist.get(i).getWorktime()));
                sickleave.setText(String.valueOf(timesheetlist.get(i).getSeaktime()));
                holiday.setText(String.valueOf(timesheetlist.get(i).getHolidays()));
                overtime.setText(String.valueOf(timesheetlist.get(i).getOvertime()));
            }
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
        сhart.setTitle("Диаграмма размера выплат ЗП всем сотрудникам");
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
                series.getData().add(new XYChart.Data<>(res[0]+" "+res[1], parseDouble(res[2])));
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
                graphicid1.setItems(combobox);
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
}

