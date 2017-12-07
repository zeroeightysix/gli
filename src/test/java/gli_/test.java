package gli_;

import glm_.vec3.Vec3i;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL33;

import java.nio.IntBuffer;

import static gli_.Java.gli;
import static gli_.buffer.BufferKt.intBufferBig;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.glCompressedTexSubImage2D;
import static org.lwjgl.opengl.GL42.glTexStorage2D;

public class test {

    public static void main(String[] args) {


    }

    public static int createTexture(String filename) {

        Texture texture = gli.load(filename);
        if (texture.empty())
            return 0;

        gli_.gli.gl.setProfile(gl.Profile.GL33);
        gl.Format format = gli_.gli.gl.translate(texture.getFormat(), texture.getSwizzles());
        gl.Target target = gli_.gli.gl.translate(texture.getTarget());
        assert (texture.getFormat().isCompressed() && target == gl.Target._2D);

        IntBuffer textureName = intBufferBig(1);
        glGenTextures(textureName);
        glBindTexture(target.getI(), textureName.get(0));
        glTexParameteri(target.getI(), GL12.GL_TEXTURE_BASE_LEVEL, 0);
        glTexParameteri(target.getI(), GL12.GL_TEXTURE_MAX_LEVEL, texture.levels() - 1);
        IntBuffer swizzles = intBufferBig(4);
        texture.getSwizzles().to(swizzles);
        glTexParameteriv(target.getI(), GL33.GL_TEXTURE_SWIZZLE_RGBA, swizzles);
        Vec3i extent = texture.extent(0);
        glTexStorage2D(target.getI(), texture.levels(), format.getInternal().getI(), extent.x, extent.y);
        for (int level = 0; level < texture.levels(); level++) {
            extent = texture.extent(level);
            glCompressedTexSubImage2D(
                    target.getI(), level, 0, 0, extent.x, extent.y,
                    format.getInternal().getI(), texture.data(0, 0, level));
        }
        return textureName.get(0);
    }
}