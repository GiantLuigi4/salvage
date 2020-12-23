package com.salvagers.client.utils.rendering.renderables;

import com.salvagers.client.utils.rendering.matrix.MatrixEntry;
import com.salvagers.common.Line3D;
import com.salvagers.common.Vector3D;

public class RenderableLine extends Renderable {
	public final Line3D line;
	
	public RenderableLine(MatrixEntry entry, Line3D line) {
		super(entry);
		this.line = line;
	}
	
	public RenderableLine(MatrixEntry entry, Vector3D start, Vector3D end) {
		super(entry);
		this.line = new Line3D(start,end);
	}
}
