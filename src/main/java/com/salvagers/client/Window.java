package com.salvagers.client;

import com.bulletphysics.linearmath.Transform;
import com.salvagers.client.utils.rendering.matrix.MatrixStack;
import com.salvagers.world.World;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import javax.vecmath.Quat4f;
import java.awt.*;
import java.io.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
	public static final ArrayList<Integer> keysDown = new ArrayList<>();
	
	private static long window;
	
	public static boolean focus = true;
	
	//TODO: switch this to lwjgl's cursor stuff
	private static final Robot r;
	
	public static final World world = new World();
	
	static {
		Robot placeholder = null;
		try {
			placeholder = new Robot();
		} catch (Throwable ignored) {
		}
		r = placeholder;
	}
	
	public static Config configs = Config.parse();
	
	public static Matrix4f projMatrix = new Matrix4f();
	public static Matrix4f viewMatrix = new Matrix4f();
	public static Matrix4f modelMatrix = new Matrix4f();
	public static Matrix4f modelViewMatrix = new Matrix4f();
	public static FloatBuffer fb = BufferUtils.createFloatBuffer(16);
	
	public static float rotationX = 0;
	public static float rotationY = 0;
	
	public static Vector3f camPos = new Vector3f();
	
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
		
		glClearColor(0.0f, 0.0f, 1, 0.0f);
		
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
		
		viewMatrix.setLookAt(0.0f, 0.0f, 1.0f,
				0.0f, 0.0f, 0.0f,
				0.0f, 1.0f, 0.0f);
//		viewMatrix.translate(0,0,0);
//		viewMatrix.rotate((float) Math.toRadians(0),1,0,0);
		glMatrixMode(GL_MODELVIEW);
		
		GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW_CURSOR_HIDDEN);

		if (focus) {
			double[] cursorX = new double[1];
			double[] cursorY = new double[1];
			glfwGetCursorPos(window,cursorX,cursorY);
			rotationX += ((cursorX[0]-(int)(width/2f))/(256f-configs.sensitivityX));
			rotationY += ((cursorY[0]-(int)(height/2f))/(256f-configs.sensitivityY));
			
			System.out.println(configs.sensitivityX);
			
			rotationY = Math.max(-90,Math.min(rotationY,90));
			
			int[] posX = new int[1];
			int[] posY = new int[1];
			glfwGetWindowPos(window,posX,posY);
			r.mouseMove(posX[0]+width/2,posY[0]+height/2);
			r.waitForIdle();
		}
		
		stack.rotate(rotationY,1,0,0);
		stack.rotate(rotationX,0,1,0);
		stack.translate(camPos.x,camPos.y,camPos.z);
	}
	
	public static void render(float rotation, MatrixStack stack) {
		//Keep this for loading icon thing
//		for (int x = -16; x <= 16; x++) {
//			for (int z = -16; z <= 16; z++) {
//				stack.push();
//				stack.translate(x / 1f, 0, z / 1f);
//
//				RenderableRectangle renderableRectangle = new RenderableRectangle(stack.getLast(),
//						new Vector3D(1, 0, 0),
//						new Vector3D(0, 0, 0),
//						new Vector3D(0, 1, 0),
//						new Vector3D(1, 1, 0)
//				);
//				RenderHelper.drawColor(renderableRectangle,(Math.abs(z)%17)/17f,(Math.abs(x)%17)/17f,(Math.abs(x+z)%17)/17f,1);
//
//				renderableRectangle = new RenderableRectangle(stack.getLast(),
//						new Vector3D(0, 0, 0),
//						new Vector3D(0, 0, 1),
//						new Vector3D(0, 1, 1),
//						new Vector3D(0, 1, 0)
//				);
//				RenderHelper.draw(renderableRectangle);
//
//				renderableRectangle = new RenderableRectangle(stack.getLast(),
//						new Vector3D(1, 0, 1),
//						new Vector3D(1, 0, 0),
//						new Vector3D(1, 1, 0),
//						new Vector3D(1, 1, 1)
//				);
//				RenderHelper.draw(renderableRectangle);
//
//				renderableRectangle = new RenderableRectangle(stack.getLast(),
//						new Vector3D(0, 0, 1),
//						new Vector3D(1, 0, 1),
//						new Vector3D(1, 1, 1),
//						new Vector3D(0, 1, 1)
//				);
//				RenderHelper.draw(renderableRectangle);
//
//				renderableRectangle = new RenderableRectangle(stack.getLast(),
//						new Vector3D(1, 1, 0),
//						new Vector3D(0, 1, 0),
//						new Vector3D(0, 1, 1),
//						new Vector3D(1, 1, 1)
//				);
//				RenderHelper.draw(renderableRectangle);
//
//				renderableRectangle = new RenderableRectangle(stack.getLast(),
//						new Vector3D(0, 0, 0),
//						new Vector3D(1, 0, 0),
//						new Vector3D(1, 0, 1),
//						new Vector3D(0, 0, 1)
//				);
//				RenderHelper.draw(renderableRectangle);
//
//				glLoadMatrixf(viewMatrix.mul(modelMatrix, modelViewMatrix).get(fb));
//				stack.pop();
//			}
//		}
		
//		Wheel wheel = new Wheel(1,1,3);
//		stack.translate(10,0,0);
//		wheel.render(stack);
		
		if (keysDown.contains(GLFW_KEY_W)) {
			camPos.add(new Vector3f((float) Math.sin(Math.toRadians(rotationX+180)), 0,(float) -Math.cos(Math.toRadians(rotationX+180))));
		}
		if (keysDown.contains(GLFW_KEY_S)) {
			camPos.sub(new Vector3f((float) Math.sin(Math.toRadians(rotationX+180)), 0,(float) -Math.cos(Math.toRadians(rotationX+180))));
		}
		if (keysDown.contains(GLFW_KEY_D)) {
			camPos.add(new Vector3f((float) Math.sin(Math.toRadians(rotationX+180+90)), 0,(float) -Math.cos(Math.toRadians(rotationX+180+90))));
		}
		if (keysDown.contains(GLFW_KEY_A)) {
			camPos.add(new Vector3f((float) Math.sin(Math.toRadians(rotationX+180-90)), 0,(float) -Math.cos(Math.toRadians(rotationX+180-90))));
		}
		if (keysDown.contains(GLFW_KEY_Q)) {
			camPos.sub(new Vector3f(0,-1,0));
		}
		if (keysDown.contains(GLFW_KEY_E)) {
			camPos.sub(new Vector3f(0,1,0));
		}
		
//		stack.translate(camPos.getX(),camPos.getY(),camPos.getZ());
		
		world.parts.forEach(part->{
			stack.push();
			Transform transform = part.collisionBody.getWorldTransform(new com.bulletphysics.linearmath.Transform());
			float interpRate = part.lastRefresh-(int)part.lastRefresh;
//			javax.vecmath.Vector3f pos = new javax.vecmath.Vector3f();
			javax.vecmath.Vector3f pos = transform.origin;
//			transform.getMatrix(new javax.vecmath.Matrix4f()).transform(pos);
			Quat4f rotationQT = transform.getRotation(new Quat4f());
//			Quaternion.Slerp(part.lastRotation,transform.getOrientation(),interpRate,rotationQT);
			stack.translate(pos.x,pos.y,pos.z);
			if (rotationQT != null) {
				stack.rotate(rotationQT);
			}
			part.render(stack);
			stack.pop();
//			part.lastPosition = pos;
//			part.lastRotation = rotationQT;
		});
		world.tick();
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
//			if ( key == GLFW_KEY_W )
			if (action == GLFW_PRESS) {
				keysDown.add(key);
			} else if (action == GLFW_RELEASE) {
				keysDown.remove((Object)key);
			}
		});
		glfwSetWindowFocusCallback(window, new GLFWWindowFocusCallback() {
			@Override
			public void invoke(long window, boolean focused) {
				focus = focused;
			}
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
