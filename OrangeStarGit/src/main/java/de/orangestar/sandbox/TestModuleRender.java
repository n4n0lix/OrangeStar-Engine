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
        
        int[][] data = new int[][] {
            new int[] {  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,},
            new int[] {  0,  2, 10, 10, 18,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,},
            new int[] {  0,  3,  1, 16,  6,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,},
            new int[] {  0,  3,  9,  8, 19,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,},
            new int[] {  2, 23,  5, 13, 20,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,},
            new int[] {  3,  1,  6,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,},
            new int[] {  4, 12, 20,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,},
            new int[] {  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,},
            new int[] {  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,},
            new int[] {  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,},
            new int[] {  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,},
            new int[] {  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,},
            new int[] {  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,},
            new int[] {  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,},
            new int[] {  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,},
            new int[] {  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,},
            new int[] {  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,},
            new int[] {  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,},
            new int[] {  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,},
            new int[] {  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,},
            new int[] {  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,},
            new int[] {  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,},
            new int[] {  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,},
            new int[] {  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,},
        };
        
//        for(int i = 0; i < data.length; i++) {
//            for(int p = 0; p < data[0].length; p++) {
//                data[i][p] = new Random().nextInt(20);
//            }
//        }
                
        
        Material material = new Material();
        material.setShader(Shader.StreamingBatchShader);
        material.setTexture(ResourceManager.Get().getTexture(new File("texture_large2.png")));
        
        ATileMap tilemap = new ATileMap(material, 16, 16);
        tilemap.setData(data);
        
        setActor(tilemap);
    }

    @Override
    public void onRender() {
        
    }
    
}