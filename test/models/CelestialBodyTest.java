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
            planetarySystem = new PlanetarySystem("G-type", "G-Sun");
            star = new Star("Sun", 5778, 1.0, planetarySystem,'M', 1500);
            giantStar = new Star("Giant6789012345678901234567890", 10000, 50.0, planetarySystem,'O', 200000);
            tooBigStar = new Star("1234567890123456789012345678901", 8909, 1.5,planetarySystem,'R', 2000000 );
            tooSmallStar = new Star("", 0, 0,null,'A', 0 );

        }
        @AfterEach
        void tearDown() {
            planetarySystem = null;
            star = null;
            giantStar = null;
            tooBigStar = null;
            tooSmallStar = null;
            planetarySystem = null;
        }
//String name, double mass, double diameter, PlanetarySystem planetarySystem
        @Test
        void testValidStarCreation() {
            assertTrue(star.getId() >=1 );
            assertEquals("Sun", star.getName());
            assertEquals(5778, star.getMass());
            assertEquals(planetarySystem, star.getPlanetarySystem());
            assertEquals(1.0, star.getDiameter());

        }
    @Test
    void testBigValidStarCreation() {
        assertTrue(giantStar.getId() >=1 );
        assertEquals("Giant6789012345678901234567890", giantStar.getName());
        assertEquals(10000, giantStar.getMass());
        assertEquals(planetarySystem, giantStar.getPlanetarySystem());
        assertEquals(50.0, giantStar.getDiameter());

    }
    @Test
    void testInValidStarCreation() {
        assertTrue(tooBigStar.getId() >=1 );
        assertEquals("123456789012345678901234567890", tooBigStar.getName());
        assertEquals(8909, tooBigStar.getMass());
        assertEquals(planetarySystem, tooBigStar.getPlanetarySystem());
        assertEquals(1.5, tooBigStar.getDiameter(), .01);

        assertTrue(tooSmallStar.getId() >=1 );
        assertEquals("", tooSmallStar.getName());
        assertEquals(.1, tooSmallStar.getMass(), .01);
        assertNull( tooSmallStar.getPlanetarySystem());
        assertEquals(0.5, tooSmallStar.getDiameter(), .01);
    }

        @Test
        void testSetValidMass() {
            star.setMass(1.5);
            assertEquals(1.5, star.getMass(), .01);
            star.setMass(15.0);
            assertEquals(15.0, star.getMass(), .01);
        }

        @Test
        void testSetInvalidMass() {
            assertEquals(5778, star.getMass(), .01);
            star.setMass(-2.0);
            assertEquals(5778, star.getMass(), .01);
            star.setMass(0.0);
            assertEquals(5778, star.getMass(), .01);
        }
    @Test
    void testSetValidDiameter() {
        assertEquals(1.0, star.getDiameter());
        star.setDiameter(1.5);
        assertEquals(1.5, star.getDiameter(), .01);

    }

    @Test
    void testSetInvalidDiameter() {
        assertEquals(1.0, star.getDiameter());
        star.setDiameter(-2.0);
        assertEquals(1.0, star.getDiameter());
        star.setDiameter(0.0);
        assertEquals(1.0, star.getDiameter());
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
        assertEquals("Sun", star.getName());
        star.setName("123456789012345678901234567890XXX");
        assertEquals("Sun", star.getName());
    }
    @Test
    void testSetPlanetarySystem() {
        assertEquals(planetarySystem, star.getPlanetarySystem());
        PlanetarySystem newPlanetarySystem = new PlanetarySystem("G-type", "G-Sun");
        star.setPlanetarySystem(newPlanetarySystem);
        assertEquals(newPlanetarySystem, star.getPlanetarySystem());

    }
    @Test
    void testToString() {
            String starString = star.toString();;
            assertTrue(starString.contains("Id: "));
            assertTrue(starString.contains("Name: "));
            assertTrue(starString.contains("Sun"));
            assertTrue(starString.contains("Mass: "));
            assertTrue(starString.contains("5778"));
            assertTrue(starString.contains("Diameter: "));
            assertTrue(starString.contains("1.0"));

    }
    }


