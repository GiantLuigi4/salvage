package com.salvagers.world;

import com.salvagers.parts.objects.Part;

import com.salvagers.parts.objects.Wheel;
import net.smert.jreactphysics3d.engine.DynamicsWorld;
import net.smert.jreactphysics3d.mathematics.Matrix3x3;
import net.smert.jreactphysics3d.mathematics.Transform;
import net.smert.jreactphysics3d.mathematics.Vector3;

import java.util.ArrayList;

public class World
{
    public ArrayList<Part> parts = new ArrayList<>();
    
    DynamicsWorld world = new DynamicsWorld(new Vector3(0,-1,0),0.1f);
    
    public World() {
        world.start();
        addPart(new Wheel(1,1,1));
    }
    
    public void addPart(Part part) {
        parts.add(part);
        part.collisionBody = world.createRigidBody(new Transform(), part.weight, new Matrix3x3(1), part.body);
    }
    
    public void tick() {
        world.update();
    }
}
