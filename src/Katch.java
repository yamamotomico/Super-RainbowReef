package src;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Katch extends GameObject implements KeyListener {

    private int x;
    private int y;
    private double speed;
    protected float velocityX = 0, velocityY = 0;
    private boolean isShooting;
    private  gameID id;
    Image katchImage;
    Handler gameHandler;

    public Katch(int x, int y, gameID id, Image img, Handler gameHandler) {
        super(x, y, id);
        this.x=x;
        this.y=y;
        this.speed = 0.2;
        this.id = id;
        this.katchImage =img;
        this.gameHandler = gameHandler;
        this.isShooting = true;
    }

    public void updatePosition(Pop star){
        int directionX = (int) Math.abs(Math.cos(star.getAngle()) * star.getSpeed());
        int directionY = (int) (Math.sin(star.getAngle()) * star.getSpeed());
        star.setX(star.getX() + directionX);
        star.setY(star.getY() - directionY);
    }
    @Override
    public void render(Graphics graphics){
        this.setWidth(katchImage.getWidth(null));
        this.setHeight(katchImage.getHeight(null));
        this.checkBorder();
        graphics.drawImage(katchImage, this.getX(), this.getY(), null);
    }
    public void checkBorder() {
        if(this.getX() < 20)
            this.setX(20);
        if(this.getX() > 540)
            this.setX(540);
    }
    //****************************************************************************************************************
    public void collision(Pop star) {
        if(this.getId() == gameID.Katch) {
            if(star.getX() > this.getX() - star.getWidth() && star.getX() < this.getX() + this.getWidth() && star.getY() > this.getY() - star.getHeight()) {
                if(star.getDirection() == "upDown_leftRight") {
                    if((star.getX() > this.getX() - star.getWidth()) && (star.getX() < this.getX() + (this.getWidth()/2)) && (star.getY() > this.getY() - star.getHeight())){
                        updatePosition(star);
                        star.setDirection("downUp_rightLeft");
                    }
                    updatePosition(star);
                    star.setDirection("downUp_leftRight");
                }
                if(star.getDirection() == "upDown_rightLeft") {
                    updatePosition(star);
                    star.setDirection("downUp_rightLeft");
                }
                star.setAngle(Math.PI - star.getAngle());
                star.setSpeed(6);
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
    public  Rectangle getBounds(){
        return new Rectangle(x, y, 80, 30);
    }
    //******************************************************************************************************************
    public double getSpeed() {
        return speed;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public float getVelocityX() {
        return velocityX;
    }
    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }
    public float getVelocityY() {
        return velocityY;
    }
    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }
    public void setCanShootOff(){
        isShooting = false;
    }
    public boolean isShooting() {
        return isShooting;
    }
    public void setCanShootOn(){
        isShooting = true;
    }
}
