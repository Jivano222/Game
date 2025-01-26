package main;

import entity.*;
import object.*;

public class AssetSetter {
	GamePanel gamePanel;
	
	public AssetSetter(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void setObject() {
		gamePanel.objects[0] = new Key();
		gamePanel.objects[0].worldX = 8 * gamePanel.tileSize;
		gamePanel.objects[0].worldY = 7 * gamePanel.tileSize;
		
		gamePanel.objects[1] = new PressurePlate();
		gamePanel.objects[1].worldX = 4 * gamePanel.tileSize;
		gamePanel.objects[1].worldY = 5 * gamePanel.tileSize;
		
		gamePanel.objects[2] = new PressurePlate();
		gamePanel.objects[2].worldX = 29 * gamePanel.tileSize;
		gamePanel.objects[2].worldY = 15 * gamePanel.tileSize;
		
		gamePanel.objects[3] = new PressurePlate();
		gamePanel.objects[3].worldX = 17 * gamePanel.tileSize;
		gamePanel.objects[3].worldY = 30 * gamePanel.tileSize;
		
		gamePanel.objects[4] = new PressurePlate();
		gamePanel.objects[4].worldX = 21 * gamePanel.tileSize;
		gamePanel.objects[4].worldY = 22 * gamePanel.tileSize;
		
		gamePanel.objects[1].eventSet = 1; 
		gamePanel.objects[2].eventSet = 1;
		gamePanel.objects[3].eventSet = 1;
		gamePanel.objects[4].eventSet = 1;

		
		int closedDoorCounter = 50;
		for(int i = 0;i<gamePanel.maxWorldRow;i++) {
			for(int j = 0;j<gamePanel.maxWorldCol;j++) {
				if(gamePanel.tileManager.mapTileNum[i][j]==2) {
					gamePanel.objects[closedDoorCounter] = new KeyDoor();
					gamePanel.objects[closedDoorCounter].worldX = j * gamePanel.tileSize;
					gamePanel.objects[closedDoorCounter].worldY = i * gamePanel.tileSize;
					gamePanel.tileManager.mapTileNum[i][j] = 1;
					closedDoorCounter++;
				}
			}
		}
		
		
//		gamePanel.objects[1] = new KeyDoor();
//		gamePanel.objects[1].worldX = 9 * gamePanel.tileSize;
//		gamePanel.objects[1].worldY = 10 * gamePanel.tileSize;
//		gamePanel.tileManager.mapTileNum[10][9] = 1;
		
		
		
//		
//		gamePanel.objects[2] = new Sword();
//		gamePanel.objects[2].worldX = 2 * gamePanel.tileSize; // col
//		gamePanel.objects[2].worldY = 2 * gamePanel.tileSize; // row
//		
//		gamePanel.objects[3] = new BossKey();
//		gamePanel.objects[3].worldX = 20 * gamePanel.tileSize; // col
//		gamePanel.objects[3].worldY = 20 * gamePanel.tileSize; // row
//		
//		gamePanel.objects[4] = new BossDoor();
//		gamePanel.objects[4].worldX = 17 * gamePanel.tileSize;
//		gamePanel.objects[4].worldY = 4 * gamePanel.tileSize;
//		gamePanel.tileManager.mapTileNum[4][17] = 1;
//		
//		gamePanel.objects[5] = new KeyDoor();
//		gamePanel.objects[5].worldX = 14 * gamePanel.tileSize;
//		gamePanel.objects[5].worldY = 18 * gamePanel.tileSize;
//		gamePanel.tileManager.mapTileNum[18][14] = 1;
		
//		gamePanel.objects[6] = new BossHammer();
//		gamePanel.objects[6].worldX = 18 * gamePanel.tileSize; // col
//		gamePanel.objects[6].worldY = 17 * gamePanel.tileSize; // row
//		
	}
	
	public void setEnemy() {
		//ISLAND 1 - START
		gamePanel.enemies[0] = new Slime();
		gamePanel.enemies[0].worldX = 7 * gamePanel.tileSize;
		gamePanel.enemies[0].worldY = 17 * gamePanel.tileSize;
		
		gamePanel.enemies[1] = new Slime();
		gamePanel.enemies[1].worldX = 18 * gamePanel.tileSize;
		gamePanel.enemies[1].worldY = 15 * gamePanel.tileSize;
		
		gamePanel.enemies[2] = new Slime();
		gamePanel.enemies[2].worldX = 25 * gamePanel.tileSize;
		gamePanel.enemies[2].worldY = 9 * gamePanel.tileSize;
		
		//4 6
		gamePanel.enemies[3] = new ShroomGuy();
		gamePanel.enemies[3].worldX = 5 * gamePanel.tileSize;
		gamePanel.enemies[3].worldY = 5 * gamePanel.tileSize;
		
		
		
		//ISLAND 1 - END
		
//		
//		gamePanel.enemies[1] = new Colby();
//		gamePanel.enemies[1].worldX = 14 * gamePanel.tileSize;
//		gamePanel.enemies[1].worldY = 5 * gamePanel.tileSize;
		
//		gamePanel.enemies[2] = new BigBoss();
//		gamePanel.enemies[2].worldX = 18 * gamePanel.tileSize;
//		gamePanel.enemies[2].worldY = 6 * gamePanel.tileSize;
//		
//		gamePanel.enemies[3] = new KeyMan();
//		gamePanel.enemies[3].worldX = 7 * gamePanel.tileSize;
//		gamePanel.enemies[3].worldY = 20 * gamePanel.tileSize;
//		
//		gamePanel.enemies[4] = new Slime();
//		gamePanel.enemies[4].worldX = 2 * gamePanel.tileSize;
//		gamePanel.enemies[4].worldY = 3 * gamePanel.tileSize;
	}
	
	//could add a decorations things for things like lampposts or something
	
	public void setNPC() {
		gamePanel.npcs[0] = new OldMan();
		gamePanel.npcs[0].worldX = 18*gamePanel.tileSize;
		gamePanel.npcs[0].worldY = 9*gamePanel.tileSize+8;
	}
}
