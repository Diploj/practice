package org.example.entities;

import org.example.world.World;

import java.awt.*;
import java.util.Random;

public class Plant{
    private int health;
    private Point position;

    public Plant(Point position) {
        this.health = 50;
        this.position = position;
    }
    public void action(World world){
       health += 25;
        if(health > 100){
            world.getChanges().add(() -> giveBirth(world));
            health /= 2;
        }
    };

    public Point getPosition() {
        return position;
    }

    private void giveBirth(World world)
    {
        Random random = world.getRandom();
        int size = world.getSize();
        int x = (position.x + random.nextInt(5) - 2 + size) % size;
        int y = (position.y + random.nextInt(5) - 2 + size) % size;
        world.getPlants().add(new Plant(new Point(x,y)));
    };
}
