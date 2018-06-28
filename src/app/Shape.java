/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Keith
 */
public class Shape {
    
    private BufferedImage block;
    private int[][] coords;
    private Board board;
    
    
    public Shape(BufferedImage block, int[][] coords, Board board) {
        this.block = block;
        this.coords = coords;
        this.board = board;        
    }
    
    public void update() {
        
    }
    
    public void render(Graphics g) {
        
        for(int row = 0; row < coords.length; row++)
            for(int col = 0; col < coords[row].length; col++)
                if(coords[row][col] != 0)
                    g.drawImage(block, col * board.getBlockSize(), row * board.getBlockSize(), null);
    }
}
