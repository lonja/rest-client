package view.master_operations_sum;

import api.ApiServiceFactory;
import controller.MainFrameController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Master;
import model.MasterOperationsSum;
import model.NewOperation;
import model.Operation;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import view.Fragment;
import view.Fragmentable;

import java.awt.event.ActionEvent;
import java.awt.geom.Arc2D;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringJoiner;

public class MasterOperationsSumFragment extends Fragment implements Fragmentable {

    @FXML
    private Button sendRequestButton;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker expDatePicker;

    private MainFrameController controller;

    public MasterOperationsSumFragment() {
        connectFxmlAndController("master_operations_sum_fragment.fxml", this);
        //super("master_operations_sum_fragment.fxml", this);
    }

    public MasterOperationsSumFragment(MainFrameController controller) {
        this();
        this.controller = controller;
    }

    public LocalDate getStartDate() {
        return startDatePicker.getValue();
    }

    public LocalDate getExpDate() {
        return expDatePicker.getValue();
    }

    @Override
    public Master getMaster() {
        return null;
    }

    public Operation getOperation() {
        return null;
    }

    @Override
    public ArrayList<TableColumn> createColumns() {
        TableColumn<MasterOperationsSum, Integer> masterColumn =
                new TableColumn<MasterOperationsSum, Integer>("id");
        TableColumn<MasterOperationsSum, String> surnameColumn =
                new TableColumn<MasterOperationsSum, String>("surname");
        TableColumn<MasterOperationsSum, String> nameColumn =
                new TableColumn<MasterOperationsSum, String>("name");
        TableColumn<MasterOperationsSum, Double> sumColumn =
                new TableColumn<MasterOperationsSum, Double>("sum");
        surnameColumn.setCellValueFactory(new PropertyValueFactory<MasterOperationsSum, String>("surname"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<MasterOperationsSum, String>("name"));
        masterColumn.setCellValueFactory(new PropertyValueFactory<MasterOperationsSum, Integer>("id"));
        sumColumn.setCellValueFactory(new PropertyValueFactory<MasterOperationsSum, Double>("sum"));
        ArrayList<TableColumn> tableColumns = new ArrayList<>();
        tableColumns.add(masterColumn);
        tableColumns.add(surnameColumn);
        tableColumns.add(nameColumn);
        tableColumns.add(sumColumn);
        return tableColumns;
    }

    @FXML
    private void onSendRequestButtonClick() {
        Call<MasterOperationsSum> call = ApiServiceFactory
                .createService()
                .getMasterOperationsSum(
                        getStartDate(),
                        getExpDate());
        call.enqueue(new Callback<MasterOperationsSum>() {

            public void onResponse(Response<MasterOperationsSum> response, Retrofit retrofit) {
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
    @Override
    public String toString() {
        return "Сумма всех операций, выполеных мастером за определенный период";
    }
}