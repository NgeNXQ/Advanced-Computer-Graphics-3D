package com.labwork.illuminationexample;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.opengl.GLES32;
import androidx.appcompat.app.AppCompatActivity;
import com.labwork.illuminationexample.runtime.Framework;
import com.labwork.illuminationexample.core.general.Mesh;
import com.labwork.illuminationexample.core.general.Color;
import com.labwork.illuminationexample.core.general.Scene;
import com.labwork.illuminationexample.core.general.Entity;
import com.labwork.illuminationexample.core.general.Shader;
import com.labwork.illuminationexample.core.general.Material;
import com.labwork.illuminationexample.core.components.concrete.LightComponent;
import com.labwork.illuminationexample.core.components.concrete.TransformComponent;
import com.labwork.illuminationexample.core.components.concrete.RenderingComponent;
import com.labwork.illuminationexample.core.components.concrete.CameraPerspectiveComponent;
import com.labwork.illuminationexample.demo.shaders.Standalone;
import com.labwork.illuminationexample.demo.components.RotationComponent;
import com.labwork.illuminationexample.demo.components.NoClipControllerComponent;
import com.labwork.illuminationexample.demo.components.DynamicLightControllerComponent;
import com.labwork.illuminationexample.rendering.passes.concrete.OpaqueRenderPass;
import com.labwork.illuminationexample.rendering.renderer.concrete.ForwardRenderer;
import com.labwork.illuminationexample.rendering.renderer.common.RendererProgrammable;
import com.labwork.illuminationexample.rendering.viewport.concrete.Viewport;
import com.labwork.illuminationexample.rendering.viewport.common.ViewportConfigurable;

public class MainActivity extends AppCompatActivity {
    private static final int MENU_ITEM_SCENE_CUBES = 1;
    private static final int MENU_ITEM_SCENE_PYRAMID = 2;
    private static final int MENU_ITEM_SCENE_DIFFUSE = 3;
    private static final int MENU_ITEM_SCENE_SPECULAR = 4;

    private Shader shader;
    private Scene cubesScene;
    private Scene pyramidScene;
    private Scene diffuseScene;
    private Scene specularScene;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        ViewportConfigurable viewport = new Viewport(this);
        RendererProgrammable renderer = new ForwardRenderer(this::initializeAssets);

        super.onCreate(savedInstanceState);
        super.setContentView(viewport.getLayout());
        Framework.getInstance().initialize(renderer, viewport);
    }

    private void initializeAssets() {
        this.shader = new Shader(OpaqueRenderPass.class, Standalone.SHADER_VERT_SOURCE, Standalone.SHADER_FRAG_SOURCE);
        Framework.getInstance().getRenderer().registerRenderPass(new OpaqueRenderPass(this.shader));
        this.specularScene = this.initializeSpecularScene();
        this.diffuseScene = this.initializeDiffuseScene();
        this.pyramidScene = this.initializePyramidScene();
        this.cubesScene = this.initializeCubesScene();
    }

    private Scene initializeCubesScene() {
        Scene scene = new Scene();
        Material material = new Material(this.shader, new Color(255, 255, 255, 0), 0.5f, 1.85f, 0.1f);

        float spacing = 2.0f;

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int z = 0; z < 3; z++) {
                    Entity cube = new Entity();
                    cube.addComponent(new TransformComponent(cube));
                    cube.addComponent(new RenderingComponent(cube, new Mesh(this.generateCubeVertices(), GLES32.GL_TRIANGLES), material));

                    float startOffset = -spacing;
                    cube.getComponent(TransformComponent.class).getPosition().setX(startOffset + x * spacing);
                    cube.getComponent(TransformComponent.class).getPosition().setY(startOffset + y * spacing);
                    cube.getComponent(TransformComponent.class).getPosition().setZ(startOffset + z * spacing);

                    scene.addEntity(cube);
                }
            }
        }

        Entity light = new Entity();
        light.addComponent(new TransformComponent(light));
        light.addComponent(new LightComponent(light, this.shader, new Color(255, 255, 255, 255), 200.0f, true));
        scene.addEntity(light);

        Entity camera = new Entity();
        camera.addComponent(new TransformComponent(camera));
        camera.addComponent(new LightComponent(camera, this.shader, new Color(255, 255, 255, 255), 200.0f, true));
        camera.addComponent(new CameraPerspectiveComponent(camera, new Color(27, 27, 27, 255), 0.001f, 100.0f, 90.0f, 90.0f));
        camera.addComponent(new NoClipControllerComponent(camera, new Button(this), new Button(this), new Button(this), new Button(this), new Button(this), new Button(this), new Button(this), new Button(this), light));
        scene.addEntity(camera);

        camera.getComponent(TransformComponent.class).getPosition().setZ(-3.5f);
        camera.getComponent(TransformComponent.class).getPosition().setY(5.5f);
        camera.getComponent(TransformComponent.class).getRotation().setX(45.0f);

        return scene;
    }

    private Scene initializePyramidScene() {
        Scene scene = new Scene();
        Material material = new Material(this.shader, new Color(255, 255, 255, 0), 0.3f, 0.7f, 0.3f);

        Entity pyramid = new Entity();
        pyramid.addComponent(new RotationComponent(pyramid));
        pyramid.addComponent(new TransformComponent(pyramid));
        Mesh pyramidMesh = new Mesh(this.generatePyramidVertices(), GLES32.GL_TRIANGLES);
        pyramid.addComponent(new RenderingComponent(pyramid, pyramidMesh, material));
        scene.addEntity(pyramid);

        pyramid.getComponent(TransformComponent.class).getPosition().setY(1.0f);

        Entity plain = new Entity();
        plain.addComponent(new TransformComponent(plain));
        Mesh plainMesh = new Mesh(this.generateChessboardVertices(), GLES32.GL_TRIANGLES);
        plain.addComponent(new RenderingComponent(plain, plainMesh, material));
        scene.addEntity(plain);

        plain.getComponent(TransformComponent.class).getScale().setX(3.0f);
        plain.getComponent(TransformComponent.class).getScale().setZ(3.0f);

        Entity light = new Entity();
        light.addComponent(new TransformComponent(light));
        Mesh lightMesh = new Mesh(this.generateLightMesh(), GLES32.GL_POINTS);
        light.addComponent(new RenderingComponent(light, lightMesh, material));
        light.addComponent(new LightComponent(light, this.shader, new Color(255, 255, 255, 255), 200.0f, true));
        light.addComponent(new DynamicLightControllerComponent(light, new Button(this), new Button(this), new Button(this), new Button(this), new Button(this), new Button(this)));
        scene.addEntity(light);

        light.getComponent(TransformComponent.class).getPosition().setY(2.5f);

        Entity camera = new Entity();
        camera.addComponent(new TransformComponent(camera));
        camera.addComponent(new CameraPerspectiveComponent(camera, new Color(27, 27, 27, 255), 0.001f, 100.0f, 90.0f, 90.0f));
        scene.addEntity(camera);

        camera.getComponent(TransformComponent.class).getPosition().setY(2.5f);
        camera.getComponent(TransformComponent.class).getPosition().setZ(-5.0f);

        return scene;
    }

    private Scene initializeDiffuseScene() {
        Scene scene = new Scene();
        Material material = new Material(this.shader, new Color(255, 255, 255, 0), 0.3f, 0.7f, 0.0f);

        Entity rectangle = new Entity();
        rectangle.addComponent(new TransformComponent(rectangle));
        Mesh rectangleMesh = new Mesh(this.generateRectangleVertices(), GLES32.GL_TRIANGLES);
        rectangle.addComponent(new RenderingComponent(rectangle, rectangleMesh, material));
        scene.addEntity(rectangle);

        Entity light = new Entity();
        light.addComponent(new TransformComponent(light));
        Mesh lightMesh = new Mesh(this.generateLightMesh(), GLES32.GL_POINTS);
        light.addComponent(new RenderingComponent(light, lightMesh, material));
        light.addComponent(new LightComponent(light, this.shader, new Color(255, 0, 127, 255), 200.0f, true));
        light.addComponent(new DynamicLightControllerComponent(light, new Button(this), new Button(this), new Button(this), new Button(this), new Button(this), new Button(this)));
        scene.addEntity(light);

        Entity camera = new Entity();
        camera.addComponent(new TransformComponent(camera));
        camera.addComponent(new CameraPerspectiveComponent(camera, new Color(27, 27, 27, 255), 0.001f, 100.0f, 90.0f, 90.0f));
        scene.addEntity(camera);

        camera.getComponent(TransformComponent.class).getPosition().setY(1.5f);
        camera.getComponent(TransformComponent.class).getPosition().setZ(-3.0f);
        camera.getComponent(TransformComponent.class).getRotation().setX(15.0f);

        return scene;
    }

    private Scene initializeSpecularScene() {
        Scene scene = new Scene();
        Material material = new Material(this.shader, new Color(255, 255, 255, 0), 0.3f, 0.0f, 0.7f);

        Entity rectangle = new Entity();
        rectangle.addComponent(new TransformComponent(rectangle));
        Mesh rectangleMesh = new Mesh(this.generateRectangleVertices(), GLES32.GL_TRIANGLES);
        rectangle.addComponent(new RenderingComponent(rectangle, rectangleMesh, material));
        scene.addEntity(rectangle);

        Entity light = new Entity();
        light.addComponent(new TransformComponent(light));
        Mesh lightMesh = new Mesh(this.generateLightMesh(), GLES32.GL_POINTS);
        light.addComponent(new RenderingComponent(light, lightMesh, material));
        light.addComponent(new LightComponent(light, this.shader, new Color(255, 0, 127, 255), 200.0f, false));
        light.addComponent(new DynamicLightControllerComponent(light, new Button(this), new Button(this), new Button(this), new Button(this), new Button(this), new Button(this)));
        scene.addEntity(light);

        Entity camera = new Entity();
        camera.addComponent(new TransformComponent(camera));
        camera.addComponent(new CameraPerspectiveComponent(camera, new Color(27, 27, 27, 255), 0.001f, 100.0f, 90.0f, 90.0f));
        scene.addEntity(camera);

        camera.getComponent(TransformComponent.class).getPosition().setY(1.5f);
        camera.getComponent(TransformComponent.class).getPosition().setZ(-3.0f);
        camera.getComponent(TransformComponent.class).getRotation().setX(15.0f);

        return scene;
    }

    private float[] generateLightMesh() {
        return new float[] {
                0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f
        };
    }

    private float[] generateCubeVertices() {
        return new float[] {
                -0.5f, -0.5f,  0.5f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
                0.5f, -0.5f,  0.5f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
                0.5f,  0.5f,  0.5f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
                0.5f,  0.5f,  0.5f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
                -0.5f,  0.5f,  0.5f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
                -0.5f, -0.5f,  0.5f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,

                -0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, -1.0f,
                0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, -1.0f,
                0.5f,  0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, -1.0f,
                0.5f,  0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, -1.0f,
                -0.5f,  0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, -1.0f,
                -0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, -1.0f,

                -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f,
                -0.5f, -0.5f,  0.5f, 0.0f, 0.0f, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f,
                -0.5f,  0.5f,  0.5f, 0.0f, 0.0f, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f,
                -0.5f,  0.5f,  0.5f, 0.0f, 0.0f, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f,
                -0.5f,  0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f,
                -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 1.0f, -1.0f, 0.0f, 0.0f,

                0.5f, -0.5f, -0.5f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f,
                0.5f, -0.5f,  0.5f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f,
                0.5f,  0.5f,  0.5f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f,
                0.5f,  0.5f,  0.5f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f,
                0.5f,  0.5f, -0.5f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f,
                0.5f, -0.5f, -0.5f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f,

                -0.5f,  0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f,
                0.5f,  0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f,
                0.5f,  0.5f,  0.5f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f,
                0.5f,  0.5f,  0.5f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f,
                -0.5f,  0.5f,  0.5f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f,
                -0.5f,  0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f,

                -0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, -1.0f, 0.0f,
                0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, -1.0f, 0.0f,
                0.5f, -0.5f,  0.5f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, -1.0f, 0.0f,
                0.5f, -0.5f,  0.5f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, -1.0f, 0.0f,
                -0.5f, -0.5f,  0.5f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, -1.0f, 0.0f,
                -0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, -1.0f, 0.0f
        };
    }

    private float[] generatePyramidVertices() {
        return new float[] {
                -1.0f, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -1.0f, 0.0f,
                1.0f, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -1.0f, 0.0f,
                1.0f, -1.0f,  1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -1.0f, 0.0f,
                1.0f, -1.0f,  1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -1.0f, 0.0f,
                -1.0f, -1.0f,  1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -1.0f, 0.0f,
                -1.0f, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, -1.0f, 0.0f,

                -1.0f, -1.0f, -1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, -1.0f,
                1.0f, -1.0f, -1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, -1.0f,
                0.0f,  1.0f,  0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, -1.0f,

                1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.7071f, 0.7071f, 0.0f,
                1.0f, -1.0f,  1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.7071f, 0.7071f, 0.0f,
                0.0f,  1.0f,  0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.7071f, 0.7071f, 0.0f,

                1.0f, -1.0f,  1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
                -1.0f, -1.0f,  1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
                0.0f,  1.0f,  0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,

                -1.0f, -1.0f,  1.0f, 1.0f, 0.0f, 0.0f, 1.0f, -0.7071f, 0.7071f, 0.0f,
                -1.0f, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, -0.7071f, 0.7071f, 0.0f,
                0.0f,  1.0f,  0.0f, 1.0f, 0.0f, 0.0f, 1.0f, -0.7071f, 0.7071f, 0.0f
        };
    }

    private float[] generateRectangleVertices() {
        return new float[] {
                -1.0f, 0.0f, -1.0f, 0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 1.0f, 0.0f,  // Bottom-left
                1.0f, 0.0f, -1.0f, 0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 1.0f, 0.0f,   // Bottom-right
                1.0f, 0.0f,  1.0f, 0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 1.0f, 0.0f,   // Top-right

                1.0f, 0.0f,  1.0f, 0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 1.0f, 0.0f,   // Top-right
                -1.0f, 0.0f,  1.0f, 0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 1.0f, 0.0f,  // Top-left
                -1.0f, 0.0f, -1.0f, 0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 1.0f, 0.0f   // Bottom-left
        };
    }

    private float[] generateChessboardVertices() {
        float[] vertices = new float[540];

        int index = 0;

        float size = 1.0f;
        float offset = 1.5f;

        for (int z = 0; z < 3; z++) {
            for (int x = 0; x < 3; x++) {
                float posX = x * size - offset;
                float posY = 0.0f;
                float posZ = z * size - offset;

                float r = ((x + z) % 2 == 0) ? 1.0f : 0.0f;
                float g = ((x + z) % 2 == 0) ? 1.0f : 0.0f;
                float b = ((x + z) % 2 == 0) ? 1.0f : 0.0f;
                float a = 1.0f;

                float nx = 0.0f;
                float ny = 1.0f;
                float nz = 0.0f;

                vertices[index++] = posX;
                vertices[index++] = posY;
                vertices[index++] = posZ;
                vertices[index++] = r;
                vertices[index++] = g;
                vertices[index++] = b;
                vertices[index++] = a;
                vertices[index++] = nx;
                vertices[index++] = ny;
                vertices[index++] = nz;

                vertices[index++] = posX + size;
                vertices[index++] = posY;
                vertices[index++] = posZ;
                vertices[index++] = r;
                vertices[index++] = g;
                vertices[index++] = b;
                vertices[index++] = a;
                vertices[index++] = nx;
                vertices[index++] = ny;
                vertices[index++] = nz;

                vertices[index++] = posX + size;
                vertices[index++] = posY;
                vertices[index++] = posZ + size;
                vertices[index++] = r;
                vertices[index++] = g;
                vertices[index++] = b;
                vertices[index++] = a;
                vertices[index++] = nx;
                vertices[index++] = ny;
                vertices[index++] = nz;

                vertices[index++] = posX;
                vertices[index++] = posY;
                vertices[index++] = posZ;
                vertices[index++] = r;
                vertices[index++] = g;
                vertices[index++] = b;
                vertices[index++] = a;
                vertices[index++] = nx;
                vertices[index++] = ny;
                vertices[index++] = nz;

                vertices[index++] = posX + size;
                vertices[index++] = posY;
                vertices[index++] = posZ + size;
                vertices[index++] = r;
                vertices[index++] = g;
                vertices[index++] = b;
                vertices[index++] = a;
                vertices[index++] = nx;
                vertices[index++] = ny;
                vertices[index++] = nz;

                vertices[index++] = posX;
                vertices[index++] = posY;
                vertices[index++] = posZ + size;
                vertices[index++] = r;
                vertices[index++] = g;
                vertices[index++] = b;
                vertices[index++] = a;
                vertices[index++] = nx;
                vertices[index++] = ny;
                vertices[index++] = nz;
            }
        }

        return vertices;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MainActivity.MENU_ITEM_SCENE_CUBES, 0, "Cubes");
        menu.add(0, MainActivity.MENU_ITEM_SCENE_PYRAMID, 0, "Pyramid");
        menu.add(0, MainActivity.MENU_ITEM_SCENE_DIFFUSE, 0, "Diffuse");
        menu.add(0, MainActivity.MENU_ITEM_SCENE_SPECULAR, 0, "Specular");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.setTitle(item.getTitle());

        switch (item.getItemId()) {
            case MainActivity.MENU_ITEM_SCENE_CUBES:
                Framework.getInstance().loadScene(this.cubesScene);
                return true;
            case MainActivity.MENU_ITEM_SCENE_PYRAMID:
                Framework.getInstance().loadScene(this.pyramidScene);
                return true;
            case MainActivity.MENU_ITEM_SCENE_DIFFUSE:
                Framework.getInstance().loadScene(this.diffuseScene);
                return true;
            case MainActivity.MENU_ITEM_SCENE_SPECULAR:
                Framework.getInstance().loadScene(this.specularScene);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}