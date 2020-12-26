package com.salvagers.client.utils.rendering.renderables;

import com.salvagers.common.Vector3D;
import com.salvagers.common.shapes.Triangle;
import org.joml.Matrix4f;

public class TexturedTriangle extends RenderableTriangle {
	//Doing it this way instead of storing the integer so that it's reloadable
	public final String texture;
	
	public final Vector3D tex1, tex2, tex3;
	
	public TexturedTriangle(Matrix4f matrix, Vector3D corner1, Vector3D corner2, Vector3D corner3, String texture, Vector3D tex1, Vector3D tex2, Vector3D tex3) {
		super(matrix, corner1, corner2, corner3);
		this.texture = texture;
		this.tex1 = tex1;
		this.tex2 = tex2;
		this.tex3 = tex3;
	}
	
	public TexturedTriangle(Matrix4f matrix, Triangle shape, String texture, Vector3D tex1, Vector3D tex2, Vector3D tex3) {
		super(matrix, shape);
		this.texture = texture;
		this.tex1 = tex1;
		this.tex2 = tex2;
		this.tex3 = tex3;
	}
	
	public TexturedTriangle(Matrix4f matrix, Triangle shape, String texture, Triangle textureCoords) {
		super(matrix, shape);
		this.texture = texture;
		this.tex1 = new Vector3D(textureCoords.corner1);
		this.tex2 = new Vector3D(textureCoords.corner2);
		this.tex3 = new Vector3D(textureCoords.corner3);
	}
}
