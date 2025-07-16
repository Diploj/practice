package org.example.entities;

import org.example.utils.CordUtil;
import org.example.world.World;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Wolf {
    private int health;
    private int hunger;
    private int visibleRange;
    private int speed;
    private int age;
    private static final int MEDIAN_DEATH_AGE = 15;
    private Point position;

    public Wolf(int speed, int visibleRange, Point position) {
        this.age = 0;
        this.health = 60;
        if (speed < 0) {
            speed = 0;
        }
        if (visibleRange < 0) {
            visibleRange = 0;
        }
        this.speed = speed;
        this.visibleRange = visibleRange;
        this.hunger = 1 + speed + visibleRange;
        this.position = position;
    }

    public void action(World world) {
        age++;
        if (age > MEDIAN_DEATH_AGE) {
            health -= (age - MEDIAN_DEATH_AGE) * 10;
        }
        int size = world.getSize();
        Hare target = findFood(world.getHares(), size);
        if (target != null) {
            if (tryMove(target.getPosition(), size)) {
                world.getHares().remove(target);
                health += 50;
            }
        } else {
            Random random = world.getRandom();
            tryMove(new Point(random.nextInt(size), random.nextInt(size)), size);
            health -= hunger;
        }
        if (health > 200) {
            world.getChanges().add(() -> giveBirth(world));
            health = 70;
        }
        if (health < 0) {
            world.getChanges().add(() -> world.getWolves().remove(this));
        }
    }

    private Hare findFood(List<Hare> hares, int size){
        Hare target = null;
        int min = 0;
        for (Hare hare: hares){
            Point trajectory = CordUtil.trajectory(position,hare.getPosition(),size);
            int distantSquares = CordUtil.distant(trajectory);
            if(distantSquares <= visibleRange*visibleRange && (target == null || min > distantSquares)){
                target = hare;
                min = distantSquares;
            }
        }
        return target;
    }

    private boolean tryMove(Point pos,int size){
        Point trajectory = CordUtil.trajectory(position,pos,size);
        int distantSquares = CordUtil.distant(trajectory);
        if(distantSquares<=speed*speed){
            position = pos;
            return true;
        }
        else
        {
            double distance = Math.sqrt(distantSquares);
            double nx = trajectory.x / distance;
            double ny = trajectory.y / distance;
            int x = position.x + (int) Math.round(nx * speed);
            int y = position.y + (int) Math.round(ny * speed);
            x = (x + size) % size;
            y = (y + size) % size;
            position = new Point(x,y);
            return false;
        }
    }

    private void giveBirth(World world)
    {
        Random random = world.getRandom();
        world.getWolves().add(new Wolf(
                speed + random.nextInt(3) - 1,
                visibleRange + random.nextInt(3) - 1,
                position
        ));
    };

    public Point getPosition() {
        return position;
    }
}
