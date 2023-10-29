package edu.neu.cs5004.maze;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.w3c.dom.Node;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Maze {
    private final static int POINTS_REQUIRED_IN_CREATE_MAP = 10;
    private Set<Point> points;
    private Point startingPoint;
    private HashMap<Coordinates,Point> visited;
    private List<String> compass = Arrays.asList("NSEW".split(""));
    private List<String> letters = Arrays.asList("ABCDEFGHIJKLMNOPQRSTUVWXYZ".split(""));
    private Random rand;


    protected class Coordinates {
        private int x;
        private int y;

        public Coordinates(int x, int y) {
            this.x=x;
            this.y=y;
        }

        public Coordinates down() {
            return new Coordinates(x,y-1);
        }

        public Coordinates up() {
            return new Coordinates(x,y+1);
        }

        public Coordinates left() {
            return new Coordinates(x-1,y);
        }

        public Coordinates right() {
            return new Coordinates(x+1,y);
        }

        public void print() {
            System.out.println("("+x+","+y+")");
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Coordinates that)) return false;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public Maze(String fileURL) throws IOException {
        ObjectMapper om = new ObjectMapper();
        try {
            this.points = om.readValue(new File(fileURL), new TypeReference<Set<Point>>() {});
            for (Point point : points) {
                if (point.isStart()) {
                    this.startingPoint=point;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //String Name, Direction[] directions, boolean isStart,boolean isEnd)
    public Maze() {
        //Collections.shuffle(letters);
        visited=new HashMap<>();
        points = new HashSet<>();
        rand = new Random();
        int index= 0;
        Coordinates curr = new Coordinates(0,0);
        Point origin = new Point(letters.get(index));
        visited.put(curr,origin);
        points.add(origin);
        this.startingPoint = origin;
        Direction d = new Direction(compass.get(rand.nextInt(4)), letters.get(index+1));
        origin.setStart(true);
        origin.addDirections(d);
        Coordinates temp = null;
        switch (d.getDirection()) {
            case "N": {
               temp=curr.up();
               break;
            }
            case "S": {
                temp=curr.down();
                break;
            }
            case "E": {
                temp=curr.left();
                break;
            }
            case "W": {
                temp=curr.right();
            }
        }
        index++;
        generatePath(visited,temp,d,origin,index,null);
        /*for (Coordinates c : visited.keySet()) {
            c.print();
            visited.get(c).print();
        }*/
    }

    public void generatePath(HashMap<Coordinates,Point> visited, Coordinates curr, Direction d, Point prev, int index, Point p) {
        if (p == null) {
            p =new Point(letters.get(index));
            visited.put(curr,p);
            points.add(p);
            index++;
            if (index==26) {
                p.addDirections(new Direction(d.getOppositeDirection(), prev.getName()));
                p.setEnd(true);
                return ;
            }
        }
        p.addDirections(new Direction(d.getOppositeDirection(), prev.getName()));
        String dir = compass.get(rand.nextInt(4));
        Coordinates temp = null;
        switch (dir) {
            case "N": {
                temp=curr.up();
                break;
            }
            case "S": {
                temp=curr.down();
                break;
            }
            case "E": {
                temp=curr.right();
                break;
            }
            case "W": {
                temp=curr.left();
                break;
            }
        }
        if (visited.containsKey(temp)) {
            Direction a = new Direction(dir,visited.get(temp).getName());
            p.addDirections(a);
            generatePath(visited,temp,a,p,index,visited.get(temp));
        } else {
            Direction a = new Direction(dir, letters.get(index));
            p.addDirections(a);
            generatePath(visited,temp,a,p,index,null);
        }

    }


    public String findPath() {
        HashSet<String> visited = new HashSet<>();
        String path =  pathCracker(startingPoint,"",visited);
        return path;
    }

    private String pathCracker(Point point,String path,HashSet<String> visited) {
        if (visited.contains(point.getName())) {
            return null;
        }
        visited.add(point.getName());
        path+=point.getName();
        if (point.isEnd()) {
            return path;
        }
        String minPaths = null;
        for (Direction d : point.getDirections()) {
            String loc=d.getLocation();
            for (Point p : points) {
                if (p.getName().equals(loc)) {
                    String paths=pathCracker(p,path,visited);
                    if (paths!=null && minPaths==null || paths!=null && paths.length()<minPaths.length()) {
                        minPaths=paths;
                    }
                }
            }
        }
        return minPaths;
    }

    public Set<Point> getPoints() {
        return points;
    }

    public Point getStartingPoint() {
        return startingPoint;
    }
}
