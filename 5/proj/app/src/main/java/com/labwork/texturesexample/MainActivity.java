package com.labwork.texturesexample;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.opengl.GLES32;
import androidx.appcompat.app.AppCompatActivity;
import com.labwork.texturesexample.runtime.Framework;
import com.labwork.texturesexample.core.general.Mesh;
import com.labwork.texturesexample.core.general.Color;
import com.labwork.texturesexample.core.general.Scene;
import com.labwork.texturesexample.core.general.Entity;
import com.labwork.texturesexample.core.general.Shader;
import com.labwork.texturesexample.core.general.Texture2D;
import com.labwork.texturesexample.core.general.Texture3D;
import com.labwork.texturesexample.core.general.Material;
import com.labwork.texturesexample.demo.components.NoClipControllerComponent;
import com.labwork.texturesexample.core.components.concrete.TransformComponent;
import com.labwork.texturesexample.core.components.concrete.SkyboxRenderingComponent;
import com.labwork.texturesexample.core.components.concrete.OpaqueRenderingComponent;
import com.labwork.texturesexample.core.components.concrete.CameraPerspectiveComponent;
import com.labwork.texturesexample.demo.shaders.Standalone;
import com.labwork.texturesexample.rendering.renderer.concrete.ForwardRenderer;
import com.labwork.texturesexample.rendering.renderer.common.RendererProgrammable;
import com.labwork.texturesexample.rendering.features.concrete.OpaqueRenderFeature;
import com.labwork.texturesexample.rendering.features.concrete.SkyboxRenderFeature;
import com.labwork.texturesexample.rendering.viewport.concrete.Viewport;
import com.labwork.texturesexample.rendering.viewport.common.ViewportConfigurable;

public class MainActivity extends AppCompatActivity {
    private static final int MENU_ITEM_SCENE_EARTH = 1;
    private static final int MENU_ITEM_SCENE_TORUS = 2;
    private static final int MENU_ITEM_SCENE_SKYBOX = 3;

    private Scene earthScene;
    private Scene torusScene;
    private Scene skyboxScene;
    private Shader opaqueShader;
    private Shader skyboxShader;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        ViewportConfigurable viewport = new Viewport(this);
        RendererProgrammable renderer = new ForwardRenderer(this::initializeAssets);

        super.onCreate(savedInstanceState);
        super.setContentView(viewport.getLayout());
        Framework.getInstance().initialize(renderer, viewport);
    }

    private void initializeAssets() {
        this.opaqueShader = new Shader(OpaqueRenderFeature.class, Standalone.SHADER_OPAQUE_VERT_SOURCE, Standalone.SHADER_OPAQUE_FRAG_SOURCE);
        this.skyboxShader = new Shader(SkyboxRenderFeature.class, Standalone.SHADER_SKYBOX_VERT_SOURCE, Standalone.SHADER_SKYBOX_FRAG_SOURCE);

        Framework.getInstance().getRenderer().registerRenderFeature(new SkyboxRenderFeature(this.skyboxShader));
        Framework.getInstance().getRenderer().registerRenderFeature(new OpaqueRenderFeature(this.opaqueShader));

        this.earthScene = this.initializeEarthScene();
        this.torusScene = this.initializeTorusScene();
        this.skyboxScene = this.initializeSkyboxScene();
    }

    private Scene initializeEarthScene() {
        Scene scene = new Scene();
        Material material = new Material(this.opaqueShader, new Color(255, 255, 255, 0), new Texture2D(this, R.drawable.earth, GLES32.GL_CLAMP_TO_EDGE, GLES32.GL_LINEAR), 0.3f, 0.7f, 0.0f);

        Entity sphere = new Entity();
        sphere.addComponent(new TransformComponent(sphere));
        Mesh sphereMesh = new Mesh(this.generateEarthVertices(), this.generateEarthIndices(), GLES32.GL_TRIANGLES);
        sphere.addComponent(new OpaqueRenderingComponent(sphere, sphereMesh, material));
        scene.addEntity(sphere);

        Entity camera = new Entity();
        camera.addComponent(new TransformComponent(camera));
        camera.addComponent(new CameraPerspectiveComponent(camera, new Color(27, 27, 27, 255), 0.001f, 100.0f, 90.0f, 90.0f));
        camera.addComponent(new NoClipControllerComponent(camera, new Button(this), new Button(this), new Button(this),
                new Button(this), new Button(this), new Button(this), new Button(this), new Button(this)));
        scene.addEntity(camera);

        camera.getComponent(TransformComponent.class).getPosition().setY(1.5f);
        camera.getComponent(TransformComponent.class).getPosition().setZ(-3.0f);
        camera.getComponent(TransformComponent.class).getRotation().setX(15.0f);

        return scene;
    }

    private Scene initializeTorusScene() {
        Scene scene = new Scene();

        Material torusMaterial = new Material(this.opaqueShader, new Color(255, 255, 255, 0), new Texture2D(this, R.drawable.torus, GLES32.GL_CLAMP_TO_EDGE, GLES32.GL_LINEAR), 0.3f, 0.7f, 0.0f);
        Entity torus = new Entity();
        torus.addComponent(new TransformComponent(torus));
        Mesh torusMesh = new Mesh(this.generateTorusVertices(), this.generateTorusIndices(), GLES32.GL_TRIANGLES);
        torus.addComponent(new OpaqueRenderingComponent(torus, torusMesh, torusMaterial));
        scene.addEntity(torus);

        Material planeMaterial = new Material(this.opaqueShader, new Color(255, 255, 255, 0), new Texture2D(this, R.drawable.chess, GLES32.GL_REPEAT, GLES32.GL_LINEAR), 0.3f, 0.7f, 0.0f);
        Entity plane = new Entity();
        plane.addComponent(new TransformComponent(plane));
        Mesh planeMesh = new Mesh(this.generatePlaneVertices(), this.generatePlaneIndices(), GLES32.GL_TRIANGLES);
        plane.addComponent(new OpaqueRenderingComponent(plane, planeMesh, planeMaterial));
        scene.addEntity(plane);

        plane.getComponent(TransformComponent.class).getPosition().setY(-0.5f);

        Entity camera = new Entity();
        camera.addComponent(new TransformComponent(camera));
        camera.addComponent(new CameraPerspectiveComponent(camera, new Color(27, 27, 27, 255), 0.001f, 100.0f, 90.0f, 90.0f));
        camera.addComponent(new NoClipControllerComponent(camera, new Button(this), new Button(this), new Button(this), new Button(this), new Button(this), new Button(this), new Button(this), new Button(this)));
        scene.addEntity(camera);

        camera.getComponent(TransformComponent.class).getPosition().setY(1.5f);
        camera.getComponent(TransformComponent.class).getPosition().setZ(-3.0f);
        camera.getComponent(TransformComponent.class).getRotation().setX(15.0f);

        return scene;
    }

    private Scene initializeSkyboxScene() {
        Scene scene = new Scene();

        Material torusMaterial = new Material(this.opaqueShader, new Color(255, 255, 255, 0), new Texture2D(this, R.drawable.torus, GLES32.GL_CLAMP_TO_EDGE, GLES32.GL_LINEAR), 0.3f, 0.7f, 0.0f);
        Entity torus = new Entity();
        torus.addComponent(new TransformComponent(torus));
        Mesh torusMesh = new Mesh(this.generateTorusVertices(), this.generateTorusIndices(), GLES32.GL_TRIANGLES);
        torus.addComponent(new OpaqueRenderingComponent(torus, torusMesh, torusMaterial));
        scene.addEntity(torus);

        Material planeMaterial = new Material(this.opaqueShader, new Color(255, 255, 255, 0), new Texture2D(this, R.drawable.chess, GLES32.GL_REPEAT, GLES32.GL_LINEAR), 0.3f, 0.7f, 0.0f);
        Entity plane = new Entity();
        plane.addComponent(new TransformComponent(plane));
        Mesh planeMesh = new Mesh(this.generatePlaneVertices(), this.generatePlaneIndices(), GLES32.GL_TRIANGLES);
        plane.addComponent(new OpaqueRenderingComponent(plane, planeMesh, planeMaterial));
        scene.addEntity(plane);
        plane.getComponent(TransformComponent.class).getPosition().setY(-0.5f);

        int[] skyboxTextures = {
                R.drawable.skybox_right,
                R.drawable.skybox_left,
                R.drawable.skybox_top,
                R.drawable.skybox_bottom,
                R.drawable.skybox_front,
                R.drawable.skybox_back
        };

        Texture3D skyboxTexture = new Texture3D(this, skyboxTextures, GLES32.GL_CLAMP_TO_EDGE, GLES32.GL_LINEAR);
        Material skyboxMaterial = new Material(this.skyboxShader, new Color(255, 255, 255, 255), null, 0.0f, 0.0f, 0.0f);
        Mesh skyboxMesh = new Mesh(generateSkyboxCubeVertices(), generateSkyboxCubeIndices(), GLES32.GL_TRIANGLES);
        Entity skybox = new Entity();
        skybox.addComponent(new TransformComponent(skybox));
        skybox.addComponent(new SkyboxRenderingComponent(skybox, skyboxMesh, skyboxMaterial, skyboxTexture));
        scene.addEntity(skybox);

        Entity camera = new Entity();
        camera.addComponent(new TransformComponent(camera));
        camera.addComponent(new CameraPerspectiveComponent(camera, new Color(27, 27, 27, 255), 0.001f, 100.0f, 90.0f, 90.0f));
        camera.addComponent(new NoClipControllerComponent(camera, new Button(this), new Button(this), new Button(this), new Button(this), new Button(this), new Button(this), new Button(this), new Button(this)));
        scene.addEntity(camera);

        camera.getComponent(TransformComponent.class).getPosition().setY(1.5f);
        camera.getComponent(TransformComponent.class).getPosition().setZ(-3.0f);
        camera.getComponent(TransformComponent.class).getRotation().setX(15.0f);

        return scene;
    }

    private float[] generateSkyboxCubeVertices() {
        int index = 0;
        float size = 50.0f;
        float[] vertices = new float[24 * 11];

        vertices[index++] = -size; vertices[index++] = -size; vertices[index++] = size;
        vertices[index++] = 0.0f; vertices[index++] = 0.0f;
        vertices[index++] = 0.0f; vertices[index++] = 0.0f; vertices[index++] = 1.0f;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f; vertices[index++] = 1.0f;

        vertices[index++] = size; vertices[index++] = -size; vertices[index++] = size;
        vertices[index++] = 1.0f; vertices[index++] = 0.0f;
        vertices[index++] = 0.0f; vertices[index++] = 0.0f; vertices[index++] = 1.0f;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f; vertices[index++] = 1.0f;

        vertices[index++] = size; vertices[index++] = size; vertices[index++] = size;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f;
        vertices[index++] = 0.0f; vertices[index++] = 0.0f; vertices[index++] = 1.0f;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f; vertices[index++] = 1.0f;

        vertices[index++] = -size; vertices[index++] = size; vertices[index++] = size;
        vertices[index++] = 0.0f; vertices[index++] = 1.0f;
        vertices[index++] = 0.0f; vertices[index++] = 0.0f; vertices[index++] = 1.0f;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f; vertices[index++] = 1.0f;

        vertices[index++] = -size; vertices[index++] = -size; vertices[index++] = -size;
        vertices[index++] = 0.0f; vertices[index++] = 0.0f;
        vertices[index++] = 0.0f; vertices[index++] = 0.0f; vertices[index++] = -1.0f;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f; vertices[index++] = 1.0f;

        vertices[index++] = size; vertices[index++] = -size; vertices[index++] = -size;
        vertices[index++] = 1.0f; vertices[index++] = 0.0f;
        vertices[index++] = 0.0f; vertices[index++] = 0.0f; vertices[index++] = -1.0f;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f; vertices[index++] = 1.0f;

        vertices[index++] = size; vertices[index++] = size; vertices[index++] = -size;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f;
        vertices[index++] = 0.0f; vertices[index++] = 0.0f; vertices[index++] = -1.0f;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f; vertices[index++] = 1.0f;

        vertices[index++] = -size; vertices[index++] = size; vertices[index++] = -size;
        vertices[index++] = 0.0f; vertices[index++] = 1.0f;
        vertices[index++] = 0.0f; vertices[index++] = 0.0f; vertices[index++] = -1.0f;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f; vertices[index++] = 1.0f;

        vertices[index++] = -size; vertices[index++] = -size; vertices[index++] = -size;
        vertices[index++] = 0.0f; vertices[index++] = 0.0f;
        vertices[index++] = -1.0f; vertices[index++] = 0.0f; vertices[index++] = 0.0f;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f; vertices[index++] = 1.0f;

        vertices[index++] = -size; vertices[index++] = -size; vertices[index++] = size;
        vertices[index++] = 1.0f; vertices[index++] = 0.0f;
        vertices[index++] = -1.0f; vertices[index++] = 0.0f; vertices[index++] = 0.0f;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f; vertices[index++] = 1.0f;

        vertices[index++] = -size; vertices[index++] = size; vertices[index++] = size;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f;
        vertices[index++] = -1.0f; vertices[index++] = 0.0f; vertices[index++] = 0.0f;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f; vertices[index++] = 1.0f;

        vertices[index++] = -size; vertices[index++] = size; vertices[index++] = -size;
        vertices[index++] = 0.0f; vertices[index++] = 1.0f;
        vertices[index++] = -1.0f; vertices[index++] = 0.0f; vertices[index++] = 0.0f;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f; vertices[index++] = 1.0f;

        vertices[index++] = size; vertices[index++] = -size; vertices[index++] = size;
        vertices[index++] = 0.0f; vertices[index++] = 0.0f;
        vertices[index++] = 1.0f; vertices[index++] = 0.0f; vertices[index++] = 0.0f;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f; vertices[index++] = 1.0f;

        vertices[index++] = size; vertices[index++] = -size; vertices[index++] = -size;
        vertices[index++] = 1.0f; vertices[index++] = 0.0f;
        vertices[index++] = 1.0f; vertices[index++] = 0.0f; vertices[index++] = 0.0f;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f; vertices[index++] = 1.0f;

        vertices[index++] = size; vertices[index++] = size; vertices[index++] = -size;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f;
        vertices[index++] = 1.0f; vertices[index++] = 0.0f; vertices[index++] = 0.0f;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f; vertices[index++] = 1.0f;

        vertices[index++] = size; vertices[index++] = size; vertices[index++] = size;
        vertices[index++] = 0.0f; vertices[index++] = 1.0f;
        vertices[index++] = 1.0f; vertices[index++] = 0.0f; vertices[index++] = 0.0f;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f; vertices[index++] = 1.0f;

        vertices[index++] = -size; vertices[index++] = size; vertices[index++] = size;
        vertices[index++] = 0.0f; vertices[index++] = 0.0f;
        vertices[index++] = 0.0f; vertices[index++] = 1.0f; vertices[index++] = 0.0f;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f; vertices[index++] = 1.0f;

        vertices[index++] = size; vertices[index++] = size; vertices[index++] = size;
        vertices[index++] = 1.0f; vertices[index++] = 0.0f;
        vertices[index++] = 0.0f; vertices[index++] = 1.0f; vertices[index++] = 0.0f;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f; vertices[index++] = 1.0f;

        vertices[index++] = size; vertices[index++] = size; vertices[index++] = -size;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f;
        vertices[index++] = 0.0f; vertices[index++] = 1.0f; vertices[index++] = 0.0f;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f; vertices[index++] = 1.0f;

        vertices[index++] = -size; vertices[index++] = size; vertices[index++] = -size;
        vertices[index++] = 0.0f; vertices[index++] = 1.0f;
        vertices[index++] = 0.0f; vertices[index++] = 1.0f; vertices[index++] = 0.0f;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f; vertices[index++] = 1.0f;

        vertices[index++] = -size; vertices[index++] = -size; vertices[index++] = -size;
        vertices[index++] = 0.0f; vertices[index++] = 0.0f;
        vertices[index++] = 0.0f; vertices[index++] = -1.0f; vertices[index++] = 0.0f;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f; vertices[index++] = 1.0f;

        vertices[index++] = size; vertices[index++] = -size; vertices[index++] = -size;
        vertices[index++] = 1.0f; vertices[index++] = 0.0f;
        vertices[index++] = 0.0f; vertices[index++] = -1.0f; vertices[index++] = 0.0f;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f; vertices[index++] = 1.0f;

        vertices[index++] = size; vertices[index++] = -size; vertices[index++] = size;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f;
        vertices[index++] = 0.0f; vertices[index++] = -1.0f; vertices[index++] = 0.0f;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f; vertices[index++] = 1.0f;

        vertices[index++] = -size; vertices[index++] = -size; vertices[index++] = size;
        vertices[index++] = 0.0f; vertices[index++] = 1.0f;
        vertices[index++] = 0.0f; vertices[index++] = -1.0f; vertices[index++] = 0.0f;
        vertices[index++] = 1.0f; vertices[index++] = 1.0f; vertices[index++] = 1.0f;

        return vertices;
    }

    private int[] generateSkyboxCubeIndices() {
        return new int[] {
                0, 1, 2,  0, 2, 3,      // Front
                4, 5, 6,  4, 6, 7,      // Back
                8, 9, 10, 8, 10, 11,    // Left
                12, 13, 14, 12, 14, 15, // Right
                16, 17, 18, 16, 18, 19, // Top
                20, 21, 22, 20, 22, 23  // Bottom
        };
    }

    private float[] generateEarthVertices() {
        int latitudeBands = 32;
        int longitudeBands = 64;
        float radius = 1.0f;

        int vertexCount = (latitudeBands + 1) * (longitudeBands + 1);
        float[] vertices = new float[vertexCount * 11];

        int index = 0;
        for (int lat = 0; lat <= latitudeBands; lat++) {
            float theta = lat * (float)Math.PI / latitudeBands;
            float sinTheta = (float)Math.sin(theta);
            float cosTheta = (float)Math.cos(theta);

            for (int lon = 0; lon <= longitudeBands; lon++) {
                float phi = lon * 2 * (float)Math.PI / longitudeBands;
                float sinPhi = (float)Math.sin(phi);
                float cosPhi = (float)Math.cos(phi);

                float x = cosPhi * sinTheta;
                float y = cosTheta;
                float z = sinPhi * sinTheta;

                float u = 1.0f - ((float)lon / longitudeBands);
                float v = (float)lat / latitudeBands;

                vertices[index++] = x * radius;
                vertices[index++] = y * radius;
                vertices[index++] = z * radius;
                vertices[index++] = u;
                vertices[index++] = v;
                vertices[index++] = x;
                vertices[index++] = y;
                vertices[index++] = z;
                vertices[index++] = 1.0f;
                vertices[index++] = 1.0f;
                vertices[index++] = 1.0f;
            }
        }

        return vertices;
    }

    private int[] generateEarthIndices() {
        int latitudeBands = 32;
        int longitudeBands = 64;
        int indexCount = latitudeBands * longitudeBands * 6;
        int[] indices = new int[indexCount];
        int index = 0;

        for (int lat = 0; lat < latitudeBands; lat++) {
            for (int lon = 0; lon < longitudeBands; lon++) {
                int first = (lat * (longitudeBands + 1)) + lon;
                int second = first + longitudeBands + 1;

                indices[index++] = first;
                indices[index++] = second;
                indices[index++] = first + 1;

                indices[index++] = second;
                indices[index++] = second + 1;
                indices[index++] = first + 1;
            }
        }
        return indices;
    }

    private float[] generateTorusVertices() {
        int radialSegments = 32;
        int tubularSegments = 64;
        float radius = 1.5f;
        float tubeRadius = 0.5f;

        int vertexCount = (radialSegments + 1) * (tubularSegments + 1);
        float[] vertices = new float[vertexCount * 11];
        int index = 0;

        for (int i = 0; i <= radialSegments; i++) {
            float u = i / (float)radialSegments * (float)Math.PI * 2;
            float cosU = (float)Math.cos(u);
            float sinU = (float)Math.sin(u);

            for (int j = 0; j <= tubularSegments; j++) {
                float v = j / (float)tubularSegments * (float)Math.PI * 2;
                float cosV = (float)Math.cos(v);
                float sinV = (float)Math.sin(v);

                float x = (radius + tubeRadius * cosV) * cosU;
                float y = tubeRadius * sinV;
                float z = (radius + tubeRadius * cosV) * sinU;

                float nx = cosV * cosU;
                float ny = sinV;
                float nz = cosV * sinU;

                float texU = (float)i / radialSegments;
                float texV = (float)j / tubularSegments;

                vertices[index++] = x;
                vertices[index++] = y;
                vertices[index++] = z;
                vertices[index++] = texU;
                vertices[index++] = texV;
                vertices[index++] = nx;
                vertices[index++] = ny;
                vertices[index++] = nz;
                vertices[index++] = 1.0f;
                vertices[index++] = 1.0f;
                vertices[index++] = 1.0f;
            }
        }

        return vertices;
    }

    private int[] generateTorusIndices() {
        int radialSegments = 32;
        int tubularSegments = 64;
        int indexCount = radialSegments * tubularSegments * 6;
        int[] indices = new int[indexCount];
        int index = 0;

        for (int i = 0; i < radialSegments; i++) {
            for (int j = 0; j < tubularSegments; j++) {
                int a = (tubularSegments + 1) * i + j;
                int b = (tubularSegments + 1) * (i + 1) + j;
                int c = (tubularSegments + 1) * (i + 1) + j + 1;
                int d = (tubularSegments + 1) * i + j + 1;

                indices[index++] = a;
                indices[index++] = b;
                indices[index++] = d;

                indices[index++] = b;
                indices[index++] = c;
                indices[index++] = d;
            }
        }

        return indices;
    }

    private float[] generatePlaneVertices() {
        float size = 2.25f;
        float repeatFactor = 1.5f;

        int index = 0;
        float[] vertices = new float[4 * 11];

        vertices[index++] = -size / 2;
        vertices[index++] = 0.0f;
        vertices[index++] = -size / 2;
        vertices[index++] = 0.0f;
        vertices[index++] = 0.0f;
        vertices[index++] = 0.0f;
        vertices[index++] = 1.0f;
        vertices[index++] = 0.0f;
        vertices[index++] = 1.0f;
        vertices[index++] = 1.0f;
        vertices[index++] = 1.0f;

        vertices[index++] = size / 2;
        vertices[index++] = 0.0f;
        vertices[index++] = -size / 2;
        vertices[index++] = repeatFactor * 2;
        vertices[index++] = 0.0f;
        vertices[index++] = 0.0f;
        vertices[index++] = 1.0f;
        vertices[index++] = 0.0f;
        vertices[index++] = 1.0f;
        vertices[index++] = 1.0f;
        vertices[index++] = 1.0f;

        vertices[index++] = size / 2;
        vertices[index++] = 0.0f;
        vertices[index++] = size / 2;
        vertices[index++] = repeatFactor * 2;
        vertices[index++] = repeatFactor * 2;
        vertices[index++] = 0.0f;
        vertices[index++] = 1.0f;
        vertices[index++] = 0.0f;
        vertices[index++] = 1.0f;
        vertices[index++] = 1.0f;
        vertices[index++] = 1.0f;

        vertices[index++] = -size / 2;
        vertices[index++] = 0.0f;
        vertices[index++] = size / 2;
        vertices[index++] = 0.0f;
        vertices[index++] = repeatFactor * 2;
        vertices[index++] = 0.0f;
        vertices[index++] = 1.0f;
        vertices[index++] = 0.0f;
        vertices[index++] = 1.0f;
        vertices[index++] = 1.0f;
        vertices[index++] = 1.0f;

        return vertices;
    }

    private int[] generatePlaneIndices() {
        int[] indices = new int[6];
        indices[0] = 0; // Bottom-left
        indices[1] = 1; // Bottom-right
        indices[2] = 2; // Top-right
        indices[3] = 0; // Bottom-left
        indices[4] = 2; // Top-right
        indices[5] = 3; // Top-left
        return indices;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_ITEM_SCENE_EARTH, 0, "Earth");
        menu.add(0, MENU_ITEM_SCENE_TORUS, 0, "Torus");
        menu.add(0, MENU_ITEM_SCENE_SKYBOX, 0, "Skybox");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.setTitle(item.getTitle());
        switch (item.getItemId()) {
            case MENU_ITEM_SCENE_EARTH:
                Framework.getInstance().loadScene(this.earthScene);
                return true;
            case MENU_ITEM_SCENE_TORUS:
                Framework.getInstance().loadScene(this.torusScene);
                return true;
            case MENU_ITEM_SCENE_SKYBOX:
                Framework.getInstance().loadScene(this.skyboxScene);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}