package com.salvagers.parts.objects;

import com.salvagers.client.utils.rendering.matrix.MatrixStack;
import net.smert.jreactphysics3d.body.CollisionBody;
import net.smert.jreactphysics3d.collision.shapes.CollisionShape;

import net.smert.jreactphysics3d.mathematics.Quaternion;
import net.smert.jreactphysics3d.mathematics.Transform;
import net.smert.jreactphysics3d.mathematics.Vector3;

public abstract class Part {
    public float weight;
    public float friction;
    public float size;
    public CollisionShape body = null;
    public Vector3 lastPosition = new Vector3(0,0,0);
    public Quaternion lastRotation = new Quaternion(0,0,0,0);
    public float lastRefresh = 0;
    public CollisionBody collisionBody;
    
    public Part(float weight, float friction, float size) {
        this.weight = weight;
        this.friction = friction;
        this.size = size;
    }
    
    public abstract void render(MatrixStack stack);
}
