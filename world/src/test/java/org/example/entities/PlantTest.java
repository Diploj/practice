package org.example.entities;

import org.example.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlantTest {
    private Plant plant;
    private World mockWorld;
    private Random mockRandom;

    @BeforeEach
    void setUp() {
        mockWorld = mock(World.class);
        mockRandom = mock(Random.class);
        plant = new Plant(new Point(1,1));
    }

    @Test
    void actionGiveBirthTest() {
        Point p = new Point(2,2);
        List<Runnable> changes = new ArrayList<>();
        List<Plant> plants = new ArrayList<>();
        when(mockRandom.nextInt(5)).thenReturn(3);
        when(mockWorld.getRandom()).thenReturn(mockRandom);
        when(mockWorld.getSize()).thenReturn(10);
        when(mockWorld.getPlants()).thenReturn(plants);
        when(mockWorld.getChanges()).thenReturn(changes);
        plant.action(mockWorld);
        plant.action(mockWorld);
        plant.action(mockWorld);
        plant.action(mockWorld);
        Assertions.assertFalse(changes.isEmpty());
        verify(mockWorld,times(1)).getChanges();
        changes.get(0).run();
        Assertions.assertEquals(p,plants.get(0).getPosition());
    }
}