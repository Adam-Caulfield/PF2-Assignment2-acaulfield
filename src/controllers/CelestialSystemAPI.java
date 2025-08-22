package controllers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class CelestialSystemAPI {
    public static ArrayList<CelestialBody> allCelestialBodies = new ArrayList<>();
    private static ArrayList<Star> stars = new ArrayList<>();
    private static ArrayList<IcePlanet> icePlanets = new ArrayList<>();
    private static ArrayList<GasPlanet> gasPlanets = new ArrayList<>();

    public CelestialSystemAPI(File file) {}

    public static int getValidId() {
        int id = allCelestialBodies.size() + 1;
        if (idExists(id)) id++;
        return id;
    }

    private static boolean idExists(int id) {
        for (CelestialBody body : allCelestialBodies) {
            if (body.getId() == id) return true;
        }
        return false;
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
        if (gasPlanets.isEmpty()) return "No Gas Planets";
        String result = "";
        for (int i = 0; i < gasPlanets.size(); i++) result += i + ": " + gasPlanets.get(i) + "\n";
        return result;
    }

    public String listAllIcePlanets() {
        if (icePlanets.isEmpty()) return "No Ice Planets";
        String result = "";
        for (int i = 0; i < icePlanets.size(); i++) result += i + ": " + icePlanets.get(i) + "\n";
        return result;
    }

    public String listAllStars() {
        if (stars.isEmpty()) return "No Stars";
        String result = "";
        for (int i = 0; i < stars.size(); i++) result += i + ": " + stars.get(i) + "\n";
        return result;
    }

    public void setIcePlanets(ArrayList<IcePlanet> icePlanets) { this.icePlanets = icePlanets; }
    public void setStars(ArrayList<Star> stars) { this.stars = stars; }

    public void addCelestialObject(CelestialBody obj) {
        if (obj instanceof Star) addStar((Star) obj);
        else if (obj instanceof GasPlanet) gasPlanets.add((GasPlanet) obj);
        else if (obj instanceof IcePlanet) icePlanets.add((IcePlanet) obj);
        else throw new IllegalArgumentException("Unsupported celestial type: " + obj.getClass().getSimpleName());
        allCelestialBodies.add(obj);
    }

    public static boolean addStar(Star obj) {
        if (obj.getPlanetarySystem() == null) return false;
        stars.add(obj);
        allCelestialBodies.add(obj);
        return true;
    }

    public static boolean addGasPlannet(String name, double mass, double diameter,
                                        String energySource, PlanetarySystem system,
                                        double avgTemp, String surfaceType,
                                        boolean hasLiquidWater, String gasComp, String coreComp,
                                        double radiationLevel) {
        if (system == null) return false;
        GasPlanet planet = new GasPlanet(name, mass, diameter, energySource, system,
                avgTemp, surfaceType, hasLiquidWater, gasComp, coreComp, radiationLevel);
        gasPlanets.add(planet);
        allCelestialBodies.add(planet);
        return true;
    }

    public static boolean addIcePlanet(String name, double mass, double diameter,
                                       String energySource, PlanetarySystem system,
                                       double avgTemp, String surfaceType,
                                       boolean hasLiquidWater, String coreComp) {
        if (system == null) return false;
        IcePlanet planet = new IcePlanet(name, mass, diameter, energySource, system,
                avgTemp, surfaceType, hasLiquidWater, coreComp);
        icePlanets.add(planet);
        allCelestialBodies.add(planet);
        return true;
    }

    public String listAllCelestialObjectsForGivenPlanetary(PlanetarySystem system) {
        if (system == null) return "Invalid Planetary System";
        StringBuilder sb = new StringBuilder();
        for (CelestialBody obj : allCelestialBodies) {
            if (obj.getPlanetarySystem().equals(system)) sb.append(obj).append("\n");
        }
        return sb.length() == 0 ? "No Celestial Bodies for this system" : sb.toString();
    }

    public Star getStarByIndex(int index) { if (index >= 0 && index < stars.size()) return stars.get(index); return null; }
    public GasPlanet getGasPlanetByIndex(int index) { if (index >= 0 && index < gasPlanets.size()) return gasPlanets.get(index); return null; }
    public IcePlanet getIcePlanetByIndex(int index) { if (index >= 0 && index < icePlanets.size()) return icePlanets.get(index); return null; }
    public Star getStarByName(String name) { for (Star s : stars) if (s.getName().equalsIgnoreCase(name)) return s; return null; }
    public GasPlanet getGasPlanetByName(String name) { for (GasPlanet g : gasPlanets) if (g.getName().equalsIgnoreCase(name)) return g; return null; }
    public IcePlanet getIcePlanetByName(String name) { for (IcePlanet i : icePlanets) if (i.getName().equalsIgnoreCase(name)) return i; return null; }

    public boolean updateStar(String oldName, Star updatedStar) {
        for (int i = 0; i < stars.size(); i++) {
            if (stars.get(i).getName().equalsIgnoreCase(oldName)) {
                stars.set(i, updatedStar);
                allCelestialBodies.set(allCelestialBodies.indexOf(stars.get(i)), updatedStar);
                return true;
            }
        }
        return false;
    }

    public boolean updateGasPlanet(String oldName, GasPlanet updatedPlanet) {
        for (int i = 0; i < gasPlanets.size(); i++) {
            if (gasPlanets.get(i).getName().equalsIgnoreCase(oldName)) {
                gasPlanets.set(i, updatedPlanet);
                allCelestialBodies.set(allCelestialBodies.indexOf(gasPlanets.get(i)), updatedPlanet);
                return true;
            }
        }
        return false;
    }

    public boolean updateIcePlanet(String oldName, IcePlanet updatedPlanet) {
        for (int i = 0; i < icePlanets.size(); i++) {
            if (icePlanets.get(i).getName().equalsIgnoreCase(oldName)) {
                icePlanets.set(i, updatedPlanet);
                allCelestialBodies.set(allCelestialBodies.indexOf(icePlanets.get(i)), updatedPlanet);
                return true;
            }
        }
        return false;
    }

    public boolean removeStarByName(String name) {
        for (Star s : stars)
            if (s.getName().equalsIgnoreCase(name)) {
                allCelestialBodies.remove(s);
                return stars.remove(s);
            }
        return false;
    }

    public boolean removeGasPlanetByName(String name) {
        for (GasPlanet g : gasPlanets)
            if (g.getName().equalsIgnoreCase(name)) {
                allCelestialBodies.remove(g);
                return gasPlanets.remove(g);
            }
        return false;
    }

    public boolean removeIcePlanetByName(String name) {
        for (IcePlanet i : icePlanets)
            if (i.getName().equalsIgnoreCase(name)) {
                allCelestialBodies.remove(i);
                return icePlanets.remove(i);
            }
        return false;
    }

    public boolean removeStarByIndex(int index) {
        if (index >= 0 && index < stars.size()) {
            allCelestialBodies.remove(stars.get(index));
            stars.remove(index);
            return true;
        }
        return false;
    }

    public boolean removeGasPlanetByIndex(int index) {
        if (index >= 0 && index < gasPlanets.size()) {
            allCelestialBodies.remove(gasPlanets.get(index));
            gasPlanets.remove(index);
            return true;
        }
        return false;
    }

    public boolean removeIcePlanetByIndex(int index) {
        if (index >= 0 && index < icePlanets.size()) {
            allCelestialBodies.remove(icePlanets.get(index));
            icePlanets.remove(index);
            return true;
        }
        return false;
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
            if (stars.get(i).getSpectralType() == type) result += i + ": " + stars.get(i) + "\n";
        }
        return result.isEmpty() ? "No Stars with spectral type " + type : result;
    }

    public boolean listAllCelestialObjectsSmallerThan(double diam) {
        for (CelestialBody obj : allCelestialBodies) if (obj.getDiameter() < diam) return true;
        return false;
    }

    public boolean listAllCelestialObjectsHeavierThan(double w) {
        for (CelestialBody obj : allCelestialBodies) if (obj.getWeight() > w) return true;
        return false;
    }


    public void load() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);

        // Solution to allow classes seen in shop V8
        xstream.allowTypes(new Class[]{
                ArrayList.class,
                CelestialBody.class,
                Star.class,
                GasPlanet.class,
                IcePlanet.class,
                PlanetarySystem.class
        });

        ObjectInputStream is = xstream.createObjectInputStream(new FileReader("CelestialObjects.xml"));
        allCelestialBodies = (ArrayList<CelestialBody>) is.readObject();
        is.close();

        stars.clear();
        gasPlanets.clear();
        icePlanets.clear();

        for (CelestialBody obj : allCelestialBodies) {
            if (obj instanceof Star) stars.add((Star) obj);
            else if (obj instanceof GasPlanet) gasPlanets.add((GasPlanet) obj);
            else if (obj instanceof IcePlanet) icePlanets.add((IcePlanet) obj);
        }
    }


    public void save() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(new Class[]{ArrayList.class, Star.class, GasPlanet.class, IcePlanet.class});
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("CelestialObjects.xml"));
        out.writeObject(allCelestialBodies);
        out.close();
    }

    public String listAllCelestialBodies() { if (allCelestialBodies.isEmpty()) return "No Celestial Bodies"; String result = ""; for (int i = 0; i < allCelestialBodies.size(); i++) result += i + ": " + allCelestialBodies.get(i) + "\n"; return result; }

    public void sortByMassDescending() {
        for (int i = allCelestialBodies.size() - 1; i >= 0; i--) {
            int highestIndex = 0;
            for (int j = 0; j <= i; j++) {
                if (allCelestialBodies.get(j).getWeight() > allCelestialBodies.get(highestIndex).getWeight()) {
                    highestIndex = j;
                }
            }
            swapProducts(allCelestialBodies, i, highestIndex);
        }
    }

    private void swapProducts(ArrayList<CelestialBody> list, int i, int j) {
        CelestialBody temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    public void sortByDiameterAscending() {
        for (int i = allCelestialBodies.size() - 1; i >= 0; i--) {
            int smallestIndex = 0;
            for (int j = 0; j <= i; j++) {
                if (allCelestialBodies.get(j).getDiameter() < allCelestialBodies.get(smallestIndex).getDiameter()) {
                    smallestIndex = j;
                }
            }
            swapProducts(allCelestialBodies, i, smallestIndex);
        }
    }

    public boolean updateCelestialObject(int id, String newName, double newMass, double newDiameter, String energySource2) {


        CelestialBody obj = allCelestialBodies.get(id);
        obj.setName(newName);
        obj.setMass(newMass);
        obj.setDiameter(newDiameter);
        obj.setEnergySource(energySource2);
        if (obj instanceof Star) {
            int index = stars.indexOf(obj);
            if (index >= 0) stars.set(index, (Star) obj);
        } else if (obj instanceof GasPlanet) {
            int index = gasPlanets.indexOf(obj);
            if (index >= 0) gasPlanets.set(index, (GasPlanet) obj);
        } else if (obj instanceof IcePlanet) {
            int index = icePlanets.indexOf(obj);
            if (index >= 0) icePlanets.set(index, (IcePlanet) obj);
        }

        return true;
    }

    public CelestialBody getbyIndex(int id) {
        if (id >= 0 && id < allCelestialBodies.size()) {
            return allCelestialBodies.get(id);
        } else {
            return null;
        }
    }
    public static void clearFile() {
        try {
            // Clear all lists
            allCelestialBodies.clear();
            stars.clear();
            gasPlanets.clear();
            icePlanets.clear();

            // Overwrite the file with an empty list
            XStream xstream = new XStream(new DomDriver());
            XStream.setupDefaultSecurity(xstream);
            xstream.allowTypes(new Class[]{ArrayList.class, CelestialBody.class, Star.class, GasPlanet.class, IcePlanet.class, PlanetarySystem.class});

            ObjectOutputStream os = xstream.createObjectOutputStream(new FileWriter("CelestialObjects.xml"));
            os.writeObject(allCelestialBodies); // write empty list
            os.close();

            System.out.println("All celestial bodies cleared and file emptied.");
        } catch (Exception e) {
            System.err.println("Error clearing file: " + e.getMessage());
        }
    }


    public static ArrayList<CelestialBody> getAllCelestials() {
        return allCelestialBodies;
    }


    public int numberOfPlanetarySystems() {
        return allCelestialBodies.size();
    }
}
