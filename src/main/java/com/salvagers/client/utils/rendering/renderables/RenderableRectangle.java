package com.salvagers.client.utils.rendering.renderables;

import com.salvagers.common.Vector3D;
import org.joml.Matrix4f;

public class RenderableRectangle extends Renderable {
	public final Vector3D corner1;
	public final Vector3D corner2;
	public final Vector3D corner3;
	public final Vector3D corner4;
	
	public RenderableRectangle(Matrix4f matrix, Vector3D corner1, Vector3D corner2, Vector3D corner3, Vector3D corner4) {
		super(matrix);
		this.corner1 = corner1;
		this.corner2 = corner2;
		this.corner3 = corner3;
		this.corner4 = corner4;
	}
}
