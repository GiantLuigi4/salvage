package com.salvagers.client;

import java.io.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
	private static long window;
	
	public static void main(String[] args) throws ReflectiveOperationException, IOException {
//		GLFWErrorCallback.createPrint(System.err).set();
//		Class.forName("SharedLibraryLoader").getMethod("load").invoke(null);
		
//		System.setProperty("java.library.path",new File("libs").getAbsolutePath());

//		run {
//			systemProperty 'java.library.path', 'path to ur natives')
//		}
		
//		clone("lwjgl.dll");
//		clone("lwjgl32.dll");
//		clone("OpenAL.dll");
//		clone("OpenAL32.dll");
		
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
		
		
		// Configure GLFW
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		
		window = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL);
		
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});
	}
	
	public static void clone(String resourceName) throws IOException {
		InputStream stream = Window.class.getClassLoader().getResourceAsStream("lwjgl.dll");
		File f = new File("libs/"+resourceName);
		if (!f.exists()) {
			f.createNewFile();
		}
		OutputStream outputStream = new FileOutputStream(f);
	}
}
