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




    private PlanetarySystemAPI populatedDevices = new PlanetarySystemAPI(new File("planetarySystemsTest.xml"));
    private PlanetarySystemAPI emptyDevices = new PlanetarySystemAPI(new File("planetarySystems.xml"));

    @BeforeEach
    void setUp() {
        planetarySystem1 =new PlanetarySystem(
                "Solar System",   // systemName
                "G-Sun",          // orbittingStarName
                4,                // age
                true,             // habitable
                2000,             // discovered
                "G-type"          // systemType
        );
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
            assertEquals(4, emptyDevices.getPlanetarySystems().size());
            emptyDevices.addPLanetSystem(planetarySystem1);
            assertEquals(5, emptyDevices.getPlanetarySystems().size());


        }
        @Test
        void addNewPlanetarty() {
            assertEquals(4, populatedDevices.getPlanetarySystems().size());
            populatedDevices.addPLanetSystem(planetarySystem1);
            assertEquals(5, populatedDevices.getPlanetarySystems().size());

        }
    }

    @Nested
    class ListingMethods {

        @Test
        void listAllReturnsMessageWhenNoPlanetarySystemsStored() {
            assertEquals(4, emptyDevices.getPlanetarySystems().size());
            assertTrue(PlanetarySystemAPI.listPlanetarySystems().contains("No Planetary Systems"));
        }

        @Test
        void listAllReturnsPlanetarySystemsWhenStored() {
            assertEquals(4, populatedDevices.getPlanetarySystems().size());
            String populatedDeviceStr = PlanetarySystemAPI.listPlanetarySystems();
            //checks for objects in the string
            assertTrue(populatedDeviceStr.contains("Solar System"));
            assertTrue(populatedDeviceStr.contains("GE345"));
            assertTrue(populatedDeviceStr.contains("Generic_234"));
            assertTrue(populatedDeviceStr.contains("orbits around: SUN"));
        }

        @Test
        void listByNameReturnsMessageWhenNoneExist() {
            assertEquals(4, populatedDevices.getPlanetarySystems().size());
            String populatedDeviceStr = populatedDevices.listAllByPlanetarySystemName("Solar Doesnt exist");
            assertTrue(populatedDeviceStr.contains("No Planetary Systems of that name"));
        }
    }

    @Nested
    class ReportingMethods {

    }

    @Nested
    class SearchingMethods {

    }



}