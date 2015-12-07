package view.master_operations;

import api.ApiServiceFactory;
import controller.MainFrameController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Master;
import model.MasterOperations;
import model.Operation;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import view.Fragment;
import view.Fragmentable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class MasterOperationsFragment extends Fragment implements Fragmentable {

    @FXML
    private Button sendRequestButton;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker expDatePicker;

    @FXML
    private ComboBox masterComboBox;

    public MasterOperationsFragment() {
        connectFxmlAndController("master_operations_fragment.fxml", this);
        Call<Master[]> call = ApiServiceFactory
                .createService()
                .getMasters();
        call.enqueue(new Callback<Master[]>() {
            public void onResponse(Response<Master[]> response, Retrofit retrofit) {
                showAlert(response.code());
                ObservableList<Master> list = FXCollections.observableArrayList(response.body());
                masterComboBox.setItems(list);
            }

            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    public MasterOperationsFragment(MainFrameController controller) {
        this();
        this.controller = controller;
    }

    public LocalDate getStartDate() {
        return startDatePicker.getValue();
    }

    public LocalDate getExpDate() {
        return expDatePicker.getValue();
    }

    public Master getMaster() {
        return (Master) masterComboBox.getValue();
    }

    public Operation getOperation() {
        return null;
    }

    @Override
    public ArrayList<TableColumn> createColumns() {
        TableColumn<MasterOperations, String> titleColumn =
                new TableColumn<MasterOperations, String>("title");
        TableColumn<MasterOperations, Double> priceColumn =
                new TableColumn<MasterOperations, Double>("price");
        TableColumn<MasterOperations, Date> startColumn =
                new TableColumn<MasterOperations, Date>("startDate");
        TableColumn<MasterOperations, Date> finishColumn =
                new TableColumn<MasterOperations, Date>("finishDate");
        titleColumn.setCellValueFactory(new PropertyValueFactory<MasterOperations, String>("title"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<MasterOperations, Double>("price"));
        startColumn.setCellValueFactory(new PropertyValueFactory<MasterOperations, Date>("startDate"));
        finishColumn.setCellValueFactory(new PropertyValueFactory<MasterOperations, Date>("finishDate"));
        ArrayList<TableColumn> tableColumns = new ArrayList<>();
        tableColumns.add(titleColumn);
        tableColumns.add(priceColumn);
        tableColumns.add(startColumn);
        tableColumns.add(finishColumn);
        return tableColumns;
    }

    @FXML
    private void onSendRequestButtonClick() {
        Master master = getMaster();
        Call<MasterOperations[]> call = ApiServiceFactory
                .createService()
                .getMasterOperations(
                        master.getId(),
                        getStartDate(),
                        getExpDate());
        call.enqueue(new Callback<MasterOperations[]>() {

            public void onResponse(Response<MasterOperations[]> response, Retrofit retrofit) {
                showAlert(response.code());
                Platform.runLater(() -> {
                    controller.setTableColumns(createColumns());
                    controller.setTableData(FXCollections.observableArrayList(response.body()));
                });
            }

            public void onFailure(Throwable throwable) {
                        throwable.printStackTrace();
                    }
        });
    }

    @Override
    public String toString() {
        return "Перечень операций за данный период";
    }

}
