package models;

public abstract class Planet extends CelestialBody {

 private String surfaceType;
 private double averageTemperature;
 private boolean hasLiquidWater;
 private String name;

 public Planet(String name, double mass, double diameter, PlanetarySystem planetarySystem, double averageTemperature, String surfaceType, boolean hasLiquidWater) {
  super(name, mass, diameter, planetarySystem);//super allows all subclasses of a class to implement a particular method as part of its code
  setAverageTemperature(averageTemperature);
  setSurfaceType(surfaceType);
  this.hasLiquidWater = hasLiquidWater;
 }



 public String getSurfaceType() {
  return surfaceType;
 }

 public void setSurfaceType(String surfaceType) {
  if (surfaceType != null && surfaceType.length() > 20) {
   this.surfaceType = surfaceType.substring(0, 20);
  } else {
   this.surfaceType = surfaceType;
  }
 }


 public void setName(String name) {
  if (name != null && !name.isBlank()) {
   this.name = name.length() > 30 ? name.substring(0, 30) : name;
  } else {
   this.name = "Unnamed";
  }
 }



 public double getAverageTemperature() {
  return averageTemperature;
 }

 public void setAverageTemperature(double averageTemperature) {
  if (averageTemperature < -400 || averageTemperature > 400) {
   this.averageTemperature = 0;
  } else {
   this.averageTemperature = averageTemperature;
  }
 }

 public boolean hasLiquidWater() {
  return hasLiquidWater;
 }

 public void setHasLiquidWater(boolean hasLiquidWater) {
  this.hasLiquidWater = hasLiquidWater;
 }

 public double calculateGravity() {
  double radius = getDiameter() / 2.0;
  return (getMass() * 6.67430e-11) / (radius * radius);
 }

}