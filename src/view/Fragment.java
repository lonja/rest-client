package view;

import controller.MainFrameController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;

import java.io.IOException;

public abstract class Fragment extends VBox {

    public Alert alert = new Alert(Alert.AlertType.ERROR);
    protected MainFrameController controller;

    protected void showAlert(int errorCode) {
        Platform.runLater(() -> {
            alert.setTitle("Ошибка");
            switch (errorCode) {
                case 400: {
                    alert.setHeaderText("Статус 400");
                    alert.setContentText("Bad request");
                }
                case 404: {
                    alert.setHeaderText("Статус 404");
                    alert.setContentText("Resource is not found");
                    break;
                }
                case 500: {
                    alert.setHeaderText("Статус 404");
                    alert.setContentText("Internal server error");
                    break;
                }
                default: {
                    return;
                }
            }
            alert.showAndWait();
        });
    }

    protected void connectFxmlAndController(String path, Fragment fragment) {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource(path));
        loader.setRoot(fragment);
        loader.setController(fragment);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}