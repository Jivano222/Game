package main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import entity.Entity;
import entity.Player;
import fight.BattleButton;
import main.GamePanel.GameState;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

	//SCREEN SETTINGS
	final int originalTileSize = 32; //32 x 32 pixels
	final int scale = 2; //Scales 32 x 32 tiles to 64 x 64
	
	public int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public int screenWidth = maxScreenCol * tileSize; //1024
	public final int screenHeight = maxScreenRow * tileSize; //768
	
	//GAME LOOP
	Thread gameThread;
	private boolean gameRunning;
	private int FPS = 60;
	public boolean isPaused = false;
	
	public enum GameState {
	    PLAYING,
	    PAUSED,
	    BATTLE
	}
	
	
	
	public GameState gameState = GameState.PLAYING;
	
	//TILES
	public TileManager tileManager = new TileManager(this);
	
	//KEY HANDLING
	KeyHandler keyHandler = new KeyHandler();
	//MOUSE HANDLER
	MouseHandler mouseHandler = new MouseHandler();
	
	MouseMotionHandler motionHandler = new MouseMotionHandler();
	
	//BATTLE
	BattleButton attackButton;
		public boolean attackMenuOpen = false;
		public boolean itemMenuOpen = false;
	BattleButton defendButton;
	BattleButton itemButton;
	BattleButton runButton;
	
		//extra battle buttons
		//BattleButton punchButton;
		BattleButton kickButton;
		BattleButton shoutButton;
		BattleButton backButton;
		
		BattleButton atk1Button;
		BattleButton atk2Button;
		BattleButton atk3Button;
		BattleButton atk4Button;
		BattleButton atk5Button;
		BattleButton atk6Button;
		BattleButton atk7Button;
		BattleButton atk8Button;
		BattleButton atk9Button;
		BattleButton atk10Button;
		BattleButton atk11Button;
		BattleButton atk12Button;
		
		BattleButton hpPotButton;
		
		
		
		
		
		//For menu - should refactor battleButton to just be menubutton but oh well for now
		BattleButton weapons;
			BattleButton swapToSword,swapToFist,swapToHammer,back;
			public boolean weaponsOpen;
		
	//dialogue
	public ArrayList<String> dialogue = new ArrayList<String>();
		
	public boolean defended;
	
	
	
	public ArrayList<String> battleLog = new ArrayList<String>();
	public int maxLogDisplay = 10;
	
	public int turnCount = 1;
	
	public boolean playerTurn;
	
	//for battle animation
	public int currentBattleSprite = 1;
	public int battleSpriteCounter = 1;
	//public boolean battleStart = true;
    
	
	//COLLISION CHECKER
	public CollisionChecker collisionChecker = new CollisionChecker(this,keyHandler);
	
	//PLAYER INFO
	public Player player = new Player(this,keyHandler);
	
	//OJBECTS
	public SuperObject objects[] = new SuperObject[20];
	public AssetSetter aSetter = new AssetSetter(this);
	
	
	//ENEMIES
	public Entity enemies[] = new Entity[20];
	
	public Entity npcs[] = new Entity[20];
	
	
	
	//WORLD SETTINGS
	public final int maxWorldCol = 25;
	public final int maxWorldRow = 25;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setDoubleBuffered(true);
		this.setBackground(Color.BLACK);
		this.addKeyListener(keyHandler);
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(motionHandler);
		this.setFocusable(true);
		
	}
	
	/**
	 * Starts the gameloop which is in run();
	 */
	public void startGameLoop() {
		gameThread = new Thread(this); //creates new thread and passes in GamePanel
		gameThread.start();//calls run();
		
	}
	


	@Override
	/**
	 * The game loop in charge of updating and repainting the game
	 */
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		
		//Game loop to run while thread exists
		while(gameThread != null) {
			
			
			currentTime = System.nanoTime();
		
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				if (gameState == GameState.PLAYING) {
					//UPDATE: update game info
					update();
	            }else if(gameState == GameState.BATTLE) {
	            	updateBattle();
	            }
				
				//DRAW: draws the screen based on the info updated
				repaint();
				delta--;
				drawCount++;
			}
			
			if(timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
			
			
		}
		
	}
	
	public void setupGame() {
		aSetter.setObject();
		aSetter.setEnemy();
		aSetter.setNPC();
	}
	
	public void update() {
		player.update();
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g); //always goes in paintComponent();
		
		Graphics2D g2 = (Graphics2D)g; //has more options than g?
			 if (gameState == GameState.PLAYING) {
			tileManager.draw(g2);
			
			if(player.talking==true) {
				//String message = "";
				if(dialogue.isEmpty()==false) {
					String message = dialogue.get(0);
					displayDialogue(g2,message);
				}
				
			}
			
			for(int i = 0;i<objects.length;i++) {
				if(objects[i]!=null) {
					objects[i].draw(g2, this);
				}
			}
			
			for(int i = 0;i<npcs.length;i++) {
				if(npcs[i]!=null) {
					npcs[i].draw(g2, this);
				}
			}
			
			for(int i = 0;i<enemies.length;i++) {
				if(enemies[i]!=null) {
					enemies[i].draw(g2,this);
				}
			}
			
			player.draw(g2);
		 }else if(gameState == GameState.BATTLE) {
			 renderBattleInterface(g2);
		 }else if(gameState == GameState.PAUSED) {
			 updatePaused();
			 renderPaused(g2);
		 }
		
		
		g2.dispose();
		
	}
	
	private void renderBattleInterface(Graphics2D g2) {
	    g2.setColor(Color.BLACK);
	    g2.fillRect(0, 0, screenWidth, screenHeight);
//	    
//	    BufferedImage enemyImage1 = enemies[player.currentlyFighting].state1; 
//	    BufferedImage enemyImage2 = enemies[player.currentlyFighting].state2; 
	    BufferedImage enemyImage = enemies[player.currentlyFighting].state2;
	 
	    
	    battleSpriteCounter++;
	    if(battleSpriteCounter>30) {
	    	if(currentBattleSprite==1) {
		    	
		    	currentBattleSprite=2;
		    }else if(currentBattleSprite==2) {
		    	
		    	currentBattleSprite=1;
		    }
	    	battleSpriteCounter = 0;
	    }
	    
	    if(currentBattleSprite==1) {
	    	enemyImage = enemies[player.currentlyFighting].state1; ;
	    	
	    }else if(currentBattleSprite==2) {
	    	enemyImage = enemies[player.currentlyFighting].state2; ;
	    
	    }
	    
	    
	    g2.drawImage(enemyImage, (screenWidth/2)-(enemyImage.getWidth()*enemies[player.currentlyFighting].battleScaling)/2, (screenHeight/4)-(enemyImage.getHeight()*enemies[player.currentlyFighting].battleScaling)/2, enemyImage.getWidth()*enemies[player.currentlyFighting].battleScaling, enemyImage.getHeight()*enemies[player.currentlyFighting].battleScaling,null);
	    
	    
	    	attackButton = new BattleButton(30,374,470,180,"Attack");
		    defendButton = new BattleButton(530,374,470,180,"Defend");
		    itemButton = new BattleButton(30,576,470,180,"Items");
		    runButton = new BattleButton(530,576,470,180,"Run");
	    
		    
		    backButton = new BattleButton(526,550,466,146,"Back");
		    
		    if(player.holding == "fist") {
		    	atk1Button = new BattleButton(30,374,218,58,"Punch");
			    atk2Button = new BattleButton(278,374,218,58,"Kick");
			    atk3Button = new BattleButton(526,374,218,58,"Grapple");	    
			    atk4Button = new BattleButton(774,374,218,58,"Throw");
		    }else if(player.holding == "sword") {
		    	atk1Button = new BattleButton(30,374,218,58,"Slash");
			    atk2Button = new BattleButton(278,374,218,58,"Stab");
			    atk3Button = new BattleButton(526,374,218,58,"Pommel Strike");	    
			    atk4Button = new BattleButton(774,374,218,58,"Impale");
		    }else if(player.holding == "hammer") {
		    	atk1Button = new BattleButton(30,374,218,58,"Crushing Blow");
			    atk2Button = new BattleButton(278,374,218,58,"Breaker Barrage");
			    atk3Button = new BattleButton(526,374,218,58,"Hammerfall");	    
			    atk4Button = new BattleButton(774,374,218,58,"Iron Tempest");
		    }
		    
		    
		    atk5Button = new BattleButton(30,462,218,58,"ATK5");
		    atk6Button = new BattleButton(278,462,218,58,"ATK6");
		    atk7Button = new BattleButton(526,462,218,58,"Zibzog Slayer");	    
		    atk8Button = new BattleButton(774,462,218,58,"ATK8");
		    
		    atk9Button = new BattleButton(30,550,218,58,"ATK9");
		    atk10Button = new BattleButton(278,550,218,58,"ATK10");
		    
		    atk11Button = new BattleButton(30,638,218,58,"ATK11");	    
		    atk12Button = new BattleButton(278,638,218,58,"ATK12");
		    
		    
		    
		    hpPotButton = new BattleButton(30,374,218,58,"Health Potion: " + String.valueOf(player.hpPots));
		   

	    
	    
		    
		    if((itemMenuOpen == false && attackMenuOpen==false)) {
		    	if(attackButton.clickableArea.contains(motionHandler.hoveredX,motionHandler.hoveredY)) {
		    		drawHoveredButton(g2, attackButton);
		    	}else {
		    		attackButton.draw(g2);
		    	}
		    	
		    	if(defendButton.clickableArea.contains(motionHandler.hoveredX,motionHandler.hoveredY)) {
		    		drawHoveredButton(g2, defendButton);
		    	}else {
		    		defendButton.draw(g2);
		    	}
		    	
		    	if(itemButton.clickableArea.contains(motionHandler.hoveredX,motionHandler.hoveredY)) {
		    		drawHoveredButton(g2, itemButton);
		    	}else {
		    		itemButton.draw(g2);
		    	}
		    	
		    	if(runButton.clickableArea.contains(motionHandler.hoveredX,motionHandler.hoveredY)) {
		    		drawHoveredButton(g2, runButton);
		    	}else {
		    		runButton.draw(g2);
		    	}
		    	
			   
			    
		    }else if(attackMenuOpen==true ) {
		    	//for the actual attack menu im thinking display each as if every possible attack in the game was there
		    	//but with like random boxes where not unlocked spells or attacks are or whatever
		    	//System.out.println("reaching attackMenuOpen");
		    	
		    	//also i should check if hovered and clicked or do something so it doesnt register a click where mouse is when fight starts
		    	if(player.atk1Unlock==true) {
		    		if(atk1Button.clickableArea.contains(motionHandler.hoveredX,motionHandler.hoveredY)) {
			    		drawHoveredButton(g2,atk1Button);
			    	}else {
			    		
			    		atk1Button.draw(g2);
			    	}
		    	}else {
		    		
		    		drawLockedButton(g2,atk1Button);
		    	}
		    	
		    	if(player.atk2Unlock==true) {
		    		if(atk2Button.clickableArea.contains(motionHandler.hoveredX,motionHandler.hoveredY)) {
			    		drawHoveredButton(g2,atk2Button);
			    	}else {
			    		
			    		atk2Button.draw(g2);
			    	}
		    	}else {
		    		drawLockedButton(g2,atk2Button);
		    	}
		    	
		    	if(player.atk3Unlock==true) {
		    		if(atk3Button.clickableArea.contains(motionHandler.hoveredX,motionHandler.hoveredY)) {
			    		drawHoveredButton(g2,atk3Button);
			    	}else {
			    		
			    		atk3Button.draw(g2);
			    	}
		    	}else {
		    		drawLockedButton(g2,atk3Button);
		    	}
		    	
		    	if(player.atk4Unlock==true) {
		    		if(atk4Button.clickableArea.contains(motionHandler.hoveredX,motionHandler.hoveredY)) {
			    		drawHoveredButton(g2,atk4Button);
			    	}else {
			    		
			    		atk4Button.draw(g2);
			    	}
		    	}else {
		    		drawLockedButton(g2,atk4Button);
		    	}
		    	
		    	if(player.atk5Unlock==true) {
		    		if(atk5Button.clickableArea.contains(motionHandler.hoveredX,motionHandler.hoveredY)) {
			    		drawHoveredButton(g2,atk5Button);
			    	}else {
			    		
			    		atk5Button.draw(g2);
			    	}
		    	}else {
		    		drawLockedButton(g2,atk5Button);
		    	}

		    	if(player.atk6Unlock==true) {
		    		if(atk6Button.clickableArea.contains(motionHandler.hoveredX,motionHandler.hoveredY)) {
			    		drawHoveredButton(g2,atk6Button);
			    	}else {
			    		
			    		atk6Button.draw(g2);
			    	}
		    	}else {
		    		drawLockedButton(g2,atk6Button);
		    	}
		    	
		    	if(player.atk7Unlock==true) {
		    		if(atk7Button.clickableArea.contains(motionHandler.hoveredX,motionHandler.hoveredY)) {
			    		drawHoveredButton(g2,atk7Button);
			    	}else {
			    		
			    		atk7Button.draw(g2);
			    	}
		    	}else {
		    		drawLockedButton(g2,atk7Button);
		    	}
		    	
		    	if(player.atk8Unlock==true) {
		    		if(atk8Button.clickableArea.contains(motionHandler.hoveredX,motionHandler.hoveredY)) {
			    		drawHoveredButton(g2,atk8Button);
			    	}else {
			    		
			    		atk8Button.draw(g2);
			    	}
		    	}else {
		    		drawLockedButton(g2,atk8Button);
		    	}
		    	
		    	if(player.atk9Unlock==true) {
		    		if(atk9Button.clickableArea.contains(motionHandler.hoveredX,motionHandler.hoveredY)) {
			    		drawHoveredButton(g2,atk9Button);
			    	}else {
			    		
			    		atk9Button.draw(g2);
			    	}
		    	}else {
		    		drawLockedButton(g2,atk9Button);
		    	}
		    	
		    	if(player.atk10Unlock==true) {
		    		if(atk10Button.clickableArea.contains(motionHandler.hoveredX,motionHandler.hoveredY)) {
			    		drawHoveredButton(g2,atk10Button);
			    	}else {
			    		
			    		atk10Button.draw(g2);
			    	}
		    	}else {
		    		drawLockedButton(g2,atk10Button);
		    	}
		    	
		    	if(player.atk11Unlock==true) {
		    		if(atk11Button.clickableArea.contains(motionHandler.hoveredX,motionHandler.hoveredY)) {
			    		drawHoveredButton(g2,atk11Button);
			    	}else {
			    		
			    		atk11Button.draw(g2);
			    	}
		    	}else {
		    		drawLockedButton(g2,atk11Button);
		    	}
		    	
		    	if(player.atk12Unlock==true) {
		    		if(atk12Button.clickableArea.contains(motionHandler.hoveredX,motionHandler.hoveredY)) {
		    			if(atk12Button.clickableArea.contains(motionHandler.hoveredX,motionHandler.hoveredY)) {
				    		drawHoveredButton(g2,atk12Button);
				    	}else {
				    		
				    		atk12Button.draw(g2);
				    	}
			    	}else {
			    		
			    		atk12Button.draw(g2);
			    	}
		    	}else {
		    		drawLockedButton(g2,atk12Button);
		    	}
		    	
		    	if(backButton.clickableArea.contains(motionHandler.hoveredX,motionHandler.hoveredY)) {
		    		drawHoveredButton(g2,backButton);
		    	}else {
		    		
		    		backButton.draw(g2);
		    	}
		    }else if(itemMenuOpen==true) {
		    	if(player.hpPots>0 && hpPotButton.clickableArea.contains(motionHandler.hoveredX,motionHandler.hoveredY)) {
		    		drawHoveredButton(g2,hpPotButton);
		    	}else if(player.hpPots<=0) {
		    		drawNoItems(g2, hpPotButton);
		    	}else {
		    		
		    		hpPotButton.draw(g2);
		    	}
		    	
		    	if(backButton.clickableArea.contains(motionHandler.hoveredX,motionHandler.hoveredY)) {
		    		drawHoveredButton(g2,backButton);
		    	}else {
		    		
		    		backButton.draw(g2);
		    	}
		    }
		  
		    
	    
	    g2.setColor(Color.WHITE);
      

	    int lineHeight = 20; // Start drawing from the top
       

	    // Draw the box
	    g2.setColor(Color.WHITE); // Box border color
	    g2.drawRect(((screenWidth/2)-(enemyImage.getWidth()*enemies[player.currentlyFighting].battleScaling)/2), 20, enemyImage.getWidth()*enemies[player.currentlyFighting].battleScaling, 344);//add a field for like enemy get battleScaling to correcly scale larger sprites if wanted
	    //g2.drawRect(((screenWidth/2)-(enemyImage.getWidth()*7)/2) - enemyImage.getWidth()*7 - 20, 20, enemyImage.getWidth()*7, 344);
	    
	    
      
        int lineY = 60;
        //Font originalFont = g2.getFont();
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        for (int i = 0;i<battleLog.size();i++) {
            g2.drawString(battleLog.get(i), 40, lineY); // Draw each line with a small margin
            lineY += lineHeight; // Move down for the next line
        }
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        
        //g2.setFont(originalFont);
	  
	    g2.setColor(Color.WHITE);
	    //PLAYER STATS
	    g2.drawString("PLAYER STATS",828,60);
	    g2.drawString("   HP : " + String.valueOf(player.hp).toUpperCase(), 828, 100);
	    g2.drawString("   ATK: " + String.valueOf(player.atk).toUpperCase(), 828, 140);
	    g2.drawString("   DEF: " + String.valueOf(player.def).toUpperCase(), 828, 180);
	    g2.drawString("   SPD: " + String.valueOf(player.spd).toUpperCase(), 828, 220);
	    if(player.poisoned == true) {
	    	g2.drawString("   STAT: POISON (" + String.valueOf(player.poisonCounter) +")", 828, 260);
	    }else if( player.bleeding == true) {
	    	g2.drawString("   STAT: BLEED (" + String.valueOf(player.bleedCounter) +")", 828, 260);
	    }else {
	    	g2.drawString("   STAT: CLEAR", 828, 260);
	    }
	    
	    
	  //ENEMY STATS
	    g2.drawString(enemies[player.currentlyFighting].name.toUpperCase() + " STATS",658,60);
	    g2.drawString("   HP : " + String.valueOf(enemies[player.currentlyFighting].hp).toUpperCase(), 658, 100);
	    g2.drawString("   ATK: " + String.valueOf(enemies[player.currentlyFighting].atk).toUpperCase(), 658, 140);
	    g2.drawString("   DEF: " + String.valueOf(enemies[player.currentlyFighting].def).toUpperCase(), 658, 180);
	    g2.drawString("   SPD: " + String.valueOf(enemies[player.currentlyFighting].spd).toUpperCase(), 658, 220);
	    if(enemies[player.currentlyFighting].poisoned == true) {
	    	g2.drawString("   STAT: POISON (" + String.valueOf(enemies[player.currentlyFighting].poisonCounter) +")", 658, 260);
	    }else {
	    	g2.drawString("   STAT: CLEAR", 658, 260);
	    }
	    
	    //ENEMY NAME
	    g2.drawString((enemies[player.currentlyFighting].name).toUpperCase(), screenWidth / 2 - (enemies[player.currentlyFighting].name).length()*6 , screenHeight / 10);
	    //g2.drawString((enemies[player.currentlyFighting].name).toUpperCase() + " HP: " + String.valueOf(enemies[player.currentlyFighting].hp).toUpperCase(), screenWidth / 4 , (screenHeight / 10) *2 );
	    
	    
	}
	
	public void renderPaused(Graphics2D g2) {
		 g2.setFont(new Font("Arial", Font.BOLD, 40));
		 g2.setColor(Color.WHITE);
	     g2.drawString("MENU", screenWidth/2-60, 50);
	     g2.setFont(new Font("Arial", Font.BOLD, 18));
	     
	     Color originalColor = g2.getColor();
	     Font originalFont = g2.getFont();
	     BufferedImage playerImage = player.down1;
	     g2.drawImage(playerImage, (screenWidth/2)-(playerImage.getWidth()*5)/2-350, (screenHeight/4)-(playerImage.getHeight()*5)/2, playerImage.getWidth()*5, playerImage.getHeight()*5,null);
	     weapons = new BattleButton(screenWidth/2-200,100,400,100,"Weapons");
	     swapToFist = new BattleButton(screenWidth/2-200,100,400,100,"Equip Fists");
	     swapToSword = new BattleButton(screenWidth/2-200,240,400,100,"Equip Sword");
	     swapToHammer = new BattleButton(screenWidth/2-200,380,400,100,"Equip Hammer");
	    
	     
	     
	     back = new BattleButton(screenWidth/2-200,600,400,100,"Back");
//	     swapToSword.draw(g2);
//	     swapToFist.draw(g2);
	     if(weaponsOpen==true) {
	    	 if(player.atk1Unlock == true) {//should be a check for weapon unlocks, not yet implemented
		    		if(swapToSword.clickableArea.contains(motionHandler.hoveredX,motionHandler.hoveredY)) {
		    			
				    		drawHoveredButton(g2,swapToSword);
				    	
			    	}else {
			    		
			    		swapToSword.draw(g2);
			    	}
		     }
		     
		     if(player.atk1Unlock == true) {//should be a check for weapon unlocks, not yet implemented
		    		if(swapToFist.clickableArea.contains(motionHandler.hoveredX,motionHandler.hoveredY)) {
		    			
				    		drawHoveredButton(g2,swapToFist);
				    	
			    	}else {
			    		
			    		swapToFist.draw(g2);
			    	}
		     }

		
		     if(player.hammerUnlock == true) {
		    	 if(swapToHammer.clickableArea.contains(motionHandler.hoveredX,motionHandler.hoveredY)) {
		    			
			    		drawHoveredButton(g2,swapToHammer);
			    	
		    	}else {
		    		
		    		swapToHammer.draw(g2);
		    	}
		     }else {
		    	 drawLockedButton(g2,swapToHammer);
		     }
		    		if(back.clickableArea.contains(motionHandler.hoveredX,motionHandler.hoveredY)) {
		    			
				    		drawHoveredButton(g2,back);
				    	
			    	}else {
			    		
			    		back.draw(g2);
			    	}
		    	
		     
	     }else {
	
		    		if(weapons.clickableArea.contains(motionHandler.hoveredX,motionHandler.hoveredY)) {
		    			
				    		drawHoveredButton(g2,weapons);
				    	
			    	}else {
			    		
			    		weapons.draw(g2);
			    	}
		     
		     
	     }
	     
	     
	     
	     int clickedX = mouseHandler.clickedX;
		 int clickedY = mouseHandler.clickedY;
	     if(weaponsOpen == true && swapToSword.clickableArea.contains(clickedX,clickedY)) {
	    	 mouseHandler.resetClicked();
	    	 player.holding="sword";
	    	 player.getPlayerImage();
	    	 System.out.println("SWAPPED TO SWORD");
	     }else if(weaponsOpen == true && swapToFist.clickableArea.contains(clickedX,clickedY)) {
	    	 mouseHandler.resetClicked();
	    	 player.holding="fist";
	    	 player.getPlayerImage();
	    	 System.out.println("SWAPPED TO FIST");
	     }else if(weaponsOpen == true && swapToHammer.clickableArea.contains(clickedX,clickedY)) {
	    	 mouseHandler.resetClicked();
	    	 player.holding="hammer";
	    	 player.getPlayerImage();
	    	 System.out.println("SWAPPED TO HAMMER");
	     }else if(weapons.clickableArea.contains(clickedX,clickedY)) {
	    	 mouseHandler.resetClicked();
	    	 weaponsOpen=true;
	    	 
	     }else if(back.clickableArea.contains(clickedX,clickedY)) {
	    	 mouseHandler.resetClicked();
	    	 weaponsOpen=false;
	     }
	     
	     //later for checking if swords unlocked
	   
	     //could turn into a method to use in either of the current menus
	     //would have to take x & y to redraw in correct pos
	     g2.setColor(originalColor);
	     g2.setFont(originalFont);
	     
	     g2.drawString("PLAYER STATS",screenWidth/2 - (playerImage.getWidth()*5)/2-350,320);
		 g2.drawString("   HP : " + String.valueOf(player.hp).toUpperCase(), screenWidth/2 - (playerImage.getWidth()*5)/2-350, 360);
		 g2.drawString("   ATK: " + String.valueOf(player.atk).toUpperCase(), screenWidth/2 - (playerImage.getWidth()*5)/2-350, 400);
		 g2.drawString("   DEF: " + String.valueOf(player.def).toUpperCase(), screenWidth/2 - (playerImage.getWidth()*5)/2-350, 440);
		 g2.drawString("   SPD: " + String.valueOf(player.spd).toUpperCase(), screenWidth/2 - (playerImage.getWidth()*5)/2-350, 480);
		 
		 
		 g2.drawString("Completed Quests",(screenWidth/4)*3,80);
		 for(int i = 0;i<player.questManager.completedQuests.size();i++) {
			 int index = i+1;//quests start at 1
			 String questName = player.questManager.completedQuests.get(index);
			 g2.drawString(questName, (screenWidth/4)*3, 120+40*i);
		 }
		 
		 g2.drawString("   HP : " + String.valueOf(player.hp).toUpperCase(), screenWidth/2 - (playerImage.getWidth()*5)/2-350, 360);
		 g2.drawString("   ATK: " + String.valueOf(player.atk).toUpperCase(), screenWidth/2 - (playerImage.getWidth()*5)/2-350, 400);
		 g2.drawString("   DEF: " + String.valueOf(player.def).toUpperCase(), screenWidth/2 - (playerImage.getWidth()*5)/2-350, 440);
		 g2.drawString("   SPD: " + String.valueOf(player.spd).toUpperCase(), screenWidth/2 - (playerImage.getWidth()*5)/2-350, 480);
	}
	
	
	public void updatePaused() {
		if(keyHandler.iPressed==true) {
			if(gameState == GameState.PLAYING) {
				gameState = GameState.PAUSED;
				keyHandler.iPressed=false;
			}else {
				gameState = GameState.PLAYING;
				keyHandler.iPressed=false;
			}
			
		}
		
	}
	
	public void updateBattle() {
		
		if(playerTurn==true) {
			int clickedX = mouseHandler.clickedX;
			int clickedY = mouseHandler.clickedY;
			
			if(attackMenuOpen == false && itemMenuOpen == false && attackButton.clickableArea.contains(clickedX,clickedY)) {
				attackMenuOpen = true;
				mouseHandler.resetClicked();
				
				
//				enemies[player.currentlyFighting].hp-=1;
//				mouseHandler.resetClicked();
//				
//				String outputMsg = "Turn " + String.valueOf(turnCount)+": " +"You hit the " + enemies[player.currentlyFighting].name + "!";
//	        	battleLog.add(outputMsg);
//	        	if(battleLog.size()>maxLogDisplay) {
//	        		battleLog.remove(0);
//	        	}
//	        	
//	        	battleLog.add("     Did " + "1" + " damage!");
//				if(battleLog.size()>maxLogDisplay) {
//	        		battleLog.remove(0);
//	        	}
//				
//				if(enemies[player.currentlyFighting].hp==0) {
//					//gameState = GameState.PLAYING;
//					endBattle();
//					enemies[player.currentlyFighting] = null;
//				}
//				playerTurn = false;
//				turnCount++;
			}else if(attackMenuOpen == false && itemMenuOpen == false && runButton.clickableArea.contains(clickedX,clickedY)) {
				//gameState = GameState.PLAYING;
				//reset enemy state so you cant return to fight to cheat it
				
				endBattle();
				
				mouseHandler.resetClicked();
				
				playerTurn = false;
				
				player.worldX = player.checkPointX;
				player.worldY = player.checkPointY;
			}else if(attackMenuOpen == false && itemMenuOpen == false && defendButton.clickableArea.contains(clickedX,clickedY)) {
		        mouseHandler.resetClicked();
				Random random = new Random();
		        int roll = random.nextInt(2);
		        if(roll==1) {
		        	defended = true;
		        	String outputMsg = "Turn " + String.valueOf(turnCount)+": " +"You defended yourself!";
		        	battleLog.add(outputMsg);
		        	if(battleLog.size()>maxLogDisplay) {
		        		battleLog.remove(0);
		        	}
		        	
		        }else {
		        	String outputMsg = "Turn " + String.valueOf(turnCount)+": " +"You failed to defend yourself!";
		        	battleLog.add(outputMsg);
		        	if(battleLog.size()>maxLogDisplay) {
		        		battleLog.remove(0);
		        	}
		        }
	
		        playerTurn = false;
				turnCount++;
				if(player.poisoned==true) {
					System.out.println("test");
					player.poisonCounter--;
					player.hp -= 4;//poison damge, true damage ignores defense
					if(player.poisonCounter<=0) {
						player.poisoned = false;
					}
				}else if(player.bleeding==true) {
					
					player.bleedCounter--;
					player.hp -= 2;
					if(player.bleedCounter<=0) {
						player.bleeding = false;
					}
				}
			}else if(attackMenuOpen == false && itemMenuOpen == false && itemButton.clickableArea.contains(clickedX,clickedY)) {
				mouseHandler.resetClicked();
				itemMenuOpen=true;
			}else if((attackMenuOpen == true || itemMenuOpen == true) && backButton.clickableArea.contains(clickedX,clickedY)) {
				attackMenuOpen = false;
				itemMenuOpen = false;
				mouseHandler.resetClicked();
			}else if(attackMenuOpen == true && itemMenuOpen == false && player.atk1Unlock==true && atk1Button.clickableArea.contains(clickedX,clickedY)) {
				mouseHandler.resetClicked();
				//make this into an attack method later
				Random random = new Random();
		        int roll = random.nextInt(3)+1;
		        int turnDmg = player.atk * roll - enemies[player.currentlyFighting].def;
		        if(turnDmg <0) {
		        	turnDmg = 0;
		        }
		        
				String outputMsg = "Turn " + String.valueOf(turnCount)+": " +"You hit the " + enemies[player.currentlyFighting].name + " with your hammer!";
				battleLog.add(outputMsg);
				if(battleLog.size()>maxLogDisplay) {
	        		battleLog.remove(0);
	        	}
	        	
	        	battleLog.add("     Did " + String.valueOf(turnDmg) + " damage!");
				if(battleLog.size()>maxLogDisplay) {
	        		battleLog.remove(0);
	        	}
				
				
				enemies[player.currentlyFighting].hp -= turnDmg;
				if(enemies[player.currentlyFighting].hp<=0) {
					endBattle();
					if(enemies[player.currentlyFighting].name=="keyman") {
						player.keyCount++;
					}else if(enemies[player.currentlyFighting].name=="slime") {
						player.slimeKC++;
					}
					enemies[player.currentlyFighting] = null;
				}
				//attackMenuOpen = false;
				playerTurn = false;
				turnCount++;
				if(player.poisoned==true) {
					System.out.println("test");
					player.poisonCounter--;
					player.hp -= 4;//poison damge, true damage ignores defense
					if(player.poisonCounter<=0) {
						player.poisoned = false;
					}
				}else if(player.bleeding==true) {
					
					player.bleedCounter--;
					player.hp -= 2;
					if(player.bleedCounter<=0) {
						player.bleeding = false;
					}
				}
			
				
			}else if(attackMenuOpen == true && itemMenuOpen == false && player.atk2Unlock==true && atk2Button.clickableArea.contains(clickedX,clickedY)) {
				mouseHandler.resetClicked();
				Random random = new Random();
		        int roll;
		        //high damage vs low armour
		        if(enemies[player.currentlyFighting].def<5) {
		        	roll = random.nextInt(10)+3;
		        }else {
		        	roll = random.nextInt(1);
		        }
		        int turnDmg = player.atk * roll - enemies[player.currentlyFighting].def;
		        if(turnDmg < 0) {
		        	turnDmg = 0;
		        }
		        
		        String outputMsg = "Turn " + String.valueOf(turnCount)+": " +"You stabbed the " + enemies[player.currentlyFighting].name + "!";
				battleLog.add(outputMsg);
				if(battleLog.size()>maxLogDisplay) {
	        		battleLog.remove(0);
	        	}
	        	
	        	battleLog.add("     Did " + String.valueOf(turnDmg) + " damage!");
				if(battleLog.size()>maxLogDisplay) {
	        		battleLog.remove(0);
	        	}
				
				
				enemies[player.currentlyFighting].hp -= turnDmg;
				if(enemies[player.currentlyFighting].hp<=0) {
					endBattle();
					if(enemies[player.currentlyFighting].name=="keyman") {
						player.keyCount++;
					}
					enemies[player.currentlyFighting] = null;
				}
				//attackMenuOpen = false;
				playerTurn = false;
				turnCount++;
				if(player.poisoned==true) {
					System.out.println("test");
					player.poisonCounter--;
					player.hp -= 4;//poison damge, true damage ignores defense
					if(player.poisonCounter<=0) {
						player.poisoned = false;
					}
				}else if(player.bleeding==true) {
					
					player.bleedCounter--;
					player.hp -= 2;
					if(player.bleedCounter<=0) {
						player.bleeding = false;
					}
				}
			}else if(attackMenuOpen == true && itemMenuOpen == false && player.atk7Unlock==true && atk7Button.clickableArea.contains(clickedX,clickedY)) {
				mouseHandler.resetClicked();
				int turnDmg = 0;
				if(enemies[player.currentlyFighting].name=="zibzog") {
					Random random = new Random();
			        int roll = random.nextInt(15);
			        turnDmg = player.atk * roll - enemies[player.currentlyFighting].def;
			        if(turnDmg<0) {
			        	turnDmg = 0;
			        }
			        String outputMsg = "Turn " + String.valueOf(turnCount)+": " +"You strike Zibzog with the hammer!";
					battleLog.add(outputMsg);
					if(battleLog.size()>maxLogDisplay) {
		        		battleLog.remove(0);
		        	}
		        	
		        	battleLog.add("     Did " + String.valueOf(turnDmg) + " damage!");
					if(battleLog.size()>maxLogDisplay) {
		        		battleLog.remove(0);
		        	}
				}else {
					battleLog.add("Turn " + String.valueOf(turnCount)+": " + "The hammer feels too heavy to swing!");
					if(battleLog.size()>maxLogDisplay) {
		        		battleLog.remove(0);
		        	}
				}
				enemies[player.currentlyFighting].hp -= turnDmg;
				if(enemies[player.currentlyFighting].hp<=0) {
					endBattle();
					if(enemies[player.currentlyFighting].name=="keyman") {
						player.keyCount++;
					}else if(enemies[player.currentlyFighting].name=="slime") {
						player.slimeKC++;
					}
					enemies[player.currentlyFighting] = null;
				}
				playerTurn = false;
				turnCount++;
				if(player.poisoned==true) {
					System.out.println("test");
					player.poisonCounter--;
					player.hp -= 4;//poison damge, true damage ignores defense
					if(player.poisonCounter<=0) {
						player.poisoned = false;
					}
				}else if(player.bleeding==true) {
					
					player.bleedCounter--;
					player.hp -= 2;
					if(player.bleedCounter<=0) {
						player.bleeding = false;
					}
				}
			}else if(player.hpPots>0 && attackMenuOpen == false && itemMenuOpen == true && hpPotButton.clickableArea.contains(clickedX,clickedY)) {
				
				mouseHandler.resetClicked();
				
				player.hpPots -=1;
				player.hp+=20;
				
				//itemMenuOpen = false;
				playerTurn = false;
				turnCount++;
				if(player.poisoned==true) {
					//System.out.println("test");
					player.poisonCounter--;
					player.hp -= 4;//poison damge, true damage ignores defense
					if(player.poisonCounter<=0) {
						player.poisoned = false;
					}
				}else if(player.bleeding==true) {
					
					player.bleedCounter--;
					player.hp -= 2;
					if(player.bleedCounter<=0) {
						player.bleeding = false;
					}
				}
			}
		//ENEMY TURN
			
		}else if(playerTurn == false) {
			 
			boolean isAttack = true;
			int turnDmg = 0;
			Random random = new Random();
	        int roll = random.nextInt(5)+1; 
	        if(roll == 1) {
	        	String[] output = new String[4];
	        	output = enemies[player.currentlyFighting].move1();
	        	checkStatus(output[2],output[3],player);
	        	if(output[0]!="null") {
	        		turnDmg = Integer.valueOf(output[0]);
	        	}else {
	        		isAttack = false;
	        	}
	        	String outputMsg = "Turn " + String.valueOf(turnCount)+": " + output[1];
	        	battleLog.add(outputMsg);
	        	if(battleLog.size()>maxLogDisplay) {
	        		battleLog.remove(0);
	        	}
	        	System.out.println(outputMsg);
	        }else if(roll == 2) {
	        	String[] output = new String[4];
	        	output = enemies[player.currentlyFighting].move2();
	        	checkStatus(output[2],output[3],player);
	        	if(output[0]!="null") {
	        		turnDmg = Integer.valueOf(output[0]);
	        	}else {
	        		isAttack = false;
	        	}
	        	String outputMsg = "Turn " + String.valueOf(turnCount)+": " + output[1];
	        	battleLog.add(outputMsg);
	        	if(battleLog.size()>maxLogDisplay) {
	        		battleLog.remove(0);
	        	}
	        	System.out.println(outputMsg);
	        }else if(roll==3){
	        	String[] output = new String[4];
	        	output = enemies[player.currentlyFighting].move3();
	        	checkStatus(output[2],output[3],player);
	        	//checkStatus(output[2],output[3],player);
	        	if(output[0]!="null") {
	        		turnDmg = Integer.valueOf(output[0]);
	        	}else {
	        		isAttack = false;
	        	}
	        	String outputMsg = "Turn " + String.valueOf(turnCount)+": " + output[1];
	        	battleLog.add(outputMsg);
	        	if(battleLog.size()>maxLogDisplay) {
	        		battleLog.remove(0);
	        	}
	        	System.out.println(outputMsg);
	        }else if(roll == 4) {
	        	
	        	String[] output = new String[4];
	        	output = enemies[player.currentlyFighting].move4();
	        	String outputMsg = "Turn " + String.valueOf(turnCount)+": " + output[1];
	        	checkStatus(output[2],output[3],player);
	        	
	        	if(output[0]!="null") {
	        		turnDmg = Integer.valueOf(output[0]);
	        	}else {
	        		isAttack = false;
	        	}
	        	
	        	battleLog.add(outputMsg);
	        	if(battleLog.size()>maxLogDisplay) {
	        		battleLog.remove(0);
	        	}
	        	System.out.println(outputMsg);
	        }else {
	        	String outputMsg = "Turn " + String.valueOf(turnCount)+": " + "The " + enemies[player.currentlyFighting].name + " did nothing!";
	        	battleLog.add(outputMsg);
	        	if(battleLog.size()>maxLogDisplay) {
	        		battleLog.remove(0);
	        	}
	        	isAttack = false;
	        
	        }
			if(isAttack == true && defended == true) {
				turnDmg=0;
				defended = false;
			}
	        if(isAttack == true) {
	        	System.out.println(turnDmg);
	        	if(turnDmg>player.def) {
					turnDmg -= player.def;
					battleLog.add("     Did " + String.valueOf(turnDmg) + " damage!");
					if(battleLog.size()>maxLogDisplay) {
		        		battleLog.remove(0);
		        	}
				}else {
					turnDmg = 0;
					battleLog.add("     Did " + "0" + " damage!");
					if(battleLog.size()>maxLogDisplay) {
		        		battleLog.remove(0);
		        	}
				}
				
				player.hp-= turnDmg;
				
	        }
	        if(enemies[player.currentlyFighting].hp<=0) {
				endBattle();
				if(enemies[player.currentlyFighting].name=="keyman") {
					player.keyCount++;
				}else if(enemies[player.currentlyFighting].name=="slime") {
					player.slimeKC++;
				}
				enemies[player.currentlyFighting] = null;
			}
			
			playerTurn = true;
			turnCount++;
		}
		
		
		
		
	}
	
	public void endBattle() {
		turnCount = 1;
		battleLog.clear();
		gameState = GameState.PLAYING;
		attackMenuOpen = false;
		defended = false;
		itemMenuOpen=false;
		clearConditions(player);
		
	}
	
	public void drawLockedButton(Graphics2D g2, BattleButton locked) {
		
		Font originalFont = g2.getFont();
		// Draw a placeholder for ATK1 if not unlocked
        g2.setColor(Color.GRAY); // Placeholder background color
        g2.fillRect(locked.x, locked.y, locked.w, locked.h);
        g2.setColor(Color.DARK_GRAY); // Placeholder border color
        g2.drawRect(locked.x, locked.y, locked.w, locked.h);

        // Draw "Locked" text
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 25));
        FontMetrics metrics = g2.getFontMetrics();
        String lockedText = "Locked";
        int textX = locked.x + (locked.w - metrics.stringWidth(lockedText)) / 2;
        int textY = locked.y + (locked.h - metrics.getHeight()) / 2 + metrics.getAscent();
        g2.drawString(lockedText, textX, textY);
        g2.setFont(originalFont);
	}
	
public void drawNoItems(Graphics2D g2, BattleButton locked) {
		
		Font originalFont = g2.getFont();
		// Draw a placeholder for ATK1 if not unlocked
        g2.setColor(Color.GRAY); // Placeholder background color
        g2.fillRect(locked.x, locked.y, locked.w, locked.h);
        g2.setColor(Color.DARK_GRAY); // Placeholder border color
        g2.drawRect(locked.x, locked.y, locked.w, locked.h);

        // Draw "Locked" text
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 25));
        FontMetrics metrics = g2.getFontMetrics();
        String lockedText = locked.label;
        int textX = locked.x + (locked.w - metrics.stringWidth(lockedText)) / 2;
        int textY = locked.y + (locked.h - metrics.getHeight()) / 2 + metrics.getAscent();
        g2.drawString(lockedText, textX, textY);
        g2.setFont(originalFont);
	}
	
	
	public void drawHoveredButton(Graphics2D g2, BattleButton hovered) {
		
		Font originalFont = g2.getFont();
		// Draw a placeholder for ATK1 if not unlocked
        g2.setColor(Color.WHITE); // Placeholder background color
        g2.fillRect(hovered.x, hovered.y, hovered.w, hovered.h);
        g2.setColor(Color.DARK_GRAY); // Placeholder border color
        g2.drawRect(hovered.x, hovered.y, hovered.w, hovered.h);

        // Draw "Locked" text
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 25));
        FontMetrics metrics = g2.getFontMetrics();
        String lockedText = hovered.label;
        int textX = hovered.x + (hovered.w - metrics.stringWidth(lockedText)) / 2;
        int textY = hovered.y + (hovered.h - metrics.getHeight()) / 2 + metrics.getAscent();
        g2.drawString(lockedText, textX, textY);
        g2.setFont(originalFont);
	}
	
	public void checkStatus(String status, String value,Entity entity) {//takes a string for the status or debuff, and the entity to apply it to if applicable value is amount to debuff by or turn count for poison etc
		if(status == "null") {
			return;
		}else if(status == "poison") {
			clearConditions(entity);
			entity.poisoned = true;
			
//			
        	entity.poisonCounter = Integer.valueOf(value);
		}else if(status =="attackdebuff") {
			entity.atk-=Integer.valueOf(value);
			
//			String outputMsg = "	" + entity.name + "'s atk was lowered!";
//        	battleLog.add(outputMsg);
//        	if(battleLog.size()>maxLogDisplay) {
//        		battleLog.remove(0);
//        	}
        	
		}else if(status == "bleed") {
			clearConditions(entity);
			entity.bleeding = true;
//			
        	entity.bleedCounter = Integer.valueOf(value);
		}
	}
	
	public void clearConditions(Entity entity) {
		entity.poisoned = false;
		entity.bleeding = false;
		
		entity.poisonCounter = 0;
		entity.bleedCounter = 0;
	}
	
//	public void displayDialogue(Graphics2D g2, String dialogue) {
//		Font originalFont = g2.getFont();
//		// Draw a placeholder for ATK1 if not unlocked
//        g2.setColor(Color.WHITE); // Placeholder background color
//        g2.fillRect(screenWidth/2,(screenHeight/4)*3,200,50);
//        g2.setColor(Color.BLACK); // Placeholder border color
//        g2.drawRect(screenWidth/2,(screenHeight/4)*3,200,50);
//
//        // Draw "Locked" text
//        g2.setColor(Color.BLACK);
//        g2.setFont(new Font("Arial", Font.BOLD, 25));
//        FontMetrics metrics = g2.getFontMetrics();
//        
////        int textX = hovered.x + (hovered.w - metrics.stringWidth(lockedText)) / 2;
////        int textY = hovered.y + (hovered.h - metrics.getHeight()) / 2 + metrics.getAscent();
//        g2.drawString(dialogue, screenWidth/2,(screenHeight/4)*3);
//        g2.setFont(originalFont);
//	}
	public void displayDialogue(Graphics2D g2, String dialogue) {
	    // Save the original font to restore later
	    Font originalFont = g2.getFont();

	    // Define dimensions for the dialogue box
	    int boxWidth = screenWidth - 40; // Leave padding on the sides
	    int boxHeight = 100; // Height of the box
	    int boxX = 20; // X position for padding
	    int boxY = screenHeight - boxHeight - 20; // Position the box near the bottom

	    // Draw the dialogue box
	    g2.setColor(Color.BLACK); // Background color
	    g2.fillRect(boxX, boxY, boxWidth, boxHeight);
	    g2.setColor(Color.WHITE); // Border color
	    g2.drawRect(boxX, boxY, boxWidth, boxHeight);

	    // Draw the text inside the box
	    g2.setColor(Color.WHITE);
	    g2.setFont(new Font("Arial", Font.BOLD, 25));
	    FontMetrics metrics = g2.getFontMetrics();

	    // Calculate the position for the text to center it within the box
	    int textX = boxX + (boxWidth - metrics.stringWidth(dialogue)) / 2; // Center horizontally
	    int textY = boxY + (boxHeight - metrics.getHeight()) / 2 + metrics.getAscent(); // Center vertically

	    // Draw the dialogue text
	    g2.drawString(dialogue, textX, textY);

	    // Restore the original font
	    g2.setFont(originalFont);
	}
	
	public void addDialogue(String[] messages) {
		
		for(int i = 0;i<messages.length;i++) {
			dialogue.add(messages[i]);
		}
	}

	
	
}
