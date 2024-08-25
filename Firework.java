import java.awt.*;
import java.util.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
public class Firework {
	double x, y;
	double vx, vy;
	boolean exploded = false;
	ArrayList<Star> stars = new ArrayList<Star>();
	ArrayList<Dust> dust = new ArrayList<Dust>();
	Color c;
	int type;
	int numStars;
	int numDust;
	double starSpeed;
	double brightness = 2;
	double dimRate = 0.025;
	public Firework(int x, double vy, Color c, int type) {
		this.x = x;
		this.y = FireworkShow.height;
		this.vx = 0;
		this.vy = vy;
		this.c = c;
		this.type = type;
		if(type == 0) {
			numStars = 0;
			numDust = 50;
		}
		if(type == 1) {
			numStars = 251;
			numDust = 10;
			starSpeed = 8;
		}
		if(type == 2) {
			numStars = 101;;
			numDust = 20;
			starSpeed = 8;
		}
		if(type == 3) {
			numStars = 19;
			numDust = 10;
			starSpeed = 4;
		}
		if(type == 4) {
			numStars = 101;
			numDust = 10;
			starSpeed = 4;
		}
		if(type == 5) {
			numStars = 499;
			numDust = 5;
			starSpeed = 8;
		}
		if(type == 6) {
			numStars = 23;
			numDust = 10;
			starSpeed = 8;
		}
	}
	public void paint(Graphics g) {
		if(!exploded) {
			g.setColor(percievedColor(50));
			for(int i = 1; i <= 10; i++) {
				g.fillOval((int)x-i, (int)y-i, i*2, i*2);
			}
		}
		else {
			for(Star star:stars) {
				star.paint(g);
			}
		}
		for(Dust d:dust) {
			d.paint(g);
		}
	}
	public void update() {
		if(!exploded) {
			if(Math.abs(vy) < 3) {
				exploded = true;
				for(int i = 0; i < numStars; i++) {
					stars.add(Star.generateStar(this, i));
				}
			}
			for(int i = 0; i < numDust; i++) {
				x += vx/numDust;
				y += vy/numDust;
				dust.add(Dust.generateDust(this));
			}
			vy += FireworkShow.g;
			vx *= (1-FireworkShow.airResistance);
			vy *= (1-FireworkShow.airResistance);
			brightness *= (1-dimRate);
		}
		else {
			for(Star star:stars) {
				star.update();
			}
		}
		for(Dust d:dust) {
			d.update();
		}
		dust.removeIf(d -> !d.exist);
		stars.removeIf(s -> (!s.exist&&s.dust.size() == 0));
	}
	public Color percievedColor(int alpha) {
		int red = Math.min(255, (int)(c.getRed()*brightness));
		int green = Math.min(255, (int)(c.getGreen()*brightness));
		int blue = Math.min(255, (int)(c.getBlue()*brightness));
		return new Color(red, green, blue, alpha);
	}
}
