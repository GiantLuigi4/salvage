package com.salvagers.client.utils.rendering.matrix;

import java.util.ArrayList;

public class MatrixStack {
	public ArrayList<MatrixEntry> entries = new ArrayList<>();
	
	public MatrixStack() {
		entries.add(new MatrixEntry(0,0,0,0,0,0,0,1,1,1));
	}
	
	public void translate(double x, double y, double z) {
		entries.add(new MatrixEntry(x, y, z, 0, 0, 0, 0, 1, 1, 1));
	}
	
	public void rotate(double angle, double x, double y, double z) {
		entries.add(new MatrixEntry(0, 0, 0, angle, x, y, z, 1, 1, 1));
	}
	
	public void scale(double x, double y, double z) {
		entries.add(new MatrixEntry(0, 0, 0, 0, 0, 0, 0, x, y, z));
	}
	
	public MatrixEntry getLast() {
		return entries.get(entries.size() - 1).with(entries.toArray(new MatrixEntry[0]));
	}
}
