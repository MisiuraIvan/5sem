package sample;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button inbutton;

    @FXML
    private TextField loginfield;

    @FXML
    private PasswordField passwordfield;

    @FXML
    private Button regbutton;

    @FXML
    void initialize() {
        Connection.getConnection();
        inbutton.setOnAction(event -> {
            String login,password;
            login=loginfield.getText();
            password=passwordfield.getText();
            if(!login.equals("") && !password.equals("")){
                loginUser(login,password);
            }else{
                System.out.println("Логин или пароль не заполнены");
                Alerts.informationAlert("Логин или пароль не заполнены");
            }
        });

        regbutton.setOnAction(actionEvent -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("reg.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage window=(Stage)regbutton.getScene().getWindow();
            window.setScene(new Scene(root,600,400));
        });
    }

    private void loginUser(String login, String password) {
        System.out.println(Connection.socket);
        try {
            Connection.writer.writeUTF("loginuser "+login + " " + password+" ");
            Connection.writer.flush();
            String res=Connection.reader.readUTF();
            String[] response=res.split(" ");
            if(response[0].equals("accounter")){
                openWindow("accounter.fxml");
            }else{
                if(response[0].equals("user")){
                    MyClass.counter=Integer.parseInt(response[1]);
                    openWindow("user.fxml");
                }else {
                    if (response[0].equals("dir")) {
                        openWindow("admin.fxml");
                    } else
                        Alerts.errorAlert("Неверный логин или пароль");
                }
            }
            System.out.println("req: "+response);
        }catch(IOException e){
           e.printStackTrace();
        }
    }
    private void openWindow(String str){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(str));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage window=(Stage)inbutton.getScene().getWindow();
        window.setScene(new Scene(root,960,753));
        Alerts.informationAlert("Успешная авторизация");
    }
}

