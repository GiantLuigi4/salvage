package com.tfc.data.tproperties;

import java.util.Collection;
import java.util.HashMap;

public class TPropObject {
	private final HashMap<String, TPropObject> objects = new HashMap<>();
	
	private final com.tfc.data.tproperties.TPropObject parent;
	private final String key;
	private final int parentCount;
	
	public TPropObject(com.tfc.data.tproperties.TPropObject parent, String key) {
		this.parent = parent;
		this.key = key;
		this.parentCount = parent.getParentCount() + 1;
	}
	
	public TPropObject() {
		this.parent = null;
		key = null;
		this.parentCount = 0;
	}
	
	protected HashMap<String, TPropObject> getObjects() {
		return objects;
	}
	
	public boolean contains(String key) {
		return objects.containsKey(key);
	}
	
	public int getParentCount() {
		return parentCount;
	}
	
	public com.tfc.data.tproperties.TPropObject get(String key) {
		return objects.get(key);
	}
	
	public com.tfc.data.tproperties.TPropObject set(String key, com.tfc.data.tproperties.TPropObject object) {
		if (objects.containsKey(key)) return objects.replace(key, object);
		else return objects.put(key, object);
	}
	
	public String getType() {
		return "object";
	}
	
	public com.tfc.data.tproperties.TPropObject getParent() {
		return parent;
	}
	
	public String getKey() {
		return key;
	}
	
	public Collection<String> getKeys() {
		return objects.keySet();
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		if (key != null) builder.append(key);
		
		for (com.tfc.data.tproperties.TPropObject object : objects.values()) {
			for (int i = 0; i < parentCount; i++) builder.append(" ");
			builder.append(object.toString()).append("\n");
		}
		
		return builder.toString();
	}
}
