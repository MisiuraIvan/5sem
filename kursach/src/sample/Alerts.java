package sample;

import javafx.scene.control.Alert;

public class Alerts {
    public static void errorAlert(String str){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(str);
        alert.showAndWait();
    }
    public static void informationAlert(String str) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Сообщение");
        alert.setHeaderText(null);
        alert.setContentText(str);
        alert.showAndWait();
    }
}
