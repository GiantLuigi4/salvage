package com.salvagers.parts.objects;

import com.salvagers.client.utils.rendering.RenderHelper;
import com.salvagers.client.utils.rendering.matrix.MatrixStack;
import com.salvagers.client.utils.rendering.renderables.RenderableRectangle;
import com.salvagers.common.Vector3D;

public class Wheel extends Part {
    public Wheel(float weight, float friction, float size) {
        super(weight, friction, size);
        this.body = Shapes.getCylinder(
                1,0.05f,0.1f
        );
    }
    
    @Override
    public void render(MatrixStack stack) {
        stack.rotate(90,1,0,0);
        float interval = 360f / 36f;
        for (int i = 0; i < 36; i++) {
            double sin = Math.sin((Math.toRadians(i + 1)) * interval);
            double cos = Math.cos((Math.toRadians(i + 1)) * interval);
            double cos1 = Math.cos(Math.toRadians(i) * interval);
            double sin1 = Math.sin(Math.toRadians(i) * interval);
            RenderableRectangle line = new RenderableRectangle(stack.getLast(),
                    new Vector3D(cos, sin, 0.1f),
                    new Vector3D(cos1, sin1, 0.1f),
                    new Vector3D(cos1, sin1, -0.1f),
                    new Vector3D(cos, sin, -0.1f)
            );
            RenderHelper.drawColor(line, i / 36f, i / 36f, i / 36f, 1);
            line = new RenderableRectangle(stack.getLast(),
                    new Vector3D(0, 0, 0.1f),
                    new Vector3D(0, 0, 0.1f),
                    new Vector3D(cos1, sin1, 0.1f),
                    new Vector3D(cos, sin, 0.1f)
            );
            RenderHelper.drawColor(line, i / 36f, i / 36f, i / 36f, 1);
            line = new RenderableRectangle(stack.getLast(),
                    new Vector3D(cos, sin, -0.1f),
                    new Vector3D(0, 0, -0.1f),
                    new Vector3D(cos1, sin1, -0.1f),
                    new Vector3D(0, 0, -0.1f)
            );
            RenderHelper.drawColor(line, i / 36f, i / 36f, i / 36f, 1);
        }
    }
}
