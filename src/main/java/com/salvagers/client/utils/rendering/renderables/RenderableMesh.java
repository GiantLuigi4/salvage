package com.salvagers.client.utils.rendering.renderables;

import com.salvagers.common.Vector3D;
import org.joml.Matrix4f;

import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.Collection;

public class RenderableMesh extends Renderable {
	public Collection<Vector3D> vector3DS = new ArrayList<>();
	
	public RenderableMesh(Matrix4f matrix) {
		super(matrix);
	}
	
	public RenderableMesh(Matrix4f matrix, Collection<Vector3f> vector3fs, boolean guessJavaIsBeingDumbSoThisIsHere) {
		super(matrix);
		for (Vector3f vector3f : vector3fs) {
			vector3DS.add(new Vector3D(vector3f));
		}
	}
	
	public RenderableMesh(Matrix4f matrix, Collection<Vector3D> vector3DS) {
		super(matrix);
		this.vector3DS = vector3DS;
	}
}
