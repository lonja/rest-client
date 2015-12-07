package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Fragments;
import view.details_expense.DetailsExpenseFragment;
import view.master_details_expense.MasterDetailsExpenseFragment;
import view.master_operations.MasterOperationsFragment;
import view.master_operations_sum.MasterOperationsSumFragment;
import view.new_operation.NewOperationFragment;
import view.new_operation_transaction.NewOperationFragmentT;
import view.new_repair_transaction.NewRepairFragment;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class MainFrameController implements Initializable {

    private Fragments fragments = new Fragments(
            new NewOperationFragment(this),
            new MasterOperationsFragment(this),
            new MasterOperationsSumFragment(this),
            new MasterDetailsExpenseFragment(this),
            new DetailsExpenseFragment(this),
            new NewRepairFragment(this),
            new NewOperationFragmentT(this));

    @FXML
    private AnchorPane responseParamsPane;

    @FXML
    private ComboBox queryComboBox;
    
    @FXML
    private TableView responseTable;

    public void setQueries(String... items) {
        ObservableList list = FXCollections.observableArrayList();
        list.addAll(items);
        queryComboBox.setItems(list);
    }



    @FXML
    public void onQuerySelected(ActionEvent actionEvent) {
        String key = queryComboBox.getValue().toString();
        responseParamsPane.getChildren().clear();
        responseParamsPane.getChildren().addAll(
                fragments.getFragment(key));
    }

    public void initialize(URL location, ResourceBundle resources) {
//        setQueries(
//                new NewOperationFragmentT(this).toString(),
//                new MasterOperationsFragment(this).toString(),
//                new MasterOperationsSumFragment(this).toString(),
//                new MasterDetailsExpenseFragment(this).toString(),
//                new DetailsExpenseFragment(this).toString()
//        );
        ObservableList<VBox> list = fragments.getAllFragments();
        for (VBox item: list) {
            queryComboBox.getItems().add(item.toString());
        }

        //queryComboBox.setItems(fragments.getAllFragments());
    }

    public TableView getResponseTable() {
        return responseTable;
    }

    public void setTableColumns(Collection columns) {
        responseTable.getColumns().clear();
        responseTable.getColumns().addAll(columns);
    };
    public void setTableData(ObservableList collection) {
        responseTable.setItems(collection);
    };

    public void sendRequestButtonClick(ActionEvent actionEvent) {
        
    }
}