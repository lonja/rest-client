package view.new_operation;

import api.ApiServiceFactory;
import controller.MainFrameController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Master;
import model.NewOperation;
import model.Operation;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import view.Fragment;
import view.Fragmentable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class NewOperationFragment extends Fragment implements Fragmentable {

    @FXML
    private ComboBox<Operation> operationComboBox;

    @FXML
    private DatePicker startDatePicker;

    private MainFrameController controller;

    public NewOperationFragment() {
        connectFxmlAndController("new_operation_fragment.fxml", this);
        Call<Operation[]> call = ApiServiceFactory
                .createService()
                .getOperations();
        call.enqueue(new Callback<Operation[]>() {
            public void onResponse(Response<Operation[]> response, Retrofit retrofit) {
                showAlert(response.code());
                ObservableList<Operation> list = FXCollections.observableArrayList(response.body());
                operationComboBox.setItems(list);
            }

            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    public NewOperationFragment(MainFrameController controller) {
        this();
        this.controller = controller;
    }

    public Operation getOperation() {
        return operationComboBox.getValue();
    }

    public LocalDate getStartDate() {
        return startDatePicker.getValue();
    }

    public LocalDate getExpDate() {
        return null;
    }

    public Master getMaster() {
        return null;
    }

    @FXML
    private void onSendRequestButtonClick() {
        Operation operation = this.getOperation();
        Call<NewOperation> call = ApiServiceFactory
                .createService()
                .startNewOperation(
                        operation.getId(),
                        getStartDate());
        call.enqueue(new Callback<NewOperation>() {
            public void onResponse(Response<NewOperation> response, Retrofit retrofit) {
                showAlert(response.code());
                Platform.runLater(() -> {
                    controller.setTableColumns(createColumns());
                    controller.setTableData(FXCollections.observableArrayList(response.body()));
                });
            }

            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void setTableColumns(TableView table, Collection columns) {
        Platform.runLater(() -> {
            table.getColumns().addAll(columns);
        });
    }

    public void setTableData(TableView table, ObservableList collection) {
        Platform.runLater(() -> {
            table.setItems(collection);
        });
    }

    public ArrayList<TableColumn> createColumns() {
        TableColumn<NewOperation, Integer> masterColumn =
                new TableColumn<NewOperation, Integer>("id");
        TableColumn<NewOperation, Integer> operationColumn =
                new TableColumn<NewOperation, Integer>("operationId");
        TableColumn<NewOperation, Date> dateColumn =
                new TableColumn<NewOperation, Date>("date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<NewOperation, Date>("date"));
        operationColumn.setCellValueFactory(new PropertyValueFactory<NewOperation, Integer>("OperationId"));
        masterColumn.setCellValueFactory(new PropertyValueFactory<NewOperation, Integer>("masterId"));
        ArrayList<TableColumn> tableColumns = new ArrayList<>();
        tableColumns.add(masterColumn);
        tableColumns.add(operationColumn);
        tableColumns.add(dateColumn);
        return tableColumns;
    }

    @Override
    public String toString() {
        return "Добавить новую операцию";
    }
}