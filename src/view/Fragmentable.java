package view;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Master;
import model.Operation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by lonja on 29.11.15.
 */
public interface Fragmentable {
    LocalDate getStartDate();
    LocalDate getExpDate();
    Master getMaster();
    Operation getOperation();
    ArrayList<TableColumn> createColumns();
}
