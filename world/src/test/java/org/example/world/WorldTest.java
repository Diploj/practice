package org.example.world;

import org.example.entities.Hare;
import org.example.entities.Plant;
import org.example.entities.Wolf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class WorldTest {

    private Hare mockHare;
    private Wolf mockWolf;
    private Plant mockPlant;
    private World world;


    @BeforeEach
    void setUp() {
        world = new World(10, 1);
        mockHare = mock(Hare.class);
        mockPlant = mock(Plant.class);
        mockWolf = mock(Wolf.class);
    }

    @Test
    void nextDayIncrementsAgeTest() {
        world.nextDay();
        assertEquals(1, world.getAge());
    }
    @Test
    void nextDayRunAnyCreatureTest() {
        world.getHares().add(mockHare);
        world.getWolves().add(mockWolf);
        world.getPlants().add(mockPlant);
        world.nextDay();
        verify(mockHare,times(1)).action(world);
        verify(mockWolf,times(1)).action(world);
        verify(mockPlant,times(1)).action(world);
    }

    @Test
    void nextDayCreatePlantsTest() {
        world.nextDay();
        assertEquals(1, world.getPlants().size());
    }

    @Test
    void nextDayDeleteExtraEntitiesTest() {
        world.getHares().addAll(new ArrayList<>(Collections.nCopies(10000, mockHare)));
        world.getWolves().addAll(new ArrayList<>(Collections.nCopies(10000, mockWolf)));
        world.getPlants().addAll(new ArrayList<>(Collections.nCopies(10000, mockPlant)));
        world.nextDay();
        assertEquals(7000, world.getPlants().size());
        assertEquals(3000, world.getHares().size());
        assertEquals(500, world.getWolves().size());
    }

    @Test
    void drawPrintAnyCreatureTest() {
        Point p = new Point(1,1);
        when(mockHare.getPosition()).thenReturn(p);
        when(mockWolf.getPosition()).thenReturn(p);
        when(mockPlant.getPosition()).thenReturn(p);
        world.getHares().add(mockHare);
        world.getWolves().add(mockWolf);
        world.getPlants().add(mockPlant);
        world.draw();
        verify(mockHare,times(1)).getPosition();
        verify(mockWolf,times(1)).getPosition();
        verify(mockPlant,times(1)).getPosition();
    }
}