package edu.neu.cs5004.maze;

import java.util.Objects;

public class Direction {
    private String direction;
    private String location;
    public Direction() {

    }
    public Direction(String direction, String location) {
        this.direction = direction;
        this.location = location;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDirection() {
        return direction;
    }

    public String getLocation() {
        return location;
    }

    public String getOppositeDirection() {
        if (direction.equals("N")) {
            return "S";
        } else if (direction.equals("S")) {
            return "N";
        } else if (direction.equals("E")) {
            return "W";
        } else {
            return "E";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Direction direction1)) return false;
        return direction.equals(direction1.direction) && location.equals(direction1.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction, location);
    }
}
