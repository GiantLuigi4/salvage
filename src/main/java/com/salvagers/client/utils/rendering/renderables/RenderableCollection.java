package com.salvagers.client.utils.rendering.renderables;

import org.joml.Matrix4f;

import java.util.Collection;

public class RenderableCollection extends Renderable {
	//Using this results in a less optimized render method
	public boolean useChildTransforms = false;
	public final Renderable[] renderables;
	
	public RenderableCollection(Matrix4f matrix, Renderable[] renderables) {
		super(matrix);
		this.renderables = renderables;
	}
	
	public RenderableCollection(Matrix4f matrix, Collection<Renderable> renderables) {
		super(matrix);
		this.renderables = renderables.toArray(new Renderable[0]);
	}
}
