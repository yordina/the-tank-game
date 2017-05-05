package PowerUp;

import java.awt.Image;

import Tank.TankWorld;
import Tank.TankWorld;

public class HealthUp extends CarePackageAbstract{

	public HealthUp(Image img, int x, int y, int speed) {
		super(img, x, y, speed);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Checking if it collide with the player plane.
	 */
	public void update() {
		y+=speed;
		if(TankWorld.p1.collision(x, y, width, height)){
			TankWorld.p1.healthUp();;
			System.out.println("health up ");
			show = false;
		}
		else if(TankWorld.p2.collision(x, y, width, height)){
			TankWorld.p2.healthUp();
			show = false;
		}

	}

}
