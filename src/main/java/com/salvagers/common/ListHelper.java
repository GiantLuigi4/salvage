package com.salvagers.common;

import com.bulletphysics.util.ObjectArrayList;
import com.salvagers.common.shapes.Triangle;

import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ListHelper {
	public static <T> ObjectArrayList<T> toObjectArrayList(Collection<T> objects) {
		ObjectArrayList<T> list = new ObjectArrayList<T>();
		list.addAll(objects);
		return list;
	}
	
	public static <T> ObjectArrayList<T> toObjectArrayList(T[] objects) {
		ObjectArrayList<T> list = new ObjectArrayList<T>();
		list.addAll(Arrays.asList(objects));
		return list;
	}
	
	public static <T> ArrayList<T> toArrayList(ObjectArrayList<T> objects) {
		return new ArrayList<T>(objects);
	}
	
	public static ArrayList<Vector3f> toVector3fArray(Collection<Triangle> triangles) {
		ArrayList<Vector3f> vector3fs = new ArrayList<>();
		for (Triangle triangle : triangles) {
			vector3fs.add(triangle.corner1);
			vector3fs.add(triangle.corner2);
			vector3fs.add(triangle.corner3);
		}
		return vector3fs;
	}
	
	public static ArrayList<Vector3f[]> toVector3fArrayArray(Collection<Triangle> triangles) {
		ArrayList<Vector3f[]> vector3fs = new ArrayList<>();
		for (Triangle triangle : triangles) {
			Vector3f[] vector3fs1 = new Vector3f[]{
					triangle.corner1,
					triangle.corner2,
					triangle.corner3
			};
			vector3fs.add(vector3fs1);
		}
		return vector3fs;
	}
}
