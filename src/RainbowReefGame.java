package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;

public class RainbowReefGame extends JPanel implements KeyListener, ActionListener, Runnable {

    protected int velocityX = 0, velocityY = 0;
    private Timer process;
    private int delay = 10;
    private int renderString = 0;
    private double newSec;
    //**********************************************************************
    private boolean scoreBoard;
    private Pop pop;
    private Katch katch;
    private Handler gameHandler;
    private boolean isRunning = false;
    private Thread thread;
    //*********************************************************************
    private long gameStart;
    private long timeEnd;
    private long timeDelta;
    private double sec;
    private boolean dRadius;
    private boolean gameOver;
    private float timeAlpha;
    private boolean secondLevelBeat;
    private boolean gameWon;
    //*********************************************************************
    private int generateBL1;
    private int generateBL2;
    private int generateBL3;
    private  boolean play = false;
    private boolean firstLevelBeat;
    private  boolean resetForLvl2 = false;
    private boolean resetForLvl3 = false;
    //*********************************************************************
    //HashMap and ArrayList
    public static HashMap<String,Image> ss;
    LinkedList<Block> blocksLevel1;
    LinkedList<Block> blocksLevel2;
    LinkedList<Block> blocksLevel3;
    LinkedList<Wall> gameWalls;
    LinkedList<Bigleg> bigL1;
    LinkedList<Bigleg> bigL2;
    LinkedList<Bigleg> bigL3;

    public RainbowReefGame(){
        Load();
        blocksLevel1 = new LinkedList<>();
        blocksLevel2 = new LinkedList<>();
        blocksLevel3 = new LinkedList<>();
        gameWalls = new LinkedList<>();
        bigL1 = new LinkedList<>();
        bigL2 = new LinkedList<>();
        bigL3 = new LinkedList<>();
        firstLevelBeat = false;
        secondLevelBeat = false;
        gameOver = false;
        gameWon = false;
        gameStart = System.currentTimeMillis();
        timeEnd = 0;
        timeDelta = 0;
        timeAlpha = 1f;
        newSec = 0;
        dRadius = false;
        scoreBoard = false;
        //Location of the pop and Katch
        pop = new Pop(310, 405, gameID.Pop, ss.get("pop"), gameHandler);
        katch = new Katch(280, 445, gameID.Katch, ss.get("katch"), gameHandler);
        //**************************************************************************************************************
        this.blocksLevel1();
        this.blocksLevel2();
        this.blocksLevel3();
        this.addWalls();
        this.bigLegLevel1();
        this.bigLegLevel2();
        this.bigLegLevel3();
        generateBL1 = bigL1.size();
        generateBL2 = bigL2.size();
        generateBL3 = bigL3.size();
        //**************************************************************************************************************
        addKeyListener(this);
        this.setFocusable(true);
        setFocusTraversalKeysEnabled(false );
        process = new Timer(delay, this);
        process.start();
    }
    public void Load(){
        ss = new HashMap<>();
        try {
            ss.put("background", this.getSprite("/Background1.png"));
            ss.put("background2", this.getSprite("/Background2.png"));
            ss.put("bigleg", this.getSprite("/Bigleg_paint.gif"));
            ss.put("bigleg_small", this.getSprite("/Bigleg_small_paint.gif"));
            ss.put("solidBlock", this.getSprite("/Block_solid.gif"));
            ss.put("splitBlock", this.getSprite("/Block_split.gif"));
            ss.put("lifeBlock", this.getSprite("/Block_life.gif"));
            ss.put("doubleBlock", this.getSprite("/Block_double.gif"));
            ss.put("purpleBlock", this.getSprite("/Block1.gif"));
            ss.put("yellowBlock", this.getSprite("/Block2.gif"));
            ss.put("redBlock", this.getSprite("/Block3.gif"));
            ss.put("greenBlock", this.getSprite("/Block4.gif"));
            ss.put("cyanBlock", this.getSprite("/Block5.gif"));
            ss.put("blueBlock", this.getSprite("/Block6.gif"));
            ss.put("grayBlock", this.getSprite("/Block7.gif"));
            ss.put("wall", this.getSprite("/Wall.gif"));
            ss.put("katch", this.getSprite("/Katch_paint.gif"));
            ss.put("pop", this.getSprite("/Pop_paint.gif"));
            ss.put("win", this.getSprite("/Congratulation.gif"));
            ss.put("title", this.getSprite("/Title.gif"));
            ss.put("lost", this.getSprite("/gameover.png"));
            ss.put("heart", this.getSprite("/heart.png"));
        }
        catch (Exception e){
            System.out.println("Fail Loading");
        }
    }
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    //******************************************************************************************************************
    //START OF DRAWING
    public void addWalls() {
        // top
        for(int i = 0; i < 32; i++) {
            Wall wallTop = new Wall(i * 20, 0, gameID.Wall, ss.get("wall"));
            gameWalls.add(wallTop);
        }
        // left
        for(int i = 0; i < 23; i++) {
            Wall wallLeft = new Wall(0, (i + 1) * 20, gameID.Wall, ss.get("wall"));
            gameWalls.add(wallLeft);
        }
        // right
        for(int i = 0; i < 23; i++) {
            Wall wallRight = new Wall(620, (i + 1) * 20, gameID.Wall, ss.get("wall"));
            gameWalls.add(wallRight);
        }
    } //done
    public void bigLegLevel1() {
        for(int i = 0; i < 3; i++) {
            Bigleg boss1 = new Bigleg(i * 160 + 140, 20, gameID.Bigleg, ss.get("bigleg_small"));
            bigL1.add(boss1);
        }
    } //done
    public void bigLegLevel2() {
        Bigleg boss1 = new Bigleg(25, 50, gameID.Bigleg, ss.get("bigleg"));
        Bigleg boss2 = new Bigleg(542, 50, gameID.Bigleg, ss.get("bigleg"));
        bigL2.add(boss1);
        bigL2.add(boss2);
    } //done
    public void bigLegLevel3() {
        Bigleg boss3 = new Bigleg(250, 50, gameID.Bigleg, ss.get("bigleg"));
        Bigleg boss4 = new Bigleg(325, 50, gameID.Bigleg, ss.get("bigleg"));
        bigL3.add(boss3);
        bigL3.add(boss4);
    } //done
    public void blocksLevel1(){
        for(int i = 0; i < 12; i++) {
            Block solid1 = new Block(100, i * 20 + 20, gameID.Block, ss.get("solidBlock"));
            Block solid4 = new Block(500, i * 20 + 20, gameID.Block, ss.get("solidBlock"));
            blocksLevel1.add(solid1);
            blocksLevel1.add(solid4);
            Block solid2 = new Block(220, i * 20 + 20, gameID.Block, ss.get("cyanBlock"));
            Block solid3 = new Block(380, i * 20 + 20, gameID.Block, ss.get("cyanBlock"));
            blocksLevel1.add(solid2);
            blocksLevel1.add(solid3);

        }
        // life
       for(int i = 0; i < 2; i++) {
            Block life1 = new Block(i * 40 + 20, 20, gameID.Lifeblock, ss.get("lifeBlock"));
            Block life2 = new Block(640 - (i + 1) * 40 - 20, 20, gameID.Lifeblock, ss.get("lifeBlock"));
            blocksLevel1.add(life1);
            blocksLevel1.add(life2);
        }
        //purple
        for(int i = 0; i < 11; i++) {
            Block purple1 = new Block(260, (i + 1) * 20, gameID.Block, ss.get("purpleBlock"));
            Block purple2 = new Block(340, (i + 1) * 20, gameID.Block, ss.get("purpleBlock"));
            blocksLevel1.add(purple1);
            blocksLevel1.add(purple2);
        }
        //yellow
        for(int i = 0; i < 10; i++) {
            Block yellow1 = new Block(140, (i) * 20 + 60, gameID.Block, ss.get("yellowBlock"));
            Block yellow2 = new Block(460, (i) * 20 + 60, gameID.Block, ss.get("yellowBlock"));
            blocksLevel1.add(yellow1);
            blocksLevel1.add(yellow2);
        }
        //red
        for(int i = 0; i < 11; i++) {
            Block red1 = new Block(60, (i + 2) * 20, gameID.Block, ss.get("redBlock"));
            Block red2 = new Block(540, (i + 2) * 20, gameID.Block, ss.get("redBlock"));
            blocksLevel1.add(red1);
            blocksLevel1.add(red2);
        }
        //green
        for(int i = 0; i < 12; i++) {
            Block green1 = new Block(180, (i + 1) * 20, gameID.Block, ss.get("greenBlock"));
            Block green2 = new Block(420, (i + 1) * 20, gameID.Block, ss.get("greenBlock"));
            blocksLevel1.add(green1);
            blocksLevel1.add(green2);
        }
        //blue
        for(int i = 0; i < 11; i++) {
            Block blue1 = new Block(20, (i + 2) * 20, gameID.Block, ss.get("blueBlock"));
            Block blue2 = new Block(580, (i + 2) * 20, gameID.Block, ss.get("blueBlock"));
            blocksLevel1.add(blue1);
            blocksLevel1.add(blue2);
        }
        //gray
        for(int i = 0; i < 9; i++) {
            Block gray1 = new Block(300, i * 20 + 60, gameID.Block, ss.get("grayBlock"));
            blocksLevel1.add(gray1);
        }
    } //done
    public void blocksLevel2() {
        //life
        for(int i = 0; i < 2; i++) {
            Block life1 = new Block(260, i * 20 + 160, gameID.Lifeblock, ss.get("lifeBlock"));
            Block life2 = new Block(340, i * 20 + 160, gameID.Lifeblock, ss.get("lifeBlock"));
            Block life3 = new Block(300, i * 20 + 160, gameID.Lifeblock, ss.get("lifeBlock"));
            blocksLevel2.add(life1);
            blocksLevel2.add(life2);
            blocksLevel2.add(life3);
        }
        //blue
        for(int i = 0; i < 8; i++) {
            Block blue1 = new Block(260, i * 20 + 200, gameID.Block, ss.get("blueBlock"));
            Block blue2 = new Block(340, i * 20 + 200, gameID.Block, ss.get("blueBlock"));
            blocksLevel2.add(blue1);
            blocksLevel2.add(blue2);
        }
        //yellow
        for(int i = 0; i < 10; i++) {
            Block yellow1 = new Block(140, i * 20 + 42, gameID.Block, ss.get("yellowBlock"));
            Block yellow2 = new Block(460, i * 20 + 42, gameID.Block, ss.get("yellowBlock"));
            blocksLevel2.add(yellow1);
            blocksLevel2.add(yellow2);
        }
        //purple
        for(int i = 0; i < 15; i++) {
            Block purple1 = new Block(100, i * 20 + 42, gameID.Block, ss.get("purpleBlock"));
            Block purple2 = new Block(500, i * 20 + 42, gameID.Block, ss.get("purpleBlock"));
            blocksLevel2.add(purple1);
            blocksLevel2.add(purple2);
        }
        //cyan
        for(int i = 0; i < 9; i++) {
            Block cyan1 = new Block(60, i * 20 + 135, gameID.Block, ss.get("cyanBlock"));
            Block cyan2 = new Block(540, i * 20 + 135, gameID.Block, ss.get("cyanBlock"));
            blocksLevel2.add(cyan1);
            blocksLevel2.add(cyan2);
        }
        //red
        for(int i = 0; i < 16; i++) {
            Block red1 = new Block(40 * i, 22, gameID.Block, ss.get("redBlock"));
            blocksLevel2.add(red1);
        }
        //gray
        for(int i = 0; i < 11; i++) {
            Block gray1 = new Block(20, i * 20 + 135, gameID.Block, ss.get("grayBlock"));
            Block gray2 = new Block(580, i * 20 + 135, gameID.Block, ss.get("grayBlock"));
            blocksLevel2.add(gray1);
            blocksLevel2.add(gray2);
        }
        //solid
        for(int i = 0; i < 16; i++) {
            Block solid = new Block(300, i * 20 + 42, gameID.Block, ss.get("solidBlock"));
            blocksLevel2.add(solid);
        }
    } //done
    public void blocksLevel3(){
        //life
        for(int i = 0; i < 7; i++) {
            Block life1 = new Block(i * 40 + 20, 180, gameID.Lifeblock, ss.get("lifeBlock"));
            Block life2 = new Block(640 - (i + 1) * 40 - 20, 180, gameID.Lifeblock, ss.get("lifeBlock"));
            blocksLevel3.add(life1);
            blocksLevel3.add(life2);
        }
        //BLOCKS
        for(int i = 0; i < 24; i++) {
            Block purple1 = new Block(20 + (i*40), 200, gameID.Block, ss.get("purpleBlock"));
            Block purple2 = new Block(20 + (i*40), 320, gameID.Block, ss.get("purpleBlock"));
            Block purple3 = new Block(20 + (i*40), 240, gameID.Block, ss.get("purpleBlock"));
            Block cyan1 = new Block(20 + (i*40), 140, gameID.Block, ss.get("cyanBlock"));
            Block gray1 = new Block(20 + (i*40), 160, gameID.Block, ss.get("grayBlock"));
            Block gray2 = new Block(20 + (i*40), 220, gameID.Block, ss.get("grayBlock"));
            Block gray3 = new Block(20 + (i*40), 280, gameID.Block, ss.get("grayBlock"));
            Block gray4 = new Block(20 + (i*40), 340, gameID.Block, ss.get("grayBlock"));
            Block red1 = new Block(20 + (i*40), 22, gameID.Block, ss.get("redBlock"));
            Block red2 = new Block(20 + (i*40), 200, gameID.Block, ss.get("redBlock"));
            Block red3 = new Block(20 + (i*40), 300, gameID.Block, ss.get("redBlock"));
            Block blue1 = new Block(20 + (i*40), 260, gameID.Block, ss.get("blueBlock"));
            blocksLevel3.add(cyan1);
            blocksLevel3.add(blue1);
            blocksLevel3.add(red1);
            blocksLevel3.add(red2);
            blocksLevel3.add(red3);
            blocksLevel3.add(gray1);
            blocksLevel3.add(gray2);
            blocksLevel3.add(gray3);
            blocksLevel3.add(gray4);
            blocksLevel3.add(purple1);
            blocksLevel3.add(purple2);
            blocksLevel3.add(purple3);
        }
    } //done
    //******************************************************************************************************************
    public void setBackground(Graphics graphics){
        Image backgroundImage;
        backgroundImage = ss.get("background");
        graphics.drawImage(backgroundImage, 0, 0, null);
    }
    public void renderTitle(Graphics graphics){
        Image titleScreenImage;
        titleScreenImage = ss.get("title");
        graphics.drawImage(titleScreenImage, 0, 0, 640, 480, null);
    }
    public void renderBigL1(Graphics graphics) {
        if(!firstLevelBeat) {
            for (int i = 0; i < bigL1.size(); i++) {
                bigL1.get(i).render(graphics);
            }
        }
    }
    public void renderBigL2(Graphics graphics){
        if(firstLevelBeat) {
            for (int i = 0; i < bigL2.size(); i++) {
                bigL2.get(i).render(graphics);
            }
        }
    }
    public void renderBigL3(Graphics graphics){
        if(secondLevelBeat) {
            for (int i = 0; i < bigL3.size(); i++) {
                bigL3.get(i).render(graphics);
            }
        }
    }
    public void renderBlocks(Graphics graphics){
        if(!firstLevelBeat) {
            for (Block blockLevel1 : blocksLevel1) {
                blockLevel1.render(graphics);
            }
        }
        if(firstLevelBeat){
            if(!secondLevelBeat) {
                for (Block blockLevel2 : blocksLevel2) {
                    blockLevel2.render(graphics);
                }
            }
        }
        if(secondLevelBeat){
            for(Block blockLevel3: blocksLevel3){
                blockLevel3.render(graphics);
            }
        }
    }
    public void renderScore(Graphics graphics){
        Font font = new Font("Arial", Font.BOLD, 25);
        graphics.setFont(font);
        graphics.drawString("Score: " + pop.getScore(), 460, 450);
    }
    public void renderWalls(Graphics graphics){
        for(int i = 0; i < gameWalls.size(); i++){
            if(gameWalls.get(i).isVisible()){
                gameWalls.get(i).render(graphics);
            }
        }
    }
    public void renderPop(Graphics graphics){
        try {
            pop.render(graphics);
        }
        catch(IOException ex){
        }
    }
    public void renderKatch(Graphics graphics){katch.render(graphics);}
    private void renderLives(Graphics graphics) {
        Image heartImage;
        heartImage = ss.get("heart");
        for(int i = 0; i < pop.getLives(); i++)
            graphics.drawImage(heartImage, i * 40 + 20, 425, null);
    }
    public void renderEndScreen(Graphics graphics){
        Image endScreenImage;
        endScreenImage = ss.get("win");
        this.readHighscores(graphics);
        graphics.drawImage(endScreenImage, 0, 0, 654, 519, null);
    }
    public void renderGameover(Graphics graphics){
        Image gameoverImage;
        gameoverImage = ss.get("lost");
        readHighscores(graphics);
        graphics.drawImage(gameoverImage, 200, 20, null);
    }
    public void setLvl1Beat(){
        if (generateBL1 == 0 && resetForLvl2 == false) {
            blocksLevel1.removeAll(blocksLevel1);
            firstLevelBeat = true;
            pop.setX(310);
            pop.setY(405);
            pop.setSpeed(5);
            pop.setAngle(Math.PI / 4);
            pop.setDirection("downUp_leftRight");
            pop.setInFlying(false);
            katch.setX(280);
            katch.setY(445);
            katch.setCanShootOn();
            resetForLvl2 = true;
        }
        if(generateBL2 == 0 && resetForLvl3 == false){
            blocksLevel1.removeAll(blocksLevel1);
            secondLevelBeat = true;
            pop.setX(310);
            pop.setY(405);
            pop.setSpeed(5);
            pop.setAngle(Math.PI / 4);
            pop.setDirection("downUp_leftRight");
            pop.setInFlying(false);
            katch.setX(280);
            katch.setY(445);
            katch.setCanShootOn();
            resetForLvl3 = true;
        }
    }
    public void resetPosition(){
        if(pop.getY() > 480 ) {
            if(pop.getLives() > 0)
                pop.setLives(pop.getLives() - 1);
            if(pop.getLives() == 0){
            }
            if (pop.getLives() > 0) {
                pop.setX(310);
                pop.setY(405);
                pop.setSpeed(5);
                pop.setAngle(Math.PI / 4);
                pop.setDirection("downUp_leftRight");
                pop.setInFlying(false);
                katch.setX(280);
                katch.setY(445);
                katch.setCanShootOn();
            }
        }
        if(pop.getY() < 20)
        {
            pop.setY(40);
            pop.setSpeed(5);
            pop.setAngle(Math.PI - pop.getAngle());
            if(pop.getDirection() == "downUp_leftRight") {
                pop.setDirection("upDown_rightLeft");
            }
        }
    }
    public void checkLives(){
        if(pop.getLives()<1) {
            setGameOver(true);
            process.stop();
        }
    }
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        double timePassed = getCurrentSeconds();
        if(!gameWon) {
            if (timePassed < 10) {
                this.renderTitle(graphics);
            } else {
                if (!gameOver) {
                    this.setBackground(graphics);
                    this.resetPosition();
                    this.wallCollision();
                    this.setLvl1Beat();
                    this.renderScore(graphics);
                    this.renderLives(graphics);
                    this.renderBigL1(graphics);
                    this.renderBigL2(graphics);
                    this.renderBigL3(graphics);
                    this.renderBlocks(graphics);
                    this.renderWalls(graphics);
                    this.renderPop(graphics);
                    this.renderKatch(graphics);
                }
                if (gameOver) {
                    this.setBackground(graphics);
                    this.renderGameover(graphics);
                }
            }
        }
        if(gameWon)
            this.renderEndScreen(graphics);
    }
    //******************************************************************************************************************
    //Collision
    public void wallCollision() {
        if (pop.getY() < 20) {
            pop.setY(40);
            pop.setSpeed(5);
            pop.setAngle(Math.PI - pop.getAngle());
            if (pop.getDirection() == "downUp_leftRight") {
                pop.setDirection("upDown_rightLeft");
            }
        }
        if(pop.isInFlying()) {
            if (pop.getX() + pop.getWidth() < 56);
            if (pop.getX()  > 580);
        }
    }
    public void biglegCollision(){
        try {
            for (Bigleg BLObject : bigL1) {
                if (BLObject.getBounds().intersects(pop.getBounds()) || pop.getBounds().intersects(BLObject.getBounds())) {
                    bigL1.remove(BLObject);
                    generateBL1--;
                }
            }
            if(firstLevelBeat) {
                for (Bigleg BLObject : bigL2) {
                    if (BLObject.getBounds().intersects(pop.getBounds()) || pop.getBounds().intersects(BLObject.getBounds())) {
                        bigL2.remove(BLObject);
                        generateBL2--;
                    }
                }
            }
            if(secondLevelBeat){
                for (Bigleg BLObject : bigL3) {
                    if (BLObject.getBounds().intersects(pop.getBounds()) || pop.getBounds().intersects(BLObject.getBounds())) {
                        bigL3.remove(BLObject);
                        generateBL3--;
                        if(generateBL3 ==0)
                            gameWon=true;
                    }
                }
            }
        }
        catch(Exception e){ ;
        }
    }
    public void blockCollision() {
            try {
                if (!firstLevelBeat) {
                    for (Block block : blocksLevel1) {
                        if (block.getBounds().intersects(pop.getBounds()) || pop.getBounds().intersects(block.getBounds())) {
                            if (block.getId() == gameID.Lifeblock)
                                pop.setLives(pop.getLives() + 1);
                            blocksLevel1.remove(block);
                            pop.setScore(pop.getScore() + 10);
                            pop.setSpeed(-pop.getSpeed());
                        }
                    }
                }

                if (firstLevelBeat) {
                    for (Block block : blocksLevel2) {
                        if (block.getBounds().intersects(pop.getBounds()) || pop.getBounds().intersects(block.getBounds())) {
                            if (block.getId() == gameID.Lifeblock)
                                pop.setLives(pop.getLives() + 1);
                            blocksLevel2.remove(block);
                            pop.setScore(pop.getScore() + 15);
                            pop.setSpeed(-pop.getSpeed());
                        }
                    }

                }
                if (secondLevelBeat) {
                    for (Block block : blocksLevel3) {
                        if (block.getBounds().intersects(pop.getBounds()) || pop.getBounds().intersects(block.getBounds())) {
                            if (block.getId() == gameID.Lifeblock)
                                pop.setLives(pop.getLives() + 1);
                            blocksLevel3.remove(block);
                            pop.setScore(pop.getScore() + 20);
                            pop.setSpeed(-pop.getSpeed());
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
    //******************************************************************************************************************
    public Image getSprite(String res) {
        URL y = RainbowReefGame.class.getResource(res);
        Image image = Toolkit.getDefaultToolkit().getImage(y);
        try {
            MediaTracker x = new MediaTracker(this);
            x.addImage(image, 0);
            x.waitForID(0);
        } catch (Exception e) {
        }
        return image;
    }
    // reading Scores
    public void readHighscores(Graphics graphics){
        File file = new File("scores.txt");
        try {
            if(!scoreBoard)
            {
            String string =  "\n" + "   Player: " + pop.getScore();
            BufferedWriter board = new BufferedWriter(new FileWriter("scores.txt", true));
            board.append(' ');
            board.append(string);
            board.close();
            scoreBoard = true;
        }
            BufferedReader score = new BufferedReader(new FileReader(file));
            String scoreString;
            Font font = new Font("Arial", Font.BOLD, 30);
            graphics.setFont(font);
            graphics.drawString("  Score Board! ", 210, 130);
            while ((scoreString=score.readLine())!=null){
                renderString(graphics, scoreString, 210, 140);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void renderString(Graphics graphics, String text, int x, int y) {
        for (String line : text.split("\n")) {
            y += renderString * graphics.getFontMetrics().getHeight();
            graphics.drawString(line, x, y);
            renderString += 1;
        }
    }

    public double getCurrentSeconds(){
        long timeEnd = System.currentTimeMillis();
        long deltaEnd = timeEnd - gameStart;
        double eTime = deltaEnd / 1000.0;
        return eTime;
    }
    public void updatePop(){
        if (!katch.isShooting()) {
            pop.collision(pop);
        }
        if (!pop.isInFlying()) {
            pop.setX(pop.getX() + velocityX);
            pop.setY(pop.getY() + velocityY);
        }
    }
    public void updateKatchPosition(){
        katch.setX(katch.getX() + velocityX);
        katch.setY(katch.getY() + velocityY);
        if (!katch.isShooting()) {
            pop.collision(pop);
            katch.collision(pop);
        }
        if (!pop.isInFlying()) {
            pop.setX(pop.getX() + velocityX);
            pop.setY(pop.getY() + velocityY);
        }
    }
    public void update() {
        checkLives();
        updateKatchPosition();
        biglegCollision();
        blockCollision();
    }
    //******************************************************************************************************************
    @Override
    public void actionPerformed(ActionEvent e) {
        process.start();
        timeAlpha += -.00091f;
        if(timeAlpha <= 0){
            timeAlpha = 0;
        }
        update();
        repaint();
        if(play){
        }
    }
    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                delta--;
            }
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
            }
        }
        stop();
    }
    private void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }
    //******************************************************************************************************************
    //KEYDETECTION
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(getCurrentSeconds() > 10) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                velocityX = -7;
            } else if (key == KeyEvent.VK_RIGHT) {
                velocityX = 7;
            }
            if (key == KeyEvent.VK_SPACE) {
                katch.setCanShootOff();
                pop.setInFlying(true);
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if(getCurrentSeconds() > 10) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                velocityX = 0;
            } else if (key == KeyEvent.VK_RIGHT) {
                velocityX = 0;
            }
        }
    }
}