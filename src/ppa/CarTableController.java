package ppa;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import ppa.model.Car;

/**
 * Created by pwilkin on 21-Nov-19.
 */
public class CarTableController {

    @FXML
    protected TableView<Car> carTableView;

    public void initialize() {
        TableColumn<Car, String> makeColumn = (TableColumn<Car, String>) carTableView.getColumns().get(0);
        makeColumn.setCellValueFactory(new PropertyValueFactory<>("make"));
        makeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        makeColumn.setOnEditCommit(edit -> {
            Car editedCar = carTableView.getEditingCell().getTableView().getItems().get(carTableView.getEditingCell().getRow());
            editedCar.setMake(edit.getNewValue());
        });

        TableColumn<Car, String> modelColumn = (TableColumn<Car, String>) carTableView.getColumns().get(1);
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        modelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        modelColumn.setOnEditCommit(edit -> {
            Car editedCar = carTableView.getEditingCell().getTableView().getItems().get(carTableView.getEditingCell().getRow());
            editedCar.setModel(edit.getNewValue());
        });

        TableColumn<Car, Integer> yearColumn = (TableColumn<Car, Integer>) carTableView.getColumns().get(2);
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("yearOfProduction"));
        yearColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        yearColumn.setOnEditCommit(edit -> {
            Car editedCar = carTableView.getEditingCell().getTableView().getItems().get(carTableView.getEditingCell().getRow());
            editedCar.setYearOfProduction(edit.getNewValue());
        });

        TableColumn<Car, Boolean> dieselColumn = (TableColumn<Car, Boolean>) carTableView.getColumns().get(3);
        dieselColumn.setCellValueFactory(new PropertyValueFactory<>("diesel"));
        dieselColumn.setCellFactory(CheckBoxTableCell.forTableColumn(dieselColumn));
        dieselColumn.setOnEditCommit(edit -> {
            Car editedCar = carTableView.getEditingCell().getTableView().getItems().get(carTableView.getEditingCell().getRow());
            editedCar.setDiesel(edit.getNewValue());
        });

        Car car1 = Car.create("Ford", "Focus", 2006, true, 82.0, "FFCC33");
        Car car2 = Car.create("Porsche", "Carrera", 1976, false, 233.0, "000000");
        Car car3 = Car.create("Honda", "Civic", 2010, false, 112.0, "D2A088");
        carTableView.getItems().addAll(car1, car2, car3);
        carTableView.setEditable(true);
        carTableView.setOnMouseClicked(click -> {
            if (click.getClickCount() > 1) {
                carTableView.edit(carTableView.getSelectionModel().getSelectedIndex(), makeColumn);
            }
        });
    }

}
