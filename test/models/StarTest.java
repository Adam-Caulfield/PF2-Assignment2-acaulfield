package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StarTest {
    private Star star;
    private Star giantStar;
    private Star tooBigStar, tooSmallStar;

    private PlanetarySystem planetarySystem;
    @BeforeEach
    void setUp() {
        planetarySystem = new PlanetarySystem("G-type", "G-Sun");
        star = new Star("Sun", 5778, 1.0, planetarySystem,'M', 1500);
        giantStar = new Star("Giant", 10000, 50.0, planetarySystem,'O', 200000);
        tooBigStar = new Star("1234567890123456789012345678901", 8909, 1.5,planetarySystem,'R', 2000000 );
        tooSmallStar = new Star("", 0, 0,planetarySystem,'A', 0 );
    }

    @Test
    void constructors(){
        assertEquals(planetarySystem, star.getPlanetarySystem());
        assertEquals("Sun", star.getName());
        assertEquals(5778, star.getMass());
        assertEquals(1.0, star.getDiameter(), .01);
        assertEquals('M', star.getSpectralType());
        assertEquals(1500, star.getLuminosity());

    }

@Test
    void testDisplayInfo() {
    String dispStr = star.displayInfo();
    assertTrue(dispStr.contains("Spectral Type: "));
    assertTrue(dispStr.contains("M"));
    assertTrue(dispStr.contains("Luminosity: "));
    assertTrue(dispStr.contains("1500"));
}

@Test
    void testCalculateGravity() {
    assertEquals(1.54E-6, star.calculateGravity(), .00001);
}

@Test
    void testClassifyBody() {
    assertEquals("Star", star.classifyBody());

}

    @Test
    void testToString() {
    String toString = star.toString();
    assertTrue(toString.contains("Star: "));
    assertTrue(toString.contains("SpectralType: "));
    assertTrue(toString.contains("M"));
    assertTrue(toString.contains("luminosity: "));
    assertTrue(toString.contains("1500"));

    }
}
