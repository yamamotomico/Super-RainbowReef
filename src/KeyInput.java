package src;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    Handler gameHandler;

    public KeyInput(Handler gameHandler){
        this.gameHandler = gameHandler;
    }
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        for (int i = 0; i < gameHandler.object.size(); i++) {
            GameObject temp = gameHandler.object.get(i);
            //checks if its Tank One
            if(temp.getId() == gameID.Katch){
             //   if (key == KeyEvent.VK_W) gameHandler.setUpKatch(true);
             //   if (key == KeyEvent.VK_S) gameHandler.setDownKatch(true);
                if (key == KeyEvent.VK_A) gameHandler.setLeftKatch(true);
                if (key == KeyEvent.VK_D) gameHandler.setRightKatch(true);
            }
        }
    }
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        for (int i = 0; i < gameHandler.object.size(); i++) {
            GameObject temp = gameHandler.object.get(i);

            //checks if its Tank One
            if(temp.getId() == gameID.Katch){
             //   if (key == KeyEvent.VK_W) gameHandler.setUpKatch(false);
             //   if (key == KeyEvent.VK_S) gameHandler.setDownKatch(false);
                if (key == KeyEvent.VK_A) gameHandler.setLeftKatch(false);
                if (key == KeyEvent.VK_D) gameHandler.setRightKatch(false);
            }
        }
    }
}
