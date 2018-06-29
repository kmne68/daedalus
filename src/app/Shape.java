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
    private int x, y;
    private int deltaX = 0;
    private long time, lastTime;
    
    private int normalSpeed = 600;
    private int speedDown = 60;
    private int currentSpeed;
    
    
    public Shape(BufferedImage block, int[][] coords, Board board) {
        this.block = block;
        this.coords = coords;
        this.board = board;  
        
        currentSpeed = normalSpeed;
        time = 0;
        lastTime = System.currentTimeMillis();
        
        // location coordinates so shape starts in the middle of the board
        x = 4;
        y = 0;
    }
    
    public void update() {
        
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        
        // x = 4, delta is based on left and right clicks, coord is 2, 3, or 4 depending on currentShape's coords array
        if(!(x + deltaX + coords[0].length > 10) && !(x + deltaX < 0))
            x += deltaX;
        
        if(!(y + 1 + coords.length > 20)) {
            if(time > currentSpeed) {
                y++;
                time = 0;
            }
        }
        
        deltaX = 0;
    }
    
    public void render(Graphics g) {
        
        for(int row = 0; row < coords.length; row++)
            for(int col = 0; col < coords[row].length; col++)
                if(coords[row][col] != 0)
                    g.drawImage(block, col * board.getBlockSize() + x * board.getBlockSize(),
                            row * board.getBlockSize() + y * board.getBlockSize(), null);
        
    }
    
    public void setDeltaX(int deltaX) {
        this.deltaX = deltaX;
    }
    
    
    public void normalSpeed() {
        currentSpeed = normalSpeed;
    }
            
    
    
    public void speedDown() {
        
        currentSpeed = speedDown;
    }
}
