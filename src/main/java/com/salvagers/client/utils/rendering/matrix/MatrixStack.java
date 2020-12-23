package com.salvagers.client.utils.rendering.matrix;

import net.smert.jreactphysics3d.mathematics.Quaternion;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import java.util.ArrayList;

public class MatrixStack {
	Matrix4f current = new Matrix4f();
	public ArrayList<Matrix4f> entries = new ArrayList<>();
	
	public MatrixStack() {
//		entries.add(new MatrixEntry(0,0,0,0,0,0,0,1,1,1));
	}
	
	public void moveTo(double x, double y, double z) {
		current.translation((float)x,(float)y,(float)z);
	}
	
	public void translate(double x, double y, double z) {
//		entries.add(new MatrixEntry(x, y, z, 0, 0, 0, 0, 1, 1, 1));
		current.translate((float)x,(float)y,(float)z);
	}
	
	public void rotate(double angle, double x, double y, double z) {
//		entries.add(new MatrixEntry(0, 0, 0, angle, x, y, z, 1, 1, 1));
		current.rotate((float)Math.toRadians(angle),(float)x,(float)y,(float)z);
	}
	
	public void rotate(Quaternion qt) {
//		entries.add(new MatrixEntry(0, 0, 0, angle, x, y, z, 1, 1, 1));
		if (Float.isNaN(qt.getX()))
			return;
		current.rotate(new Quaternionf(
				(qt.getX()),
				(qt.getY()),
				(qt.getZ()),
				(qt.getW())
		));
	}
	
	public void rotate(Quaternionf qt) {
//		entries.add(new MatrixEntry(0, 0, 0, angle, x, y, z, 1, 1, 1));
		current.rotate(qt);
	}
	
	public void push() {
//		pushPositions.add(entries.size());
		entries.add(new Matrix4f(current));
	}
	
	public void pop() {
//		while (entries.size() > pushPositions.get(pushPositions.size()-1)) {
//			entries.remove(entries.size()-1);
//		}
//		pushPositions.remove(pushPositions.size()-1);
		current = entries.remove(entries.size()-1);
	}
	
	public void scale(double x, double y, double z) {
//		entries.add(new MatrixEntry(0, 0, 0, 0, 0, 0, 0, x, y, z));
		current.scale((float)x,(float)y,(float)z);
	}
	
//	public MatrixEntry getLast() {
	public Matrix4f getLast() {
		return current;
//		return entries.get(entries.size() - 1).with(entries.toArray(new MatrixEntry[0]));
	}
}
