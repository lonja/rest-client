package view.details_expense;

import api.ApiServiceFactory;
import controller.MainFrameController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.DetailsExpense;
import model.Master;
import model.Operation;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import view.Fragment;
import view.Fragmentable;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class DetailsExpenseFragment extends Fragment implements Fragmentable {

    @FXML
    private Button sendRequestButton;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker expDatePicker;

    private MainFrameController controller;

    public DetailsExpenseFragment() {
        connectFxmlAndController("details_expense_fragment.fxml", this);
    }

    public DetailsExpenseFragment(MainFrameController controller) {
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
        return null;
    }

    public Operation getOperation() {
        return null;
    }

    @Override
    public String toString() {
        return "Ведомость расхода деталей за указанный период";
    }

    @FXML
    private void onSendRequestButtonClick() {
        Call<DetailsExpense[]> call = ApiServiceFactory
                .createService()
                .getDetailExpense(
                        getStartDate(),
                        getExpDate());
        call.enqueue(new Callback<DetailsExpense[]>() {
            public void onResponse(Response<DetailsExpense[]> response, Retrofit retrofit) {
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

    public ArrayList<TableColumn> createColumns() {
        TableColumn<DetailsExpense, Date> dateColumn =
                new TableColumn<DetailsExpense, Date>();
        TableColumn<DetailsExpense, String> titleColumn =
                new TableColumn<DetailsExpense, String>();
        TableColumn<DetailsExpense, String> quantityColumn =
                new TableColumn<DetailsExpense, String>();
        dateColumn.setCellValueFactory(new PropertyValueFactory<DetailsExpense, Date>("date"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<DetailsExpense, String>("title"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<DetailsExpense, String>("quantity"));
        ArrayList<TableColumn> tableColumns = new ArrayList<>();
        tableColumns.add(dateColumn);
        tableColumns.add(titleColumn);
        tableColumns.add(quantityColumn);
        return tableColumns;
    }
}