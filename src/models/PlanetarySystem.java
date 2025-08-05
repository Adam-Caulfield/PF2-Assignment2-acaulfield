package models;

import utils.Utilities;

import java.util.Objects;

public class PlanetarySystem {
    private String systemName; //e.g TRAPPIST-1 or Solar System -  max 50 chars
    private String orbittingStarName; //e.g Sun for Solar System - max 30 chars
    //more attributes


    public PlanetarySystem(String systemName, String orbittingStarName) {
        this.systemName = Utilities.truncateString(systemName, 50);;
        this.orbittingStarName = Utilities.truncateString(orbittingStarName, 30);;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        if (Utilities.validStringlength(systemName, 50))
            this.systemName = systemName;
    }

    public String getOrbittingStarName() {
        return orbittingStarName;
    }

    public void setOrbittingStarName(String orbittingStarName) {
        if (Utilities.validStringlength(orbittingStarName, 30))
            this.orbittingStarName = orbittingStarName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanetarySystem that = (PlanetarySystem) o;
        return Objects.equals(systemName, that.systemName) && Objects.equals(orbittingStarName, that.orbittingStarName);
    }

    @Override
    public String toString() {
        return "Planetary System:  " +
                "Name: " + systemName +
                ", orbits around: " + orbittingStarName.toUpperCase() ;
    }
}

/*
Extra Information about this class
Our solar system is just one specific planetary system—a star with planets orbiting around it.
Our planetary system is the only one officially called “solar system,” but astronomers have discovered more than 3,200
other stars with planets orbiting them in our galaxy. That’s just how many we’ve found so far.
There are likely to be many more planetary systems out there waiting to be discovered!

 */