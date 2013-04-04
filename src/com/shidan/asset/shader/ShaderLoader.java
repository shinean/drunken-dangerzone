package com.shidan.asset.shader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;


public class ShaderLoader {

    private static ShaderLoader shaderLoader;
    private ShaderLoader() {

    }

    public static ShaderLoader getInstance() {
        if (shaderLoader == null) {
            shaderLoader = new ShaderLoader();
        }
        return shaderLoader;
    }

    public String loadContents(String shaderName) throws Exception{
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(new File("assets/shaders/"+shaderName+".vert")));
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();
        return builder.toString();
    }

    public int getShaderProgram(String vertexShaderName, String fragmentShaderName) throws Exception{
        int enableShader = glCreateProgram();
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        String vertexShaderContent = ShaderLoader.getInstance().loadContents(vertexShaderName);
        String fragmentShaderContent = ShaderLoader.getInstance().loadContents(fragmentShaderName);

        glShaderSource(vertexShader,vertexShaderContent);
        glCompileShader(vertexShader);
        if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Error in vertexshader compiling");
        }

        glShaderSource(fragmentShader,fragmentShaderContent);
        glCompileShader(fragmentShader);
        if (glGetShaderi(fragmentShader,GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Error in fragmentshader compiling");
        }

        glAttachShader(enableShader,vertexShader);
        glAttachShader(enableShader,fragmentShader);
        glLinkProgram(enableShader);
        glValidateProgram(enableShader);
        return enableShader;
    }




}
