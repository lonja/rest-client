package view.master_details_expense;

import api.ApiServiceFactory;
import controller.MainFrameController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import view.Fragment;
import view.Fragmentable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;


public class MasterDetailsExpenseFragment extends Fragment implements Fragmentable {
    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker expDatePicker;

    @FXML
    private ComboBox masterComboBox;

    private MainFrameController controller;

    public MasterDetailsExpenseFragment() {
        connectFxmlAndController("master_details_expense_fragment.fxml", this);
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

    public MasterDetailsExpenseFragment(MainFrameController controller) {
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
        TableColumn<MasterDetailsExpense, Date> dateColumn =
                new TableColumn<MasterDetailsExpense, Date>("date");
        TableColumn<MasterDetailsExpense, String> titleColumn =
                new TableColumn<MasterDetailsExpense, String>("title");
        TableColumn<MasterDetailsExpense, Integer> quantityColumn =
                new TableColumn<MasterDetailsExpense, Integer>("quantity");
        dateColumn.setCellValueFactory(new PropertyValueFactory<MasterDetailsExpense, Date>("date"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<MasterDetailsExpense, String>("title"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<MasterDetailsExpense, Integer>("quantity"));
        ArrayList<TableColumn> tableColumns = new ArrayList<>();
        tableColumns.add(dateColumn);
        tableColumns.add(titleColumn);
        tableColumns.add(quantityColumn);
        return tableColumns;
    }

    @FXML
    private void onSendRequestButtonClick() {
        Master master = getMaster();
        Call<MasterDetailsExpense[]> call = ApiServiceFactory
                .createService()
                .getMasterDetailsExpense(master.getId(),
                        getStartDate(),
                        getExpDate());
        call.enqueue(new Callback<MasterDetailsExpense[]>() {
            @Override
            public void onResponse(Response<MasterDetailsExpense[]> response, Retrofit retrofit) {
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

    @Override
    public String toString() {
        return "Ведомость расхода деталей определенным мастером";
    }

}
