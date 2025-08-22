package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlanetarySystemTest {

    PlanetarySystem pValid, pInvalid, pBorder, pBelowBorder, pEmpty;

    @BeforeEach
    void setup() {
        pValid = new PlanetarySystem("Solar System", "Sun", 4, true, 2000, "G-type");
        pInvalid = new PlanetarySystem("Solar System1234567890123456789012345678901234567890",
                "Sun4567890123456789012345678901", 5, false, 1990, "K-type");
        pBorder = new PlanetarySystem("Solar System34567890123456789012345678901234567890",
                "Sun456789012345678901234567890", 6, true, 1980, "M-type");
        pBelowBorder = new PlanetarySystem("Solar System3456789012345678901234567890123456789",
                "Sun45678901234567890123456789", 7, false, 1970, "O-type");
        pEmpty = new PlanetarySystem("", "", 0, false, 1900, "");
    }

    @Test
    void constructorTests() {
        // systemName
        assertEquals("Solar System", pValid.getSystemName());
        assertEquals("Solar System12345678901234567890123456789012345678", pInvalid.getSystemName()); // truncated to 50
        assertEquals("Solar System34567890123456789012345678901234567890", pBorder.getSystemName());
        assertEquals("Solar System3456789012345678901234567890123456789", pBelowBorder.getSystemName());
        assertEquals("", pEmpty.getSystemName());

        // orbittingStarName
        assertEquals("Sun", pValid.getOrbittingStarName());
        assertEquals("Sun456789012345678901234567890", pInvalid.getOrbittingStarName()); // truncated to 30
        assertEquals("Sun456789012345678901234567890", pBorder.getOrbittingStarName());
        assertEquals("Sun45678901234567890123456789", pBelowBorder.getOrbittingStarName());
        assertEquals("", pEmpty.getOrbittingStarName());

        // systemType
        assertEquals("G-type", pValid.getSystemType());
        assertEquals("K-type", pInvalid.getSystemType());
        assertEquals("M-type", pBorder.getSystemType());
        assertEquals("O-type", pBelowBorder.getSystemType());
        assertEquals("", pEmpty.getSystemType());
    }

    @Test
    void planetaryNameGetAndSetWorkingCorrectly() {
        assertEquals("Solar System", pValid.getSystemName());
        pValid.setSystemName("Bigger Solar System");
        assertEquals("Bigger Solar System", pValid.getSystemName());

        pValid.setSystemName("Solar System1234567890123456789012345678901234567890"); // too long
        assertEquals("Bigger Solar System", pValid.getSystemName()); // should reject and will stay same

        pValid.setSystemName("New Name");
        assertEquals("New Name", pValid.getSystemName());
    }

    @Test
    void orbitStarGetAndSetWorkingCorrectly() {
        assertEquals("Sun", pValid.getOrbittingStarName());
        pValid.setOrbittingStarName("Sunny Sun");
        assertEquals("Sunny Sun", pValid.getOrbittingStarName());

        pValid.setOrbittingStarName("Sunny Sun12345678901234567890123445566890"); // too long
        assertEquals("Sunny Sun", pValid.getOrbittingStarName()); // truncated/ignored

        pValid.setOrbittingStarName("");
        assertEquals("", pValid.getOrbittingStarName());
    }

    @Test
    void validatingTheEqualsMethod() {
        PlanetarySystem sameRef = pValid;
        assertEquals(sameRef, pValid);

        PlanetarySystem sameValues = new PlanetarySystem("New Name", "Sunny Sun", 1, true, 2000, "G-type");
        sameValues.setSystemName(pValid.getSystemName());
        sameValues.setOrbittingStarName(pValid.getOrbittingStarName());
        assertEquals(pValid, sameValues);

        PlanetarySystem diffName = new PlanetarySystem("Other Name", "Sun", 1, true, 2000, "G-type");
        PlanetarySystem diffStar = new PlanetarySystem("Solar System", "Other Sun", 1, true, 2000, "G-type");
        PlanetarySystem bothDiff = new PlanetarySystem("Other Name", "Other Sun", 1, true, 2000, "G-type");

        assertNotEquals(pValid, diffName);
        assertNotEquals(pValid, diffStar);
        assertNotEquals(pValid, bothDiff);
    }

    @Nested
    class ToString {
        @Test
        void toStringContainsAllFields() {
            String str = pValid.toString();
            assertTrue(str.contains("Solar System"));
            assertTrue(str.contains("SUN"));
        }

        @Test
        void toStringHandlesLongNames() {
            String str = pInvalid.toString();
            assertTrue(str.contains("Solar"));
        }
    }
}
