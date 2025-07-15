package org.example.entities;

import org.example.utils.CordUtil;
import org.example.world.World;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Hare{
    private int health;
    private int hunger;
    private int visibleRange;
    private int speed;
    private Point position;

    public Hare(int speed, int visibleRange, Point position) {
        this.health = 50;
        if(speed < 0) {
            speed = 0;
        }
        if(visibleRange < 0) {
            visibleRange = 0;
        }
        this.speed = speed;
        this.visibleRange = visibleRange;
        this.hunger = 1 + speed + visibleRange;
        this.position = position;
    }

    public void action(World world) {
        int size = world.getSize();
        Plant target = findFood(world.getPlants(), size);
        Wolf enemy = findEnemy(world.getWolves(), size);
        if (enemy != null && health > 2 * hunger) {
            runAway(enemy.getPosition(), size);
            health -= hunger;
        } else {
            if (target != null) {
                if (tryMove(target.getPosition(), size)) {
                    world.getPlants().remove(target);
                    health += 30;
                }
            } else {
                Random random = world.getRandom();
                tryMove(new Point(random.nextInt(size), random.nextInt(size)), size);
                health -= hunger;
                if (health < 0) {
                    world.getChanges().add(() -> world.getHares().remove(this));
                }

            }
            if (health > 100) {
                world.getChanges().add(() -> giveBirth(world));
                health = 70;
            }
        }
    }

    ;

    private Plant findFood(List<Plant> plants, int size) {
        Plant target = null;
        int min = 0;
        for (Plant plant : plants) {
            Point trajectory = CordUtil.trajectory(position, plant.getPosition(), size);
            int distantSquares = CordUtil.distant(trajectory);
            if (distantSquares <= visibleRange * visibleRange && (target == null || min > distantSquares)) {
                target = plant;
                min = distantSquares;
            }
        }
        return target;
    }

    private Wolf findEnemy(List<Wolf> wolves, int size) {
        Wolf target = null;
        int min = 0;
        for (Wolf wolf : wolves) {
            Point trajectory = CordUtil.trajectory(position, wolf.getPosition(), size);
            int distantSquares = CordUtil.distant(trajectory);
            if (distantSquares <= visibleRange * visibleRange && (target == null || min > distantSquares)) {
                target = wolf;
                min = distantSquares;
            }
        }
        return target;
    }

    private boolean tryMove(Point pos, int size) {
        Point trajectory = CordUtil.trajectory(position, pos, size);
        int distantSquares = CordUtil.distant(trajectory);
        if (distantSquares <= speed * speed) {
            position = pos;
            return true;
        } else {
            double distance = Math.sqrt(distantSquares);
            double nx = trajectory.x / distance;
            double ny = trajectory.y / distance;
            int x = position.x + (int) Math.round(nx * speed);
            int y = position.y + (int) Math.round(ny * speed);
            x = (x + size) % size;
            y = (y + size) % size;
            position = new Point(x, y);
            return false;
        }
    }

    private void runAway(Point pos, int size) {
        Point trajectory = CordUtil.trajectory(position, pos, size);
        trajectory.x = -trajectory.x;
        trajectory.y = -trajectory.y;
        double distance = Math.sqrt(CordUtil.distant(trajectory));
        double nx = trajectory.x / distance;
        double ny = trajectory.y / distance;
        int x = position.x + (int) Math.round(nx * speed);
        int y = position.y + (int) Math.round(ny * speed);
        x = (x + size) % size;
        y = (y + size) % size;
        position = new Point(x, y);
    }

    private void giveBirth(World world) {
        Random random = world.getRandom();
        world.getHares().add(new Hare(
                speed + random.nextInt(3) - 1,
                visibleRange + random.nextInt(3) - 1,
                position
        ));
    }

    ;

    public Point getPosition() {
        return position;
    }
}
