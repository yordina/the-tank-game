package Tank;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import javax.imageio.ImageIO;

import MyTank.*;
import PowerUp.CarePackageAbstract;
import PowerUp.HealthUp;
import PowerUp.PowerUp;
import SoundEffect.Sound;
import Wall.Wall;
import GameCore.Explosion;
import GameCore.GameEvents;
import Bullet.TankBullet;


@SuppressWarnings("serial")
public class TankWorld extends JApplet implements Runnable{

	private int w = 900, h = 600; // game window will be 800 x 600
	private int battleField_X_Size = 1536, battleField_Y_Size = 1536;
	private int p1WindowBoundX, p1WindowBoundY, p2WindowBoundX, p2WindowBoundY, count = 1, gameOverCounter = 50;
    Graphics2D g2, mapGraphics;
	public static Image player1, player2;
	private Image strongWall, weakWall, weakBullet, strongBullet, life, powerUp, win;
	private Image background, tankP1, tankP2;
	private Image[] healthBar = new Image[4], explosion = new Image[7], wallExplosion = new Image[6];
	public InputStream map ;
	public static Tank p1, p2;
	JPanel p = new JPanel();
	JPanel pLeft = new JPanel();
	boolean gameOver = false;
	String winner, loser;
	private static final TankWorld myGame = new TankWorld();
	public static GameEvents gameEvents;
	private Thread thread;


    public static Random generator = new Random(4567);


    private Dimension windowSize;


    private BufferedImage bimg, bimg2, player_1_window, player_2_window;
    
    public ArrayList<Wall> wall = new ArrayList<Wall>(), weakwall = new ArrayList<Wall>();
    public ArrayList<TankBullet> bullets = new ArrayList<TankBullet>();
    public ArrayList<Explosion> explosionAnimation = new ArrayList<Explosion>();
    public ArrayList<CarePackageAbstract> carePackage = new ArrayList<CarePackageAbstract>();
    
	@Override
	public void init(){
		this.setFocusable(true);
		//this.setLayout(null);
		Sound.player("background2.wav", true);
		try{
			
			background = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/Background.png"));
			tankP1 = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/Tank_blue_heavy_strip60.png"));
			tankP2 = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/Tank_red_heavy_strip60.png"));
			strongWall = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/Blue_wall1.png"));
			weakWall = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/Blue_wall2.png"));
			map = this.getClass().getClassLoader().getResourceAsStream("Resources/tankmap.txt");
			weakBullet = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/Shell_basic_strip60.png"));
			strongBullet = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/Shell_heavy_strip60.png"));
			life = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/life.png"));
			powerUp = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/heavy_weapon.png"));
			win = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/youWin.png"));

	        healthBar[3] = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/health.png"));
	        healthBar[2] = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/health1.png"));
	        healthBar[1] = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/health2.png"));
	        healthBar[0] = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/health3.png"));
	        
	        explosion[0] = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/explosion2_1.png"));
	        explosion[1] = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/explosion2_2.png"));
	        explosion[2] = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/explosion2_3.png"));
	        explosion[3] = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/explosion2_4.png"));
	        explosion[4] = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/explosion2_5.png"));
	        explosion[5] = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/explosion2_6.png"));
	        explosion[6] = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/explosion2_7.png"));
	        wallExplosion[0] = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/explosion1_1.png"));
	        wallExplosion[1] = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/explosion1_2.png"));
	        wallExplosion[2] = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/explosion1_3.png"));
	        wallExplosion[3] = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/explosion1_4.png"));
	        wallExplosion[4] = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/explosion1_5.png"));
	        wallExplosion[5] = ImageIO.read(this.getClass().getClassLoader().getResource("Resources/explosion1_6.png"));


		}catch(IOException e){
			System.err.println(e+"NO RESOURCE ARE FOUND!");
		}
		//p1 = new Tank(tankP1, 736, 64, 5, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W,KeyEvent.VK_S, KeyEvent.VK_SPACE);
		//p2 = new Tank(tankP2, 700, 1200, 5, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP,KeyEvent.VK_DOWN, KeyEvent.VK_ENTER);
        gameEvents = new GameEvents();

        //gameEvents.addObserver(p1);
        //gameEvents.addObserver(p2);

		KeyControl key = new KeyControl();
        addKeyListener(key);
        mapPrinter();
	}
	/**
	 * 
	 * @return ArrayList of the bullets.
	 */
	public ArrayList<TankBullet> getBullets(){
		return bullets;
	}
	/**
	 * 
	 * @return ArrayList of the explosion.
	 */
	public ArrayList<Explosion> getExplosion(){
		return explosionAnimation;
	}
	/**
	 * 
	 * @return ArrayList of the wall.
	 */
	public ArrayList<Wall> getWall(){
		return wall;
	}
	/**
	 * 
	 * @return The instance of the game, so it's easier to call method/get variable/get tank. The instance will be static.
	 */
	public static TankWorld getTankGame(){
		return myGame;
	}
	/**
	 * 
	 * @return The image of the weak bullet that deals 1 damage.
	 */
	public Image getWeakBulletImg(){
		return weakBullet;
	}
	/**
	 * 
	 * @return The image of the strong bullet that deals 2 damage.
	 */
	public Image getStrongBulletImg(){
		return strongBullet;
	}
	/**
	 * 
	 * @return The image array of the health bars.
	 */
	public Image[] getHealthBar(){
		return healthBar;
	}
	/**
	 * 
	 * @return The image array of the explosions.
	 */
	public Image[] getExplosionImg(){
		return explosion;
	}
	/**
	 * 
	 * @return The image array of the wall explosions.
	 */
	public Image[] getWallExplosionImg(){
		return wallExplosion;
	}
	
	@Override
    public void start() {
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }
	
	@Override
	public void run() {
		Thread me = Thread.currentThread();
		while (thread == me) {
			repaint();
			try {
				thread.sleep(35);
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	@Override
	public void paint(Graphics g) {

		bimg = (BufferedImage) createImage(battleField_X_Size, battleField_Y_Size);
		
		g2 = bimg.createGraphics();
		
		if (!gameOver) {

			drawDemo();
			g.drawImage(bimg2, 0, 0, this);
		} else { 			// Used to print the final image, so it won't draw the game anymore.
			g2.drawImage(win, windowSize.width/2-(win.getWidth(null)/2), 0, this);
			g2.setColor(Color.BLACK);
			g2.setFont(new Font("TimesRoman", Font.BOLD, 28));

			g2.drawString(winner, (windowSize.width/2)-150, windowSize.height/2);
			g2.drawString(loser, (windowSize.width/2)-150, windowSize.height/2+50);

			g.drawImage(bimg, 0, 0, this);

		}
	}

	public void drawDemo() {

		drawBackGroundWithTileImage();


		if (count % 600 == 0) {
			carePackage.add(new PowerUp(powerUp, 256, 736, 0));
			carePackage.add(new HealthUp(life, 1216, 736, 0));
		}

		if (!wall.isEmpty()) {
			for (int i = 0; i <= wall.size() - 1; i++) {
				wall.get(i).draw(this, g2);
			}
		}

		if (!carePackage.isEmpty()) {
			for (int i = 0; i <= carePackage.size() - 1; i++) {
				if (!carePackage.get(i).isShow()) {

					carePackage.remove(i--); 
				} else {
					carePackage.get(i).update();
					carePackage.get(i).draw(this, g2);
				}
			}
		}

		if (!bullets.isEmpty()) {
			for (int i = 0; i <= bullets.size() - 1; i++) {
				//TankBullet tempBullet = bullets.get(i);
				bullets.get(i).draw(this, g2);
				if (!bullets.get(i).isShow()) {
					bullets.remove(i--);
				}
			}
		}

		

		p1.draw(this, g2);
		p1.updateMove();
		p2.draw(this, g2);
		p2.updateMove();

		if (!explosionAnimation.isEmpty()) {
			for (int i = 0; i <= explosionAnimation.size() - 1; i++) {
				if (explosionAnimation.get(i).isDone()) {
					explosionAnimation.remove(i--);
				} else {
					explosionAnimation.get(i).draw(this, g2);
				}
			}
		}
		bimg2 = (BufferedImage) createImage(windowSize.width, windowSize.height);
		//System.out.print(windowSize.width);

		g2 = bimg2.createGraphics();

		//That calculate the movement of the camera then print according line to and image, then draw the image
		playerViewBoundChecker();
		player_1_window = bimg.getSubimage(p1WindowBoundX, p1WindowBoundY, windowSize.width / 2, windowSize.height);
		player_2_window = bimg.getSubimage(p2WindowBoundX, p2WindowBoundY, windowSize.width / 2, windowSize.height);
		
		g2.drawImage(player_1_window, 0, 0, this);

		if (p1.getLife() != 0) {
			for (int i = 0; i < p1.getLife(); i++) {
				g2.drawImage(life, i * 32, windowSize.height - 20, this);
			}
			if (p1.getHealth() != 0) {
				g2.drawImage(healthBar[p1.getHealth() - 1], 0,
						windowSize.height - 10, this);
			}
		}

		g2.drawImage(player_2_window, windowSize.width / 2, 0, this);
		if (p2.getLife() != 0) {
			for (int i = 0; i < p2.getLife(); i++) {
				g2.drawImage(life, windowSize.width - (3 * life.getWidth(null))
						+ ((i * life.getWidth(null))), windowSize.height - 20,
						this);
			}
			if (p2.getHealth() != 0) {
				g2.drawImage(healthBar[p2.getHealth() - 1],
						windowSize.width - 120, windowSize.height - 10, this);
			}
		}
		
		Image scaledMap = bimg.getScaledInstance(200, 200, Image.SCALE_FAST);

		g2.drawImage(scaledMap, windowSize.width / 2 - 100, windowSize.height / 2 - 100, 200, 200, this);
		//g2.drawImage(bimg, 0, 300, 200, 200, null);
		if(p1.isLose()){
			if((--gameOverCounter)==0){
				gameOver = true;
				//System.out.println("P1 LOST");
				winner= "Player 2 win!   Score:"+p2.getScore();
				loser = "Player 1 lose!   Score:"+p1.getScore();
			}
		}else if(p2.isLose()){
			if((--gameOverCounter)==0){
				gameOver = true;
				//System.out.println("P1 LOST");
				winner = "Player 1 win!   Score:"+p1.getScore();
				loser= "Player 2 lose!   Score:"+p2.getScore();
			}
		}
		count++;
	}

	public void drawBackGroundWithTileImage() {
		int TileWidth = background.getWidth(this);
		int TileHeight = background.getHeight(this);

		int NumberX = 6; // 6 times 256 = 1536, the actual map size is 1536 x 1536
		int NumberY = 6; 

		for (int i = 0; i < NumberY; i++) {
			for (int j = 0; j < NumberX; j++) {
				g2.drawImage(background, j * TileWidth, i * TileHeight,
						TileWidth, TileHeight, this);
			}
		}
	}

    public void mapPrinter(){
    	BufferedReader line = new BufferedReader(new InputStreamReader(map));
		int position = 0;
		String read;
		try{
    		while((read =line.readLine())!=null){
    			//System.out.println(position);
    			for(int i = 0; i < read.length(); i++){
    				char ch = read.charAt(i);
    				switch (ch){
						case'1':  wall.add(new Wall(strongWall, (position % 39) * 32, position/ 40 * 32, false));
						case'2':  wall.add(new Wall(weakWall, (position % 39) * 32, position/ 40 * 32, true));
						case'3': p1 = new Tank (tankP1, 600, 64, 5, KeyEvent.VK_A,KeyEvent.VK_D,KeyEvent.VK_W,KeyEvent.VK_S, KeyEvent.VK_SPACE);
							gameEvents.addObserver(p1);
						case'4': p2 = new Tank (tankP2, 500, 1100, 5, KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT,KeyEvent.VK_UP,KeyEvent.VK_DOWN, KeyEvent.VK_ENTER);
							gameEvents.addObserver(p2);
						default:break;
					}

    				 position++;
    			}
    		}
    	}catch(Exception e){
    		System.err.println("MapPrinter"+ e);
    	}
    }
    
    

    

    public void playerViewBoundChecker(){
    	if((p1WindowBoundX= (p1.getTankCenterX()-(windowSize.width/4))) < 0){
        	p1WindowBoundX = 0;
        }else if(p1WindowBoundX>=battleField_X_Size-(windowSize.width/2)){
        	p1WindowBoundX = battleField_X_Size-(windowSize.width/2);
        }

        if((p1WindowBoundY = (p1.getTankCenterY()-(windowSize.height/2))) < 0){
        	p1WindowBoundY = 0;
        }else if(p1WindowBoundY>=(battleField_Y_Size-(windowSize.height))){
        	p1WindowBoundY = battleField_Y_Size-(windowSize.height);
        }
        
     	if((p2WindowBoundX= (p2.getTankCenterX()-(windowSize.width/4))) < 0){
        	p2WindowBoundX = 0;
        }else if(p2WindowBoundX>=battleField_X_Size-(windowSize.width/2)){
        	p2WindowBoundX = battleField_X_Size-(windowSize.width/2);
        }

        if((p2WindowBoundY = (p2.getTankCenterY()-(windowSize.height/2))) < 0){
        	p2WindowBoundY = 0;
        }else if(p2WindowBoundY>=(battleField_Y_Size-(windowSize.height))){
        	p2WindowBoundY = battleField_Y_Size-(windowSize.height);
        }
    }

	public class KeyControl extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			gameEvents.setValue(e);
		}
		public void keyPressed(KeyEvent e) {
			gameEvents.setValue(e);
		}
	}
	
    public static void main(String argv[]) {
    	myGame.init();
        JFrame f = new JFrame("Tank shooter");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.addWindowListener(new WindowAdapter() {
        });
        f.getContentPane().add("Center", myGame);
        f.pack();
        f.setSize(new Dimension(myGame.w, myGame.h));
        f.setVisible(true);
        f.setResizable(false);
        
        myGame.windowSize = f.getContentPane().getSize();

    	myGame.start();
    }

}
