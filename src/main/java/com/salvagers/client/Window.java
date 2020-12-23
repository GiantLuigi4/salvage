package com.salvagers.client;

import com.salvagers.client.utils.rendering.RenderHelper;
import com.salvagers.client.utils.rendering.matrix.MatrixStack;
import com.salvagers.client.utils.rendering.renderables.RenderableLine;
import com.salvagers.client.utils.rendering.renderables.RenderableRectangle;
import com.salvagers.common.Vector3D;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.io.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
	private static long window;
	
	public static Matrix4f projMatrix = new Matrix4f();
	public static Matrix4f viewMatrix = new Matrix4f();
	public static Matrix4f modelMatrix = new Matrix4f();
	public static Matrix4f modelViewMatrix = new Matrix4f();
	public static FloatBuffer fb = BufferUtils.createFloatBuffer(16);
	
	public static void main(String[] args) {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");
		
		init();
		loop();
		
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
	static void renderCube() {
		glBegin(GL_QUADS);
		glColor3f(   0.0f,  0.0f,  0.2f );
		glVertex3f(  0.5f, -0.5f, -0.5f );
		glVertex3f( -0.5f, -0.5f, -0.5f );
		glVertex3f( -0.5f,  0.5f, -0.5f );
		glVertex3f(  0.5f,  0.5f, -0.5f );
		glColor3f(   0.0f,  0.0f,  1.0f );
		glVertex3f(  0.5f, -0.5f,  0.5f );
		glVertex3f(  0.5f,  0.5f,  0.5f );
		glVertex3f( -0.5f,  0.5f,  0.5f );
		glVertex3f( -0.5f, -0.5f,  0.5f );
		glColor3f(   1.0f,  0.0f,  0.0f );
		glVertex3f(  0.5f, -0.5f, -0.5f );
		glVertex3f(  0.5f,  0.5f, -0.5f );
		glVertex3f(  0.5f,  0.5f,  0.5f );
		glVertex3f(  0.5f, -0.5f,  0.5f );
		glColor3f(   0.2f,  0.0f,  0.0f );
		glVertex3f( -0.5f, -0.5f,  0.5f );
		glVertex3f( -0.5f,  0.5f,  0.5f );
		glVertex3f( -0.5f,  0.5f, -0.5f );
		glVertex3f( -0.5f, -0.5f, -0.5f );
		glColor3f(   0.0f,  1.0f,  0.0f );
		glVertex3f(  0.5f,  0.5f,  0.5f );
		glVertex3f(  0.5f,  0.5f, -0.5f );
		glVertex3f( -0.5f,  0.5f, -0.5f );
		glVertex3f( -0.5f,  0.5f,  0.5f );
		glColor3f(   0.0f,  0.2f,  0.0f );
		glVertex3f(  0.5f, -0.5f, -0.5f );
		glVertex3f(  0.5f, -0.5f,  0.5f );
		glVertex3f( -0.5f, -0.5f,  0.5f );
		glVertex3f( -0.5f, -0.5f, -0.5f );
		glEnd();
	}
	
	public static void loop() {
		GL.createCapabilities();
		
		glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
		
		int rotation = 0;
		
		
		while (!glfwWindowShouldClose(window)) {
			MatrixStack stack = new MatrixStack();
			setupView(stack, rotation++);
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			render(rotation,stack);
			
			glfwSwapBuffers(window);
			
			glfwPollEvents();
		}
	}
	
	public static void setupView(MatrixStack stack, float rotation) {
		int[] sizeX = new int[1];
		int[] sizeY = new int[1];
		glfwGetWindowSize(window,sizeX, sizeY);
		int width = sizeX[0];
		int height = sizeY[0];
		glViewport(0, 0, width, height);
		
		projMatrix.setPerspective((float) Math.toRadians(40),
				(float)width/height, 0.01f, 100.0f);
		glMatrixMode(GL_PROJECTION);
		glLoadMatrixf(projMatrix.get(fb));
		
		viewMatrix.setLookAt(0.0f, 0.0f, 10.0f,
				0.0f, 0.0f, 0.0f,
				0.0f, 1, 0.0f);
		viewMatrix.translate(0,0,0);
		viewMatrix.rotate((float) Math.toRadians(0),1,0,0);
		glMatrixMode(GL_MODELVIEW);
		
		stack.translate(0,-5,0);
		stack.rotate(rotation,0,1,0);
		stack.translate(-0.5f,0,0);
		stack.rotate(0,1,0,0);
//		stack.translate(2,0,0);
//		stack.scale(0.01f,0.01f,0.01f);
	}
	
	//Keep this for loading icon thing
	public static void render(float rotation, MatrixStack stack) {
		for (int x = -16; x <= 16; x++) {
			for (int z = -16; z <= 16; z++) {
//				modelMatrix.translation(0,0,0).rotateY(0);
				stack.push();
				stack.translate(x / 1f, 0, z / 1f);
				
				RenderableRectangle renderableRectangle = new RenderableRectangle(stack.getLast(),
						new Vector3D(1, 0, 0),
						new Vector3D(0, 0, 0),
						new Vector3D(0, 1, 0),
						new Vector3D(1, 1, 0)
				);
				RenderHelper.drawColor(renderableRectangle,(Math.abs(z)%17)/17f,(Math.abs(x)%17)/17f,(Math.abs(x+z)%17)/17f,1);

				renderableRectangle = new RenderableRectangle(stack.getLast(),
						new Vector3D(0, 0, 0),
						new Vector3D(0, 0, 1),
						new Vector3D(0, 1, 1),
						new Vector3D(0, 1, 0)
				);
				RenderHelper.draw(renderableRectangle);

				renderableRectangle = new RenderableRectangle(stack.getLast(),
						new Vector3D(1, 0, 1),
						new Vector3D(1, 0, 0),
						new Vector3D(1, 1, 0),
						new Vector3D(1, 1, 1)
				);
				RenderHelper.draw(renderableRectangle);

				renderableRectangle = new RenderableRectangle(stack.getLast(),
						new Vector3D(0, 0, 1),
						new Vector3D(1, 0, 1),
						new Vector3D(1, 1, 1),
						new Vector3D(0, 1, 1)
				);
				RenderHelper.draw(renderableRectangle);

				renderableRectangle = new RenderableRectangle(stack.getLast(),
						new Vector3D(1, 1, 0),
						new Vector3D(0, 1, 0),
						new Vector3D(0, 1, 1),
						new Vector3D(1, 1, 1)
				);
				RenderHelper.draw(renderableRectangle);

				renderableRectangle = new RenderableRectangle(stack.getLast(),
						new Vector3D(0, 0, 0),
						new Vector3D(1, 0, 0),
						new Vector3D(1, 0, 1),
						new Vector3D(0, 0, 1)
				);
				RenderHelper.draw(renderableRectangle);
				
				glLoadMatrixf(viewMatrix.mul(modelMatrix, modelViewMatrix).get(fb));
				stack.pop();
			}
		}
	}
	
	public static void clone(String resourceName) throws IOException {
		InputStream stream = Window.class.getClassLoader().getResourceAsStream("lwjgl.dll");
		File f = new File("libs/"+resourceName);
		if (!f.exists()) {
			f.createNewFile();
		}
		OutputStream outputStream = new FileOutputStream(f);
	}
	
	public static void init() {
		GLFWErrorCallback.createPrint(System.err).set();
		
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_CENTER_CURSOR, GLFW_TRUE);
		
		window = glfwCreateWindow(1000, 1000, "Hello World!", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");
		
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true);
		});
		
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			
			glfwGetWindowSize(window, pWidth, pHeight);
			
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			
			glfwSetWindowPos(
					window,
					(vidmode.width() - pWidth.get(0)) / 2,
					(vidmode.height() - pHeight.get(0)) / 2
			);
		}
		
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		
		glfwShowWindow(window);
	}
}
