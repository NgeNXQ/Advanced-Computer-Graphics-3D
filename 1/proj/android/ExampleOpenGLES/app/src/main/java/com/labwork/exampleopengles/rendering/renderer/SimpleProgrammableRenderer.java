package com.labwork.exampleopengles.rendering.renderer;

import java.util.List;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES32;
import android.opengl.GLSurfaceView.Renderer;
import com.labwork.exampleopengles.demo.Standalone;
import com.labwork.exampleopengles.runtime.Framework;
import com.labwork.exampleopengles.core.general.Scene;
import com.labwork.exampleopengles.core.general.Mesh;
import com.labwork.exampleopengles.core.general.Color;
import com.labwork.exampleopengles.core.general.Entity;
import com.labwork.exampleopengles.core.general.Shader;
import com.labwork.exampleopengles.core.general.Material;
import com.labwork.exampleopengles.rendering.passes.common.RenderPass;
import com.labwork.exampleopengles.rendering.passes.concrete.OpaqueRenderPass;
import com.labwork.exampleopengles.core.components.concrete.RenderingComponent;
import com.labwork.exampleopengles.core.components.concrete.TransformComponent;
import com.labwork.exampleopengles.core.components.concrete.CameraPerspectiveComponent;

public final class SimpleProgrammableRenderer implements Renderer {

    private static final int POLYGON_SIDES = 16;
    private static final float RAY_LENGTH = 2.0f;

    private final List<RenderPass> passes;
    private final List<Entity> dispatchedEntities;

    private Entity camera;
    private Entity raysEntity;
    private Entity polygonEntity;
    private Entity triangleEntity;

    private Color color;
    private Shader shader;
    private Material material;

    public SimpleProgrammableRenderer() {
        this.passes = new ArrayList<>();
        this.passes.add(new OpaqueRenderPass());
        this.dispatchedEntities = new ArrayList<>();
    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        GLES32.glClearColor(0.0f, 0.0f, 0.3f, 1.0f);

        Scene scene = new Scene();

        this.shader = new Shader(OpaqueRenderPass.class, Standalone.SHADER_VERT_SOURCE, Standalone.SHADER_FRAG_SOURCE);
        this.shader.compile();

        this.color = new Color(255, 20, 147, 255);
        this.material = new Material(this.color, this.shader);

        this.triangleEntity = new Entity();
        this.triangleEntity.addComponent(new TransformComponent(this.triangleEntity));
        Mesh triangleMesh = new Mesh(this.generateTriangleVertices(), GLES32.GL_TRIANGLE_STRIP);
        this.triangleEntity.addComponent(new RenderingComponent(this.triangleEntity, triangleMesh, this.material));

        this.triangleEntity.getComponent(TransformComponent.class).getScale().setX(2.0f);
        this.triangleEntity.getComponent(TransformComponent.class).getScale().setY(2.25f);
        this.triangleEntity.getComponent(TransformComponent.class).getPosition().setX(0.5f);
        this.triangleEntity.getComponent(TransformComponent.class).getPosition().setY(-1.25f);
        this.triangleEntity.getComponent(TransformComponent.class).getPosition().setZ(-5.0f);

        this.raysEntity = new Entity();
        this.raysEntity.addComponent(new TransformComponent(this.raysEntity));
        Mesh raysMesh = new Mesh(this.generateRays(POLYGON_SIDES, RAY_LENGTH), GLES32.GL_LINES);
        this.raysEntity.addComponent(new RenderingComponent(this.raysEntity, raysMesh, this.material));

        this.raysEntity.getComponent(TransformComponent.class).getScale().setX(0.65f);
        this.raysEntity.getComponent(TransformComponent.class).getScale().setY(0.65f);
        this.raysEntity.getComponent(TransformComponent.class).getPosition().setY(2.5f);
        this.raysEntity.getComponent(TransformComponent.class).getPosition().setZ(-5.0f);

        this.polygonEntity = new Entity();
        this.polygonEntity.addComponent(new TransformComponent(this.polygonEntity));
        Mesh polygonMesh =new Mesh(this.generatePolygonVertices(POLYGON_SIDES), GLES32.GL_TRIANGLE_FAN);
        this.polygonEntity.addComponent(new RenderingComponent(this.polygonEntity, polygonMesh, this.material));

        this.polygonEntity.getComponent(TransformComponent.class).getScale().setX(0.65f);
        this.polygonEntity.getComponent(TransformComponent.class).getScale().setY(0.65f);
        this.polygonEntity.getComponent(TransformComponent.class).getPosition().setY(2.5f);
        this.polygonEntity.getComponent(TransformComponent.class).getPosition().setZ(-5.0f);

        this.camera = new Entity();
        this.camera.addComponent(new TransformComponent(this.camera));
        this.camera.addComponent(new CameraPerspectiveComponent(this.camera, new Color(27, 27, 27, 255), 0.001f, 100.0f, 90.0f, 90.0f));

        scene.addEntity(this.camera);
        scene.addEntity(this.raysEntity);
        scene.addEntity(this.polygonEntity);
        scene.addEntity(this.triangleEntity);

        Framework.getInstance().loadScene(scene);

        for (Entity entity : scene.getEntities())
            entity.onStart();
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES32.glViewport(0, 0, width, height);
        this.camera.getComponent(CameraPerspectiveComponent.class).setAspectRatio((float) width / height);
    }

    public void onDrawFrame(GL10 unused) {
        this.dispatchedEntities.clear();

        for (Entity entity : Framework.getInstance().getScene().getEntities()) {
            if (entity.getIsActive()) {
                entity.onUpdate();
                this.dispatchedEntities.add(entity);
            }
        }

        for (RenderPass pass : this.passes)
            pass.execute(this.dispatchedEntities);
    }

    private float[] generateTriangleVertices() {
        final float height = (float) (Math.sqrt(3) / 2);

        return new float[]{
                0.0f, height, 0.0f,  // Top vertex
                -0.5f, 0.0f, 0.0f,   // Bottom left vertex
                0.5f, 0.0f, 0.0f     // Bottom right vertex
        };
    }

    private float[] generatePolygonVertices(int edgesCount) {
        final int vertexDimensionsCount = 3;
        final float[] vertices = new float[(edgesCount + 2) * vertexDimensionsCount];

        // Center vertex
        vertices[0] = 0.0f;
        vertices[1] = 0.0f;
        vertices[2] = 0.0f;

        for (int i = 0; i <= edgesCount; ++i) {
            int index = (i + 1) * vertexDimensionsCount;
            float angle = (float) (2 * Math.PI * i / edgesCount);

            vertices[index] = (float) Math.cos(angle);
            vertices[index + 1] = (float) Math.sin(angle);
            vertices[index + 2] = 0.0f;
        }

        return vertices;
    }

    private float[] generateRays(int raysCount, float rayLength) {
        final int lineVerticesCount = 2;
        final int vertexDimensionsCount = 3;
        final float[] vertices = new float[raysCount * lineVerticesCount * vertexDimensionsCount];

        for (int i = 0; i < raysCount; ++i) {
            int index = i * lineVerticesCount * vertexDimensionsCount;
            float angle = (float) (2 * Math.PI * i / raysCount);

            vertices[index] = (float) Math.cos(angle);
            vertices[index + 1] = (float) Math.sin(angle);
            vertices[index + 2] = 0.0f;

            vertices[index + 3] = (float) Math.cos(angle) * rayLength;
            vertices[index + 4] = (float) Math.sin(angle) * rayLength;
            vertices[index + 5] = 0.0f;
        }

        return vertices;
    }
}
