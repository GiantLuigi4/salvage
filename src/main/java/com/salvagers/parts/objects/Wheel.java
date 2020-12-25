package com.salvagers.parts.objects;

import com.salvagers.client.utils.rendering.RenderHelper;
import com.salvagers.client.utils.rendering.matrix.MatrixStack;
import com.salvagers.client.utils.rendering.renderables.Renderable;
import com.salvagers.client.utils.rendering.renderables.RenderableCollection;
import com.salvagers.client.utils.rendering.renderables.RenderableRectangle;
import com.salvagers.client.utils.rendering.renderables.TexturedRectangle;
import com.salvagers.common.Vector3D;

import java.util.ArrayList;

public class Wheel extends Part {
    private final Renderable[] renderables;
    
    /**
     * Constructs a wheel
     * @param weight the weight of the wheel (0 for if it should be immovable)
     * @param friction the friction of the wheel
     * @param size the size of the wheel (NYI)
     */
    public Wheel(float weight, float friction, float size) {
        super(weight, friction, size);
        this.body = Shapes.getCylinder(1,0.2f);
        
        ArrayList<Renderable> renderablesArrayList = new ArrayList<>();
        
        MatrixStack stack = new MatrixStack();
        float interval = 360f / 36f;
        //Makes the wheel shape for rendering
        for (int i = 0; i < 36; i++) {
            double sin = Math.sin((Math.toRadians(i + 1)) * interval);
            double cos = Math.cos((Math.toRadians(i + 1)) * interval);
            double cos1 = Math.cos(Math.toRadians(i) * interval);
            double sin1 = Math.sin(Math.toRadians(i) * interval);
            //Creates the shape of the wheel's rubber
            RenderableRectangle line = new TexturedRectangle(stack.getLast(),
                    new Vector3D(cos, sin, 0.1f),
                    new Vector3D(cos1, sin1, 0.1f),
                    new Vector3D(cos1, sin1, -0.1f),
                    new Vector3D(cos, sin, -0.1f),
                    "assets/salvagers/textures/tire_temp.png",
                    new Vector3D(1f/16,0,0),
                    new Vector3D(1f/16,1,0),
                    new Vector3D(3f/16,1,0),
                    new Vector3D(3f/16,0,0)
            );
            renderablesArrayList.add(line);
            line = new TexturedRectangle(stack.getLast(),
                    new Vector3D(cos, sin, -0.1f),
                    new Vector3D(cos1, sin1, -0.1f),
                    new Vector3D(cos1*0.9f, sin1*0.9f, -0.15f),
                    new Vector3D(cos*0.9f, sin*0.9f, -0.15f),
                    "assets/salvagers/textures/tire_temp.png",
                    new Vector3D(0f/16,0,0),
                    new Vector3D(0f/16,1,0),
                    new Vector3D(1f/16,1,0),
                    new Vector3D(1f/16,0,0)
            );
            renderablesArrayList.add(line);
            line = new TexturedRectangle(stack.getLast(),
                    new Vector3D(cos*0.9f, sin*0.9f, -0.15f),
                    new Vector3D(cos1*0.9f, sin1*0.9f, -0.15f),
                    new Vector3D(cos1*0.7f, sin1*0.7f, -0.15f),
                    new Vector3D(cos*0.7f, sin*0.7f, -0.15f),
                    "assets/salvagers/textures/tire_temp.png",
                    new Vector3D(1f/16,0,0),
                    new Vector3D(1f/16,1,0),
                    new Vector3D(3f/16,1,0),
                    new Vector3D(3f/16,0,0)
            );
            renderablesArrayList.add(line);
            line = new TexturedRectangle(stack.getLast(),
                    new Vector3D(cos*0.7f, sin*0.7f, -0.15f),
                    new Vector3D(cos1*0.7f, sin1*0.7f, -0.15f),
                    new Vector3D(cos1*0.6f, sin1*0.6f, -0.1f),
                    new Vector3D(cos*0.6f, sin*0.6f, -0.1f),
                    "assets/salvagers/textures/tire_temp.png",
                    new Vector3D(3f/16,0,0),
                    new Vector3D(3f/16,1,0),
                    new Vector3D(4f/16,1,0),
                    new Vector3D(4f/16,0,0)
            );
            renderablesArrayList.add(line);
            line = new TexturedRectangle(stack.getLast(),
                    new Vector3D(cos1*0.6f, sin1*0.6f, 0.1f),
                    new Vector3D(cos*0.6f, sin*0.6f, 0.1f),
                    new Vector3D(cos*0.6f, sin*0.6f, -0.1f),
                    new Vector3D(cos1*0.6f, sin1*0.6f, -0.1f),
                    "assets/salvagers/textures/tire_temp.png",
                    new Vector3D(1f/16,0,0),
                    new Vector3D(1f/16,1,0),
                    new Vector3D(3f/16,1,0),
                    new Vector3D(3f/16,0,0)
            );
            renderablesArrayList.add(line);
            line = new TexturedRectangle(stack.getLast(),
                    new Vector3D(cos1*0.7f, sin1*0.7f, 0.15f),
                    new Vector3D(cos*0.7f, sin*0.7f, 0.15f),
                    new Vector3D(cos*0.6f, sin*0.6f, 0.1f),
                    new Vector3D(cos1*0.6f, sin1*0.6f, 0.1f),
                    "assets/salvagers/textures/tire_temp.png",
                    new Vector3D(3f/16,0,0),
                    new Vector3D(3f/16,1,0),
                    new Vector3D(4f/16,1,0),
                    new Vector3D(4f/16,0,0)
            );
            renderablesArrayList.add(line);
            line = new TexturedRectangle(stack.getLast(),
                    new Vector3D(cos1*0.9f, sin1*0.9f, 0.15f),
                    new Vector3D(cos*0.9f, sin*0.9f, 0.15f),
                    new Vector3D(cos*0.7f, sin*0.7f, 0.15f),
                    new Vector3D(cos1*0.7f, sin1*0.7f, 0.15f),
                    "assets/salvagers/textures/tire_temp.png",
                    new Vector3D(1f/16,0,0),
                    new Vector3D(1f/16,1,0),
                    new Vector3D(3f/16,1,0),
                    new Vector3D(3f/16,0,0)
            );
            renderablesArrayList.add(line);
            line = new TexturedRectangle(stack.getLast(),
                    new Vector3D(cos1, sin1, 0.1f),
                    new Vector3D(cos, sin, 0.1f),
                    new Vector3D(cos*0.9f, sin*0.9f, 0.15f),
                    new Vector3D(cos1*0.9f, sin1*0.9f, 0.15f),
                    "assets/salvagers/textures/tire_temp.png",
                    new Vector3D(0f/16,0,0),
                    new Vector3D(0f/16,1,0),
                    new Vector3D(1f/16,1,0),
                    new Vector3D(1f/16,0,0)
            );
            renderablesArrayList.add(line);
        }
    
        renderables = renderablesArrayList.toArray(new Renderable[0]);
    
        for (Renderable renderable : renderables)
            renderable.color = new Vector3D(255,255,255);
    }
    
    @Override
    public void render(MatrixStack stack) {
        stack.rotate(90,1,0,0);
        RenderHelper.draw(
                new RenderableCollection(
                        stack.getLast(),
                        renderables
                )
        );
    }
}
