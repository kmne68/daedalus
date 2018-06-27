/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Keith
 */
public class Board extends JPanel {
    
    private BufferedImage blocks;
    
    public Board() {
        
        try {
            blocks = ImageIO.read(Board.class.getResource("/tetris_sprite.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        
        // g.drawRect(100, 100, 50, 50);
        g.drawImage(blocks, 0, 0, null);
    }
}
