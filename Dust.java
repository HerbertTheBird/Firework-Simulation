import java.awt.*;
public class Dust {
	double x, y;
	double vx, vy;
	double brightness;
	double dimRate;
	double randomMove;
	Color c = new Color(255, 240, 200);
	boolean exist = true;
	int type;
	double flicker;
	double wind = .025;
	public Dust(double x, double y, double brightness, int type, Color c) {
		this.x = x;
		this.y = y;
		vx = 0;
		vy = 0;
		this.brightness = brightness;
		this.type = type;
		if(type == 0) {
			dimRate = 0.02;
			randomMove = 0.5;
			flicker = 0.8;
		}
		if(type == 1) {
			dimRate = 0.02;
			randomMove = 0.05;
			flicker = 0.8;
		}
		if(type == 2) {
			dimRate = 0.01;
			randomMove = 0.01;
			flicker = 0.8;
			this.c = c;
		}
		if(type == 3) {
			dimRate = 0.005;
			randomMove = 0.05;
			flicker = 0.8;
		}
		if(type == 4) {
			dimRate = 0.005;
			randomMove = 0.05;
			flicker = 0.8;
			this.c = c;
		}
		if(type == 5) {
			dimRate = 0.02;
			randomMove = 0.05;
			flicker = 0.6;
		}
		if(type == 6) {
			dimRate = 0.01;
			randomMove = 0.5;
			flicker = 0.8;
		}
		if(type == 7) {
			dimRate = 0.02;
			randomMove = 0.5;
			flicker = 0.8;
		}
	}
	public void update() {
		if(exist) {
			x += vx;
			y += vy;
			vy += FireworkShow.g/2;
			vx *= (1-FireworkShow.dustResistance);
			vy *= (1-FireworkShow.dustResistance);
			vx += (Math.random()-.5)*randomMove;
			vy += (Math.random()-.5)*randomMove;
			vx += wind;
			brightness *= (1-dimRate);
			if(brightness < Math.random()) {
				exist = false;
			}
			if(Math.random() < 0.01) {
				exist = false;
			}
		}
	}
	public void paint(Graphics g) {
		if(exist) {
			g.setColor(percievedColor());
			if(Math.random()<flicker)
			g.fillRect((int)x, (int)y, 1, 1);
			else
				g.fillRect((int)x, (int)y, 2, 2);
		}
	}
	public Color percievedColor() {
		int red = Math.min(255, (int)(c.getRed()*brightness));
		int green = Math.min(255, (int)(c.getGreen()*brightness));
		int blue = Math.min(255, (int)(c.getBlue()*brightness));
		return new Color(red, green, blue);
	}
	public static Dust generateDust(Firework f) {
		return new Dust(f.x+Math.random()*5-2.5, f.y+Math.random()*5-2.5, Math.sqrt(f.brightness), f.type, Color.white);
	}
	public static Dust generateDust(Star s) {
		return new Dust(s.x+Math.random()*5-2.5, s.y+Math.random()*5-2.5, Math.sqrt(s.brightness), s.type, s.c);
	}
}
