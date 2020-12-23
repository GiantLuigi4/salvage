package com.salvagers.parts.objects;

import com.salvagers.client.utils.rendering.matrix.MatrixStack;
import net.smert.jreactphysics3d.body.CollisionBody;
import net.smert.jreactphysics3d.collision.shapes.CollisionShape;

public abstract class Part {
    public float weight;
    public float friction;
    public float size;
    public CollisionShape body = null;
    public boolean isMoving = false;
    public CollisionBody collisionBody;
    
    public Part(float weight, float friction, float size) {
        this.weight = weight;
        this.friction = friction;
        this.size = size;
    }
    
    public abstract void render(MatrixStack stack);
}
