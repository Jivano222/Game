package object;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PressurePlate extends SuperObject{
	//add some sort of "set" number so when all members of the set == true the event for set1 is triggered
	//maybe an events class that can check if all conditions for event are met
	
	public BufferedImage pressedImage,unpressedImage;
	
	

	public PressurePlate() {
		
		name = "plate";
		this.pressed = false;
		try {
			//image = ImageIO.read(getClass().getResourceAsStream("/objects/plateUp.png"));
			pressedImage = ImageIO.read(getClass().getResourceAsStream("/objects/plateDown.png"));
			unpressedImage = ImageIO.read(getClass().getResourceAsStream("/objects/plateUp.png"));
			image = unpressedImage;
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void onPress() {
		if(this.pressed == false) {
			
			pressed = true;
			image = pressedImage;
			//add to set count
		}else if(pressed == true) {
			pressed = false;
			image = unpressedImage;
			//- from set count
		}
	}
	
}
