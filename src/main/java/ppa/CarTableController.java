package ppa;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.thoughtworks.xstream.XStream;

import javafx.beans.value.ObservableValueBase;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.converter.IntegerStringConverter;
import ppa.component.CheckBoxEditableTableCell;
import ppa.dao.XlsxImportExportInterface;
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
        dieselColumn.setCellFactory(list -> new CheckBoxEditableTableCell<>());
        dieselColumn.setOnEditCommit(edit -> {
            Car editedCar = carTableView.getEditingCell().getTableView().getItems().get(carTableView.getEditingCell().getRow());
            editedCar.setDiesel(edit.getNewValue());
        });

        TableColumn<Car, Circle> colorColumn = (TableColumn<Car, Circle>) carTableView.getColumns().get(4);
        colorColumn.setCellValueFactory(carCircleCellDataFeatures -> {
            Car car = carCircleCellDataFeatures.getValue();
            String colorString = car.getColor();
            Circle colorCircle = new Circle(10.0);
            colorCircle.setFill(Color.web(colorString));
            return new ObservableValueBase<Circle>() {
                @Override
                public Circle getValue() {
                    return colorCircle;
                }
            };
        });
        /*dieselColumn.setCellFactory(CheckBoxTableCell.forTableColumn(dieselColumn));
        dieselColumn.setOnEditCommit(edit -> {
            Car editedCar = carTableView.getEditingCell().getTableView().getItems().get(carTableView.getEditingCell().getRow());
            editedCar.setDiesel(edit.getNewValue());
        });*/
        File file = new File("cars.xlsx");
        XlsxImportExportInterface importInterface = new XlsxImportExportInterface(file);
        try {
            List<Car> deserializedCars = importInterface.readCars();
            carTableView.getItems().addAll(deserializedCars);
        } catch (Exception e) {
            e.printStackTrace();
            Car car1 = Car.create("Ford", "Focus", 2006, true, 82.0, "#FFCC33");
            Car car2 = Car.create("Porsche", "Carrera", 1976, true, 233.0, "#000000");
            Car car3 = Car.create("Honda", "Civic", 2010, true, 112.0, "#D2A088");
            carTableView.getItems().addAll(car1, car2, car3);
        }
        importInterface.writeCars(carTableView.getItems());
        carTableView.setEditable(true);
        carTableView.getSelectionModel().cellSelectionEnabledProperty().setValue(true);
        carTableView.setOnMouseClicked(click -> {
            if (click.getClickCount() > 1) {
                editFocusedCell();
            }
        });
    }

    private void editFocusedCell() {
        TablePosition<Car, ?> focusedCell = carTableView.focusModelProperty().get().focusedCellProperty().get();
        carTableView.edit(focusedCell.getRow(), focusedCell.getTableColumn());
    }

    /*
    try (FileInputStream fis = new FileInputStream("cars.xml")) {
            List<Car> deserializedCars = (List<Car>) serializer.fromXML(new InputStreamReader(fis));
            carTableView.getItems().addAll(deserializedCars);
        } catch (Exception e) {
            e.printStackTrace();
            Car car1 = Car.create("Ford", "Focus", 2006, true, 82.0, "#FFCC33");
            Car car2 = Car.create("Porsche", "Carrera", 1976, true, 233.0, "#000000");
            Car car3 = Car.create("Honda", "Civic", 2010, true, 112.0, "#D2A088");
            carTableView.getItems().addAll(car1, car2, car3);
        }
        carTableView.setEditable(true);
        carTableView.getSelectionModel().cellSelectionEnabledProperty().setValue(true);
        carTableView.setOnMouseClicked(click -> {
            if (click.getClickCount() > 1) {
                editFocusedCell();
            }
        });
        // chcemy zapis przenieść do jakiegoś przycisku
        String xmlVersion = serializer.toXML(new ArrayList<>(carTableView.getItems()));
        try (FileOutputStream fos = new FileOutputStream("cars.xml")) { // try-with-resources
            try (PrintWriter pw = new PrintWriter(fos)) {
                pw.print(xmlVersion);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        try (PdfWriter writer = new PdfWriter("cars.pdf")) {
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            Table table = new Table(4).useAllAvailableWidth();
            table.addHeaderCell("Make").addHeaderCell("Model").addHeaderCell("Year").addHeaderCell("Diesel");
            for (Car item : carTableView.getItems()) {
                table.startNewRow();
                table.addCell(item.getMake()).addCell(item.getModel()).addCell(item.getYearOfProduction().toString()).
                    addCell(item.isDiesel().toString());
            }
            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
     */



}
