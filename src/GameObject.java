package src;

import java.awt.*;
import java.io.IOException;

public abstract class GameObject {

    protected int height;
    protected int width;
    protected int x, y;
    protected gameID id;
    private Image image;
    private boolean isVisible = true;

    public GameObject(int x, int y, gameID id){
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public abstract void render(Graphics graphics) throws IOException;
    public abstract Rectangle getBounds();
    public void checkBorder() {
        if(this.getX() < 20)
            this.setX(20);
        if(this.getX() > 540)
            this.setX(540);
    }
    //**********************************************************************************************************************
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public gameID getId() {
        return id;
    }

    public void setId(gameID id) {
        this.id = id;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
