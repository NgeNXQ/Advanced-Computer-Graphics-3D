package com.labwork.texturesexample.demo.shaders;

public final class Standalone {

    public static final String SHADER_OPAQUE_VERT_SOURCE =
            "#version 300 es\n" +

            "in vec3 aVertexColorRGB;\n" +
            "in vec3 aVertexNormalLocal;\n" +
            "in vec3 aVertexPositionLocal;\n" +
            "in vec2 aVertexTextureCoordinate;\n" +

            "uniform mat4 uMatrixView;\n" +
            "uniform mat4 uMatrixModel;\n" +
            "uniform mat4 uMatrixProjection;\n" +

             "out vec3 vVertexColorRGB;\n" +
             "out vec2 vVertexTextureCoordinate;\n" +

             "void main() {\n" +
             "    gl_Position = uMatrixProjection * uMatrixView * uMatrixModel * vec4(aVertexPositionLocal, 1.0);\n" +

             "    vVertexColorRGB = aVertexColorRGB;\n" +
             "    vVertexTextureCoordinate = aVertexTextureCoordinate;\n" +
             "}\n";

    public static final String SHADER_OPAQUE_FRAG_SOURCE =
            "#version 300 es\n" +
            "precision mediump float;\n" +

            "in vec3 vVertexColorRGB;\n" +
            "in vec2 vVertexTextureCoordinate;\n" +

            "uniform vec4 uMaterialColorRGBA;\n" +
            "uniform sampler2D uTextureAlbedo;\n" +

            "out vec4 outFragmentColor;\n" +

            "void main() {\n" +
            "    outFragmentColor = texture(uTextureAlbedo, vVertexTextureCoordinate);\n" +
            "}\n";

    public static final String SHADER_SKYBOX_VERT_SOURCE =
            "#version 300 es\n" +

            "in vec3 aVertexColorRGB;\n" +
            "in vec3 aVertexNormalLocal;\n" +
            "in vec3 aVertexPositionLocal;\n" +
            "in vec2 aVertexTextureCoordinate;\n" +

            "uniform mat4 uMatrixView;\n" +
            "uniform mat4 uMatrixModel;\n" +
            "uniform mat4 uMatrixProjection;\n" +

            "out vec3 vTextureCoordinate;\n" +

            "void main() {\n" +
            "    mat4 viewNoTranslation = mat4(mat3(uMatrixView));\n" +
            "    gl_Position = uMatrixProjection * viewNoTranslation * uMatrixModel * vec4(aVertexPositionLocal, 1.0);\n" +

            "    vTextureCoordinate = aVertexPositionLocal;\n" +
            "}\n";

    public static final String SHADER_SKYBOX_FRAG_SOURCE =
            "#version 300 es\n" +
            "precision mediump float;\n" +

            "in vec3 vTextureCoordinate;\n" +

            "uniform samplerCube uCubeTexture;\n" +

            "out vec4 outFragmentColor;\n" +

            "void main() {\n" +
            "    outFragmentColor = texture(uCubeTexture, vTextureCoordinate);\n" +
            "}\n";
}