package fight;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class BattleButton {

	public int x,y;
	
	public int w,h;
	
	public String label;
	
	public Rectangle clickableArea;
	
	public BattleButton(int x,int y,int w,int h,String label) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.label = label;
		clickableArea = new Rectangle(x,y,w,h);
	}
	
	public void draw(Graphics2D g2) {
        // Change color if hovered
//        if (isHovered) {
//            g2.setColor(Color.LIGHT_GRAY);
//        } else {
//            g2.setColor(Color.GRAY);
//        }
		
		g2.setColor(Color.BLACK);
        g2.fillRect(x, y, w, h);

        // Draw the border
        g2.setColor(Color.WHITE);
        g2.drawRect(x, y, w, h);

        // Draw the label
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics = g2.getFontMetrics();
        int labelX = x + (w - metrics.stringWidth(label)) / 2;
        int labelY = y + ((h - metrics.getHeight()) / 2) + metrics.getAscent();
        g2.drawString(label, labelX, labelY);
    }
	
	
}
