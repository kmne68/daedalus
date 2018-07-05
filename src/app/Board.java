/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Keith
 */
public class Board extends JPanel implements KeyListener {
    
    private BufferedImage blocks;
    private final int blockSize = 30;
    private final int boardWidth = 10;
    private final int boardHeight = 20;
    private int[][] boardGrid = new int[boardHeight][boardWidth];
    
    private Shape[] shapes = new Shape[7];
    private Shape currentShape;
   
    private Timer timer;
    private final int FPS = 60;
    private final int delay = 1000/FPS;
    
    public Board() {
        
        try {
            blocks = ImageIO.read(Board.class.getResource("/tetris_sprite.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }            
        });
        
        timer.start();
        
        // shapes
        shapes[0] = new Shape(blocks.getSubimage(0, 0, blockSize, blockSize), new int[][] {
            {1, 1, 1, 1} // I-shape
            }, this);
        
        shapes[1] = new Shape(blocks.getSubimage(blockSize, 0, blockSize, blockSize), new int[][] {
            {1, 1, 0},
            {0, 1, 1}   // Z-shape
        }, this);
        
        shapes[2] = new Shape(blocks.getSubimage(blockSize * 2, 0, blockSize, blockSize), new int[][] {
            {0, 1, 1},
            {1, 1, 0}   // S-shape
        }, this);
        
        shapes[3] = new Shape(blocks.getSubimage(blockSize * 3, 0, blockSize, blockSize), new int[][] {
            {1, 1, 1},
            {0, 0, 1}   // J-shape
        }, this);
        
        shapes[4] = new Shape(blocks.getSubimage(blockSize * 4, 0, blockSize, blockSize), new int[][] {
            {1, 1, 1},
            {0, 1, 0}   // T-shape
        }, this);
        
        shapes[5] = new Shape(blocks.getSubimage(blockSize * 5, 0, blockSize, blockSize), new int[][] {
            {1, 0, 0},
            {1, 1, 1}   // L-shape
        }, this);
        
        shapes[6] = new Shape(blocks.getSubimage(blockSize * 6, 0, blockSize, blockSize), new int[][] {
            {1, 1},
            {1, 1}   // O-shape
        }, this);
        
        // currentShape = shapes[4];
        setNextShape();
    }

    
    public void update() {
        currentShape.update();
    }

    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        
        currentShape.render(g);
        
        for(int row = 0; row < boardGrid.length; row++) {
            for(int col = 0; col < boardGrid[row].length; col++)
                if(boardGrid[row][col] != 0)
                    g.drawImage(blocks.getSubimage(0, 0, blockSize, blockSize), col * blockSize, row * blockSize, null);
        }
        
        
        // g.drawRect(100, 100, 50, 50);
        // g.drawImage(blocks, 0, 0, null);
        
        for(int i = 0; i < boardHeight; i++) {
            g.drawLine(0, i*blockSize, boardWidth * blockSize, i * blockSize);
        }
        
        for(int j = 0; j < boardWidth; j++) {
            g.drawLine(j * blockSize, 0, j * blockSize, boardHeight * blockSize);
        }
        
    }
    
    public int getBlockSize() {
        return blockSize;
    }

    
    public int[][] getBoard() {
        return boardGrid;
    }
    

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
            currentShape.setDeltaX(-1);
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            currentShape.setDeltaX(1);
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            currentShape.speedDown();
        if(e.getKeyCode() == KeyEvent.VK_UP)
            currentShape.rotate();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            currentShape.normalSpeed();
    }
    
    
    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public void setNextShape() {
        
        int index = (int)(Math.random() * shapes.length);
        
        Shape newShape = new Shape(shapes[index].getBlock(), shapes[index].getCoords(), this);
        
        currentShape = newShape;
    }
}
