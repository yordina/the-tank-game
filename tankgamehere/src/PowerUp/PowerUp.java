package PowerUp;

import java.awt.Image;

import Tank.TankWorld;
import Tank.TankWorld;

public class PowerUp extends CarePackageAbstract{

	public PowerUp(Image img, int x, int y, int speed) {
		super(img, x, y, speed);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Checking if it collide with the player plane.
	 */
	public void update() {
		y+=speed;
		if(TankWorld.p1.collision(x, y, width, height)){
			TankWorld.p1.oneUp();
			TankWorld.p1.setScore(100);
			System.out.println("one up ");
			show = false;
		}
		else if(TankWorld.p2.collision(x, y, width, height)){
			TankWorld.p2.oneUp();
			TankWorld.p2.setScore(100);
			show = false;
		}
	}
}
