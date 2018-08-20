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
    private int startX, startY;
    private int deltaX = 0;
    private long time, lastTime;

    private int normalSpeed = 600;
    private int speedDown = 60;
    private int currentSpeed;
    private int color;
    
    private boolean collision = false;
    private boolean moveX = false;

    public Shape(BufferedImage block, int[][] coords, Board board, int color) {
        this.block = block;
        this.coords = coords;
        this.board = board;
        this.color = color;

        currentSpeed = normalSpeed;
        time = 0;
        lastTime = System.currentTimeMillis();

        // location coordinates so shape starts in the middle of the board
        startX = 4;
        startY = 0;
    }

    public void update() {

        time += System.currentTimeMillis() - lastTime;  // this would be better called timeSinceLast
        lastTime = System.currentTimeMillis();

        // if there has been a collision, draw the piece on the board and set up the next piece
        if (collision) {
            for (int row = 0; row < coords.length; row++)                       // we have coords.length rows
                for (int col = 0; col < coords[row].length; col++)              // we have  row columns               
                    if (coords[row][col] != 0) {                                // if the square of the piece is not a 0
                        board.getBoard()[startY + row][startX + col] = color;         // draw it on the board 
                    }       
            checkLine();
            board.setNextShape();
        }

        // startX = 4, delta is based on left and right clicks, coord is 2, 3, or 4 depending on currentShape's coords array
        if (!(startX + deltaX + coords[0].length > 10) && !(startX + deltaX < 0)) {  // make sure we are still within the board's boundaries
            
            for(int row = 0; row < coords.length; row++)
                for(int col = 0; col < coords[row].length; col++)
                    if(coords[row][col] != 0) {  // if the block isn't zero
                        if(board.getBoard()[startY + row][startX + deltaX + col] != 0)  // if grid square is not empty
                            moveX = false;                                              // we cannot move in the X direction
                    }
            
            if(moveX)               // if the grid square is empty then we can move...
                startX += deltaX;   // ...so startX changes by deltaX
        }

        if (!(startY + 1 + coords.length > 20)) {   // have we reached the bottom of the board?
            for (int row = 0; row < coords.length; row++) 
                for (int col = 0; col < coords[row].length; col++) 
                    if (coords[row][col] != 0) {
                        
                        if(board.getBoard()[startY + row + 1][col + startX] != 0) {
                            System.out.println("startY = " + startY + "; startX = " + startX);  // startX and startY are based on now not the beginning of the
                            collision = true;                                                   // pieces life cycle
                        }
                        
                    }  
                        if (time > currentSpeed) {
                            startY++;
                            time = 0;
                        }       
        } else {
            collision = true;
        }

        deltaX = 0;
        moveX = true;
    }   

    public void render(Graphics g) {

        for (int row = 0; row < coords.length; row++) {
            for (int col = 0; col < coords[row].length; col++) {
                if (coords[row][col] != 0) {
                    g.drawImage(block, col * board.getBlockSize() + startX * board.getBlockSize(),
                            row * board.getBlockSize() + startY * board.getBlockSize(), null);
                }
            }
        }
    }
    
    
    private void checkLine() {
        
        int height = board.getBoard().length - 1;
        
        for(int i = height; i > 0; i--) {
            
            int count = 0;
            for(int j = 0; j < board.getBoard()[0].length; j++) {
                
                if(board.getBoard()[i][j] != 0)
                    count++;
                
                board.getBoard()[height][j] = board.getBoard()[i][j];
            }
            if(count < board.getBoard()[0].length)
                height--;
        }
    }

    /*
    1, 0, 0 rotate 1, 1 rotate 1, 1, 1 rotate 0, 1
    1, 1, 1   ==>  1, 0   ==>  0, 0, 1   ==>  0, 1
            right  1, 0  right          right 1, 1
     */
    public void rotate() {

        if(collision)
            return;
        
        int[][] rotatedMatrix = null;

        rotatedMatrix = getTranspose(coords);

        rotatedMatrix = getReverseMatrix(rotatedMatrix);

        if (startX + rotatedMatrix[0].length > 10 || startY + rotatedMatrix.length > 20) {
            return;
        }
        
        for(int row = 0; row < rotatedMatrix.length; row++) {
           for(int col = 0; col < rotatedMatrix[0].length; col++) {
               
               if(board.getBoard()[startY + row][startX + col] != 0) {
                   return;
               }
           }
        }

        coords = rotatedMatrix;
    }

    private int[][] getTranspose(int[][] matrix) {

        int[][] newMatrix = new int[matrix[0].length][matrix.length];
        
        System.out.println("matrix[0].length = " + matrix[0].length + " ; matrix.length = " + matrix.length);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                newMatrix[j][i] = matrix[i][j];
            }
        }

        return newMatrix;

    }

    private int[][] getReverseMatrix(int[][] matrix) {

        int middle = matrix.length / 2;
        System.out.println("matrix length = " + matrix.length);

        // e.g. matrix = {1, 1, 1}, {1, 0, 0} -- matrix.length = 3;
        for (int i = 0; i < middle; i++) {
            int[] m = matrix[i];                   // if i = 0, m = {1, 1, 1}
            matrix[i] = matrix[matrix.length - i - 1]; // matrix[0] = matrix[3 - 0 - 1 = 2]
            matrix[matrix.length - i - 1] = m;     // matrix[3 - 1 - 1 = 1] = m
        }
        return matrix;
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

    public BufferedImage getBlock() {

        return block;
    }

    public int[][] getCoords() {

        return coords;
    }
    
    
    public int getColor() {
        
        return color;
    }
}
