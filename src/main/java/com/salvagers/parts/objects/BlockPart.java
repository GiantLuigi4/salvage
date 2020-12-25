package com.salvagers.parts.objects;

import com.salvagers.client.utils.rendering.RenderHelper;
import com.salvagers.client.utils.rendering.matrix.MatrixStack;
import com.salvagers.client.utils.rendering.renderables.Renderable;
import com.salvagers.client.utils.rendering.renderables.RenderableCollection;
import com.salvagers.client.utils.rendering.renderables.RenderableRectangle;
import com.salvagers.client.utils.rendering.renderables.TexturedRectangle;
import com.salvagers.common.Vector3D;

import java.util.ArrayList;

public class BlockPart extends Part {
	private static final Renderable[] renderables;
	
	static {
		//Makes the block shape for rendering
		MatrixStack stack = new MatrixStack();
		ArrayList<Renderable> renderablesArrayList = new ArrayList<>();
		RenderableRectangle renderableRectangle = new TexturedRectangle(stack.getLast(),
				new Vector3D(1, 0, 0),
				new Vector3D(0, 0, 0),
				new Vector3D(0, 1, 0),
				new Vector3D(1, 1, 0),
				"assets/salvagers/textures/wood_temp.png",
				new Vector3D(0,1,0),
				new Vector3D(1,1,0),
				new Vector3D(1,0,0),
				new Vector3D(0,0,0)
		);
		renderablesArrayList.add(renderableRectangle);
		
		renderableRectangle = new TexturedRectangle(stack.getLast(),
				new Vector3D(0, 0, 0),
				new Vector3D(0, 0, 1),
				new Vector3D(0, 1, 1),
				new Vector3D(0, 1, 0),
				"assets/salvagers/textures/wood_temp.png",
				new Vector3D(0,1,0),
				new Vector3D(1,1,0),
				new Vector3D(1,0,0),
				new Vector3D(0,0,0)
		);
		renderablesArrayList.add(renderableRectangle);
		
		renderableRectangle = new TexturedRectangle(stack.getLast(),
				new Vector3D(1, 0, 1),
				new Vector3D(1, 0, 0),
				new Vector3D(1, 1, 0),
				new Vector3D(1, 1, 1),
				"assets/salvagers/textures/wood_temp.png",
				new Vector3D(0,1,0),
				new Vector3D(1,1,0),
				new Vector3D(1,0,0),
				new Vector3D(0,0,0)
		);
		renderablesArrayList.add(renderableRectangle);
		
		renderableRectangle = new TexturedRectangle(stack.getLast(),
				new Vector3D(0, 0, 1),
				new Vector3D(1, 0, 1),
				new Vector3D(1, 1, 1),
				new Vector3D(0, 1, 1),
				"assets/salvagers/textures/wood_temp.png",
				new Vector3D(0,1,0),
				new Vector3D(1,1,0),
				new Vector3D(1,0,0),
				new Vector3D(0,0,0)
		);
		renderablesArrayList.add(renderableRectangle);
		
		renderableRectangle = new TexturedRectangle(stack.getLast(),
				new Vector3D(1, 1, 0),
				new Vector3D(0, 1, 0),
				new Vector3D(0, 1, 1),
				new Vector3D(1, 1, 1),
				"assets/salvagers/textures/wood_temp.png",
				new Vector3D(0,1,0),
				new Vector3D(1,1,0),
				new Vector3D(1,0,0),
				new Vector3D(0,0,0)
		);
		renderablesArrayList.add(renderableRectangle);
		
		renderableRectangle = new TexturedRectangle(stack.getLast(),
				new Vector3D(0, 0, 0),
				new Vector3D(1, 0, 0),
				new Vector3D(1, 0, 1),
				new Vector3D(0, 0, 1),
				"assets/salvagers/textures/wood_temp.png",
				new Vector3D(0,1,0),
				new Vector3D(1,1,0),
				new Vector3D(1,0,0),
				new Vector3D(0,0,0)
		);
		renderablesArrayList.add(renderableRectangle);
		
		renderables = renderablesArrayList.toArray(new Renderable[0]);

		for (Renderable renderable : renderables)
			renderable.color = new Vector3D(255,255,255);
	}
	
	public BlockPart(float weight, float friction, float size) {
		super(weight, friction, size);
		this.body = Shapes.getBox(1,1,1);
	}
	
	@Override
	public void render(MatrixStack stack) {
		for (Renderable renderable : renderables)
			renderable.matrix = stack.getLast();
		RenderHelper.draw(
				new RenderableCollection(
						stack.getLast(),
						renderables
				)
		);
	}
}
