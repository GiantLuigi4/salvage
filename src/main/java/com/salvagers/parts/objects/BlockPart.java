package com.salvagers.parts.objects;

import com.salvagers.client.utils.rendering.RenderHelper;
import com.salvagers.client.utils.rendering.matrix.MatrixStack;
import com.salvagers.client.utils.rendering.renderables.RenderableRectangle;
import com.salvagers.common.Vector3D;

public class BlockPart extends Part {
	public BlockPart(float weight, float friction, float size) {
		super(weight, friction, size);
		this.body = Shapes.getBox(1,1,1,0.1f);
	}
	
	@Override
	public void render(MatrixStack stack) {
		stack.push();
		
		RenderableRectangle renderableRectangle = new RenderableRectangle(stack.getLast(),
				new Vector3D(1, 0, 0),
				new Vector3D(0, 0, 0),
				new Vector3D(0, 1, 0),
				new Vector3D(1, 1, 0)
		);
		RenderHelper.drawColor(renderableRectangle, 0.5f,0.5f,0.5f,1f);
		
		renderableRectangle = new RenderableRectangle(stack.getLast(),
				new Vector3D(0, 0, 0),
				new Vector3D(0, 0, 1),
				new Vector3D(0, 1, 1),
				new Vector3D(0, 1, 0)
		);
		RenderHelper.drawColor(renderableRectangle,1f,1f,1f,1f);
		
		renderableRectangle = new RenderableRectangle(stack.getLast(),
				new Vector3D(1, 0, 1),
				new Vector3D(1, 0, 0),
				new Vector3D(1, 1, 0),
				new Vector3D(1, 1, 1)
		);
		RenderHelper.drawColor(renderableRectangle,0f,0f,0f,1f);
		
		renderableRectangle = new RenderableRectangle(stack.getLast(),
				new Vector3D(0, 0, 1),
				new Vector3D(1, 0, 1),
				new Vector3D(1, 1, 1),
				new Vector3D(0, 1, 1)
		);
		RenderHelper.drawColor(renderableRectangle,0.25f,0.25f,0.25f,1f);
		
		renderableRectangle = new RenderableRectangle(stack.getLast(),
				new Vector3D(1, 1, 0),
				new Vector3D(0, 1, 0),
				new Vector3D(0, 1, 1),
				new Vector3D(1, 1, 1)
		);
		RenderHelper.drawColor(renderableRectangle,0.75f,0.75f,0.75f,1f);
		
		renderableRectangle = new RenderableRectangle(stack.getLast(),
				new Vector3D(0, 0, 0),
				new Vector3D(1, 0, 0),
				new Vector3D(1, 0, 1),
				new Vector3D(0, 0, 1)
		);
		RenderHelper.drawColor(renderableRectangle,1f,1f,1f,1f);
		
		stack.pop();
	}
}
