package edu.neu.cs5004.maze;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MazeTest {
    Maze m;
    Maze g;
    @BeforeEach
    void setUp() throws IOException {
        m = new Maze("src/main/resources/Maze1.json");
        g = new Maze();
    }

    @Test
    void findPath() {
        assertEquals("ABE",m.findPath());
        for (int i=0;i<100;i++) {
            g=new Maze();
            assertNotNull(g.findPath());
        }
    }

    @Test
    void getPoints() {
        assertEquals(5,m.getPoints().size());
        assertEquals(26,g.getPoints().size());
    }

    @Test
    void getStartingPoint() {
        for (int i=0;i<20;i++) {
            g=new Maze();
            for (Point p: g.getPoints()) {
                if (p.isStart()) {
                    assertEquals(p,g.getStartingPoint());
                }
            }
        }

    }

}