package ppa.model;

/**
 * Created by pwilkin on 21-Nov-19.
 */
public class Car {

    protected String make;
    protected String model;
    protected Integer yearOfProduction;
    protected Double engineCapacity; // ccm
    protected Double enginePower; // KM
    protected String color; // rgb

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(Integer yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public Double getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(Double engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public Double getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(Double enginePower) {
        this.enginePower = enginePower;
    }

    public static Car create(String make, String model, Integer yearOfProduction, Double engineCapacity, Double enginePower, String color) {
        Car car = new Car();
        car.setMake(make);
        car.setModel(model);
        car.setYearOfProduction(yearOfProduction);
        car.setEngineCapacity(engineCapacity);
        car.setEnginePower(enginePower);
        car.setColor(color);
        return car;
    }
}
