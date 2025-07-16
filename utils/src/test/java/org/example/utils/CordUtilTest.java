package org.example.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

class CordUtilTest {
    @Test
    void trajectoryWithoutCrossingTheBorderTest() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(3, 2);
        Point trajectory = CordUtil.trajectory(p1,p2,10);
        Assertions.assertEquals(trajectory.x,p2.x-p1.x);
        Assertions.assertEquals(trajectory.y,p2.y-p1.y);
    }
    @Test
    void trajectoryWithCrossingTheBorderTest() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(9, 9);
        Point trajectory = CordUtil.trajectory(p1,p2,10);
        Assertions.assertEquals(trajectory.x,-2);
        Assertions.assertEquals(trajectory.y,-2);
    }

    @Test
    void distant() {
        Point trajectory = new Point(3, 4);
        int distantSquares = CordUtil.distant(trajectory);
        Assertions.assertEquals(distantSquares,25);
    }
}