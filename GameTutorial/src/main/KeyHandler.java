package main;

import java.awt.event.*;

import entity.Player;

public class KeyHandler implements KeyListener{
	
	public boolean upPressed,downPressed,leftPressed,rightPressed;//main movement
	public boolean shiftPressed,spacePressed,iPressed,ePressed;
	
	public boolean eProcessed = false;

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = true;
		}
		
		if(code == KeyEvent.VK_A) {
			leftPressed = true;
		}

		if(code == KeyEvent.VK_S) {
			downPressed = true;
		}

		if(code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		
		if(code == KeyEvent.VK_SHIFT) {
			shiftPressed = true;
		}
		
		if(code == KeyEvent.VK_SPACE) {
			spacePressed = true;
		}
		
		if(code == KeyEvent.VK_I) {
			iPressed = true;
		}
		
		if(code == KeyEvent.VK_E) {
			ePressed = true;
			
			 
		}
		
		
		
	}
	

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed = false;
		}
		
		if(code == KeyEvent.VK_A) {
			leftPressed = false;
		}

		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}

		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		
		if(code == KeyEvent.VK_SHIFT) {
			shiftPressed = false;
		}
		
		if(code == KeyEvent.VK_SPACE) {
			spacePressed = false;
			
		}
		
		if(code == KeyEvent.VK_I) {
			iPressed = false;
		}
		
		if(code == KeyEvent.VK_E) {
			ePressed = false;
			eProcessed = false;
			
		}
		
	}

}
