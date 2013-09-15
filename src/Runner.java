

import java.awt.Dimension;

import GameEngine.GameCanvas;
import javax.swing.*;

public class Runner {
    public static void main (String [] args) {
        
        JFrame frame = new JFrame("Survival Game");
        SurvivalGame ag = new SurvivalGame(100);
        ag.linkToFrame(frame);
        
        GameCanvas glc = new GameCanvas(ag);
        frame.add(glc);
        
        frame.setResizable(true);
        frame.setMinimumSize(new Dimension(770, 800));

        frame.setSize(770, 800);
        frame.setVisible(true);
        
        // need this so that you don't have to click on the window to gain focus ;)
        glc.requestFocusInWindow();
    }
}
