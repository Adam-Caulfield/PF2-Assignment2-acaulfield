package models;

public abstract class CelestialBody {
    private static int nextId = 1000;
    private int id;
    private String name;
    private double mass;
    private double diameter;
    private PlanetarySystem planetarySystem;

    public CelestialBody(String name, double mass, double diameter, PlanetarySystem planetarySystem) {
        this.id = nextId++;

        setName(name);
        setMass(mass);
        setDiameter(diameter);

        if (planetarySystem == null) {
            throw new IllegalArgumentException("Planetary System must not be null");
        }
        this.planetarySystem = planetarySystem;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMass() {
        return mass;
    }

    public double getDiameter() {
        return diameter;
    }

    public PlanetarySystem getPlanetarySystem() {
        return planetarySystem;
    }

    public void setName(String name) {
        if (name != null && !name.isBlank()) {
            this.name = name.length() > 30 ? name.substring(0, 30) : name;
        } else {
            this.name = "Unnamed";
        }
    }

    public void setMass(double mass) {
        if (mass > 0.1) {
            this.mass = mass;
        } else {
            this.mass = 0.1;
        }

    }

    public void setDiameter(double diameter) {
        this.diameter = diameter > 0.5 ? diameter : 0.5;
    }

    public void setPlanetarySystem(PlanetarySystem planetarySystem) {
        if (planetarySystem == null) {
            throw new IllegalArgumentException("Planetary System must not be null");
        }
        this.planetarySystem = planetarySystem;
    }

    public abstract String displayInfo();

    public abstract double calculateGravity();

    public abstract String classifyBody();

}