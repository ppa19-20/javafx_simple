package ppa.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ppa.model.Car;

/**
 * Created by pwilkin on 20-Feb-20.
 */
public class XlsxImportExportInterface implements ImportExportInterface {

    private final File xlsxFile;
    private boolean headersFirstRow = true;

    public boolean isHeadersFirstRow() {
        return headersFirstRow;
    }

    public void setHeadersFirstRow(boolean headersFirstRow) {
        this.headersFirstRow = headersFirstRow;
    }

    public XlsxImportExportInterface(File xlsxFile) {
        this.xlsxFile = xlsxFile;
    }

    @Override
    public List<Car> readCars() {
        try {
            List<Car> cars = new ArrayList<>();
            XSSFWorkbook workbook = new XSSFWorkbook(xlsxFile);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iter = sheet.rowIterator();
            boolean firstRow = true;
            while (iter.hasNext()) {
                Row row = iter.next();
                if (!firstRow || !headersFirstRow) {
                    Car car = new Car();
                    car.setModel(getCellStringValue(row, 0));
                    car.setMake(getCellStringValue(row, 1));
                    Number yearNum = getCellNumericValue(row, 2);
                    if (yearNum != null) {
                        car.setYearOfProduction(yearNum.intValue());
                    }
                    car.setDiesel(getBooleanCellValue(row, 3));
                    Number engineNum = getCellNumericValue(row, 4);
                    if (engineNum != null) {
                        car.setEnginePower(engineNum.doubleValue());
                    }
                    car.setColor(getCellStringValue(row, 5));
                    cars.add(car);
                }
                firstRow = false;
            }
            return cars;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Boolean getBooleanCellValue(Row row, int col) {
        Cell cell = row.getCell(col, MissingCellPolicy.RETURN_NULL_AND_BLANK);
        return cell == null ? null : cell.getBooleanCellValue();
    }

    private Number getCellNumericValue(Row row, int col) {
        Cell cell = row.getCell(col, MissingCellPolicy.RETURN_NULL_AND_BLANK);
        return cell == null ? null : cell.getNumericCellValue();
    }

    private String getCellStringValue(Row row, int col) {
        Cell cell = row.getCell(col, MissingCellPolicy.RETURN_NULL_AND_BLANK);
        return cell == null ? null : cell.getStringCellValue();
    }

    @Override
    public void writeCars(List<Car> cars) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet();
            int rowNum = 0;
            if (headersFirstRow) {
                XSSFRow firstRow = getOrCreateRow(sheet, rowNum);
                rowNum++;
                firstRow.getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue("Make");
                firstRow.getCell(1, MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue("Model");
                firstRow.getCell(2, MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue("Year");
                firstRow.getCell(3, MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue("Is diesel?");
                firstRow.getCell(4, MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue("Engine power");
                firstRow.getCell(5, MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue("Color");
            }
            if (cars != null) {
                for (Car car : cars) {
                    XSSFRow row = getOrCreateRow(sheet, rowNum);
                    rowNum++;
                    row.getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(car.getMake());
                    row.getCell(1, MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(car.getModel());
                    row.getCell(2, MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(car.getYearOfProduction());
                    row.getCell(3, MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(car.isDiesel());
                    row.getCell(4, MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(car.getEnginePower());
                    row.getCell(5, MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(car.getColor());
                }
            }
            try (FileOutputStream fos = new FileOutputStream(xlsxFile)) {
                workbook.write(fos);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private XSSFRow getOrCreateRow(XSSFSheet sheet, int rowNum) {
        XSSFRow row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }
        return row;
    }
}
