package models;

import utils.SpectralTypeUtility;
import utils.EnergySourceUtility;

public abstract class StellarObject extends CelestialBody {

    private char spectralType;
    private double luminosity;

    public StellarObject(String name, double mass, double diameter, String energySource,
                         PlanetarySystem planetarySystem, char type, double luminosity) {
        super(name, mass, diameter, energySource, planetarySystem);


        if (SpectralTypeUtility.isValidSpectralType(type)) {
            this.spectralType = type;
        } else {
            this.spectralType = 'M'; // default
        }

        // Validate and assign luminosity
        if (luminosity >= 1000 && luminosity <= 200000) {
            this.luminosity = luminosity;
        } else {
            this.luminosity = 1000.0; // default
        }


        if (!EnergySourceUtility.isValidEnergySource(getEnergySource())) {
            setEnergySource("Unknown"); //default
        }
    }

    public char getSpectralType() {
        return spectralType;
    }

    public void setSpectralType(char spectralType) {
        if (SpectralTypeUtility.isValidSpectralType(spectralType)) {
            this.spectralType = spectralType;
        }
    }

    public double getLuminosity() {
        return luminosity;
    }

    public void setLuminosity(double luminosity) {
        if (luminosity >= 1000 && luminosity <= 200000) {
            this.luminosity = luminosity;
        }
    }

    @Override
    public String displayInfo() {
        return "Spectral Type is: " + spectralType + " - Luminosity is: " + luminosity + "Energy Source: " + getEnergySource();
    }
}
