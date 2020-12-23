package com.salvagers.parts.objects;

import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.salvagers.client.utils.rendering.matrix.MatrixStack;

public abstract class Part {
    public float weight;
    public float friction;
    public float size;
    public btRigidBody body = null;
    public boolean isMoving = false;

    public Part(float weight, float friction, float size) {
        this.weight = weight;
        this.friction = friction;
        this.size = size;
    }
    
    public abstract void render(MatrixStack stack);
    
    public abstract void dispose();
}
