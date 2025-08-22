package models;

import utils.Utilities;
import java.util.Objects;

public class PlanetarySystem {
    public String getSystemID;
    private String systemName;        // e.g. Solar System - max 50 chars
    private String orbittingStarName; // e.g. Sun - max 30 chars
    private boolean habitable;
    private int age;                  // in billions of years
    private int discovered;           // year discovered (1900–2025)
    private String systemType;
    // Full constructor
    public PlanetarySystem(String systemName, String orbittingStarName, int age, boolean habitable, int discovered,String systemType) {
        this.systemName = Utilities.truncateString(systemName, 50);
        this.orbittingStarName = Utilities.truncateString(orbittingStarName, 30);
        this.age = age;
        this.habitable = habitable;
        setDiscovered(discovered);
        this.systemType = systemType;
    }

    // Getters & Setters
    public String getSystemName() {
        return systemName;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }
    public String getSystemType() {
        return systemType;
    }

    public void setSystemName(String systemName) {
        if (Utilities.validStringlength(systemName, 50)) {
            this.systemName = systemName;
        }
    }

    public String getOrbittingStarName() {
        return orbittingStarName;
    }

    public void setOrbittingStarName(String orbittingStarName) {
        if (Utilities.validStringlength(orbittingStarName, 30)){
            this.orbittingStarName = orbittingStarName;
        }
    }

    public boolean isHabitable() {
        return habitable;
    }

    public void setHabitable(boolean habitable) {
        this.habitable = habitable;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getDiscovered() {
        return discovered;
    }

    // Validate discovered year between 1900 and 2025
    public void setDiscovered(int discovered) {
        if (discovered >= 1900 && discovered <= 2025) {
            this.discovered = discovered;
        } else {
            this.discovered = -1; // invalid year
        }
    }

    public void setGetSystemID(String getSystemID) {
        this.getSystemID = getSystemID;
    }

    public String getGetSystemID() {
        return getSystemID;
    }

    // Equals & hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanetarySystem that = (PlanetarySystem) o;
        return Objects.equals(systemName, that.systemName) && Objects.equals(orbittingStarName, that.orbittingStarName);
    }

    @Override
    public String toString() {
        return "Planetary System: " +
                "Name: " + systemName +
                ", Orbits Around: " + orbittingStarName.toUpperCase() +
                ", Habitable: " + habitable +
                ", Age: " + age +
                " Billion years" +
                ", Discovered: " + discovered +
                ", System Type: " + systemType;
    }
}
