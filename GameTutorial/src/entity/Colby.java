package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Colby extends Entity{
	//public BufferedImage state1,state2;
	GamePanel gamePanel;
	public int state = 2;
	
	public int spriteCounter = 0;
	
	public Colby() {
		this.hp = 16;
		this.atk = 2;
		this.spd = 1000;
		this.def = 1;
		this.name = "colby";
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
			state1 = ImageIO.read(getClass().getResourceAsStream("/enemies/colbyWhite1.png"));
			state2 = ImageIO.read(getClass().getResourceAsStream("/enemies/colbyWhite2.png"));
			image = state1;
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void draw(Graphics2D g2, GamePanel gamePanel) {
		
		//System.out.println(spriteCounter);
		
		if(spriteCounter>27) {
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
		//behaviour();
		
		
		
		
		
		int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
		int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
		
		
		if(worldX + gamePanel.tileSize*2 > gamePanel.player.worldX - gamePanel.player.screenX  
		   && worldX - gamePanel.tileSize*2< gamePanel.player.worldX + gamePanel.player.screenX
		   && worldY + gamePanel.tileSize*2> gamePanel.player.worldY - gamePanel.player.screenY
		   && worldY - gamePanel.tileSize*2< gamePanel.player.worldY + gamePanel.player.screenY) {
			g2.drawImage(image, screenX, screenY, gamePanel.tileSize,gamePanel.tileSize,null);
		}
	}

	@Override
	public String[] move1() {
		String[] output = new String[4];
		Random random = new Random();
        int roll = random.nextInt(2) + 1; 
        output[0] = String.valueOf(roll *this.atk);
        output[1] = "Colby kicks you in the shin!";
        output[2] = "null";
        output[3] = "0";
        
        return output;
	}

	@Override
	public String[] move2() {
		String[] output = new String[4];
		Random random = new Random();
        int roll = random.nextInt(10);
        output[0] = String.valueOf(roll *this.atk);
        output[1] = "Colby roundhouse kicks you!";
        output[2] = "null";
        output[3] = "0";
        
        return output;
	}

	@Override
	public String[] move3() {
		String[] output = new String[4];
		output[0] = "null";
		this.atk+=2;
		if(this.def>=3) {
			this.def-=3;
		}else {
			this.def = 0;
			
		}
		output[1] = "Colby raises its ATK at the cost of DEF!";
		output[2] = "null";
        output[3] = "0";
		return output;
	}

	@Override
	public String[] move4() {
		// TODO Auto-generated method stub
		String[] output = new String[4];
		output[0] = "null";
		output[1] = "this move for colby wasnt programmed yet :)";
		output[2] = "null";
        output[3] = "0";
        return output;
	}
}
