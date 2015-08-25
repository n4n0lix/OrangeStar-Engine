package de.orangestar.engine.render.actor.ui;

import de.orangestar.engine.render.Batch;
import de.orangestar.engine.render.IRenderEngine;
import de.orangestar.engine.render.PrimitiveType;
import de.orangestar.engine.render.actor.Actor;
import de.orangestar.engine.render.shader.Shader;
import de.orangestar.engine.utils.RenderUtils;
import de.orangestar.engine.values.Color4f;
import de.orangestar.engine.values.Rectangle4i;
import de.orangestar.engine.values.Viewport4f;

public class UILabel extends Actor {

    public UILabel(Font font, String text) {
        setFont(font);
        setText(text);
    }
    
    public String getText() {
        return _text;
    }
    
    public void setText(String text) {
        _text = text;
        _textHasChanged = true;
    }
    
    public Font getFont() {
        return _font;
    }
    
    public void setFont(Font font) {
        _font = font;
        
        if (_batch != null) {
            _batch.onDeinitialize();
        }
        
        _batch = new Batch.Builder()
                .material(_font.getTexture(), Shader.GodShader, new Color4f(0.5f, 1f))
                .noInstances()
                .build();
    }
    
    @Override
    public void onRelease() {
        _batch.onDeinitialize();
    }

    @Override
    public void onRender(IRenderEngine engine) {
        if (_textHasChanged) {
            updateBatch();
            _textHasChanged = false;
        }
        
        _batch.render(engine, PrimitiveType.TRIANGLES);
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private String  _text;
    private boolean _textHasChanged;
    private int _charsInBatch;
    
    private Font   _font;

    private Batch _batch;
    
    private float _width;
    private float _height;

    private void updateBatch() {
        _width  = 0.0f;
        _height = 0.0f;
        _charsInBatch = 0;
        
        for(int i = 0; i < _text.length(); i++) {
            char chr = _text.charAt(i);
            if (_font.hasChar(chr)) {
                writeCharIntoBatch(_width, 0f, chr);
                _width  += _font.getRectangleForChar(chr).width + 1;
                _height += _font.getRectangleForChar(chr).height;
                _charsInBatch++;
            }
        }
        
        _height = _height / _charsInBatch;
    }
    
    private void writeCharIntoBatch(float x, float y, char chr) {
        if (!_font.hasChar(chr)) {
            return;
        }
        
        // Get texture rectangle
        Rectangle4i rectangle = _font.getRectangleForChar(chr);
        
        // Calc texcoords
        float texcoordXPerPixel = 1f / _batch.getMaterial().getTexture().getWidth();
        float texcoordYPerPixel = 1f / _batch.getMaterial().getTexture().getHeight();
      
        Viewport4f texcoord = new Viewport4f();
        texcoord.left   = texcoordXPerPixel * rectangle.x;
        texcoord.top    = texcoordYPerPixel * rectangle.y;
        texcoord.right  = texcoord.left + texcoordXPerPixel * rectangle.width;
        texcoord.bottom = texcoord.top + texcoordYPerPixel * rectangle.height;
              
        // Add gpu data
        _batch.addVertexData(RenderUtils.generate2DQuadVertices(x, y, rectangle.width, rectangle.height, texcoord.left, texcoord.top, texcoord.right, texcoord.bottom));
        _batch.addIndicesData(RenderUtils.generate2DQuadIndices(_charsInBatch * 4));
    }

    @Override
    public float getWidth() {
        return _width;
    }

    @Override
    public float getHeight() {
        return _height;
    }

    @Override
    public float getDepth() {
        // TODO Auto-generated method stub
        return 0;
    }
}
