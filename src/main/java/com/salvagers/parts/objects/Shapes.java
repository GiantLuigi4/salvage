package com.salvagers.parts.objects;

import net.smert.jreactphysics3d.collision.shapes.BoxShape;
import net.smert.jreactphysics3d.collision.shapes.CollisionShape;
import net.smert.jreactphysics3d.collision.shapes.CylinderShape;
import net.smert.jreactphysics3d.collision.shapes.SphereShape;
import net.smert.jreactphysics3d.mathematics.Vector3;

public class Shapes {
	public static CollisionShape getSphere(float radius, float margin) {
		return new SphereShape(radius, margin);
	}
	
	public static CollisionShape getCylinder(float radius, float height, float margin) {
		return new CylinderShape(radius, height, margin);
	}
	
	public static CollisionShape getBox(float width, float height, float length, float margin) {
		return new BoxShape(new Vector3(width, height, length), margin);
	}
}
