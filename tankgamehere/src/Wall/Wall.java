package Wall;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Observable;

import SoundEffect.Sound;
import Tank.TankWorld;
import Bullet.Bullet;
import Bullet.TankBullet;
import GameCore.*;
public class Wall extends GameObj{
	boolean breakableWall;
	private int coolDown, width, height;
	private Rectangle wallRect;
	public Wall(Image img, int x, int y, boolean weakWall ) {
		super(img, x, y, 0);
		coolDown = 0;
		breakableWall = weakWall;
		this.width = img.getWidth(null);
		this.height = img.getHeight(null);
		boom=false;
		wallRect = new Rectangle(x,y,width,height);
	}

	public boolean isDestroyAble(){
		return breakableWall;
	}
	public boolean isRespawning(){
		return coolDown == 0;
	}
	public void breakWall(){
		coolDown = 700;
	}
	public Rectangle getWallRectangle(){
		return wallRect;
	}
	/**
	 * This method is for drawing the wall!
	 * @param obs
	 * @param g
	 */
	@Override
	public void draw(ImageObserver obs, Graphics2D g) {
		if(coolDown == 0) {
			g.drawImage(img, x, y, obs);
			update();
		}else{
			coolDown--;
		}
    }
	/**
	 * This method is called from the draw method.
	 */
	public void update() {		
		if (TankWorld.p1.collision(this.x, this.y, width, height)){
			if(TankWorld.p1.x>(x)){ // wont work if i did tankgame.p1.x>(x+this.width), since it will think that my tank is < the side, and it will not bounce back.
				TankWorld.p1.x+=3;
			}
			else if(TankWorld.p1.x<(this.x)){
				TankWorld.p1.x-=3;
			}
			if(TankWorld.p1.y>(this.y)){
				TankWorld.p1.y+=3;
			}
			else if(TankWorld.p1.y<this.y){
				TankWorld.p1.y-=3;
			}
		}
		if (TankWorld.p2.collision(this.x, this.y, width, height)){
			if(TankWorld.p2.x>(x)){
				TankWorld.p2.x+=3;
			}
			else if(TankWorld.p2.x<(this.x)){
				TankWorld.p2.x-=3;
			}
			if(TankWorld.p2.y>(this.y)){
				TankWorld.p2.y+=3;
			}
			else if(TankWorld.p2.y<this.y){
				TankWorld.p2.y-=3;
			}
		}
	}
}
