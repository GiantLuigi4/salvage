package com.salvagers.common.terrain;

import com.salvagers.common.shapes.Triangle;
import net.rgsw.ptg.noise.perlin.Perlin2D;

import javax.vecmath.Vector3f;
import java.util.ArrayList;

public class TerrainGen {
	public Perlin2D perlin = new Perlin2D(0);
	
	public TerrainGen(int seed) {
		perlin.setSeed(seed);
	}
	
	public ArrayList<Triangle> getTriangles(int x, int z, int sizeX, int sizeZ) {
		ArrayList<Triangle> triangles = new ArrayList<>();
		for (int xGen = x; xGen <= x + sizeX; xGen++) {
			for (int zGen = z; zGen <= z + sizeZ; zGen++) {
				triangles.add(
						new Triangle(
								new Vector3f(xGen, (float) perlin.generate((xGen / 10f), (zGen / 10f)) * 10f, zGen),
								new Vector3f(xGen, (float) perlin.generate((xGen / 10f), ((zGen + 1) / 10f)) * 10f, zGen + 1),
								new Vector3f(xGen + 1, (float) perlin.generate(((xGen + 1) / 10f), ((zGen + 1) / 10f)) * 10f, zGen + 1)
						)
				);
				triangles.add(
						new Triangle(
								new Vector3f(xGen + 1, (float) perlin.generate(((xGen + 1) / 10f), ((zGen + 1) / 10f)) * 10f, zGen + 1),
								new Vector3f(xGen + 1, (float) perlin.generate(((xGen + 1) / 10f), ((zGen) / 10f)) * 10f, zGen),
								new Vector3f(xGen, (float) perlin.generate(((xGen) / 10f), ((zGen) / 10f)) * 10f, zGen)
						)
				);
			}
		}
		return triangles;
	}
}
