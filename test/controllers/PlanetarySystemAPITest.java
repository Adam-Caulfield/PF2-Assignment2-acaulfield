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
    private PlanetarySystemAPI emptyDevices = new PlanetarySystemAPI(new File("planetary.xml"));

    @BeforeEach
    void setUp() {
        planetarySystem1 = new PlanetarySystem("Galaxy Far, far away" , "something bright" );
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
            assertEquals(4, populatedDevices.getPlanetarySystems().size());
            populatedDevices.addPLanetSystem(planetarySystem1);
            assertEquals(5, populatedDevices.getPlanetarySystems().size());

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
            assertEquals(4, populatedDevices.getPlanetarySystems().size());
            String populatedDeviceStr = populatedDevices.listPlanetarySystems();
            //checks for objects in the string
            assertTrue(populatedDeviceStr.contains("Solar System"));
            assertTrue(populatedDeviceStr.contains("GE345"));
            assertTrue(populatedDeviceStr.contains("Generic_234"));
            assertTrue(populatedDeviceStr.contains("orbits around: SUN"));


        }

        @Test
        void listByNameWhereNoneExist() {
            assertEquals(4, populatedDevices.getPlanetarySystems().size());
            String populatedDeviceStr = populatedDevices.listAllByPlanetarySystemName("Solar Doesnt exist");
            assertTrue(populatedDeviceStr.contains("No Planetary Systems"));
        }


    }

    @Nested
    class ReportingMethods {

    }

    @Nested
    class SearchingMethods {

    }



}