package ppa.dao;

import java.util.List;

import ppa.model.Car;

/**
 * Created by pwilkin on 20-Feb-20.
 */
public interface ImportExportInterface {

    public List<Car> readCars();
    public void writeCars(List<Car> cars);

}
