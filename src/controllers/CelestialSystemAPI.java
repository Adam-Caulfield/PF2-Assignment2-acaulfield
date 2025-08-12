package controllers;

import models.*;

import java.io.File;
import java.util.ArrayList;

public class CelestialSystemAPI {
    private ArrayList<Star> stars = new ArrayList<>();
    private ArrayList<IcePlanet> icePlanets = new ArrayList<>();
    private ArrayList<GasPlanet> gasPlanets = new ArrayList<>();

    public CelestialSystemAPI(File file) {
    }

    public boolean topFiveHighestRadiationGasPlanet() {
        for (int i = gasPlanets.size() - 1; i >= 0; i--) {
            int highestIndex = 0;
            for (int j = 0; j <= i; j++) {
                if (gasPlanets.get(j).getRadiationLevel() > gasPlanets.get(highestIndex).getRadiationLevel()) {
                    highestIndex = j;
                }
            }
            swapPlanets(gasPlanets, i, highestIndex);
        }
        return true;
    }

    private void swapPlanets(ArrayList<GasPlanet> list, int i, int j) {
        GasPlanet temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    public String listAllGasPlanets() {
        if (gasPlanets.isEmpty()) {
            return "No Gas Planets";
        }
        String result = "";
        for (int i = 0; i < gasPlanets.size(); i++) {
            result += i + ": " + gasPlanets.get(i) + "\n";
        }
        return result;
    }

    public String listAllIcePlanets() {
        if (icePlanets.isEmpty()) {
            return "No Ice Planets";
        }
        String result = "";
        for (int i = 0; i < icePlanets.size(); i++) {
            result += i + ": " + icePlanets.get(i) + "\n";
        }
        return result;
    }

    public String listAllStars() {
        if (stars.isEmpty()) {
            return "No Stars";
        }
        String result = "";
        for (int i = 0; i < stars.size(); i++) {
            result += i + ": " + stars.get(i) + "\n";
        }
        return result;
    }

    public void setIcePlanets(ArrayList<IcePlanet> icePlanets) {
        this.icePlanets = icePlanets;
    }

    public void setStars(ArrayList<Star> stars) {
        this.stars = stars;
    }

    public void addCelestialObject(CelestialBody obj) {
        if (obj instanceof Star) {
            stars.add((Star) obj);
        }
        else if (obj instanceof GasPlanet) {
            gasPlanets.add((GasPlanet) obj);
        }
        else if (obj instanceof IcePlanet) {
            icePlanets.add((IcePlanet) obj);
        }
        else {
            throw new IllegalArgumentException(
                    "Unsupported celestial type: " + obj.getClass().getSimpleName()
            );
        }
    }


    public int isValidId(int id) {
        if (id < 0) {
            return -1;
        }
        return id;
    }

    public String deleteCelestialId(int id) {
        if (id < 0) {
            return "Invalid ID";
        }
        if (id < gasPlanets.size()) {
            gasPlanets.remove(id);
            return "Gas Planet at id " + id + " deleted";
        }
        id -= gasPlanets.size();

        if (id < icePlanets.size()) {
            icePlanets.remove(id);
            return "Ice Planet at id " + id + " deleted";
        }
        id -= icePlanets.size();

        if (id < stars.size()) {
            stars.remove(id);
            return "Star at id " + id + " deleted";
        }

        return "ID not found";
    }

    public String listAllStarsForSpectralType(char type) {
        String result = "";
        for (int i = 0; i < stars.size(); i++) {
            if (stars.get(i).getSpectralType() == type) {
                result += i + ": " + stars.get(i) + "\n";
            }
        }
        return result.isEmpty() ? "No Stars with spectral type " + type : result; // the ? acts as if else
    }

    public boolean listAllCelestialObjectsSmallerThan(double diam) {
        for (Star star : stars) {
            if (star.getDiameter() < diam) return true;
        }
        for (IcePlanet ice : icePlanets) {
            if (ice.getDiameter() < diam) return true;
        }
        for (GasPlanet gas : gasPlanets) {
            if (gas.getDiameter() < diam) return true;
        }
        return false;
    }

    public boolean listAllCelestialObjectsHeavierThan(double w) {
        for (Star star : stars) {
            if (star.getWeight() > w) return true;
        }
        for (IcePlanet ice : icePlanets) {
            if (ice.getWeight() > w) return true;
        }
        for (GasPlanet gas : gasPlanets) {
            if (gas.getWeight() > w) return true;
        }
        return false;
    }

    public boolean listAllCelestialObjectsForGivenPlanetary(PlanetarySystem m) {
        return false;
    }

    public void load() {
    }

    public void save() {
    }

    public boolean listAllCelestialBodies() {
        return false;
    }
}
