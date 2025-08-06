package models;

public class Star extends StellarObject {
    private double gravity;
    public Star(String name, double mass, double diameter, PlanetarySystem planetarySystem, char type, double luminosity) {
        super(name, mass, diameter, planetarySystem, type, luminosity);
    }

    @Override
    public String displayInfo() {
        return "Star: " + getName() + ", Spectral Type: " + getSpectralType() + ", Luminosity: " + getLuminosity();
    }

    @Override
    public String classifyBody() {
        return "Star";
    }

    public double calculateGravity() {
        double radius = getDiameter() / 2.0;
        gravity =  (getMass() * 6.67430e-11) / (radius * radius);
        return gravity;
    }
    public String toString() {
        return "Star: " + getName()+
                "gravity"+ gravity;
    }
}