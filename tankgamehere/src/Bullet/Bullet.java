package Bullet;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import GameCore.GameObj;
import MyTank.Tank;

public class Bullet extends GameObj{
	int sizeX, sizeY;
	Rectangle bbox;
	boolean show;
	int sideSpeed = 0;
	Tank myTank;
	
	public Tank getTank(){
		return myTank;
	}
	public boolean isShow(){
		return show;
	}
	/**
	 * Bullet constructor.
	 * @param img
	 * @param x
	 * @param y
	 * @param speed
	 * @param sideSpeed
	 */
	Bullet(Image img, int x, int y, int speed, int sideSpeed){
		super(img, x, y, speed);
		//sizeX = img.getWidth(null);
		//sizeY = img.getHeight(null);
		show = true;
		this.sideSpeed = sideSpeed;
	}
	
	public void draw(ImageObserver obs, Graphics2D g) {
		if(show){
			g.drawImage(img, x, y, obs);
		}
		update();
    }
	
	public void update(){
		
	}

}
