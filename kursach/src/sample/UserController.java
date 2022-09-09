package sample;

import java.io.IOException;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UserController {

    ObservableList<EmpTable> emplist= FXCollections.observableArrayList();
    ObservableList<TimeSheet> timesheetlist= FXCollections.observableArrayList();
    ObservableList<Salary> salarylist= FXCollections.observableArrayList();

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

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
        private Pane graphic;

        @FXML
        private TableColumn<Salary, Integer> salaryaward;

        @FXML
        private TableColumn<Salary, String> salarydate;

        @FXML
        private TableColumn<Salary, Integer> salaryemp;

        @FXML
        private TableColumn<Salary, Double> salaryresult;

        @FXML
        private TableView<Salary> salarytable;

        @FXML
        private TableColumn<Salary, Integer> salarytimesheet;

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
        private TableColumn<TimeSheet, Integer> timesheetseak;

        @FXML
        private TableView<TimeSheet> timesheettable;

        @FXML
        private TableColumn<TimeSheet, Integer> timesheetworktime;

        @FXML
        void initialize() {
            dataForEmpTable();
            dataForTimeSheetTable();
            dataForSalaryTable();
            createGraphic();
            exit.setOnAction(e->{
                goBack();
            });
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
            Connection.writer.writeUTF("dataforgraphic "+MyClass.counter);
            System.out.println(MyClass.counter);
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

    private void dataForSalaryTable() {
        try {
            Connection.writer.writeUTF("dataforsalarytable "+MyClass.counter);
            Connection.writer.flush();
            String end="";
            String response;
            while(!end.equals("end")) {
                response = Connection.reader.readUTF();
                System.out.println(response);
                String[] res=response.split(" ");
                salarylist.add(new Salary(Integer.parseInt(res[0]),res[1]+" "+res[2],Integer.parseInt(res[3]),Double.parseDouble(res[4]),Integer.parseInt(res[5])));
                end=res[10];
            }
            salarydate.setCellValueFactory(new PropertyValueFactory<Salary, String>("date"));
            salaryaward.setCellValueFactory(new PropertyValueFactory<Salary, Integer>("award"));
            salaryresult.setCellValueFactory(new PropertyValueFactory<Salary, Double>("salary"));
            salarytimesheet.setCellValueFactory(new PropertyValueFactory<Salary, Integer>("timesheetid"));
            salarytable.setItems(salarylist);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void dataForTimeSheetTable() {
        try {
            Connection.writer.writeUTF("datafortimesheettable "+MyClass.counter);
            Connection.writer.flush();
            String end="";
            String response;
            while(!end.equals("end")) {
                response = Connection.reader.readUTF();
                System.out.println(response);
                String[] res=response.split(" ");
                timesheetlist.add(new TimeSheet(Integer.parseInt(res[0]),Integer.parseInt(res[1]),res[2]+" "+res[3],Integer.parseInt(res[4]),Integer.parseInt(res[5]),Integer.parseInt(res[6]),Integer.parseInt(res[7])));
                end=res[8];
            }
            timesheetid.setCellValueFactory(new PropertyValueFactory<TimeSheet, Integer>("timesheetid"));
            timesheetdate.setCellValueFactory(new PropertyValueFactory<TimeSheet, String>("date"));
            timesheetworktime.setCellValueFactory(new PropertyValueFactory<TimeSheet, Integer>("worktime"));
            timesheetseak.setCellValueFactory(new PropertyValueFactory<TimeSheet, Integer>("seaktime"));
            timesheetholiday.setCellValueFactory(new PropertyValueFactory<TimeSheet, Integer>("holidays"));
            timesheetovertime.setCellValueFactory(new PropertyValueFactory<TimeSheet, Integer>("overtime"));
            timesheettable.setItems(timesheetlist);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void dataForEmpTable() {
        try {
            Connection.writer.writeUTF("dataforemptables "+MyClass.counter);
            Connection.writer.flush();
            String end="";
            String response;
            while(!end.equals("end")) {
                response = Connection.reader.readUTF();
                String[] res=response.split(" ");
                emplist.add(new EmpTable(Integer.parseInt(res[0]),res[1],res[2],res[3],res[4],Integer.parseInt(res[5])));
                end=res[6];
            }
            empid.setCellValueFactory(new PropertyValueFactory<EmpTable, Integer>("id"));
            empsname.setCellValueFactory(new PropertyValueFactory<EmpTable, String>("sname"));
            empname.setCellValueFactory(new PropertyValueFactory<EmpTable, String>("name"));
            empmiddlename.setCellValueFactory(new PropertyValueFactory<EmpTable, String>("middlename"));
            emppost.setCellValueFactory(new PropertyValueFactory<EmpTable, String>("post"));
            empoklad.setCellValueFactory(new PropertyValueFactory<EmpTable, Integer>("oklad"));
            emptable.setItems(emplist);
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

