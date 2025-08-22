package utils;

import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class EnergySourceUtility {


    private static final Set<String> validEnergySources = new HashSet<>() {{
        add("Nuclear Fusion");
        add("Geothermal");
        add("Solar Powered");
        add("Tidal Heating");
    }};


    public static List<String> getValidEnergySources() {
        return new ArrayList<>(validEnergySources);
    }


    public static boolean isValidEnergySource(String energyToCheck) {
        if (energyToCheck == null || energyToCheck.isBlank()) return false;

        for (String energy : validEnergySources) {
            if (energy.equalsIgnoreCase(energyToCheck)) {
                return true;
            }
        }
        return false;
    }
}
