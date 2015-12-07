package view.new_repair_transaction;

import api.ApiServiceFactory;
import controller.MainFrameController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Master;
import model.Operation;
import model.OperationMaster;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import view.Fragment;
import view.Fragmentable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class NewRepairFragment extends Fragment implements Fragmentable {

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField durationTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField patronymicTextField;

    @FXML
    private DatePicker startDatePicker;

    public NewRepairFragment() {
        connectFxmlAndController("new_repair_fragment.fxml" ,this);
    }

    public NewRepairFragment(MainFrameController controller) {
        this();
        this.controller = controller;
    }

    @Override
    public LocalDate getStartDate() {
        return startDatePicker.getValue();
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
        TableColumn<OperationMaster, Integer> operationColumn =
                new TableColumn<OperationMaster, Integer>("operationId");
        TableColumn<OperationMaster, Integer> masterColumn =
                new TableColumn<OperationMaster, Integer>("masterId");
        TableColumn<OperationMaster, Date> startDateColumn =
                new TableColumn<OperationMaster, Date>("startDate");
        TableColumn<OperationMaster, Date> finishDateColumn =
                new TableColumn<OperationMaster, Date>("finishDate");
        operationColumn.setCellValueFactory(new PropertyValueFactory<OperationMaster, Integer>("operationId"));
        masterColumn.setCellValueFactory(new PropertyValueFactory<OperationMaster, Integer>("masterId"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<OperationMaster, Date>("startDate"));
        finishDateColumn.setCellValueFactory(new PropertyValueFactory<OperationMaster, Date>("finishDate"));
        ArrayList<TableColumn> tableColumns = new ArrayList<>();
        tableColumns.add(operationColumn);
        tableColumns.add(masterColumn);
        tableColumns.add(startDateColumn);
        tableColumns.add(finishDateColumn);
        return tableColumns;
    }

    @FXML
    private void onSendRequestButtonClick() {
        Call<OperationMaster> call = ApiServiceFactory
                .createService()
                .createNewRepair(getTitle(), getDuration(),
                        getPrice(), getName(),
                        getSurname(), getPatronymic(), getStartDate());
        call.enqueue(new Callback<OperationMaster>() {

            @Override
            public void onResponse(Response<OperationMaster> response, Retrofit retrofit) {
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

    public double getPrice() {
        return Double.parseDouble(priceTextField.getText());
    }

    public String getDuration() {
        return durationTextField.getText();
    }

    public String getName() {
        return nameTextField.getText();
    }

    public String getSurname() {
        return surnameTextField.getText();
    }

    public String getPatronymic() {
        return patronymicTextField.getText();
    }

    @Override
    public String toString() {
        return "Добавить информацию о новом ремонте";
    }
}