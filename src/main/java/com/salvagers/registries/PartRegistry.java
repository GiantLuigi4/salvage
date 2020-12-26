package com.salvagers.registries;

import com.salvagers.parts.objects.Block;
import com.salvagers.parts.objects.Part;
import com.salvagers.parts.objects.Wheel;

import java.util.HashMap;
import java.util.function.Function;

public class PartRegistry {
	public static final HashMap<String, Function<Integer, Part>> registry = new HashMap<>();
	
	static {
		register("Block", Block::new);
		register("Wheel", Wheel::new);
	}
	
	/**
	 * Registers a part
	 *
	 * @param name       the registry name of the part
	 * @param seedToPart the function which takes a seed and returns a part
	 */
	public static void register(String name, Function<Integer, Part> seedToPart) {
		registry.put(name, seedToPart);
	}
	
	/**
	 * Gets a registered part and constructs it with the given seed
	 *
	 * @param key  the registry name of the part
	 * @param seed the seed to use
	 * @return the part which has been requested
	 */
	public static Part get(String key, int seed) {
		return registry.get(key).apply(seed);
	}
}
