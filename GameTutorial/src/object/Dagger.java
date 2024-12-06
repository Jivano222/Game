package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Dagger extends SuperObject{
	
	public Dagger() {
		name = "dagger";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/dagger.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
