package org.example.utils;

import java.awt.*;

public class CordUtil {
    public static Point trajectory(Point p1, Point p2, int size){
        int x = p2.x - p1.x;
        int y = p2.y - p1.y;
        if(x > size / 2){
            x -= size;
        }
        else{
            if(x < -size/2){
                x += size;
            }
        }
        if(y > size / 2){
            y -= size;
        }
        else{
            if(y < -size/2){
                y += size;
            }
        }
        return new Point(x,y);
    }

    public static int distant(Point p){
        return p.x*p.x+p.y*p.y;
    }
}
