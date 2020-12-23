package com.salvagers.client;

import com.salvagers.client.utils.rendering.RenderHelper;
import com.salvagers.client.utils.rendering.matrix.MatrixStack;
import com.salvagers.client.utils.rendering.renderables.RenderableLine;
import com.salvagers.client.utils.rendering.renderables.RenderableRectangle;
import com.salvagers.common.Vector3D;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.io.*;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
	private static long window;
	
	public static void main(String[] args) {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");
		
		init();
		loop();
		
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
	public static void loop() {
		GL.createCapabilities();
		
		glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
		
		MatrixStack stack = new MatrixStack();
		RenderableLine line = new RenderableLine(stack.getLast(), new Vector3D(0, 0, 0), new Vector3D(10, 10, 0));
		RenderableRectangle rectangle = new RenderableRectangle(stack.getLast(),
				new Vector3D(0, 0, 0), new Vector3D(2.5, 2.5, 2.5),
				new Vector3D(5, 5, 5), new Vector3D(-2.5, 2.5, 2.5)
		);
		
		while (!glfwWindowShouldClose(window)) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			glColor3f(0,0,0);
			RenderHelper.draw(line);
			glColor3f(1,0,1);
			RenderHelper.draw(rectangle);
			
			glfwSwapBuffers(window);
			
			glfwPollEvents();
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
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		
		window = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL);
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
