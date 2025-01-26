package main;

import entity.Entity;

public class CollisionChecker {
	
	GamePanel gamePanel;
	
	KeyHandler keyHandler;//added this to maybe check when 2 keys are being pressed at once
	

	public CollisionChecker(GamePanel gamePanel,KeyHandler keyHandler) {
		this.gamePanel = gamePanel;
		this.keyHandler = keyHandler;
	}
	
	public int checkObject(Entity entity, boolean player) {
		
		int index = 999;
		
		for(int i = 0;i<gamePanel.objects.length;i++) {
			if(gamePanel.objects[i]!=null) {
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				
				gamePanel.objects[i].solidArea.x = gamePanel.objects[i].worldX + gamePanel.objects[i].solidArea.x;
				gamePanel.objects[i].solidArea.y = gamePanel.objects[i].worldY + gamePanel.objects[i].solidArea.y;
				
				switch(entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed-4;
					if(entity.solidArea.intersects(gamePanel.objects[i].solidArea)) {
						if(gamePanel.objects[i].collision == true) {
							//entity.collisionOn = true;
							entity.collisionOnY = true;
						}
						
						if(player == true) {
							index = i;
						}
					}
					break;
					
				case "down":
					entity.solidArea.y += entity.speed+4;
					if(entity.solidArea.intersects(gamePanel.objects[i].solidArea)) {
						if(gamePanel.objects[i].collision == true) {
							//entity.collisionOn = true;
							entity.collisionOnY = true;
						}
						
						if(player == true) {
							index = i;
						}
					}
					break;
					
				case "left":
					entity.solidArea.x -= entity.speed-4;
					if(entity.solidArea.intersects(gamePanel.objects[i].solidArea)) {
						if(gamePanel.objects[i].collision == true) {
							entity.collisionOn = true;
						}
						
						if(player == true) {
							index = i;
						}
					}
					break;
					
				case "right":
					entity.solidArea.x += entity.speed+4;
					if(entity.solidArea.intersects(gamePanel.objects[i].solidArea)) {
						if(gamePanel.objects[i].collision == true) {
							entity.collisionOn = true;
						}
						
						if(player == true) {
							index = i;
						}
					}
					break;
				
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				
				gamePanel.objects[i].solidArea.x = gamePanel.objects[i].solidAreaDefaultX;
				gamePanel.objects[i].solidArea.y = gamePanel.objects[i].solidAreaDefaultY;
			}
		}
		
		return index;
	}
	
	public int checkNPC(Entity entity, boolean player) {
		
		int index = 999;
		
		for(int i = 0;i<gamePanel.npcs.length;i++) {
			if(gamePanel.npcs[i]!=null) {
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				
				gamePanel.npcs[i].detectionArea.x = gamePanel.npcs[i].worldX + gamePanel.npcs[i].solidArea.x;
				gamePanel.npcs[i].detectionArea.y = gamePanel.npcs[i].worldY + gamePanel.npcs[i].solidArea.y;
				if(entity.solidArea.intersects(gamePanel.npcs[i].detectionArea)) {
					index = i;
				}

				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				
				gamePanel.npcs[i].detectionArea.x = gamePanel.npcs[i].solidAreaDefaultX;
				gamePanel.npcs[i].detectionArea.y = gamePanel.npcs[i].solidAreaDefaultY;
				
			}
		}
				
			
		return index;
	}
	
	
	public int checkEnemy(Entity entity, boolean player) {
		
		int index = 999;
		
		for(int i = 0;i<gamePanel.enemies.length;i++) {
			if(gamePanel.enemies[i]!=null) {
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				
				gamePanel.enemies[i].solidArea.x = gamePanel.enemies[i].worldX + gamePanel.enemies[i].solidArea.x;
				gamePanel.enemies[i].solidArea.y = gamePanel.enemies[i].worldY + gamePanel.enemies[i].solidArea.y;
				
				
				switch(entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed;
					if(entity.solidArea.intersects(gamePanel.enemies[i].solidArea)) {
						if(gamePanel.enemies[i].collision == true) {
							entity.collisionOn = true;
							entity.collisionOnY = true;
						}
						
						if(player == true) {
							index = i;
						}
					}
					break;
					
				case "down":
					entity.solidArea.y += entity.speed;
					if(entity.solidArea.intersects(gamePanel.enemies[i].solidArea)) {
						if(gamePanel.enemies[i].collision == true) {
							entity.collisionOn = true;
							entity.collisionOnY = true;
						}
						
						if(player == true) {
							index = i;
						}
					}
					break;
					
				case "left":
					entity.solidArea.x -= entity.speed;
					if(entity.solidArea.intersects(gamePanel.enemies[i].solidArea)) {
						if(gamePanel.enemies[i].collision == true) {
							entity.collisionOn = true;
						}
						
						if(player == true) {
							index = i;
						}
					}
					break;
					
				case "right":
					entity.solidArea.x += entity.speed;
					if(entity.solidArea.intersects(gamePanel.enemies[i].solidArea)) {
						if(gamePanel.enemies[i].collision == true) {
							entity.collisionOn = true;
						}
						
						if(player == true) {
							index = i;
						}
					}
					break;
				}
				
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				
				gamePanel.enemies[i].solidArea.x = gamePanel.enemies[i].solidAreaDefaultX;
				gamePanel.enemies[i].solidArea.y = gamePanel.enemies[i].solidAreaDefaultY;
			}
		}
		
		return index;
	}
	
	public void checkTile(Entity entity) {
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height+3;
		
		int entityLeftCol = entityLeftWorldX/gamePanel.tileSize;
		int entityRightCol = entityRightWorldX/gamePanel.tileSize;
		int entityTopRow = entityTopWorldY/gamePanel.tileSize;
		int entityBottomRow = entityBottomWorldY/gamePanel.tileSize;
		
		int tileNum1,tileNum2;
		
		if(entity.direction == "up") {
			entityTopRow = (entityTopWorldY - entity.speed)/gamePanel.tileSize;
			tileNum1 = gamePanel.tileManager.mapTileNum[entityTopRow][entityLeftCol];
			tileNum2 = gamePanel.tileManager.mapTileNum[entityTopRow][entityRightCol];
			if(gamePanel.tileManager.tile[tileNum1].collision == true || gamePanel.tileManager.tile[tileNum2].collision == true) {
				entity.collisionOnY = true;
				entity.collisionOn = true;
				//entity.worldY = Math.max(entity.worldY-entity.speed, entity.worldY);
			}
		}
		else if(entity.direction == "down") {
			entityBottomRow = (entityBottomWorldY + entity.speed)/gamePanel.tileSize;
			tileNum1 = gamePanel.tileManager.mapTileNum[entityBottomRow][entityLeftCol];
			tileNum2 = gamePanel.tileManager.mapTileNum[entityBottomRow][entityRightCol];
			if(gamePanel.tileManager.tile[tileNum1].collision == true || gamePanel.tileManager.tile[tileNum2].collision == true) {
				entity.collisionOnY = true;
				entity.collisionOn = true;
			}
		}
		if(entity.direction == "left") {
			entityLeftCol = (entityLeftWorldX - entity.speed)/gamePanel.tileSize;
			tileNum1 = gamePanel.tileManager.mapTileNum[entityTopRow][entityLeftCol];
			tileNum2 = gamePanel.tileManager.mapTileNum[entityBottomRow][entityLeftCol];
			if(gamePanel.tileManager.tile[tileNum1].collision == true || gamePanel.tileManager.tile[tileNum2].collision == true) {
				entity.collisionOnX = true;
				entity.collisionOn = true;
			}
		}
		
		else if(entity.direction == "right") {
			entityRightCol = (entityRightWorldX + entity.speed)/gamePanel.tileSize;
			tileNum1 = gamePanel.tileManager.mapTileNum[entityTopRow][entityRightCol];
			tileNum2 = gamePanel.tileManager.mapTileNum[entityBottomRow][entityRightCol];
			if(gamePanel.tileManager.tile[tileNum1].collision == true || gamePanel.tileManager.tile[tileNum2].collision == true) {
				entity.collisionOnX = true;
				entity.collisionOn = true;
			}
		}
		
		if (keyHandler.upPressed == true && keyHandler.leftPressed == true) {
			entityTopRow = (entityTopWorldY - entity.speed)/gamePanel.tileSize;
			tileNum1 = gamePanel.tileManager.mapTileNum[entityTopRow][entityLeftCol];
			tileNum2 = gamePanel.tileManager.mapTileNum[entityTopRow][entityRightCol];
	        if (gamePanel.tileManager.tile[tileNum2].collision) {
	            entity.collisionOnY = true;
	        }
	    }
		
		else if (keyHandler.upPressed == true && keyHandler.rightPressed == true) {
			entityTopRow = (entityTopWorldY - entity.speed)/gamePanel.tileSize;
			tileNum1 = gamePanel.tileManager.mapTileNum[entityTopRow][entityRightCol];
			tileNum2 = gamePanel.tileManager.mapTileNum[entityTopRow][entityLeftCol];
	        if ( gamePanel.tileManager.tile[tileNum2].collision) {
	            entity.collisionOnY = true;
	        }
	    }
		
		else if (keyHandler.downPressed == true && keyHandler.leftPressed == true) {
			entityBottomRow = (entityBottomWorldY + entity.speed)/gamePanel.tileSize;
			tileNum1 = gamePanel.tileManager.mapTileNum[entityBottomRow][entityLeftCol];
			tileNum2 = gamePanel.tileManager.mapTileNum[entityBottomRow][entityRightCol];
	        if (gamePanel.tileManager.tile[tileNum2].collision) {
	            entity.collisionOnY = true;
	        }
	    }
		
		else if (keyHandler.downPressed == true && keyHandler.rightPressed == true) {
			entityBottomRow = (entityBottomWorldY + entity.speed)/gamePanel.tileSize;
			
			tileNum1 = gamePanel.tileManager.mapTileNum[entityBottomRow][entityRightCol];
			tileNum2 = gamePanel.tileManager.mapTileNum[entityBottomRow][entityLeftCol];
			
	        if (gamePanel.tileManager.tile[tileNum2].collision) {
	            entity.collisionOnY = true;
	        }
	    }
		
		
			
		
		
		
	}
}
