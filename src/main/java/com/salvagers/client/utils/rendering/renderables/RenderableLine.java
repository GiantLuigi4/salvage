package com.salvagers.client.utils.rendering.renderables;

import com.salvagers.common.Line3D;
import com.salvagers.common.Vector3D;
import org.joml.Matrix4f;

public class RenderableLine extends Renderable {
	public final Line3D line;
	
	public RenderableLine(Matrix4f matrix, Line3D line) {
		super(matrix);
		this.line = line;
	}
	
	public RenderableLine(Matrix4f matrix, Vector3D start, Vector3D end) {
		super(matrix);
		this.line = new Line3D(start,end);
	}
}
