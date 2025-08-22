package models;

public class IcePlanet extends Planet {

    private String iceComposition;

    public IcePlanet(String name, double mass, double diameter,String energySource, PlanetarySystem planetarySystem,
                     double averageTemperature, String surfaceType, boolean hasLiquidWater,
                     String iceComposition) {
        super(name, mass, diameter,energySource, planetarySystem, averageTemperature, surfaceType, hasLiquidWater);
        this.iceComposition = iceComposition;
    }


    public void setIceComposition(String iceComposition) {
        if (iceComposition != null && !iceComposition.isBlank()) {
            this.iceComposition = iceComposition;
        }
    }

    public String getIceComposition() {
        return iceComposition;
    }

    @Override
    public String classifyBody() {
        return "Ice Planet";
    }




    @Override
    public String displayInfo() {
        return toString() + "\nIce Composition: " + iceComposition;
    }
}
