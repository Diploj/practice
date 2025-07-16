package org.example.entities;

import org.example.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import java.util.List;

import static org.mockito.Mockito.*;

class HareTest {
    private Hare hare;
    private Wolf mockWolf;
    private Plant mockPlant;
    private World mockWorld;
    private Random mockRandom;

    @BeforeEach
    void setUp() {
        mockWorld = mock(World.class);
        mockRandom = mock(Random.class);
        hare = new Hare(2,3,new Point(1,1));
        mockPlant = mock(Plant.class);
        mockWolf = mock(Wolf.class);
    }

    @Test
    void actionEatPlantTest() {
        List<Plant> plants = new ArrayList<>(List.of(mockPlant));
        Point p = new Point(2,2);
        when(mockPlant.getPosition()).thenReturn(p);
        when(mockWorld.getSize()).thenReturn(10);
        when(mockWorld.getPlants()).thenReturn(plants);
        when(mockWorld.getWolves()).thenReturn(new ArrayList<>());
        hare.action(mockWorld);
        Assertions.assertTrue(plants.isEmpty());
        Assertions.assertEquals(p,hare.getPosition());
        verify(mockPlant,times(2)).getPosition();
        verify(mockWorld,times(1)).getSize();
        verify(mockWorld,times(1)).getWolves();
        verify(mockWorld,times(2)).getPlants();
    }

    @Test
    void actionRunFromWolfTest() {
        List<Plant> plants = new ArrayList<>(List.of(mockPlant));
        List<Wolf> wolves = new ArrayList<>(List.of(mockWolf));
        Point p1 = new Point(0,0);
        Point p2 = new Point(2,2);
        when(mockPlant.getPosition()).thenReturn(p2);
        when(mockWolf.getPosition()).thenReturn(p1);
        when(mockWorld.getSize()).thenReturn(10);
        when(mockWorld.getPlants()).thenReturn(plants);
        when(mockWorld.getWolves()).thenReturn(wolves);
        hare.action(mockWorld);
        Assertions.assertFalse(plants.isEmpty());
        Assertions.assertEquals(p2,hare.getPosition());
        verify(mockPlant,times(1)).getPosition();
        verify(mockWolf,times(2)).getPosition();
        verify(mockWorld,times(1)).getSize();
        verify(mockWorld,times(1)).getWolves();
        verify(mockWorld,times(1)).getPlants();
    }
}