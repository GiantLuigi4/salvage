package com.salvagers.client.utils.rendering.matrix;

public class MatrixEntry {
	public final double x, y, z, angle, rotX, rotY, rotZ, scaleX, scaleY, scaleZ;
	
	public MatrixEntry[] previous;
	
	public MatrixEntry(double x, double y, double z, double angle, double rotX, double rotY, double rotZ, double scaleX, double scaleY, double scaleZ) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.angle = angle;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.scaleZ = scaleZ;
	}
	
	public MatrixEntry with(MatrixEntry[] toArray) {
		previous = toArray;
		return this;
	}
}
