package de.orangestar.engine.render;

import de.orangestar.engine.render.shader.Shader;
import de.orangestar.engine.values.Color4f;

public class Material {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public Material() {

    }

    public Texture getTexture() {
        return _texture;
    }
    
    public void setTexture(Texture _texture) {
        this._texture = _texture;
    }
    
    public Shader getShader() {
        return _shader;
    }
    
    public void setShader(Shader _shader) {
        this._shader = _shader;
    }
    
    public Color4f getColor() {
        return _color;
    }
    
    public void setColor(Color4f _color) {
        this._color = _color;
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
