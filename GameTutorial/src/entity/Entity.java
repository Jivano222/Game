package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

abstract public class Entity {
	public int worldX,worldY;
	public int speed;
	
	public BufferedImage state1,state2;
	
	public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public Rectangle solidArea;
	public boolean collisionOn = false;
	public boolean collisionOnX;
	public boolean collisionOnY;
	
	public boolean collision;
	
	public int solidAreaDefaultX,solidAreaDefaultY;
	
	public String name;
	
	public BufferedImage image;
	
	//Stats
	public int hp;
	public int atk;
	public int def;
	public int spd;
	
	public int hpMax;
	public int atkMax;
	public int defMax;
	public int spdMax;
	
	public String status = "clear";
	public int statusCounter = 0;
	
	public boolean bleeding = false;
		public int bleedCounter = 0;
	
	
	public boolean poisoned = false;
		public int poisonCounter = 0;
	
	public boolean sleeping = false;
	public boolean burning = false;
	public boolean blinded = false;
	
	public int battleScaling = 7;
	
	//TRYING TO HANDLE DIAGONALS
	public String directionX;
	public String directionY;
	
	//for npcs
	
	public Rectangle detectionArea = new Rectangle(0,0,64,64);
	
	public void draw(Graphics2D g2,GamePanel gamePanel) {
		
	}
	
	abstract public String[] move1();
	
	abstract public String[] move2();
	
	abstract public String[] move3();
	
	abstract public String[] move4();
}
