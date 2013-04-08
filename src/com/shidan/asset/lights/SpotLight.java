package com.shidan.asset.lights;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
public class SpotLight {
    private float[] ambient;
    private float[] diffuse;
    private float[] specular;
    private float[] position;
    private float[] direction;
    public SpotLight(float[] ambient, float[] diffuse, float[] specular, float[] position, float[] direction) {
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.position = position;
        this.direction = direction;
    }

    private FloatBuffer asFloatBuffer(float[] array) {
        FloatBuffer fb = BufferUtils.createFloatBuffer(array.length);
        fb.put(array);
        fb.flip();
        return fb;
    }

    public void addLightSource() {
        glEnable(GL_LIGHTING);
        glEnable(GL_LIGHT1);
        glEnable(GL_DEPTH_TEST);
        glLightModeli(GL_LIGHT_MODEL_LOCAL_VIEWER,GL_TRUE);
            glLight(GL_LIGHT1,GL_AMBIENT,asFloatBuffer(ambient));
            glLight(GL_LIGHT1,GL_DIFFUSE,asFloatBuffer(diffuse));
            glLight(GL_LIGHT1,GL_SPECULAR,asFloatBuffer(specular));
            glLight(GL_LIGHT1,GL_POSITION,asFloatBuffer(position));
            glLightf(GL_LIGHT1,GL_CONSTANT_ATTENUATION,1.5f);
            glLightf(GL_LIGHT1,GL_LINEAR_ATTENUATION,0.5f);
            glLightf(GL_LIGHT1,GL_QUADRATIC_ATTENUATION,0.2f);
            glLightf(GL_LIGHT1,GL_SPOT_CUTOFF,45F);
            glLight(GL_LIGHT1, GL_SPOT_DIRECTION, asFloatBuffer(direction));
            glLightf(GL_LIGHT1,GL_SPOT_EXPONENT,2f);
        glDisable(GL_LIGHTING);
    }

}
