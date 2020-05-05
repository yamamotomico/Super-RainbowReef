package src;

import java.awt.*;

public class Bigleg extends GameObject {

    private Image bigLegImage;
    private int x, y;
    public Bigleg(int x, int y, gameID id, Image image){
        super(x, y, id);
        this.x=x;
        this.y=y;
        this.id=id;
        this.bigLegImage =image;
    }
    @Override
    public void render(Graphics graphics){
        this.setWidth(bigLegImage.getWidth(null));
        this.setHeight(bigLegImage.getHeight(null));
        graphics.drawImage(bigLegImage, this.getX(), this.getY(), null);
    }
    public Rectangle getBounds(){
        return new Rectangle(this.getX(), this.getY(), bigLegImage.getWidth(null), bigLegImage.getHeight(null));
    }
}
