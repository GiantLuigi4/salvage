package com.salvagers.client.utils.rendering;

import com.salvagers.client.Window;
import com.salvagers.client.utils.rendering.renderables.*;
import com.salvagers.client.utils.rendering.textures.Storage;
import com.salvagers.common.Vector3D;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import static com.salvagers.client.Window.fb;
import static org.lwjgl.opengl.GL11.*;

public class RenderHelper {
	public static void draw(Renderable renderable) {
		glPushMatrix();
		glLoadMatrixf(Window.viewMatrix.mul(renderable.matrix, Window.modelViewMatrix).get(fb));
		
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_NORMALIZE);
		glDepthFunc(GL_LEQUAL);
		
		drawWithoutSetup(renderable, true);
		
		glPopMatrix();
	}
	
	public static void setupColor(Renderable renderable, float r, float g, float b, float a) {
		if (renderable instanceof RenderableRectangle) {
			Vector3D corner1 = ((RenderableRectangle) renderable).corner1.getTransformedInstance(renderable.matrix);
			Vector3D corner2 = ((RenderableRectangle) renderable).corner2.getTransformedInstance(renderable.matrix);
			Vector3D corner3 = ((RenderableRectangle) renderable).corner3.getTransformedInstance(renderable.matrix);
			
			//https://www.khronos.org/opengl/wiki/Calculating_a_Surface_Normal
			Vector3D u = corner1.subtract(corner2);
			Vector3D v = corner1.subtract(corner3);
			
			Vector3D normal = new Vector3D(0, 0, 0);
			normal.x = (u.y * v.z) - (u.z * v.y);
			normal.y = (u.z * v.x) - (u.x * v.z);
			normal.z = (u.x * v.y) - (u.y * v.x);
			
			double val = normal.toVector3().absolute().mul(-1).dot(new Vector3f(0, 1, 1).normalize());
			if (Double.isNaN(val)) {
				val = 1;
			}
			val = val % 1;
			val = ((val / 2f) - 0.5f) % 1.01f;
			val = Math.abs(val);
			glColor4f(r * (float) val, g * (float) val, b * (float) val, a);
		} else {
			glColor4f(r, g, b, a);
		}
	}
	
	public static void drawColor(Renderable renderable, float r, float g, float b, float a) {
		setupColor(renderable, r, g, b, a);
		draw(renderable);
	}
	
	public static void drawWithoutSetup(Renderable renderable, boolean isMatrixSet) {
		if (!isMatrixSet) {
			glPushMatrix();
			glLoadMatrixf(Window.viewMatrix.mul(new Matrix4f(), Window.modelViewMatrix).get(fb));
			
			glEnable(GL_CULL_FACE);
			glEnable(GL_DEPTH_TEST);
			glEnable(GL_NORMALIZE);
			glDepthFunc(GL_LEQUAL);
		}
		
		if (renderable instanceof RenderableLine) {
			glBegin(GL20.GL_LINES);
			glVertex3d(((RenderableLine) renderable).line.start.x, ((RenderableLine) renderable).line.start.y, ((RenderableLine) renderable).line.start.z);
			glVertex3d(((RenderableLine) renderable).line.end.x, ((RenderableLine) renderable).line.end.y, ((RenderableLine) renderable).line.end.z);
			glEnd();
		} else if (renderable instanceof RenderableRectangle) {
			if (renderable instanceof TexturedRectangle) {
				glEnable(GL_TEXTURE_2D);
				glBindTexture(GL11.GL_TEXTURE_2D, Storage.getOrGenerate(((TexturedRectangle) renderable).texture));
			}
			glBegin(GL_POLYGON);
			if (renderable instanceof TexturedRectangle) {
				glTexCoord3d(((TexturedRectangle) renderable).tex1.x, ((TexturedRectangle) renderable).tex1.y, ((TexturedRectangle) renderable).tex1.z);
				glVertex3d(((RenderableRectangle) renderable).corner1.x, ((RenderableRectangle) renderable).corner1.y, ((RenderableRectangle) renderable).corner1.z);
				glTexCoord3d(((TexturedRectangle) renderable).tex2.x, ((TexturedRectangle) renderable).tex2.y, ((TexturedRectangle) renderable).tex2.z);
				glVertex3d(((RenderableRectangle) renderable).corner2.x, ((RenderableRectangle) renderable).corner2.y, ((RenderableRectangle) renderable).corner2.z);
				glTexCoord3d(((TexturedRectangle) renderable).tex3.x, ((TexturedRectangle) renderable).tex3.y, ((TexturedRectangle) renderable).tex3.z);
				glVertex3d(((RenderableRectangle) renderable).corner3.x, ((RenderableRectangle) renderable).corner3.y, ((RenderableRectangle) renderable).corner3.z);
				glTexCoord3d(((TexturedRectangle) renderable).tex4.x, ((TexturedRectangle) renderable).tex4.y, ((TexturedRectangle) renderable).tex4.z);
			} else {
				glVertex3d(((RenderableRectangle) renderable).corner1.x, ((RenderableRectangle) renderable).corner1.y, ((RenderableRectangle) renderable).corner1.z);
				glVertex3d(((RenderableRectangle) renderable).corner2.x, ((RenderableRectangle) renderable).corner2.y, ((RenderableRectangle) renderable).corner2.z);
				glVertex3d(((RenderableRectangle) renderable).corner3.x, ((RenderableRectangle) renderable).corner3.y, ((RenderableRectangle) renderable).corner3.z);
			}
			glVertex3d(((RenderableRectangle) renderable).corner4.x, ((RenderableRectangle) renderable).corner4.y, ((RenderableRectangle) renderable).corner4.z);
			glEnd();
			if (renderable instanceof TexturedRectangle)
				glDisable(GL_TEXTURE_2D);
		} else if (renderable instanceof RenderableCollection) {
			if (((RenderableCollection) renderable).useChildTransforms)
				for (Renderable renderable1 : ((RenderableCollection) renderable).renderables) {
					if (renderable1.color != null)
						drawColor(renderable1, (float) renderable1.color.x / 255f, (float) renderable1.color.y / 255f, (float) renderable1.color.z / 255f, 1);
					else draw(renderable1);
				}
			else
				for (Renderable renderable1 : ((RenderableCollection) renderable).renderables) {
					if (renderable1.color != null)
						drawColorWithoutSetup(renderable1, (float) renderable1.color.x / 255f, (float) renderable1.color.y / 255f, (float) renderable1.color.z / 255f, 1, true);
					else drawWithoutSetup(renderable1, true);
				}
		}
		if (!isMatrixSet)
			glPopMatrix();
	}
	
	public static void drawColorWithoutSetup(Renderable renderable, float r, float g, float b, int a, boolean isMatrixSet) {
		setupColor(renderable, r, g, b, a);
		drawWithoutSetup(renderable, isMatrixSet);
	}
}
