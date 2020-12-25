package com.salvagers.parts.objects;

import com.bulletphysics.collision.shapes.*;

import javax.vecmath.Vector3f;

public class Shapes {
	public static CollisionShape getSphere(float radius) {
		return new SphereShape(radius);
	}
	
	public static CollisionShape getSphere(float radius, float height) {
		return new CapsuleShape(radius, height);
	}
	
	public static CollisionShape getCylinder(float radius, float height) {
		return new CylinderShape(new Vector3f(radius, height, radius));
	}
	
	public static CollisionShape getBox(float width, float height, float length) {
		return new BoxShape(new Vector3f(width, height, length));
	}
}
