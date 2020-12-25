package com.salvagers.client.utils.rendering.renderables;

import com.salvagers.common.Vector3D;
import org.joml.Matrix4f;

public class Renderable {
	public Matrix4f matrix;
	public Vector3D color = null;
	
	public Renderable(Matrix4f matrix) {
		this.matrix = matrix;
	}
}
