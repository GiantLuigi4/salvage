package com.salvagers.registries;

import com.salvagers.parts.objects.Block;
import com.salvagers.parts.objects.Part;
import com.salvagers.parts.objects.Wheel;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class PartRegistry {
	public static final HashMap<String, Function<Integer,Part>> registry = new HashMap<>();
	
	static {
		registry.put("Block",Block::new);
		registry.put("Wheel", Wheel::new);
	}
}
