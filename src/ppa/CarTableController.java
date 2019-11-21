package ppa;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

        TableColumn<Car, String> modelColumn = (TableColumn<Car, String>) carTableView.getColumns().get(1);
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Car, Integer> yearColumn = (TableColumn<Car, Integer>) carTableView.getColumns().get(2);
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("yearOfProduction"));

        Car car1 = Car.create("Ford", "Focus", 2006, 1399.0, 82.0, "FFCC33");
        Car car2 = Car.create("Porsche", "Carrera", 1976, 4600.0, 233.0, "000000");
        Car car3 = Car.create("Honda", "Civic", 2010, 1699.0, 112.0, "D2A088");
        carTableView.getItems().addAll(car1, car2, car3);
    }

}
