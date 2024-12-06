package object;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Player;
import main.GamePanel;

public class Key extends SuperObject{
	
	
	public Key() {
		
		
		
		
		name = "key";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/newKey.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	

}
