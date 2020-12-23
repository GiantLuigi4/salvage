package com.salvagers.client.utils.rendering;

import com.salvagers.client.Window;
import com.salvagers.client.utils.rendering.matrix.MatrixEntry;
import com.salvagers.client.utils.rendering.renderables.Renderable;
import com.salvagers.client.utils.rendering.renderables.RenderableLine;
import com.salvagers.client.utils.rendering.renderables.RenderableRectangle;
import org.lwjgl.opengl.GL20;

import static com.salvagers.client.Window.fb;
import static org.lwjgl.opengl.GL11.*;

public class RenderHelper {
	public static void draw(Renderable renderable) {
		glPushMatrix();
//		for (MatrixEntry entry : renderable.entry.previous) {
//			glTranslated(entry.x, -entry.y, entry.z);
//			glRotated(entry.angle, entry.rotX, entry.rotY, entry.rotZ);
//			glScaled(entry.scaleX, entry.scaleY, entry.scaleZ);
//		}
//		{
//			MatrixEntry entry = renderable.entry;
//			glTranslated(entry.x, -entry.y, entry.z);
//			glRotated(entry.angle, entry.rotX, entry.rotY, entry.rotZ);
//			glScaled(entry.scaleX, entry.scaleY, entry.scaleZ);
//		}
		glLoadMatrixf(Window.viewMatrix.mul(renderable.entry, Window.modelViewMatrix).get(fb));
		
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_NORMALIZE);
		glDepthFunc(GL_LEQUAL);
		
		if (renderable instanceof RenderableLine) {
			glBegin(GL20.GL_LINES);
			glVertex3d(((RenderableLine) renderable).line.start.x, ((RenderableLine) renderable).line.start.y, ((RenderableLine) renderable).line.start.z);
			glVertex3d(((RenderableLine) renderable).line.end.x, ((RenderableLine) renderable).line.end.y, ((RenderableLine) renderable).line.end.z);
			glEnd();
		} else if (renderable instanceof RenderableRectangle) {
			glBegin(GL_POLYGON);
			glVertex3d(((RenderableRectangle) renderable).corner1.x, ((RenderableRectangle) renderable).corner1.y, ((RenderableRectangle) renderable).corner1.z);
			glVertex3d(((RenderableRectangle) renderable).corner2.x, ((RenderableRectangle) renderable).corner2.y, ((RenderableRectangle) renderable).corner2.z);
			glVertex3d(((RenderableRectangle) renderable).corner3.x, ((RenderableRectangle) renderable).corner3.y, ((RenderableRectangle) renderable).corner3.z);
			glVertex3d(((RenderableRectangle) renderable).corner4.x, ((RenderableRectangle) renderable).corner4.y, ((RenderableRectangle) renderable).corner4.z);
			glEnd();
		}
		
		glPopMatrix();
	}
	
	public static void drawColor(Renderable renderable, float r, float g, float b, float a) {
		glColor4f(r,g,b,a);
		draw(renderable);
	}
}
