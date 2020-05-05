package src;

import java.awt.*;
import java.util.LinkedList;

public class Handler {

    static LinkedList<GameObject> object = new LinkedList<GameObject>();

    private boolean upKatch = false;
    private boolean downKatch = false;
    private boolean rightKatch = false;
    private boolean leftKatch = false;

    //**************************************************************************************
    public void addObject(GameObject x){
        object.add(x);
    }
    public void removeObject(GameObject x){
        object.remove(x);
    }
    //**************************************************************************************
    public boolean isUpKatch() {
        return upKatch;
    }
    public void setUpKatch(boolean upKatch) {
        this.upKatch = upKatch;
    }
    public boolean isDownKatch() {
        return downKatch;
    }
    public void setDownKatch(boolean downKatch) {
        this.downKatch = downKatch;
    }
    public boolean isRightKatch() {
        return rightKatch;
    }
    public void setRightKatch(boolean rightKatch) {
        this.rightKatch = rightKatch;
    }
    public boolean isLeftKatch() {
        return leftKatch;
    }
    public void setLeftKatch(boolean leftKatch) {
        this.leftKatch = leftKatch;
    }
}
