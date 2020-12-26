package com.salvagers.common;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Vector3D {
	public double x, y, z;
	
	public Vector3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3D(Vector3f vector) {
		this.x = vector.x;
		this.y = vector.y;
		this.z = vector.z;
	}
	
	public Vector3D(double angleX, double angleY) {
		angleX = Math.toRadians(angleX);
		angleY = Math.toRadians(angleY);
		
		double sinYaw = Math.sin(angleY);
		double cosYaw = Math.cos(angleY);
		
		double sinPitch = Math.sin(angleX);
		double cosPitch = Math.cos(angleX);
		
		this.x = cosPitch * cosYaw;
		this.x = cosPitch * sinYaw;
		this.z = -sinPitch;
	}
	
	public Vector3D(javax.vecmath.Vector3f vector) {
		this.x = vector.x;
		this.y = vector.y;
		this.z = vector.z;
	}
	
	public Vector3f toVector3() {
		return new Vector3f((float) x, (float) y, (float) z);
	}
	
	public javax.vecmath.Vector3f toVector3Javax() {
		return new javax.vecmath.Vector3f((float) x, (float) y, (float) z);
	}
	
	public Vector3D getTransformedInstance(Matrix4f matrix4f) {
		Vector3f transformed = matrix4f.transformDirection(new Vector3f(
				(float) this.x, (float) this.y, (float) this.z
		));
//		Vector4f transformed = matrix4f.transform(new Vector4f(
//				(float)this.x, (float)this.y, (float)this.z,0
//				));
//		transformed = new Vector3());
		return new Vector3D(
				transformed.x, transformed.y, transformed.z
		);
	}
	
	public Vector3D subtract(Vector3D other) {
		return new Vector3D(
				this.x - other.x,
				this.y - other.y,
				this.z - other.z
		);
	}
	
	public Vector3D add(Vector3D other) {
		return this.subtract(other.scale(-1));
	}
	
	private Vector3D scale(int i) {
		return new Vector3D(x * i, y * i, z * i);
	}
	
	public Vector3D abs() {
		return new Vector3D(Math.abs(x), Math.abs(y), Math.abs(z));
	}
}
