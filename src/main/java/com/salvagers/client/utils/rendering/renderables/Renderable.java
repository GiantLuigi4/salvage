package com.salvagers.client.utils.rendering.renderables;

import com.salvagers.client.utils.rendering.matrix.MatrixEntry;
import org.joml.Matrix4f;

public class Renderable {
	public final Matrix4f entry;
	
	public Renderable(Matrix4f entry) {
		this.entry = entry;
	}
}
