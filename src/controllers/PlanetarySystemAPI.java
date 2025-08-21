package controllers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import models.PlanetarySystem;
import utils.ISerializer;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static utils.Utilities.isValidIndex;


//todo include PlanetarySystem API into starter code
public class PlanetarySystemAPI implements ISerializer {

    private static List<PlanetarySystem> planetarySystemList = new ArrayList<>();

    private File file;

    public PlanetarySystemAPI(File file)  {
        this.file = file;
    }

    public static boolean listAllPlaneterySystems() {
        return planetarySystemList.isEmpty();
    }


    //---------------------
    // Create methods
    //---------------------
    public boolean addPLanetSystem(PlanetarySystem planetarySystem) {
        if (isValidPlanetSys(planetarySystem.getSystemName())){
            return false;
        }
        return planetarySystemList.add(planetarySystem);
    }

    //---------------------
    // Read methods
    //---------------------
    public PlanetarySystem getPlanetarySystemByIndex(int index){
        if (isValidIndex(planetarySystemList, index)){
            return planetarySystemList.get(index);
        }
        else{
            return null;
        }
    }

    public PlanetarySystem getPlanetarySystemByName (String pName){
        int index = retrievePlanetarySystemIndex(pName);
        if (index != -1){
            return planetarySystemList.get(index);
        }
        return null;
    }


    public static String listPlanetarySystems(){
        String listPlanetarySystems = "";
        for (PlanetarySystem planetarySystem : planetarySystemList){
            listPlanetarySystems += planetarySystemList.indexOf(planetarySystem) + ": " + planetarySystem + "\n";
        }
        if (listPlanetarySystems.equals("")){
            return "No Planetary Systems";

        }
        else {
            return listPlanetarySystems;
        }
    }
    public String listAllByPlanetarySystemName(String pName){
        if (!planetarySystemList.isEmpty()) {
            String listPlanetarySystems = "";
            for (PlanetarySystem planetarySystem : planetarySystemList) {
                if (planetarySystem.getSystemName().equalsIgnoreCase(pName))
                    listPlanetarySystems += planetarySystemList.indexOf(planetarySystem) + ": " + planetarySystem + "\n";
            }
            if (listPlanetarySystems.equals("")) {
                return "No Planetary Systems of that name";
            } else {
                return listPlanetarySystems;
            }
        }
        else return "There are no Planetary Systems in the list.";
    }
    //---------------------
    // Update methods
    //---------------------
    public boolean updatePlanetarySystem(String pSysName, String starName){
        if (isValidPlanetSys(pSysName)){
            PlanetarySystem planetarySystemByName = getPlanetarySystemByName(pSysName);
            planetarySystemByName.setOrbittingStarName(starName);
            return true;
        }
        return false;
    }

    //---------------------
    // Delete methods
    //---------------------
    public boolean removePlanetarySystem(PlanetarySystem planetarySystem){
        if (planetarySystemList.contains(planetarySystem)) {
            return planetarySystemList.remove(planetarySystem);
        }
        return false;
    }

    public PlanetarySystem removePlanetarySystemByName(String planetarySystemName){
        int index = retrievePlanetarySystemIndex(planetarySystemName);
        if (index != -1) {
            return planetarySystemList.remove(index);
        }
        return null;
    }

    //---------------------
    // Validation Methods
    //---------------------
    public boolean isValidPlanetSys(String planSysName){
        for (PlanetarySystem planetarySystem : planetarySystemList){
            if (planetarySystem.getSystemName().equalsIgnoreCase(planSysName)){
                return true;
            }
        }
        return false;
    }

    public int retrievePlanetarySystemIndex(String planetarySystemName){
        for (PlanetarySystem planetarySystem : planetarySystemList){
            if (planetarySystem.getSystemName().equalsIgnoreCase(planetarySystemName)){
                return planetarySystemList.indexOf(planetarySystem);
            }
        }
        return -1;
    }

    //---------------------
    // Getters/Setters
    //---------------------

    public List<PlanetarySystem> getPlanetarySystems() {
        return planetarySystemList;
    }



    @Override
    public String fileName() {
        return String.valueOf(file);
    }

    public void save() throws Exception {
        var xstream = new XStream(new DomDriver());
        ObjectOutputStream os = xstream.createObjectOutputStream(new FileWriter(file));
        os.writeObject(planetarySystemList);
        os.close();
    }


    public void load() throws Exception {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]{ PlanetarySystem.class};

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(file));
        planetarySystemList = (List<PlanetarySystem>) in.readObject();
        in.close();
    }




    public String searchObjects() {
        if (planetarySystemList.isEmpty()) {
            return "No Planetary Systems";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < planetarySystemList.size(); i++) {
            sb.append(i).append(": ").append(planetarySystemList.get(i)).append("\n");
        }
        return sb.toString();
    }
}
