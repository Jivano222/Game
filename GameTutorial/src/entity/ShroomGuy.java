package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

/**
 * Notes: add moveset
 * 			something is wrong with him waking up, recheck the calculation and how stuff is calculated relative to the player
 */
public class ShroomGuy extends Entity{
	
	public int state = 2;
	public int spriteCounter = 0;
	
	public boolean activated = false;
	public boolean ready = false;
	
	public BufferedImage intro1,intro2,intro3,intro4;
	
	public ShroomGuy() {
		this.hp = 16;
		this.atk = 2;
		this.spd = 20;
		this.def = 1;
		this.name = "shroom";
		this.direction = "down";
		this.speed = 2;
		solidArea = new Rectangle();
		solidArea.x = 0;
		solidArea.y = 0;
		
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		solidArea.height = 64;
		solidArea.width = 64;
		
		this.collision = true;
		
		loadSprites();
	}

	
	
public void loadSprites() {
		
		try {
			state1 = ImageIO.read(getClass().getResourceAsStream("/enemies/shroomGuy1.png"));
			state2 = ImageIO.read(getClass().getResourceAsStream("/enemies/shroomGuy2.png"));
			
			intro1 = ImageIO.read(getClass().getResourceAsStream("/enemies/shroomGuyIntro1.png"));
			intro2 = ImageIO.read(getClass().getResourceAsStream("/enemies/shroomGuyIntro2.png"));
			intro3 = ImageIO.read(getClass().getResourceAsStream("/enemies/shroomGuyIntro3.png"));
			intro4 = ImageIO.read(getClass().getResourceAsStream("/enemies/shroomGuyIntro4.png"));
			image = intro1;
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}

	public void draw(Graphics2D g2, GamePanel gamePanel) {
		
	
		
	
		
		
		
		int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
		int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
		
		//System.out.println(spriteCounter);
		if(activated==true) {
			spriteCounter++;
			if(ready == true) {
				if(spriteCounter>20) {
					spriteCounter = 0;
					if(state == 1) {
						state = 2;
						image = state2;
					}else {
						state = 1;
						image = state1;
					}
				}
			}else {
				if(spriteCounter>7 && spriteCounter < 14) {
					image = intro2;
				}else if(spriteCounter>14 && spriteCounter < 21) {
					image = intro3;
				}else if(spriteCounter>21 && spriteCounter < 28) {
					image = intro4;
					ready = true;
					spriteCounter = 0;
				}
			}
			
		}
		if(Math.abs(gamePanel.player.screenX) - Math.abs(screenX) <=175 && Math.abs(gamePanel.player.screenY) - Math.abs(screenY) <=175) {
			activated = true;
		}
		
		if(worldX + gamePanel.tileSize*2 > gamePanel.player.worldX - gamePanel.player.screenX  
		   && worldX - gamePanel.tileSize*2< gamePanel.player.worldX + gamePanel.player.screenX
		   && worldY + gamePanel.tileSize*2> gamePanel.player.worldY - gamePanel.player.screenY
		   && worldY - gamePanel.tileSize*2< gamePanel.player.worldY + gamePanel.player.screenY) {
			g2.drawImage(image, screenX, screenY, gamePanel.tileSize,gamePanel.tileSize,null);
		}
	}




	@Override
	public String[] move1() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] move2() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] move3() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] move4() {
		// TODO Auto-generated method stub
		return null;
	}

}
