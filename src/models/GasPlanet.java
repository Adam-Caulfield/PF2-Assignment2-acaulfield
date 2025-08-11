package models;

import utils.Utilities;

public class GasPlanet extends Planet {

    private String gasComposition;
    private String coreComposition;
    private double radiationLevel;

    // Constructor
    public GasPlanet(String name, double mass, double diameter, PlanetarySystem planetarySystem,
                     double averageTemperature, String surfaceType, boolean hasLiquidWater,
                     String gasComposition, String coreComposition, double radiationLevel) {
        super(name, mass, diameter, planetarySystem, averageTemperature, surfaceType, hasLiquidWater);
        setGasComposition(gasComposition);
        setCoreComposition(coreComposition);
        setRadiationLevel(radiationLevel);
    }

    // Getters and Setters
    public String getGasComposition() {
        return gasComposition;
    }

    public void setGasComposition(String gasComposition) {
        if (Utilities.validStringlength(gasComposition, 20)) {  //  max length  20
            this.gasComposition = gasComposition;
        } else {
            this.gasComposition = "mostly gas";  // set a default
        }
    }

    public String getCoreComposition() {
        return coreComposition;
    }

    public void setCoreComposition(String coreComposition) {
        this.coreComposition = coreComposition;
    }

    public double getRadiationLevel() {
        return radiationLevel;
    }

    public void setRadiationLevel(double radiationLevel) {
        if (radiationLevel >= 0.01 && radiationLevel <= 200.05) {  // Range specified that is in spec
            this.radiationLevel = radiationLevel;
        } else {
            this.radiationLevel = 0.9;  // Default
        }
    }


    public String displayInfo() {
        return "Gas Composition: " + getGasComposition() +
                ", Core Composition: " + getCoreComposition() +
                ", Radiation Level: " + getRadiationLevel();
    }


    public String classifyBody() {
        return "Gas planet";
    }

}
