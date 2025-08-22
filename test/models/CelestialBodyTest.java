package models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CelestialBodyTest {

    private Star star;
    private Star giantStar;
    private Star tooBigStar, tooSmallStar;

    private PlanetarySystem planetarySystem;

    @BeforeEach
    void setUp() {
        planetarySystem = new PlanetarySystem(
                "Solar System",   // systemName
                "G-Sun",          // orbittingStarName
                4,                // age
                true,             // habitable
                2000,             // discovered
                "G-type"          // systemType
        );

        star = new Star("Sun", 5778, 1.0, "Geothermal", planetarySystem, 'M', 1500);
        giantStar = new Star("Giant6789012345678901234567890", 10000, 50.0, "Geothermal", planetarySystem, 'O', 200000);
        tooBigStar = new Star("1234567890123456789012345678901", 8909, 1.5, "Geothermal", planetarySystem, 'R', 2000000);
        // Updated tooSmallStar to use a valid PlanetarySystem instead of null
        PlanetarySystem smallPlanetarySystem = new PlanetarySystem(
                "Tiny System",   // systemName
                "Tiny-Star",     // orbittingStarName
                1,               // age
                false,           // habitable
                2025,            // discovered
                "M-type"         // systemType
        );
        tooSmallStar = new Star("", 0, 0, "Geothermal", smallPlanetarySystem, 'A', 0);
    }

    @AfterEach
    void tearDown() {
        planetarySystem = null;
        star = null;
        giantStar = null;
        tooBigStar = null;
        tooSmallStar = null;
    }

    @Test
    void testValidStarCreation() {
        assertTrue(star.getId() >= 1);
        assertEquals("Sun", star.getName());
        assertEquals(5778, star.getMass());
        assertEquals(planetarySystem, star.getPlanetarySystem());
        assertEquals(1.0, star.getDiameter());
    }

    @Test
    void testBigValidStarCreation() {
        assertTrue(giantStar.getId() >= 1);
        assertEquals("Giant6789012345678901234567890", giantStar.getName());
        assertEquals(10000, giantStar.getMass());
        assertEquals(planetarySystem, giantStar.getPlanetarySystem());
        assertEquals(50.0, giantStar.getDiameter());
    }

    @Test
    void testInValidStarCreation() {
        assertTrue(tooBigStar.getId() >= 1);
        assertEquals("123456789012345678901234567890", tooBigStar.getName());
        assertEquals(8909, tooBigStar.getMass());
        assertEquals(planetarySystem, tooBigStar.getPlanetarySystem());
        assertEquals(1.5, tooBigStar.getDiameter(), 0.01);

        assertTrue(tooSmallStar.getId() >= 1);
        assertEquals("Unnamed", tooSmallStar.getName());
        assertEquals(0.1, tooSmallStar.getMass(), 0.01);
        assertEquals("Tiny System", tooSmallStar.getPlanetarySystem().getSystemName());
        assertEquals(0.5, tooSmallStar.getDiameter(), 0.01);
    }

    @Test
    void testSetValidMass() {
        star.setMass(1.5);
        assertEquals(1.5, star.getMass(), 0.01);
        star.setMass(15.0);
        assertEquals(15.0, star.getMass(), 0.01);
    }

    @Test
    void testSetInvalidMass() {
        assertEquals(5778, star.getMass(), 0.01);
        star.setMass(-2.0);
        assertEquals(0.1, star.getMass(), 0.01);
        star.setMass(0.0);
        assertEquals(0.1, star.getMass(), 0.01);
    }

    @Test
    void testSetValidDiameter() {
        assertEquals(1.0, star.getDiameter());
        star.setDiameter(1.5);
        assertEquals(1.5, star.getDiameter(), 0.01);
    }

    @Test
    void testSetInvalidDiameter() {
        star.setDiameter(1.0);
        assertEquals(1.0, star.getDiameter());
        star.setDiameter(-2.0);
        assertEquals(.5, star.getDiameter());
        star.setDiameter(0.0);
        assertEquals(.5, star.getDiameter());
    }

    @Test
    void testSetValidName() {
        assertEquals("Sun", star.getName());
        star.setName("12345678901234567890123456789");
        assertEquals("12345678901234567890123456789", star.getName());
        star.setName("123456789012345678901234567890");
        assertEquals("123456789012345678901234567890", star.getName());
    }

    @Test
    void testSetInValidName() {
        star.setName("Sun");
        assertEquals("Sun", star.getName());
        star.setName("123456789012345678901234567890XXX11111");
        assertEquals("123456789012345678901234567890", star.getName());//Char limit should kick in and stop it here
    }

    @Test
    void testSetPlanetarySystem() {
        assertEquals(planetarySystem, star.getPlanetarySystem());
        PlanetarySystem newPlanetarySystem = new PlanetarySystem(
                "New System",   // systemName
                "New-Star",     // orbittingStarName
                2,              // age
                false,          // habitable
                2020,           // discovered
                "K-type"        // systemType
        );
        star.setPlanetarySystem(newPlanetarySystem);
        assertEquals(newPlanetarySystem, star.getPlanetarySystem());
    }
    @Test
    void testToString() {
            String starString = star.toString();;

            assertTrue(starString.contains("Name: "));
            assertTrue(starString.contains("Sun"));
            assertTrue(starString.contains("Mass: "));
            assertTrue(starString.contains("5778"));
            assertTrue(starString.contains("Diameter: "));
            assertTrue(starString.contains("1.0"));
            assertTrue(starString.contains("id: "));

    }
}
