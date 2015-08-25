package de.orangestar.engine.render.actor.ui;

import de.orangestar.engine.render.Texture;
import de.orangestar.engine.utils.Deinitializable;
import de.orangestar.engine.values.Rectangle4i;

public class Font implements Deinitializable {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public Font(Texture texture) {
        _texture = texture;
        _charRectangles = new Rectangle4i[Character.MAX_VALUE];
    }
    
    public void addChar(char chr, Rectangle4i rect) {
        assert rect != null;
        assert rect.x >= 0;
        assert rect.y >= 0;
        assert rect.width > 0;
        assert rect.height > 0;
        
        _charRectangles[chr] = rect;
    }
    
    public boolean hasChar(char chr) {
        return _charRectangles[chr] != null;
    }

    public Rectangle4i getRectangleForChar(char chr) {
        return _charRectangles[chr];
    }
    
    public Texture getTexture() {
        return _texture;
    }
    
    @Override
    public void onDeinitialize() {
        _texture.onDeinitialize();
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private Texture         _texture;
    private Rectangle4i[]   _charRectangles;
    
}
