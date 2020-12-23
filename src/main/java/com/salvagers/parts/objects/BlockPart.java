package com.salvagers.parts.objects;

import com.salvagers.client.utils.rendering.matrix.MatrixStack;

public class BlockPart extends Part {
	public BlockPart(float weight, float friction, float size) {
		super(weight, friction, size);
		this.body = Shapes.getCollider(
				"cylinder",1,1,1,1
		);
	}
	
	@Override
	public void render(MatrixStack stack) {
	}
	
	@Override
	public void dispose() {
		body.dispose();
	}
}
