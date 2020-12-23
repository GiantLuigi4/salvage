package com.salvagers.world;

import com.badlogic.gdx.physics.bullet.Bullet;
import com.salvagers.parts.objects.Part;

import java.util.ArrayList;

public class World
{
    public ArrayList<Part> parts = new ArrayList<>();
    
    static
    {
        Bullet.init();
    }
}
