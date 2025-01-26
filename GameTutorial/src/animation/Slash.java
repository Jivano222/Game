package animation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;




public class Slash {
	public  BufferedImage slash1;
	public  BufferedImage slash2;
	public  BufferedImage slash3;
	public  BufferedImage slash4;
	public  BufferedImage slash5;
	public  BufferedImage slash6;
	public int spriteCounter;
	public  BufferedImage currentSprite;
	public boolean active = false;
	public Slash() {
		spriteCounter = 0;
		loadSprites();
	}
	public void loadSprites() {
	
	try {
		slash1 = ImageIO.read(getClass().getResourceAsStream("/animations/slash1.png"));
		slash2 = ImageIO.read(getClass().getResourceAsStream("/animations/slash2.png"));
		slash3 = ImageIO.read(getClass().getResourceAsStream("/animations/slash3.png"));
		slash4 = ImageIO.read(getClass().getResourceAsStream("/animations/slash4.png"));
		slash5 = ImageIO.read(getClass().getResourceAsStream("/animations/slash5.png"));
		slash6 = ImageIO.read(getClass().getResourceAsStream("/animations/slash6.png"));
		currentSprite = null;
	}catch(IOException e) {
		e.printStackTrace();
	}
	
}
	
public void animate() {
		//if(active==true) {
			//spriteCounter++;
			if(spriteCounter<7) {
				currentSprite = slash1;
			}else if(spriteCounter>7 && spriteCounter < 14) {
				currentSprite = slash2;
			}else if(spriteCounter>14 && spriteCounter < 21) {
				currentSprite = slash3;
			}else if(spriteCounter>21 && spriteCounter < 28) {
				currentSprite = slash4;
			}else if(spriteCounter>28 && spriteCounter < 35) {
				currentSprite = slash5;
			}else if(spriteCounter>35 && spriteCounter < 42) {
				currentSprite = slash6;
			}else if(spriteCounter>45) {//placeholder in if statement
				currentSprite = null;
				active = false;
				spriteCounter = 0;
			}
		//}
		
	
		
		
		
		
		
		
		
		
	}
}


