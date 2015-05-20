package de.orangestar.sandbox;

import java.io.File;
import java.util.Random;

import de.orangestar.engine.logic.GameObject;
import de.orangestar.engine.logic.modules.RenderModule;
import de.orangestar.engine.render.Material;
import de.orangestar.engine.render.actor.ATileMap;
import de.orangestar.engine.render.shader.Shader;
import de.orangestar.engine.resources.ResourceManager;

public class TestModuleRender extends RenderModule {

    public TestModuleRender(GameObject parent) {
        super(parent);
        
        int[][] data = new int[64][64];
        
        for(int i = 0; i < data.length; i++) {
            for(int p = 0; p < data[0].length; p++) {
                data[i][p] = new Random().nextInt(2);
            }
        }
        
        Material material = new Material();
        material.setShader(Shader.StreamingBatchShader);
        material.setTexture(ResourceManager.Get().getTexture(new File("texture2.png")));
        
        ATileMap tilemap = new ATileMap(material, 16, 16);
        tilemap.setData(data);
        
        setActor(tilemap);
    }

    @Override
    public void onRender() {
        
    }
    
}