package org.example.entities;

import org.example.world.World;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class WolfTest {

    private Hare mockHare;
    private Wolf wolf;
    private World mockWorld;
    private Random mockRandom;

    @BeforeEach
    void setUp() {
        mockWorld = mock(World.class);
        mockRandom = mock(Random.class);
        mockHare = mock(Hare.class);
        wolf = new Wolf(2,3,new Point(1,1));
    }

    @Test
    void actionEatHareTest() {
        List<Hare> hares = new ArrayList<>(java.util.List.of(mockHare));
        Point p = new Point(2,2);
        when(mockHare.getPosition()).thenReturn(p);
        when(mockWorld.getSize()).thenReturn(10);
        when(mockWorld.getHares()).thenReturn(hares);
        wolf.action(mockWorld);
        Assertions.assertTrue(hares.isEmpty());
        Assertions.assertEquals(p,wolf.getPosition());
        verify(mockHare,times(2)).getPosition();
        verify(mockWorld,times(1)).getSize();
        verify(mockWorld,times(2)).getHares();
    }

    @Test
    void actionDontFindHareTest() {
        Point p = new Point(2,2);
        when(mockRandom.nextInt(10)).thenReturn(2);
        when(mockHare.getPosition()).thenReturn(p);
        when(mockWorld.getSize()).thenReturn(10);
        when(mockWorld.getRandom()).thenReturn(mockRandom);
        when(mockWorld.getHares()).thenReturn(new ArrayList<>());
        wolf.action(mockWorld);
        Assertions.assertEquals(p,wolf.getPosition());
        verify(mockWorld,times(1)).getRandom();
        verify(mockWorld,times(1)).getSize();
        verify(mockWorld,times(1)).getHares();
        verify(mockRandom,times(2)).nextInt(10);
    }


}