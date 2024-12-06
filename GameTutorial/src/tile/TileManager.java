package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {

	GamePanel gamePanel;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public TileManager(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		tile = new Tile[10];//num of dif types of tiles
		
		mapTileNum = new int[gamePanel.maxWorldRow][gamePanel.maxWorldCol];
		
		
		loadMap("/maps/map3.txt");
		getTileImage();
	}
	
	public void getTileImage() {
		
		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/newGrass.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/newBrick.png"));
			tile[1].collision = true;
			
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/newDoorWay.png"));
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/newWater.png"));
			tile[3].collision = true;
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/woodFloorTest1.png"));
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/treeBottom.png"));
			tile[5].collision = true;
			
			tile[6] = new Tile();
			tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/treeTop.png"));
			tile[6].collision = true;
			
			tile[7] = new Tile();
			tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/bridgeVertical.png"));
			
			tile[8] = new Tile();
			tile[8].image = ImageIO.read(getClass().getResourceAsStream("/tiles/bridgeHorizontal.png"));
			

			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String map) {
		try {
			InputStream is = getClass().getResourceAsStream(map);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
//			for(int i = 0;i<gamePanel.maxWorldRow;i++) {
//				String line = br.readLine();
//				String numbers[] = line.split(" ");
//				for(int j = 0;j<gamePanel.maxWorldCol;j++) {
//					int num = Integer.parseInt(numbers[j]);
//					mapTileNum[i][j] = num;
//				}
//				
//			}
			
			int row = 0;
			
			int col = 0;
			
			while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
				String line = br.readLine();
				while(col<gamePanel.maxWorldCol) {
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[row][col] = num;
					//System.out.println(mapTileNum[col][row]);
					col++;
				}
				if(col == gamePanel.maxWorldCol) {
					col = 0;
					row++;
					//System.out.println("\n");
				}
			}
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int worldRow = 0;
		int worldCol = 0;
		
		
		
		//g2.drawImage(tile[0].image, 0, 0, gamePanel.tileSize,gamePanel.tileSize,null);
		
		while(worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {
			int tileType = mapTileNum[worldRow][worldCol];
			
			int worldX = worldCol * gamePanel.tileSize;
			int worldY = worldRow * gamePanel.tileSize;
			int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
			int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
			
			if(worldX + gamePanel.tileSize*2 > gamePanel.player.worldX - gamePanel.player.screenX  
			   && worldX - gamePanel.tileSize*2< gamePanel.player.worldX + gamePanel.player.screenX
			   && worldY + gamePanel.tileSize*2> gamePanel.player.worldY - gamePanel.player.screenY
			   && worldY - gamePanel.tileSize*2< gamePanel.player.worldY + gamePanel.player.screenY) {
				g2.drawImage(tile[tileType].image, screenX, screenY, gamePanel.tileSize,gamePanel.tileSize,null);
			}
				
			
			worldCol++;
			
			if(worldCol==gamePanel.maxWorldCol) {
				worldCol=0;
				worldRow++;
				
			}
			
		}
	}
	
}
