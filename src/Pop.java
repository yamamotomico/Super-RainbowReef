package src;

import java.awt.*;
import java.io.IOException;

public class Pop extends GameObject {

    private int x, y;
    private double speed = 5.0;
    private double angle = Math.PI/4;
    private int score;
    private int lives;
    private boolean inFlying;
    Handler gameHandler;
    Image popImage;
    private String direction = "downUp_leftRight";

    public Pop(int x, int y, gameID id, Image image, Handler gameHandler){
        super(x, y, id);
        this.x = x;
        this.y = y;
        this.gameHandler = gameHandler;
        this.popImage =image;
        this.score = 0;
        this.lives = 3;
        this.inFlying = false;
    }
    @Override
    public void render(Graphics graphics) throws IOException {
        this.setWidth(popImage.getWidth(null));
        this.setHeight(popImage.getHeight(null));
        this.checkBorder();
        graphics.drawImage(popImage, this.getX(), this.getY(), null);
    }
    public void checkBorder() {
        if(!this.isInFlying()) {
            if (this.getX() < 55)
                this.setX(55);
            if (this.getX() > 575)
                this.setX(575);
        }
    }
    public void collision(Pop star) {
        int directionX = (int) Math.abs(Math.cos(star.getAngle()) * star.getSpeed());
        int directionY = (int) (Math.sin(star.getAngle()) * star.getSpeed());
        if(star.getDirection() == "downUp_leftRight") {
            star.setX(star.getX() + directionX);
            star.setY(star.getY() - directionY);
        }
        if(star.getDirection() == "downUp_rightLeft") {
            star.setX(star.getX() - directionX);
            star.setY(star.getY() - directionY);
        }
        if(star.getDirection() == "upDown_leftRight") {
            star.setX(star.getX() + directionX);
            star.setY(star.getY() + directionY);
        }
        if(star.getDirection() == "upDown_rightLeft") {
            star.setX(star.getX() - directionX);
            star.setY(star.getY() + directionY);
        }
        //wall on the left
        if(star.getX() < 20) {
            if(star.getDirection() == "upDown_rightLeft") {
                star.setDirection("upDown_leftRight");
                star.setAngle(Math.PI - star.getAngle());
                double nSpeed = star.getSpeed()-2;
                star.setSpeed(nSpeed);
            }
            if(star.getDirection() == "downUp_rightLeft") {
                star.setDirection("downUp_leftRight");
                star.setAngle(Math.PI - star.getAngle());
                double nSpeed = star.getSpeed()-2;
                star.setSpeed(nSpeed);
            }
        }
        //wall on the right
        if(star.getX() > 580) {
            if(star.getDirection() == "upDown_leftRight") {
                star.setDirection("upDown_rightLeft");
                star.setAngle(Math.PI - star.getAngle());
                double nSpeed = star.getSpeed()-2;
                star.setSpeed(nSpeed);
            }
            if(star.getDirection() == "downUp_leftRight") {
                star.setDirection("downUp_rightLeft");
                star.setAngle(Math.PI - star.getAngle());
                double nSpeed = star.getSpeed()-2;
                star.setSpeed(nSpeed);
            }
        }
        //wall on the top
        if(star.getY() < 20) {
            if(star.getDirection() == "downUp_leftRight") {
                star.setDirection("upDown_leftRight");
                star.setAngle(Math.PI - star.getAngle());
                double nSpeed = star.getSpeed()-2;
                star.setSpeed(nSpeed);
            }
            if(star.getDirection() == "downUp_rightLeft") {
                star.setDirection("upDown_rightLeft");
                star.setAngle(Math.PI - star.getAngle());
                double nSpeed = star.getSpeed()-2;
                star.setSpeed(nSpeed);
            }
        }
    }
    @Override
    public Rectangle getBounds(){
        return new Rectangle(this.getX(), this.getY(), 35, 35);
    }
    //******************************************************************************************************************
    public String getDirection() { return direction; }
    public boolean isInFlying() {
        return inFlying;
    }
    public void setInFlying(boolean inFlying) {
        this.inFlying = inFlying;
    }
    public double getSpeed() {
        return speed;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public double getAngle() {
        return angle;
    }
    public void setAngle(double angle) {
        this.angle = angle;
    }
    public int getLives() {
        return lives;
    }
    public void setLives(int lives) {
        this.lives = lives;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }
}
