package models;

import utils.Utilities;

public class DwarfPlanet extends Planet {

    private String surfaceComposition;

    public DwarfPlanet(String name, double mass, double diameter,String energySource, PlanetarySystem planetarySystem,
                       double averageTemperature, String surfaceType, boolean hasLiquidWater,
                       String surfaceComposition) {
        super(name, mass, diameter,energySource, planetarySystem, averageTemperature, surfaceType, hasLiquidWater);
        setSurfaceComposition(surfaceComposition);
    }

    // Getters and Setters
    public String getSurfaceComposition() {
        return surfaceComposition;
    }

    public void setSurfaceComposition(String surfaceComposition) {
        if (surfaceComposition != null) {

            if (surfaceComposition.length() > 100) {
                this.surfaceComposition = surfaceComposition.substring(0, 100);
            } else {
                this.surfaceComposition = surfaceComposition;
            }
        } else {
            this.surfaceComposition = null;
        }
    }

    @Override
    public String classifyBody() {
        return "Dwarf Planet";
    }

    @Override
    public String displayInfo() {
        return "Surface Composition: " + getSurfaceComposition();
    }

    @Override
    public String toString() {
        return super.toString() + " | Gravity: " + calculateGravity();
    }
}
