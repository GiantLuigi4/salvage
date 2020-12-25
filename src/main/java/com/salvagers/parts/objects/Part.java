package com.salvagers.parts.objects;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.salvagers.client.utils.rendering.matrix.MatrixStack;

import javax.vecmath.Vector3f;

public abstract class Part {
    public float weight;
    public float friction;
    public float size;
    public CollisionShape body = null;
    public RigidBody collisionBody;
    public Vector3f pos = new Vector3f();
    
    public Part(float weight, float friction, float size) {
        this.weight = weight;
        this.friction = friction;
        this.size = size;
    }
    
    public abstract void render(MatrixStack stack);
    
    public Part setDefaultPos(double x, double y, double z) {
        this.pos.x = (float)x;
        this.pos.y = (float)y;
        this.pos.z = (float)z;
        return this;
    }
}
