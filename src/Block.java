package src;

import java.awt.*;

public class Block extends GameObject {

    private Image blockImage;
    private int x, y;
    public Block(int x, int y, gameID id, Image img){
        super(x, y, id);
        this.x = x;
        this.y=y;
        this.blockImage = img;
    }
    @Override
    public void render(Graphics graphics){
        this.setWidth(blockImage.getWidth(null));
        this.setHeight(blockImage.getHeight(null));
        graphics.drawImage(blockImage, this.getX(), this.getY(), null);
    }
    public Rectangle getBounds(){
        return new Rectangle(x, y, 32, 32);
    }
}
