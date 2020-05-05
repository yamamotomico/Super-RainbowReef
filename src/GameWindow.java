package src;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    public void start(){
        RainbowReefGame game = new RainbowReefGame();
        this.add(game);
        this.addKeyListener(game);
        this.setTitle("Super Rainbow Reef");
        this.setSize(654, 519);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    public static void main(String[] args){
        new GameWindow().start();
    }
}
