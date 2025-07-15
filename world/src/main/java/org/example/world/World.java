package org.example.world;

import org.example.entities.Hare;
import org.example.entities.Plant;
import org.example.entities.Wolf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class World {
    private static final Logger log = LogManager.getLogger(World.class);
    private int size;
    private int countPlants;
    private Random random = new Random();
    private List<Plant> plants;
    private List<Hare> hares;
    private List<Wolf> wolves;
    private List<Runnable> changes;
    private int age;


    public World(int size,int countPlants) {
        log.info("Creating new world");
        this.size = size;
        this.age = 0;
        this.countPlants = countPlants;
        plants = new ArrayList<>();
        hares = new ArrayList<>();
        wolves = new ArrayList<>();
        changes = new ArrayList<>();
    }

    public void nextDay() {
        age++;
        log.info("Start day " + age);
        spawnPlants();
        for (Wolf wolf : wolves) {
            wolf.action(this);
        }
        for (Hare hare : hares) {
            hare.action(this);
        }
        for (Plant plant : plants) {
            plant.action(this);
        }
        for (Runnable change : changes) {
            change.run();
        }
        changes.clear();
        if (plants.size() > 10000) {
            plants = plants.subList(0, 7000);
        }
        if (hares.size() > 4000) {
            hares = hares.subList(0, 3000);
        }
        if (wolves.size() > 1000) {
            wolves = wolves.subList(0, 500);
        }
        log.info("Count wolves: " + wolves.size());
        log.info("Count hares: " + hares.size());
        log.info("Count plants: " + plants.size());
    }

    public void draw() {
        char[][] grid = new char[size][size];

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                grid[y][x] = ' ';
            }
        }
        for (Plant plant : plants) {
            Point p = plant.getPosition();
            grid[p.x][p.y] = '*';
        }
        for (Hare hare : hares) {
            Point p = hare.getPosition();
            grid[p.x][p.y] = '%';
        }
        for (Wolf wolf : wolves) {
            Point p = wolf.getPosition();
            grid[p.x][p.y] = '@';
        }

        System.out.println("\nДень " + age + "\n");

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                System.out.print(grid[y][x] + " ");
            }
            System.out.println();
        }

        System.out.println("\nСтатистика:");
        System.out.println("Растения: " + plants.size());
        System.out.println("Зайцы: " + hares.size());
        System.out.println("Волки: " + wolves.size());
    }

    private void spawnPlants(){
        for(int i = 0;i<countPlants;++i){
            plants.add(new Plant(new Point(
                    random.nextInt(size),
                    random.nextInt(size)
            )));
        }
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public List<Hare> getHares() {
        return hares;
    }

    public List<Wolf> getWolves() {
        return wolves;
    }

    public int getSize() {
        return size;
    }

    public Random getRandom() {
        return random;
    }

    public List<Runnable> getChanges() {
        return changes;
    }
}
