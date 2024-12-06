package object;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

public class KeyDoor extends SuperObject{
	
	public KeyDoor() {
		//this.solidArea = new Rectangle(-32, -32, 128, 128);
		name = "keyDoor";
		collision = true;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/newKeyDoor.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
