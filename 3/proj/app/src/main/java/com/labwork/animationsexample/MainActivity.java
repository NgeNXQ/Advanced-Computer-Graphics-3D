package com.labwork.animationsexample;

import android.opengl.GLES32;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;

import com.labwork.animationsexample.core.components.concrete.CameraPerspectiveComponent;
import com.labwork.animationsexample.core.components.concrete.RenderingComponent;
import com.labwork.animationsexample.demo.components.RotationComponent;
import com.labwork.animationsexample.core.components.concrete.TransformComponent;
import com.labwork.animationsexample.core.general.Color;
import com.labwork.animationsexample.core.general.Entity;
import com.labwork.animationsexample.core.general.Material;
import com.labwork.animationsexample.core.general.Mesh;
import com.labwork.animationsexample.core.general.Shader;
import com.labwork.animationsexample.demo.components.FixedOrientationControllerComponent;
import com.labwork.animationsexample.demo.shaders.Standalone;
import com.labwork.animationsexample.rendering.passes.concrete.OpaqueRenderPass;
import com.labwork.animationsexample.rendering.renderer.common.RendererProgrammable;
import com.labwork.animationsexample.rendering.renderer.concrete.SimpleProgrammableRenderer;
import com.labwork.animationsexample.rendering.viewport.common.ViewportConfigurable;
import com.labwork.animationsexample.rendering.viewport.concrete.Viewport;
import com.labwork.animationsexample.runtime.Framework;

import com.labwork.animationsexample.core.general.Scene;
import com.labwork.animationsexample.demo.components.NoClipControllerComponent;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int MENU_ITEM_SCENE_CUBES = 1;
    private static final int MENU_ITEM_SCENE_PYRAMID = 2;

    private Shader shader;
    private Scene cubesScene;
    private Scene pyramidScene;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        ViewportConfigurable viewport = new Viewport(this);
        RendererProgrammable renderer = new SimpleProgrammableRenderer(this::initializeAssets);

        super.onCreate(savedInstanceState);
        super.setContentView(viewport.getLayout());
        Framework.getInstance().initialize(renderer, viewport);
    }

    private void initializeAssets() {
        this.shader = new Shader(OpaqueRenderPass.class, Standalone.SHADER_VERT_SOURCE, Standalone.SHADER_FRAG_SOURCE);
        Framework.getInstance().getRenderer().registerRenderPass(new OpaqueRenderPass(this.shader));
        this.pyramidScene = this.initializePyramidScene();
        this.cubesScene = this.initializeCubesScene();
    }

    private Scene initializeCubesScene() {
        Scene scene = new Scene();
        Material material = new Material(new Color(255, 255, 255, 0), this.shader);

        Entity chessboard = new Entity();
        chessboard.addComponent(new TransformComponent(chessboard));
        Mesh chessboardMesh = new Mesh(this.generateChessboardVertices(), GLES32.GL_TRIANGLES);
        chessboard.addComponent(new RenderingComponent(chessboard, chessboardMesh, material));
        scene.addEntity(chessboard);

        float spacing = 2.0f;

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                for (int z = 0; z < 3; z++) {
                    Entity cube = new Entity();
                    TransformComponent transform = new TransformComponent(cube);
                    cube.addComponent(transform);
                    Mesh cubeMesh = new Mesh(this.generateCubeVertices(), GLES32.GL_TRIANGLES);
                    cube.addComponent(new RenderingComponent(cube, cubeMesh, material));

                    float startOffset = -spacing;
                    transform.getPosition().setX(startOffset + x * spacing);
                    transform.getPosition().setY(0.5f + y * spacing);
                    transform.getPosition().setZ(startOffset + z * spacing);

                    scene.addEntity(cube);
                }
            }
        }

        Entity camera = new Entity();
        camera.addComponent(new TransformComponent(camera));
        camera.addComponent(new NoClipControllerComponent(camera, new Button(this), new Button(this), new Button(this), new Button(this), new Button(this), new Button(this), new Button(this), new Button(this)));
        camera.addComponent(new CameraPerspectiveComponent(camera, new Color(27, 27, 27, 255), 0.001f, 100.0f, 90.0f, 90.0f));
        camera.getComponent(TransformComponent.class).getPosition().setY(3.0f);
        camera.getComponent(TransformComponent.class).getPosition().setZ(-7.0f);
        scene.addEntity(camera);

        return scene;
    }

    private Scene initializePyramidScene() {
        Scene scene = new Scene();
        Material material = new Material(new Color(255, 255, 255, 0), this.shader);

        Entity pyramid = new Entity();
        pyramid.addComponent(new RotationComponent(pyramid));
        pyramid.addComponent(new TransformComponent(pyramid));
        Mesh pyramidMesh = new Mesh(this.generatePyramidVertices(), GLES32.GL_TRIANGLES);
        pyramid.addComponent(new RenderingComponent(pyramid, pyramidMesh, material));

        pyramid.getComponent(TransformComponent.class).getPosition().setY(0.5f);

        Entity chessboard = new Entity();
        chessboard.addComponent(new TransformComponent(chessboard));
        Mesh chessboardMesh = new Mesh(this.generateChessboardVertices(), GLES32.GL_TRIANGLES);
        chessboard.addComponent(new RenderingComponent(chessboard, chessboardMesh, material));

        Entity camera = new Entity();
        camera.addComponent(new TransformComponent(camera));
        camera.addComponent(new FixedOrientationControllerComponent(camera, new Button(this), new Button(this), new Button(this), new Button(this), new Button(this), new Button(this)));
        camera.addComponent(new CameraPerspectiveComponent(camera, new Color(27, 27, 27, 255), 0.001f, 100.0f, 90.0f, 90.0f));
        camera.getComponent(TransformComponent.class).getPosition().setY(1.0f);
        camera.getComponent(TransformComponent.class).getPosition().setZ(-5.0f);

        scene.addEntity(camera);
        scene.addEntity(pyramid);
        scene.addEntity(chessboard);

        return scene;
    }

    private float[] generateCubeVertices() {
        return new float[] {
                // Front face
                -0.5f, -0.5f,  0.5f, 1.0f, 0.0f, 0.0f, 1.0f,
                0.5f, -0.5f,  0.5f, 1.0f, 0.0f, 0.0f, 1.0f,
                0.5f,  0.5f,  0.5f, 1.0f, 0.0f, 0.0f, 1.0f,
                0.5f,  0.5f,  0.5f, 1.0f, 0.0f, 0.0f, 1.0f,
                -0.5f,  0.5f,  0.5f, 1.0f, 0.0f, 0.0f, 1.0f,
                -0.5f, -0.5f,  0.5f, 1.0f, 0.0f, 0.0f, 1.0f,

                // Back face
                -0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 1.0f,
                0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 1.0f,
                0.5f,  0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 1.0f,
                0.5f,  0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 1.0f,
                -0.5f,  0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 1.0f,
                -0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 1.0f,

                // Left face
                -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 1.0f,
                -0.5f, -0.5f,  0.5f, 0.0f, 0.0f, 1.0f, 1.0f,
                -0.5f,  0.5f,  0.5f, 0.0f, 0.0f, 1.0f, 1.0f,
                -0.5f,  0.5f,  0.5f, 0.0f, 0.0f, 1.0f, 1.0f,
                -0.5f,  0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 1.0f,
                -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 1.0f,

                // Right face
                0.5f, -0.5f, -0.5f, 1.0f, 1.0f, 0.0f, 1.0f,
                0.5f, -0.5f,  0.5f, 1.0f, 1.0f, 0.0f, 1.0f,
                0.5f,  0.5f,  0.5f, 1.0f, 1.0f, 0.0f, 1.0f,
                0.5f,  0.5f,  0.5f, 1.0f, 1.0f, 0.0f, 1.0f,
                0.5f,  0.5f, -0.5f, 1.0f, 1.0f, 0.0f, 1.0f,
                0.5f, -0.5f, -0.5f, 1.0f, 1.0f, 0.0f, 1.0f,

                // Top face
                -0.5f,  0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 1.0f,
                0.5f,  0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 1.0f,
                0.5f,  0.5f,  0.5f, 0.0f, 1.0f, 1.0f, 1.0f,
                0.5f,  0.5f,  0.5f, 0.0f, 1.0f, 1.0f, 1.0f,
                -0.5f,  0.5f,  0.5f, 0.0f, 1.0f, 1.0f, 1.0f,
                -0.5f,  0.5f, -0.5f, 0.0f, 1.0f, 1.0f, 1.0f,

                // Bottom face
                -0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 1.0f, 1.0f,
                0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 1.0f, 1.0f,
                0.5f, -0.5f,  0.5f, 1.0f, 0.0f, 1.0f, 1.0f,
                0.5f, -0.5f,  0.5f, 1.0f, 0.0f, 1.0f, 1.0f,
                -0.5f, -0.5f,  0.5f, 1.0f, 0.0f, 1.0f, 1.0f,
                -0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 1.0f, 1.0f
        };
    }

    private float[] generatePyramidVertices() {
        return new float[] {
                -1.0f, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f,  // Bottom-left
                1.0f, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f,  // Bottom-right
                1.0f, -1.0f,  1.0f, 1.0f, 0.0f, 0.0f, 1.0f,  // Top-right
                1.0f, -1.0f,  1.0f, 1.0f, 0.0f, 0.0f, 1.0f,  // Top-right
                -1.0f, -1.0f,  1.0f, 1.0f, 0.0f, 0.0f, 1.0f,  // Top-left
                -1.0f, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f,  // Bottom-left

                -1.0f, -1.0f, -1.0f, 0.0f, 1.0f, 0.0f, 1.0f,  // Bottom-left
                1.0f, -1.0f, -1.0f, 0.0f, 1.0f, 0.0f, 1.0f,  // Bottom-right
                0.0f,  1.0f,  0.0f, 0.0f, 1.0f, 0.0f, 1.0f,  // Apex

                1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f,  // Bottom-right
                1.0f, -1.0f,  1.0f, 0.0f, 0.0f, 1.0f, 1.0f,  // Top-right
                0.0f,  1.0f,  0.0f, 0.0f, 0.0f, 1.0f, 1.0f,  // Apex

                1.0f, -1.0f,  1.0f, 1.0f, 1.0f, 0.0f, 1.0f,  // Top-right
                -1.0f, -1.0f,  1.0f, 1.0f, 1.0f, 0.0f, 1.0f,  // Top-left
                0.0f,  1.0f,  0.0f, 1.0f, 1.0f, 0.0f, 1.0f,  // Apex

                -1.0f, -1.0f,  1.0f, 1.0f, 0.0f, 0.0f, 0.0f,  // Top-left
                -1.0f, -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f,  // Bottom-left
                0.0f,  1.0f,  0.0f, 1.0f, 0.0f, 0.0f, 0.0f   // Apex
        };
    }

    private float[] generateChessboardVertices() {
        List<Float> vertices = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                float r = (i + j) % 2 == 0 ? 1.0f : 0.0f;
                float g = (i + j) % 2 == 0 ? 1.0f : 0.0f;
                float b = (i + j) % 2 == 0 ? 1.0f : 0.0f;

                float y = 0.0f;
                float x1 = i - 4.0f;
                float x2 = i - 3.0f;
                float z1 = j - 4.0f;
                float z2 = j - 3.0f;

                vertices.add(x1); vertices.add(y); vertices.add(z1); vertices.add(r); vertices.add(g); vertices.add(b); vertices.add(1.0f);
                vertices.add(x2); vertices.add(y); vertices.add(z1); vertices.add(r); vertices.add(g); vertices.add(b); vertices.add(1.0f);
                vertices.add(x2); vertices.add(y); vertices.add(z2); vertices.add(r); vertices.add(g); vertices.add(b); vertices.add(1.0f);

                vertices.add(x2); vertices.add(y); vertices.add(z2); vertices.add(r); vertices.add(g); vertices.add(b); vertices.add(1.0f);
                vertices.add(x1); vertices.add(y); vertices.add(z2); vertices.add(r); vertices.add(g); vertices.add(b); vertices.add(1.0f);
                vertices.add(x1); vertices.add(y); vertices.add(z1); vertices.add(r); vertices.add(g); vertices.add(b); vertices.add(1.0f);
            }
        }

        float[] result = new float[vertices.size()];

        for (int i = 0; i < vertices.size(); i++) {
            result[i] = vertices.get(i);
        }

        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MainActivity.MENU_ITEM_SCENE_CUBES, 0, "Cubes");
        menu.add(0, MainActivity.MENU_ITEM_SCENE_PYRAMID, 0, "Pyramid");
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
