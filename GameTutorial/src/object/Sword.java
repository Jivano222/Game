package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Sword extends SuperObject{
	
	public Sword() {
		name = "sword";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/sword.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
