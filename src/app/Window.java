/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * https://www.youtube.com/watch?v=KD7wHKN22DQ
 */
package app;

import javax.swing.JFrame;

/**
 *
 * @author Keith
 */
public class Window {
    
    public static final int WIDTH = 307; // slightly larger than 300 to make up for lines
    public static final int HEIGHT = 630;
    private Board board;
    
    private JFrame window;
    
    public Window() {
        
        window = new JFrame("Auctoris Tetris");
        
        window.setSize(WIDTH, HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        
        board = new Board();
        window.add(board);
        window.addKeyListener(board);
        
        window.setVisible(true);

    }
    
    public static void main(String[] args) {
        
        new Window();
        
    }
}
