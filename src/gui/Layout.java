/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import app.Board;
import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author kmne6
 */
public class Layout {

    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
    private static Board board = new Board();
    private static JFrame frame = new JFrame();

    public static void addComponentsToPane(Container pane) {
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }

        JButton btnSave;
        JButton btnQuit;
        pane.setLayout(new GridBagLayout());
        //    GridBagConstraints c = new GridBagConstraints();
        GridBagConstraints top = new GridBagConstraints();
        GridBagConstraints gamePanel = new GridBagConstraints();
        GridBagConstraints footer = new GridBagConstraints();

        //   JPanel nextPiece = new JPanel();
        if (shouldFill) {
            top.fill = GridBagConstraints.HORIZONTAL;
        }

        // Score panel (displays score)
        JPanel score = new JPanel();
        score.setPreferredSize(new Dimension(150, 125));
        score.setBorder(BorderFactory.createBevelBorder(0, Color.lightGray, Color.yellow));
        if (shouldWeightX) {
            top.weightx = 0.5;
        }
        top.fill = GridBagConstraints.HORIZONTAL;
        top.gridx = 0;
        top.gridy = 0;
        pane.add(score, top);

        // Graphics panel (displays next piece)
        JPanel graphics = new JPanel();
        graphics.setPreferredSize(new Dimension(150, 125));
        graphics.setBorder(BorderFactory.createBevelBorder(0, Color.black, Color.green));
        if (shouldWeightX) {
            top.weightx = 0.5;
        }
        top.fill = GridBagConstraints.HORIZONTAL;
        top.gridx = 1;
        top.gridy = 0;
        pane.add(graphics, top);

    //    Board board = new Board();
        board.setPreferredSize(new Dimension(300, 600));
        board.setBorder(BorderFactory.createBevelBorder(0, Color.ORANGE, Color.RED));
//        if (shouldFill) {
//            //natural height, maximum width
//            gamePanel.fill = GridBagConstraints.HORIZONTAL;
//        }
        gamePanel.fill = GridBagConstraints.HORIZONTAL;
        gamePanel.weightx = 0.0;
        gamePanel.gridwidth = 3;
        gamePanel.gridx = 0;
        gamePanel.gridy = 1;
        pane.add(board, gamePanel);
        
        // Buttons panel
        btnSave = new JButton("Save");
        btnQuit = new JButton("Quit");
        
        if (shouldFill) {
            footer.fill = GridBagConstraints.HORIZONTAL;
        }
        
        JPanel buttons = new JPanel();
        buttons.setPreferredSize(new Dimension(300, 125));
        buttons.setBorder(BorderFactory.createBevelBorder(0, Color.magenta, Color.cyan));

        footer.gridx = 0;
        footer.gridy = 2;
        footer.ipady = 0;
        footer.weightx = 0.0;
        footer.weighty = 1.0;
        footer.gridwidth = 2;

        
        pane.add(buttons, footer);
        buttons.add(btnSave);
        buttons.add(btnQuit);
         
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
     //   JFrame frame = new JFrame("GridBagLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //    frame.addKeyListener(board);      // commented out 2018-10-14
        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
