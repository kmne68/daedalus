/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * https://www.youtube.com/watch?v=KD7wHKN22DQ
 */
package gui;

import app.Board;
import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author Keith
 */
public class Window {
    
    public static final int WIDTH = 307; // slightly larger than 300 to make up for lines
    public static final int HEIGHT = 735;
    private Board board;
    private StatsPanel stats;
    private GraphicsPanel graphics;
    private TopPanel top;
    
    private JFrame window;
    
    public Window() {
        
        window = new JFrame("Auctoris Tetris");
        
        window.setSize(WIDTH, HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        
        stats = new StatsPanel();
        graphics = new GraphicsPanel();
        top = new TopPanel();
        board = new Board();
        
        
        window.setLayout(new BorderLayout());
        
        window.add(top, BorderLayout.NORTH);
        window.add(stats, BorderLayout.WEST);
        window.add(stats, BorderLayout.EAST);
        window.add(board, BorderLayout.SOUTH);
               
        
    //    window.add(board);
        window.addKeyListener(board);
        
        window.setVisible(true);

    }
    
    public static void main(String[] args) {
        
        new Window();
        
    }
}
