package controllers;

import models.PlanetarySystem;
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class PlanetarySystemAPITest {

    PlanetarySystem planetarySystem1;
    PlanetarySystem planetarySystem2;

    private PlanetarySystemAPI populatedDevices;
    private PlanetarySystemAPI emptyDevices;

    @BeforeEach
    void setUp() {
        planetarySystem1 = new PlanetarySystem(
                "Solar System", "G-Sun", 4, true, 2000, "G-type"
        );
        planetarySystem2 = new PlanetarySystem(
                "Alpha Centauri", "A-Star", 5, false, 1995, "K-type"
        );

        // Use dummy file references; we will populate manually
        populatedDevices = new PlanetarySystemAPI(new File("populated.xml"));
        emptyDevices = new PlanetarySystemAPI(new File("empty.xml"));

        // Clear and populate lists to ensure consistent test data
        populatedDevices.getPlanetarySystems().clear();
        populatedDevices.addPLanetSystem(planetarySystem1);
        populatedDevices.addPLanetSystem(planetarySystem2);

        emptyDevices.getPlanetarySystems().clear();
    }

    @Nested
    class CRUDMethods {
        @Test
        void addNewPlanetarySystemToEmpty() {
            assertEquals(0, emptyDevices.getPlanetarySystems().size());
            emptyDevices.addPLanetSystem(planetarySystem1);
            assertEquals(1, emptyDevices.getPlanetarySystems().size());
        }

        @Test
        void addNewPlanetarySystemToPopulated() {
            assertEquals(2, populatedDevices.getPlanetarySystems().size());
            PlanetarySystem newSystem = new PlanetarySystem(
                    "Kepler-22", "K-Star", 3, false, 2010, "G-type"
            );
            populatedDevices.addPLanetSystem(newSystem);
            assertEquals(3, populatedDevices.getPlanetarySystems().size());
        }

        @Test
        void removePlanetarySystemByObject() {
            assertTrue(populatedDevices.getPlanetarySystems().contains(planetarySystem1));
            assertTrue(populatedDevices.removePlanetarySystem(planetarySystem1));
            assertFalse(populatedDevices.getPlanetarySystems().contains(planetarySystem1));
        }

        @Test
        void removePlanetarySystemByName() {
            PlanetarySystem removed = populatedDevices.removePlanetarySystemByName("Alpha Centauri");
            assertNotNull(removed);
            assertEquals("Alpha Centauri", removed.getSystemName());
        }

        @Test
        void updatePlanetarySystemStarName() {
            boolean updated = populatedDevices.updatePlanetarySystem("Solar System", "New-Sun");
            assertTrue(updated);
            assertEquals("New-Sun", populatedDevices.getPlanetarySystemByName("Solar System").getOrbittingStarName());
        }
    }

    @Nested
    class ListingMethods {

        @Test
        void listAllReturnsMessageWhenEmpty() {
            assertEquals(0, emptyDevices.getPlanetarySystems().size());
            String result = emptyDevices.listPlanetarySystems();
            assertTrue(result.contains("No Planetary Systems"));
        }

        @Test
        void listAllReturnsPlanetarySystemsWhenStored() {
            assertEquals(2, populatedDevices.getPlanetarySystems().size());
            String list = populatedDevices.listPlanetarySystems();
            assertTrue(list.contains("Solar System"));
            assertTrue(list.contains("Alpha Centauri"));
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

        @Test
        void searchObjectsReturnsMessageWhenEmpty() {
            assertEquals(0, emptyDevices.getPlanetarySystems().size());
            String result = emptyDevices.searchObjects();
            assertTrue(result.contains("No Planetary Systems"));
        }

        @Test
        void searchObjectsReturnsSystemsWhenPopulated() {
            assertEquals(2, populatedDevices.getPlanetarySystems().size());
            String result = populatedDevices.searchObjects();
            assertTrue(result.contains("Solar System"));
            assertTrue(result.contains("Alpha Centauri"));
        }
    }
}
