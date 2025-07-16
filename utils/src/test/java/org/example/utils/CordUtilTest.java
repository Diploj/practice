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
        Assertions.assertEquals(p2.x-p1.x,trajectory.x);
        Assertions.assertEquals(p2.y-p1.y,trajectory.y);
    }
    @Test
    void trajectoryWithCrossingTheBorderTest() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(9, 9);
        Point trajectory = CordUtil.trajectory(p1,p2,10);
        Assertions.assertEquals(-2,trajectory.x);
        Assertions.assertEquals(-2,trajectory.y);
    }

    @Test
    void distantTest() {
        Point trajectory = new Point(3, 4);
        int distantSquares = CordUtil.distant(trajectory);
        Assertions.assertEquals(25,distantSquares);
    }
}