package com.salvagers.client.utils.rendering.renderables;

import com.salvagers.common.Vector3D;
import com.salvagers.common.shapes.Triangle;
import org.joml.Matrix4f;

public class RenderableTriangle extends Renderable {
	public final Vector3D corner1;
	public final Vector3D corner2;
	public final Vector3D corner3;
	
	public RenderableTriangle(Matrix4f matrix, Vector3D corner1, Vector3D corner2, Vector3D corner3) {
		super(matrix);
		this.corner1 = corner1;
		this.corner2 = corner2;
		this.corner3 = corner3;
	}
	
	public RenderableTriangle(Matrix4f matrix, Triangle shape) {
		super(matrix);
		this.corner1 = new Vector3D(shape.corner1);
		this.corner2 = new Vector3D(shape.corner2);
		this.corner3 = new Vector3D(shape.corner3);
	}
}
