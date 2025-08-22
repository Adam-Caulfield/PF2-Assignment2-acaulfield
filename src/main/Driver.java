package main;

import controllers.CelestialSystemAPI;

import controllers.PlanetarySystemAPI;

import models.*;
import utils.ScannerInput;

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

        loadAllData();  //load data on start
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
                case 5 -> System.out.println(planetarySystemAPI.searchObjects());
                case 6 -> sortCelestialObjects();
                case 10 -> saveAllData();
                case 11 -> loadAllData();
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option = mainMenu();
        }
        exitApp();
    }




    private void sortCelestialObjects() {
        System.out.println("""
            Sort celestial objects by:
            1) Mass (Descending)
            2) Diameter (Ascending)
        """);
        int choice = ScannerInput.readNextInt("Enter choice: ");
        switch (choice) {
            case 1 -> {
                celestialAPI.sortByMassDescending();
                System.out.println(celestialAPI.listAllCelestialBodies());
            }
            case 2 -> {
                celestialAPI.sortByDiameterAscending();
                System.out.println(celestialAPI.listAllCelestialBodies());
            }
            default -> System.out.println("Invalid sort option.");
        }
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
        String orbittingStar = ScannerInput.readNextLine("Please enter the name of the start that it orbits: ");
        String planetarysystemsName = ScannerInput.readNextLine("Please enter the planetary systems name: ");
        int Age = ScannerInput.readNextInt("Enter approximate age (in billions of years): ");
        boolean habitable = askYesNo("Habitable?: ");
        int Discovered;
        while (true) {
            Discovered = ScannerInput.readNextInt("Enter the year the planetary system was Discovered: ");
            if (Discovered >= 1900 && Discovered <= 2025) break;
            System.out.println("Invalid year. Please enter a value between 1900 and 2025.");
        }
        String systemType = ScannerInput.readNextLine("Please enter the system type such as Single Star Binary or multi-Star.");
        if (planetarySystemAPI.addPLanetSystem(
                new PlanetarySystem(planetarysystemsName, orbittingStar, Age, habitable, Discovered,systemType)
        )) {
            System.out.println("Changes saved");
            saveAllData();
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

            int newAge = ScannerInput.readNextInt("Enter new age (leave 0 to keep current): ");
            if (newAge > 0) {
                pSys.setAge(newAge);
                System.out.println("Age updated.");
            }

            int newDiscovered;
            while (true) {
                newDiscovered = ScannerInput.readNextInt("Enter new discovered year (1900-2025, 0 to keep current): ");
                if (newDiscovered == 0 || (newDiscovered >= 1900 && newDiscovered <= 2025)) break;
                System.out.println("Invalid year. Please enter between 1900-2025 or 0 to skip.");
            }
            if (newDiscovered != 0) {
                pSys.setDiscovered(newDiscovered);
                System.out.println("Discovered year updated.");
            }
        } else {
            System.out.println("Planetary System name is NOT valid");
        }
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
                case 1 -> {
                    System.out.println(planetarySystemAPI.listPlanetarySystems());

                    addCelestial();
                }
                case 2 -> deleteCelestial();
                case 3 -> System.out.println(celestialAPI.listAllCelestialBodies());
                case 4 -> {
                    System.out.println("which would you like to update");
                   updateCelestial();
                }
                default -> System.out.println("Invalid option entered" + option);
            }
            ScannerInput.readNextLine("\n Press the enter key to continue");
            option = celestialAPIMenu();
        }
    }

    private void updateCelestial() {
        listCelestialId();
        int id = ScannerInput.readNextInt("Enter the ID of the celestial object to update: ");

        CelestialBody obj = celestialAPI.getbyIndex(id);

        if (obj == null) {
            System.out.println("Invalid ID entered.");
            return;
        }

        System.out.println("Leave fields blank or enter -1 to keep current values.");

        // Common fields
        String newName = ScannerInput.readNextLine("Enter new name (leave blank to keep unchanged): ");
        double newMass = ScannerInput.readNextDouble("Enter new mass (>0.1, -1 to keep unchanged): ");
        double newDiameter = ScannerInput.readNextDouble("Enter new diameter (>0.5, -1 to keep unchanged): ");
        String newEnergySource = askEnergySource();

        if (!newName.isBlank()) obj.setName(newName);
        if (newMass >= 0.1) obj.setMass(newMass);
        if (newDiameter >= 0.5) obj.setDiameter(newDiameter);
        obj.setEnergySource(newEnergySource);

        // Type-specific updates
        if (obj instanceof Star star) {
            String spectralInput = ScannerInput.readNextLine("Enter new spectral type (O, B, A, F, G, K, M) or leave blank: ").toUpperCase();
            if (!spectralInput.isBlank() && "OBAFGKM".contains(spectralInput)) {
                star.setSpectralType(spectralInput.charAt(0));
            }

            double newLuminosity = ScannerInput.readNextDouble("Enter new luminosity (-1 to keep current): ");
            if (newLuminosity >= 0) star.setLuminosity(newLuminosity);

        } else if (obj instanceof GasPlanet gas) {
            double newTemp = ScannerInput.readNextDouble("Enter new average surface temperature (-999 to keep current): ");
            if (newTemp != -999) gas.setAverageTemperature(newTemp);

            String newSurface = ScannerInput.readNextLine("Enter new surface type or leave blank: ");
            if (!newSurface.isBlank()) gas.setSurfaceType(newSurface);

            boolean hasWater = askYesNo("Does it have liquid water? (Y/N): ");
            gas.setHasLiquidWater(hasWater);

            String newGasComp = ScannerInput.readNextLine("Enter new gas composition or leave blank: ");
            if (!newGasComp.isBlank()) gas.setGasComposition(newGasComp);

            String newCoreComp = ScannerInput.readNextLine("Enter new core composition or leave blank: ");
            if (!newCoreComp.isBlank()) gas.setCoreComposition(newCoreComp);

            double newRadiation = ScannerInput.readNextDouble("Enter new radiation level (-1 to keep current): ");
            if (newRadiation >= 0) gas.setRadiationLevel(newRadiation);

        } else if (obj instanceof IcePlanet ice) {
            double newTemp = ScannerInput.readNextDouble("Enter new average surface temperature (-999 to keep current): ");
            if (newTemp != -999) ice.setAverageTemperature(newTemp);

            String newSurface = ScannerInput.readNextLine("Enter new surface type or leave blank: ");
            if (!newSurface.isBlank()) ice.setSurfaceType(newSurface);

            boolean hasWater = askYesNo("Does it have liquid water? (Y/N): ");
            ice.setHasLiquidWater(hasWater);

            String newIceComp = ScannerInput.readNextLine("Enter new ice composition or leave blank: ");
            if (!newIceComp.isBlank()) ice.setIceComposition(newIceComp);
        }

        System.out.println("Update successful.");
    }


    private void deleteCelestial() {
        listCelestialId();
        int id = ScannerInput.readNextInt("Please enter id number to delete: ");
        if (celestialAPI.isValidId(id) != -1) {
            String deleted = celestialAPI.deleteCelestialId(id);
            if (deleted != null) {
                System.out.println("Successful delete: " + deleted);
            } else {
                System.out.println("No Celestial Object was deleted.");
            }
        } else {
            System.out.println("Invalid ID entered.");
        }
    }

   private void listCelestialId() {
        if (celestialAPI.allCelestialBodies.isEmpty()) {
            System.out.println("No celestial objects found.");
            return;

        }

        for (int i = 0; i < celestialAPI.allCelestialBodies.size(); i++) {
            System.out.println("ID: " + i);
            celestialAPI.allCelestialBodies.get(i).displayInfo();
            System.out.println();
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

    private String askEnergySource() {
        System.out.println("""
        Select Energy Source:
        1) Nuclear Fusion
        2) Geothermal
        3) Solar Powered
        4) Tidal Heating
    """);

        while (true) {
            int choice = ScannerInput.readNextInt("Enter choice (1-4): ");

            switch (choice) {
                case 1: return "Nuclear Fusion";
                case 2: return "Geothermal";
                case 3: return "Solar Powered";
                case 4: return "Tidal Heating";
                default: System.out.println("Invalid choice. Please select 1-4.");
            }
        }
    }

    private PlanetarySystem askPlanetarySystem() {
        System.out.println(planetarySystemAPI.listPlanetarySystems());
        int index = ScannerInput.readNextInt("Enter the index of the planetary system to add this celestial object to: ");
        PlanetarySystem planetarySystem = planetarySystemAPI.getPlanetarySystemByIndex(index);
        System.out.println("you selected : "+planetarySystem);
        if (planetarySystem == null) {
            System.out.println("Planetary system does not exist. Cannot add celestial object.");
            return planetarySystem;
        }
        return planetarySystem;
    }

    private void addCelestial() {
        PlanetarySystem planetarySystem = askPlanetarySystem(); // get the correct system
        if (planetarySystem == null) {
            System.out.println("Cannot add celestial object. No valid planetary system selected.");
            return;
        }

        int celestialType = ScannerInput.readNextInt("""
    Which type of celestial object do you wish to add? 
    1) Star
    2) Gas Planet
    3) Ice Planet
    ==>> """);

        String name = ScannerInput.readNextLine("Enter name of celestial object (max 30 chars): ");
        double mass = ScannerInput.readNextDouble("Enter mass (in ronnagrams, > 0.1, default 0.1): ");
        if (mass < 0.1) mass = 0.1;

        double diameter = ScannerInput.readNextDouble("Enter diameter (in km, > 0.5, default 0.5): ");
        if (diameter < 0.5) diameter = 0.5;

        String energySource = askEnergySource();

        switch (celestialType) {
            case 1 -> {
                char spectralType;
                while (true) {
                    spectralType = ScannerInput.readNextChar("Enter spectral type (O, B, A, F, G, K, M): ");
                    spectralType = Character.toUpperCase(spectralType); // ensure uppercase

                    if (spectralType == 'O' || spectralType == 'B' || spectralType == 'A' ||
                            spectralType == 'F' || spectralType == 'G' || spectralType == 'K' ||
                            spectralType == 'M') {
                        break; // needs valid input to break the loop
                    } else {
                        System.out.println("Invalid spectral type. Please enter one of: O, B, A, F, G, K, M");
                    }
                }

                System.out.println("You selected spectral type: " + spectralType);
                double luminosity = ScannerInput.readNextDouble("Enter luminosity (double to represent light): ");
                Star star = new Star(name, mass, diameter, energySource, planetarySystem, spectralType, luminosity);
                celestialAPI.addCelestialObject(star);
            }
            case 2 -> {
                double avgTemp = ScannerInput.readNextDouble("Enter average surface temperature (°C, between -400 and 400): ");
                String surfaceType = ScannerInput.readNextLine("Enter surface type: ");
                boolean hasLiquidWater = askYesNo("Does it have liquid water?");
                String gasComposition = ScannerInput.readNextLine("Enter gas composition (max 20 chars): ");
                String coreComposition = ScannerInput.readNextLine("Enter core composition (max 40 chars): ");
                double radiationLevel = ScannerInput.readNextDouble("Enter radiation level: ");
                CelestialSystemAPI.addGasPlannet(name, mass, diameter, energySource, planetarySystem,
                        avgTemp, surfaceType, hasLiquidWater, gasComposition, coreComposition, radiationLevel);
            }
            case 3 -> { // Ice Planet
                double avgTemp = ScannerInput.readNextDouble("Enter average surface temperature (°C, between -400 and 400): ");
                String surfaceType = ScannerInput.readNextLine("Enter surface type: ");
                boolean hasLiquidWater = askYesNo("Does it have liquid water?");
                String iceComposition = ScannerInput.readNextLine("Enter ice composition (max 30 chars): ");
                celestialAPI.addCelestialObject(new IcePlanet(name, mass, diameter, energySource, planetarySystem,
                        avgTemp, surfaceType, hasLiquidWater, iceComposition));
            }
            default -> System.out.println("Invalid celestial object type selected.");
        }

        System.out.println("Celestial object added successfully.");
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
                case 3 ->  planetarySystemAPI.listPlanetarySystems();
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
        PlanetarySystem selectedSystem = askPlanetarySystem();

        if (selectedSystem != null) {
            System.out.println("\nCelestial objects associated with " + selectedSystem.getSystemName() + ":");

            // Filter objects belonging only to this planetary system
            for (CelestialBody obj : celestialAPI.allCelestialBodies) {
                if (obj.getPlanetarySystem() != null &&
                        obj.getPlanetarySystem().getSystemName().equals(selectedSystem.getSystemName())) {

                    System.out.println( obj.displayInfo());
                }
            }
        } else {
            System.out.println("No planetary system selected.");
        }
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



    private PlanetarySystem getPlanetaryByName() {
        String planetarysystemsName = ScannerInput.readNextLine("Please enter the planetary systems's name: ");
        if (planetarySystemAPI.isValidPlanetSys(planetarysystemsName)) {
            return planetarySystemAPI.getPlanetarySystemByName(planetarysystemsName);
        } else {
            return null;
        }
    }


}

