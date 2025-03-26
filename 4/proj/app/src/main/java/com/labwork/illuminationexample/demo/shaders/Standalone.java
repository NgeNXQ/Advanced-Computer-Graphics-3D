package com.labwork.illuminationexample.demo.shaders;

public final class Standalone {

    public static final String SHADER_VERT_SOURCE =
        "#version 300 es\n" +

        "in vec4 inVertexColorRGBA;\n" +
        "in vec3 inVertexNormalLocal;\n" +
        "in vec3 inVertexPositionLocal;\n" +

        "uniform mat4 uMatrixView;\n" +
        "uniform mat4 uMatrixModel;\n" +
        "uniform mat4 uMatrixProjection;\n" +
        "uniform vec3 uTransformLightPositionGlobal;\n" +
        "uniform vec3 uTransformCameraPositionGlobal;\n" +

        "out vec4 vVertexColorRGBA;\n" +
        "out vec3 vVertexPositionGlobal;\n" +
        "out vec3 vVertexNormalGlobalNormalized;\n" +
        "out vec3 vTransformLightPositionGlobal;\n" +
        "out vec3 vTransformCameraPositionGlobal;\n" +

        "void main() {\n" +
        "    gl_PointSize = 25.0f;\n" +
        "    gl_Position = uMatrixProjection * uMatrixView * uMatrixModel * vec4(inVertexPositionLocal, 1.0);\n" +

        "    vVertexPositionGlobal = mat3(uMatrixModel) * inVertexPositionLocal;\n" +
        "    vVertexNormalGlobalNormalized = normalize(mat3(uMatrixModel) * inVertexNormalLocal);\n" +

        "    vVertexColorRGBA = inVertexColorRGBA;\n" +
        "    vTransformLightPositionGlobal = uTransformLightPositionGlobal;\n" +
        "    vTransformCameraPositionGlobal = uTransformCameraPositionGlobal;\n" +
        "}\n";

    public static final String SHADER_FRAG_SOURCE =
        "#version 300 es\n" +
        "precision mediump float;\n" +

        "in vec4 vVertexColorRGBA;\n" +
        "in vec3 vVertexPositionGlobal;\n" +
        "in vec3 vVertexNormalGlobalNormalized;\n" +
        "in vec3 vTransformLightPositionGlobal;\n" +
        "in vec3 vTransformCameraPositionGlobal;\n" +

        "uniform bool uIsDistanceDependent;\n" +
        "uniform vec4 uLightPropertyColorRGBA;\n" +
        "uniform float uLightPropertyIntensity;\n" +
        "uniform vec4 uMaterialColorAlbedoRGBA;\n" +
        "uniform float uMaterialPropertyAmbient;\n" +
        "uniform float uMaterialPropertyDiffuse;\n" +
        "uniform float uMaterialPropertySpecular;\n" +

        "out vec4 outFragmentColorFinal;\n" +

        "void main() {\n" +
        "    vec3 fromVertexToLight = vTransformLightPositionGlobal - vVertexPositionGlobal;\n" +
        "    vec3 fromVertexToLightNormalized = normalize(fromVertexToLight);\n" +
        "    float fromVertexToLightLength = length(fromVertexToLight);\n" +
        "    vec3 lightReflectionDirectionNormalized = normalize(reflect(-fromVertexToLightNormalized, vVertexNormalGlobalNormalized));\n" +

        "    vec3 fromVertexToCamera = vTransformCameraPositionGlobal - vVertexPositionGlobal;\n" +
        "    vec3 fromVertexToCameraNormalized = normalize(fromVertexToCamera);\n" +

        "    vec4 mixedColorRGBA = mix(vVertexColorRGBA, uMaterialColorAlbedoRGBA, uMaterialColorAlbedoRGBA.a);\n" +

        "    vec4 ambientColorRGBA = uMaterialPropertyAmbient * mixedColorRGBA;\n" +

        "    float specular = max(dot(fromVertexToCameraNormalized, lightReflectionDirectionNormalized), 0.0);\n" +
        "    vec4 specularColorRGBA = uMaterialPropertySpecular * uLightPropertyColorRGBA * pow(specular, uLightPropertyIntensity);\n" +

        "    float diffuse = max(dot(vVertexNormalGlobalNormalized, fromVertexToLightNormalized), 0.0);\n" +
        "    vec4 diffuseColorRGBA = uMaterialPropertyDiffuse * diffuse * uLightPropertyColorRGBA * mixedColorRGBA;\n" +

        "    if (uIsDistanceDependent) {\n" +
        "        float attenuation = 1.0f / (1.0f + fromVertexToLightLength * fromVertexToLightLength);\n" +
        "        outFragmentColorFinal = vec4(attenuation * (ambientColorRGBA + diffuseColorRGBA + specularColorRGBA).rgb, mixedColorRGBA.a);\n" +
        "    } else {\n" +
        "        outFragmentColorFinal = vec4((ambientColorRGBA + diffuseColorRGBA + specularColorRGBA).rgb, mixedColorRGBA.a);\n" +
        "    }\n" +
        "}\n";
}
