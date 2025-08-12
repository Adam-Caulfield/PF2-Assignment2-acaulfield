package main;

import controllers.CelestialSystemAPI;

import controllers.PlanetarySystemAPI;

import models.*;
import utils.ScannerInput;
import utils.Utilities;

import java.io.File;

public class Driver {


    private CelestialSystemAPI celestialAPI;
    private PlanetarySystemAPI planetarySystemAPI;


    public static void main(String[] args) throws Exception {
        new Driver().start();
    }

    public void start() {

        celestialAPI = new CelestialSystemAPI(new File("celestialSystems.xml"));
        planetarySystemAPI = new PlanetarySystemAPI(new File("planetarySystems.xml"));

        loadAllData();  //load all data once the serializers are set up
        runMainMenu();
    }

    private int mainMenu() {

        System.out.println("""
                 -------Space Place -------------
                |  1) Planetary Systems CRUD MENU|
                |  2) Celestial CRUD MENU        |
                |  3) Reports MENU               |
                |--------------------------------|
                |  4) Search Planetary Systems   |
                |  5) Search Planetary Objects   |  
                |  6) Sort Planetary Objects     | 
                |--------------------------------|
                |  10) Save all                  |
                |  11) Load all                  |
                |--------------------------------|
                |  0) Exit                       |
                 --------------------------------""");
        return ScannerInput.readNextInt("==>> ");
    }

    //// search todo by different criteria i.e. look at the list methods and give options based on that.
// sort todo (and give a list of options - not a recurring menu thou)
    private void runMainMenu() {
        int option = mainMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> runPlanetaryMenu();
                case 2 -> runCelestialAPIMenu();
                case 3 -> runReportsMenu();
                case 4 -> System.out.println(planetarySystemAPI.listPlanetarySystems());
                case 10 -> saveAllData();
                case 11 -> loadAllData();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option = mainMenu();
        }
        exitApp();
    }

    private void exitApp() {
        saveAllData();
        System.out.println("Exiting....");
        System.exit(0);
    }

    //----------------------
    //  Developer Menu Items
    //----------------------
    private int planetarySystemsMenu() {
        System.out.println("""
                 --------Planetary Menu---------
                |  1) Add a planetary systems           |
                |  2) Delete a planetary systems        |
                |  3) Update planetary systems details  |
                |  4) List all planetary systems       |
                |  5) Find a planetary systems          |
                |  0) Return to main menu          |
                 ----------------------------------""");
        return ScannerInput.readNextInt("==>>");
    }

    private void runPlanetaryMenu() {
        int option = planetarySystemsMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> addPlanetary();
                case 2 -> deletePlanetary();
                case 3 -> updatePlanetary();
                case 4 -> System.out.println(planetarySystemAPI.listPlanetarySystems());
                case 5 -> findPlanetary();
                case 6 -> listByPlanetaryName();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option = planetarySystemsMenu();
        }
    }

    private void addPlanetary() {
        String planetarysystemsName = ScannerInput.readNextLine("Please enter the planetary systems name: ");
        String orbittingStar = ScannerInput.readNextLine("Please enter the name of the start that it orbits: ");

        if (planetarySystemAPI.addPLanetSystem(new PlanetarySystem(planetarysystemsName, orbittingStar))) {
            System.out.println("Add successful");
        } else {
            System.out.println("Add not successful");
        }
    }

    private void deletePlanetary() {
        String planetarysystemsName = ScannerInput.readNextLine("Please enter the planetary systems name: ");
        if (planetarySystemAPI.removePlanetarySystemByName(planetarysystemsName) != null) {
            System.out.println("Delete successful");
        } else {
            System.out.println("Delete not successful");
        }
    }

    private void updatePlanetary() {
        PlanetarySystem pSys = getPlanetaryByName();
        if (pSys != null) {
            String name = pSys.getSystemName();

            String orbittingStar = ScannerInput.readNextLine("Please enter the name of the star that it orbits: ");
            if (planetarySystemAPI.updatePlanetarySystem(name, orbittingStar))
                System.out.println("Star name Updated");
            else
                System.out.println("Star Name NOT Updated");
        } else
            System.out.println("Planetary System name is NOT valid");
    }

    private void findPlanetary() {
        PlanetarySystem pSys = getPlanetaryByName();
        if (pSys == null) {
            System.out.println("No such planetary systems exists");
        } else {
            System.out.println(pSys);
        }
    }

    private void listByPlanetaryName() {
        String planetarysystems = ScannerInput.readNextLine("Enter the planetary systems's name:  ");

        System.out.println(planetarySystemAPI.listAllByPlanetarySystemName(planetarysystems));
    }


    //---------------------
    //  App Store Menu
    //---------------------

    private int celestialAPIMenu() {
        System.out.println(""" 
                 -----Celestial Object Menu----- 
                | 1) Add a Celestial Object           |
                | 2) Delete a Celestial Object        |
                | 3) List all Celestial Object       |
                | 4) Update Celestial Object          |
                | 0) Return to main menu         |
                 ----------------------------""");
        return ScannerInput.readNextInt("==>>");
    }

    private void runCelestialAPIMenu() {
        int option = celestialAPIMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> addCelestial();
                case 2 -> deleteCelestial();
                case 3 -> System.out.println(celestialAPI.listAllCelestialBodies());
                case 4 -> System.out.println("todo");
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option = celestialAPIMenu();
        }
    }

    private void deleteCelestial() {
        int id = ScannerInput.readNextInt("Please enter id number to delete: ");
        System.out.println(celestialAPI.listAllCelestialBodies());
        if (celestialAPI.isValidId(id)!=-1) {
            String t = celestialAPI.deleteCelestialId(id);
            if (t != null)
                System.out.println("Sucessful delete : " + t);
            else System.out.println("No Celestial Object was deleted");
        }

    }
    private boolean askYesNo(String prompt) {
        while (true) {
            String input = ScannerInput.readNextLine(prompt + " (Y/N): ").trim().toUpperCase();
            if (input.equals("Y")) return true;
            if (input.equals("N")) return false;
            System.out.println("Invalid input, please enter Y or N.");
        }
    }

    private void addCelestial() {
        int celestialType = ScannerInput.readNextInt("""
        Which type of celestial object do you wish to add? 
        1) Star
        2) Gas Planet
        3) Ice Planet
        ==>> """);

        String systemName = ScannerInput.readNextLine("Enter the name of the planetary system to add this celestial object to: ");
        PlanetarySystemAPI.listPlanetarySystems();
        PlanetarySystem planetarySystem = planetarySystemAPI.getPlanetarySystemByName(systemName);

        if (planetarySystem == null) {
            System.out.println("Planetary system does not exist. Cannot add celestial object.");
            return;
        }

        String name = ScannerInput.readNextLine("Enter name of celestial object (max 30 chars): ");
        double mass = ScannerInput.readNextDouble("Enter mass (in ronnagrams, > 0.1, default 0.1): ");
        if (mass < 0.1) mass = 0.1;

        double diameter = ScannerInput.readNextDouble("Enter diameter (in km, > 0.5, default 0.5): ");
        if (diameter < 0.5) diameter = 0.5;

        switch (celestialType) {
            case 1 -> {
                char spectralType = ScannerInput.readNextChar("Enter spectral type (O, B, A, F, G, K, M): ");
                double luminosity = ScannerInput.readNextDouble("Enter luminosity: ");
                celestialAPI.addCelestialObject(new Star(name, mass, diameter, planetarySystem, spectralType, luminosity));
                System.out.println("Star added successfully.");
            }
            case 2 -> {
                double avgTemp = ScannerInput.readNextDouble("Enter average surface temperature (°C, between -400 and 400): ");
                String surfaceType = ScannerInput.readNextLine("Enter surface type: ");
                boolean hasLiquidWater = askYesNo("Does it have liquid water?");
                String gasComposition = ScannerInput.readNextLine("Enter gas composition (max 20 chars): ");
                String coreComposition = ScannerInput.readNextLine("Enter core composition (max 40 chars): ");
                double radiationLevel = ScannerInput.readNextDouble("Enter radiation level: ");
                celestialAPI.addCelestialObject(new GasPlanet(name, mass, diameter, planetarySystem, avgTemp, surfaceType,
                        hasLiquidWater, gasComposition, coreComposition, radiationLevel));
                System.out.println("Gas Planet added successfully.");
            }
            case 3 -> {
                double avgTemp = ScannerInput.readNextDouble("Enter average surface temperature (°C, between -400 and 400): ");
                String surfaceType = ScannerInput.readNextLine("Enter surface type: ");
                boolean hasLiquidWater = askYesNo("Does it have liquid water?");
                String iceComposition = ScannerInput.readNextLine("Enter ice composition (max 30 chars): ");
                celestialAPI.addCelestialObject(new IcePlanet(name, mass, diameter, planetarySystem, avgTemp,
                        surfaceType, hasLiquidWater, iceComposition));
                System.out.println("Ice Planet added successfully.");
            }
            default -> System.out.println("Invalid celestial object type selected.");
        }
    }


    public void runReportsMenu() {
        int option = reportsMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> runPlanetaryReports();
                case 2 -> runCelestialReportsMenu();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option = reportsMenu();
        }
    }

    private int reportsMenu() {
        System.out.println(""" 
                 --------Reports Menu ---------
                | 1) Planetarys Overview    | 
                | 2) Celestials Overview         |
                | 0) Return to main menu       | 
                  -----------------------------  """);
        return ScannerInput.readNextInt("==>>");
    }

    public void runCelestialReportsMenu() {
        int option = celestialReportsMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> System.out.println(celestialAPI.listAllCelestialBodies());
                case 2 -> System.out.println(celestialAPI.listAllGasPlanets());
                case 3 -> System.out.println(celestialAPI.listAllIcePlanets());
                case 4 -> System.out.println(celestialAPI.listAllStars());
                case 5 -> listAllCelestialHeavierThan();
                case 6 -> listAllCelestialSmallerThan();
                case 7 -> listAllStarsForSpectralType();
                case 8 -> System.out.println(celestialAPI.topFiveHighestRadiationGasPlanet());

                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option = celestialReportsMenu();
        }
    }


    private int celestialReportsMenu() {
        System.out.println(""" 
                 ---------- Celestial Reports Menu  ---------------------
                | 1) List all Celestial Objects                                 | 
                | 2) List all Gas Planet                                 |
                | 3) List all Ice Planets                               |
                | 4) List all Stars                                    |
                | 5) List all objects heavier than                      |
                | 6) List all ojects smaller than                      |
                | 7) List all stars for a spectral type                |
                | 8) List the top five gas planets by radiation levels      |
                | 0) Return to main menu                                 | 
                  ----------------------------------------------------  """);
        return ScannerInput.readNextInt("==>>");
    }

    private int planetarysystemsReportsMenu() {
        System.out.println(""" 
                 ---------- Planetarys Reports Menu  -------------
                | 1) List All Planetarys Systems                              | 
                | 2) List Celestial Objects from a given planetary systems    |
                | 3) List Planetary Systems by a given name              |
                | 0) Return to main menu                             | 
                  ---------------------------------------------------  """);
        return ScannerInput.readNextInt("==>>");
    }

    public void runPlanetaryReports() {
        int option = planetarysystemsReportsMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> System.out.println(planetarySystemAPI.listPlanetarySystems());
                case 2 -> listAllCelestialFromaGivenPlanetary();
                case 3 -> System.out.println("todo");
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option = planetarysystemsReportsMenu();
        }
    }

//todo update methods counting methods

    private void listAllStarsForSpectralType() {
        char type = ScannerInput.readNextChar("Enter Spectral Type: ");
        System.out.println(celestialAPI.listAllStarsForSpectralType(type));
    }

    private void listAllCelestialSmallerThan() {
        double diam = ScannerInput.readNextDouble("Enter diameter of Celestial: ");
        System.out.println(celestialAPI.listAllCelestialObjectsSmallerThan(diam));
    }

    private void listAllCelestialHeavierThan() {
        double w = ScannerInput.readNextDouble("Enter weight of Celestial: ");
        System.out.println(celestialAPI.listAllCelestialObjectsHeavierThan(w));
    }

    public void listAllCelestialFromaGivenPlanetary() {
        String manu = ScannerInput.readNextLine("What planetary systems you want a list of objects for?  : ");
        PlanetarySystem m = planetarySystemAPI.getPlanetarySystemByName(manu);
        if (!(m == null))
            System.out.println(celestialAPI.listAllCelestialObjectsForGivenPlanetary(m));
        else
            System.out.println("No planetary systems with tha name exists");
    }


    //---------------------
    //  General Menu Items
    //---------------------

    private void saveAllData() {
        System.out.println("Storing all data....");
        try {
            celestialAPI.save();
            planetarySystemAPI.save();
        } catch (Exception e) {
            System.err.println("Error writing to file: " + e);
        }
    }

    private void loadAllData() {
        System.out.println("Loading all data....");
        try {
            celestialAPI.load();
            planetarySystemAPI.load();
        } catch (Exception e) {
            System.err.println("Error loading from this file:  " + e);
        }
    }

    //---------------------
    //  Helper Methods
    //---------------------

    private int getValidId() {
        int id = ScannerInput.readNextInt("\tID Number (must be unique): ");
        if (celestialAPI.isValidId(id)!=1) {
            return id;
        } else {
            System.err.println("\tId already exists / is not valid.");
            return -1;
        }
    }

    private PlanetarySystem getPlanetaryByName() {
        String planetarysystemsName = ScannerInput.readNextLine("Please enter the planetary systems's name: ");
        if (planetarySystemAPI.isValidPlanetSys(planetarysystemsName)) {
            return planetarySystemAPI.getPlanetarySystemByName(planetarysystemsName);
        } else {
            return null;
        }
    }


}

