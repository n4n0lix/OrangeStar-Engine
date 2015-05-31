package de.orangestar.engine.resources;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import de.orangestar.engine.AbstractManager;
import de.orangestar.engine.render.Texture;

public class ResourceManager extends AbstractManager {
    
    @Override
    public void start() {

    }
    @Override
    public void update() {
        
    }
    @Override
    public void shutdown() {
        
    }

    public Texture getTexture(String file) {
        return getTexture(new File(file));
    }
    
    public Texture getTexture(File file) {
        Texture result = null;
        
        try {
        
        if (file.getName().toLowerCase().endsWith(".png")) {
                result = new Texture( ImageIO.read(file), false);
        }
        
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            
        return result;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              SINGLETON                             */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * Returns the active instance of {@link ResourceManager}
     * @return A resource manager
     */
    public static ResourceManager Get() {
        if (INSTANCE == null) {
            INSTANCE = new ResourceManager();
        }

        return INSTANCE;
    }

    private static ResourceManager INSTANCE = null;

}
