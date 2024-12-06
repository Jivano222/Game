package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class BossHammer extends SuperObject{
	public BossHammer() {
		name = "bosshammer";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/bossHammer.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
