package de.orangestar.engine.resources;

import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PNGDecoder {

    public static byte[] read(File file) throws IOException {
       return  ((DataBufferByte) ImageIO.read(file).getData().getDataBuffer()).getData();
    }
    
}
