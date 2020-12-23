package com.salvagers.world;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.utils.Array;
import com.salvagers.parts.objects.Part;

import com.badlogic.gdx.physics.bullet.collision.*;
import com.salvagers.parts.objects.Wheel;

public class World
{
    public Array<Part> parts = new Array<>();
    
    public btCollisionConfiguration collisionConfig = new btDefaultCollisionConfiguration();
    public btCollisionDispatcher dispatcher = new btCollisionDispatcher(collisionConfig);
    public btDbvtBroadphase broadphase = new btDbvtBroadphase();
    public btSequentialImpulseConstraintSolver constraintSolver = new btSequentialImpulseConstraintSolver();
    public btDynamicsWorld collisionWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, constraintSolver, collisionConfig);
    public WorldContactListener listener = new WorldContactListener();
    
    static
    {
        Bullet.init();
    }
    
    public World() {
        addPart(new Wheel(1,1,1));
        addPart(new Wheel(1,1,2));
    }
    
    public void addPart(Part part) {
        parts.add(part);
        collisionWorld.addCollisionObject(part.body);
    }
    
    public void tick() {
        for (Part obj : parts) {
            obj.isMoving = true;
            obj.body.setActivationState(Collision.DISABLE_DEACTIVATION);
            obj.body.activate();
            obj.body.setContactCallbackFlag(1);
            obj.body.setContactCallbackFilter(1);
            obj.body.clearForces();
            obj.body.applyForce(new Vector3(0,-3f,0),new Vector3());
            if (checkCollision(obj.body, parts.get(0).body)) {
            }
        }
    
        collisionWorld.setGravity(new Vector3(0,1,0));

        Vector3 pos = new Vector3();
        collisionWorld.getCollisionObjectArray().atConst(0).getWorldTransform().getTranslation(pos);
        Matrix4 transform = collisionWorld.getCollisionObjectArray().atConst(0).getWorldTransform();
        transform.setTranslation(0,0.001f,0);
        collisionWorld.getCollisionObjectArray().atConst(0).setWorldTransform(transform);
        System.out.println(pos);
        float delta = 1f / 30f;
        collisionWorld.stepSimulation(delta, 5, 1f/60f);
//        collisionWorld.performDiscreteCollisionDetection();
    }
    
    public boolean checkCollision(btCollisionObject obj0, btCollisionObject obj1) {
        CollisionObjectWrapper co0 = new CollisionObjectWrapper(obj0);
        CollisionObjectWrapper co1 = new CollisionObjectWrapper(obj1);
        
        btCollisionAlgorithm algorithm = dispatcher.findAlgorithm(co0.wrapper, co1.wrapper,
                dispatcher.getNewManifold(obj0,obj1),0
        );
        
        btDispatcherInfo info = new btDispatcherInfo();
        btManifoldResult result = new btManifoldResult(co0.wrapper, co1.wrapper);
        
        algorithm.processCollision(co0.wrapper, co1.wrapper, info, result);
        
        boolean r = result.getPersistentManifold().getNumContacts() > 0;
        
        dispatcher.freeCollisionAlgorithm(algorithm.getCPointer());
        result.dispose();
        info.dispose();
        co1.dispose();
        co0.dispose();
        
        return r;
    }
    
    public void dispose () {
        for (Part part : parts)
            part.dispose();
        parts.clear();
        
        collisionWorld.dispose();
        broadphase.dispose();
        dispatcher.dispose();
        collisionConfig.dispose();
        
        listener.dispose();
    }
    
    public class WorldContactListener extends ContactListener {
//        @Override
//        public boolean onContactAdded (int userValue0, int partId0, int index0, int userValue1, int partId1, int index1) {
//            parts.get(userValue0).isMoving = false;
//            parts.get(userValue1).isMoving = false;
//            return true;
//        }

//        @Override
//        public boolean onContactAdded(btManifoldPoint cp, btCollisionObjectWrapper colObj0Wrap, int partId0, int index0, btCollisionObjectWrapper colObj1Wrap, int partId1, int index1) {
////            parts.get(colObj0Wrap.getCollisionObject().getUserValue())
//        }
    }
}
