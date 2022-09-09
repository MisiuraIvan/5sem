package sample;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backbutton;

    @FXML
    private TextField middlenamefield;

    @FXML
    private TextField loginfield;

    @FXML
    private TextField namefield;

    @FXML
    private TextField passwordfield;

    @FXML
    private TextField postfield;

    @FXML
    private Button regbutton;

    @FXML
    private TextField snamefield;

    @FXML
    void initialize() {
        regbutton.setOnAction(event->{
            registration();
        });

        backbutton.setOnAction(actionEvent -> {
            goBack();
        });

    }

    private void goBack() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage window=(Stage)backbutton.getScene().getWindow();
        window.setScene(new Scene(root,600,400));
    }

    private void registration() {
            String login, password, name, sname, post, middlename;
            login = loginfield.getText();
            password = passwordfield.getText();
            name = namefield.getText();
            sname = snamefield.getText();
            post = postfield.getText();
            middlename = middlenamefield.getText();
            Validator validator=new Validator(RegularEx.NAME_PATTERN);
            if (!name.equals("") && validator.validate(login) && !password.equals("") && !middlename.equals("") && !post.equals("") && !sname.equals("")) {
                regUser(login, password, name, sname, middlename,post);
            } else {
                System.out.println("Не все поля заполнены");
                Alerts.errorAlert("Ошибка регистрации");
            }
    }

    private void regUser(String login, String password, String name, String sname,  String middlename,String post) {
        try {
            Connection.writer.writeUTF("reguser "+login + " " + password+" "+name+" "+sname+" "+middlename+" "+post+" ");
            Connection.writer.flush();
            String response=Connection.reader.readUTF();
            if(response.equals("ok")){
                Alerts.informationAlert("Регистрация прошла успешно!");
                goBack();
            }
            else{
                if(response.equals("nopost")) {
                    Alerts.errorAlert("Укажите должность корректно");
                }else
                    Alerts.errorAlert("Уже существует пользователь с таким логином");
            }
            System.out.println("req: "+response);
            } catch(IOException e){
            e.printStackTrace();
        }
    }
}
