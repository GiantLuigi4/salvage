package com.salvagers.parts.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import static com.badlogic.gdx.graphics.VertexAttributes.Usage;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;

public class Shapes {
	public static final Model model;
	
	static {
//		ModelBuilder mb = new ModelBuilder();
//
//		mb.begin();
//		mb.node().id = "sphere";
//		mb.part("sphere", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.GREEN)))
//				.sphere(1f, 1f, 1f, 10, 10);
//		mb.node().id = "box";
//		mb.part("box", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.BLUE)))
//				.box(1f, 1f, 1f);
//		mb.node().id = "cone";
//		mb.part("cone", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.YELLOW)))
//				.cone(1f, 1f, 1f, 10);
//		mb.node().id = "capsule";
//		mb.part("capsule", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.CYAN)))
//				.capsule(1, 2f, 10);
//		mb.node().id = "cylinder";
//		mb.part("cylinder", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal,
//				new Material(ColorAttribute.createDiffuse(Color.MAGENTA))).cylinder(1f, 2f, 1f, 10);
//		model = mb.end();
		model = null;
	}
	
	public static ModelInstance getModel(String name) {
		return new ModelInstance(model, name);
	}
	
	public static btCollisionShape getShape(String name, float width, float height, float length) {
		btCollisionShape shape;
		if (name.equals("sphere")) {
			shape = new btSphereShape(width);
		} else if (name.equals("box")) {
			shape = new btBoxShape(new Vector3(width, height, length));
		} else if (name.equals("cone")) {
			shape = new btConeShape(width, height);
		} else if (name.equals("capsule")) {
			shape = new btCapsuleShape(width, height);
		} else if (name.equals("cylinder")) {
			shape = new btCylinderShape(new Vector3(width, height, length));
		} else {
			shape = null;
		}
		return shape;
	}
	
	public static btRigidBody getCollider(String name, float mass, float width, float height, float length) {
		Vector3 localInertia = new Vector3(0, 0, 0);
		btCollisionShape shape = getShape(name, width, height, length);
		if (mass > 0f)
			shape.calculateLocalInertia(mass, localInertia);
		btRigidBody.btRigidBodyConstructionInfo info = new btRigidBody.btRigidBodyConstructionInfo(mass, null, shape, localInertia);
		btRigidBody body = new btRigidBody(info);
		return body;
	}
	
	//this will not work with capsule btw
	public static ModelInstance getModel(String name, float width, float height, float length) {
		ModelInstance instance = getModel(name);
		instance.transform.scale(width,height,length);
		return instance;
	}
}
