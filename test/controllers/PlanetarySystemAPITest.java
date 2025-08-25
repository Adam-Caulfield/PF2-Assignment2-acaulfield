package controllers;

import java.io.File;


import models.PlanetarySystem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
class PlanetarySystemAPITest {

    PlanetarySystem planetarySystem1,planetarySystemEmpty, planetarySystemNull;




    private PlanetarySystemAPI populatedDevices = new PlanetarySystemAPI(new File("populatedTest.xml"));
    private PlanetarySystemAPI emptyDevices = new PlanetarySystemAPI(new File("emptyTest.xml"));

    @BeforeEach
    void setUp() {


            planetarySystem1 = new PlanetarySystem(
                    "Galaxy Far, far away", "something bright", 5, false, 1, "Andromeda"
            );
            PlanetarySystem ps2 = new PlanetarySystem("Solar System", "G-type", 8, true, 4, "Milky Way");

            populatedDevices.getPlanetarySystems().clear();
            populatedDevices.addPLanetSystem(ps2);


            emptyDevices.getPlanetarySystems().clear();

        try {
            populatedDevices.load();
            emptyDevices.load();
        } catch (Exception e){
            System.out.println(e);
        }

    }



    @Nested
    class CRUDMethods {
        @Test
        void addNewPlanetartyDevicetoEmpty() {
            assertEquals(0, emptyDevices.getPlanetarySystems().size());
            emptyDevices.addPLanetSystem(planetarySystem1);
            assertEquals(1, emptyDevices.getPlanetarySystems().size());


        }
        @Test
        void addNewPlanetarty() {
            assertEquals(0, populatedDevices.getPlanetarySystems().size());
            populatedDevices.addPLanetSystem(planetarySystem1);
            assertEquals(1, populatedDevices.getPlanetarySystems().size());

        }
    }

    @Nested
    class ListingMethods {

        @Test
        void listAllReturnsNoPlanetartyStoredWhenArrayListIsEmpty() {
            assertEquals(0, emptyDevices.getPlanetarySystems().size());
            assertTrue(emptyDevices.listPlanetarySystems().toLowerCase().contains(" planetary systems"));
        }

        @Test
        void listAllReturnsPlanetartyDevicesStoredWhenArrayListHasPlanetartyDevicesStored() {
            assertEquals(0, populatedDevices.getPlanetarySystems().size());
            String populatedDeviceStr = populatedDevices.listPlanetarySystems();
            //checks for objects in the string
            assertTrue(populatedDeviceStr.contains("Solar System"));
            assertTrue(populatedDeviceStr.contains("GE345"));
            assertTrue(populatedDeviceStr.contains("Generic_234"));
            assertTrue(populatedDeviceStr.contains("orbits around: SUN"));


        }

        @Test
        void listByNameReturnsMessageWhenNoneExist() {
            String result = populatedDevices.listAllByPlanetarySystemName("Non-Existent");
            assertTrue(result.contains("No Planetary Systems of that name"));
        }

        @Test
        void listByNameReturnsSystemWhenExists() {
            String result = populatedDevices.listAllByPlanetarySystemName("Solar System");
            assertTrue(result.contains("Solar System"));
        }
    }

    @Nested
    class SearchingMethods {

    }



}