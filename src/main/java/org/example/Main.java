package org.example;

import org.example.entities.Hare;
import org.example.entities.Plant;
import org.example.entities.Wolf;
import org.example.world.World;

import java.awt.*;
import java.util.Scanner;

public class Main {
    /*Цель задачи написать симулятор естественного отбора.
    * Всего в мире присутствует 3 типа существ трава, зайцы и волки.
    * Мир представляет, из себя квадрат заданного размера при пересечении
    * границы, которого существо оказывается на другой стороне.
    * При рождении новых существ их характеристики могут немного откланяться от родительских.*/
    public static void main(String[] args) {
        World world = new World(20,10);
        world.getPlants().add(new Plant(new Point(1,1)));
        world.getPlants().add(new Plant(new Point(9,13)));
        world.getPlants().add(new Plant(new Point(9,10)));
        world.getHares().add(new Hare(2,3,new Point(14,4)));
        world.getHares().add(new Hare(2,3,new Point(6,4)));
        world.getWolves().add(new Wolf(2,4,new Point(10,7)));
        Scanner scanner = new Scanner(System.in);
        int choice = 1;
        while (choice != 0){
            world.nextDay();
            world.draw();
            System.out.print("Введите 0 чтобы закончить\n");
            choice = scanner.nextInt();
        }
    }
}