package com.salvagers.common;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.vecmath.Quat4f;

public class Quaternion {
	public double x, y, z, w;
	
	public Quaternion(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Quaternion(double x, double y, double z) {
		Quaternionf quaternion = new Quaternionf(0, 0, 0, 0).rotationXYZ((float) x, (float) y, (float) z);
		this.x = quaternion.x;
		this.y = quaternion.y;
		this.z = quaternion.z;
		this.w = quaternion.w;
	}
	
	public Quaternion(Quat4f quaternion) {
		this.x = quaternion.x;
		this.y = quaternion.y;
		this.z = quaternion.z;
		this.w = quaternion.w;
	}
	
	public Vector3f rotate(Vector3f input) {
		Quaternionf quaternion = new Quaternionf(x, y, z, w);
		return quaternion.transform(input);
	}
	
	public javax.vecmath.Vector3f rotate(javax.vecmath.Vector3f input) {
		Quaternionf quaternion = new Quaternionf(x, y, z, w);
		return new Vector3D(quaternion.transform(new Vector3D(input).toVector3())).toVector3Javax();
	}
	
	public Quat4f toQuat4f() {
		return new Quat4f((float) x, (float) y, (float) z, (float) w);
	}
	
	public Quaternionf toQuaternionf() {
		return new Quaternionf((float) x, (float) y, (float) z, (float) w);
	}
}
