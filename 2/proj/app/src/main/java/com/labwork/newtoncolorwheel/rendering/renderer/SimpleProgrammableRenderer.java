package com.labwork.newtoncolorwheel.rendering.renderer;

import java.util.List;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES32;
import android.opengl.GLSurfaceView.Renderer;
import com.labwork.newtoncolorwheel.demo.Standalone;
import com.labwork.newtoncolorwheel.runtime.Framework;
import com.labwork.newtoncolorwheel.core.general.Scene;
import com.labwork.newtoncolorwheel.core.general.Mesh;
import com.labwork.newtoncolorwheel.core.general.Color;
import com.labwork.newtoncolorwheel.core.general.Entity;
import com.labwork.newtoncolorwheel.core.general.Shader;
import com.labwork.newtoncolorwheel.core.general.Material;
import com.labwork.newtoncolorwheel.rendering.passes.common.RenderPass;
import com.labwork.newtoncolorwheel.rendering.passes.concrete.OpaqueRenderPass;
import com.labwork.newtoncolorwheel.core.components.concrete.RenderingComponent;
import com.labwork.newtoncolorwheel.core.components.concrete.TransformComponent;
import com.labwork.newtoncolorwheel.core.components.concrete.ColorShiftingComponent;
import com.labwork.newtoncolorwheel.core.components.concrete.CameraPerspectiveComponent;

public final class SimpleProgrammableRenderer implements Renderer {

    private final List<RenderPass> passes;
    private final List<Entity> dispatchedEntities;

    private Shader shader;
    private Entity wheel;
    private Entity camera;
    private Entity rectangle;

    public SimpleProgrammableRenderer() {
        this.passes = new ArrayList<>();
        this.passes.add(new OpaqueRenderPass());
        this.dispatchedEntities = new ArrayList<>();
    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        Scene scene = new Scene();

        this.shader = new Shader(OpaqueRenderPass.class, Standalone.SHADER_VERT_SOURCE, Standalone.SHADER_FRAG_SOURCE);
        this.shader.compile();

        this.rectangle = new Entity();
        this.rectangle.addComponent(new TransformComponent(this.rectangle));
        Mesh rectangleMesh = new Mesh(this.generateRectangleVertices(), GLES32.GL_TRIANGLE_STRIP);
        this.rectangle.addComponent(new RenderingComponent(this.rectangle, rectangleMesh, new Material(new Color(255, 255, 255, 0), this.shader)));

        this.rectangle.getComponent(TransformComponent.class).getScale().setX(2.0f);
        this.rectangle.getComponent(TransformComponent.class).getScale().setY(0.5f);
        this.rectangle.getComponent(TransformComponent.class).getPosition().setY(-3.0f);

        this.wheel = new Entity();
        this.wheel.addComponent(new TransformComponent(this.wheel));
        Mesh wheelMesh = new Mesh(this.generateWheelVertices(), GLES32.GL_TRIANGLE_FAN);
        this.wheel.addComponent(new ColorShiftingComponent(this.wheel));
        this.wheel.addComponent(new RenderingComponent(this.wheel, wheelMesh, new Material(new Color(255, 255, 255, 255), this.shader)));

        this.wheel.getComponent(TransformComponent.class).getScale().setX(1.5f);
        this.wheel.getComponent(TransformComponent.class).getScale().setY(1.5f);
        this.wheel.getComponent(TransformComponent.class).getPosition().setY(0.5f);

        this.camera = new Entity();
        this.camera.addComponent(new TransformComponent(this.camera));
        this.camera.addComponent(new CameraPerspectiveComponent(this.camera, new Color(27, 27, 27, 255), 0.001f, 100.0f, 90.0f, 90.0f));

        this.camera.getComponent(TransformComponent.class).getPosition().setZ(-5.0f);

        scene.addEntity(this.wheel);
        scene.addEntity(this.camera);
        scene.addEntity(this.rectangle);

        Framework.getInstance().loadScene(scene);

        for (Entity entity : scene.getEntities())
            entity.onStart();
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES32.glViewport(0, 0, width, height);
        this.camera.getComponent(CameraPerspectiveComponent.class).setAspectRatio((float)width / height);
    }

    public void onDrawFrame(GL10 unused) {
        List<Entity> entities = Framework.getInstance().getScene().getEntities();

        for (Entity entity : entities) {
            if (entity.getIsActive()) {
                entity.onUpdate();
                this.dispatchedEntities.add(entity);
            }
        }

        for (RenderPass pass : this.passes)
            pass.execute(this.dispatchedEntities);
    }

    private float[] generateWheelVertices() {
        return new float[] {
                0.0f,  0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, // White
                0.0f,  1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, // Red
                0.7f,  0.7f, 0.0f, 1.0f, 0.5f, 0.0f, 1.0f, // Orange
                1.0f,  0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, // Yellow
                0.7f, -0.7f, 0.0f, 0.5f, 1.0f, 0.0f, 1.0f, // Green
                0.0f, -1.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f, // Cyan
               -0.7f, -0.7f, 0.0f, 0.0f, 0.5f, 1.0f, 1.0f, // Blue
               -1.0f,  0.0f, 0.0f, 0.5f, 0.0f, 1.0f, 1.0f, // Purple
               -0.7f,  0.7f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f, // Magenta
                0.0f,  1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, // Closing
        };
    }

    private float[] generateRectangleVertices() {
        return new float[] {
                -1.0f,  0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, // [0] Left-top (Red)
                -1.0f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, // [1] Left-bottom (Red)
                -0.6f,  0.5f, 0.0f, 1.0f, 0.5f, 0.0f, 1.0f, // [2] Next-top (Orange)
                -0.6f, -0.5f, 0.0f, 1.0f, 0.5f, 0.0f, 1.0f, // [3] Next-bottom (Orange)
                -0.3f,  0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, // [4] Next-top (Yellow)
                -0.3f, -0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, // [5] Next-bottom (Yellow)
                 0.0f,  0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, // [6] Middle-top (Green)
                 0.0f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, // [7] Middle-bottom (Green)
                 0.3f,  0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, // [8] Next-top (Cyan)
                 0.3f, -0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, // [9] Next-bottom (Cyan)
                 0.6f,  0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, // [10] Next-top (Blue)
                 0.6f, -0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, // [11] Next-bottom (Blue)
                 1.0f,  0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, // [12] Right-top (Magenta)
                 1.0f, -0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, // [13] Right-bottom (Magenta)
        };
    }
}
