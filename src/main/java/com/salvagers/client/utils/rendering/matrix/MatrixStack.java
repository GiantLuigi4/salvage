package com.salvagers.client.utils.rendering.matrix;

import org.joml.Matrix4f;
import org.joml.Quaternionf;

import javax.vecmath.Quat4f;
import java.util.ArrayList;

public class MatrixStack {
	Matrix4f current = new Matrix4f();
	public ArrayList<Matrix4f> entries = new ArrayList<>();
	
	public MatrixStack() {
	}
	
	public void moveTo(double x, double y, double z) {
		current.translation((float)x,(float)y,(float)z);
	}
	
	public void translate(double x, double y, double z) {
		current.translate((float)x,(float)y,(float)z);
	}
	
	public void rotate(double angle, double x, double y, double z) {
		current.rotate((float)Math.toRadians(angle),(float)x,(float)y,(float)z);
	}
	
	public void rotate(Quat4f qt) {
		if (Float.isNaN(qt.x))
			return;
		current.rotate(new Quaternionf(
				(qt.x),
				(qt.y),
				(qt.z),
				(qt.w)
		));
	}
	
	public void rotate(Quaternionf qt) {
		current.rotate(qt);
	}
	
	public void push() {
		entries.add(new Matrix4f(current));
	}
	
	public void pop() {
		current = entries.remove(entries.size()-1);
	}
	
	public void scale(double x, double y, double z) {
		current.scale((float)x,(float)y,(float)z);
	}
	
	public Matrix4f getLast() {
		return current;
	}
}
