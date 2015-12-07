package view.new_operation_transaction;

import api.ApiServiceFactory;
import controller.MainFrameController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Master;
import model.Operation;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import view.Fragment;
import view.Fragmentable;

import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;

public class NewOperationFragmentT extends Fragment implements Fragmentable {

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField durationTextField;

    public NewOperationFragmentT() {
        connectFxmlAndController("new_operation_fragment_t.fxml", this);
    }

    public NewOperationFragmentT(MainFrameController controller) {
        this();
        this.controller = controller;
    }

    @Override
    public LocalDate getStartDate() {
        return null;
    }

    @Override
    public LocalDate getExpDate() {
        return null;
    }

    @Override
    public Master getMaster() {
        return null;
    }

    @Override
    public Operation getOperation() {
        return null;
    }

    @Override
    public ArrayList<TableColumn> createColumns() {
        TableColumn<Operation, Integer> idColumn =
                new TableColumn<Operation, Integer>("id");
        TableColumn<Operation, Double> priceColumn =
                new TableColumn<Operation, Double>("price");
        TableColumn<Operation, String> durationColumn =
                new TableColumn<Operation, String>("duration");
        TableColumn<Operation, String> titleColumn =
                new TableColumn<Operation, String>("title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<Operation, String>("title"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Operation, Double>("price"));
        idColumn.setCellValueFactory(new PropertyValueFactory<Operation, Integer>("id"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<Operation, String>("duration"));
        ArrayList<TableColumn> tableColumns = new ArrayList<>();
        tableColumns.add(idColumn);
        tableColumns.add(titleColumn);
        tableColumns.add(durationColumn);
        tableColumns.add(priceColumn);
        return tableColumns;
    }
    @FXML
    private void onSendRequestButtonClick(ActionEvent event) {
        Call<Operation> call = ApiServiceFactory
                .createService()
                .createNewOperation(getTitle(), getDuration(), getPrice());
        call.enqueue(new Callback<Operation> () {

            @Override
            public void onResponse(Response<Operation> response, Retrofit retrofit) {
                showAlert(response.code());
                Platform.runLater(() -> {
                    controller.setTableColumns(createColumns());
                    controller.setTableData(FXCollections.observableArrayList(response.body()));
                });
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    public String getTitle() {
        return titleTextField.getText();
    }

    public String getDuration() {
        //Pattern pattern = new Pattern.compile("\d\d \d\d:\d\d");
        return durationTextField.getText();
    }

    public Double getPrice() {
        return Double.parseDouble(priceTextField.getText());
    }

    @Override
    public String toString() {
        return "Добавить информацию о новой операции";
    }
}