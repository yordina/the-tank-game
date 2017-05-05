package GameCore;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.Observable;

public class Explosion extends GameObj{
	Image[] img;
	int count, i;
	private boolean isDone;

	public Explosion(Image[] img, int x, int y, int speed) {
		super(img, x, y, speed);
		this.img = img;
		count = 0;
		i = 0;
		isDone = false;
	}
	/**
	 * Used this method to draw the animation instead of the original!
	 * @param obs
	 * @param g
	 */
	public void draw(ImageObserver obs, Graphics2D g) {
		if (i < this.img.length) {
			// count = (count+1)%10;
			g.drawImage(this.img[i % this.img.length], x, y, obs);
			if (((count++) % 10) == 0) {
				i++;
			}
		} else {
			isDone = true;
		}
	}
	/**
	 * @return True if the animation is done, so it can be removed. Return False otherwise.
	 */
	public boolean isDone() {
		return isDone;
	}

	@Override
	public void update(Observable obj, Object arg) {

	}
	
}
