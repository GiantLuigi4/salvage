package com.salvagers.client.utils.rendering.textures;

import java.util.HashMap;

public class Storage {
	private static final HashMap<String, Integer> textureMap = new HashMap<>();
	
	public static int getOrGenerate(String resource) {
		if (!textureMap.containsKey(resource)) textureMap.put(resource, Loader.loadTexture(resource));
		return textureMap.get(resource);
	}
}
