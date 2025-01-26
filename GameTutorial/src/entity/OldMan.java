package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OldMan extends Entity{
	public int state = 2;
	
	public int spriteCounter = 0;
	public int switchCooldown = 0;
	
	
	public OldMan() {
		this.detectionArea = new Rectangle(-64,-64,192,192);
		//this.detectionArea.setBounds(detectionArea);
		this.name = "oldman";
		
		solidArea = new Rectangle();
		solidArea.x = 0;
		solidArea.y = 40;
		//this.battleScaling = 4;
		
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		solidArea.height = 64;
		solidArea.width = 64;
		loadSprites();
	}
	
	
public void draw(Graphics2D g2, GamePanel gamePanel) {
		
		//System.out.println(spriteCounter);
		
		if(spriteCounter>67) {
			if(state == 1) {
				image = state1;
				state=2;
			}else if(state == 2) {
				image = state2;
				state=1;
			}
			spriteCounter=0;
		}
		spriteCounter++;

		int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
		int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
		
		
		if(worldX + gamePanel.tileSize*2 > gamePanel.player.worldX - gamePanel.player.screenX  
		   && worldX - gamePanel.tileSize*2< gamePanel.player.worldX + gamePanel.player.screenX
		   && worldY + gamePanel.tileSize*2> gamePanel.player.worldY - gamePanel.player.screenY
		   && worldY - gamePanel.tileSize*2< gamePanel.player.worldY + gamePanel.player.screenY) {
			g2.drawImage(image, screenX, screenY, gamePanel.tileSize,gamePanel.tileSize,null);
		}
		
		
		
			
		
		}
	public void loadSprites() {
		
		try {
			state1 = ImageIO.read(getClass().getResourceAsStream("/npcs/oldMan1.png"));
			state2 = ImageIO.read(getClass().getResourceAsStream("/npcs/oldMan2.png"));
//			state1 = ImageIO.read(getClass().getResourceAsStream("/npcs/joeyySprite.png"));
//			state2 = state1;
			image = state1;
		}catch(IOException e) {
			e.printStackTrace();
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
