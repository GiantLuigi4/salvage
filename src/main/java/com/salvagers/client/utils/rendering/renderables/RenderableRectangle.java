package com.salvagers.client.utils.rendering.renderables;

import com.salvagers.client.utils.rendering.matrix.MatrixEntry;
import com.salvagers.common.Line3D;
import com.salvagers.common.Vector3D;

public class RenderableRectangle extends Renderable {
	public final Vector3D corner1;
	public final Vector3D corner2;
	public final Vector3D corner3;
	public final Vector3D corner4;
	
	public RenderableRectangle(MatrixEntry entry, Vector3D corner1, Vector3D corner2, Vector3D corner3, Vector3D corner4) {
		super(entry);
		this.corner1 = corner1;
		this.corner2 = corner2;
		this.corner3 = corner3;
		this.corner4 = corner4;
	}
}
