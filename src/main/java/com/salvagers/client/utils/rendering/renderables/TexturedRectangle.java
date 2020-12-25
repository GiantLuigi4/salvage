package com.salvagers.client.utils.rendering.renderables;

import com.salvagers.common.Vector3D;
import org.joml.Matrix4f;

public class TexturedRectangle extends RenderableRectangle {
	//Doing it this way instead of storing the integer so that it's reloadable
	public final String texture;
	
	public final Vector3D tex1,tex2,tex3,tex4;
	
	public TexturedRectangle(Matrix4f entry, Vector3D corner1, Vector3D corner2, Vector3D corner3, Vector3D corner4, String texture, Vector3D tex1, Vector3D tex2, Vector3D tex3, Vector3D tex4) {
		super(entry, corner1, corner2, corner3, corner4);
		this.texture = texture;
		this.tex1 = tex1;
		this.tex2 = tex2;
		this.tex3 = tex3;
		this.tex4 = tex4;
	}
}
