package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class BossKey extends SuperObject{
	
	public BossKey() {
		name = "bossKey";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/bossKeyWhite.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
