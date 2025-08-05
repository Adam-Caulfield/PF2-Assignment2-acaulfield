package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

class PlanetarySystemTest {
    PlanetarySystem pValid, pInvalid, pBorder, pBelowBorder, pEmpty;

    @BeforeEach
    public void setup() {//50 30
        pValid = new PlanetarySystem("Solar System", "Sun");
        pInvalid = new PlanetarySystem("Solar System1234567890123456789012345678901234567890", "Sun4567890123456789012345678901");
        pBorder = new PlanetarySystem("Solar System34567890123456789012345678901234567890", "Sun456789012345678901234567890");
        pBelowBorder = new PlanetarySystem("Solar System3456789012345678901234567890123456789", "Sun45678901234567890123456789");
        pEmpty = new PlanetarySystem("", "");
    }

    @Test
    void constructorTests() {
        assertEquals("Solar System", pValid.getSystemName());  //value accepted - under 50 length limit
        assertEquals("Solar System12345678901234567890123456789012345678", pInvalid.getSystemName());  //value truncated to  50 length limit
        assertEquals("Solar System34567890123456789012345678901234567890", pBorder.getSystemName());
        assertEquals("Solar System3456789012345678901234567890123456789", pBelowBorder.getSystemName());
        assertEquals("", pEmpty.getSystemName());

        //testing numEmployees (>=1)  - at valid and invalid,
        assertEquals("Sun", pValid.getOrbittingStarName());  //valid value accepted correctly
        assertEquals("Sun456789012345678901234567890", pInvalid.getOrbittingStarName());  // check that default is set when invalid input given
        assertEquals("Sun456789012345678901234567890", pBorder.getOrbittingStarName());    // check that limit is accepted as valid input
        assertEquals("Sun45678901234567890123456789", pBelowBorder.getOrbittingStarName());
        assertEquals("", pEmpty.getOrbittingStarName());

    }

    @Test
    void planetaryNameGetAndSetWorkingCorrectly() {
        assertEquals("Solar System", pValid.getSystemName());  //value accepted - under 50 length limit
        pValid.setSystemName("Bigger Solar System");  //valid change
        assertEquals("Bigger Solar System", pValid.getSystemName());  //value accepted - under 50 length limit
        pValid.setSystemName("Solar System34567890123456789012345678901234567890");  //valid change
        assertEquals("Solar System34567890123456789012345678901234567890", pValid.getSystemName());  //value accepted - under 50 length limit
        pValid.setSystemName("");  //valid change
        assertEquals("", pValid.getSystemName());  //value accepted - under 50 length limit
        pValid.setSystemName("Solar System1234567890123456789012345678901234567890");  //invalid change
        assertEquals("", pValid.getSystemName());  //

    }


    @Test
    void orbitStarGetAndSetWorkingCorrectly() {
        assertEquals("Sun", pValid.getOrbittingStarName());  //valid value accepted correctly
        pValid.setOrbittingStarName("Sunny Sun");
        assertEquals("Sunny Sun", pValid.getOrbittingStarName());
        pValid.setOrbittingStarName("");
        assertEquals("", pValid.getOrbittingStarName());
        pValid.setOrbittingStarName("Sunny Sun");
        assertEquals("Sunny Sun", pValid.getOrbittingStarName());
        pValid.setOrbittingStarName("Sunny Sun12345678901234567890123445566890");
        assertEquals("Sunny Sun", pValid.getOrbittingStarName());


    }

    @Test
    void validatingTheEqualsMethod() {
        //checking that equals works when the objects are at the same location
        PlanetarySystem planSysInvalid = pValid;
        assertEquals(planSysInvalid, pValid);
        //now checking that true is returned when the values in separate objects are the same
        assertEquals(pValid, new PlanetarySystem("Solar System", "Sun"));
        //checking that false is returned  when one or both fields are different
        assertNotEquals(pValid, new PlanetarySystem("Our Solar System", "Sun"));
        assertNotEquals(pValid, new PlanetarySystem("Solar System", "The Sun"));
        assertNotEquals(pValid, new PlanetarySystem("Our Solar System", "The Sun"));
    }

    @Nested
    class ToString {
        @Test
        void toStringContainsAllFieldsInObject() {

            String planetaryString = pValid.toString();
            assertTrue(planetaryString.contains("Solar System"));
            assertTrue(planetaryString.contains("SUN"));


        }

        @Test
        void toStringAddsEmployeesToTheString() {

            String planetaryString = pInvalid.toString();
            assertTrue(planetaryString.contains("Solar"));


        }
    }
}