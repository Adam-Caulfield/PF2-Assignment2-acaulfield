package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StellarObjectTest {
/* protected char spectralType = 'M'; //must be one of OBAFGKM  default to M
    protected double luminosity = 1000.0; //must be a value between 1000 and 200000 default to 1000.0
 */

    private Star star;

    private PlanetarySystem planetarySystem;
    @BeforeEach
    void setUp() {
        planetarySystem = new PlanetarySystem("G-type", "G-Sun");
        star = new Star("Sun", 5778, 1.0, planetarySystem,'M', 1500);
    }
    @Test
    void testConstructors(){
        assertEquals(planetarySystem, star.getPlanetarySystem());
        assertEquals("Sun", star.getName());
        assertEquals(5778, star.getMass());
        assertEquals(1.0, star.getDiameter(), .01);
        assertEquals('M', star.getSpectralType());
        assertEquals(1500, star.getLuminosity());
    }

    @Test
    void testSetValidSpectralTypes(){
        assertEquals('M', star.getSpectralType());
        star.setSpectralType('O');   //valid
        assertEquals('O', star.getSpectralType());
        star.setSpectralType('B');   //valid
        assertEquals('B', star.getSpectralType());
        star.setSpectralType('A');   //valid
        assertEquals('A', star.getSpectralType());
        star.setSpectralType('F');   //valid
        assertEquals('F', star.getSpectralType());
        star.setSpectralType('G');   //valid
        assertEquals('G', star.getSpectralType());
        star.setSpectralType('K');   //valid
        assertEquals('K', star.getSpectralType());
        star.setSpectralType('M');
        assertEquals('M', star.getSpectralType());
    }
    @Test
    void testSetInValidSpectralTypes(){
        //testing lower case version of valid characters
        assertEquals('M', star.getSpectralType());
        star.setSpectralType('o');   //valid
        assertEquals('M', star.getSpectralType());
        star.setSpectralType('b');
        assertEquals('M', star.getSpectralType());
        star.setSpectralType('a');
        assertEquals('M', star.getSpectralType());
        star.setSpectralType('f');
        assertEquals('M', star.getSpectralType());
        star.setSpectralType('g');
        assertEquals('M', star.getSpectralType());
        star.setSpectralType('k');
        assertEquals('M', star.getSpectralType());
        star.setSpectralType('m');
        assertEquals('M', star.getSpectralType());

        star.setSpectralType('X');
        assertEquals('M', star.getSpectralType());
        star.setSpectralType('x');
        assertEquals('M', star.getSpectralType());

    }

    @Test
    void testSetValidLuminosity(){
        assertEquals(1500, star.getLuminosity());
        star.setLuminosity(5778);
        assertEquals(5778, star.getLuminosity());
        star.setLuminosity(200000);
        assertEquals(200000, star.getLuminosity());
        star.setLuminosity(1000);
        assertEquals(1000, star.getLuminosity());
    }
    @Test
    void testSetInValidLuminosity(){
        assertEquals(1500, star.getLuminosity());
        star.setLuminosity(999);
        assertEquals(1500, star.getLuminosity());
        star.setLuminosity(200001);
        assertEquals(1500, star.getLuminosity());
        star.setLuminosity(-1);
        assertEquals(1500, star.getLuminosity());
    }

    @Test
    void testDisplayInfo(){
        String displayInfo = star.displayInfo();
        assertTrue(displayInfo.contains("Spectral Type: "));
        assertTrue(displayInfo.contains("M"));
        assertTrue(displayInfo.contains(" - Luminosity:"));
        assertTrue(displayInfo.contains("1500"));
    }

    @Test
    void testToString(){
        String string = star.toString();
        assertTrue(string.contains("SpectralType: "));
        assertTrue(string.contains("M"));
        assertTrue(string.contains("luminosity: "));
        assertTrue(string.contains("1500"));
    }

}