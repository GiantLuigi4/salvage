package com.tfc.data.tproperties;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class TPropertiesReader implements Closeable {
	private final InputStream stream;
	private final String text;
	
	private com.tfc.data.tproperties.TPropObject object = null;
	
	public TPropertiesReader(InputStream stream) throws IOException {
		InputStream tempStream = stream;
		byte[] bytes = new byte[tempStream.available()];
		tempStream.read(bytes);
		boolean couldReset = false;
		try {
			tempStream.reset();
			couldReset = true;
		} catch (Throwable ignored) {
			stream.close();
		}
		
		if (couldReset) this.stream = tempStream;
		else this.stream = new ByteArrayInputStream(bytes);
		
		this.text = new String(bytes).replace("\r", "");
	}
	
	private static String lTrim(String text) {
		char[] val = text.toCharArray();
		int len = val.length;
		int st = 0;
		
		while ((st < len) && (val[st] <= ' ')) st++;
		
		return ((st > 0)) ? text.substring(st, len) : text;
	}
	
	public static void main(String[] args) throws IOException {
		InputStream stream = com.tfc.data.tproperties.TPropertiesReader.class.getClassLoader().getResourceAsStream("data/tetric_additions/module_types/armor/binding.tproperties");
		com.tfc.data.tproperties.TPropertiesReader reader = new com.tfc.data.tproperties.TPropertiesReader(stream);
		
		System.out.println(reader.getAsTPropObject().getKeys().toString());
		
		System.out.println();
		System.out.println();
		
		System.out.println(reader.getAsTPropObject().toString());
		
		System.out.println();
		System.out.println();
		
		System.out.println(((com.tfc.data.tproperties.TPropArray) reader.getAsTPropObject().get("valid materials")).get(0).toString());
		
		reader.close();
	}
	
	public InputStream getStream() {
		return stream;
	}
	
	public String getText() {
		return text;
	}
	
	//TODO: configuration stuff
	//TODO: arrays in arrays
	public com.tfc.data.tproperties.TPropObject getAsTPropObject() {
		if (object == null) {
			com.tfc.data.tproperties.TPropObject currentObject = new com.tfc.data.tproperties.TPropObject();
			int spacers = 0;
			int prevSpacers;
			
			for (String line : text.split("\n")) {
				if (line.isEmpty() || line.startsWith("#"))
					continue;

//				if (line.length() != lTrim(line).length()) {
//					System.out.println(line.length());
//					System.out.println(lTrim(line).length());
//					System.out.println(line.length() - lTrim(line).length());
//				}
				
				prevSpacers = spacers;
				spacers = line.length() - lTrim(line).length();
				
				if (spacers > prevSpacers) {
				} else if (spacers < prevSpacers) {
					currentObject.getParent().set(currentObject.getKey(), currentObject);
					currentObject = currentObject.getParent();
				}
				
				if (currentObject instanceof com.tfc.data.tproperties.TPropArray)
					((com.tfc.data.tproperties.TPropArray) currentObject).add(new TPropString(currentObject, ((com.tfc.data.tproperties.TPropArray) currentObject).getSize() + "", lTrim(line)).setHideKey());
				
				if (line.endsWith(" are")) {
					currentObject = new com.tfc.data.tproperties.TPropArray(currentObject, line.trim().substring(0, line.length() - " are".length()));
					spacers++;
				} else if (line.contains(" is ")) {
					String key = line.trim().substring(0, line.indexOf(" is "));
					
					currentObject.set(key, new TPropString(currentObject, key, line.substring(key.length() + " is ".length())));
				}
			}
			
			while (currentObject.getParent() != null) {
				currentObject.getParent().set(currentObject.getKey(), currentObject);
				currentObject = currentObject.getParent();
			}
			
			return object = currentObject;
		} else {
			return object;
		}
	}
	
	@Override
	public void close() throws IOException {
		stream.close();
		object = null;
	}
}
