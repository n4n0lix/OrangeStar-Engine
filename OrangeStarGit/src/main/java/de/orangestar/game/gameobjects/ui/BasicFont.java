package de.orangestar.game.gameobjects.ui;

import de.orangestar.engine.render.Texture;
import de.orangestar.engine.render.actor.ui.Font;
import de.orangestar.engine.values.Rectangle4i;

public class BasicFont extends Font {

    public BasicFont() {
        super(TEXTURE);

        initSmallChars();
        initBigChars();
        initNumbers();
        initExtra1();
        initExtra2();
        initGermanChars();
    }
    
    private void initBigChars() {
        addChar('A', new Rectangle4i(  0,0,4,6));
        addChar('B', new Rectangle4i(  8,0,4,6));
        addChar('C', new Rectangle4i( 16,0,4,6));
        addChar('D', new Rectangle4i( 24,0,4,6));
        addChar('E', new Rectangle4i( 32,0,4,6));
        addChar('F', new Rectangle4i( 40,0,4,6));
        addChar('G', new Rectangle4i( 48,0,4,6));
        addChar('H', new Rectangle4i( 56,0,4,6));
        addChar('I', new Rectangle4i( 64,0,3,6));
        addChar('J', new Rectangle4i( 72,0,3,6));
        addChar('K', new Rectangle4i( 80,0,4,6));
        addChar('L', new Rectangle4i( 88,0,3,6));
        addChar('M', new Rectangle4i( 96,0,5,6));
        addChar('N', new Rectangle4i(104,0,5,6));
        addChar('O', new Rectangle4i(112,0,4,6));
        addChar('P', new Rectangle4i(120,0,4,6));
        
        addChar('Q', new Rectangle4i( 0,8,5,6));
        addChar('R', new Rectangle4i( 8,8,4,6));
        addChar('S', new Rectangle4i(16,8,4,6));
        addChar('T', new Rectangle4i(24,8,4,6));
        addChar('U', new Rectangle4i(32,8,4,6));
        addChar('V', new Rectangle4i(40,8,5,6));
        addChar('W', new Rectangle4i(48,8,5,6));
        addChar('X', new Rectangle4i(56,8,4,6));
        addChar('Y', new Rectangle4i(64,8,4,6));
        addChar('Z', new Rectangle4i(72,8,4,6));
        addChar(' ', new Rectangle4i(80,8,3,6));
    }
    
    private void initSmallChars() {        
        addChar('a', new Rectangle4i(  0,16,4,6));
        addChar('b', new Rectangle4i(  8,16,4,6));
        addChar('c', new Rectangle4i( 16,16,3,6));
        addChar('d', new Rectangle4i( 24,16,4,6));
        addChar('e', new Rectangle4i( 32,16,4,6));
        addChar('f', new Rectangle4i( 40,16,2,6));
        addChar('g', new Rectangle4i( 48,16,4,6));
        addChar('h', new Rectangle4i( 56,16,3,6));
        addChar('i', new Rectangle4i( 64,16,1,6));
        addChar('j', new Rectangle4i( 72,16,2,6));
        addChar('k', new Rectangle4i( 80,16,3,6));
        addChar('l', new Rectangle4i( 88,16,2,6));
        addChar('m', new Rectangle4i( 96,16,5,6));
        addChar('n', new Rectangle4i(104,16,3,6));
        addChar('o', new Rectangle4i(112,16,4,6));
        addChar('p', new Rectangle4i(120,16,3,6));
        
        addChar('q', new Rectangle4i( 0,24,4,6));
        addChar('r', new Rectangle4i( 8,24,2,6));
        addChar('s', new Rectangle4i(16,24,3,6));
        addChar('t', new Rectangle4i(24,24,2,6));
        addChar('u', new Rectangle4i(32,24,3,6));
        addChar('v', new Rectangle4i(40,24,3,6));
        addChar('w', new Rectangle4i(48,24,5,6));
        addChar('x', new Rectangle4i(56,24,3,6));
        addChar('y', new Rectangle4i(64,24,3,6));
        addChar('z', new Rectangle4i(72,24,4,6));
    }
    
    private void initNumbers() {
        addChar('1', new Rectangle4i(  0,32,3,6));
        addChar('2', new Rectangle4i(  8,32,4,6));
        addChar('3', new Rectangle4i( 16,32,4,6));
        addChar('4', new Rectangle4i( 24,32,4,6));
        addChar('5', new Rectangle4i( 32,32,4,6));
        addChar('6', new Rectangle4i( 40,32,4,6));
        addChar('7', new Rectangle4i( 48,32,4,6));
        addChar('8', new Rectangle4i( 56,32,4,6));
        addChar('9', new Rectangle4i( 64,32,4,6));
        addChar('0', new Rectangle4i( 72,32,4,6));
    }
    
    private void initExtra1() {
        addChar('.', new Rectangle4i(  0,40,1,6));
        addChar(',', new Rectangle4i(  8,40,2,6));
        addChar(';', new Rectangle4i( 16,40,2,6));
        addChar(':', new Rectangle4i( 24,40,1,6));
        addChar('_', new Rectangle4i( 32,40,5,6));
        addChar('-', new Rectangle4i( 40,40,3,6));
        addChar('+', new Rectangle4i( 48,40,3,6));
        addChar('*', new Rectangle4i( 56,40,3,6));
        addChar('/', new Rectangle4i( 64,40,3,6));
        addChar('\\',new Rectangle4i( 72,40,3,6));
        addChar('!', new Rectangle4i( 80,40,1,6));
        addChar('?', new Rectangle4i( 88,40,3,6));
        addChar('(', new Rectangle4i( 96,40,2,6));
        addChar(')', new Rectangle4i(104,40,2,6));
        addChar('[', new Rectangle4i(112,40,2,6));
        addChar(']', new Rectangle4i(120,40,2,6));
    }
    
    private void initExtra2() {
        addChar('^', new Rectangle4i(  0,48,3,6));
        addChar('"', new Rectangle4i(  8,48,3,6));
        addChar('\'',new Rectangle4i( 16,48,1,6));
        addChar('§', new Rectangle4i( 24,48,3,6));
        addChar('%', new Rectangle4i( 32,48,5,6));
        addChar('&', new Rectangle4i( 40,48,4,6));
        addChar('=', new Rectangle4i( 48,48,3,6));
        addChar('~', new Rectangle4i( 56,48,4,6));
        addChar('#', new Rectangle4i( 64,48,5,6));
        addChar('<', new Rectangle4i( 72,48,3,6));
        addChar('>', new Rectangle4i( 80,48,3,6));
        addChar('|', new Rectangle4i( 88,48,1,6));
//        addChar('', new Rectangle4i( 96,48,5,6));
//        addChar('', new Rectangle4i(104,48,5,6));
//        addChar('', new Rectangle4i(112,48,4,6));
//        addChar('', new Rectangle4i(120,48,4,6));
    }
    
    private void initGermanChars() {
        addChar('ä', new Rectangle4i(  0,56,4,6));
        addChar('ö', new Rectangle4i(  8,56,3,6));
        addChar('ü',new Rectangle4i( 16,56,3,6));
        addChar('Ä', new Rectangle4i( 24,56,4,6));
        addChar('Ö', new Rectangle4i( 32,56,4,6));
        addChar('Ü', new Rectangle4i( 40,56,3,6));
        addChar('ß', new Rectangle4i( 48,56,4,6));
//        addChar('~', new Rectangle4i( 56,56,4,6));
//        addChar('#', new Rectangle4i( 64,56,3,6));
//        addChar('<', new Rectangle4i( 72,56,3,6));
//        addChar('>', new Rectangle4i( 80,56,4,6));
//        addChar('|', new Rectangle4i( 88,56,3,6));
//        addChar('(', new Rectangle4i( 96,56,5,6));
//        addChar(')', new Rectangle4i(104,56,5,6));
//        addChar('[', new Rectangle4i(112,56,4,6));
//        addChar(']', new Rectangle4i(120,56,4,6));
    }
    
    private static Texture TEXTURE = new Texture("textures/basicfont.png");

}
