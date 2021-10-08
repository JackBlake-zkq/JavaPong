package com.pong;

import javax.swing.JFrame;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.RenderingHints;

public class Window extends JFrame implements Runnable {

    private Graphics2D g2;
    private KL keyListener = new KL();
    private Rect playerOne, 
        playerTwo,
        ball;
    
    private int ballXVelocity = 0;
    private int ballYVelocity = 0;    
    private int xVal = 0; 
    private final int PADDLE_VELOCITY = 100;     
    private int playerOneScore = 0;
    private int playerTwoScore = 0;   
    private boolean ai = false;

    public static int randomPosOrNegOne(){
        int[] x = {-1, 1};
        return x[(int)(Math.random() * 2 - 1)];
    }

    public Window(){
        this.setSize(500, 300);
        this.setTitle("Pong");
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(keyListener);
        g2 = (Graphics2D)this.getGraphics();
        playerOne = new Rect(10, 125, 10, 50, Color.red);
        playerTwo = new Rect(480, 125, 10, 50, Color.red);
        ball = new Rect(245,145, 10, 10, Color.blue);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

    public void update(double deltaTime){
        // System.out.println( 1 / deltaTime + " fps");

        g2.setColor(Color.black);
        g2.fillRect(0, 0, 500, 300);

        int paddleMoveAmount = (int)(deltaTime * PADDLE_VELOCITY + 0.5);

        if(keyListener.isKeyPressed(KeyEvent.VK_W) && playerOne.getY() >= paddleMoveAmount + 30){
            playerOne.setY(playerOne.getY() - paddleMoveAmount);
        } 
        if(keyListener.isKeyPressed(KeyEvent.VK_S) && playerOne.getY() <= 250 - paddleMoveAmount){
            playerOne.setY(playerOne.getY() + paddleMoveAmount);
        }
        if(!ai){
            if(keyListener.isKeyPressed(KeyEvent.VK_UP) && playerTwo.getY() >= paddleMoveAmount + 30){
                playerTwo.setY(playerTwo.getY() - paddleMoveAmount);
            }
            if(keyListener.isKeyPressed(KeyEvent.VK_DOWN) && playerTwo.getY() <= 250 - paddleMoveAmount){
                playerTwo.setY(playerTwo.getY() + paddleMoveAmount);
            }
        } else {
            if(ball.getX() >= 240 && ballXVelocity > 0){
                if(ball.getY() < playerTwo.getY() + 20 && playerTwo.getY() >= paddleMoveAmount + 30)
                    playerTwo.setY(playerTwo.getY() - paddleMoveAmount);
                if(ball.getY() > playerTwo.getY() + 20 && playerTwo.getY() <= 250 - paddleMoveAmount)
                    playerTwo.setY(playerTwo.getY() + paddleMoveAmount);
            }
        }
        if(keyListener.isKeyPressed(KeyEvent.VK_SPACE)){  //Start or Restart
            xVal = 0;
            ballXVelocity = randomPosOrNegOne() * arctanFunc(xVal);
            ballYVelocity = (int)(Math.random() * 60 - 29); 
            playerOneScore = 0;
            playerTwoScore = 0;  
            ball.setX(245);
            ball.setY(145);
        }
        if(keyListener.isKeyPressed(KeyEvent.VK_P)){  //toggleAI
            ai = !ai;
        }


        int ballMoveXAmount = (int)Math.round(ballXVelocity * deltaTime);
        int ballMoveYAmount = (int)Math.round(ballYVelocity * deltaTime); 

        ball.setX(ball.getX() + ballMoveXAmount);
        ball.setY(ball.getY() + ballMoveYAmount);

        playerOne.draw(g2);
        playerTwo.draw(g2);
        ball.draw(g2);

        Rect[] paddles = {playerOne, playerTwo};
        for(Rect paddle : paddles){
            if(ball.getX() > paddle.getX() - ball.getWidth() && ball.getX() < paddle.getX() + paddle.getWidth()
            && ball.getY() > paddle.getY() - ball.getWidth() && ball.getY() < paddle.getY() + paddle.getHeight()) {
                int sign = ballXVelocity / Math.abs(ballXVelocity);
                ballXVelocity = sign * arctanFunc(++xVal);
                ballXVelocity = -ballXVelocity;
                ballYVelocity = ballYVelocity + 2 * ((ball.getY()) - (paddle.getY() + 25));
            }
        }

        if(ball.getY() <= 30) ballYVelocity = Math.abs(ballYVelocity);
        if(ball.getY() >= 290) ballYVelocity = -Math.abs(ballYVelocity);


        if(ball.getX() <= 0) {
            playerTwoScore += 1;
            ball.setX(245);
            ball.setY(145);
            xVal = 0;
            ballXVelocity = randomPosOrNegOne() * arctanFunc(xVal);
            ballYVelocity = (int)(Math.random() * 60 - 29); 
            
        } else if(ball.getX() >= 490) {
            playerOneScore += 1;
            ball.setX(245);
            ball.setY(145);
            xVal = 0;
            ballXVelocity = randomPosOrNegOne() * arctanFunc(xVal);
            ballYVelocity = (int)(Math.random() * 60 - 29); 
        }

        //Should do this with a function called within conditionals so it saves memory
        g2.setColor(Color.gray);
        g2.drawString(playerOneScore + "", 100, 250);
        g2.drawString(playerTwoScore + "", 400, 250);
    }

    private int arctanFunc(int x){
        return (int)(50 * (Math.atan(0.75*x - 10) + Math.PI / 2) + 98 - 2.983);
    }

    public void run(){
        double lastFrameTime = 0.0;
        while(true){
            double time = Time.getTime();
            update(time - lastFrameTime);
            lastFrameTime = time;

            //cap frames to ~30 fps;
             try {
                Thread.sleep(33);
            } catch (Exception e){
                System.out.println(e); 
            }
            
        }
    }

}
