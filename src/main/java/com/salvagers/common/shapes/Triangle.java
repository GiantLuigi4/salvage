package com.salvagers.common.shapes;

import com.salvagers.common.Vector3D;

import javax.vecmath.Vector3f;

public class Triangle {
	public Vector3f corner1;
	public Vector3f corner2;
	public Vector3f corner3;
	
	public Triangle(Vector3f corner1, Vector3f corner2, Vector3f corner3) {
		this.corner1 = corner1;
		this.corner2 = corner2;
		this.corner3 = corner3;
	}
	
	public Triangle moveTo0() {
		Vector3D min = new Vector3D(
				Math.min(corner1.x, Math.min(corner2.x, corner3.x)),
				Math.min(corner1.y, Math.min(corner2.y, corner3.y)),
				Math.min(corner1.z, Math.min(corner2.z, corner3.z))
		);
		return new Triangle(
				new Vector3D(corner1).subtract(min).toVector3Javax(),
				new Vector3D(corner2).subtract(min).toVector3Javax(),
				new Vector3D(corner3).subtract(min).toVector3Javax()
		);
	}
	
	public Vector3f getMinVector() {
		Vector3D min = new Vector3D(
				Math.min(corner1.x, Math.min(corner2.x, corner3.x)),
				Math.min(corner1.y, Math.min(corner2.y, corner3.y)),
				Math.min(corner1.z, Math.min(corner2.z, corner3.z))
		);
		return min.toVector3Javax();
	}
}
