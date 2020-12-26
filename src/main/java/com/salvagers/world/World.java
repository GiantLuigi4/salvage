package com.salvagers.world;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.dynamics.constraintsolver.*;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;
import com.salvagers.client.Window;
import com.salvagers.common.shapes.Triangle;
import com.salvagers.parts.objects.Block;
import com.salvagers.parts.objects.Part;
import com.salvagers.parts.objects.Shapes;
import com.salvagers.parts.objects.Wheel;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import java.util.ArrayList;

//https://github.com/OskarVeerhoek/YouTube-tutorials/blob/master/src/episode_39/JBulletDemo.java
public class World {
    public ArrayList<Part> parts = new ArrayList<>();
    
    BroadphaseInterface broadphase = new DbvtBroadphase();
    CollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
    CollisionDispatcher dispatcher = new CollisionDispatcher(collisionConfiguration);
    ConstraintSolver solver = new SequentialImpulseConstraintSolver();
    DiscreteDynamicsWorld dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
    
    public World() {
        addPart(new Wheel(1, 1, 1).setDefaultPos(0, 100, 0));
        
        ArrayList<Triangle> triangles = Window.terrainGen.getTriangles(-64, -64, 128, 128);
        triangles.forEach(tri -> addShape(Shapes.getTriangle(tri), 0, new Vector3f(0, 0, 0)));
        
        addConstraint(
                addPart(new Block(1, 1, 1).setDefaultPos(0, 110, 0)),
                addPart(new Block(1, 1, 1).setDefaultPos(0, 120, 0)),
                new Vector3f(0, 0, 0),
                new Vector3f(0, 1, 0),
                TypedConstraintType.POINT2POINT_CONSTRAINT_TYPE,
                true
        );
    }
    
    /**
     * adds a part to the world
     *
     * @param part the part to add
     * @return the input (as a way to simplify stuff)
     */
    public Part addPart(Part part) {
        parts.add(part);
        
        part.collisionBody = addShape(part.body, part.weight, part.pos);
        
        return part;
    }
    
    /**
     * Adds a shape to the world (because some shapes aren't going to be parts, such as with terrain)
     *
     * @param shape the shape you want added
     * @param mass  the mass of the shape (0 for immovable)
     * @return the rigid body from said shape
     */
    public RigidBody addShape(CollisionShape shape, float mass, Vector3f pos) {
        Vector3f inertia = new Vector3f(0, 0, 0);
        shape.calculateLocalInertia(mass, inertia);
        
        Transform startingTransform = new Transform(new Matrix4f(new Quat4f(0, 0, 0, 1), new Vector3f(0, 35, 0), 1.0f));
        MotionState motionState = new DefaultMotionState(new Transform(startingTransform));
        RigidBodyConstructionInfo bodyConstructionInfo = new RigidBodyConstructionInfo(mass, motionState, shape, inertia);
        bodyConstructionInfo.angularDamping = 0.95f;
        
        RigidBody collisionBody = new RigidBody(bodyConstructionInfo);
        Transform transform = collisionBody.getMotionState().getWorldTransform(new Transform());
        transform.setIdentity();
        transform.origin.set(pos);
        collisionBody.getMotionState().setWorldTransform(transform);
        transform = collisionBody.getWorldTransform(new Transform());
        transform.setIdentity();
        transform.origin.set(pos);
        collisionBody.setWorldTransform(transform);
        
        dynamicsWorld.addRigidBody(collisionBody);
        if (mass != 0) collisionBody.activate();
        else collisionBody.setDeactivationTime(1000000000);
        return collisionBody;
    }
    
    public void tick() {
        dynamicsWorld.stepSimulation(1 / 60.0f);
    }
    
    /**
     * adds a constraint between two parts
     *
     * @param partA        the first part of the constraint
     * @param partB        the second part of the constraint
     * @param attachPointA position offset relative to the first part
     * @param attachPointB position offset relative to the second part
     * @param type         the type of constraint (currently only HINGE_CONSTRAINT_TYPE, SLIDER_CONSTRAINT_TYPE, POINT2POINT_CONSTRAINT_TYPE)
     */
    public void addConstraint(Part partA, Part partB, Vector3f attachPointA, Vector3f attachPointB, TypedConstraintType type) {
        addConstraint(partA, partB, attachPointA, attachPointB, type, false);
    }
    
    /**
     * adds a constraint between two parts
     *
     * @param partA            the first part of the constraint
     * @param partB            the second part of the constraint
     * @param attachPointA     position offset relative to the first part
     * @param attachPointB     position offset relative to the second part
     * @param type             the type of constraint (currently only HINGE_CONSTRAINT_TYPE, SLIDER_CONSTRAINT_TYPE, POINT2POINT_CONSTRAINT_TYPE)
     * @param disableCollision whether or not partA should be able to collide with partB
     */
    public void addConstraint(Part partA, Part partB, Vector3f attachPointA, Vector3f attachPointB, TypedConstraintType type, boolean disableCollision) {
        TypedConstraint constraint = null;
        switch (type) {
            //Using brackets so I can use repetitive variable names
            case HINGE_CONSTRAINT_TYPE: {
                Transform transformA = new Transform();
                Transform transformB = new Transform();
                transformA.origin.set(attachPointA);
                transformB.origin.set(attachPointB);
                constraint = new HingeConstraint(partA.collisionBody, partB.collisionBody, transformA, transformB);
                break;
            }
            case SLIDER_CONSTRAINT_TYPE: {
                Transform transformA = new Transform();
                Transform transformB = new Transform();
                transformA.origin.set(attachPointA);
                transformB.origin.set(attachPointB);
                constraint = new SliderConstraint(partA.collisionBody, partB.collisionBody, transformA, transformB, true);
                break;
            }
            case POINT2POINT_CONSTRAINT_TYPE: {
                constraint = new Point2PointConstraint(partA.collisionBody, partB.collisionBody, attachPointA, attachPointB);
                break;
            }
        }
        if (constraint != null)
            dynamicsWorld.addConstraint(constraint, disableCollision);
    }
}
