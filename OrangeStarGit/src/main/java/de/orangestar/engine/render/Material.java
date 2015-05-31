package de.orangestar.engine.render;

import de.orangestar.engine.render.shader.Shader;
import de.orangestar.engine.values.Color4f;

/**
 * Material that consists of atleast a shader, an optional color and optional texture.
 * Describes the appearence of objects and how they are being rendered.
 * 
 * @author Basti
 */
public class Material {

    public static class Builder {

        public Builder texture(Texture texture) {
            _texture = texture;
            return this;
        }
        
        public Builder shader(Shader shader) {
            _shader = shader;
            return this;
        }
        
        public Builder color(Color4f color) {
            _color = color;
            return this;
        }
        
        public Material build() {
            return new Material(_texture, _shader, _color);
        }
        
        private Texture _texture;
        private Shader  _shader;
        private Color4f _color;
        
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public Material(Texture tex, Shader shader, Color4f color) {
        _texture = tex;
        _shader  = shader;
        _color   = color;
    }

    /**
     * Returns the texture of this material.
     * @return A texture
     */
    public Texture getTexture() {
        return _texture;
    }

    /**
     * Returns the shader of this material.
     * @return A shader
     */
    public Shader getShader() {
        return _shader;
    }

    /**
     * Returns the color of this material.
     * @return A color
     */
    public Color4f getColor() {
        return _color;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_color == null) ? 0 : _color.hashCode());
        result = prime * result + ((_shader == null) ? 0 : _shader.hashCode());
        result = prime * result
                + ((_texture == null) ? 0 : _texture.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Material other = (Material) obj;
        if (_color == null) {
            if (other._color != null)
                return false;
        } else if (!_color.equals(other._color))
            return false;
        if (_shader == null) {
            if (other._shader != null)
                return false;
        } else if (!_shader.equals(other._shader))
            return false;
        if (_texture == null) {
            if (other._texture != null)
                return false;
        } else if (!_texture.equals(other._texture))
            return false;
        return true;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private Texture         _texture;
    private Shader          _shader;
    private Color4f         _color;

}
