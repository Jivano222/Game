package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;

public class BigBoss extends Entity{

	public int state = 2;
	
	public int spriteCounter = 0;
	
	
	
	public BigBoss() {
		this.hp = 100;
		this.atk = 7;
		this.spd = 15;
		this.def = 8;
		this.name = "zibzog";
		this.direction = "down";
		this.speed = 30;
		this.battleScaling = 4 ;
		solidArea = new Rectangle();
		solidArea.x = 0;
		solidArea.y = 0;
		//this.battleScaling = 4;
		
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		solidArea.height = 128;
		solidArea.width = 128;
		
		this.collision = true;
		
		loadSprites();
		
	}
	
	
	public void loadSprites() {
		
		try {
			state1 = ImageIO.read(getClass().getResourceAsStream("/enemies/newBoss1.png"));
			state2 = ImageIO.read(getClass().getResourceAsStream("/enemies/newBoss2.png"));
			image = state1;
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void draw(Graphics2D g2, GamePanel gamePanel) {
		
		//System.out.println(spriteCounter);
		
		if(spriteCounter>100) {
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
			g2.drawImage(image, screenX, screenY, gamePanel.tileSize*2,gamePanel.tileSize*2,null);
		}
	}
	
	public String[] move1() {
		String[] output = new String[4];
		Random random = new Random();
        int roll = random.nextInt(3); 
        output[0] = String.valueOf(roll *this.atk);
        output[1] = "The slime slimed you!";
        output[2] = "null";
        output[3] = "0";
        
        return output;
        
	}
	
	public String[] move2() {
		String[] output = new String[4];
		output[0] = "null";
		output[1] = "The slime increased its ATK!";
		output[2] = "null";
        output[3] = "0";
		this.atk+=1;
		return  output;
        
	}


	@Override
	public String[] move3() {
		String[] output = new String[4];
		output[0] = "null";
		output[1] = "The slime increased its ATK!";
		output[2] = "null";
        output[3] = "0";
		return output;
	}


	@Override
	public String[] move4() {
		String[] output = new String[4];
		Random random = new Random();
        int roll = random.nextInt(3); 
        output[0] = String.valueOf(roll *this.atk);
        output[1] = "The slime slimed you!";
        output[2] = "null";
        output[3] = "0";
        return output;
        
	}
	
	
	
}
