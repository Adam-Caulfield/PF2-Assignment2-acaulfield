package models;

import utils.SpectralTypeUtility;

public abstract class StellarObject extends CelestialBody {

    private char spectralType;
    private double luminosity;

    public StellarObject(String name, double mass, double diameter, PlanetarySystem planetarySystem, char type, double luminosity) {
        super(name, mass, diameter,planetarySystem);


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

    private boolean isValidStellartype(char type) {
        return SpectralTypeUtility.isValidSpectralType(type);
    }

    @Override
    public String displayInfo() {
        return "Spectral Type is: " + spectralType + " - Luminosity is: " + luminosity + ";";
    }
}

/*
More Info
https://lweb.cfa.harvard.edu/~pberlind/atlas/htmls/note.html - spectral type
One fundamental property of a star is the total amount of energy it radiates each second. This energy output is called luminosity or absolute brightness.
https://www.teachastronomy.com/textbook/Properties-of-Stars/Stellar-Luminosity/#:~:text=A%20slightly%20modified%20version%20of,called%20luminosity%20or%20absolute%20brightness.

 */