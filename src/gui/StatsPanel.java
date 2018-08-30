/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author kmne6
 */
public class StatsPanel extends JPanel {

    private String title;

    public StatsPanel() {

        this.title = "Score";
        
        this.setBounds(0, 11, 150, 90);
        this.setVisible(true);

//        setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createTitledBorder(title),
//                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }
}
