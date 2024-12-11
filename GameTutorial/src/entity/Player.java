package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import main.GamePanel;
import main.KeyHandler;
import main.QuestManager;
import main.GamePanel.GameState;

public class Player extends Entity {

	GamePanel gamePanel;
	KeyHandler keyHandler;
	
	public QuestManager questManager = new QuestManager();

	public final int screenX;
	public final int screenY;

	public int keyCount = 0;
	public int bossKeyCount = 0;
	
	public int slimeKC;

	public int currentlyFighting = 999;
	
	public int currentlyInteracting;
	
	public int hpPots = 3;
	
	public int dodgeCounter;
	
	//attack unlocks
	public boolean atk1Unlock = true;
	public boolean atk2Unlock = true;
	public boolean atk3Unlock = true;
	public boolean atk4Unlock = true;
	public boolean atk5Unlock = false;
	public boolean atk6Unlock = false;
	public boolean atk7Unlock = false;
	public boolean atk8Unlock = false;
	public boolean atk9Unlock = false;
	public boolean atk10Unlock = false;
	public boolean atk11Unlock = false;
	public boolean atk12Unlock = false;
	
	//wpn management
	public boolean swordUnlock,hammerUnlock;
	
	
	public int checkPointX;
	public int checkPointY;
	
	public boolean talking = false;
	public int talkCounter = 0;
	public Timer timer;
	
	public String holding = "fist";
	
	//Inventory
	ArrayList<String> inventory = new ArrayList<String>();


	public Player(GamePanel gamePanel, KeyHandler keyHandler) {
		
		
		this.name = "Player";
		this.hp = 100;
		this.atk = 4;
		this.def = 2;
		this.spd = 100;

		this.gamePanel = gamePanel;
		this.keyHandler = keyHandler;

		screenX = gamePanel.screenWidth / 2 - gamePanel.tileSize / 2;
		screenY = gamePanel.screenHeight / 2 - gamePanel.tileSize / 2;

		solidArea = new Rectangle();
		solidArea.x = 16;
		solidArea.y = 32;

		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		solidArea.height = 32;
		solidArea.width = 32;

		setDefaultValues();
		getPlayerImage();
	}

	public void setDefaultValues() {
		worldX = gamePanel.tileSize * 5;
		worldY = gamePanel.tileSize * 4;
		checkPointX  = gamePanel.tileSize * 5;
		checkPointY  = gamePanel.tileSize * 4;
		speed = 5;
		direction = "down";
	}

	public void getPlayerImage() {

		try {
			if(holding=="fist") {
				up1 = ImageIO.read(getClass().getResourceAsStream("/player/playerFistUp1.png"));
				up2 = ImageIO.read(getClass().getResourceAsStream("/player/playerFistUp2.png"));
				down1 = ImageIO.read(getClass().getResourceAsStream("/player/playerFistDown1.png"));//down1
				down2 = ImageIO.read(getClass().getResourceAsStream("/player/playerFistDown2.png"));//down2
				left1 = ImageIO.read(getClass().getResourceAsStream("/player/playerFistLeft1.png"));
				left2 = ImageIO.read(getClass().getResourceAsStream("/player/playerFistLeft2.png"));
				right1 = ImageIO.read(getClass().getResourceAsStream("/player/playerFistRight1.png"));
				right2 = ImageIO.read(getClass().getResourceAsStream("/player/playerFistRight2.png"));
			}else if(holding =="sword") {
				up1 = ImageIO.read(getClass().getResourceAsStream("/player/playerSwordUp1.png"));
				up2 = ImageIO.read(getClass().getResourceAsStream("/player/playerSwordUp2.png"));
				down1 = ImageIO.read(getClass().getResourceAsStream("/player/playerSwordDown1.png"));//down1
				down2 = ImageIO.read(getClass().getResourceAsStream("/player/playerSwordDown2.png"));//down2
				left1 = ImageIO.read(getClass().getResourceAsStream("/player/playerSwordLeft1.png"));
				left2 = ImageIO.read(getClass().getResourceAsStream("/player/playerSwordLeft2.png"));
				right1 = ImageIO.read(getClass().getResourceAsStream("/player/playerSwordRight1.png"));
				right2 = ImageIO.read(getClass().getResourceAsStream("/player/playerSwordRight2.png"));
			}else if(holding=="hammer") {
				up1 = ImageIO.read(getClass().getResourceAsStream("/player/playerHammerUp1.png"));
				up2 = ImageIO.read(getClass().getResourceAsStream("/player/playerHammerUp2.png"));
				down1 = ImageIO.read(getClass().getResourceAsStream("/player/playerHammerDown1.png"));//down1
				down2 = ImageIO.read(getClass().getResourceAsStream("/player/playerHammerDown2.png"));//down2
				left1 = ImageIO.read(getClass().getResourceAsStream("/player/playerHammerLeft1.png"));
				left2 = ImageIO.read(getClass().getResourceAsStream("/player/playerHammerLeft2.png"));
				right1 = ImageIO.read(getClass().getResourceAsStream("/player/playerHammerRight1.png"));
				right2 = ImageIO.read(getClass().getResourceAsStream("/player/playerHammerRight2.png"));
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ePressHandle() {
		
	}
	public void update() {
		if(keyHandler.ePressed==true) {
			 if (!keyHandler.eProcessed) {
		            keyHandler.eProcessed = true; // Mark the key as processed
		            //System.out.println("test e single press");
		            if(talking == true) {
		    			if(gamePanel.dialogue.isEmpty()==false) {
		    				gamePanel.dialogue.remove(0);
		    				if(gamePanel.dialogue.isEmpty()==true) {
		    					talking = false;
		    				}
		    			}
		    		}else {
		    			interactNPC(gamePanel.collisionChecker.checkNPC(this, true));
		    		}
		    		
		    		
		            
		        }
		}
		
		if(keyHandler.iPressed==true) {
			if(gamePanel.gameState == GameState.PLAYING) {
				gamePanel.gameState = GameState.PAUSED;
				keyHandler.iPressed=false;
			}else{
				gamePanel.gameState = GameState.PLAYING;
				keyHandler.iPressed=false;
			}
			
		}

		// basic movement
		if (keyHandler.upPressed == true || keyHandler.downPressed == true || keyHandler.leftPressed == true
				|| keyHandler.rightPressed == true) {
			if (keyHandler.upPressed == true) {
				direction = "up";
				directionY = "up";

			}

			 if (keyHandler.downPressed == true) {
				direction = "down";
				directionY = "down";

			}

			 if (keyHandler.leftPressed == true) {
				direction = "left";
				directionX = "left";

			}

			 if (keyHandler.rightPressed == true) {
				direction = "right";
				directionX = "right";

			}

			if (keyHandler.shiftPressed == true) {
				speed = 8;
				
				// spriteCounter++;
			
			} else {
				speed = 4;
			}
			
			
//			if(dodgeCounter<100) {
//				dodgeCounter++;
//			}
//		    if(keyHandler.spacePressed == true&& dodgeCounter == 100) {	
//		    	speed = 40;
//		    	dodgeCounter = 0;
//		    }

			// CHECK TILE COLLISION
			collisionOn = false;
			collisionOnY = false;
			gamePanel.collisionChecker.checkTile(this);
			int objIndex = gamePanel.collisionChecker.checkObject(this, true);
			interactObject(objIndex);
			currentlyFighting = gamePanel.collisionChecker.checkEnemy(this, true);
			interactEnemy(currentlyFighting);
			
			
			

			// if collisionOn false player can move
			
			if(keyHandler.spacePressed == true) {
				collisionOnX=false;
				collisionOnY=false;
				collisionOn=false;
			}
			
			if (collisionOnY == false) {
				if (keyHandler.upPressed == true  && keyHandler.downPressed == false) {
					worldY -= speed;

				}

				else if (keyHandler.downPressed == true && keyHandler.upPressed == false) {
					worldY += speed;

				}
			
			}
			
//			collisionOn = false;
//			collisionOnY = false;
//			gamePanel.collisionChecker.checkTile(this);
			if (collisionOn == false) {
			    if (keyHandler.leftPressed == true && keyHandler.rightPressed == false) {
					worldX -= speed;

				}

			    else if (keyHandler.rightPressed == true && keyHandler.leftPressed == false) {
					worldX += speed;

				}
			}

			// switch sprites for animation
			// if((keyHandler.upPressed == true
//			|| keyHandler.downPressed == true 
//			|| keyHandler.rightPressed == true 
//			|| keyHandler.leftPressed == true )) {

			spriteCounter++;
			if (spriteCounter > 10) {
				if (spriteNum == 1) {
					spriteNum = 2;
				} else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}

	}

	public void draw(Graphics2D g2) {

//		g2.setColor(Color.MAGENTA);	
//		g2.fillRect(x,y,gamePanel.tileSize,gamePanel.tileSize);

		BufferedImage image = null;
		
		//test for adding dialogue from npcs or from other interactions
//		if (talking == true) {
//			Font originalFont = g2.getFont();
//			String testText = "testing dialogue";
//			g2.setColor(Color.BLACK);
//			g2.fillRect(gamePanel.screenWidth/2-200,(gamePanel.screenHeight/10)*8, 400, 50);
//			g2.setColor(Color.WHITE);
//			g2.drawRect(gamePanel.screenWidth/2-200,(gamePanel.screenHeight/10)*8, 400, 50);
//			 g2.setFont(new Font("Arial", Font.BOLD, 25));
//			for(int i = 0;i<testText.length();i++) {
//				
//				g2.drawString(""+testText.charAt(i), gamePanel.screenWidth/2-200 + 20 + i*20,(gamePanel.screenHeight/10)*8 + 20);
//			}
//			 g2.setFont(originalFont);
//		}

		switch (direction) {
		case "up":
			if (spriteNum == 1) {
				image = up1;
			}
			if (spriteNum == 2) {
				image = up2;
			}
			break;
		case "down":
			if (spriteNum == 1) {
				image = down1;
			}
			if (spriteNum == 2) {
				image = down2;
			}
			break;
		case "left":
			if (spriteNum == 1) {
				image = left1;
			}
			if (spriteNum == 2) {
				image = left2;
			}
			break;
		case "right":
			if (spriteNum == 1) {
				image = right1;
			}
			if (spriteNum == 2) {
				image = right2;
			}
			break;
		}
		g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);

	}

	public void interactObject(int index) {
		if (index != 999) {
			if (gamePanel.objects[index].name == "key") {
				keyCount++;
				gamePanel.objects[index] = null;
			} else if (gamePanel.objects[index].name == "keyDoor") {
				if (keyCount > 0) {
					int tempX = gamePanel.objects[index].worldX/gamePanel.tileSize;
					int tempY = gamePanel.objects[index].worldY/gamePanel.tileSize;
					gamePanel.tileManager.mapTileNum[tempY][tempX]=2;
					gamePanel.objects[index] = null;
					keyCount--;
				}
			}else if(gamePanel.objects[index].name=="sword") {
				//atk2Unlock = true;
				gamePanel.objects[index] = null;
				holding = "sword";
				swordUnlock = true;
				getPlayerImage();
				
			}else if(gamePanel.objects[index].name == "bossKey") {
				bossKeyCount++;
				gamePanel.objects[index] = null;
				
			}else if(gamePanel.objects[index].name == "bossDoor") {
				if (bossKeyCount > 0) {
					int tempX = gamePanel.objects[index].worldX/gamePanel.tileSize;
					int tempY = gamePanel.objects[index].worldY/gamePanel.tileSize;
					gamePanel.tileManager.mapTileNum[tempY][tempX]=2;
					gamePanel.objects[index] = null;
					bossKeyCount--;
				}
			}else if(gamePanel.objects[index].name == "bosshammer") {
				atk7Unlock = true;
				gamePanel.objects[index] = null;
			}

		}

		return;
	}

	public void interactEnemy(int index) {
		if (index != 999) {
			if (gamePanel.enemies[index].name == "slime") {
				if (this.spd > gamePanel.enemies[index].spd) {
					gamePanel.playerTurn = true;
				} else {
					gamePanel.playerTurn = false;
				}

				gamePanel.gameState = GameState.BATTLE;
				// gamePanel.enemies[index]=null;
			} else if (gamePanel.enemies[index].name == "colby") {
				if (this.spd > gamePanel.enemies[index].spd) {
					gamePanel.playerTurn = true;
				} else {
					gamePanel.playerTurn = false;
				}
				gamePanel.gameState = GameState.BATTLE;
			}else if(gamePanel.enemies[index].name == "zibzog") {
				if (this.spd > gamePanel.enemies[index].spd) {
					gamePanel.playerTurn = true;
				} else {
					gamePanel.playerTurn = false;
				}
				gamePanel.gameState = GameState.BATTLE;
			}else if(gamePanel.enemies[index].name == "keyman") {
				if (this.spd > gamePanel.enemies[index].spd) {
					gamePanel.playerTurn = true;
				} else {
					gamePanel.playerTurn = false;
				}
				gamePanel.gameState = GameState.BATTLE;
			}
		}
	}
	
	public void interactNPC(int index) {
		if(index == 999) {
			return;
		}else if(index == 0 && gamePanel.npcs[index].name == "oldman") {
			questManager.quest1(slimeKC,this);
			if(questManager.currentQuests.get(1)==null && questManager.quest1Complete==false) {
				System.out.println("You interacting with the old man");
				String[] messages = new String[3];
				messages[0] = "hello!";
				messages[1] = "here is a quest, kill 2 slimes";
				messages[2] = "goodbye!";
			
				questManager.currentQuests.put(1,"Cull Slime Population");
				//questManager.quest1(slimeKC);
				
				
				if(talking == false) {
					gamePanel.addDialogue(messages);
				}
				talking = true;
			}else if(questManager.quest1Complete==false) {
				String[] messages = new String[1];
				messages[0] = "Quit yapping and go do the quest!";
				if(talking == false) {
					gamePanel.addDialogue(messages);
				}
				talking = true;
			}else if(questManager.quest1Complete==true && questManager.quest1Claimed==false) {
				
				questManager.quest1Claimed=true;
				System.out.println("quest complete");
				System.out.println("You interacting with the old man");
				String[] messages = new String[2];
				messages[0] = "hello!";
				messages[1] = "you finished the quest, take this hammer";
				holding = "hammer";
				getPlayerImage();
				if(talking == false) {
					gamePanel.addDialogue(messages);
				}
				talking = true;
			}else if(questManager.quest1Complete == true && questManager.quest1Claimed==true) {
				String[] messages = new String[1];
				messages[0] = "thanks again for the help";
			
			
				if(talking == false) {
					gamePanel.addDialogue(messages);
				}
				talking = true;
			}
		}else {
			//talking = false;
		}
	}

	@Override
	public String[] move1() {
		if(holding == "fist") {
			String[] output = new String[4];
			Random random = new Random();
	        int roll = random.nextInt(3)+1; 
	        output[0] = String.valueOf(roll *this.atk);
	        output[1] = "You punched the " + gamePanel.enemies[currentlyFighting].name.toUpperCase() +"!";
	        output[2] = "null";
	        output[3] = "0";
	        
	        return output;
			
		}else if(holding == "sword") {
			
		}else if(holding == "hammer") {
			
		}
		String[] output = new String[2];
		return output;
	}

	@Override
	public String[] move2() {
		if(holding == "fist") {
			
		}else if(holding == "sword") {
			
		}else if(holding == "hammer") {
			
		}
		return null;
	}

	@Override
	public String[] move3() {
		if(holding == "fist") {
			
		}else if(holding == "sword") {
			
		}else if(holding == "hammer") {
			
		}
		return null;
	}

	@Override
	public String[] move4() {
		if(holding == "fist") {
			
		}else if(holding == "sword") {
			
		}else if(holding == "hammer") {
			
		}
		return null;
	}

}
