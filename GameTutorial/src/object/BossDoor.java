package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class BossDoor extends SuperObject{
	
	public BossDoor() {
		name = "bossDoor";
		collision = true;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/newBossDoor.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
