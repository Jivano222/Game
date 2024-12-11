package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;

public class KeyMan extends Entity{

	public int state = 2;
	
	public int spriteCounter = 0;
	public int switchCooldown = 0;
	
	
	public KeyMan() {
		this.hp = 40;
		this.atk = 5;
		this.spd = 150;
		this.def = 7;
		this.name = "keyman";
		this.direction = "down";
		this.speed = 7;
		solidArea = new Rectangle();
		solidArea.x = 0;
		solidArea.y = 0;
		//this.battleScaling = 4;
		
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		solidArea.height = 65;
		solidArea.width = 65;
		
		this.collision = true;
		
		loadSprites();
		
	}
	
	
	public void loadSprites() {
		
		try {
			state1 = ImageIO.read(getClass().getResourceAsStream("/enemies/whiteKeyMan1.png"));
			state2 = ImageIO.read(getClass().getResourceAsStream("/enemies/whiteKeyMan2.png"));
			image = state1;
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void draw(Graphics2D g2, GamePanel gamePanel) {
		
		//System.out.println(spriteCounter);
		
		if(spriteCounter>20) {
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
		behaviour();
		collisionOn=false;
		gamePanel.collisionChecker.checkTile(this);
		
		
		
		if(collisionOn==false) {
			
			if(direction == "left") {
				worldX -= speed;
			}else if(direction == "right") {
				worldX += speed;
			}else if(direction == "up") {
				worldY -= speed;
			}else if(direction == "down") {
				worldY += speed;
			}
		}
		
		
		
		int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
		int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
		
		
		if(worldX + gamePanel.tileSize*2 > gamePanel.player.worldX - gamePanel.player.screenX  
		   && worldX - gamePanel.tileSize*2< gamePanel.player.worldX + gamePanel.player.screenX
		   && worldY + gamePanel.tileSize*2> gamePanel.player.worldY - gamePanel.player.screenY
		   && worldY - gamePanel.tileSize*2< gamePanel.player.worldY + gamePanel.player.screenY) {
			g2.drawImage(image, screenX, screenY, gamePanel.tileSize,gamePanel.tileSize,null);
		}
	}
	
	public String[] move1() {
		String[] output = new String[4];
		Random random = new Random();
        int roll = random.nextInt(4); 
        output[0] = String.valueOf(roll *this.atk);
        output[1] = "The keyman stabbed you!";
        output[2] = "null";
        output[3] = "0";
        
        return output;
        
	}
	
	public String[] move2() {
		String[] output = new String[4];
		output[0] = "7";
		output[1] = "Keyman stabbed you, making you bleed!";
		output[2] = "bleed";
        output[3] = "5";
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
	public void behaviour() {
		if(switchCooldown<=30 && collisionOn==false) {
			switchCooldown++;
		}else if(switchCooldown>=30 || collisionOn==true) {
			Random random = new Random();
			int direct = random.nextInt(4)+1;
			
			if(direct == 1) {
				this.direction = "up";
			}else if(direct==2) {
				this.direction = "down";
			}else if(direct==3) {
				this.direction = "left";
			}else {
				this.direction = "right";
			}
			switchCooldown = 0;
		}
		
		
		
		
	}
}
