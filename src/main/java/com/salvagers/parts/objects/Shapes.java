package com.salvagers.parts.objects;

import com.bulletphysics.collision.shapes.*;
import com.bulletphysics.linearmath.Transform;
import com.salvagers.common.ListHelper;
import com.salvagers.common.shapes.Triangle;

import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Shapes {
	/**
	 * creates a sphere collision shape from the given radius
	 *
	 * @param radius the radius of the sphere
	 * @return the sphere collision shape
	 */
	public static CollisionShape getSphere(float radius) {
		return new SphereShape(radius);
	}
	
	/**
	 * creates a capsule collision shape from the given radius and height
	 *
	 * @param radius the radius of the capsule
	 * @param height the height of the capsule
	 * @return the capsule collision shape
	 */
	public static CollisionShape getCapsule(float radius, float height) {
		return new CapsuleShape(radius, height);
	}
	
	/**
	 * creates a cylinder collision shape from the given radius and height
	 *
	 * @param radius the radius of the cylinder
	 * @param height the height of the cylinder
	 * @return the cylinder collision shape
	 */
	public static CollisionShape getCylinder(float radius, float height) {
		return new CylinderShape(new Vector3f(radius, height, radius));
	}
	
	/**
	 * creates a box collision shape from the given width, height, and length
	 *
	 * @param width  the width of the box
	 * @param height the height of the box
	 * @param length the length of the box
	 * @return the box collision shape
	 */
	public static CollisionShape getBox(float width, float height, float length) {
		return new BoxShape(new Vector3f(width, height, length));
	}
	
	/**
	 * creates a triangle collision shape
	 *
	 * @param triangle the triangle
	 * @return the collision shape
	 */
	public static CollisionShape getTriangle(Triangle triangle) {
		return new TriangleShape(triangle.corner1, triangle.corner2, triangle.corner3);
	}
	
	/**
	 * creates a collision shape from the given vectors
	 *
	 * @param vector3fs the vectors of the shape
	 * @return the collision shape
	 */
	public static CollisionShape create(Collection<Vector3f> vector3fs) {
		return new ConvexHullShape(ListHelper.toObjectArrayList(vector3fs));
	}
	
	/**
	 * super laggy, creates a collision shape which is a combination of many collision shapes
	 *
	 * @param vector3fs the vectors of the shapes
	 * @return the collision shape
	 */
	public static CollisionShape createComplex(ArrayList<Vector3f[]> vector3fs) {
		CompoundShape shape = new CompoundShape();
		for (Vector3f[] vector3fs1 : vector3fs) {
			if (vector3fs1.length == 3) {
				shape.addChildShape(new Transform(), getTriangle(new Triangle(vector3fs1[0], vector3fs1[1], vector3fs1[2])));
			} else {
				shape.addChildShape(new Transform(), create(Arrays.asList(vector3fs1)));
			}
		}
		return shape;
	}
}
