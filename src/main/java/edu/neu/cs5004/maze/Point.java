package edu.neu.cs5004.maze;

import java.util.*;

public class Point {
    private String name;
    private Direction[] directions;
    private boolean isStart;
    private boolean isEnd;


    public Point(String Name, boolean isStart,boolean isEnd, Direction[] directions) {
        this.name=Name;
        this.isStart=isStart;
        this.isEnd=isEnd;
        this.directions=directions;
    }

    public Point(String name) {
        this.name=name;
        this.isStart=false;
        this.isEnd=false;
        this.directions = new Direction[0];
    }
    public Point() {}


    public String getName() {
        return name;
    }
    public Direction[] getDirections() {
        return directions;
    }
    public boolean isStart() {
        return isStart;
    }
    public boolean isEnd() {
        return isEnd;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDirections(Direction[] directions) {
        this.directions = directions;
    }
    public void addDirections( Direction newDirection) {
        boolean directionExists = false;
        for (Direction direction : directions) {
            if (direction.equals(newDirection)) {
                directionExists = true;
                break;
            }
        }
        if (!directionExists) {
            Direction[] newDirections = Arrays.copyOf(directions, directions.length + 1);
            newDirections[directions.length] = newDirection;
            directions = newDirections;
        }
    }
    public void setStart(boolean start) {
        isStart = start;
    }
    public void setEnd(boolean end) {
        isEnd = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;
        return isStart == point.isStart && isEnd == point.isEnd && name.equals(point.name) && Arrays.equals(directions, point.directions);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, isStart, isEnd);
        result = 31 * result + Arrays.hashCode(directions);
        return result;
    }

    public void print() {
        System.out.println(this.name);

        for (Direction d: this.directions) {
            System.out.println("Pointing "+d.getDirection()+" to: " + d.getLocation()+" ");
        }
    }
}
