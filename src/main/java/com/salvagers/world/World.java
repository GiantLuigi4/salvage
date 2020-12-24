package com.salvagers.world;

import com.salvagers.parts.objects.BlockPart;
import com.salvagers.parts.objects.Part;

import com.salvagers.parts.objects.Wheel;
import net.smert.jreactphysics3d.body.CollisionBody;
import net.smert.jreactphysics3d.engine.DynamicsWorld;
import net.smert.jreactphysics3d.mathematics.Matrix3x3;
import net.smert.jreactphysics3d.mathematics.Quaternion;
import net.smert.jreactphysics3d.mathematics.Transform;
import net.smert.jreactphysics3d.mathematics.Vector3;

import java.util.ArrayList;

public class World
{
    public ArrayList<Part> parts = new ArrayList<>();
    
    DynamicsWorld world = new DynamicsWorld(new Vector3(0,-1,0),0.1f);
    
    public World() {
        world.start();
        addPart(new Wheel(1, 1, 1));
        for (int x = -8; x < 8; x++) {
//        for (int x = -0; x < 1; x++) {
            for (int z = -8; z < 8; z++) {
//            for (int z = -0; z < 1; z++) {
                CollisionBody cube = addPart(new BlockPart(1, 1, 1)).collisionBody;
                cube.setIsMotionEnabled(false);
                if (z >=0) {
                    cube.getTransform().getPosition().set(new Vector3(x, -20, z));
                } else {
                    cube.getTransform().getPosition().set(new Vector3(x, -19, z));
                }
            }
        }
    }
    
    public Part addPart(Part part) {
        parts.add(part);
        
        Matrix3x3 inertiaTensor = new Matrix3x3();
        part.body.computeLocalInertiaTensor(inertiaTensor, part.weight);
    
        Quaternion initOrientation = new Quaternion().identity();
        
        part.collisionBody = world.createRigidBody(new Transform(new Vector3(0,0,0),initOrientation), part.weight, inertiaTensor, part.body);
        part.collisionBody.setIsCollisionEnabled(true);
        return part;
    }
    
    public void tick() {
        for (Part part : parts) {
            if ((int)part.lastRefresh != (int)world.getPhysicsTime()) {
                part.lastPosition = part.collisionBody.getTransform().getPosition();
                part.lastRotation = part.collisionBody.getTransform().getOrientation();
            }
            part.lastRefresh = world.getPhysicsTime();
        }
        world.update();
    }
}
