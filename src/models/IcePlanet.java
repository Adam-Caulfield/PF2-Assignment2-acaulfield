package models;

public class IcePlanet extends Planet {

    private String iceComposition;

    public IcePlanet(String name, double mass, double diameter, PlanetarySystem planetarySystem,
                     double averageTemperature, String surfaceType, boolean hasLiquidWater,
                     String iceComposition) {
        super(name, mass, diameter, planetarySystem, averageTemperature, surfaceType, hasLiquidWater);
        this.iceComposition = iceComposition;
    }
    private void setIceComposition(String iceComposition) {
    }

    @Override
    public String classifyBody() {
        return "";
    }
}
