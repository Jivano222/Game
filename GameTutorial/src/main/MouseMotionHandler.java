package main;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MouseMotionHandler extends MouseMotionAdapter {
	public int hoveredX;
	public int hoveredY;
	
	public void mouseMoved(MouseEvent e) {
		Point cursorPosition = e.getPoint();
		hoveredX = (int) cursorPosition.getX();
		hoveredY = (int) cursorPosition.getY();
	}

}
