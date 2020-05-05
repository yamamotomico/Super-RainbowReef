package src;

import java.awt.*;

public class Wall extends GameObject {

    private int x, y;
    Image wallImage;

    public Wall(int x, int y, gameID id, Image image){
        super(x, y, id);
        this.x = x;
        this.y = y;
        this.wallImage = image;
    }

    @Override
    public void render(Graphics graphics){
        graphics.drawImage(wallImage, this.getX(), this.getY(), null);
        this.setWidth(wallImage.getWidth(null));
        this.setHeight(wallImage.getHeight(null));
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, 20, 20);
    }
}
