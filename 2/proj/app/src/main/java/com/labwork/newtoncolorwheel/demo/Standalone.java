package com.labwork.newtoncolorwheel.demo;

public final class Standalone {

    public static final String SHADER_VERT_SOURCE =
            "#version 300 es\n" +
            "in vec4 inVertexColor;\n" +
            "in vec3 inVertexPosition;\n" +
            "uniform mat4 uMatrixMVP;\n" +
            "uniform vec4 uMaterialAlbedoColor;\n" +
            "out vec4 vVertexColor;\n" +
            "out vec4 vMaterialAlbedoColor;\n" +
            "void main() {\n" +
            "   gl_Position = uMatrixMVP * vec4(inVertexPosition, 1.0);\n" +
            "   vVertexColor = inVertexColor;\n" +
            "   vMaterialAlbedoColor = uMaterialAlbedoColor;\n" +
            "}\n";

    public static final String SHADER_FRAG_SOURCE =
            "#version 300 es\n" +
            "precision mediump float;\n" +
            "in vec4 vVertexColor;\n" +
            "in vec4 vMaterialAlbedoColor;\n" +
            "out vec4 outFragmentColor;\n" +
            "void main() {\n" +
            "    outFragmentColor = vVertexColor * vMaterialAlbedoColor;\n" +
            "}\n";
}
