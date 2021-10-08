package com.pong;

import java.awt.Graphics2D;
import java.awt.Color;

public class Rect {
    private int x, 
        y,
        width,
        height;
    private Color color;

    public Rect(int x, int y, int width, int height, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }
    
    public void draw(Graphics2D g2){
        g2.setColor(color);
        g2.fillRect(x, y, width, height);
    }


    public int getX(){ return x;}
    public int getY(){ return y;}
    public int getWidth(){ return width;}
    public int getHeight(){ return height;}

    public void setX(int x){ this.x = x;}
    public void setY(int y){ this.y = y;}
    public void setWidth(int width){ this.width = width;}
    public void setHeight(int height) { this.height = height;}

}
