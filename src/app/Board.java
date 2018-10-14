/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 * Based on: https://www.youtube.com/watch?v=KD7wHKN22DQ
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
import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/**
 *
 * @author Keith
 */
// public class Board extends JPanel implements KeyListener {
public class Board extends JPanel {

    private BufferedImage blocks;
    private final int blockSize = 30;
    private final int boardWidth = 10;
    private final int boardHeight = 20;
    private int[][] boardGrid = new int[boardHeight][boardWidth];   // the grid that covers the board

    private Shape[] shapes = new Shape[7];
    private Shape currentShape;

    private Timer timer;
    private final int FPS = 60;
    private final int delay = 1000 / FPS;
    private boolean gameOver = false;

    public Board() {

        try {
            blocks = ImageIO.read(Board.class.getResource("/tetris_sprite.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardUpdate();
                repaint();
            }
        });

        move();
//        this.addAction("LEFT", -1);
//        this.addAction("RIGHT", 1);
//        this.addAction("DOWN", 0);
//        this.addAction("UP", 2);

        timer.start();

        // shapes
        shapes[0] = new Shape(blocks.getSubimage(0, 0, blockSize, blockSize), new int[][]{
            {1, 1, 1, 1} // I-shape
        }, this, 1);

        shapes[1] = new Shape(blocks.getSubimage(blockSize, 0, blockSize, blockSize), new int[][]{
            {1, 1, 0},
            {0, 1, 1} // Z-shape
        }, this, 2);

        shapes[2] = new Shape(blocks.getSubimage(blockSize * 2, 0, blockSize, blockSize), new int[][]{
            {0, 1, 1},
            {1, 1, 0} // S-shape
        }, this, 3);

        shapes[3] = new Shape(blocks.getSubimage(blockSize * 3, 0, blockSize, blockSize), new int[][]{
            {1, 1, 1},
            {0, 0, 1} // J-shape
        }, this, 4);

        shapes[4] = new Shape(blocks.getSubimage(blockSize * 4, 0, blockSize, blockSize), new int[][]{
            {1, 1, 1},
            {0, 1, 0} // T-shape
        }, this, 5);

        shapes[5] = new Shape(blocks.getSubimage(blockSize * 5, 0, blockSize, blockSize), new int[][]{
            {1, 0, 0},
            {1, 1, 1} // L-shape
        }, this, 6);

        shapes[6] = new Shape(blocks.getSubimage(blockSize * 6, 0, blockSize, blockSize), new int[][]{
            {1, 1},
            {1, 1} // O-shape
        }, this, 7);

        // currentShape = shapes[4];
        setNextShape();
    }

    public void boardUpdate() {
        currentShape.update();

        if (gameOver) {
            timer.stop();
        }
    }
    
    
    public void move() {
        this.addAction("LEFT", -1);
        this.addAction("RIGHT", 1);
        this.addAction("DOWN", 0);
        this.addAction("UP", 2);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        // draw horizontal grid lines
        for (int i = 0; i < boardHeight; i++) {
            g.drawLine(0, i * blockSize, boardWidth * blockSize, i * blockSize);
        }

        // draw vertical gridlines
        for (int j = 0; j < boardWidth; j++) {
            g.drawLine(j * blockSize, 0, j * blockSize, boardHeight * blockSize);
        }

        currentShape.render(g);

        for (int row = 0; row < boardGrid.length; row++) {
            for (int col = 0; col < boardGrid[row].length; col++) {
                if (boardGrid[row][col] != 0) { // for all nonzero squares...
                    // drawImage takes an image, in this case a subimage defined by x, y, width and height
                    // for each column of the blocks image we count across the row and multiply by the blocksize to get the value 
                    // the y value is zero because the blocks image has only one row
                    // blocks.getSubimage((boardGrid[row][col] - 1 is how we determine which color to paint a boardGrid square
                    g.drawImage(blocks.getSubimage((boardGrid[row][col] - 1) * blockSize, 0, blockSize, blockSize), col * blockSize, row * blockSize, null);
                }
            }
        }
    }

    public int getBlockSize() {
        return blockSize;
    }

    public int[][] getBoard() {
        return boardGrid;
    }

    /* Disabled 2018-10-14
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
     */
    public void setNextShape() {

        int index = (int) (Math.random() * shapes.length);

        Shape newShape = new Shape(shapes[index].getBlock(), shapes[index].getShapeBlock(), this, shapes[index].getColor());

        currentShape = newShape;

        // test for game over (start position blocked)
        for (int row = 0; row < currentShape.getShapeBlock().length; row++) {
            for (int col = 0; col < currentShape.getShapeBlock()[row].length; col++) {
                if (currentShape.getShapeBlock()[row][col] != 0) {

                    if (boardGrid[row][col + 4] != 0) {         // 4 is the start x position and if it is not empty (i.e. equal to zero, then
                        gameOver = true;                     // the pieces have reached the top and the game is over
                        System.out.println("Game Over!");
                    }
                }
            }
        }
    }

    public MotionAction addAction(String name, int deltaX) {

        MotionAction action = new MotionAction(name, deltaX);

        KeyStroke pressedKeyStroke = KeyStroke.getKeyStroke(name);
        InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(pressedKeyStroke, name);
        this.getActionMap().put(name, action);

        return action;
    }

    private class MotionAction extends AbstractAction implements ActionListener {

        private int deltaX;

        public MotionAction(String name, int deltaX) {
            super(name);

            this.deltaX = deltaX;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (deltaX == 0) {
                currentShape.speedDown();
                
            }
            if (deltaX == 2) {
                currentShape.rotate();
            } else {

            currentShape.setDeltaX(deltaX);
            //move(deltaX, deltaY);
            }
        }
    }
}
