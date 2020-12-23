package com.salvagers.parts.objects;

import net.smert.jreactphysics3d.collision.shapes.CollisionShape;
import net.smert.jreactphysics3d.collision.shapes.CylinderShape;
import net.smert.jreactphysics3d.collision.shapes.SphereShape;

public class Shapes {
	public static CollisionShape getShape(String type) {
		if (type.equals("sphere")) {
			return new SphereShape(1,1);
		} else if (type.equals("cylinder")) {
			return new CylinderShape(1,1,1);
		}
		return null;
	}
}
