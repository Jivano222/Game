package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class SuperObject {
	
	/**
	 * Stores the sprite, name, if the object has collision, and its position in the world
	 */
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldX,worldY;
	
	/**
	 * The hitbox for each object
	 */
	public Rectangle solidArea = new Rectangle(0,0,64,64);
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	

	/**
	 * Counter to cause delay between moving an object up or down for its animation
	 */
	public int spriteCounter = 0;
	public int spriteCounter2 = 0;
	
	/**
	 * To track the state for object animations (in this case up or down)
	 */
	public int spriteNum = 1;
	public int spriteNum2 = 1;
	
	
	
	/**
	 * Draws all items that have been created
	 * Draws relative to player since the camera follows it
	 * @param g2 Graphics2D to draw components
	 * @param gamePanel screen where game takes place
	 */
	public void draw(Graphics2D g2, GamePanel gamePanel) {
		
		
		
		int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
		int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
		if(this.name=="key" || this.name=="bossKey") {
			spriteCounter++;
			this.keyAnimation();
		}
		
		if(this.name=="dagger") {
			spriteCounter2++;
			this.daggerAnimation();
		}
		if(this.name=="bosshammer") {
			spriteCounter2++;
			this.daggerAnimation();
		}
		
		if(worldX + gamePanel.tileSize*2 > gamePanel.player.worldX - gamePanel.player.screenX  
		   && worldX - gamePanel.tileSize*2< gamePanel.player.worldX + gamePanel.player.screenX
		   && worldY + gamePanel.tileSize*2> gamePanel.player.worldY - gamePanel.player.screenY
		   && worldY - gamePanel.tileSize*2< gamePanel.player.worldY + gamePanel.player.screenY) {
			g2.drawImage(image, screenX, screenY, gamePanel.tileSize,gamePanel.tileSize,null);
		}
		
	}
	
	/**
	 * Causes dagger(s) on the map to move up and down periodically
	 */
	public void keyAnimation() {
		if(spriteCounter>20) {
			if(spriteNum==1) {
				worldY -= 10;
				spriteNum=2;
			}else if(spriteNum==2) {
				worldY+=10;
				spriteNum=1;
			}
			spriteCounter=0;
		}
	}
	
	/**
	 * Causes dagger(s) on the map to move up and down periodically
	 * Uses separate counters from keyAnimation to have longer delay between movements
	 */
	public void daggerAnimation() {
		if(spriteCounter2>50) {
			if(spriteNum2==1) {
				worldY -= 10;
				spriteNum2=2;
			}else if(spriteNum2==2) {
				worldY+=10;
				spriteNum2=1;
			}
			spriteCounter2=0;
		}
	}

}
