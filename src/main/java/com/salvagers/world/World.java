package com.salvagers.world;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;
import com.salvagers.parts.objects.Block;
import com.salvagers.parts.objects.Part;

import com.salvagers.parts.objects.Wheel;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import java.util.ArrayList;

//https://github.com/OskarVeerhoek/YouTube-tutorials/blob/master/src/episode_39/JBulletDemo.java
public class World
{
    public ArrayList<Part> parts = new ArrayList<>();
    
    BroadphaseInterface broadphase = new DbvtBroadphase();
    CollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
    CollisionDispatcher dispatcher = new CollisionDispatcher(collisionConfiguration);
    ConstraintSolver solver = new SequentialImpulseConstraintSolver();
    DiscreteDynamicsWorld dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
    
    public World() {
        addPart(new Wheel(1, 1, 1));
        dynamicsWorld.setGravity(new Vector3f(0, -10, 0));
        int size = 4;
        for (int x = -size; x <= size; x++) {
            for (int z = -size; z <= size; z++) {
                Part part = new Block(0, 1, 1);
                if (z>=0) {
                    part.setDefaultPos(x,-20,z);
                } else {
                    part.setDefaultPos(x,-19,z);
                }
                addPart(part);
            }
        }
    }
    
    /**
     * adds a part to the world
     * @param part the part to add
     * @return the input (as a way to simplify stuff)
     */
    public Part addPart(Part part) {
        parts.add(part);
        
        Vector3f inertia = new Vector3f(0, 0, 0);
        part.body.calculateLocalInertia(part.weight, inertia);
        
        Transform startingTransform = new Transform(new Matrix4f(new Quat4f(0, 0, 0, 1), new Vector3f(0, 35, 0), 1.0f));
        MotionState motionState = new DefaultMotionState(new Transform(startingTransform));
        RigidBodyConstructionInfo bodyConstructionInfo = new RigidBodyConstructionInfo(part.weight, motionState, part.body, inertia);
        bodyConstructionInfo.angularDamping = 0.95f;
        
        part.collisionBody = new RigidBody(bodyConstructionInfo);
        Transform transform = part.collisionBody.getMotionState().getWorldTransform(new Transform());
        transform.setIdentity();
        transform.origin.set(part.pos);
        part.collisionBody.getMotionState().setWorldTransform(transform);
        transform = part.collisionBody.getWorldTransform(new Transform());
        transform.setIdentity();
        transform.origin.set(part.pos);
        part.collisionBody.setWorldTransform(transform);
        
        dynamicsWorld.addRigidBody(part.collisionBody);
        part.collisionBody.activate();
        return part;
    }
    
    public void tick() {
        dynamicsWorld.stepSimulation(1 / 60.0f);
    }
}
