package models;

public class Star extends StellarObject {
    private double gravity;

    public Star(String name, double mass, double diameter, String energySource,
                PlanetarySystem planetarySystem, char type, double luminosity) {
        super(name, mass, diameter, energySource, planetarySystem, type, luminosity);
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
        gravity = (getMass() * 6.67430e-11) / (radius * radius);
        return gravity;
    }

    @Override
    public String toString() {
        return "Star Details:\n" +
                "Name: " + getName() + "\n" +
                "Mass: " + getMass() + "\n" +
                "Diameter: " + getDiameter() + "\n" +
                "Gravity: " + calculateGravity() + "\n" +
                "Spectral Type: " + getSpectralType() + "\n" +
                "Luminosity: " + getLuminosity() + "\n" +
                "Energy Source: " + getEnergySource() + "\n" +
                "Planetary System: " + getPlanetarySystem().getSystemName();
    }
}